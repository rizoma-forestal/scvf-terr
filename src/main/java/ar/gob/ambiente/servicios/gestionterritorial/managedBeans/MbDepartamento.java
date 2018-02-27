
package ar.gob.ambiente.servicios.gestionterritorial.managedBeans;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPoblado;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Usuario;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.gestionterritorial.facades.DepartamentoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.ProvinciaFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;


/**
 * Bean de respaldo para la gestión de Departamento
 * @author rincostante
 */
public class MbDepartamento implements Serializable {

    /**
     * Variable privada: Departamento Entidad que se gestiona mediante el bean
     */   
    private Departamento current;
    
    /**
     * Variable privada: DataModel data model para el listado de Entidades
     */
    private DataModel items = null;
    
    /**
     * Variable privada: List<Departamento> listado para el filtrado de la tabla
     */
    private List<Departamento> listFilter;
    
    /**
     * Variable privada: List<CentroPoblado> listado para el filtrado de la tabla de Centros poblados
     */
    private List<CentroPoblado> listCentroPobFilter;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Provincia
     */
    @EJB
    private ProvinciaFacade pciaFacade;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Departamento
     */
    @EJB
    private DepartamentoFacade deptoFacade; 
    
    /**
     * Variable privada: Entero que indica el tipo de actualización
     * 0=updateNormal | 1=deshabiliar | 2=habilitar 
     */
    private int update;
    
    /**
     * Variable privada: List<Provincia> listado de las Provincias disponibles para su selección
     */
    private List<Provincia> listProvincias;
    
    /**
     * Variable privada: MbLogin bean de gestión de la sesión del usuario
     */
    private MbLogin login;
    
    /**
     * Variable privada: Usuario usuario logeado
     */
    private Usuario usLogeado;
    
    /**
     * Variable privada: boolean que indica si se inició el bean
     */
    private boolean iniciado;
    
    /**
     * Constructor
     */
    public MbDepartamento() {
    }
    
    /**
     * Método que se ejecuta luego de instanciada la clase e inicializa los datos del usuario
     */
    @PostConstruct
    public void init(){
        iniciado = false;
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

    public List<Departamento> getListFilter() {
        return listFilter;
    }

    public void setListFilter(List<Departamento> listFilter) {
        this.listFilter = listFilter;
    }

    public List<CentroPoblado> getListCentroPobFilter() {
        return listCentroPobFilter;
    }

    public void setListCentroPobFilter(List<CentroPoblado> listCentroPobFilter) {
        this.listCentroPobFilter = listCentroPobFilter;
    }
    
    public Departamento getCurrent() {
        return current;
    }

    public void setCurrent(Departamento current) {
        this.current = current;
    }

    
    public List<Provincia> getListProvincias() {
        return listProvincias;
    }

    public void setListProvincias(List<Provincia> listProvincias) {
        this.listProvincias = listProvincias;
    }
   
        
    /********************************
     ** Métodos para la navegación **
     ********************************/
    /**
     * Método que setea la entidad a gestionar
     * @return Departamento La entidad gestionada
     */
    public Departamento getSelected() {
        if (current == null) {
            current = new Departamento();
        }
        return current;
    }   
    
    /**
     * Método que setea el listado de Departamentos
     * @return DataModel listado de Departamentos
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
     * Método para inicializar el listado de los Departamentos
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
        listProvincias = pciaFacade.getActivos();
        current = new Departamento();
        return "new";
    }

    /**
     * Método para inicializar la vista de actualización de una entidad
     * @return String nombre de la vista a mostrar
     */
    public String prepareEdit() {
        listProvincias = pciaFacade.getActivos();
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
     * Método para preparar la búsqueda
     * @return String la ruta a la vista que muestra los resultados de la consulta en forma de listado
     */
    public String prepareSelect(){
        return "list";
    }
    
    /**
     * Método que prepara la habilitación de un Deparatamento.
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
        if (getFacade().tieneDependencias(current.getId())){
            update = 1;
            update();        
            recreateModel();
        }
        else{
            //No Deshabilita 
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("DepartamentoNonDeletable"));            
        }
        return "view";
    }
    
    
    /**
     * Método que restea la entidad
     */
    private void recreateModel() {
        items = null;
    }

    /*************************
    ** Métodos de operación **
    **************************/
    /**
     * Método que inserta un nuevo Departamento en la base de datos, previamente genera una entidad de administración
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
            if(getFacade().noExiste(current.getNombre(), current.getProvincia())){
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartamentoCreated"));
                return "view";
            }else{
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("CreateDepartamentoExistente"));
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("DepartamentoCreatedErrorOccured"));
            return null;
        }
    }

    /**
     * Método para que implementa la actualización de un Departamento, sea para la edición, habilitación o deshabilitación:
     * Actualiza la entidad administrativa según corresponda, procede según el valor de "update",
     * ejecuta el método edit() del facade y devuelve el nombre de la vista detalle o null.
     * @return String nombre de la vista detalle o null
     */
    public String update() {
        Date date = new Date(System.currentTimeMillis());
        Departamento depto;
        
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
            if(update == 0){
                if(getFacade().noExiste(current.getNombre(), current.getProvincia())){
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartamentoUpdated"));
                    return "view";
                }else{
                    depto = getFacade().getExistente(current.getNombre(), current.getProvincia());
                    if(depto.getId() == current.getId()){
                        getFacade().edit(current);
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartamentoUpdated"));
                        return "view";
                    }else{
                        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("CreateDepartamentoExistente"));
                        return null;
                    }
                }
            }else if(update == 1){
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartamentoDeshabilitado"));
                return "view";
            }else{
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DepartamentoHabilitado"));
                return "view";
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("DepartamentoUpdatedErrorOccured"));
            return null;
        }
    }
          
    /*************************
    ** Métodos de selección **
    **************************/

    /**
     * Método que recupera un Departamento según su id
     * @param id Long id de la entidad persistida
     * @return Departamento la entidad correspondiente
     */
    public Departamento getDepartamento(java.lang.Long id) {
        return deptoFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método privado que devuelve el facade para el acceso a datos de los Departamentos
     * @return EJB DepartamentoFacade Acceso a datos
     */
    private DepartamentoFacade getFacade() {
        return deptoFacade;
    }
 
        
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = Departamento.class)
    public static class DepartamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbDepartamento controller = (MbDepartamento) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbDepartamento");
            return controller.getDepartamento(getKey(value));
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
            if (object instanceof Departamento) {
                Departamento o = (Departamento) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Departamento.class.getName());
            }
        }
    }        
}
