/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.metier;

import java.util.List;
import m1.film.dao.HibernateDao;
import m1.film.modele.BaseModele;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */

@Service
public class BaseMetier {

    @Autowired
    private HibernateDao dao;
    
    public void setDao(HibernateDao dao) {
        this.dao = dao;
    }
    
    public HibernateDao getDao() {
        return dao;
    }
    
    public void save(BaseModele obj) throws Exception {
         this.getDao().save(obj);
    }
    
    public void update(BaseModele obj) throws Exception {
        this.getDao().update(obj);
    }
    
    public void delete(BaseModele obj) throws Exception {
        this.getDao().delete(obj);
    }
    
    public BaseModele findById(BaseModele obj )  throws Exception {
        return this.getDao().findById(obj);
    }
    
    public List findAll(BaseModele obj) throws Exception {
        return this.getDao().findAll(obj);
    }
    
    public List findAll(BaseModele obj, Integer limit, Integer offset) throws Exception{
        return this.getDao().findAll(obj,limit,offset);
    }
    
    public BaseModele findObject(BaseModele obj){
        return this.getDao().findObject(obj);
    }
    
    public BaseModele findObject(BaseModele obj,Session sess){
        return this.getDao().findObject(obj, sess);
    }
    
    public Integer count(BaseModele modele){
        return this.getDao().count(modele).intValue();
    }
    
    public Integer count(Criteria criteria){
        return this.getDao().count(criteria);
    }
    
    public int nbPage(Integer count,Integer size){
        return (int) Math.ceil(count.doubleValue()/size.doubleValue());
    }
}
