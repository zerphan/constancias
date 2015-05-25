/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.administracion;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import modelo.entidades.Administrador;
import modelo.facade.AdministradorFacade;
import org.primefaces.context.RequestContext;
import web.beans.util.JsfUtil;

/**
 *
 * @author Gandhi
 */
@Named("administradorBean")
@SessionScoped
public class AdministradorBean extends JsfUtil
        implements Serializable {

    private Administrador administrador;
    @Inject
    private AdministradorFacade administradorFacade;
    private String nuevoEmail;
    private String nuevoPassword;
    private String actualPassword;

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public void cambiarPassword() {
        if (actualPassword.equals(administrador.getPassword())) {
            try {
                administrador.setPassword(nuevoPassword);
                administradorFacade.edit(administrador);
                addSuccessMessage("Password cambiado satisfactoriamente");
            } catch (Exception e) {
                addErrorMessage("Error cambiar el password: " + e.toString());
            } finally {
                RequestContext.getCurrentInstance().execute("hideModalPassword()");
            }
        } else {
            addErrorMessage("El password actual no coincide, intente nuevamente");
        }

    }

    public void cambiarEmail() {
        try {
            administrador.setEmail(nuevoEmail);
            administradorFacade.edit(administrador);
            JsfUtil.addSuccessMessage("Email cambiado satisfactoriamente");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error cambiar el email: " + e.toString());
        }
        RequestContext.getCurrentInstance().execute("hideModalEmail()");
    }

    public void cerrarSesion() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
        session.invalidate();
        redirectFromContextPath("proximosSeminarios.xhtml");
    }

    public void prepararCambiarEmail() {
        nuevoEmail = "";
    }

    public void prepararCambiarPassword() {
        nuevoPassword = "";
    }

    public void editar() {
        try {
            administradorFacade.edit(administrador);
            JsfUtil.addSuccessMessage("Edición satisfactoria");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al editar: " + e.toString());
        }
    }

    public String getNuevoEmail() {
        return nuevoEmail;
    }

    public void setNuevoEmail(String nuevoEmail) {
        this.nuevoEmail = nuevoEmail;
    }

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }

    public String getActualPassword() {
        return actualPassword;
    }

    public void setActualPassword(String actualPassword) {
        this.actualPassword = actualPassword;
    }

}
