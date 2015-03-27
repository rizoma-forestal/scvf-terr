/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPoblado;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
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
public class CentroPobladoFacade extends AbstractFacade<CentroPoblado> {
    @PersistenceContext(unitName = "ar.gob.ambiente.servicios_gestionTerritorial_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CentroPobladoFacade() {
        super(CentroPoblado.class);
    }


    /**
     * Metodo que verifica si ya existe la entidad.
     * @param nombre
     * @param depto
     * @return: devuelve True o False
     */
    public boolean noExiste(String nombre, Departamento depto){
        em = getEntityManager();
        String queryString = "SELECT cp FROM CentroPoblado cp "
                + "WHERE cp.nombre = :stringParam "
                + "AND cp.departamento = :depto";
        Query q = em.createQuery(queryString)
                .setParameter("stringParam", nombre)
                .setParameter("depto", depto);
        return q.getResultList().isEmpty();
    }  

    /**
     * Método que obtiene un Centro Poblado existente según los datos recibidos como parámetro
     * @param nombre
     * @param depto
     * @return 
     */
    public CentroPoblado getExistente(String nombre, Departamento depto){
        List<CentroPoblado> lCp;
        em = getEntityManager();
        String queryString = "SELECT cp FROM CentroPoblado cp "
                + "WHERE cp.nombre = :stringParam "
                + "AND cp.departamento = :depto";
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

    public boolean tieneDependencias(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
