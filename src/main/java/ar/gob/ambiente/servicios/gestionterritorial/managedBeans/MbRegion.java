
package ar.gob.ambiente.servicios.gestionterritorial.managedBeans;

import ar.gob.ambiente.servicios.gestionterritorial.entidades.AdminEntidad;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.EspecificidadDeRegion;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Provincia;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Region;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.Usuario;
import ar.gob.ambiente.servicios.gestionterritorial.entidades.util.JsfUtil;
import ar.gob.ambiente.servicios.gestionterritorial.facades.EspecificidadDeRegionFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.ProvinciaFacade;
import ar.gob.ambiente.servicios.gestionterritorial.facades.RegionFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;


/**
 * Bean de respaldo para la gestión de Región
 * @author rincostante
 */
public class MbRegion implements Serializable{

    /**
     * Variable privada: Region Entidad que se gestiona mediante el bean
     */
    private Region current;
    
    /**
     * Variable privada: List<Provincia> listado de Provincias vinculadas con la Región
     */
    private List<Provincia> provVinc;
    
    /**
     * Variable privada: List<Provincia> listado para el filtrado de la tabla de Provincias
     */
    private List<Provincia> provVincFilter;
    
    /**
     * Variable privada: List<Provincia> listado para la selección de Provincias disponibles
     */
    private List<Provincia> provDisp;
    
    /**
     * Variable privada: List<Provincia> listado para la el filtrado de la table de selección de Provincias disponibles
     */
    private List<Provincia> provDispFilter;
    
    /**
     * Variable privada: List<Provincia> listado de Provincias para vincular a la región
     */
    private List<Provincia> provincias;
    
    /**
     * Variable privada: List<Provincia> listado para filtrar el listado de Provincias a vincular
     */
    private List<Provincia> provinciasFilter;
    
    /**
     * Variable privada: List<Provincia> listado de Provincias
     */
    private List<Provincia> listProvincias;
    
    /**
     * Variable privada: boolean indica si se asigna una Provincia a una Región
     */
    private boolean asignaProvincia; 
    
    /**
     * Variable privada: List<Region> listado de las Regiones existentes
     */
    private List<Region> listRegion;  

    /**
     * Variable privada: EJB inyectado para el acceso a datos de Region
     */
    @EJB
    private RegionFacade regionFacade;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de Provincia
     */
    @EJB
    private ProvinciaFacade provFacade;
    
    /**
     * Variable privada: EJB inyectado para el acceso a datos de EspecificidadDeRegion
     */
    @EJB
    private EspecificidadDeRegionFacade espRegionFacade;
    
    /**
     * Variable privada: Region se setea con la Región seleccionada
     */
    private Region regionSelected;
    
    /**
     * Variable privada: Usuario usuario logeado
     */
    private Usuario usLogeado;
    
    /**
     * Variable privada: List<EspecificidadDeRegion> listado de las Especificidades de Región disponibles
     */
    private List<EspecificidadDeRegion> listaEspecificidadDeRegion;
    
    /**
     * Variable privada: MbLogin bean de gestión de la sesión del usuario
     */
    private MbLogin login;   
    
    /**
     * Variable privada: Usuario usuario logeado
     */
    private boolean iniciado;


    /**
     * Constructor
     */
    public MbRegion() {
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
    
    /********************************
     ** Getters y Setters ***********
     ********************************/
    
    public List<Provincia> getProvVincFilter() {
        return provVincFilter;
    }

    public void setProvVincFilter(List<Provincia> provVincFilter) {
        this.provVincFilter = provVincFilter;
    }

    public List<Provincia> getProvDispFilter() {
        return provDispFilter;
    }

    public void setProvDispFilter(List<Provincia> provDispFilter) {
        this.provDispFilter = provDispFilter;
    }
    
    public List<Provincia> getProvinciasFilter() {
        return provinciasFilter;
    }

    public void setProvinciasFilter(List<Provincia> provinciasFilter) {
        this.provinciasFilter = provinciasFilter;
    }

    public List<Region> getListRegion() {
        if(listRegion == null){
            listRegion = getFacade().getHabilitadas();
        }
        return listRegion;
    }
    
    public void setListRegion(List<Region> listRegion) {
        this.listRegion = listRegion;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public List<Provincia> getListProvincias() {
        return listProvincias;
    }

    public void setListProvincias(List<Provincia> listProvincias) {
        this.listProvincias = listProvincias;
    }

    public boolean isAsignaProvincia() {
        return asignaProvincia;
    }

    public void setAsignaProvincia(boolean asignaProvincia) {
        this.asignaProvincia = asignaProvincia;
    }

    public List<Provincia> getProvVinc() {
        return provVinc;
    }

    public void setProvVinc(List<Provincia> provVinc) {
        this.provVinc = provVinc;
    }

    public List<Provincia> getProvDisp() {
        return provDisp;
    }

    public void setProvDisp(List<Provincia> provDisp) {
        this.provDisp = provDisp;
    }

    public List<EspecificidadDeRegion> getListaEspecificidadDeRegion() {
        return listaEspecificidadDeRegion;
    }

    public void setListaEspecificidadDeRegion(List<EspecificidadDeRegion> listaEspecificidadDeRegion) {
        this.listaEspecificidadDeRegion = listaEspecificidadDeRegion;
    }

    public Region getRegionSelected() {
        return regionSelected;
    }

    public void setRegionSelected(Region regionSelected) {
        this.regionSelected = regionSelected;
    }

    public Usuario getUsLogeado() {
        return usLogeado;
    }

    public void setUsLogeado(Usuario usLogeado) {
        this.usLogeado = usLogeado;
    }
    
 
    /********************************
     ** Métodos para el datamodel **
     ********************************/
    /**
     * Método que setea la entidad a gestionar
     * @return Region La entidad gestionada
     */
    public Region getSelected() {
        if (current == null) {
            current = new Region();
        }
        return current;
    }    
    
    
    /*******************************
     ** Métodos de inicialización **
     *******************************/
    /**
     * Método para inicializar el listado de las Regiones
     * @return String nombre de la vista a mostrar
     */
    public String prepareList() {
        iniciado = true;
        asignaProvincia = false;
        recreateModel();
        if(provVinc != null){
            provVinc.clear();
        }
        if(provDisp != null){
            provDisp.clear();
        }
        return "list";
    } 
    
    public String prepareListaDes() {
        recreateModel();
        asignaProvincia = false;
        if(provVinc != null){
            provVinc.clear();
        }
        if(provDisp != null){
            provDisp.clear();
        }
        return "listaDes";
    }     
    
    /**
     * Método para inicializar la vista detalle
     * @return String nombre de la vista a mostrar
     */
    public String prepareView() {
        asignaProvincia = false;
        current = regionSelected;
        provVinc = current.getProvincias();
        return "view";
    }
    
    public String prepareViewDes() {
        asignaProvincia = false;
        current = regionSelected;
        provVinc = current.getProvincias();
        return "viewDes";
    }

    /**
     * Método para inicializar la vista de creación de una entidad
     * @return String nombre de la vista a mostrar
     */
    public String prepareCreate() {
        // cargo los list para el combo de Especificidad De Region
        listaEspecificidadDeRegion = espRegionFacade.getActivos();
     
        // cargo la tabla de Provincias
        provincias = provFacade.getActivos();

        current = new Region();
        return "new";
    }

    /**
     * Método para inicializar la vista de actualización de una entidad
     * @return String nombre de la vista a mostrar
     */
    public String prepareEdit() {
        //cargo los list para los combos
        listaEspecificidadDeRegion = espRegionFacade.getActivos();
        current = regionSelected;
        asignaProvincia = true;
        provVinc = current.getProvincias();
        provDisp = cargarProvinciasDisponibles();
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
     * Método para inicializar la deshabilitación de una Región
     * @return String ruta a la vista detalle
     */      
    public String prepareDestroy(){
        current = regionSelected;
        boolean libre = getFacade().getUtilizado(current.getId());

        if (libre){
            // Elimina
            performDestroy();
            recreateModel();
        }else{
            //No Elimina 
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RegionNonDeletable"));
        }
        provVinc = current.getProvincias();
        return "view";
    }  
    
    /**
     * Método para inicializar la habilitación de una Región
     * @return String ruta a la vista detalle
     */ 
    public String prepareHabilitar(){
        current = regionSelected;
        try{
            // Actualización de datos de administración de la entidad
            Date date = new Date(System.currentTimeMillis());
            current.getAdminentidad().setFechaModif(date);
            current.getAdminentidad().setUsModif(usLogeado);
            current.getAdminentidad().setHabilitado(true);
            current.getAdminentidad().setUsBaja(null);
            current.getAdminentidad().setFechaBaja(null);

            // Actualizo
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegionHabilitada"));
            provVinc = current.getProvincias();
            return "view";
        }catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("RegionHabilitadaErrorOccured"));
            return null; 
        }
    }     

    /*************************
    ** Métodos de operación **
    **************************/
    /**
     * Método que inserta una nueva provincia en la base de datos, previamente genera una entidad de administración
     * con los datos necesarios y luego se la asigna a la región
     * @return mensaje que notifica la inserción
     */
    public String create() {
        if(current.getProvincias().isEmpty()){
            JsfUtil.addSuccessMessage("La Región que está guardando debe estar vinculada, al menos, a una Provincia.");
            return null;
        }else{
            try {
                if(getFacade().noExiste(current.getNombre(), current.getEspecificidadderegion())){
                    // Creación de la entidad de administración y asignación
                    Date date = new Date(System.currentTimeMillis());
                    AdminEntidad admEnt = new AdminEntidad();
                    admEnt.setFechaAlta(date);
                    admEnt.setHabilitado(true);
                    admEnt.setUsAlta(usLogeado);
                    current.setAdminentidad(admEnt);

                    // Inserción
                    getFacade().create(current);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegionCreated"));
                    listaEspecificidadDeRegion.clear();
                    provincias.clear();
                    provVinc = current.getProvincias();
                    return "view";
                }else{
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RegionExistente"));
                    return null;
                }
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("RegionCreatedErrorOccured"));
                return null;
            }
        }
    }

    /**
     * Método que actualiza una nueva Provincia en la base de datos.
     * Previamente actualiza los datos de administración
     * @return mensaje que notifica la actualización
     */
    public String update() {    
        boolean edito;
        Region sub;
        try {
            sub = getFacade().getExistente(current.getNombre(), current.getEspecificidadderegion());
            if(sub == null){
                edito = true;  
            }else{
                edito = sub.getId().equals(current.getId());
            }
            if(edito){
                // Actualización de datos de administración de la entidad
                Date date = new Date(System.currentTimeMillis());
                current.getAdminentidad().setFechaModif(date);
                current.getAdminentidad().setUsModif(usLogeado);

                // Actualizo
                getFacade().edit(current);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegionUpdated"));
                asignaProvincia = false;
                provDisp.clear();
                listaEspecificidadDeRegion.clear(); 
                return "view";
            }else{
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RegionExistente"));
                return null; 
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("RegionUpdatedErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = regionSelected;
        performDestroy();
        recreateModel();
        return "view";
    }    
    
    /*************************
    ** Métodos de selección **
    **************************/

    /**
     * Método que recupera un Región según su id
     * @param id Long id de la entidad persistida
     * @return Region la entidad correspondiente
     */
    public Region getRegion(java.lang.Long id) {
        return getFacade().find(id);
    }  
    
    public String cleanUp(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        session.removeAttribute("mbRegion");

        return "inicio";
    }  
    
    /**
     * Método que lista las provincias disponibles
     */
    public void verProvincias(){
        provincias = current.getProvincias();
        Map<String,Object> options = new HashMap<>();
        options.put("contentWidth", 950);
        RequestContext.getCurrentInstance().openDialog("", options, null);
    }       

    /**
     * Método que asigna una provincia a la Región
     * @param prov Provincia Provincia a asignar
     */
    public void asignarProvincia(Provincia prov){
        provVinc.add(prov);
        provDisp.remove(prov);
        if(provVincFilter != null){
            provVincFilter = null;
        }
        if(provDispFilter != null){
            provDispFilter = null;
        }
    }

    /**
     * Método que desvincula una provincia a la Región
     * @param prov Provincia Provincia a desvincular
     */    
    public void quitarProvincia(Provincia prov){
        provVinc.remove(prov);
        provDisp.add(prov);
        if(provVincFilter != null){
            provVincFilter = null;
        }
        if(provDispFilter != null){
            provDispFilter = null;
        }
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
    
    
    /*********************
    ** Métodos privados **
    **********************/
    /**
     * Método privado que devuelve el facade para el acceso a datos de las Provincias
     * @return EJB RegionFacade Acceso a datos
     */
    private RegionFacade getFacade() {
        return regionFacade;
    }    
    
    /**
     * Método que restea la entidad
     */
    private void recreateModel() {
        listRegion.clear();
        listRegion = null;
        if(provVincFilter != null){
            provVincFilter = null;
        }
        if(provDispFilter != null){
            provDispFilter = null;
        }
        if(provinciasFilter != null){
            provinciasFilter = null;
        }
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RegionDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("RegionDeletedErrorOccured"));
        }
    }             
    
    private List<Provincia> cargarProvinciasDisponibles(){
        List<Provincia> provs = provFacade.getActivos();
        List<Provincia> provsSelect = new ArrayList();
        Iterator itProvs = provs.listIterator();
        while(itProvs.hasNext()){
            Provincia prov = (Provincia)itProvs.next();
            if(!provVinc.contains(prov)){
                provsSelect.add(prov);
            }
        }
        return provsSelect;
    }
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = Region.class)
    public static class RegionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbRegion controller = (MbRegion) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbRegion");
            return controller.getRegion(getKey(value));
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
            if (object instanceof Region) {
                Region o = (Region) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Region.class.getName());
            }
        }
    }                 
}
 

  