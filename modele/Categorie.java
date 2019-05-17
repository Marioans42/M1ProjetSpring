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
@Table(name = "Categorie")
@SequenceGenerator(name="sequence_id",sequenceName="categorie_id_seq",allocationSize=1 )
public class Categorie extends BaseModele {
    @Column(name = "libelle")
    private String libelle;
    
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
