
package ar.gob.ambiente.servicios.gestionterritorial.rest;

import ar.gob.ambiente.servicios.gestionterritorial.facades.UsuarioFacade;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Recurso para validar usuario de acceso a la API y devolver el token
 * @author rincostante
 */
@Path("usuario")
public class UsuarioResource {
    
    @EJB
    private UsuarioFacade usFacade;    
    
    /**
     * @param user
     * @return 
     * @api {get} /login/ Obtiene un Response con el token incluído en el header
     * @apiVersion 1.0.0
     * @apiName GetUsuario
     * @apiGroup Usuario
     * @apiDescription Método para obtener el token para incluir en los header de los request a efectuar por parte del cliente.
     * Recibe un nombre de usuario, valida que esté registrado correctamente con el método local validarUsuarioApi(user) y, de ser así,  
     * genera un token y lo devuelve al usuario.
     * @apiParam {String} nombre Nombre del usuario autorizado para consumir el servicio.
     * @apiSuccess {String} authorization  Token de autenticación para enviar como header en los métodos de consulta.
     * @apiSuccessExample Respuesta exitosa:
     *     HTTP/1.1 200 OK
     *     {
     *       "authorization": "{{token}}"
     *     }
     * @apiError UsuarioInvalido Mensaje vacío.
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 404 Not Found
     *     {}
     */          
    @GET
    @Path("login")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response authenticateUser(@QueryParam("user") String user){
        
        // valido que el usuario esté registrado con acceso a la API
        if(usFacade.validarUsuarioApi(user)){
            // obtengo el token para el usuario
            String token = issueToken(user);
            // envío el token en la cabecera del mensaje
            return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
        }else{
            // envío un mensaje de error
            return Response.noContent().build();
        }
    }
    
    /**
     * Método privado para generar el token con vigencia de 60 min.
     * Codificado mediante la KEY del RestSecurityFilter
     * @param login String nombre del usuario
     * @return String token generado
     */
    private String issueToken(String login) {
        
    	//Calculamos la fecha de expiración del token
    	Date issueDate = new Date();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(issueDate);
    	calendar.add(Calendar.MINUTE, 60);
        Date expireDate = calendar.getTime();
        
	//Creamos el token
        String jwtToken = Jwts.builder()
        	.claim("rol", "a,b,c")
                .setSubject(login)
                .setIssuer(ResourceBundle.getBundle("/Config").getString("Servidor"))
                .setIssuedAt(issueDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, RestSecurityFilter.KEY)
                .compact();
        return jwtToken;        
    }
}
