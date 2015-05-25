package modelo.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "configuracionconstancia", catalog = "constancias", schema = "")
@XmlRootElement
public class ConfiguracionConstancia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "fondoConstanciaAsistencia")
    private Boolean fondoConstanciaAsistencia;
    @Lob
    @Column(name = "archivoFondoConstanciaAsistencia")
    @Basic(fetch = FetchType.LAZY)
    private byte[] archivoFondoConstanciaAsistencia;
    @Column(name = "fondoConstanciaPonente")
    private Boolean fondoConstanciaPonente;
    @Lob
    @Column(name = "archivoFondoConstanciaPonente")
    @Basic(fetch = FetchType.LAZY)
    private byte[] archivoFondoConstanciaPonente;

    public ConfiguracionConstancia() {
    }

    public ConfiguracionConstancia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isFondoConstanciaAsistencia() {
        return fondoConstanciaAsistencia;
    }

    public void setFondoConstanciaAsistencia(Boolean fondoConstanciaAsistencia) {
        this.fondoConstanciaAsistencia = fondoConstanciaAsistencia;
    }

    public byte[] getArchivoFondoConstanciaAsistencia() {
        return archivoFondoConstanciaAsistencia;
    }

    public void setArchivoFondoConstanciaAsistencia(byte[] archivoFondoConstanciaAsistencia) {
        this.archivoFondoConstanciaAsistencia = archivoFondoConstanciaAsistencia;
    }

    public Boolean isFondoConstanciaPonente() {
        return fondoConstanciaPonente;
    }

    public void setFondoConstanciaPonente(Boolean fondoConstanciaPonente) {
        this.fondoConstanciaPonente = fondoConstanciaPonente;
    }

    public byte[] getArchivoFondoConstanciaPonente() {
        return archivoFondoConstanciaPonente;
    }

    public void setArchivoFondoConstanciaPonente(byte[] archivoFondoConstanciaPonente) {
        this.archivoFondoConstanciaPonente = archivoFondoConstanciaPonente;
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
        if (!(object instanceof ConfiguracionConstancia)) {
            return false;
        }
        ConfiguracionConstancia other = (ConfiguracionConstancia) object;
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
