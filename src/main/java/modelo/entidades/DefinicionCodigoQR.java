/*
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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gandhi
 */
@Entity
@Table(name = "definicioncodigoqr", catalog = "constancias", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DefinicionCodigoQR.findAll", query = "SELECT d FROM DefinicionCodigoQR d"),
    @NamedQuery(name = "DefinicionCodigoQR.findByIdDefinicionCodigoQR", query = "SELECT d FROM DefinicionCodigoQR d WHERE d.idDefinicionCodigoQR = :idDefinicionCodigoQR"),
    @NamedQuery(name = "DefinicionCodigoQR.findByPreIdentificador", query = "SELECT d FROM DefinicionCodigoQR d WHERE d.preIdentificador = :preIdentificador"),
    @NamedQuery(name = "DefinicionCodigoQR.findByPostIdentificador", query = "SELECT d FROM DefinicionCodigoQR d WHERE d.postIdentificador = :postIdentificador")})
public class DefinicionCodigoQR implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "idDefinicionCodigoQR", nullable = false)
    private Integer idDefinicionCodigoQR;
    @Size(max = 32)
    @Column(name = "preIdentificador", length = 32)
    private String preIdentificador;
    @Size(max = 32)
    @Column(name = "postIdentificador", length = 32)
    private String postIdentificador;

    public DefinicionCodigoQR() {
    }

    public DefinicionCodigoQR(Integer idDefinicionCodigoQR) {
        this.idDefinicionCodigoQR = idDefinicionCodigoQR;
    }

    public Integer getIdDefinicionCodigoQR() {
        return idDefinicionCodigoQR;
    }

    public void setIdDefinicionCodigoQR(Integer idDefinicionCodigoQR) {
        this.idDefinicionCodigoQR = idDefinicionCodigoQR;
    }

    public String getPreIdentificador() {
        return preIdentificador;
    }

    public void setPreIdentificador(String preIdentificador) {
        this.preIdentificador = preIdentificador;
    }

    public String getPostIdentificador() {
        return postIdentificador;
    }

    public void setPostIdentificador(String postIdentificador) {
        this.postIdentificador = postIdentificador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDefinicionCodigoQR != null ? idDefinicionCodigoQR.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DefinicionCodigoQR)) {
            return false;
        }
        DefinicionCodigoQR other = (DefinicionCodigoQR) object;
        if ((this.idDefinicionCodigoQR == null && other.idDefinicionCodigoQR != null) || (this.idDefinicionCodigoQR != null && !this.idDefinicionCodigoQR.equals(other.idDefinicionCodigoQR))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.Definicioncodigoqr[ idDefinicionCodigoQR=" + idDefinicionCodigoQR + " ]";
    }

}
