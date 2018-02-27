
package ar.gob.ambiente.servicios.gestionterritorial.managedBeans;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPoblado;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPobladoTipo;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Usuario;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.gestionterritorial.facades.CentroPobladoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.CentroPobladoTipoFacade;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;


/**
 * Bean de respaldo para la gestión de CentroPoblado (Localidad)
 * @author rincostante
 */

public class MbCentroPoblado implements Serializable {

    /**
     * Variable privada: CentroPoblado Entidad que se gestiona mediante el bean
     */    
    private CentroPoblado current;
    
    /**
     * Variable privada: DataModel data model para el listado de Entidades
     */
    private DataModel items = null;

    /**
     * Variable privada: EJB inyectado para el acceso a datos de Departamento
     */
    @EJB
    private DepartamentoFacade departamentoFacade;

    /**
     * Variable privada: EJB inyectado para el acceso a datos de CentroPobladoTipo
     */
    @EJB
    private CentroPobladoTipoFacade centroPobladoTipoFacade;
   
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Provincia
     */
    @EJB
    private ProvinciaFacade provFacade;
           
    /**
     * Variable privada: EJB inyectado para el acceso a datos de CentroPoblado
     */
    @EJB
    private CentroPobladoFacade centroPobladoFacade;
    
    /**
     * Variable privada: Listado de los Departamentos disponibles para su selección y asignación a un Centro polbado
     */
    private List<Departamento> listaDepartamentos; 
    
    /**
     * Variable privada: Listado de Tipos de centros poblados para asignar a un Centro polbado
     */
    private List<CentroPobladoTipo> listaTiposCP;
    
    /**
     * Variable privada: Listado de Provincias disponibles para su selección
     */
    private List<Provincia> listaProvincias;
    
    /**
     * Variable privada: boolean que indica si se inició el bean
     */
    private boolean iniciado;
    
    /**
     * Variable privada: Provincia se setea con la Provincia seleccinoada
     */
    private Provincia provSelected;
    
    /**
     * Variable privada: Entero que indica el tipo de actualización
     * 0=updateNormal | 1=deshabiliar | 2=habilitar 
     */
    private int update;
    
    /**
     * Variable privada: MbLogin bean de gestión de la sesión del usuario
     */
    private MbLogin login;
    
    /**
     * Variable privada: Usuario usuario logeado
     */
    private Usuario usLogeado;    
    
    /**
     * Variable privada: List<CentroPoblado> listado de Centros poblados registrados
     */
    private List<CentroPoblado> listado;
    
    /**
     * Variable privada: List<CentroPoblado> listado para el filtrado de la tabla
     */
    private List<CentroPoblado> listadoFilter;

    /**
     * Constructor
     */
    public MbCentroPoblado() {
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

    /********************************
     ** Getters y Setters ***********
     ********************************/ 
    public CentroPoblado getCurrent() {
        return current;
    }

    public void setCurrent(CentroPoblado current) {
        this.current = current;
    }
    
    public Provincia getProvSelected() {
        return provSelected;
    }

    public void setProvSelected(Provincia provSelected) {
        this.provSelected = provSelected;
    }

    public List<CentroPoblado> getListado() {
        return listado;
    }

    public void setListado(List<CentroPoblado> listado) {
        this.listado = listado;
    }

    public List<CentroPoblado> getListadoFilter() {
        return listadoFilter;
    }

    public void setListadoFilter(List<CentroPoblado> listadoFilter) {
        this.listadoFilter = listadoFilter;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<CentroPobladoTipo> getListaTiposCP() {
        return listaTiposCP;
    }

    public void setListaTiposCP(List<CentroPobladoTipo> listaTiposCP) {
        this.listaTiposCP = listaTiposCP;
    }

    public List<Provincia> getListaProvincias() {
        return listaProvincias;
    }

    public void setListaProvincias(List<Provincia> listaProvincias) {
        this.listaProvincias = listaProvincias;
    }

    /**
     * Método que setea la entidad a gestionar
     * @return CentroPoblado La entidad gestionada
     */
    public CentroPoblado getSelected() {
        if (current == null) {
            current = new CentroPoblado();
        }
        return current;
    }

    /**
     * Método que setea el listado de Localidades
     * @return DataModel listado de localidades
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
     * Método para inicializar el listado de los Centros poblados
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
     * y poblado de los listados correspondientes a los combos
     * @return String nombre de la vista a mostrar
     */
    public String prepareCreate() {
        listaProvincias = provFacade.getActivos();
        listaDepartamentos = departamentoFacade.getActivos();
        listaTiposCP = centroPobladoTipoFacade.getActivos();
        current = new CentroPoblado();
        return "new";
    }    
    
    /**
     * Método para inicializar la vista de actualización de una entidad
     * @return String nombre de la vista a mostrar
     */
    public String prepareEdit() {
        //cargo los list para los combos
        listaTiposCP = centroPobladoTipoFacade.getActivos();
        listaProvincias = provFacade.getActivos();
        listaDepartamentos = departamentoFacade.getActivos();
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
   
    
    /*************************
    ** Métodos de operación **
    **************************/
    /**
     * Método que inserta un nuevo Centro Poblado en la base de datos, previamente genera una entidad de administración
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
            if(getFacade().noExiste(current.getNombre(), current.getDepartamento())){
                getFacade().create(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoCreated"));
                return "view";
            }else{
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("CreateCentroPobladoExistente"));
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoCreatedErrorOccured"));
            return null;
        }
    }
    
    /**
     * Método para que implementa la actualización de un Centro poblado, sea para la edición, habilitación o deshabilitación:
     * Actualiza la entidad administrativa según corresponda, procede según el valor de "update",
     * ejecuta el método edit() del facade y devuelve el nombre de la vista detalle o null.
     * @return String nombre de la vista detalle o null
     */
    public String update() {
        Date date = new Date(System.currentTimeMillis());
        CentroPoblado centroPoblado;
        
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
                if(getFacade().noExiste(current.getNombre(), current.getDepartamento())){
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoUpdated"));
                    return "view";
                }else{
                    centroPoblado = getFacade().getExistente(current.getNombre(), current.getDepartamento());
                    if(centroPoblado.getId() == current.getId()){
                        getFacade().edit(current);
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoUpdated"));
                        return "view";
                    }else{
                        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("CreateCentroPobladoExistente"));
                        return null;
                    }
                }
            }else if(update == 1){
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoDeshabilitado"));
                return "view";
            }else{
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoHabilitado"));
                return "view";
            }         
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoUpdatedErrorOccured"));
            return null;
        }
    }
    
    /**
     * Método que prepara la habilitación de un Centro Poblado.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     */
    public void habilitar() {
        update = 2;
        update();        
        recreateModel();
    }

     /**
     * Método que prepara la deshabilitación de una Especie.
     * Setea el tipo de actualización, la ejecuta y resetea el listado
     */   
    public void deshabilitar() {
        update = 1;
        update();        
        recreateModel();
    } 
    
    /**
     * Método que recupera un Centro poblado según su id
     * @param id Long id de la entidad persistida
     * @return CentroPoblado la entidad correspondiente
     */
    public CentroPoblado getCentroPoblado(java.lang.Long id) {
        return getFacade().find(id);
    } 
   

    /**
     * Método que setea la Provincia seleccionada y obtiene los Departamentos vinculados a ella
     * @param event ValueChangeEvent evento de cambio de item seleccionado por el usuario
     */    
    public void provinciaChangeListener(ValueChangeEvent event){
        provSelected = (Provincia)event.getNewValue();
        listaDepartamentos = departamentoFacade.getPorProvincia(provSelected);
    }

    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método privado que devuelve el facade para el acceso a datos de los Centros Poblados
     * @return EJB CentroPobladoFacade Acceso a datos
     */
    private CentroPobladoFacade getFacade() {
        return centroPobladoFacade;
    }    
    
    /**
     * Método que restea el listado
     */
    private void recreateModel() {
        items = null;
    }      
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = CentroPoblado.class)
    public static class CentroPobladoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbCentroPoblado controller = (MbCentroPoblado) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbCentroPoblado");
            return controller.getCentroPoblado(getKey(value));
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
            if (object instanceof CentroPoblado) {
                CentroPoblado o = (CentroPoblado) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CentroPoblado.class.getName());
            }
        }
    }             
}
