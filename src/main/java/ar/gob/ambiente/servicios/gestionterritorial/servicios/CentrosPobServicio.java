
package ar.gob.ambiente.servicios.gestionterritorial.servicios;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPoblado;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPobladoTipo;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.EspecificidadDeRegion;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Municipio;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Region;
import ar.gob.ambiente.servicios.gestionterritorial.facades.CentroPobladoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.CentroPobladoTipoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.DepartamentoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.EspecificidadDeRegionFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.MunicipioFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.ProvinciaFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.RegionFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * EJB que actúa como interface de servicios de acceso a datos expuesta para ser consumida por el web service CentrosPobladosWebService
 * @author Administrador
 * @deprecated Esta clase forma parte de la anterior implementación de API de servicios SOAP, reemplazada por la API Rest
 */
@Stateless
@LocalBean
public class CentrosPobServicio {
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de CentroPoblado
     */
    @EJB
    private CentroPobladoFacade centroPobladoFacade;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Departamento
     */
    @EJB
    private DepartamentoFacade departamentoFacade;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Municipio
     */
    @EJB
    private MunicipioFacade municipioFacade;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Region
     */
    @EJB
    private RegionFacade regionFacade;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Provincia
     */
    @EJB
    private ProvinciaFacade provinciaFacade;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de CentroPobladoTipo
     */
    @EJB
    private CentroPobladoTipoFacade centroPobladoTipoFacade;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de EspecificidadDeRegion
     */
    @EJB
    private EspecificidadDeRegionFacade espRegFacade;
    
    /**
     * Variable privada para el logeo en el servidor
     */
    private static final Logger logger = Logger.getLogger(CentroPoblado.class.getName());
    
    /**
     * Método que obtiene las localidades por Departamento mediente el método getCentrosXDepto(Long idDepto)
     * Inscribe en el log del server el resultado de la operación
     * @param idDepto Long id del Departamento
     * @return List<CentroPoblado> Listado de las localidades correspondientes
     */
    public List<CentroPoblado> getCentrosPorDepto(Long idDepto) {
        List<CentroPoblado> lstCentros = new ArrayList();
        Date date;
        try{
            lstCentros = centroPobladoFacade.getCentrosXDepto(idDepto);
            logger.log(Level.INFO, "Ejecutando el método getCentrosXDepto() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getCentrosXDepto() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }        
        return lstCentros;
    }

    /**
     * Método que obtiene las localidades por Departamento y Tipo mediente el método getCentrosXDeptoTipo(Long idDepto, Long idTipo)
     * Inscribe en el log del server el resultado de la operación
     * @param idDepto Long id del Departamento
     * @param idTipo Long id del Tipo del Centro poblado
     * @return List<CentroPoblado> Listado de las localidades correspondientes
     */
    public List<CentroPoblado> getCentrosPorDeptoYTipo(Long idDepto, Long idTipo) {
        List<CentroPoblado> lstCentros = new ArrayList();
        Date date;
        try{
            lstCentros = centroPobladoFacade.getCentrosXDeptoTipo(idDepto, idTipo);
            logger.log(Level.INFO, "Ejecutando el método getCentrosXDeptoTipo() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getCentrosXDeptoTipo() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }        
        return lstCentros;
    }
    
    /**
     * Método que obtiene las localidades por Provincia y Tipo mediente el método getCentrosXProvTipo(Long idProv, Long idTipo);
     * Inscribe en el log del server el resultado de la operación
     * @param idProv Long id de la Provincia
     * @param idTipo Long id del Tipo del Centro poblado
     * @return List<CentroPoblado> Listado de las localidades correspondientes
     */
    public List<CentroPoblado> getCentrosPorProvYTipo(Long idProv, Long idTipo) {
        List<CentroPoblado> lstCentros = new ArrayList();
        Date date;
        try{
            lstCentros = centroPobladoFacade.getCentrosXProvTipo(idProv, idTipo);
            logger.log(Level.INFO, "Ejecutando el método getCentrosPorProvYTipo() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getCentrosPorProvYTipo() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }      
        return lstCentros;
    }
    
    /**
     * Método que obtiene las localidades por Región y Tipo mediente el método getCentrosXRegionTipo(Long idRegion, Long idTipo)
     * Inscribe en el log del server el resultado de la operación
     * @param idRegion Long id de la Región
     * @param idTipo Long id del Tipo del Centro poblado
     * @return List<CentroPoblado> Listado de las localidades correspondientes
     */
    public List<CentroPoblado> getCentrosPorRegionYTipo(Long idRegion, Long idTipo) {
        List<CentroPoblado> lstCentros = new ArrayList();
        Date date;
        try{
            lstCentros = centroPobladoFacade.getCentrosXRegionTipo(idRegion, idTipo);
            logger.log(Level.INFO, "Ejecutando el método getCentrosPorRegionYTipo() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getCentrosPorRegionYTipo() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }      
        return lstCentros;
    }
    
    /**
     * Método que obtiene las localidades por Provincia el método getCentrosXRegionTipo(Long idRegion, Long idTipo)
     * Inscribe en el log del server el resultado de la operación
     * @param idProv Long id de la Provincia
     * @return List<CentroPoblado> Listado de las localidades correspondientes
     */
    public List<Departamento> getDeptosPorProvincia(Long idProv){
        List<Departamento> lstDeptos = new ArrayList();
        Date date;
        try{
            lstDeptos = departamentoFacade.getDeptosXIdProv(idProv);
            logger.log(Level.INFO, "Ejecutando el método getDeptosPorProvincia() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getDeptosPorProvincia() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }      
        return lstDeptos;
    }
    
    /**
     * Método que obtiene los Municipios por Provincia mediante el método getMunicipioXIdProv(Long idProv)
     * Inscribe en el log del server el resultado de la operación
     * @param idProv Long id de la Provincia
     * @return List<Municipio> Listado de los Municipios correspondientes
     */
    public List<Municipio> getMunicipiosPorProvincia(Long idProv){
        List<Municipio> lstMunicipios = new ArrayList();
        Date date;
        try{
            lstMunicipios = municipioFacade.getMunicipioXIdProv(idProv);
            logger.log(Level.INFO, "Ejecutando el método getMunicipiosPorProvincia() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getMunicipiosPorProvincia() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }      
        return lstMunicipios;
    }
    
    /**
     * Método que obtiene los Municipios por id mediante el método find(Long idMunicipio)
     * Inscribe en el log del server el resultado de la operación
     * @param idMunicipio Long id del Municipio
     * @return Municipio Municipio correspondiente
     */    
    public Municipio getMunicipioPorId(Long idMunicipio){
        Municipio result;
        Date date;
        try{
            result = municipioFacade.find(idMunicipio);
            logger.log(Level.INFO, "Ejecutando el método getMunicipioPorId() desde el servicio");
            return result;
        }catch(Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getMunicipioPorId() desde el servicio de Centros Poblados. " + date + ". ", ex);
            return null;
        }
    }
    
    /**
     * Método que obtiene las Regiones por Provincia mediante el método getRegionesXidProv(Long idProv)
     * Inscribe en el log del server el resultado de la operación
     * @param idProv Long id de la Provincia
     * @return List<Region> Listado de las Regiones correspondientes
     */    
    public List<Region> getRegionesPorProvincia(Long idProv){
        List<Region> lstRegiones = new ArrayList();
        Date date;
        try{
            lstRegiones = regionFacade.getRegionesXidProv(idProv);
            logger.log(Level.INFO, "Ejecutando el método getRegionesPorProvincia() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getRegionesPorProvincia() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }      
        return lstRegiones;
    }
    
    /**
     * Método que obtiene las Regiones por Especificidade mediante el método getRegionesXidEspecif(Long idEspecif)
     * Inscribe en el log del server el resultado de la operación
     * @param idEspecif Long id de la Especificidad
     * @return List<Region> Listado de las Regiones correspondientes
     */        
    public List<Region> getRegionesPorEspecif(Long idEspecif){
        List<Region> lstRegiones = new ArrayList();
        Date date;
        try{
            lstRegiones = regionFacade.getRegionesXidEspecif(idEspecif);
            logger.log(Level.INFO, "Ejecutando el método getRegionesPorEspecif() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getRegionesPorEspecif() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }      
        return lstRegiones;
    }

    /**
     * Método que obtiene las Provincias por Región mediante el método getProvXIdRegion(Long idRegion)
     * Inscribe en el log del server el resultado de la operación
     * @param idRegion Long id de la Región
     * @return List<Provincia> Listado de las Provincias correspondientes
     */      
    public List<Provincia> getProvinciasPorRegion(Long idRegion){
        List<Provincia> lstProvincias = new ArrayList();
        Date date;
        try{
            lstProvincias = provinciaFacade.getProvXIdRegion(idRegion);
            logger.log(Level.INFO, "Ejecutando el método getProvinciasPorRegion() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getProvinciasPorRegion() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }      
        return lstProvincias;
    }    
    
    /**
     * Método que obtiene las Provincias disponibles mediante el método getActivos()
     * Inscribe en el log del server el resultado de la operación
     * @return List<Provincia> Listado de las Provincias correspondientes
     */      
    public List<Provincia> getProvincias(){
        List<Provincia> lstProvincias = new ArrayList();
        Date date;
        try{
            lstProvincias = provinciaFacade.getActivos();
            logger.log(Level.INFO, "Ejecutando el método getProvincias() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getProvincias() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }      
        return lstProvincias;
    }
    
    /**
     * Método que obtiene los tipos de Centros poblados disponibles mediante el método getActivos()
     * Inscribe en el log del server el resultado de la operación
     * @return List<CentroPobladoTipo> Listado de los Centros poblados correspondientes
     */ 
    public List<CentroPobladoTipo> getTiposCentros(){
        List<CentroPobladoTipo> lstTipoCentro = new ArrayList();
        Date date;
        try{
            lstTipoCentro = centroPobladoTipoFacade.getActivos();
            logger.log(Level.INFO, "Ejecutando el método getTiposCentros() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getTiposCentros() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }      
        return lstTipoCentro;
    }    
    
    /**
     * Método que obtiene los tipos de Especificidad de región disponibles mediante el método getActivos()
     * Inscribe en el log del server el resultado de la operación
     * @return List<EspecificidadDeRegion> Listado de las Especificidades correspondientes
     */     
    public List<EspecificidadDeRegion> getEspecifRegion(){
        List<EspecificidadDeRegion> lstEpecificidades = new ArrayList();
        Date date;
        try{
            lstEpecificidades = espRegFacade.getActivos();
            logger.log(Level.INFO, "Ejecutando el método getEspecifRegion() desde el servicio");
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getEspecifRegion() desde el servicio de Centros Poblados. " + date + ". ", ex);
        }      
        return lstEpecificidades;
    }     
    
    /**
     * Método que obtiene un Centro poblado por id mediante el método find(Long id)
     * Inscribe en el log del server el resultado de la operación
     * @return CentroPoblado Centro poblado correspondiente
     */       
    public CentroPoblado getCentroPoblado(Long id) {
        CentroPoblado centro;
        Date date;
        try{
            centro = centroPobladoFacade.find(id);
            logger.log(Level.INFO, "Ejecutando el método getCentroPoblado() desde el servicio");
            return centro;
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getCentroPoblado() desde el servicio de Centros Poblados. " + date + ". ", ex);
            return null;
        }
    }    
    
    public CentroPoblado getCentroPobByNombreYNomDep(String nombreCentro, String nombreDepto){
        CentroPoblado centro;
        Date date;
        try{
            centro = centroPobladoFacade.getByNomCentroYNomDepto(nombreCentro, nombreDepto);
            logger.log(Level.INFO, "Ejecutando el método getCentroPobByNombreYNomDep() desde el servicio");
            return centro;
        }
        catch (Exception ex){
            date = new Date(System.currentTimeMillis());
            logger.log(Level.SEVERE, "Hubo un error al ejecutar el método getCentroPobByNombreYNomDep() desde el servicio de Centros Poblados. " + date + ". ", ex);
            return null;
        }
    }
}
