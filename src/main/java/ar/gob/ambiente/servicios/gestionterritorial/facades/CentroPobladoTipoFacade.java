/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPobladoTipo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author epassarelli
 */

@Stateless
public class CentroPobladoTipoFacade  extends AbstractFacade<CentroPobladoTipo> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CentroPobladoTipoFacade() {
        super(CentroPobladoTipo.class);
    }

    /**
     * Método que devuelve todas las CentroPobladoes que contienen la cadena recibida como parámetro 
 dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<CentroPobladoTipo> getXString(String stringParam){
        em = getEntityManager();
        List<CentroPobladoTipo> result;
        
        String queryString = "SELECT cpt FROM CentroPobladoTipo cpt "
                + "WHERE cpt.nombre LIKE :stringParam ";    
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + stringParam + "%");    
        
        result = q.getResultList();
        
        return result;
    }

    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar: es la cadena que buscara para ver si ya existe en la BDD
     * @return: devuelve True o False
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();
        
        String queryString = "SELECT cpt.nombre FROM CentroPobladoTipo cpt "
                + "WHERE cpt.nombre = :stringParam";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        
        return q.getResultList().isEmpty();
    }  
   /**
     * Método que verifica si la entidad tiene dependencia (Hijos) en estado HABILITADO
     * @param id: ID de la entidad
     * @return: True o False
     */
    public boolean noTieneDependencias(Long id){
        em = getEntityManager();        
        String queryString = "SELECT cp FROM CentroPoblado cp " 
                + "WHERE cp.centroPobladoTipo.id = :idParam "
                + "AND cp.adminentidad.habilitado = true";        
        Query q = em.createQuery(queryString)
                .setParameter("idParam", id);
        return q.getResultList().isEmpty();
    } 

     
    /**
     * Metodo para el autocompletado de la búsqueda por nombre
     * @return 
     */
    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT cpt.nombre FROM CentroPobladoTipo cpt "
                + "WHERE cpt.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    } 

    
   /**
     * Método que devuelve un LIST con las entidades HABILITADAS
     * @return: True o False
     */
    public List<CentroPobladoTipo> getActivos(){
        em = getEntityManager();        
        List<CentroPobladoTipo> result;
        String queryString = "SELECT cpt FROM CentroPobladoTipo cpt " 
                + "WHERE cpt.adminentidad.habilitado = true";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }        
    
}
