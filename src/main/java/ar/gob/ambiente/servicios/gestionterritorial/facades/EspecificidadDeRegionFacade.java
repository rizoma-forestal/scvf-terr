
package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.EspecificidadDeRegion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Especificidad de Región.
 * @author rincostante
 */
@Stateless
public class EspecificidadDeRegionFacade extends AbstractFacade<EspecificidadDeRegion> {
    
    /**
     * Variable privada: EntityManager al que se le indica la unidad de persistencia mediante la cual accederá a la base de datos
     */
    @PersistenceContext(unitName = "gestionTerritorial-PU")
    private EntityManager em;
    
    /**
     * Método que implementa el abstracto para la obtención del EntityManager
     * @return EntityManager para acceder a datos
     */  
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }    

    /**
     * Constructor
     */
    public EspecificidadDeRegionFacade() {
        super(EspecificidadDeRegion.class);
    }
    
    /**
     * Método que devuelve todas las Especificidades de Región que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param aBuscar String cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return List<EspecificidadDeRegion> conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<EspecificidadDeRegion> getXString(String aBuscar){
        em = getEntityManager();
        List<EspecificidadDeRegion> result;
        String queryString = "SELECT edr FROM EspecificidadDeRegion edr "
                + "WHERE edr.nombre LIKE :stringParam";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + aBuscar + "%");
        result = q.getResultList();
        return result;
    }
    
    
    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar String cadena que buscara para ver si ya existe en la BDD
     * @return boolean devuelve True o False según el caso
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();       
        String queryString = "SELECT edr.nombre FROM EspecificidadDeRegion edr "
                + "WHERE edr.nombre = :stringParam";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        return q.getResultList().isEmpty();
    }    
    
    /**
     * Método que verifica si la entidad tiene dependencia (Hijos) en estado HABILITADO
     * @param id Long ID de la entidad
     * @return boolean True o False según el caso
     */
    public boolean tieneDependencias(Long id){
        em = getEntityManager();        
        String queryString = "SELECT reg FROM Region reg " 
                + "WHERE reg.especificidadderegion.id = :idParam "
                + "AND reg.adminentidad.habilitado = true";        
        Query q = em.createQuery(queryString)
                .setParameter("idParam", id);
        return q.getResultList().isEmpty();
    }

    /**
     * Método que obtiene todas los nombres de las Especificidades de Región registradas
     * @return List<String> listado de los nombres de todas las Especificidades de Región registradas
     */
    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT edr.nombre FROM EspecificidadDeRegion edr";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }


   /**
     * Método que devuelve las Especificidades de Región habilitados
     * @return List<EspecificidadDeRegion> listado de las Especificidades de Región requeridas
     */
    public List<EspecificidadDeRegion> getActivos(){
        em = getEntityManager();        
        List<EspecificidadDeRegion> result;
        String queryString = "SELECT edr FROM EspecificidadDeRegion edr " 
                + "WHERE edr.adminentidad.habilitado = true "
                + "ORDER BY edr.nombre";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }
}        