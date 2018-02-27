
package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.EspecificidadDeRegion;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Region;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Región.
 * @author rincostante
 */
@Stateless
public class RegionFacade extends AbstractFacade<Region> {
    
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
    public RegionFacade() {
        super(Region.class);
    }
 
   /**
     * Método que verifica si la Region puede ser eliminada
     * @param id Long Id de la region que se desea verificar
     * @return boolean True o False según corresponda
     */
    public boolean getUtilizado(Long id){
        em = getEntityManager();
        String queryString = "SELECT prov.id FROM Provincia prov "
                + "INNER JOIN prov.regiones reg "
                + "WHERE reg.id = :id";
        Query q = em.createQuery(queryString)
                .setParameter("id", id);
        return q.getResultList().isEmpty();
    } 
    /**
     * Método para validad que no exista una Región según el nombre y la especificidad
     * @param nombre String nombre de la Región
     * @param espReg EspecificidadDeRegion Especificidad de la Región
     * @return boolean True o False según corresponda
     */
    public boolean noExiste(String nombre, EspecificidadDeRegion espReg){
        em = getEntityManager();
        String queryString = "SELECT reg FROM Region reg "
                + "WHERE reg.nombre = :nombre "
                + "AND reg.especificidadderegion = :espReg";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre)
                .setParameter("espReg", espReg);
        return q.getResultList().isEmpty();
    }    
    /**
     * Método que obtiene una Region existente según los datos recibidos como parámetro
     * @param nombre String nombre de la Región
     * @param espReg EspecificidadDeRegion Especificidad de la Región
     * @return Region Región que cumple con los datos requeridos
     */
    public Region getExistente(String nombre, EspecificidadDeRegion espReg){
        List<Region> lProv;
        String queryString = "SELECT reg FROM Region reg "
                + "WHERE reg.nombre = :nombre "
                + "AND reg.especificidadderegion = :espReg";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre)
                .setParameter("espReg", espReg);
        lProv = q.getResultList();
        if(!lProv.isEmpty()){
            return lProv.get(0);
        }else{
            return null;
        }
    }     
    
    /**
     * Método que devuelve todas las Regiones habilitadas y vigentes
     * @return List<Region> listado de las Regiones correspondientes
     */
    public List<Region> getHabilitadas(){
        em = getEntityManager();
        String queryString = "SELECT reg FROM Region reg "
                + "WHERE reg.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }

    /**
     * Método que devuelve todas las Regiones deshabilitadas
     * @return List<Region> listado de las Regiones correspondientes
     */
    public List<Region> getDeshabilitadas(){
        em = getEntityManager();
        String queryString = "SELECT reg FROM Region reg "
                + "WHERE reg.adminentidad.habilitado = false";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }  
 
    /**
     * Método que devuelve las Regiones vinculadas a una Provincia según su id
     * @param idProv Long id de la Provincia
     * @return List<Region> listado de las Regiones correspondientes
     */
    public List<Region> getRegionesXidProv(Long idProv){
        em = getEntityManager();
        String queryString = "SELECT reg FROM Region reg "
                + "INNER JOIN reg.provincias prov "
                + "WHERE prov.id = :idProv "
                + "AND reg.adminentidad.habilitado = true "
                + "ORDER BY reg.nombre";
        Query q = em.createQuery(queryString)
                .setParameter("idProv", idProv);
        return q.getResultList();
    }
    
    /**
     * Método que devuelve las Regiones de a una Especificidad según su id
     * @param idEspecif Long id de la Especificidad de la Región
     * @return List<Region> listado de las Regiones correspondientes
     */
    public List<Region> getRegionesXidEspecif(Long idEspecif){
        em = getEntityManager();
        String queryString = "SELECT reg FROM Region reg "
                + "WHERE reg.especificidadderegion.id = :idEspecif "
                + "AND reg.adminentidad.habilitado = true "
                + "ORDER BY reg.nombre";
        Query q = em.createQuery(queryString)
                .setParameter("idEspecif", idEspecif);
        return q.getResultList();
    }
}      
     
