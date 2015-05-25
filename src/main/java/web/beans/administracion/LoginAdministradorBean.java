/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.administracion;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.PublicidadSeminario;
import modelo.entidades.Administrador;
import modelo.facade.AdministradorFacade;
import modelo.facade.SeminarioFacade;
import web.beans.documentos.DocumentosBean;
import web.beans.util.JsfUtil;

/**
 *
 * @author Gandhi
 */
@Named("loginAdministradorBean")
@ViewScoped
public class LoginAdministradorBean extends JsfUtil {

    @Inject
    private AdministradorFacade administradorFacade;
    private Administrador administrador;

    @Inject
    private AdministradorBean administradorBean;

    @Inject
    private SeminarioFacade seminarioFacade;

    @Inject
    DocumentosBean documentosBean;

    private String password;
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void doLoginAdministrador() {       
        administrador = administradorFacade.doLogin(username, password);
        if (administrador != null) {
            administradorBean.setAdministrador(administrador);
            addSuccessMessage("Bienvenido administrador: " + administrador.getUsername());
            redirectFromContextPath("admin/gestionarProximosSeminarios.xhtml");
        }
        addErrorMessage("Acceso denegado");
    }

}
