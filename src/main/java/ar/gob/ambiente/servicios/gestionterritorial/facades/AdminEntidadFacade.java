
package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.AdminEntidad;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase que implementa la abstracta para el acceso a datos de la entidad administrativa.
 * @author rincostante
 */
@Stateless
public class AdminEntidadFacade extends AbstractFacade<AdminEntidad> {
    @PersistenceContext(unitName = "gestionTerritorial-PU")
    
    /**
     * Variable privada: EntityManager al que se le indica la unidad de persistencia mediante la cual accederá a la base de datos
     */    
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
    public AdminEntidadFacade() {
        super(AdminEntidad.class);
    }
    
}
