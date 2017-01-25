/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 *
 * @author epassarelli
 */
public class MbCentroPobladoTipo  implements Serializable{
    
    private CentroPobladoTipo current;
    private DataModel items = null;
    private List<CentroPobladoTipo> listFilter;
    private List<CentroPoblado> listCentroPobladoFilter;
    
    @EJB
    private CentroPobladoTipoFacade centroPobladoTipoFacade;
    
    private int update; // 0=updateNormal | 1=deshabiliar | 2=habilitar
    private Usuario usLogeado;
    private boolean iniciado;
    private MbLogin login;
    
    /*
     * Creates a new instance of MbCentroPobladoTipo
     */
    public MbCentroPobladoTipo() {
         
    }   
    
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
     * @return La entidad gestionada
     */
    public CentroPobladoTipo getSelected() {
        if (current == null) {
            current = new CentroPobladoTipo();
        }
        return current;
    }   
    
    /**
     * @return el listado de entidades a mostrar en el list
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
     * @return acción para el listado de entidades
     */
    public String prepareList() {
        recreateModel();
        return "list";
    }

    /**
     * @return acción para el detalle de la entidad
     */
    public String prepareView() {
        return "view";
    }

    /**
     * @return acción para el formulario de nuevo
     */
    public String prepareCreate() {
        current = new CentroPobladoTipo();
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
    
     public void habilitar() {
        update = 2;
        update();        
        recreateModel();
    }  
     /**
     * @return 
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
     * @return mensaje que notifica la actualización
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
    **    Métodos de selección     **
    **************************/

    /**
     * @param id equivalente al id de la entidad persistida
     * @return la entidad correspondiente
     */
    public CentroPobladoTipo getCentroPobladoTipo(java.lang.Long id) {
        return centroPobladoTipoFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * @return el Facade
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
