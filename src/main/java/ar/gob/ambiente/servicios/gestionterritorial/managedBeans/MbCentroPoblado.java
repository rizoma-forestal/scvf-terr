/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.servlet.http.HttpSession;


/**
 *
 * @author epassarelli
 */
public class MbCentroPoblado implements Serializable {

    private CentroPoblado current;
    private List<CentroPoblado> listado;
    private List<CentroPoblado> listadoFilter;

    @EJB
    private ProvinciaFacade provFacade;
    
    @EJB
    private DepartamentoFacade dptoFacade;

    @EJB
    private CentroPobladoTipoFacade tipocpFacade;
        
    @EJB
    private CentroPobladoFacade centroPobladoFacade;

    private List<Departamento> listaDepartamentos; 
    private List<CentroPobladoTipo> listaTiposCP;
    private List<Provincia> listaProvincias; 
    private boolean iniciado;
    private Provincia selectProvincia; 
    private Long idTipo;
    private Provincia provSelected;

    private MbLogin login;
    private Usuario usLogeado;    
    
    /**
     * Creates a new instance of MbCentroPoblado
     */
    public MbCentroPoblado() {
    }

    /**
     *
     */
    /*
    @PostConstruct
    private void init(){
        iniciado = false;
    } */
    
    @PostConstruct
    public void init(){
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        login = (MbLogin)ctx.getSessionMap().get("mbLogin");
        usLogeado = login.getUsLogeado();
    }
    /********************************
     ** Getters y Setters ***********
     ********************************/ 
    
    public Provincia getProvSelected() {
        return provSelected;
    }

    public void setProvSelected(Provincia provSelected) {
        this.provSelected = provSelected;
    }
 
    
    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }
 
    
    public CentroPoblado getCurrent() {
        return current;
    }

    public void setCurrent(CentroPoblado current) {
        this.current = current;
    }

    public List<CentroPoblado> getListado() {
        if (listado == null) {
            listado = getFacade().findAll();
        }        
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
                    if(!s.equals("mbCentroPoblado")){
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
     * @return La entidad gestionada
     */
    public CentroPoblado getSelected() {
        if (current == null) {
            current = new CentroPoblado();
        }
        return current;
    }

    /*******************************
     ** Métodos de inicialización **
     *******************************/
    /**
     * Método para inicializar el listado de los Participantes autorizados
     * @return acción para el listado de entidades
     */
    public String prepareList() {
        iniciado = true;
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
        //cargo los list para los combos
        listaTiposCP = tipocpFacade.getActivos();
        listaProvincias = provFacade.getActivos();
        //listaDepartamentos = dptoFacade.getActivos();
        current = new CentroPoblado();
        return "new";
    }    
    
    /**
     * @return acción para la edición de la entidad
     */
    public String prepareEdit() {
        //cargo los list para los combos
        listaTiposCP = tipocpFacade.getActivos();
        listaProvincias = provFacade.getActivos();
        listaDepartamentos = dptoFacade.getActivos();
        return "edit";
    }    
   
    /**
     * Método que verifica que el CentroPoblado que se quiere eliminar no esté siento utilizada por otra entidad
     * @return 
     */
    public String prepareDestroy(){
            performDestroy();
            recreateModel();
        return "view";
    }  
    
    /**
     * 
     * @return 
     */
    public String prepareHabilitar(){
        try{
            // Actualización de datos de administración de la entidad
            Date date = new Date(System.currentTimeMillis());
            current.getAdminentidad().setFechaModif(date);
            current.getAdminentidad().setUsModif(usLogeado);
            current.getAdminentidad().setHabilitado(true);
            current.getAdminentidad().setUsBaja(usLogeado);
            current.getAdminentidad().setFechaBaja(null);

            // Actualizo
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoHabilitado"));
            return "view";
        }catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoHabilitadoErrorOccured"));
            return null; 
        }
    }    
    
    
    /*************************
    ** Métodos de operación **
    **************************/
    /**
     * Método que inserta un nuevo Centro Poblado en la base de datos, previamente genera una entidad de administración
     * con los datos necesarios y luego se la asigna a la persona
     * @return mensaje que notifica la inserción
     */
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
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoCreated"));
                listaTiposCP.clear();
                listaProvincias.clear();
                
                listado.clear();
                listado = null;
                
                return "view";
            }else{
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoExistente"));
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoCreatedErrorOccured"));
            return null;
        }
    }    
    
     /**
     * Método que actualiza un nuevo Centro Poblado en la base de datos.
     * Previamente actualiza los datos de administración
     * @return mensaje que notifica la actualización
     */
    public String update() {    
        CentroPoblado cp;
        try {
            cp = getFacade().getExistente(current.getNombre(), current.getDepartamento());
            if(cp == null){
                // Actualización de datos de administración de la entidad
                Date date = new Date(System.currentTimeMillis());
                current.getAdminentidad().setFechaModif(date);
                current.getAdminentidad().setUsModif(usLogeado);
                
                // Actualizo
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoUpdated"));
                listaTiposCP.clear();
                listaProvincias.clear();
                
                listado.clear();
                listado = null;
                
                return "view";
            }else{
                if(cp.getId().equals(current.getId())){
                    // Actualización de datos de administración de la entidad
                    Date date = new Date(System.currentTimeMillis());
                    current.getAdminentidad().setFechaModif(date);
                    current.getAdminentidad().setUsModif(usLogeado);

                    // Actualizo
                    getFacade().edit(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoUpdated"));
                    listaTiposCP.clear();
                    listaProvincias.clear();
                    return "view";                   
                }else{
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoExistente"));
                    return null;
                }
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoUpdatedErrorOccured"));
            return null;
        }
    }   
    
    /**
     * @param id equivalente al id de la entidad persistida
     * @return la entidad correspondiente
     */
    public CentroPoblado getCentroPoblado(java.lang.Long id) {
        return getFacade().find(id);
    } 
    
    /**
     * Método para revocar la sesión del MB
     * @return 
     */
    public String cleanUp(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        session.removeAttribute("mbCentroPoblado");
     
        return "inicio";
    }     

    
    /**
     * Método que deshabilita la entidad
     */
    private void performDestroy() {
        try {
            // Actualización de datos de administración de la entidad
            Date date = new Date(System.currentTimeMillis());
            current.getAdminentidad().setFechaBaja(date);
            current.getAdminentidad().setUsBaja(usLogeado);
            current.getAdminentidad().setHabilitado(false);
            
            // Deshabilito la entidad
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoDeletedErrorOccured"));
        }
    }      
    
    /**
     * Método que deshabilita la entidad
     * @param event
     */    
    public void provinciaChangeListener(ValueChangeEvent event){
        provSelected = (Provincia)event.getNewValue();
        listaDepartamentos = dptoFacade.getPorProvincia(provSelected);
    }

    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * @return el Facade
     */
    private CentroPobladoFacade getFacade() {
        return centroPobladoFacade;
    }    
    
    /**
     * Restea la entidad
     */
    private void recreateModel() {
        idTipo = null;
        provSelected = null;
        listaDepartamentos.clear();
    }        
    
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = CentroPoblado.class)
    public static class CentroPobladoControllerConverter implements Converter {

        /**
         *
         * @param facesContext
         * @param component
         * @param value
         * @return
         */
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

        /**
         *
         * @param facesContext
         * @param component
         * @param object
         * @return
         */
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
