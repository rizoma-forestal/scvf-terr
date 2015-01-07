/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.GeoRef;
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
public class GeoRefFacade extends AbstractFacade<GeoRef> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public GeoRefFacade() {
        super(GeoRef.class);
    }
    
    /**
     * Método que devuelve todos los Organismos que contienen la cadena recibida como parámetro 
     * dentro de alguno de sus campos string, en este caso el nombre.
     * @param stringParam: cadena que buscará en todos los campos de tipo varchar de la tabla correspondiente
     * @return: El conjunto de resultados provenientes de la búsqueda. 
     */      
    public List<GeoRef> getXString(String stringParam){
        em = getEntityManager();
        List<GeoRef> result;
        String queryString = "SELECT * FROM georef WHERE punto LIKE '%" + stringParam + "%'";
        Query q = em.createNativeQuery(queryString, GeoRef.class);
        result = q.getResultList();
        em.close();
        return result;
    }     
}
