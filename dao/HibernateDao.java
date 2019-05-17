/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.dao;

import m1.film.modele.BaseModele;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author user
 */
@Repository
public class HibernateDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }
    
    public void save(BaseModele obj,Session sess){
        sess.save(obj);
    }
    
    public void update(BaseModele obj,Session sess){
        sess.update(obj);
    }
    
    public void delete(BaseModele obj,Session sess){
        sess.delete(obj);
    }
    
    public BaseModele findById(BaseModele obj,Session sess){
        return (BaseModele)sess.get(obj.getClass(), obj.getId());
    }
    
    @Transactional
    public void save(BaseModele obj) throws Exception{
        Session session = getSessionFactory().getCurrentSession();
        save(obj,session);
    }
    
    @Transactional(readOnly = true)
    public BaseModele findObject(BaseModele obj){
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(obj.getClass());
        criteria.add(Example.create(obj));
        return (BaseModele) criteria.uniqueResult();
    }
    
    @Transactional(readOnly = true)
    public BaseModele findObject(BaseModele obj,Session sess) {
        Criteria criteria = sess.createCriteria(obj.getClass());
        criteria.add(Example.create(obj));
        return (BaseModele) criteria.uniqueResult();
    }
    
    @Transactional(readOnly = true)
    public BaseModele findById(BaseModele obj){
        BaseModele result;
        Session session  = getSessionFactory().getCurrentSession();
        result = findById(obj,session);
        return result;
    }
    
    @Transactional(readOnly = true)
    public Long count(BaseModele modele){
        Session session  = getSessionFactory().getCurrentSession();
        return (Long) session.createCriteria(modele.getClass()).setProjection(Projections.rowCount()).uniqueResult();
    }
    
    public Integer count(Criteria criteria){
        Long result = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return result.intValue();
    }
    
    @Transactional(readOnly = true)
    public List findAll(BaseModele modele, Integer limit, Integer offset){
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(modele.getClass());
        criteria.add(Example.create(modele));
        criteria.setFirstResult(offset);
        criteria.setMaxResults(limit);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public List findAll(BaseModele obj){
        Session session = getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(obj.getClass());
        criteria.add(Example.create(obj));
        return criteria.list();
    }
    
    @Transactional
    public void update(BaseModele obj) throws Exception {
        Session session  = getSessionFactory().getCurrentSession();
        update(obj,session);
    }

    @Transactional
    public void delete(BaseModele obj) throws Exception {
        Session session = getSessionFactory().getCurrentSession();
        BaseModele modeleSupp = (BaseModele) session.get(obj.getClass(), obj.getId()); 
        delete(modeleSupp,session);
    }
}
