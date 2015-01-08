/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.Entidad;
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
public class EntidadFacade extends AbstractFacade<Entidad> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntidadFacade() {
        super(Entidad.class);
    }
    
    /**
     * Método que devuelve todas las Entidades que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<Entidad> getXString(String stringParam){
        em = getEntityManager();
        List<Entidad> result;
        String queryString = "SELECT * FROM entidad WHERE nombre LIKE '%" + stringParam + "%'";
        Query q = em.createNativeQuery(queryString, Entidad.class);
        result = q.getResultList();
        return result;
    }     
}
