
package ar.gob.ambiente.servicios.gestionterritorial.managedBeans;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Municipio;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Usuario;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.gestionterritorial.facades.DepartamentoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.MunicipioFacade;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;

/**
 * Bean de respaldo para la gestión de Municipio
 * @author rincostante
 */
public class MbMunicipio implements Serializable {
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Provincia
     */
    @EJB
    private ProvinciaFacade pciaFacade;

    /**
     * Variable privada: EJB inyectado para el acceso a datos de Departamento
     */
    @EJB
    private DepartamentoFacade dptoFacade;    
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Municipio
     */
    @EJB
    private MunicipioFacade muniFacade;
    
    /**
     * Variable privada: Municipio Entidad que se gestiona mediante el bean
     */ 
    private Municipio current;
    
    /**
     * Variable privada: DataModel data model para el listado de Entidades
     */
    private DataModel items = null; 
    
    /**
     * Variable privada: List<Provincia> listado para el filtrado de la tabla de Provincias
     */
    private List<Provincia> listaProvincias;  
    
    /**
     * Variable privada: List<Departamento> listado para el filtrado de la tabla de Departamentos
     */ 
    private List<Departamento> comboDepartamentos;
    
    /**
     * Variable privada: Provincia setea la Provincia seleccinonada para vincular al Municipio
     */
    private Provincia selectProvincia;
    
    /**
     * Variable privada: boolean que indica si se inició el bean
     */
    private boolean iniciado;
    
    /**
     * Variable privada: List<Municipio> listado de los Municipios existentes
     */
    private List<Municipio> listado;
    
    /**
     * Variable privada: List<Municipio> listado para el filtrado de la tabla
     */
    private List<Municipio> listadoFilter;
    
    /**
     * Variable privada: MbLogin bean de gestión de la sesión del usuario
     */
    private MbLogin login;
    
    /**
     * Variable privada: Usuario usuario logeado
     */
    private Usuario usLogeado;
    
    /**
     * Variable privada: Entero que indica el tipo de actualización
     * 0=updateNormal | 1=deshabiliar | 2=habilitar 
     */
    private int update;
    
    /**
     * Constructor
     */
    public MbMunicipio() {
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
    
    /********************************
     ** Métodos para la navegación **
     ********************************/
    /**
     * Método que setea la entidad a gestionar
     * @return Municipio La entidad gestionada
     */
    public Municipio getSelected() {
        if (current == null) {
            current = new Municipio();
        }
        return current;
    }   
    
    public List<Municipio> getListadoFilter() {
        return listadoFilter;
    }

    public void setListadoFilter(List<Municipio> listadoFilter) {
        this.listadoFilter = listadoFilter;
    }
    
    /**
     * Método que setea el listado de Municipios
     * @return DataModel listado de Municipios
     */    
    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(getFacade().findAll());
        }
        return items;
    }

    public Municipio getCurrent() {
        return current;
    }

    public void setCurrent(Municipio current) {
        this.current = current;
    }

    public List<Provincia> getListaProvincias() {
        return listaProvincias;
    }

    public void setListaProvincias(List<Provincia> listaProvincias) {
        this.listaProvincias = listaProvincias;
    }

    public List<Departamento> getComboDepartamentos() {
        return comboDepartamentos;
    }

    public void setComboDepartamentos(List<Departamento> comboDepartamentos) {
        this.comboDepartamentos = comboDepartamentos;
    }

    public Provincia getSelectProvincia() {
        return selectProvincia;
    }

    public void setSelectProvincia(Provincia selectProvincia) {
        this.selectProvincia = selectProvincia;
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
    
    /*******************************
     ** Métodos de inicialización **
     *******************************/
    /**
     * Método para inicializar el listado de los Municipios
     * @return String nombre de la vista a mostrar
     */
    public String prepareList() {
        recreateModel();
        listaProvincias = pciaFacade.findAll();
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
        listaProvincias = pciaFacade.getActivos();
        current = new Municipio();
        return "new";
    }

    /**
     * Método para inicializar la vista de actualización de una entidad
     * @return String nombre de la vista a mostrar
     */
    public String prepareEdit() {
        listaProvincias = pciaFacade.getActivos();
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
     * Método que restea la entidad
     */
    private void recreateModel() {
        items = null;
    }

    /*************************
    ** Métodos de operación **
    **************************/
    /**
     * Método que inserta un nuevo Municipio en la base de datos, previamente genera una entidad de administración
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
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MunicipioCreated"));
                return "view";
            }else{
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MunicipioExistente"));
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("MunicipioCreatedErrorOccured"));
            return null;
        }
    }

    public List<Municipio> getListado() {
        return listado;
    }

    public void setListado(List<Municipio> listado) {
        this.listado = listado;
    }

    /**
     * Método para que implementa la actualización de un Municipio, sea para la edición, habilitación o deshabilitación:
     * Actualiza la entidad administrativa según corresponda, procede según el valor de "update",
     * ejecuta el método edit() del facade y devuelve el nombre de la vista detalle o null.
     * @return String nombre de la vista detalle o null
     */
    public String update() {
        Date date = new Date(System.currentTimeMillis());
        Municipio municipio;
        
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
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MunicipioUpdated"));
                    return "view";
                }else{
                    municipio = getFacade().getExistente(current.getNombre(), current.getDepartamento());
                    if(municipio.getId() == current.getId()){
                        getFacade().edit(current);
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MunicipioUpdated"));
                        return "view";
                    }else{
                        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("MunicipioExistente"));
                        return null;
                    }
                }
            }else if(update == 1){
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MunicipioDeshabilitado"));
                return "view";
            }else{
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MunicipioHabilitado"));
                return "view";
            }            

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("MunicipioUpdatedErrorOccured"));
            return null;
        }
    }
    
    /**
     * Método que setea la Provincia seleccionada y obtiene los Departamentos vinculados a ella
     * @param event ValueChangeEvent evento de cambio de item seleccionado por el usuario
     */    
    public void departamentoChangeListener(ValueChangeEvent event) {      
        selectProvincia = (Provincia)event.getNewValue();      
        comboDepartamentos = dptoFacade.getPorProvincia(selectProvincia);      
    }    
    
    /**
     * Método que prepara la habilitación de un Municipio.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     */        
    public void habilitar() {
        update = 2;
        update();        
        recreateModel();
    }  
    
     /**
     * Método que prepara la deshabilitación de un Municipio.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     */   
    public void deshabilitar() {
        update = 1;
        update();        
        recreateModel();
    }      
    
    /*************************
    ** Métodos de selección **
    **************************/

    /**
     * Método que recupera un Municipio según su id
     * @param id Long id de la entidad persistida
     * @return Municipio la entidad correspondiente
     */
    public Municipio getMunicipio(java.lang.Long id) {
        return muniFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método privado que devuelve el facade para el acceso a datos de los Municipios
     * @return EJB MunicipioFacade Acceso a datos
     */
    private MunicipioFacade getFacade() {
        return muniFacade;
    }

    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = Municipio.class)
    public static class MunicipioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbMunicipio controller = (MbMunicipio) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbMunicipio");
            return controller.getMunicipio(getKey(value));
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
            if (object instanceof Municipio) {
                Municipio o = (Municipio) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Municipio.class.getName());
            }
        }
    }          
}
