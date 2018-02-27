
package ar.gob.ambiente.servicios.gestionterritorial.managedBeans;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPoblado;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPobladoTipo;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Usuario;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.gestionterritorial.facades.CentroPobladoTipoFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

/**
 * Bean de respaldo para la gestión de CentroPobladoTipo
 * @author rincostante
 */
public class MbCentroPobladoTipo  implements Serializable{
    
    /**
     * Variable privada: CentroPobladoTipo Entidad que se gestiona mediante el bean
     */   
    private CentroPobladoTipo current;
    
    /**
     * Variable privada: DataModel data model para el listado de Entidades
     */
    private DataModel items = null;
    
    /**
     * Variable privada: List<CentroPobladoTipo> listado para el filtrado de la tabla
     */
    private List<CentroPobladoTipo> listFilter;
    
    /**
     * Variable privada: List<CentroPobladoTipo> listado para el filtrado de la tabla de Centros poblados
     */
    private List<CentroPoblado> listCentroPobladoFilter;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de CentroPobladoTipo
     */
    @EJB
    private CentroPobladoTipoFacade centroPobladoTipoFacade;
    
    /**
     * Variable privada: Entero que indica el tipo de actualización
     * 0=updateNormal | 1=deshabiliar | 2=habilitar 
     */
    private int update;
    
    /**
     * Variable privada: Usuario usuario logeado
     */
    private Usuario usLogeado;
    
    /**
     * Variable privada: boolean que indica si se inició el bean
     */
    private boolean iniciado;
    
    /**
     * Variable privada: MbLogin bean de gestión de la sesión del usuario
     */
    private MbLogin login;
    
    /*
     * Constructor
     */
    public MbCentroPobladoTipo() {
         
    }   
    
    /**
     * Método que se ejecuta luego de instanciada la clase e inicializa los datos del usuario
     */
    @PostConstruct
    public void init(){
        iniciado = false;
        update = 0;
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        login = (MbLogin)ctx.getSessionMap().get("mbLogin");
        if(login != null)usLogeado = login.getUsLogeado();
    }

    /**
     * Método que borra de la memoria los MB innecesarios al cargar el listado 
     */
    public void iniciar(){
        if(!iniciado){
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
        }
    }    

    public List<CentroPoblado> getListCentroPobladoFilter() {
        return listCentroPobladoFilter;
    }

    public void setListCentroPobladoFilter(List<CentroPoblado> listCentroPobladoFilter) {
        this.listCentroPobladoFilter = listCentroPobladoFilter;
    }

    public List<CentroPobladoTipo> getListFilter() {
        return listFilter;
    }

    public void setListFilter(List<CentroPobladoTipo> listFilter) {
        this.listFilter = listFilter;
    }

    public CentroPobladoTipo getCurrent() {
        return current;
    }

    public void setCurrent(CentroPobladoTipo current) {
        this.current = current;
    }       
    
    /********************************
     ** Métodos para la navegación **
     ********************************/
    /**
     * Método que setea la entidad a gestionar
     * @return CentroPobladoTipo La entidad gestionada
     */
    public CentroPobladoTipo getSelected() {
        if (current == null) {
            current = new CentroPobladoTipo();
        }
        return current;
    }   
    
    /**
     * Método que setea el listado de CentroPobladoTipo
     * @return DataModel listado de CentroPobladoTipo
     */
    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(getFacade().findAll());
        }
        return items;
    }
    

    /*******************************
     ** Métodos de inicialización **
     *******************************/
    /**
     * Método para inicializar el listado de los Tipos Centros poblados
     * @return String nombre de la vista a mostrar
     */
    public String prepareList() {
        recreateModel();
        return "list";
    }

    /**
     * Método para inicializar la vista detalle
     * @return String nombre de la vista a mostrar
     */
    public String prepareView() {
        return "view";
    }

    /**
     * Método para inicializar la vista de creación de una entidad
     * @return String nombre de la vista a mostrar
     */
    public String prepareCreate() {
        current = new CentroPobladoTipo();
        return "new";
    }

    /**
     * Método para inicializar la vista de actualización de una entidad
     * @return String nombre de la vista a mostrar
     */
    public String prepareEdit() {
        return "edit";
    }
    
    /**
     * Método para inicializar la aplicación
     * @return String ruta a la vista de inicio
     */    
    public String prepareInicio(){
        recreateModel();
        return "/faces/index";
    }
    
    /**
     * Método que prepara la habilitación de un Tipo Centro Poblado.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     */    
     public void habilitar() {
        update = 2;
        update();        
        recreateModel();
    }  

     /**
     * Método que prepara la deshabilitación de un Departamento.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     * @return String nombre de la vista detalle
     */        
    public String deshabilitar() {
        if(getFacade().noTieneDependencias(current.getId())){
            update = 1;
            update();        
            recreateModel();
        }else{
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoTipoNonDeletable"));
        }
        return "view";
    } 
    
    /**
     * Método para validar que no exista ya una entidad con este nombre al momento de crearla
     * @param arg0 FacesContext vista jsf que llama al validador
     * @param arg1 UIComponent objeto de la vista que hace el llamado
     * @param arg2 Object contenido del campo de texto a validar 
     */
    public void validarInsert(FacesContext arg0, UIComponent arg1, Object arg2){
        validarExistente(arg2);
    }
    
    /**
     * Método para validar que no exista una entidad con este nombre, siempre que dicho nombre no sea el que tenía originalmente
     * @param arg0 FacesContext vista jsf que llama al validador
     * @param arg1 UIComponent objeto de la vista que hace el llamado
     * @param arg2 Object contenido del campo de texto a validar 
     * @throws ValidatorException 
     */
    public void validarUpdate(FacesContext arg0, UIComponent arg1, Object arg2){
        if(!current.getNombre().equals((String)arg2)){
            validarExistente(arg2);
        }
    }
    
    /**
     * Método para validar que no exista la entidad que se quiere insertar
     * @param arg2 Object contenido del campo de texto a validar 
     * @throws ValidatorException 
     */
    private void validarExistente(Object arg2) throws ValidatorException{
        if(!getFacade().existe((String)arg2)){
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("CreateEspRegionExistente")));
        }
    }
    
    /**
     * Método que estea la entidad
     */
    private void recreateModel() {
        items = null;
    }

    /*************************
    ** Métodos de operación **
    **************************/
    /**
     * Método que inserta un nuevo Tipo Centro Poblado en la base de datos, previamente genera una entidad de administración
     * con los datos necesarios y luego se la asigna a la persona
     * @return String mensaje que notifica la inserción
     */
    public String create() {
        // Creación de la entidad de administración y asignación
        Date date = new Date(System.currentTimeMillis());
        AdminEntidad admEnt = new AdminEntidad();
        admEnt.setFechaAlta(date);
        admEnt.setHabilitado(true);
        admEnt.setUsAlta(usLogeado);
        current.setAdminentidad(admEnt);        
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoTipoCreado"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoTipoErrorAlCrearlo"));
            return null;
        }
    }

    /**
     * Método para que implementa la actualización de un Tipo Centro poblado, sea para la edición, habilitación o deshabilitación:
     * Actualiza la entidad administrativa según corresponda, procede según el valor de "update",
     * ejecuta el método edit() del facade y devuelve el nombre de la vista detalle o null.
     * @return String nombre de la vista detalle o null
     */
    public String update() {
        Date date = new Date(System.currentTimeMillis());
        //Date dateBaja = new Date();
        
        // actualizamos según el valor de update
        if(update == 1){
            current.getAdminentidad().setFechaBaja(date);
            current.getAdminentidad().setUsBaja(usLogeado);
            current.getAdminentidad().setHabilitado(false);
        }
        if(update == 2){
            current.getAdminentidad().setFechaModif(date);
            current.getAdminentidad().setUsModif(usLogeado);
            current.getAdminentidad().setHabilitado(true);
            current.getAdminentidad().setFechaBaja(null);
            current.getAdminentidad().setUsBaja(usLogeado);
        }
        if(update == 0){
            current.getAdminentidad().setFechaModif(date);
            current.getAdminentidad().setUsModif(usLogeado);
        }

        // acualizo
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoTipoActualizado"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoTipoErrorAlActualizarlo"));
            return null;
        }
    }

    /**************************
    ** Métodos de selección  **
    **************************/

    /**
     * Método que recupera un Tipo de Centro poblado según su id
     * @param id Long id de la entidad persistida
     * @return CentroPobladoTipo la entidad correspondiente
     */
    public CentroPobladoTipo getCentroPobladoTipo(java.lang.Long id) {
        return centroPobladoTipoFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método privado que devuelve el facade para el acceso a datos de los Tipos de Centros Poblados
     * @return EJB CentroPobladoTipoFacade Acceso a datos
     */
    private CentroPobladoTipoFacade getFacade() {
        return centroPobladoTipoFacade;
    }
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = CentroPobladoTipo.class)
    public static class CentroPobladoTipoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbCentroPobladoTipo controller = (MbCentroPobladoTipo) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbCentroPobladoTipo");
            return controller.getCentroPobladoTipo(getKey(value));
        }

        
        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }
        
        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CentroPobladoTipo) {
                CentroPobladoTipo o = (CentroPobladoTipo) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CentroPobladoTipo.class.getName());
            }
        }
    }        
}
