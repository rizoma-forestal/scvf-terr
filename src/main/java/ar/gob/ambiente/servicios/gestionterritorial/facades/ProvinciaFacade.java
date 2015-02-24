/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rincostante
 */
@Stateless
public class ProvinciaFacade extends AbstractFacade<Provincia> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProvinciaFacade() {
        super(Provincia.class);
    }
    
    /**
     * Método que devuelve todas las Provincias que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param aBuscar: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Provincia> getXString(String aBuscar){
        em = getEntityManager();
        List<Provincia> result;
        
        String queryString = "SELECT pro FROM Provincia pro "
                + "WHERE pro.nombre LIKE :stringParam";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + aBuscar + "%");    
        
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

        String queryString = "SELECT pro FROM Provincia pro "
                + "WHERE pro.nombre = :stringParam";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        
        return q.getResultList().isEmpty();
    }    
    
    /**
     * Método que verifica si la entidad tiene dependencia (Hijos)
     * @param id: ID de la entidad
     * @return: True o False
     */
    public boolean tieneDependencias(Long id){
        em = getEntityManager();
        String queryString = "SELECT dep FROM Departamento dep " 
                + "WHERE dep.provincia.id = :idParam "
                + "AND dep.adminentidad.habilitado = true";           
        
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
        String queryString = "SELECT pro.nombre FROM Provincia pro";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }    

   /**
     * Método que devuelve un LIST con las entidades HABILITADAS
     * @return: True o False
     */
    public List<Provincia> getActivos(){
        em = getEntityManager();        
        List<Provincia> result;
        String queryString = "SELECT pro FROM Provincia pro " 
                + "WHERE pro.adminentidad.habilitado = true";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }    
    
}