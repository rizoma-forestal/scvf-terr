/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.AdminEntidad;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rincostante
 */
@Stateless
public class AdminEntidadFacade extends AbstractFacade<AdminEntidad> {
    @PersistenceContext(unitName = "gestionTerritorial-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminEntidadFacade() {
        super(AdminEntidad.class);
    }
    
}
