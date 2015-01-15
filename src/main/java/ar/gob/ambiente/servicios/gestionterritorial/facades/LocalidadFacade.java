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
     * @param stringParam: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Localidad> getXString(String aBuscar){
        em = getEntityManager();
        List<Localidad> result;
        
        String queryString = "SELECT reg.nombre FROM region reg "
                + "WHERE reg.nombre LIKE :stringParam ";        
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
    public boolean existe(String nombre){
        em = getEntityManager();
        String queryString = "SELECT nombre FROM localidad WHERE nombre = '" + nombre + "'";
        Query q = em.createNativeQuery(queryString, Localidad.class);
        return q.getResultList().isEmpty();
    }  
    
    
}
