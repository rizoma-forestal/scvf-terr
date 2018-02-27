
package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPobladoTipo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad CetroPobladoTipo.
 * @author rincostante
 */

@Stateless
public class CentroPobladoTipoFacade  extends AbstractFacade<CentroPobladoTipo> {
    
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
    public CentroPobladoTipoFacade() {
        super(CentroPobladoTipo.class);
    }

    /**
     * Método que devuelve todas los Tipos de Centro Poblado que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam String cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return List<CentroPobladoTipo> El conjunto de resultados provenientes de la búsqueda. 
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
     * @param aBuscar String es la cadena que buscara para ver si ya existe en la BDD
     * @return boolean devuelve True o False según el caso
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
     * @param id Long ID de la entidad
     * @return boolean True o False según el caso
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
     * Método que obtiene todas los nombres de los tipos de centros poblados registrados
     * @return List<String> listado de los nombres de todos los tipos de centros poblados
     */
    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT cpt.nombre FROM CentroPobladoTipo cpt "
                + "WHERE cpt.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    } 

    
   /**
     * Método que devuelve los tipos de centros poblados habilitados
     * @return List<CentroPobladoTipo> listado de los tipos de centros poblados requeridos
     */
    public List<CentroPobladoTipo> getActivos(){
        em = getEntityManager();        
        List<CentroPobladoTipo> result;
        String queryString = "SELECT cpt FROM CentroPobladoTipo cpt " 
                + "WHERE cpt.adminentidad.habilitado = true "
                + "ORDER BY cpt.nombre";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }        
    
}
