
package ar.gob.ambiente.servicios.gestionterritorial.rest;

import ar.gob.ambiente.servicios.gestionterritorial.annotation.Secured;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPoblado;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
import ar.gob.ambiente.servicios.gestionterritorial.facades.CentroPobladoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.DepartamentoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Servicio que implementa los métodos expuestos por la API REST para la entidad Departamento
 * Solo se tratará se consulta, de modo que los únicos métodos implementado serán con el verbo GET
 * @author rincostante
 */
@Stateless
@Path("departamentos")
public class DepartamentoFacadeREST {

    @EJB
    private DepartamentoFacade deptoFacade;   
    @EJB
    private CentroPobladoFacade centroFacade;
    
    /**
     * @api {get} /departamentos/:id Ver un Departamento
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "Content-Type: application/json" -X GET -d /gestionTerritorial/rest/departamentos/16
     * @apiVersion 1.0.0
     * @apiName GetDepartamento
     * @apiGroup Departamentos
     *
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * 
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "ssNjyzTYUqNUefxw4NOAtWGrpimMD96VXRxRlseoHewGxqqhnIw"
     *     } 
     * 
     * @apiDescription Método para obtener un Departamento existente según el id remitido.
     * Obtiene el departamento mediante el método local find(Long id)
     * 
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.Departamento} Departamento Detalle del departamento registrado.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       {
     *          "id": "16",
     *          "nombre": "FLORENCIO VARELA",
     *          "provincia": 
     *              {
     *                  "id": "2",
     *                  "nombre": "BUENOS AIRES"
     *              }
     *       }
     *     }
     *
     * @apiError DepartamentoNotFound No existe departamento registrado con ese id.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay departamento registrado con el id recibido"
     *     }
     */          
    @GET
    @Secured
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Departamento find(@PathParam("id") Long id) {
        return deptoFacade.find(id);
    }
    
    /**
     * @api {get} /departamentos/:id/centrospoblados Ver las Localidades de un Departamento
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "Content-Type: application/json" -X GET -d /gestionTerritorial/rest/departamentos/1290/centrospoblados
     * @apiVersion 1.0.0
     * @apiName GetCentosPoblados
     * @apiGroup Departamentos
     *
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * 
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "ssNjyzTYUqNUefxw4NOAtWGrpimMD96VXRxRlseoHewGxqqhnIw"
     *     } 
     * 
     * @apiDescription Método para obtener las Localidades asociadas a un Departamento existente según el id remitido.
     * Obtiene las localidades mediante el método local getCentrosXDepto(Long id)
     * 
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.CentroPoblado} CentroPoblado Listado de las Localidades registradas vinculadas al Departamento cuyo id se recibió.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *      {
     *          "centrospoblados": [
     *              {
     *                  "id": "1290",
     *                  "nombre": "ALTAMIRANO",
     *                  "departamento": 
     *                      {
     *                          "id": "18",
     *                          "nombre": "BRANDSEN",
     *                          "provincia": 
     *                              {
     *                                  "id": "2",
     *                                  "nombre": "BUENOS AIRES"
     *                              }
     *                      },
     *                  "centrpopobladotipo":
     *                      {
     *                          "id": "7",
     *                          "nombre": "CASERIO"
     *                      }
     *              },
     *              {
     *                  "id": "1291",
     *                  "nombre": "JEPPENER",
     *                  "departamento": 
     *                      {
     *                          "id": "18",
     *                          "nombre": "BRANDSEN",
     *                          "provincia": 
     *                              {
     *                                  "id": "2",
     *                                  "nombre": "BUENOS AIRES"
     *                              }
     *                      },
     *                  "centrpopobladotipo":
     *                      {
     *                          "id": "7",
     *                          "nombre": "CASERIO"
     *                      }
     *              }
     *          ]
     *      }
     * 
     * @apiError CentroPobladoNotFound No existen localidades registradas vinculadas a la id del departamento.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay localidades registradas vinculadas al id del departamento recibido."
     *     }
     */  
    @GET
    @Secured
    @Path("{id}/centrospoblados")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CentroPoblado> findByDepto(@PathParam("id") Long id) {
        return centroFacade.getCentrosXDepto(id);
    }    

    /**
     * @api {get} /departamentos Ver todos los Departamentos
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "Content-Type: application/json" -X GET -d /gestionTerritorial/rest/departamentos
     * @apiVersion 1.0.0
     * @apiName GetDepartamentos
     * @apiGroup Departamentos
     *
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * 
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "ssNjyzTYUqNUefxw4NOAtWGrpimMD96VXRxRlseoHewGxqqhnIw"
     *     } 
     * 
     * @apiDescription Método para obtener un listado de los Departamentos existentes.
     * Obtiene los departamentos mediante el método local findAll()
     * 
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.Departamento} Departamento Listado con todos los Departamentos registrados.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "departamentos": [
     *          {"id": "16",
     *          "nombre": "FLORENCIO VARELA",
     *          "provincia": 
     *              {
     *                  "id": "2",
     *                  "nombre": "BUENOS AIRES"
     *              }
     *          },
     *          {"id": "18",
     *          "nombre": "BRANDSEN",
     *          "provincia": 
     *              {
     *                  "id": "2",
     *                  "nombre": "BUENOS AIRES"
     *              }
     *          }
     *       ]
     *     }
     *
     * @apiError DepartamentosNotFound No existen departamentos registrados.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay Departamentos registrados"
     *     }
     */   
    @GET
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Departamento> findAll() {
        return deptoFacade.findAll();
    }
  
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Departamento> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return deptoFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(deptoFacade.count());
    }
}
