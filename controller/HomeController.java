/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import m1.film.metier.FilmMetier;
import m1.film.modele.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author user
 */
@Controller
public class HomeController {
    @Autowired
    private FilmMetier filmMetier;
    
    public FilmMetier getFilmMetier() {
        return filmMetier;
    }

    public void setFilmMetier(FilmMetier filmMetier) {
        this.filmMetier = filmMetier;
    }
    
    @GetMapping("")
    public ModelAndView index() throws Exception {
        Film aChercher = new Film();
        Integer count  = getFilmMetier().count(aChercher);
        int nbPage  = getFilmMetier().nbPage(count, 3);
        List<Film> films = getFilmMetier().findAll(aChercher,3,0);
        ModelAndView mav = new ModelAndView("listFilm");
        mav.addObject("films", films);
        mav.addObject("nbPage", nbPage);
        mav.addObject("page", 1);
        mav.addObject("title", "Liste films");
        return mav;
    }
    
    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) throws Exception {
       ModelAndView mav = new ModelAndView("redirect:/");
       return mav;
    }
}
