
package ar.gob.ambiente.servicios.gestionterritorial.managedBeans;


import ar.gob.ambiente.servicios.gestionterritorial.entidades.Usuario;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.util.CriptPass;
import ar.gob.ambiente.servicios.gestionterritorial.facades.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.omnifaces.util.Faces;

/**
 * Bean de respaldo para la gestión de la sesión del usuario
 * @author rincostante
 */
public class MbLogin implements Serializable{
    
    private static final long serialVersionUID = -2152389656664659476L;
    
    /**
     * Variable privada: String nombre del usuario (login)
     */
    private String nombre;
    
    /**
     * Variable privada: String contraseña del usuario
     */
    private String clave;
    
    /**
     * Variable privada: boolean indica el estado de logeado del usuario
     */
    private boolean logeado = false;  
    
    /**
     * Variable privada: String indica el rol del usuario
     */
    private String ambito;
    
    /**
     * Variable privada: Usuario usuario logeado
     */
    private Usuario usLogeado;
    
    /**
     * Variable privada: String para guardar la contraseña existente del usuario
     */
    private String claveAnterior_1;
    
    /**
     * Variable privada: String para guardar la contraseña existente repetida del usuario
     */
    private String claveAnterior_2;
    
    /**
     * Variable privada: String para guardar la contraseña nueva del usuario
     */
    private String claveNueva;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Usuarios
     */
    @EJB
    private UsuarioFacade usuarioFacade;
    
    /**
     * Variable privada: List<String> listado de Beans de respaldo activos
     */
    private List<String> listMbActivos;
    
    /**
     * Variable privada: boolean que indica si se inició el bean
     */
    private boolean iniciado;
    
    /**
     * Constructor
     */
    public MbLogin() {
    }
    
    /**
     * Método para inicializar el listado de los Mb activos
     */
    @PostConstruct
    public void init(){
        iniciado = false;
        listMbActivos = new ArrayList();
    }
    
    /**
     * Método que borra de la memoria los MB innecesarios al cargar el listado 
     * @throws java.io.IOException
     */
    public void iniciar() throws IOException{
        if(iniciado){
            String s;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(true);
            Enumeration enume = session.getAttributeNames();
            while(enume.hasMoreElements()){
                s = (String)enume.nextElement();
                if(s.substring(0, 2).equals("mb")){
                    if(!s.equals("mbLogin")){
                        session.removeAttribute(s);
                    }
                }
            }
        }else{
            // obtengo el nombre del usuario logeado de la cookie correspondiente
            String nomUsuario;
            String valueEnc = Faces.getRequestCookie(ResourceBundle.getBundle("/Bundle").getString("nameCookieUser"));
            
            try {
                // desencripto el nombre
                nomUsuario = CriptPass.desencriptar(valueEnc);
                
                // obtebtengo el usuario correspondiente al nombre desencriptado
                if(usuarioFacade.getUsuario(nomUsuario) != null){
                    usLogeado = usuarioFacade.getUsuario(nomUsuario);
                    ambito = usLogeado.getRol().getNombre();
                    iniciado = true;
                }else{
                    // si no tengo un usuario registrado en la aplicación para este nombre, redirecciono a la vista de error de acceso
                    FacesContext fc=FacesContext.getCurrentInstance();
                    fc.getExternalContext().redirect(ResourceBundle.getBundle("/Bundle").getString("logError"));
                }
            } catch (Exception ex) {
                Logger.getLogger(MbLogin.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
    }     
    
    public List<String> getListMbActivos() {
        return listMbActivos;
    }

    public void setListMbActivos(List<String> listMbActivos) {
        this.listMbActivos = listMbActivos;
    }

    public String getClaveAnterior_1() {
        return claveAnterior_1;
    }

    public void setClaveAnterior_1(String claveAnterior_1) {
        this.claveAnterior_1 = claveAnterior_1;
    }

    public String getClaveAnterior_2() {
        return claveAnterior_2;
    }

    public void setClaveAnterior_2(String claveAnterior_2) {
        this.claveAnterior_2 = claveAnterior_2;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    public Usuario getUsLogeado() {
        return usLogeado;
    }

    public void setUsLogeado(Usuario usLogeado) {
        this.usLogeado = usLogeado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String rol) {
        this.ambito = rol;
    }
    
    /**
     * Método que cierra la sesión del usuario
     */    
    public void logout(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        logeado = false;
    }  
}

