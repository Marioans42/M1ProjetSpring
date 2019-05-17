/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity 
@Table(name = "Utilisateur")
@SequenceGenerator(name="sequence_id",sequenceName="utilisateur_id_seq",allocationSize=1 )
public class Utilisateur extends BaseModele {
    @Column(name = "nom")
    private String nom;
    @Column(name = "login")
    private String login;
    @Column(name = "mdp")
    private String mdp;
    private Integer idRole;
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    
    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }
    
    public boolean estEmployer(){
        if(idRole == 2){
            return true;
        }
        return false;
    }
}
