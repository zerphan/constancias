/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package modelo.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Gandhi
 */
@Entity
@Table(name = "ponente", catalog = "constancias", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ponente.findAll", query = "SELECT p FROM Ponente p"),
    @NamedQuery(name = "Ponente.findByIdPonente", query = "SELECT p FROM Ponente p WHERE p.idPonente = :idPonente"),
    @NamedQuery(name = "Ponente.findByNombre", query = "SELECT p FROM Ponente p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Ponente.findByApellidoPaterno", query = "SELECT p FROM Ponente p WHERE p.apellidoPaterno = :apellidoPaterno"),
    @NamedQuery(name = "Ponente.findByApellidoMaterno", query = "SELECT p FROM Ponente p WHERE p.apellidoMaterno = :apellidoMaterno"),
    @NamedQuery(name = "Ponente.findByEmail", query = "SELECT p FROM Ponente p WHERE p.email = :email")})
public class Ponente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "idPonente", nullable = false)
    private Integer idPonente;
    @Size(max = 32)
    @Column(name = "nombre", length = 32)
    private String nombre;
    @Size(max = 32)
    @Column(name = "apellidoPaterno", length = 32)
    private String apellidoPaterno;
    @Size(max = 32)
    @Column(name = "apellidoMaterno", length = 32)
    private String apellidoMaterno;
    @Lob
    @Size(max = 65535)
    @Column(name = "cv", length = 65535)
    private String cv;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Email invalido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 32)
    @Column(name = "email", length = 32)
    private String email;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Column(name = "archivoConstancia")
    @Basic(fetch = FetchType.LAZY)
    private byte[] archivoConstancia;
    @JoinColumn(name = "gradoacademico", referencedColumnName = "id")
    @ManyToOne
    private GradoAcademico gradoAcademico;

    @OneToOne(mappedBy = "ponente") //Como se llama en la otra entidad el atributo
    private Seminario seminario;

    public Ponente() {
    }

    public Ponente(Integer idPonente) {
        this.idPonente = idPonente;
    }

    public Integer getIdPonente() {
        return idPonente;
    }

    public void setIdPonente(Integer idPonente) {
        this.idPonente = idPonente;
    }

    public String getNombre() {
        return capitalizar(nombre);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return capitalizar(apellidoPaterno);
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return capitalizar(apellidoMaterno);
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public byte[] getArchivoConstancia() {
        return archivoConstancia;
    }

    public void setArchivoConstancia(byte[] archivoConstancia) {
        this.archivoConstancia = archivoConstancia;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public GradoAcademico getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(GradoAcademico gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    public Seminario getSeminario() {
        return seminario;
    }

    public void setSeminario(Seminario seminario) {
        this.seminario = seminario;
    }

    public String getNombreCompleto() {
        String nombre = "";
        nombre += "" + getGradoAcademico().getAbreviacion();
        nombre += " " + this.nombre;
        nombre += " " + this.apellidoPaterno;
        nombre += " " + this.apellidoMaterno;
        nombre = capitalizar(nombre);
        return nombre;
    }

    public String capitalizar(String texto) {
        return WordUtils.capitalize(texto);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPonente != null ? idPonente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ponente)) {
            return false;
        }
        Ponente other = (Ponente) object;
        if ((this.idPonente == null && other.idPonente != null) || (this.idPonente != null && !this.idPonente.equals(other.idPonente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.Ponente[ idPonente=" + idPonente + " ]";
    }

}
