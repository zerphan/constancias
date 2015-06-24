package model.dto;

import java.util.Date;
import modelo.entidades.Seminario;

public class PublicidadSeminario {

    int idSeminario;
    String resumen;
    String tituloPonencia;
    Date fechaInicio;
    String abreviacion;
    String direccion;
    String nombre;
    String apellidoPaterno;
    String apellidoMaterno;
    String cv;

    public PublicidadSeminario() {
    }

    public PublicidadSeminario(Seminario seminario) {
        idSeminario = seminario.getIdSeminario();
        resumen = seminario.getResumen();
        tituloPonencia = seminario.getTituloPonencia();
        fechaInicio = seminario.getFechaInicio().toDate();
        abreviacion = seminario.getPonente().getGradoAcademico().getAbreviacion();
        direccion = seminario.getDireccion();
        nombre = seminario.getPonente().getNombre();
        apellidoPaterno = seminario.getPonente().getApellidoPaterno();
        apellidoMaterno = seminario.getPonente().getApellidoMaterno();
        cv = seminario.getPonente().getCv();
    }

    public int getIdSeminario() {
        return idSeminario;
    }

    public void setIdSeminario(int idSeminario) {
        this.idSeminario = idSeminario;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getTituloPonencia() {
        return tituloPonencia;
    }

    public void setTituloPonencia(String tituloPonencia) {
        this.tituloPonencia = tituloPonencia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

}
