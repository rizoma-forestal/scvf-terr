/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;


import ar.gob.ambiente.servicios.gestionterritorial.entidades.Region;
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
public class RegionFacade extends AbstractFacade<Region> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU")
    private EntityManager em;


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegionFacade() {
        super(Region.class);
    }
 
   /**
     * Método que verifica si la Region puede ser eliminada
     * @param id: Id de la region que se desea verificar
     * @return
     */
    public boolean getUtilizado(Long id){
        em = getEntityManager();
        String queryString = "SELECT pro.id FROM Provincia pro "
                + "INNER JOIN pro.regiones reg "
                + "WHERE reg.id = :id";
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
        return q.getResultList().isEmpty();
    } 
       
    /**
     * Método que devuelve todas las Regiones que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Region> getXString(String stringParam){
        em = getEntityManager();
        List<Region> result;
        
        String queryString = "SELECT reg FROM Region reg "
                + "WHERE reg.nombre LIKE :stringParam "
                + "AND reg.adminentidad.habilitado =true";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + stringParam + "%");  
        
        result = q.getResultList();
        return result;
    }
  /**
     * Método que obtiene una Region existente según los datos recibidos como parámetro
     * @param nombre
     * @return 
     */
    public Region getExistente(String nombre){
        List<Region> lPcia;
        em = getEntityManager();
        String queryString = "SELECT reg FROM Region reg "
                + "WHERE reg.nombre = :nombre";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);
        lPcia = q.getResultList();
        if(!lPcia.isEmpty()){
            return lPcia.get(0);
        }else{
            return null;
        }
    }     
    
    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar: es la cadena que buscara para ver si ya existe en la BDD
     * @return: devuelve True o False
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();
        String queryString = "SELECT reg.nombre FROM Region reg "
                + "WHERE reg.nombre = :stringParam "
                +  "AND reg.adminentidad.habilitado = true";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        return q.getResultList().isEmpty();
    }    
    /**
     * Método para validad que no exista una Actividad Planificada con este nombre ya ingresado
     * @param nombre
     * @return 
     */
    public boolean noExiste(String nombre){
        em = getEntityManager();
        String queryString = "SELECT reg FROM Region reg "
                + "WHERE reg.nombre = :nombre";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);
        return q.getResultList().isEmpty();
    }    
    
    
    /**
     * Método que verifica si la entidad tiene dependencia (Hijos)
     * @param id: ID de la entidad
     * @return: True o False
     */
    public boolean tieneDependencias(Long id){
        em = getEntityManager();        
        
        String queryString = "SELECT pro FROM Provincia pro " 
                + "WHERE pro.region.id = :idParam ";               
        
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
        String queryString = "SELECT reg.nombre FROM Region reg "
                + "AND reg.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }    
/**
     * Método que devuelve un LIST con las entidades HABILITADAS
     * @return: True o False
     */
    public List<Region> getActivos(){
        em = getEntityManager();        
        List<Region> result;
        String queryString = "SELECT reg FROM Region reg " 
                + "WHERE reg.adminentidad.habilitado = true";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }        
     
    /**
     * Método que devuelve todas los Actividades Planificadas habilitadas y vigentes
     * @return 
     */
    public List<Region> getHabilitadas(){
        em = getEntityManager();
        String queryString = "SELECT reg FROM Region reg "
                + "WHERE reg.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }
    

    /**
     * Método que devuelve todas los Actividades Planificadas deshabilitadas
     * @return 
     */
    public List<Region> getDeshabilitadas(){
        em = getEntityManager();
        String queryString = "SELECT reg FROM Region reg "
                + "WHERE reg.adminentidad.habilitado = false";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }  
}