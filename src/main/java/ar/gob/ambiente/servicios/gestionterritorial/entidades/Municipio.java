
package ar.gob.ambiente.servicios.gestionterritorial.entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Enditad que encapsula la información relativa a los Municipios pertenecientes a las Provincias.
 * Se omite la documentación de los métodos get y set.
 * No disponible para la API rest
 * @author rincostante
 */
@XmlRootElement(name = "municipio")
@Entity
@Table(name = "municipio")
public class Municipio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable privada: Identificador único
     */  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Variable privada: Nombre del Municipio
     */  
    private String nombre;

    /**
     * Variable privada: Provincia a la que pertenece el Municipio
     */
    @ManyToOne
    @JoinColumn(name="provincia_id")
    private Provincia provincia;  
    
    /**
     * Variable privada: Departamento al que se vincula el Municipio
     */
    @ManyToOne
    @JoinColumn(name="departamento_id")
    private Departamento departamento;

    /**
     * Variable privada de tipo AdminEntidad: representa la entidad administrativa del Municipio
     */  
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad; 

    
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
     * Método para obtener el Departamento vinculado al Municipio.
     * No disponible para la API rest
     * @return Departamento Departamento del Municipio en cuestión
     */        
    @XmlTransient
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    /**
     * Método que devuelve la entidad administrativa del Municipio
     * No disponible para la API rest
     * @return AdminEntidad entidad administrativa del Municipio
     */        
    @XmlTransient
    public AdminEntidad getAdminentidad() {
        return adminentidad;
    }

    public void setAdminentidad(AdminEntidad adminentidad) {
        this.adminentidad = adminentidad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
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
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;
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
        return "ar.gob.ambiente.servicios.gestionterritorial.entidades.Municipio[ id=" + id + " ]";
    }
    
}

