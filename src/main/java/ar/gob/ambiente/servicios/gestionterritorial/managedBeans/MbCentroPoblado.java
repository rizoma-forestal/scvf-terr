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
import ar.gob.ambiente.servicios.gestionterritorial.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.gestionterritorial.facades.CentroPobladoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.CentroPobladoTipoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.DepartamentoFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.ProvinciaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author epassarelli
 */
public class MbCentroPoblado implements Serializable {

    private CentroPoblado current;
    private DataModel items = null;

    @EJB
    private ProvinciaFacade provFacade;
    
    @EJB
    private DepartamentoFacade dptoFacade;

    @EJB
    private CentroPobladoTipoFacade tipocpFacade;
        
    @EJB
    private CentroPobladoFacade locFacade;

    private int selectedItemIndex;
    private String selectParam;    
    private List<String> listaNombres;     
    private List<Departamento> listaDepartamentos; 
    private List<Departamento> comboDepartamentos;
    private List<CentroPobladoTipo> listaTiposCP;


    private List<Provincia> listaProvincias; 
    
    private Provincia selectProvincia; 

    
    /**
     * Creates a new instance of MbCentroPoblado
     */
    public MbCentroPoblado() {
    }

   @PostConstruct
   public void init(){
        listaProvincias = provFacade.findAll();
        listaDepartamentos = dptoFacade.findAll();
        listaTiposCP = tipocpFacade.findAll();
   }


    /*
     ** Getters y Setters *********** 
    */   
 
    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
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
    
    public List<Departamento> getComboDepartamentos() {
        return comboDepartamentos;
    }

    public void setComboDepartamentos(List<Departamento> comboDepartamentos) {
        this.comboDepartamentos = comboDepartamentos;
    }   

    public List<CentroPobladoTipo> getListaTiposCP() {
        return listaTiposCP;
    }

    public void setListaTiposCP(List<CentroPobladoTipo> listaTiposCP) {
        this.listaTiposCP = listaTiposCP;
    }
 
   
    /********************************
     ** Métodos para la navegación **
     ********************************/
    /**
     * @return La entidad gestionada
     */
    public CentroPoblado getSelected() {
        if (current == null) {
            current = new CentroPoblado();
            selectedItemIndex = -1;
        }
        return current;
    }   
    
    /**
     * @return el listado de entidades a mostrar en el list
     */
    public DataModel getItems() {
        if (items == null) {
            //items = getPagination().createPageDataModel();
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
    
    public String iniciarList(){
        String redirect = "";
        if(selectParam != null){
            redirect = "list";
        }else{
            redirect = "administracion/centropoblado/list";
        }
        recreateModel();
        return redirect;
    }

    /**
     * @return acción para el detalle de la entidad
     */
    public String prepareView() {
        current = (CentroPoblado) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        return "view";
    }

    /** (Probablemente haya que embeberlo con el listado para una misma vista)
     * @return acción para el formulario de nuevo
     */
    public String prepareCreate() {
        //listaEspecificidadesDeRegion = espRegionFacade.getActivos();
        listaProvincias = provFacade.getActivos();
        listaTiposCP = tipocpFacade.getActivos();
        current = new CentroPoblado();
        selectedItemIndex = -1;
        return "new";
    }

    /**
     * @return acción para la edición de la entidad
     */
    public String prepareEdit() {
        current = (CentroPoblado) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        selectedItemIndex = getItems().getRowIndex();
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
        items = null;
        buscarCentroPoblado();
        return "list";
    }
    
    /**
     * Método que verifica que el Tipo de Capacitación que se quiere eliminar no esté siento utilizado por otra entidad
     * @return 
     */
    public String prepareDestroy(){
        current = (CentroPoblado) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        performDestroy();
        recreateModel();     
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
            throw new ValidatorException(new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("CreateCentroPobladoExistente")));
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
        admEnt.setUsAlta(2);
        current.setAdminentidad(admEnt);        
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoCreated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoCreatedErrorOccured"));
            return null;
        }
    }

    /**
     * @return mensaje que notifica la actualización
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoUpdated"));
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoUpdatedErrorOccured"));
            return null;
        }
    }

    /**
     * @return mensaje que notifica el borrado
     */    
    public String destroy() {
        current = (CentroPoblado) getItems().getRowData();
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        selectedItemIndex = getItems().getRowIndex();
        performDestroy();
        //recreatePagination();
        recreateModel();
        return "view";
    }

    /**
     * @return mensaje que notifica la inserción
     */
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "view";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "list";
        }
    }    
    
  
    
    /*************************
    ** Métodos de selección **
    **************************/
    /**
     * @return la totalidad de las entidades persistidas formateadas
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(locFacade.findAll(), false);
    }

    /**
     * @return de a una las entidades persistidas formateadas
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(locFacade.findAll(), true);
    }

    /**
     * @param id equivalente al id de la entidad persistida
     * @return la entidad correspondiente
     */
    public CentroPoblado getCentroPoblado(java.lang.Long id) {
        return locFacade.find(id);
    }    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * @return el Facade
     */
    private CentroPobladoFacade getFacade() {
        return locFacade;
    }
    
    /**
     * Opera el borrado de la entidad
     */
    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CentroPobladoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("CentroPobladoDeletedErrorOccured"));
        }
    }

    /**
     * Actualiza el detalle de la entidad si la última se eliminó
     */
    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            /*
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
            */
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
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
    
    private void buscarCentroPoblado(){
        items = new ListDataModel(getFacade().getXString(selectParam)); 
    }   
    
    /**
     * Método para llegar la lista para el autocompletado de la búsqueda de nombres
     * @param query
     * @return 
     */
    public List<String> completeNombres(String query){
        listaNombres = getFacade().getNombres();
        List<String> nombres = new ArrayList();
        Iterator itLista = listaNombres.listIterator();
        while(itLista.hasNext()){
            String nom = (String)itLista.next();
            if(nom.contains(query)){
                nombres.add(nom);
            }
        }
        return nombres;
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

    public CentroPoblado getCurrent() {
        return current;
    }

    public void setCurrent(CentroPoblado current) {
        this.current = current;
    }
        
    /**
     * 
     * @param event
     * Metodo que recibe como parametro una provincia y carga los Departamentos relacionados a la misma
     * 
     */
    
    public void departamentoChangeListener(ValueChangeEvent event) {
        selectProvincia = (Provincia)event.getNewValue();
        
        comboDepartamentos = new ArrayList();
        Iterator itRows = listaDepartamentos.iterator();
        
        // recorro el datamodel
        while(itRows.hasNext()){
            Departamento dpto = (Departamento)itRows.next();
            if ( ( dpto.getProvincia().equals(selectProvincia) ) && (dpto.getAdminentidad().isHabilitado()) ){
                comboDepartamentos.add(dpto);
            }          
        }        
    }
     public String habilitar() {
        current.getAdminentidad().setHabilitado(true);
        update();        
        recreateModel();
        return "view";
    }  

    /**
     * @return mensaje que notifica la actualizacion de estado
     */    
    public String deshabilitar() {
            current.getAdminentidad().setHabilitado(false);
            update();        
            recreateModel();
        return "view";
    }     
    
}
