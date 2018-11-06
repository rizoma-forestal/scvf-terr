
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Servicio que implementa los métodos expuestos por la API REST para la entidad Centro Poblado
 * Solo se tratará se consulta, de modo que los únicos métodos implementado serán con el verbo GET
 * @author rincostante
 */
@Stateless
@Path("centrospoblados")
public class CentroPobladoFacadeREST {

    @EJB
    private CentroPobladoFacade centroFacade; 
    @EJB
    private DepartamentoFacade deptoFacade;

    /**
     * @api {get} /centrospoblados/:id Ver una Localidad
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X GET -d /gestionTerritorial/rest/centrospoblados/1293 -H "authorization: xXyYvWzZ"
     * @apiVersion 1.0.0
     * @apiName GetCentroPoblado
     * @apiGroup Centros poblados
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     } 
     * @apiParam {Long} id Identificador único de la Localidad
     * @apiDescription Método para obtener una Localidad existente según el id remitido.
     * Obtiene la localidad mediante el método local find(Long id)
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.CentroPoblado} CentroPoblado  Detalle de la localidad registrada.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *     {
     *          {
     *              "id": "1293",
     *              "nombre": "CORONEL BRANDSEN",
     *              "departamento": 
     *                  {
     *                      "id": "18",
     *                      "nombre": "BRANDSEN",
     *                      "provincia": 
     *                          {
     *                              "id": "2",
     *                              "nombre": "BUENOS AIRES"
     *                          }
     *                  },
     *              "centrpopobladotipo":
     *                  {
     *                      "id": "3",
     *                      "nombre": "CIUDAD"
     *                  }
     *          }
     *     }
     * @apiError CentroPobladoNotFound No existe localidad registrada con ese id.
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay localidad registrada con el id recibido"
     *     }
     */     
    @GET
    @Secured
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CentroPoblado find(@PathParam("id") Long id) {
        return centroFacade.find(id);
    }

    /**
     * @api {get} /svf_especies Ver todas las Localidades
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X GET -d /gestionTerritorial/rest/centrospoblados -H "authorization: xXyYvWzZ"
     * @apiVersion 1.0.0
     * @apiName GetCentrosPoblados
     * @apiGroup Centros poblados
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     } 
     * @apiDescription Método para obtener un listado de las Localidades existentes.
     * Obtiene las localidades mediante el método local findAll()
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.CentroPoblado} CentroPoblado Listado con todos las Localidades registradas.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *     {
     *          "localidades": [
     *             {
     *                 "id": "1293",
     *                 "nombre": "CORONEL BRANDSEN",
     *                 "departamento": 
     *                     {
     *                         "id": "18",
     *                         "nombre": "BRANDSEN",
     *                         "provincia": 
     *                             {
     *                                 "id": "2",
     *                                 "nombre": "BUENOS AIRES"
     *                             }
     *                     },
     *                 "centrpopobladotipo":
     *                     {
     *                         "id": "3",
     *                         "nombre": "CIUDAD"
     *                     }
     *             },
     *             {
     *                 "id": "1291",
     *                 "nombre": "SAMBOROMBON",
     *                 "departamento": 
     *                     {
     *                         "id": "18",
     *                         "nombre": "BRANDSEN",
     *                         "provincia": 
     *                             {
     *                                 "id": "2",
     *                                 "nombre": "BUENOS AIRES"
     *                             }
     *                     },
     *                 "centrpopobladotipo":
     *                     {
     *                         "id": "9",
     *                         "nombre": "PARAJE"
     *                     }
     *             }
     *          ]
     *     }
     * @apiError EspeciesNotFound No existen localidades registradas.
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay Localidades registradas"
     *     }
     */    
    @GET
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CentroPoblado> findAll() {
        return centroFacade.findAll();
    }
    
    /**
     * @api {get} /centrospoblados/query?nombre=:nombre&id_depto=172 Ver una Loclidad según su nombre y el id del Departamento al que pertenece.
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X GET -d [PATH_SERVER]/:gestionTerritorial/rest/centrospoblados/query?nombre=TACUARI&id_depto=172 -H "authorization: xXyYvWzZ"
     * @apiVersion 1.0.0
     * @apiName GetLocalidadesQuery
     * @apiGroup Localidades
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "xXyYvWzZ"
     *     }
     * @apiParam {String} nombre de la Localidad solicitada
     * @apiParam {String} id del Departamento al que pertenece
     * @apiDescription Método para obtener una Localidad según su nombre y el id del Departamento al que pertenece.
     * Obtiene la Localidad con el método local getExistente(String nombre, Departamento depto), (en mayúsculas el nombre)
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.CentroPoblado} Provincia provincia obtenida.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *              {
     *                  "id":"1137",
     *                  "nombre":"TACUARI",
     *                  "centropobladotipo":{
     *                              "id":"9",
     *                              "nombre":"PARAJE"
     *                          }
     *                  "departamento":{
     *                      "id":"172",
     *                      "nombre":"BERMEJO",
     *                          "provincia":{
     *                                  "id":"6",
     *                                  "nombre":"CHACO"
     *                              }
     *              }
     * @apiError LocalidadNotFound No existe Localidad registrada con ese nombre para el Departamento solicitado.
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay Localidad registrada con con ese nombre para el Departamento solicitado"
     *     }
     */         
    @GET
    @Path("/query")
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CentroPoblado findByQuery(@QueryParam("nombre") String nombre,
            @QueryParam("id_depto") String id_depto){
        CentroPoblado result = new CentroPoblado();
        // obtengo el Departamento a partir del id recibido
        Departamento depto = deptoFacade.find(Long.valueOf(id_depto));
        if(depto != null){
            result = centroFacade.getExistente(nombre, depto);
        }
        return result;
    }


    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CentroPoblado> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return centroFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(centroFacade.count());
    }
}
