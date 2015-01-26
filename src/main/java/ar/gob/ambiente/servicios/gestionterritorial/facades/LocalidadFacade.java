/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;


import ar.gob.ambiente.servicios.gestionterritorial.entidades.Localidad;
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
public class LocalidadFacade extends AbstractFacade<Localidad> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocalidadFacade() {
        super(Localidad.class);
    }

    /**
     * Método que devuelve todas las Localidades que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param aBuscar: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Localidad> getXString(String aBuscar){
        em = getEntityManager();
        List<Localidad> result;
        
        String queryString = "SELECT loc.nombre FROM Localidad loc "
                + "WHERE loc.nombre LIKE :stringParam ";        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + aBuscar + "%");        
        result = q.getResultList();
        
        return result;
    }

    /**
     * Metodo que verifica si ya existe la entidad.
     * @param nombre: es la cadena que buscara para ver si ya existe en la BDD
     * @return: devuelve True o False
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();
        
        String queryString = "SELECT loc FROM Localidad loc "
                + "WHERE loc.nombre = :stringParam";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        
        return q.getResultList().isEmpty();
    }  
    
    /**
     * Metodo para el autocompletado de la búsqueda por nombre
     * @return 
     */
    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT loc.nombre FROM Localidad loc ";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    } 

    /**
     * Método que verifica si la entidad tiene dependencia (Hijos)
     * @param id: ID de la entidad
     * @return: True o False
     */
    public boolean tieneDependencias(Long id){
        em = getEntityManager();
        String queryString = "SELECT geo FROM GeoRef geo " 
                + "WHERE geo.localidad.id = :idParam";               
        Query q = em.createQuery(queryString)
                .setParameter("idParam", id); 
        return q.getResultList().isEmpty();
    }     
    
}
