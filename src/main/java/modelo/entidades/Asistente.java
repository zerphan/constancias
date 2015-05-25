package modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gandhi
 */
@Entity
@Table(name = "asistente", catalog = "constancias", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asistente.findAll", query = "SELECT a FROM Asistente a"),
    @NamedQuery(name = "Asistente.findAllByIdSeminario", query = "SELECT a FROM Asistente a WHERE a.seminario.idSeminario = :idSeminario ORDER BY a.apellidoPaterno"),
    @NamedQuery(name = "Asistente.findByIdAsistente", query = "SELECT a FROM Asistente a WHERE a.idAsistente = :idAsistente"),
    @NamedQuery(name = "Asistente.findByNombre", query = "SELECT a FROM Asistente a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Asistente.findByApellidoPaterno", query = "SELECT a FROM Asistente a WHERE a.apellidoPaterno = :apellidoPaterno"),
    @NamedQuery(name = "Asistente.findByApellidoMaterno", query = "SELECT a FROM Asistente a WHERE a.apellidoMaterno = :apellidoMaterno"),
    @NamedQuery(name = "Asistente.findByEmail", query = "SELECT a FROM Asistente a WHERE a.email = :email"),
    @NamedQuery(name = "Asistente.findByNoBoleta", query = "SELECT a FROM Asistente a WHERE a.noBoleta = :noBoleta")})
public class Asistente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAsistente", nullable = false)
    private Integer idAsistente;
    @Size(max = 32)
    @Column(name = "nombre", length = 32)
    private String nombre;
    @Size(max = 32)
    @Column(name = "apellidoPaterno", length = 32)
    private String apellidoPaterno;
    @Size(max = 32)
    @Column(name = "apellidoMaterno", length = 32)
    private String apellidoMaterno;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Email invalido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 32)
    @Column(name = "email", length = 32)
    private String email;
    @Size(max = 10)
    @Column(name = "noBoleta", length = 10)
    private String noBoleta;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Column(name = "archivoConstancia")
    @Basic(fetch = FetchType.LAZY)
    private byte[] archivoConstancia;
    @JoinColumn(name = "Seminario", referencedColumnName = "idSeminario")
    @ManyToOne
    private Seminario seminario;

    public Asistente() {
    }

    public Asistente(Integer idAsistente) {
        this.idAsistente = idAsistente;
    }

    public Integer getIdAsistente() {
        return idAsistente;
    }

    public void setIdAsistente(Integer idAsistente) {
        this.idAsistente = idAsistente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getArchivoConstancia() {
        return archivoConstancia;
    }

    public void setArchivoConstancia(byte[] archivoConstancia) {
        this.archivoConstancia = archivoConstancia;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoBoleta() {
        return noBoleta;
    }

    public void setNoBoleta(String noBoleta) {
        this.noBoleta = noBoleta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }   

    public Seminario getSeminario() {
        return seminario;
    }

    public void setSeminario(Seminario seminario) {
        this.seminario = seminario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsistente != null ? idAsistente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistente)) {
            return false;
        }
        Asistente other = (Asistente) object;
        if ((this.idAsistente == null && other.idAsistente != null) || (this.idAsistente != null && !this.idAsistente.equals(other.idAsistente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.Asistente[ idAsistente=" + idAsistente + " ]";
    }
    
}