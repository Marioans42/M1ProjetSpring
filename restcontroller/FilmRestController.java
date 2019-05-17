/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.restcontroller;

import java.util.List;
import m1.film.metier.FilmMetier;
import m1.film.modele.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */
@RestController
public class FilmRestController {
    @Autowired
    private FilmMetier filmMetier;
     
    public FilmMetier getFilmMetier() {
        return filmMetier;
    }

    public void setFilmMetier(FilmMetier filmMetier) {
        this.filmMetier = filmMetier;
    }
   
    @GetMapping("/filmrest")
    public List<Film> getListCat(@RequestParam(value = "q") String valeur) throws Exception {
        List<Film> films = getFilmMetier().rechercheGlobal(valeur);
        return films;
    }
}
