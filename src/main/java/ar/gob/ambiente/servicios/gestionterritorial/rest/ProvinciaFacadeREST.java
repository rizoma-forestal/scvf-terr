
package ar.gob.ambiente.servicios.gestionterritorial.rest;

import ar.gob.ambiente.servicios.gestionterritorial.annotation.Secured;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;
import ar.gob.ambiente.servicios.gestionterritorial.facades.DepartamentoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.ProvinciaFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Servicio que implementa los métodos expuestos por la API REST para la entidad Provincia
 * Solo se tratará se consulta, de modo que los únicos métodos implementado serán con el verbo GET
 * @author rincostante
 */
@Stateless
@Path("provincias")
public class ProvinciaFacadeREST {

    @EJB
    private ProvinciaFacade provinciaFacade;   
    @EJB
    private DepartamentoFacade deptoFacade;
    
    /**
     * @api {get} /provincias/:id Ver una Provincia
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "Content-Type: application/json" -X GET -d /gestionTerritorial/rest/provincias/2
     * @apiVersion 1.0.0
     * @apiName GetProvincia
     * @apiGroup Provincias
     *
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * 
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "ssNjyzTYUqNUefxw4NOAtWGrpimMD96VXRxRlseoHewGxqqhnIw"
     *     } 
     * 
     * @apiDescription Método para obtener una Provincia existente según el id remitido.
     * Obtiene la provincia mediante el método local find(Long id)
     * 
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.Provincia} Provincia Detalle de la provincia registrada.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       {
     *          "id": "2",
     *          "nombre": "BUENOS AIRES"
     *       }
     *     }
     *
     * @apiError ProvinciaNotFound No existe provincia registrada con ese id.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay provincia registrada con el id recibido"
     *     }
     */             
    @GET
    @Path("{id}")
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Provincia find(@PathParam("id") Long id) {
        return provinciaFacade.find(id);
    }

    /**
     * @api {get} /provincias Ver todas las Provincias
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "Content-Type: application/json" -X GET -d /gestionTerritorial/rest/provincias
     * @apiVersion 1.0.0
     * @apiName GetProvincias
     * @apiGroup Provincias
     *
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * 
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "ssNjyzTYUqNUefxw4NOAtWGrpimMD96VXRxRlseoHewGxqqhnIw"
     *     } 
     * 
     * @apiDescription Método para obtener un listado de las Provincias existentes.
     * Obtiene las provincias mediante el método local findAll()
     * 
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.Provincia} Provincias Listado con todas las Provincias registradas.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "provincias": [
     *          {"id": "2",
     *          "nombre": "BUENOS AIRES"},
     *          {"id": "3",
     *          "nombre": "CATAMARCA"}
     *          ]
     *     }
     *
     * @apiError ProvinciasNotFound No existen provincias registradas.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay Provincias registradas"
     *     }
     */         
    @GET
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Provincia> findAll() {
        return provinciaFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Provincia> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return provinciaFacade.findRange(new int[]{from, to});
    }
    
    /**
     * @api {get} /provincias/:id/generos Ver los Departamentos de una Provincia
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "Content-Type: application/json" -X GET -d /gestionTerritorial/rest/provincias/2/departamentos
     * @apiVersion 1.0.0
     * @apiName GetDepartamentos
     * @apiGroup Provincias
     *
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * 
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "ssNjyzTYUqNUefxw4NOAtWGrpimMD96VXRxRlseoHewGxqqhnIw"
     *     } 
     * 
     * @apiDescription Método para obtener los Departamentos asociados a una Provincia existente según el id remitido.
     * Obtiene los departamentos mediante el método local getDeptosXIdProv(Long id)
     * 
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.Departamento} Departamento Listado de los Departamentos registrados vinculados a la Provincia cuyo id se recibió.
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
     * @apiError DeptosNotFound No existen departamentos registrados vinculados a la id de la provincia.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay departamentos registrados vinculados al id de la provincia recibida."
     *     }
     */        
    @GET
    @Secured
    @Path("{id}/departamentos")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Departamento> findByProvincia(@PathParam("id") Long id) {
        return deptoFacade.getDeptosXIdProv(id);
    }        

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(provinciaFacade.count());
    }
}
