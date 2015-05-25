package modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gandhi
 */
@Entity
@Table(name = "acreditador", catalog = "constancias", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acreditador.findAll", query = "SELECT a FROM Acreditador a"),
    @NamedQuery(name = "Administrador.findByDescripcion", query = "SELECT a FROM Acreditador a WHERE a.descripcion = :descripcion")
})
public class Acreditador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 128)
    @Column(name = "descripcion", length = 128)
    private String descripcion;
    @Size(max = 64)
    @Column(name = "nombre", length = 64)
    private String nombre;
    @Size(max = 32)
    @Column(name = "apellidoPaterno", length = 32)
    private String apellidoPaterno;
    @Size(max = 32)
    @Column(name = "apellidoMaterno", length = 32)
    private String apellidoMaterno;
    @Column(name = "fechaTermino")
    @Temporal(TemporalType.DATE)
    private Date fechaTermino;
    @JoinColumn(name = "gradoacademico", referencedColumnName = "id")
    @ManyToOne
    private GradoAcademico gradoAcademico;

    @OneToMany(mappedBy = "acreditador1")
    private List<Seminario> seminarioList1;

    @OneToMany(mappedBy = "acreditador2")
    private List<Seminario> seminarioList2;

    public Acreditador() {
    }

    public Acreditador(Integer id) {
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

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public String getNombreCompleto() {
        String nombre = "";
        nombre += "" + getGradoAcademico().getAbreviacion();
        nombre += " " + this.nombre;
        nombre += " " + this.apellidoPaterno;
        nombre += " " + this.apellidoMaterno;
        return nombre;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public GradoAcademico getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(GradoAcademico gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Seminario> getSeminarioList1() {
        return seminarioList1;
    }

    public void setSeminarioList1(List<Seminario> seminarioList1) {
        this.seminarioList1 = seminarioList1;
    }

    public List<Seminario> getSeminarioList2() {
        return seminarioList2;
    }

    public void setSeminarioList2(List<Seminario> seminarioList2) {
        this.seminarioList2 = seminarioList2;
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
        if (!(object instanceof Acreditador)) {
            return false;
        }
        Acreditador other = (Acreditador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + id;
    }

}
