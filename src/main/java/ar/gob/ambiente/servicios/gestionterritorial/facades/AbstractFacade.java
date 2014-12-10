/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Administrador
 */
public abstract class AbstractFacade<T> {
    private final Class<T> entityClass;
    private static EntityManagerFactory emf = null;
    private EntityManager em;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected static EntityManager getEntityManager()
    {
        if(emf == null){
            emf = Persistence.createEntityManagerFactory("ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU");
        }
        return emf.createEntityManager();
    }

    public void create(T obj) {
        em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        
        em.persist(obj);
        
        etx.commit();
        em.clear();
    }

    public void edit(T obj) {
        em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        
        em.merge(obj);
        
        etx.commit();
        em.clear();
    }

    public void remove(T obj) {
        em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        
        em.remove(obj);
        
        etx.commit();
        em.clear();
    }

    public T find(Object id) {
        em = getEntityManager();
        T p = em.find(entityClass, id);
        em.close();
        return p;
    }

    public List<T> findAll() {
        em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        List<T> resultList = em.createQuery(cq).getResultList();
        em.close();
        return resultList;
    }

    public List<T> findRange(int[] range) {
        em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        List<T> resultList = q.getResultList();
        em.close();
        return resultList;
    }

    public int count() {
        em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(em.getCriteriaBuilder().count(rt));
        javax.persistence.Query q = em.createQuery(cq);
        int regs = ((Long) q.getSingleResult()).intValue();
        em.close();
        return regs;
    }
    
}
