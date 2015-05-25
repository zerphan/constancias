package modelo.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gandhi
 */
@Entity
@Table(name = "gradoacademico", catalog = "constancias", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GradoAcademico.findAll", query = "SELECT g FROM GradoAcademico g"),
    @NamedQuery(name = "GradoAcademico.findById", query = "SELECT g FROM GradoAcademico g WHERE g.id = :id"),
    @NamedQuery(name = "GradoAcademico.findByNombre", query = "SELECT g FROM GradoAcademico g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "GradoAcademico.findByAbreviacion", query = "SELECT g FROM GradoAcademico g WHERE g.abreviacion = :abreviacion")})
public class GradoAcademico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 32)
    @Column(name = "nombre", length = 32)
    private String nombre;
    @Size(max = 16)
    @Column(name = "abreviacion", length = 16)
    private String abreviacion;
    @OneToMany(mappedBy = "gradoAcademico")
    private List<Ponente> ponenteList;
    @OneToMany(mappedBy = "gradoAcademico")
    private List<Acreditador> acreditadorList;

    public GradoAcademico() {
    }

    public GradoAcademico(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    @XmlTransient
    public List<Ponente> getPonenteList() {
        return ponenteList;
    }

    public void setPonenteList(List<Ponente> ponenteList) {
        this.ponenteList = ponenteList;
    }

    @XmlTransient
    public List<Acreditador> getAcreditadorList() {
        return acreditadorList;
    }

    public void setAcreditadorList(List<Acreditador> acreditadorList) {
        this.acreditadorList = acreditadorList;
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
        if (!(object instanceof GradoAcademico)) {
            return false;
        }
        GradoAcademico other = (GradoAcademico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + "";
    }

}
