
package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Municipio;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad Municipio.
 * @author rincostante
 */
@Stateless
public class MunicipioFacade extends AbstractFacade<Municipio> {
    
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
    public MunicipioFacade() {
        super(Municipio.class);
    }

    /**
     * Método que devuelve todos los Municipios que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param aBuscar String cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return List<Municipio> conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Municipio> getXString(String aBuscar){
        em = getEntityManager();
        List<Municipio> result;
        String queryString = "SELECT mun FROM Municipio mun "
                + "WHERE mun.nombre LIKE :stringParam";    
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + aBuscar + "%"); 
        result = q.getResultList();
        return result;
    }    

    /**
     * Método que obtiene todas los nombres de los Municipios registrados
     * @return List<String> listado de los nombres de todos los Municipios registrados
     */
    public List<String> getNombres(){
        em = getEntityManager();
        String queryString = "SELECT mun.nombre FROM Municipio mun ";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    } 
    
    /**
     * Método que obtiene un Municipio existente según los datos recibidos como parámetro
     * @param nombre String nombre del Municipio
     * @param depto Departamento Departamento vinculado al Municipio
     * @return Municipio Municipio que cumple con las condiciones
     */
    public Municipio getExistente(String nombre, Departamento depto){
        List<Municipio> lCp;
        em = getEntityManager();
        String queryString = "SELECT mu FROM Municipio mu "
                + "WHERE mu.nombre = :stringParam "
                + "AND mu.departamento = :depto";
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
    /**
     * Metodo que verifica si ya existe la entidad.
     * @param nombre String nombre del Municipio
     * @param prov Provincia Provincia a la que pertenece el Municipio
     * @return boolean devuelve True o False
     */
    public boolean noExiste(String nombre, Provincia prov){
        em = getEntityManager();
        String queryString = "SELECT mu FROM Municipio mu "
                + "WHERE mu.nombre = :stringParam "
                + "AND mu.provincia = :prov";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", nombre)
                .setParameter("prov", prov);
        return q.getResultList().isEmpty();
    }  
    
    /**
     * Método que obtiene los Municipios pertenecientes a la Provincia de la que se remite su id
     * @param idProv Long id de la Provincia
     * @return List<Municipio> Municipios resultantes
     */
    public List<Municipio> getMunicipioXIdProv(Long idProv){
        em = getEntityManager();
        String queryString = "SELECT mu FROM Municipio mu "
                + "WHERE mu.provincia.id = :idProv "
                + "AND mu.adminentidad.habilitado = true "
                + "ORDER BY mu.nombre";
        Query q = em.createQuery(queryString)
                .setParameter("idProv", idProv);
        return q.getResultList();
    }
}
