/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.servicios.gestionterritorial.managedBeans;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.EspecificidadDeRegion;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Usuario;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.gestionterritorial.facades.EspecificidadDeRegionFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
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
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;
import static sun.security.jgss.GSSUtil.login;

/**
 *
 * @author epassarelli
 */
public class MbEspRegion implements Serializable{
    
    private EspecificidadDeRegion current;
    private DataModel items = null;
    private List<EspecificidadDeRegion> listado;
    
    @EJB
    private EspecificidadDeRegionFacade espDeRegionFacade;
    
    //private PaginationHelper pagination;
    private int selectedItemIndex;
    private String selectParam;    
    private boolean iniciado;
    //private List<String> listaNombres;    
    private MbLogin login;
    private Usuario usLogeado;
    
    /*
     * Creates a new instance of MbEspRegion
     */
    public MbEspRegion() {
         
    }   

    @PostConstruct
    public void init(){
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        login = (MbLogin)ctx.getSessionMap().get("mbLogin");
        usLogeado = login.getUsLogeado();
    }    
   
    /********************************
     ** Getters y Setters ***********
     ********************************/     
    
    public EspecificidadDeRegion getCurrent() {
        return current;
    }

    public void setCurrent(EspecificidadDeRegion current) {
        this.current = current;
    }

    /**
     * @return La entidad gestionada
     */
    public EspecificidadDeRegion getSelected() {
        if (current == null) {
            current = new EspecificidadDeRegion();
            selectedItemIndex = -1;
        }
        return current;
    }   
    
    /**
     * @return el listado de entidades a mostrar en el list
     */
    /*
    public DataModel getItems() {
        if (items == null) {
            //items = getPagination().createPageDataModel();
            items = new ListDataModel(getFacade().findAll());
        }
        return items;
    }
    */
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
                    if(!s.equals("mbEspRegion")){
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
     * @return acción para el listado de entidades
     */
    public String prepareList() {
        recreateModel();
        return "list";
    }
    
    public String iniciarList(){
        String redirect = "";
        if(selectParam != null){
            redirect = "list";
        }else{
            redirect = "administracion/espRegion/list";
        }
        recreateModel();
        return redirect;
    }

    /**
     * @return acción para el detalle de la entidad
     */
    public String prepareView() {
        return "view";
    }

    /** (Probablemente haya que embeberlo con el listado para una misma vista)
     * @return acción para el formulario de nuevo
     */
    public String prepareCreate() {
        current = new EspecificidadDeRegion();
        return "new";
    }

    /**
     * @return acción para la edición de la entidad
     */
    public String prepareEdit() {
        return "edit";
    }
    
    public String prepareInicio(){
        recreateModel();
        return "/faces/index";
    }
    
    /**
     * Método para preparar la búsqueda
     * @return la ruta a la vista que muestra los resultados de la consulta en forma de listado
     */
    public String prepareSelect(){
        //items = null;
        return "list";
    }

    /**
     * 
     * @return 
     */
    public String habilitar(){
        try{
            // Actualización de datos de administración de la entidad
            Date date = new Date(System.currentTimeMillis());
            current.getAdminentidad().setFechaModif(date);
            current.getAdminentidad().setUsModif(usLogeado);
            current.getAdminentidad().setHabilitado(true);
            //current.getAdminentidad().setUsBaja();
            current.getAdminentidad().setFechaBaja(null);

            // Actualizo
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspRegionHabilitado"));
            return "view";
        }catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("EspRegionHabilitadoErrorOccured"));
            return null; 
        }
    }       

    /**
     * Método que deshabilita la entidad
     */
    private void deshabilitar() {
        try {
            // Actualización de datos de administración de la entidad
            Date date = new Date(System.currentTimeMillis());
            current.getAdminentidad().setFechaBaja(date);
            current.getAdminentidad().setUsBaja(usLogeado);
            current.getAdminentidad().setHabilitado(false);
            
            // Deshabilito la entidad
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspRegionDeshabilitar"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("EspRegionDeshabilitarErrorOccured"));
        }
    }     
    
    
    /**
     * Método para validar que no exista ya una entidad con este nombre al momento de crearla
     * @param arg0: vista jsf que llama al validador
     * @param arg1: objeto de la vista que hace el llamado
     * @param arg2: contenido del campo de texto a validar 
     */
    public void validarInsert(FacesContext arg0, UIComponent arg1, Object arg2){
        validarExistente(arg2);
    }
    
    /**
     * Método para validar que no exista una entidad con este nombre, siempre que dicho nombre no sea el que tenía originalmente
     * @param arg0: vista jsf que llama al validador
     * @param arg1: objeto de la vista que hace el llamado
     * @param arg2: contenido del campo de texto a validar 
     * @throws ValidatorException 
     */
    public void validarUpdate(FacesContext arg0, UIComponent arg1, Object arg2){
        if(!current.getNombre().equals((String)arg2)){
            validarExistente(arg2);
        }
    }
    
    private void validarExistente(Object arg2) throws ValidatorException{
        if(!getFacade().existe((String)arg2)){
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("CreateEspRegionExistente")));
        }
    }
    
    /**
     * Restea la entidad
     */
   
    private void recreateModel() {
        items = null;
        if(selectParam != null){
            selectParam = null;
        }
    }

    /*************************
    ** Métodos de operación **
    **************************/
    /**
     * @return 
     */
    
    public String create() {
        // Creación de la entidad de administración y asignación
        Date date = new Date(System.currentTimeMillis());
        AdminEntidad admEnt = new AdminEntidad();
        admEnt.setFechaAlta(date);
        admEnt.setHabilitado(true);
        admEnt.setUsAlta(usLogeado);
        current.setAdminentidad(admEnt);     

        listado.clear();
        listado = null;
        
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EspecificidadDeRegionCreated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("EspecificidadDeRegionCreatedErrorOccured"));
            return null;
        }
    }

    
    public String create() {
        try {
            if(getFacade().noExiste(current.getNombre(), current.getDepartamento())){
                // Creación de la entidad de administración y asignación
                Date date = new Date(System.currentTimeMillis());
                AdminEntidad admEnt = new AdminEntidad();
                admEnt.setFechaAlta(date);
                admEnt.setHabilitado(true);
                admEnt.setUsAlta(usLogeado);
                current.setAdminentidad(admEnt);
                
                CentroPobladoTipo cp = tipocpFacade.find(idTipo);
                current.setCentropobladotipo(cp);
                
                // Inserción
                getFacade().create(current);
           

    /**************************
    **    Métodos de selección     **
    **************************/
    /**
     * @return la totalidad de las entidades persistidas formateadas
     */
/*    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(espDeRegionFacade.findAll(), false);
    }
*/
    /**
     * @return de a una las entidades persistidas formateadas
     */
/*    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(espDeRegionFacade.findAll(), true);
    }
*/
    /**
     * @param id equivalente al id de la entidad persistida
     * @return la entidad correspondiente
     */
    public EspecificidadDeRegion getEspecificidadDeRegion(java.lang.Long id){
        return espDeRegionFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * @return el Facade
     */
    private EspecificidadDeRegionFacade getFacade() {
        return espDeRegionFacade;
    }
    
    /*
     * Métodos de búsqueda
     */
    public String getSelectParam() {
        return selectParam;
    }

    public void setSelectParam(String selectParam) {
        this.selectParam = selectParam;
    }
    
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = EspecificidadDeRegion.class)
    public static class EspecificidadDeRegionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbEspRegion controller = (MbEspRegion) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbEspRegion");
            return controller.getEspecificidadDeRegion(getKey(value));
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
            if (object instanceof EspecificidadDeRegion) {
                EspecificidadDeRegion o = (EspecificidadDeRegion) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EspecificidadDeRegion.class.getName());
            }
        }
    }        
}
