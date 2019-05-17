/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.metier;

import m1.film.model.Login;
import m1.film.modele.Utilisateur;
import org.springframework.stereotype.Service;


/**
 *
 * @author user
 */
@Service
public class UtilisateurMetier extends BaseMetier {
    public Utilisateur valider(Login log){
        Utilisateur search  = new Utilisateur();
        search.setLogin(log.getUsername());
        search.setMdp(log.getPassword());
        Utilisateur user = (Utilisateur) this.findObject(search);
        return user;
    }
}
