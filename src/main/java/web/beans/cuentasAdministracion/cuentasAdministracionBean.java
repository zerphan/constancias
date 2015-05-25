package web.beans.cuentasAdministracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import modelo.entidades.Administrador;
import modelo.facade.AdministradorFacade;
import web.beans.administracion.AdministradorBean;
import web.beans.interfaces.GestionBeanInterface;
import web.beans.util.JsfUtil;

@Named("cuentasAdministracionBean")
@ViewScoped
public class cuentasAdministracionBean extends JsfUtil implements Serializable,
        GestionBeanInterface {

    @Inject
    private AdministradorFacade administradorFacade;
    @Inject
    private AdministradorBean administradorBean;
    private Administrador currentAdminstrador;
    private List<Administrador> administradorList;
    private String paginaRetornoGet;
    private String idGet;

    public cuentasAdministracionBean() {
        administradorList = new ArrayList();
        currentAdminstrador = new Administrador();

    }

    @Override
    public void findAll() {
        try {
            administradorList = administradorFacade.findAll();
            if (administradorList != null) {
                administradorList.remove(administradorBean.getAdministrador());
            }
        } catch (Exception e) {
            addErrorMessage("Error al obtener las cuentas de adminsitración: "
                    + e.toString());
        }

    }

    @Override
    public void findByIdGet() {
        try {
            currentAdminstrador = administradorFacade.find(Integer
                    .parseInt(idGet));
        } catch (Exception e) {
            addErrorMessage("Error al obtener la cuenta de administracion - "
                    + idGet + ": " + e.toString());
        }

    }

    @Override
    public void registrar() {
        try {
            if (administradorFacade.findByUsername(currentAdminstrador.getUsername()) != null) {
                addErrorMessage("El nombre de usuario ingresado ya existe, intente nuevamente.");
                return;
            }
            if (administradorFacade.findByEmail(currentAdminstrador.getEmail()) != null) {
                addErrorMessage("El email ingresado ya es ocupado en otra cuenta, intente nuevamente.");
                return;
            }
            administradorFacade.create(currentAdminstrador);
            addSuccessMessage("Registro exitoso");
            redirectFromContextPath("admin/gestionarCuentasAdministracion.xhtml");
        } catch (Exception e) {
            addErrorMessage("Error al crear la cuenta de administración");
        }

    }

    @Override
    public void prepararEditar() {
        findByIdGet();

    }

    @Override
    public void prepararRegistrar() {
        currentAdminstrador.setAdministracionAvanzada(Boolean.FALSE);

    }

    @Override
    public void editar() {
        try {
            administradorFacade.edit(currentAdminstrador);
            addSuccessMessage("Editado satisfactoriamente");
            redirectFromContextPath("admin/" + paginaRetornoGet);
        } catch (Exception e) {
            addErrorMessage("Error al editar la cuenta de administración");
        }

    }

    @Override
    public void eliminar() {
        try {
            administradorFacade.remove(currentAdminstrador);
            addSuccessMessage("Eliminado satisfactoriamente");
            findAll();
        } catch (Exception e) {
            addErrorMessage("Error al eliminar: " + e.toString());
        }

    }

    public String getIdGet() {
        return idGet;
    }

    public void setIdGet(String idGet) {
        this.idGet = idGet;
    }

    public Administrador getCurrentAdminstrador() {
        return currentAdminstrador;
    }

    public void setCurrentAdminstrador(Administrador currentAdminstrador) {
        this.currentAdminstrador = currentAdminstrador;
    }

    public List<Administrador> getAdministradorList() {
        return administradorList;
    }

    public void setAdministradorList(List<Administrador> administradorList) {
        this.administradorList = administradorList;
    }

    public String getPaginaRetornoGet() {
        return paginaRetornoGet;
    }

    public void setPaginaRetornoGet(String paginaRetornoGet) {
        this.paginaRetornoGet = paginaRetornoGet;
    }

}
