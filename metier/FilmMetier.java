/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.metier;

import java.util.ArrayList;
import java.util.List;
import m1.film.modele.Film;
import m1.film.util.ResultatPage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author user
 */
@Service
public class FilmMetier extends BaseMetier {
    @Transactional(readOnly = true)
    public List<Film> parCategorie(Integer idCategorie) throws Exception{
        Film film =  new Film();
        List<Film>  films = this.findAll(film);
        film.setIdcategorie(idCategorie);
        return films;
    }
    
    @Transactional(readOnly = true)
    public ResultatPage rechercheGlobal(String recherche,Integer page,Integer size){
        if(recherche==null){
            return new ResultatPage(new ArrayList<Film>(),0);
        }
        Session session = getDao().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Film.class);
        Criterion titre  = Restrictions.like("titre","%" + recherche +"%").ignoreCase(); 
        Criterion categorie  = Restrictions.like("categorie","%" + recherche +"%").ignoreCase();
        Criterion annee  = Restrictions.like("annee","%" + recherche +"%");
        Criterion realisateur  = Restrictions.like("realisateur","%" + recherche +"%").ignoreCase();
        criteria.add(Restrictions.or(titre,categorie,annee,realisateur));
        criteria.setFirstResult((page-1) * size);
        criteria.setMaxResults(size);
        List<Film> list  = criteria.list();
        System.out.println("Size");
        System.out.println(list.size());
        
        criteria.setFirstResult(0);
        criteria.setMaxResults(0);
        Long resultCount = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
        return new ResultatPage(list,nbPage(resultCount.intValue(), size));
    }
    
    @Transactional(readOnly = true)
    public ResultatPage rechercheMuticritere(String titre,String annee,Integer categorie,String realisateur,Integer page,Integer size){
        if(titre==null && annee ==null && categorie ==null && realisateur==null){
            return new ResultatPage(new ArrayList<Film>(),0);
        }
        Session session = getDao().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Film.class);
        if(titre!=null ){
            Criterion criterion  = Restrictions.like("titre","%" + titre +"%").ignoreCase();
            criteria.add(criterion);
        }
        if(categorie!=null){
            Criterion criterion  = Restrictions.eq("idcategorie",categorie);
            criteria.add(criterion);
        }
        if(annee!=null){
            Criterion criterion  = Restrictions.like("annee","%" + annee +"%");
            criteria.add(criterion);
        }
        if(realisateur!=null){
            Criterion criterion  = Restrictions.like("realisateur","%" + realisateur +"%").ignoreCase();
            criteria.add(criterion);
        }
        criteria.setFirstResult((page-1) * size);
        criteria.setMaxResults(size);
        List<Film> list  = criteria.list();
        System.out.println("Size");
        System.out.println(list.size());
        
        criteria.setFirstResult(0);
        criteria.setMaxResults(0);
        Long resultCount = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
        return new ResultatPage(list,nbPage(resultCount.intValue(), size));
    }
    
   public List<Film> topFilm() throws Exception {
        Film film =  new Film();
        List<Film>  films = this.getDao().findAll(film);
        return films;
    }

    @Transactional(readOnly = true)
    public List<Film> rechercheGlobal(String recherche) {
        Session session = getDao().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Film.class);
        Criterion titre  = Restrictions.like("titre","%" + recherche +"%").ignoreCase(); 
        Criterion categorie  = Restrictions.like("categorie","%" + recherche +"%").ignoreCase();
        Criterion annee  = Restrictions.like("annee","%" + recherche +"%");
        Criterion realisateur  = Restrictions.like("realisateur","%" + recherche +"%").ignoreCase();
        criteria.add(Restrictions.or(titre,categorie,annee,realisateur));
        List<Film> list  = criteria.list();
        return list;
    }
    
    @Transactional(readOnly = true)
    public List<Film> listFilm()
    {
        Session session = getDao().getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Film.class);
        List<Film> list = criteria.list();
        return list;
    }
}
