/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.modele;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import m1.film.util.Util;
import org.joda.time.DateTime;

/**
 *
 * @author user
 */
@Entity 
@Table(name = "FilmAvecCategorie")
@SequenceGenerator(name="sequence_id",sequenceName="film_id_seq",allocationSize=1 )
public class Film extends BaseModele {
    private String categorie;
    private String titre;
    private String annee;
    private Date datesortie;
    private Time duree;
    private Integer idcategorie;
    private String realisateur;
    private String description;
    
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public void setDuree(Time time){
        this.duree = time;
    }
    
    public void setDuree(String duree) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        long ms = sdf.parse(duree).getTime();
        this.duree = new Time(ms);
    }
    
    public String getAnnee() {
        return annee;
    }
    
    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public Time getDuree() {
        return duree;
    }
    
    public String getDureeString() {
        DateTime dat = new DateTime(duree);
        String res = String.format("%dh%d",dat.getHourOfDay(),dat.getMinuteOfHour());
        return res;
    }

    public Integer getIdcategorie() {
        return idcategorie;
    }
    
    public void setIdcategorie(Integer idcategorie) {
        this.idcategorie = idcategorie;
    }
    
    
    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getResume() {
        if(description.length() < 120)
        return description + "...";
        else return description.substring(0, 120) + "...";
    }
    
    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    
    public Date getDatesortie() {
        return datesortie;
    }

    public String getDatesortieString() {
        return Util.dateToString(datesortie);
    }
    
    public void setDatesortie(Date datesortie) {
        this.datesortie = datesortie;
    }
    
}
