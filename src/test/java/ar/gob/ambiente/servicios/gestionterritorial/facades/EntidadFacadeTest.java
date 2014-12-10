/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.Entidad;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author rincostante
 */
@RunWith(Arquillian.class)
public class EntidadFacadeTest {
    @Deployment
    public static JavaArchive getDeployment(){
        return ShrinkWrap.create(JavaArchive.class, "entidad.jar")
                .addClasses(Entidad.class, EntidadFacade.class);
    }
    
    @EJB
    EntidadFacade enitdadFacade;
    
    @Test
    @UsingDataSet("entidad.xml")
    public void EntidadFacadeTest001(){
        int reg;
        Entidad p1 = new Entidad();
        p1.setNombre("Bob");
        
        enitdadFacade.create(p1);
    }
}
