/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author epassarelli
 */
@Entity
public class Region implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name="especificidadderegion_id")
    private EspecificidadDeRegion especificidadderegion;
    
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="adminentidad_id")
    private AdminEntidad adminentidad;    
   

    /**
     * Campo de tipo Array que contiene el conjunto de las provincias que contienen esta region
     */
    @ManyToMany
    @JoinTable(
            name = "regionesXProvincias",
            joinColumns = @JoinColumn(name = "region_fk"),
            inverseJoinColumns = @JoinColumn(name = "provincia_fk")
    )
    private List<Provincia> provincias;
        /**
     * Campo de texto que contiene la url a la informaciación general de la actividad en el centro documental
     */    
 //   @Column (nullable=false, length=200)
//    @NotNull(message = "Este campo es obligatorio")
//    @Size(message = "El campo no puede excederse de  los 200 caracteres", max = 200)
 //   private String verMas;       
        
    public Region(){
       provincias = new ArrayList<>();
    }

    
    public AdminEntidad getAdminentidad() {
        return adminentidad;
    }

    public void setAdminentidad(AdminEntidad adminentidad) {
        this.adminentidad = adminentidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    
    public EspecificidadDeRegion getEspecificidadderegion() {
        return especificidadderegion;
    }

    public void setEspecificidadderegion(EspecificidadDeRegion especificidadderegion) {
        this.especificidadderegion = especificidadderegion;
    }
    
    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    

    /**
     *
     * @return
     */
    @XmlTransient
    public List<Provincia> getProvincias() {
        return provincias;
    }

    /**
     *
     * @param provincias
     */
    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return "ar.gob.ambiente.servicios.gestionterritorial.entidades.Region[ id=" + id + " ]";
    }
 
}
