
package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPoblado;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad CetroPoblado (Localidad).
 * @author rincostante
 */

@Stateless
public class CentroPobladoFacade extends AbstractFacade<CentroPoblado> {
    
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
    public CentroPobladoFacade() {
        super(CentroPoblado.class);
    }
    
    /**
     * Método que devuelve todas las Localidades que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam String cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return List<CentroPoblado> El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<CentroPoblado> getXString(String stringParam){
        em = getEntityManager();
        List<CentroPoblado> result;
        
        String queryString = "SELECT cp.* FROM CentroPoblado cp "
                + "WHERE cp.nombre LIKE :stringParam "
                + "AND cp.adminentidad.habilitado =true";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + stringParam + "%");        
        
        result = q.getResultList();
        return result;
    }
     
    /**
     * Método que obtiene todas los nombres de las localidades registradas
     * @return List<String> listado de los nombres de todas las localidades
     */
    public List<String> getNombre(){
        em = getEntityManager();
        String queryString = "SELECT cp.nombre FROM CentroPoblado cp";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }

    /**
     * Metodo que verifica si ya existe la entidad.
     * @param nombre String nombre de la Localidad
     * @param depto Departamento Departamento al cual pertenece la Localidad
     * @return boolean devuelve True o False sgún la Localidad existe o no
     */
    public boolean noExiste(String nombre, Departamento depto){
        em = getEntityManager();
        String queryString = "SELECT cp FROM CentroPoblado cp "
                + "WHERE cp.nombre = :stringParam "
                + "AND cp.departamento = :depto";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", nombre)
                .setParameter("depto", depto);
        return q.getResultList().isEmpty();
    }  

    /**
     * Método que obtiene una Localidad existente según los datos recibidos como parámetro
     * @param nombre String nombre de la Localidad
     * @param depto Departamento Departamento al cual pertenece la Localidad
     * @return CentroPoblado Localidad existente
     */
    public CentroPoblado getExistente(String nombre, Departamento depto){
        List<CentroPoblado> lCp;
        em = getEntityManager();
        String queryString = "SELECT cp FROM CentroPoblado cp "
                + "WHERE cp.nombre = :stringParam "
                + "AND cp.departamento = :depto";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", nombre)
                .setParameter("depto", depto);
        lCp = q.getResultList();
        if(!lCp.isEmpty()){
            return lCp.get(0);
        }else{
            return null;
        }
    }    
    
    public CentroPoblado getByNomCentroYNomDepto(String nomCentro, String nomDepto){
        List<CentroPoblado> lCp;
        em = getEntityManager();
        String queryString = "SELECT cp FROM CentroPoblado cp "
                + "WHERE cp.nombre = :nomCentro "
                + "AND cp.departamento.nombre = :nomDepto";
        Query q = em.createQuery(queryString)
                .setParameter("nomCentro", nomCentro)
                .setParameter("nomDepto", nomDepto);
        lCp = q.getResultList();
        if(!lCp.isEmpty()){
            return lCp.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * Método que obtiene todas las Localidades pertenecientes al Departamento del cual se recibe su id
     * @param idDepto Long identificador del Departamento
     * @return List<CentroPoblado> listado de las Localidades pertenecientes al Departamento en cuestión
     */
    public List<CentroPoblado> getCentrosXDepto(Long idDepto){
        em = getEntityManager();
        String queryString = "SELECT cp FROM CentroPoblado cp "
                + "WHERE cp.departamento.id = :idDepto "
                + "AND cp.adminentidad.habilitado = true "
                + "ORDER BY cp.nombre";
        Query q = em.createQuery(queryString)
                .setParameter("idDepto", idDepto);
        return q.getResultList();         
    }    
    
    /**
     * Método que devuelve todas las Localidades de un determinado tipo pertenecientes a un Departamento
     * @param idDepto Long identificador del Departamento
     * @param idTipo Long identificador del Tipo de Centrpo poblado
     * @return List<CentroPoblado> listado de las Localidades que cumplen con lo requerido
     */
    public List<CentroPoblado> getCentrosXDeptoTipo(Long idDepto, Long idTipo){
        em = getEntityManager();
        String queryString = "SELECT cp FROM CentroPoblado cp "
                + "WHERE cp.departamento.id = :idDepto "
                + "AND cp.centroPobladoTipo.id = :idTipo "
                + "AND cp.adminentidad.habilitado = true "
                + "ORDER BY cp.nombre";
        Query q = em.createQuery(queryString)
                .setParameter("idDepto", idDepto)
                .setParameter("idTipo", idTipo);
        return q.getResultList();         
    }
    
    /**
     * Método que devuelve todas las Localidades de un determinado tipo pertenecientes a una Provincia
     * @param idProv Long identificador de la Provincia
     * @param idTipo Long identificador del tipo de Centro Poblado
     * @return List<CentroPoblado> listado de las Localidades que cumplen con lo requerido
     */
    public List<CentroPoblado> getCentrosXProvTipo(Long idProv, Long idTipo){
        em = getEntityManager();
        String queryString = "SELECT cp FROM CentroPoblado cp "
                + "WHERE cp.departamento.provincia.id = :idProv "
                + "AND cp.centroPobladoTipo.id = :idTipo "
                + "AND cp.adminentidad.habilitado = true "
                + "ORDER BY cp.nombre";
        Query q = em.createQuery(queryString)
                .setParameter("idProv", idProv)
                .setParameter("idTipo", idTipo);
        return q.getResultList();     
    }
    
    /**
     * Método que devuelve todas las Localidades de un determinado tipo pertenecientes a una Región
     * @param idRegion Long identificador de la Región
     * @param idTipo Long identificador del Tipo de Centro Poblado
     * @return List<CentroPoblado> listado de las Localidades que cumplen con lo requerido
     */
    public List<CentroPoblado> getCentrosXRegionTipo(Long idRegion, Long idTipo){
        em = getEntityManager();
        String queryString = "SELECT cp FROM CentroPoblado cp "
                + "INNER JOIN cp.departamento depto "
                + "INNER JOIN depto.provincia prov "
                + "INNER JOIN prov.regiones reg "
                + "WHERE reg.id = :idRegion "
                + "AND cp.centroPobladoTipo.id = :idTipo "
                + "AND cp.adminentidad.habilitado = true "
                + "ORDER BY cp.nombre";    
        Query q = em.createQuery(queryString)
                .setParameter("idRegion", idRegion)
                .setParameter("idTipo", idTipo);
        return q.getResultList(); 
    }
}
