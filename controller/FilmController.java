/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.controller;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import m1.film.metier.BaseMetier;
import m1.film.metier.FilmMetier;
//import m1.gcinema.modele.Film;
import m1.film.modele.Film;
import m1.film.modele.Utilisateur;
import m1.film.util.ResultatPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author user
 */
@Controller
@RequestMapping(value = "/films")
public class FilmController {
    @Autowired
    private FilmMetier filmMetier;
    @Autowired
    private BaseMetier baseMetier;
    private final int size = 3;
    private static final String UPLOADED_FOLDER = "D:\\image\\";
    
    public FilmMetier getFilmMetier() {
        return filmMetier;
    }

    public void setFilmMetier(FilmMetier filmMetier) {
        this.filmMetier = filmMetier;
    }
    
    public BaseMetier getBaseMetier() {
        return baseMetier;
    }

    public void setBaseMetier(BaseMetier baseMetier) {
        this.baseMetier = baseMetier;
    }
    
    @GetMapping("")
    public ModelAndView index(HttpSession session,@RequestParam(value = "recherche", required = false) String recherche,@RequestParam(value = "page", required = false) Integer page) throws Exception {
        ModelAndView mav = new ModelAndView();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("authentification");
        if(utilisateur==null){
            mav.setViewName("redirect:/login");
            return mav;
        }
        if(!utilisateur.estEmployer()){
            mav.setViewName("error");
            mav.addObject("title", "Erreur");
            mav.addObject("message", "Vous n'avez pas le droit d'accéder a cette page");
            return mav;
        }
        mav.setViewName("admin/films");
        if(page == null){
            page = 1;
        }if(recherche ==null){
            recherche = "";
        }
        ResultatPage result = getFilmMetier().rechercheGlobal(recherche,page,size);
        mav.addObject("films", result.getList());
        mav.addObject("nbPage", result.getNbPage());
        mav.addObject("page", page);
        mav.addObject("recherche", recherche);
        mav.addObject("title", "Gestion des films");
        return mav;
    }
    
    @GetMapping("/recherche")
    public ModelAndView recherche(@RequestParam(value = "valeur") String valeur,@RequestParam(value = "page", required = false) Integer page) throws Exception {
        if(page == null){
                page = 1;
        }
        ResultatPage result = getFilmMetier().rechercheGlobal(valeur,page,size);
        ModelAndView mav = new ModelAndView("resultRecherche");
        mav.addObject("films", result.getList());
        mav.addObject("nbPage", result.getNbPage());
        mav.addObject("page", page);
        mav.addObject("valeur", valeur);
        mav.addObject("title", "Recherche");
        return mav;
    }
    
    @GetMapping("/recherchemulti")
    public ModelAndView rechercheMulti(@RequestParam(value = "titre") String titre,
            @RequestParam(value = "annee") String annee,
            @RequestParam(value = "categorie") Integer categorie,
            @RequestParam(value = "realisateur") String realisateur,
            @RequestParam(value = "page", required = false) Integer page) throws Exception {
            if(page == null){
                page = 1;
            }
            ResultatPage result = getFilmMetier().rechercheMuticritere(titre,annee,categorie,realisateur,page,size);
            ModelAndView mav = new ModelAndView("rechercheMulticritere");
            mav.addObject("films", result.getList());
            mav.addObject("nbPage", result.getNbPage());
            mav.addObject("page", page);
            mav.addObject("titre", titre);
            mav.addObject("annee", annee);
            mav.addObject("categorie", categorie);
            mav.addObject("realisateur", realisateur);
            mav.addObject("title", "Recherche");
        return mav;
    }
    
    @GetMapping("/{id}")
    public ModelAndView filmDetail(@PathVariable(value = "id") Integer id) throws Exception {
        Film film = new Film();
        film.setId(id);
        film = (Film) getFilmMetier().findById(film);
        ModelAndView mav = new ModelAndView("detailFilm");
        mav.addObject("film", film);
        mav.addObject("title", film.getTitre());
        return mav;
    }
    
    
    @GetMapping("/{id}/seances/add")
    public ModelAndView ajouterSeance(@PathVariable(value = "id") Integer id) throws Exception {
        Film film = new Film();
        film.setId(id);
        film = (Film) getFilmMetier().findById(film);
        ModelAndView mav = new ModelAndView("ajoutseance");
        mav.addObject("film", film);
        mav.addObject("title", "Ajouter séances : "+ film.getTitre());
        return mav;
    }
    
    @GetMapping("/page/{pagenum}")
    public ModelAndView filmPage(@PathVariable(value = "pagenum") Integer page) throws Exception {
        Film aChercher = new Film();
        Integer count  = getFilmMetier().count(aChercher);
        int nbPage  = getFilmMetier().nbPage(count, size);
        List<Film> films = getFilmMetier().findAll(aChercher,size,(page-1) * size);
        ModelAndView mav = new ModelAndView("listFilm");
        mav.addObject("films", films);
        mav.addObject("nbPage", nbPage);
        mav.addObject("page", page);
        mav.addObject("title", "Liste film");
        return mav;
    }
    
    @GetMapping("/image/{imageName}")
    public void getImage(@PathVariable(value = "imageName") String imageName,HttpServletResponse response) throws IOException{
        File serverFile = null;
        byte[] imgByte = null;
        try{
            serverFile = new File(UPLOADED_FOLDER + imageName + ".jpg");
            imgByte = Files.readAllBytes(serverFile.toPath());
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            ServletOutputStream responseOutputStream = response.getOutputStream();
            responseOutputStream.write(imgByte);
            responseOutputStream.flush();
            responseOutputStream.close();
        }catch(NoSuchFileException ex){
            serverFile = new File(UPLOADED_FOLDER + "default.jpg");
            imgByte = Files.readAllBytes(serverFile.toPath());
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            ServletOutputStream responseOutputStream = response.getOutputStream();
            responseOutputStream.write(imgByte);
            responseOutputStream.flush();
            responseOutputStream.close();
        }
    }
    
    @GetMapping("/add")
    public ModelAndView ajouterFilm() throws Exception {
        ModelAndView mav = new ModelAndView("admin/formulairefilm");
        mav.addObject("title", "Ajouter nouveau film");
        return mav;
    }
    
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable(value = "id")Integer id) throws Exception {
        Film film = new Film();
        film.setId(id);
        filmMetier.delete(film);
        ModelAndView mav = new ModelAndView("redirect:/films");
        return mav;
    }
    
    @GetMapping("/update/{id}")
    public ModelAndView ouvrirFormulaire(@PathVariable(value = "id")Integer id) throws Exception {
        Film film = new Film();
        film.setId(id);
        film = (Film) getFilmMetier().findById(film);
        ModelAndView mav = new ModelAndView("admin/formulairemodiffilm");
        mav.addObject("film",film);
        return mav;
    }
    
    @PostMapping("/update/{id}")
    public ModelAndView modifFilm(Film film,@PathVariable(value = "id")Integer id) throws Exception {
        getFilmMetier().update(film);
        ModelAndView mav = new ModelAndView("redirect:/films");
        mav.addObject("film",film);
        return mav;
    }
    
    @PostMapping("")
    public String ajouterFilm(Film film,@RequestParam("file") MultipartFile file) throws Exception{
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                getBaseMetier().save(film);
                String nameImage = String.format("%d.jpg",film.getId());
                Path path = Paths.get(UPLOADED_FOLDER + nameImage);
                Files.write(path, bytes);
                return "redirect:/films";
            } catch (Exception e) {
                return "redirect:/uploadFailure";
            }
        }
        return "redirect:/uploadFailure";
    }
    
    
}
