
package ar.gob.ambiente.servicios.gestionterritorial.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Enditad que encapsula la información relativa a los Departamentos pertenecientes a las Provincias.
 * Se omite la documentación de los métodos get y set.
 * @author rincostante
 */
@XmlRootElement(name = "departamento")
@Entity
@Table(name = "departamento")
public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable privada: Identificador único
     */  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Variable privada: Nombre del Departamento
     */    
    private String nombre;

    /**
     * Variable privada: Provincia a la que pertenece el Departamento
     */
    @ManyToOne
    @JoinColumn(name="provincia_id")
    private Provincia provincia;

    /**
     * Variable privada: Listado de los Centros poblados contenidos por el Departamento
     * No disponible para la API rest
     */
    @OneToMany(mappedBy="departamento")
    private List<CentroPoblado> centrosPoblados;    

    /**
     * Variable privada: Listado de los Municipios contenidos por el Departamento
     * No disponible para la API rest
     */
    @OneToMany(mappedBy="departamento")
    private List<Municipio> municipios;     

    /**
     * Variable privada de tipo AdminEntidad: representa la entidad administrativa del Departamento
     */    
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad; 

    /**
     * Constructor
     */
    public Departamento(){
        centrosPoblados = new ArrayList();
        municipios = new ArrayList();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    
    
    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    /**
     * Método para obtener los Centros poblados vinculados al Departamento.
     * No disponible para la API rest
     * @return List<CentroPoblado> Listado de Centros poblados del Departamento en cuestión
     */    
    @XmlTransient
    public List<CentroPoblado> getCentrosPoblados() {
        return centrosPoblados;
    }

    public void setCentrosPoblados(List<CentroPoblado> centrosPoblados) {
        this.centrosPoblados = centrosPoblados;
    }
    
    /**
     * Método para obtener los Municipios vinculados al Departamento.
     * No disponible para la API rest
     * @return List<Municipio> Listado de Municipios del Departamento en cuestión
     */      
    @XmlTransient
    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }    
    
    /**
     * Método que devuelve la entidad administrativa del Departamento
     * No disponible para la API rest
     * @return AdminEntidad entidad administrativa del Departamento
     */    
    @XmlTransient
    public AdminEntidad getAdminentidad() {
        return adminentidad;
    }

    public void setAdminentidad(AdminEntidad adminentidad) {
        this.adminentidad = adminentidad;
    }
   
    /**
     * Método que crea un hash con a partir de la id de la entidad
     * @return int Un entero con el hash
     */ 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Método que compara una instancia de esta entidad con otra según su id
     * @param object La instancia de entidad a comparar con la presente
     * @return boolean Verdadero si son iguales, falso si son distintas
     */         
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Método que devuelve un String con el id de la entidad
     * @return String id de la entidad en formato String
     */       
    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.gestionterritorial.entidades.Departamento[ id=" + id + " ]";
    }
    public AdminEntidad getAdminentidad(AdminEntidad admEnt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
    
}
