/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.EspecificidadDeRegion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rincostante
 */
@Stateless
public class EspecificidadDeRegionFacade extends AbstractFacade<EspecificidadDeRegion> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public EspecificidadDeRegionFacade() {
        super(EspecificidadDeRegion.class);
    }
    
    public boolean tieneDependencias(Long id){
        em = getEntityManager();
        String queryString = "SELECT * FROM region WHERE especificidadderegion_id = " + id;
        Query q = em.createNativeQuery(queryString, EspecificidadDeRegion.class);
        return q.getResultList().isEmpty();
    }
    
    public boolean existe(String nombre){
        em = getEntityManager();
        EspecificidadDeRegion esp = em.find(EspecificidadDeRegion.class, nombre);
        return esp.getNombre().isEmpty();
    }
}
