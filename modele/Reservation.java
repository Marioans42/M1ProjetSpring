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
@Table(name = "Reservation")
@SequenceGenerator(name="sequence_id",sequenceName="reservation_id_seq",allocationSize=1)
public class Reservation extends BaseModele {
    @Column(name = "idseancesalle")
    private Integer idSeance;
    @Column(name = "idUtilisateur")
    private Integer idUtilisateur;
    @Column(name = "nbPlace")
    private Integer nbPlace;

    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(Integer nbPlace) {
        this.nbPlace = nbPlace;
    }
    
}
