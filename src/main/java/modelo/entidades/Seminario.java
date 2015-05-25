package modelo.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.joda.time.DateTime;

/**
 *
 * @author Gandhi
 */
@Entity
@Table(name = "seminario", catalog = "constancias", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seminario.findAll", query = "SELECT s FROM Seminario s"),
    @NamedQuery(name = "Seminario.findSeminariosDespuesUnaFecha", query = "SELECT s FROM Seminario s WHERE s.fechaInicio > :fecha"),
    @NamedQuery(name = "Seminario.findSeminariosEnEjecucion", query = "SELECT s FROM Seminario s WHERE  s.fechaInicio < CURRENT_TIMESTAMP AND s.fechaTermino >  CURRENT_TIMESTAMP"),
    @NamedQuery(name = "Seminario.findSeminariosConcluidos", query = "SELECT s FROM Seminario s WHERE s.fechaTermino <  CURRENT_TIMESTAMP ORDER BY s.fechaInicio DESC"),
    @NamedQuery(name = "Seminario.findSeminariosProximos", query = "SELECT s FROM Seminario s WHERE s.fechaTermino > CURRENT_TIMESTAMP order by s.fechaTermino"),
    @NamedQuery(name = "Seminario.findByIdSeminario", query = "SELECT s FROM Seminario s WHERE s.idSeminario = :idSeminario"),
    @NamedQuery(name = "Seminario.findByTituloPonencia", query = "SELECT s FROM Seminario s WHERE s.tituloPonencia = :tituloPonencia"),
    @NamedQuery(name = "Seminario.findByFecha", query = "SELECT s FROM Seminario s WHERE s.fechaInicio = :fecha"),
    @NamedQuery(name = "Seminario.findByCodigoSeguridad", query = "SELECT s FROM Seminario s WHERE s.codigoSeguridad = :codigoSeguridad")})
public class Seminario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "idSeminario", nullable = false)
    private Integer idSeminario;
    @Size(max = 255)
    @Column(name = "tituloPonencia", length = 255)
    private String tituloPonencia;
    @Lob
    @Size(max = 65535)
    @Column(name = "resumen", length = 65535)
    private String resumen;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fechaTermino")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTermino;
    @Lob
    @Size(max = 65535)
    @Column(name = "lugar", length = 65535)
    private String lugar;
    @Lob
    @Size(max = 65535)
    @Column(name = "direccion", length = 65535)
    private String direccion;
    @Size(max = 6)
    @Column(name = "codigoSeguridad", length = 6)
    private String codigoSeguridad;
    @OneToMany(mappedBy = "seminario", cascade = {CascadeType.REMOVE})
    private List<Asistente> asistenteList;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "Ponente", referencedColumnName = "idPonente")
    private Ponente ponente;

    @ManyToOne
    @JoinColumn(name = "Acreditador1", referencedColumnName = "id")
    private Acreditador acreditador1;

    @ManyToOne
    @JoinColumn(name = "Acreditador2", referencedColumnName = "id")
    private Acreditador acreditador2;

   

    public Seminario() {
    }

    public Seminario(Integer idSeminario) {
        this.idSeminario = idSeminario;
    }

    public Integer getIdSeminario() {
        return idSeminario;
    }

    public void setIdSeminario(Integer idSeminario) {
        this.idSeminario = idSeminario;
    }

    public String getTituloPonencia() {
        return tituloPonencia;
    }

    public void setTituloPonencia(String tituloPonencia) {
        this.tituloPonencia = tituloPonencia;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public DateTime getFechaTermino() {
        return new DateTime(fechaTermino);
    }

    public void setFechaTermino(DateTime fechaTermino) {
        this.fechaTermino = fechaTermino.toDate();
    }

    public DateTime getFechaInicio() {
        return new DateTime(fechaInicio);
    }

    public void setFechaInicio(DateTime fechaInicio) {
        this.fechaInicio = fechaInicio.toDate();
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    @XmlTransient
    public List<Asistente> getAsistenteList() {
        return asistenteList;
    }

    public void setAsistenteList(List<Asistente> asistenteList) {
        this.asistenteList = asistenteList;
    }

    public Ponente getPonente() {
        return ponente;
    }

    public void setPonente(Ponente ponente) {
        this.ponente = ponente;
    }

    public Acreditador getAcreditador1() {
        return acreditador1;
    }

    public void setAcreditador1(Acreditador acreditador1) {
        this.acreditador1 = acreditador1;
    }

    public Acreditador getAcreditador2() {
        return acreditador2;
    }

    public void setAcreditador2(Acreditador acreditador2) {
        this.acreditador2 = acreditador2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeminario != null ? idSeminario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seminario)) {
            return false;
        }
        Seminario other = (Seminario) object;
        if ((this.idSeminario == null && other.idSeminario != null) || (this.idSeminario != null && !this.idSeminario.equals(other.idSeminario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + idSeminario;
    }

}
