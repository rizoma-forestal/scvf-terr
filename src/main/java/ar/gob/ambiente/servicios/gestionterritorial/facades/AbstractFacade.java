/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrador
 * @param <T>
 */
public abstract class AbstractFacade<T> {
    private final Class<T> entityClass;
    private static EntityManagerFactory emf = null;
    private EntityManager em;

    /**
     *
     * @param entityClass
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     *
     * @return
     */
    protected static EntityManager getEntityManager()
    {
        if(emf == null){
            emf = Persistence.createEntityManagerFactory("ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU");
        }
        return emf.createEntityManager();
    }

    /**
     *
     * @param obj
     */
    public void create(T obj) {
        em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        
        em.persist(obj);
        
        etx.commit();
        em.clear();
    }

    /**
     *
     * @param obj
     */
    public void edit(T obj) {
        em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        
        em.merge(obj);
        
        etx.commit();
        em.clear();
    }

    /**
     *
     * @param obj
     */
    public void remove(T obj) {
        em = getEntityManager();
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        
        em.remove(obj);
        
        etx.commit();
        em.clear();
    }

    /**
     *
     * @param id
     * @return
     */
    public T find(Object id) {
        em = getEntityManager();
        T p = em.find(entityClass, id);
        em.close();
        return p;
    }

    /**
     *
     * @return
     */
    public List<T> findAll() {
        em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        List<T> resultList = em.createQuery(cq).getResultList();
        em.close();
        return resultList;
    }

    /**
     *
     * @param range
     * @return
     */
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

    /**
     *
     * @return
     */
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

    /**
     * Método que obtiene las entidades según el valor de un campo dado.
     * @param table: Cadena que contiene el nombre de la tabla en la que realizar la búsqueda
     * @param campo: Cadena que indica el nombre del campo cuyo valor se desa buscar. El el nombre del campo en la base de datos
     * @param valor: Valor que se buscará.
     * @param tipo: Tipo de dato del campo (String/int)
     * @return
     */
    public List<T> findFieldValue(String table, String campo, Object valor, String tipo) {
        em = getEntityManager();
        List<T> result;
        String queryString = "";
        switch (tipo) {
            case "String":
                queryString = "SELECT * FROM " + table + " WHERE " + campo + " = '" + valor + "'";
                break;
                
            case "int":
                queryString = "SELECT * FROM " + table + " WHERE " + campo + " = " + valor;
                break;                
        }
        javax.persistence.Query q = em.createNativeQuery(queryString, entityClass);
        result = q.getResultList();
        em.close();
        return result;
    }
    
    /**
     * Método que obtiene las entidades según el valor de un campo dado de tipo TemporalType.
     * @param campo: Cadena que indica el nombre del campo cuyo valor se desa buscar.
     * @param date: Valor de la fecha a buscar.
     * @param temporalType: Especificación del tipo de fecha que se busca.
     * @return
     */
    public List<T> findXFecha(String campo, Date date, TemporalType temporalType) {
        em = getEntityManager();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setParameter(campo, date, temporalType);
        List<T> resultList = q.getResultList();
        em.close();
        return resultList;
    }     
}
