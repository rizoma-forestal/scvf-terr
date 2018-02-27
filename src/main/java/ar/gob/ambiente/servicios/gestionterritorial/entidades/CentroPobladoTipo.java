
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
 * Entidad que encapsula la información relativa a los Tipos de Centros poblados
 * Ej: Ciudad, Localidad, Paraje, etc.
 * Se omite la documentación de los métodos get y set.
 * @author rincostante
 */
@XmlRootElement(name = "centropobladotipo")
@Entity
@Table(name = "centropobladotipo")
public class CentroPobladoTipo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    private String nombre;

    
    @OneToMany(mappedBy="centroPobladoTipo")
    private List<CentroPoblado> centrosPoblados;
    
    
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;    
    
    
    public CentroPobladoTipo(){
        centrosPoblados = new ArrayList();
    }

    /**
     * Método para obtener los Centros poblados vinculados al Tipo.
     * No disponible para la API rest
     * @return List<CentroPoblado> Listado de Centros poblados del Tipo en cuestión
     */
    @XmlTransient
    public List<CentroPoblado> getCentrosPoblados() {
        return centrosPoblados;
    }

    public void setCentrosPoblados(List<CentroPoblado> centrosPoblados) {
        this.centrosPoblados = centrosPoblados;
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
     * Método que devuelve la entidad administrativa del Tipo de Centro poblado
     * No disponible para la API rest
     * @return AdminEntidad entidad administrativa del Tipo de Centro poblado
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
        if (!(object instanceof CentroPobladoTipo)) {
            return false;
        }
        CentroPobladoTipo other = (CentroPobladoTipo) object;
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
        return "ar.gob.ambiente.servicios.gestionterritorial.entidades.CentroPobladoTipo[ id=" + id + " ]";
    }
    
}
