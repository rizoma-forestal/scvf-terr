
package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Departamento.
 * @author rincostante
 */
@Stateless
public class DepartamentoFacade extends AbstractFacade<Departamento> {
    
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
    public DepartamentoFacade() {
        super(Departamento.class);
    }

    /**
     * Método que devuelve todas las Departamentos que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam String cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return List<Departamento> El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Departamento> getXString(String stringParam){
        em = getEntityManager();        
        List<Departamento> result; 
        
        String queryString = "SELECT depto FROM Departamento depto "
                + "WHERE depto.nombre LIKE :stringParam "
                + "AND depto.adminentidad.habilitado = true"; 
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + stringParam + "%");  
        
        result = q.getResultList();       
        return result;
    }

    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar String la cadena que buscara para ver si ya existe en la BDD
     * @param prov Provincia Provincia a la que pertenece el Departamento
     * @return boolean devuelve True o False según el caso
     */
    public boolean noExiste(String aBuscar, Provincia prov){
        em = getEntityManager();
        String queryString = "SELECT depto FROM Departamento depto "
                + "WHERE depto.nombre = :stringParam "
                + "AND depto.provincia = :prov "
                + "AND depto.adminentidad.habilitado = true";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar)
                .setParameter("prov", prov);
        return q.getResultList().isEmpty();
    }  
    
    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar String cadena que buscara para ver si ya existe en la BDD
     * @param prov Provincia Provincia a la que pertenece el Departamento
     * @return boolean devuelve True o False según el caso
     */
    public Departamento getExistente(String aBuscar, Provincia prov){
        em = getEntityManager();
        String queryString = "SELECT depto FROM Departamento depto "
                + "WHERE depto.nombre = :stringParam "
                + "AND depto.provincia = :prov "
                + "AND depto.adminentidad.habilitado = true";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar)
                .setParameter("prov", prov);
        return (Departamento)q.getSingleResult();
    }        
    
    /**
     * Método que verifica si la entidad tiene dependencia (Hijos)
     * @param id Long ID de la entidad
     * @return boolean True o False según el caso
     */
    
    public boolean tieneDependencias(Long id){
        em = getEntityManager();        
        
        String queryString = "SELECT pob FROM CentroPoblado pob " 
                + "WHERE pob.departamento.id = :idParam "
                + "AND pob.adminentidad.habilitado = true";        
        
        Query q = em.createQuery(queryString)
                .setParameter("idParam", id);
        
        return q.getResultList().isEmpty();
    }
    
    
    /**
     * Método que obtiene todas los nombres de los Departamentos registrados
     * @return List<String> listado de los nombres de todos los Departamentos registrados
     */
    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT dep.nombre FROM Departamento dep "
                + "AND dep.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    } 


   /**
     * Método que devuelve los Departamentos habilitados
     * @return List<Departamento> listado de los Departamentos requeridos
     */
    public List<Departamento> getActivos(){
        em = getEntityManager();        
        List<Departamento> result;
        String queryString = "SELECT dpto FROM Departamento dpto " 
                + "WHERE dpto.adminentidad.habilitado = true";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }      

    
   /**
     * Método que devuelve un LIST con TODOS los Departamentos de la Provincia
     * @param prov Provincia Provincia a la cual pertenecen los Departamentos solicitados
     * @return List<Departamento> listado de los Departamentos requeridos
     */
    public List<Departamento> getPorProvincia(Provincia prov){
        em = getEntityManager();        
        List<Departamento> result;
        String queryString = "SELECT dpto FROM Departamento dpto " 
                + "WHERE dpto.provincia = :objParam "
                + "AND dpto.adminentidad.habilitado = true "
                + "ORDER BY dpto.nombre";

        Query q = em.createQuery(queryString)
                .setParameter("objParam", prov);        
        
        result = q.getResultList();
        return result;
    }      
    
   /**
     * Método que devuelve un LIST con TODOS los Departamentos de la Provincia según el id de la misma
     * @param idProv Long id de la Provincia
     * @return List<Departamento> listado de los Departamentos requeridos
     */    
    public List<Departamento> getDeptosXIdProv(Long idProv){
        em = getEntityManager();     
        String queryString = "SELECT dpto FROM Departamento dpto "
                + "WHERE dpto.provincia.id = :idProv "
                + "AND dpto.adminentidad.habilitado = true "
                + "ORDER BY dpto.nombre";
        Query q = em.createQuery(queryString)
                .setParameter("idProv", idProv);  
        return q.getResultList();
    }
}
