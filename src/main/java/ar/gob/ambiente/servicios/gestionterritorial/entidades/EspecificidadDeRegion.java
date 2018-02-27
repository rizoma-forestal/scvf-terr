
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Enditad que encapsula la información relativa a las especificidades de región de las distintas Regiones.
 * Se omite la documentación de los métodos get y set.
 * No disponible para la API rest
 * @author rincostante
 */
@XmlRootElement(name = "especificidadderegion")
@Entity
@Table(name = "especificidadderegion")
public class EspecificidadDeRegion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable privada: Identificador único
     */  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Variable privada: Nombre de la Especificidad de Región
     */ 
    private String nombre;
    
    /**
     * Variable privada: Listado de las Regiones del tipo de Especificidad en cuestión
     * No disponible para la API rest
     */
    @OneToMany(mappedBy="especificidadderegion")
    private List<Region> regiones;
    
    /**
     * Variable privada de tipo AdminEntidad: representa la entidad administrativa de la Especificidad de Región
     */ 
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;

    /**
     * Constructor
     */
    public EspecificidadDeRegion(){
        regiones = new ArrayList();
  
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

    /**
     * Método para obtener las Regiones vinculadas a la Especificidad.
     * No disponible para la API rest
     * @return List<Region> Listado de Regiones de la Especificidad en cuestión
     */        
    @XmlTransient
    public List<Region> getRegiones() {
        return regiones;
    }

    public void setRegiones(List<Region> regiones) {
        this.regiones = regiones;
    }
    
    /**
     * Método que devuelve la entidad administrativa de la Especificidad de Región
     * No disponible para la API rest
     * @return AdminEntidad entidad administrativa de la Especificidad de Región
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
        if (!(object instanceof EspecificidadDeRegion)) {
            return false;
        }
        EspecificidadDeRegion other = (EspecificidadDeRegion) object;
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
        return "ar.gob.ambiente.servicios.gestionterritorial.entidades.EspecificidadDeRegion[ id=" + id + " ]";
    }
    
}
