
package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Provincia.
 * @author rincostante
 */
@Stateless
public class ProvinciaFacade extends AbstractFacade<Provincia> {
    
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
    public ProvinciaFacade() {
        super(Provincia.class);
    }
    
    /**
     * Método que devuelve todas las Provincias que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam String cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return List<Provincia> conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Provincia> getXString(String stringParam){
        em = getEntityManager();
        List<Provincia> result;
        
        String queryString = "SELECT pro FROM Provincia pro "
                + "WHERE pro.nombre LIKE :stringParam "
                + "AND pro.adminentidad.habilitado =true";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + stringParam + "%");    
        
        result = q.getResultList();
        return result;
    }
    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar String cadena que buscara para ver si ya existe en la BDD
     * @return boolean devuelve True o False según corresponda
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();
        String queryString = "SELECT pro.nombre FROM Provincia pro "
                + "WHERE pro.nombre = :stringParam "
                + "AND pro.adminentidad.habilitado = true";
        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", aBuscar);
        return q.getResultList().isEmpty();
    }
    
    /**
     * Método que obtiene una Provincia según su nombre.
     * Implementado para la API REST solicitado por el registro remoto de guías del CGL
     * @param nombre Nombre de la Provincia a buscar
     * @return Provincia Provincia solicitada
     */
    public Provincia getExistente(String nombre){
        em = getEntityManager();
        String queryString = "SELECT prov FROM Provincia prov "
                + "WHERE prov.nombre = :nombre "
                + "AND prov.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);
        return (Provincia)q.getSingleResult();
    }
    /**
     * Método que verifica si la entidad tiene dependencia (Hijos)
     * @param id Long ID de la entidad
     * @return boolean True o False según corresponda
     */
    public boolean tieneDependencias(Long id){
        em = getEntityManager();        
        
        String queryString = "SELECT mun FROM Municipio mun " 
                + "WHERE mun.provincia.id = :idParam ";        
        
        Query q = em.createQuery(queryString)
                .setParameter("idParam", id);
        
        return q.getResultList().isEmpty();
 
    }

    /**
     * Método que obtiene todas los nombres de las Provincias registradas
     * @return List<String> listado de los nombres de todos las Provincias registradas
     */
    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT pro.nombre FROM Provincia pro "
                + "AND pro.adminentidad.habilitado = true";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }    
    
    /**
     * Método que devuelve las Provincias habilitadas
     * @return List<Provincia> listado de las Provincias habilitadas
     */
    public List<Provincia> getActivos(){
        em = getEntityManager();        
        List<Provincia> result;
        String queryString = "SELECT pro FROM Provincia pro " 
                + "WHERE pro.adminentidad.habilitado = true "
                + "ORDER BY pro.nombre";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }    

    /**
     * Método que devuelve las Provincias vinculadas a una Región de la cual se remite su id
     * @param idRegion Long id de la Región
     * @return List<Provincia> conjunto de las Provincias vinculadas a la Región
     */
    public List<Provincia> getProvXIdRegion(Long idRegion) {
        em = getEntityManager(); 
        String queryString = "SELECT pro FROM Provincia pro "
                + "INNER JOIN pro.regiones reg "
                + "WHERE reg.id = :idRegion "
                + "AND pro.adminentidad.habilitado = true "
                + "ORDER BY pro.nombre"; 
        Query q = em.createQuery(queryString)
                .setParameter("idRegion", idRegion);
        return q.getResultList();
    }
    
}