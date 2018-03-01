
package ar.gob.ambiente.servicios.gestionterritorial.rest;

import ar.gob.ambiente.servicios.gestionterritorial.annotation.Secured;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPoblado;
import ar.gob.ambiente.servicios.gestionterritorial.facades.CentroPobladoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
