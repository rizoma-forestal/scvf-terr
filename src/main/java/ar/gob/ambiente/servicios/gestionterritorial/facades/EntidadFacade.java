/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.Entidad;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrador
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
    
    public List<Entidad> getEntidadesXString(String stringParam){
        List<Entidad> entidades;
        entidades = new ArrayList<>();
        
        String queryString = "SELECT * FROM entidad WHERE nombre LIKE '%" + stringParam + "%';";
        
        Query q = em.createNativeQuery(queryString, Entidad.class);
        
        entidades = q.getResultList();
        
        return entidades;
    }
}
