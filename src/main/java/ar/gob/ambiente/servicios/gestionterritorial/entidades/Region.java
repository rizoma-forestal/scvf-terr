
package ar.gob.ambiente.servicios.gestionterritorial.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Enditad que encapsula la información relativa a las Regiones según usos o características específicas.
 * Cada Región pudede avarcar una o más Provincias de modo total o parcial.
 * Se omite la documentación de los métodos get y set.
 * No disponible para la API rest
 * @author rincostante
 */
@XmlRootElement(name = "region")
@Entity
@Table(name = "region")
public class Region implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable privada: Identificador único
     */  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        
    /**
     * Variable privada: Nombre de la Región
     */   
    @Column (nullable=false, length=200, unique=true)
    @NotNull(message = "{entidades.fieldNotNullError}")
    @Size(message = "{endidades.stringSizeError}", min = 1, max = 200)
    private String nombre;
    
    /**
     * Variable privada: de tipo EspecificidadDeRegion que indica el uso o característica específica de la región
     */
    @ManyToOne 
    @JoinColumn(name="especificidadderegion_id")
    private EspecificidadDeRegion especificidadderegion;
    
    /**
     * Variable privada de tipo AdminEntidad: representa la entidad administrativa de la Región
     */      
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;    

    /**
     * Variable privada: contiene el conjunto de las provincias vinculadas a esta Región
     */
    @ManyToMany
    @JoinTable(
            name = "regionesXProvincias",
            joinColumns = @JoinColumn(name = "region_fk"),
            inverseJoinColumns = @JoinColumn(name = "provincia_fk")
    )
    private List<Provincia> provincias;

    /**
     * Constructor
     */
    public Region(){
        provincias = new ArrayList();
    }

    public EspecificidadDeRegion getEspecificidadderegion() {
        return especificidadderegion;
    }

    public void setEspecificidadderegion(EspecificidadDeRegion especificidadderegion) {
        this.especificidadderegion = especificidadderegion;
    }

    /**
     * Método para obtener las Provincias vinculadas a la Región.
     * @return List<Provincia> Listado de Provincias de la Región en cuestión
     */     
    @XmlTransient
    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que devuelve la entidad administrativa de la Región
     * @return AdminEntidad entidad administrativa de la Región
     */  
    @XmlTransient
    public AdminEntidad getAdminentidad() {
        return adminentidad;
    }

    public void setAdminentidad(AdminEntidad adminentidad) {
        this.adminentidad = adminentidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Region)) {
            return false;
        }
        Region other = (Region) object;
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
        return "ar.gob.ambiente.servicios.gestionterritorial.entidades.Region[ id=" + id + " ]";
    }
    
}
