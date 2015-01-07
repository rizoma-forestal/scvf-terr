/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.facades;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.GeoRef;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;


/**
 *
 * @author lagarcia
 */
@RunWith(Arquillian.class)
public class GeoRefFacadeTest {
    @Deployment
    public static JavaArchive getDeployment(){
        return ShrinkWrap.create(JavaArchive.class, "georef.jar")
                .addClasses(GeoRef.class, GeoRefFacade.class);
    }
    
    @EJB
    GeoRefFacade gorefFacade;
    
    @Test
    @UsingDataSet("georef.xml")
    /**
     * Pruebo el create()
     */
    public void EntidadFacadeTest001(){ 
        GeoRef georef = new GeoRef();
        
        georef.setPunto("Primer punto");
        
        gorefFacade.create(georef);
        
        Assert.assertEquals("Primer punto", georef.getPunto());
    }   
    
    @Test
    @UsingDataSet("georef.xml")
    /**
     * Pruebo el getXString()
     */
    public void EntidadFacadeTest002(){
        List<GeoRef> lGeorefs = gorefFacade.getXString("Pri");
        
        Assert.assertEquals(1, lGeorefs.size());
    }     
}
