/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.controller;

import m1.film.model.Login;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import m1.film.metier.UtilisateurMetier;
import m1.film.modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @Autowired
    UtilisateurMetier utilisateurMetier;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin() {
      ModelAndView mav = new ModelAndView("login");
      mav.addObject("login", new Login());
      return mav;
    }
    
    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess( HttpServletRequest request,@ModelAttribute("login") Login login) {
        ModelAndView mav = null;
        Utilisateur user = utilisateurMetier.valider(login);
        if (null != user) {
            HttpSession sess = request.getSession();
            sess.setAttribute("authentification", user);
            mav = new ModelAndView("redirect:/home");
        } else {
            mav = new ModelAndView("redirect:/login");
            mav.addObject("error", "Erreur");
        }
        return mav;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView deconnexion(HttpServletRequest request) {
      ModelAndView mav = new ModelAndView("redirect:/home");
      HttpSession sess = request.getSession();
      sess.removeAttribute("authentification");
      return mav;
    }
}