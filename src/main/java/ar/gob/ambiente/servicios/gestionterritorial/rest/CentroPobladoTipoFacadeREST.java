
package ar.gob.ambiente.servicios.gestionterritorial.rest;

import ar.gob.ambiente.servicios.gestionterritorial.annotation.Secured;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPobladoTipo;
import ar.gob.ambiente.servicios.gestionterritorial.facades.CentroPobladoTipoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Servicio que implementa los métodos expuestos por la API REST para la entidad Tipo Centro Poblado
 * Solo se tratará se consulta, de modo que los únicos métodos implementado serán con el verbo GET
 * @author rincostante
 */
@Stateless
@Path("tipooscentrospoblados")
public class CentroPobladoTipoFacadeREST {

    @EJB
    private CentroPobladoTipoFacade centroTipoFacade;   
    
    /**
     * @api {get} /tipooscentrospoblados/:id Ver un Tipo de centro poblado
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "Content-Type: application/json" -X GET -d /gestionTerritorial/rest/tipooscentrospoblados/7
     * @apiVersion 1.0.0
     * @apiName GetCentroPobladoTipo
     * @apiGroup Tipos de Centros poblados
     * 
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * 
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "ssNjyzTYUqNUefxw4NOAtWGrpimMD96VXRxRlseoHewGxqqhnIw"
     *     } 
     *
     * @apiDescription Método para obtener un Tipo de centro poblado existente según el id remitido.
     * Obtiene el tipo de centro poblado mediante el método local find(Long id)
     * 
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.CentroPobladoTipo} CentroPobladoTipo  Detalle del tipo de centro poblado registrado.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *          "id": "7",
     *          "nombre": "CASERIO"
     *     }
     *
     * @apiError CentroPobladoTipoNotFound No existe tipo de centro poblado registrado con ese id.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay tipo de centro poblado registrado con el id recibido"
     *     }
     */         
    @GET
    @Secured
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CentroPobladoTipo find(@PathParam("id") Long id) {
        return centroTipoFacade.find(id);
    }

    /**
     * @api {get} /tipooscentrospoblados Ver todas los Tipos de centros poblados
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "Content-Type: application/json" -X GET -d /gestionTerritorial/rest/tipooscentrospoblados
     * @apiVersion 1.0.0
     * @apiName GetCentroPobladoTipos
     * @apiGroup Tipos de Centros poblados
     *
     * @apiHeader {String} Authorization Token recibido al autenticar el usuario
     * 
     * @apiHeaderExample {json} Ejemplo de header:
     *     {
     *       "Authorization": "ssNjyzTYUqNUefxw4NOAtWGrpimMD96VXRxRlseoHewGxqqhnIw"
     *     } 
     * 
     * @apiDescription Método para obtener un listado de todos los Tipos de Centros Poblados existentes.
     * Obtiene las los tipos de centros poblados mediante el método local findAll()
     * 
     * @apiSuccess {ar.gob.ambiente.sacvefor.servicios.territorial.CentroPobladoTipo} CentroPobladoTipo Listado con todos los Tipos de Centros poblados registradas.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *          "tipooscentrospoblados": [
     *              {
     *                  "id": "7",
     *                  "nombre": "CASERIO"
     *              },
     *              {
     *                  "id": "4",
     *                  "nombre": "BARRIO"
     *              }
     *          ]
     *     }
     *
     * @apiError CentroPobladoTipoNotFound No existen tipos de centros poblados registrados.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay Tipos de centros poblados registrados"
     *     }
     */      
    @GET
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CentroPobladoTipo> findAll() {
        return centroTipoFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CentroPobladoTipo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return centroTipoFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(centroTipoFacade.count());
    }
}
