
package ar.gob.ambiente.servicios.gestionterritorial.ws;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPoblado;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPobladoTipo;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.EspecificidadDeRegion;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Municipio;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Region;
import ar.gob.ambiente.servicios.gestionterritorial.servicios.CentrosPobServicio;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Servicio web SOAP generado para compartir la información con el SIIF en su etapa de desarrollo
 * @deprecated Este servicio forma parte de la anterior implementación de API de servicios SOAP, reemplazada por la API Rest
 * @author rincostante
 */
@WebService(serviceName = "CentrosPobladosWebService")
@Stateless()
public class CentrosPobladosWebService {
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de CentrosPobServicio
     */
    @EJB
    private CentrosPobServicio ejbRef;

    /**
     * Web service operation que obtiene los Centros poblados por Departamento
     * @param idDepto Long id del Departamento
     * @return List<CentroPoblado> Listado de Centros
     */    
    @WebMethod(operationName = "buscarCentrosPorDepto")
    public List<CentroPoblado> getCentrosPorDepto(@WebParam(name = "idDepto") Long idDepto) {
        return ejbRef.getCentrosPorDepto(idDepto);
    }

    /**
     * Web service operation que obtiene los Centros poblados por Departamento y Tipo
     * @param idDepto Long id del Departamento
     * @param idTipo Long id del Tipo de Centro
     * @return List<CentroPoblado> Listado de Centros
     */       
    @WebMethod(operationName = "buscarCentrosPorDeptoYTipo")
    public List<CentroPoblado> getCentrosPorDeptoYTipo(@WebParam(name = "idDepto") Long idDepto, @WebParam(name = "idTipo") Long idTipo) {
        return ejbRef.getCentrosPorDeptoYTipo(idDepto, idTipo);
    }

    /**
     * Web service operation que obtiene los Centros poblados por Provincia y Tipo
     * @param idProv Long id de la Provincia
     * @param idTipo Long id del Tipo de Centro
     * @return List<CentroPoblado> Listado de Centros
     */       
    @WebMethod(operationName = "buscarCentrosPorProvYTipo")
    public List<CentroPoblado> getCentrosPorProvYTipo(@WebParam(name = "idProv") Long idProv, @WebParam(name = "idTipo") Long idTipo) {
        return ejbRef.getCentrosPorProvYTipo(idProv, idTipo);
    }

    /**
     * Web service operation que obtiene los Centros poblados por Región y Tipo
     * @param idRegion Long id de la Región
     * @param idTipo Long id del Tipo de Centro
     * @return List<CentroPoblado> Listado de Centros
     */       
    @WebMethod(operationName = "buscarCentrosPorRegionYTipo")
    public List<CentroPoblado> getCentrosPorRegionYTipo(@WebParam(name = "idRegion") Long idRegion, @WebParam(name = "idTipo") Long idTipo) {
        return ejbRef.getCentrosPorRegionYTipo(idRegion, idTipo);
    }

    /**
     * Web service operation que obtiene los Departamentos por Provincia
     * @param idProv Long id de la Provincia
     * @return List<Departamento> Listado de Departamentos
     */           
    @WebMethod(operationName = "buscarDeptosPorProvincia")
    public List<Departamento> getDeptosPorProvincia(@WebParam(name = "idProv") Long idProv) {
        return ejbRef.getDeptosPorProvincia(idProv);
    }

    /**
     * Web service operation que obtiene los Municipios por Provincia
     * @param idProv Long id de la Provincia
     * @return List<Municipio> Listado de Municipios
     */     
    @WebMethod(operationName = "buscarMunicipiosPorProvincia")
    public List<Municipio> getMunicipiosPorProvincia(@WebParam(name = "idProv") Long idProv) {
        return ejbRef.getMunicipiosPorProvincia(idProv);
    }
    
    /**
     * Web service operation que obtiene un Municipio por su id
     * @param idMuni Long id del Municipio
     * @return Municipio Municipio correspondiente
     */ 
    @WebMethod(operationName = "buscarMunicipiosPorId")
    public Municipio getMunicipiosPorId(@WebParam(name = "idMuni") Long idMuni) {
        return ejbRef.getMunicipioPorId(idMuni);
    }

    /**
     * Web service operation que obtiene las Regiones por Provincia
     * @param idProv Long id de la Provincia
     * @return List<Region> Listado de Regiones
     */    
    @WebMethod(operationName = "buscarRegionesPorProvincia")
    public List<Region> getRegionesPorProvincia(@WebParam(name = "idProv") Long idProv) {
        return ejbRef.getRegionesPorProvincia(idProv);
    }

    /**
     * Web service operation que obtiene las Regiones por Especificación
     * @param idEspecif Long id de la Especificidad
     * @return List<Region> Listado de Regiones
     */        
    @WebMethod(operationName = "buscarRegionesPorEspecif")
    public List<Region> getRegionesPorEspecif(@WebParam(name = "idEspecif") Long idEspecif) {
        return ejbRef.getRegionesPorEspecif(idEspecif);
    }

    /**
     * Web service operation que obtiene las Provincias por Región
     * @param idRegion Long id de la Región
     * @return List<Provincia> Listado de Provincias
     */    
    @WebMethod(operationName = "buscarProvinciasPorRegion")
    public List<Provincia> getProvinciasPorRegion(@WebParam(name = "idRegion") Long idRegion) {
        return ejbRef.getProvinciasPorRegion(idRegion);
    }

    /**
     * Web service operation que obtiene las Provincias disponibles
     * @return List<Provincia> Listado de Provincias
     */        
    @WebMethod(operationName = "verProvincias")
    public List<Provincia> getProvincias() {
        return ejbRef.getProvincias();
    }

    /**
     * Web service operation que obtiene los tipos de Centros disponibles
     * @return List<CentroPobladoTipo> Listado de Tipos de Centros
     */    
    @WebMethod(operationName = "verTiposCentros")
    public List<CentroPobladoTipo> getTiposCentros() {
        return ejbRef.getTiposCentros();
    }

    /**
     * Web service operation que obtiene las Especificidades de regiómn disponibles
     * @return List<EspecificidadDeRegion> Listado de Especificidades de región
     */  
    @WebMethod(operationName = "verEspecifRegion")
    public List<EspecificidadDeRegion> getEspecifRegion() {
        return ejbRef.getEspecifRegion();
    
    }
    
    /**
     * Web service operation que obtiene un Centro poblado por su id
     * @param id Long id del Centro
     * @return CentroPoblado Centro correspondiente
     */
    @WebMethod(operationName = "buscarCentroPoblado")
    public CentroPoblado getCentroPoblado(@WebParam(name = "id") Long id) {
        return ejbRef.getCentroPoblado(id);
    }

    /**
     * Web service operation que obtiene un Centro poblado por nombre y departamento
     * @param nomCentro String nombre del Centro
     * @param nomDepto String nombre del Departamento
     * @return 
     */
    @WebMethod(operationName = "buscarCentrosPorNomCpNomDto")
    public CentroPoblado buscarCentrosPorNomCpNomDto(@WebParam(name = "nomCentro") String nomCentro, @WebParam(name = "nomDepto") String nomDepto) {
        return ejbRef.getCentroPobByNombreYNomDep(nomCentro, nomDepto);
    }
}
