/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPobladoTipo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author epassarelli
 */

@Stateless
public class CentroPobladoTipoFacade  extends AbstractFacade<CentroPobladoTipo> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CentroPobladoTipoFacade() {
        super(CentroPobladoTipo.class);
    }

    /**
     * Método que devuelve todas las CentroPobladoes que contienen la cadena recibida como parámetro 
 dentro de alguno de sus campos string, en este caso el nombre.
     * @param aBuscar: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<CentroPobladoTipo> getXString(String aBuscar){
        em = getEntityManager();
        List<CentroPobladoTipo> result;
        
        String queryString = "SELECT cpt.nombre FROM CentroPobladoTipo cpt "
                + "WHERE cpt.nombre LIKE :stringParam ";        
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", "%" + aBuscar + "%");        
        result = q.getResultList();
        
        return result;
    }

    /**
     * Metodo que verifica si ya existe la entidad.
     * @param aBuscar: es la cadena que buscara para ver si ya existe en la BDD
     * @return: devuelve True o False
     */
    public boolean existe(String aBuscar){
        em = getEntityManager();
        
        String queryString = "SELECT cpt FROM CentroPobladoTipo cpt "
                + "WHERE cpt.nombre = :stringParam";
        
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
        String queryString = "SELECT cpt.nombre FROM CentroPobladoTipo cpt ";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    } 

 
    
}