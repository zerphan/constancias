package web.beans.acreditadores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.entidades.Acreditador;
import modelo.facade.AcreditadorFacade;
import web.beans.interfaces.GestionBeanInterface;
import web.beans.util.JsfUtil;

/**
 *
 * @author Gandhi
 */
@Named("acreditadorBean")
@ViewScoped
public class AcreditadorBean extends JsfUtil
        implements Serializable {

    private Acreditador currentAcreditador;
    private List<Acreditador> acreditadoresList;
    @Inject
    private AcreditadorFacade acreditadorFacade;
    private String paginaRetornoGet;
    private String idCurrentAcreditadorGet;

    public AcreditadorBean() {
        currentAcreditador = new Acreditador();
        acreditadoresList = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
    }

    public void findAcreditadores() {
        acreditadoresList = acreditadorFacade.findAll();
    }

    public void findAcreditadorByIdGet() {
        try {
            currentAcreditador = acreditadorFacade.find(Integer.parseInt(idCurrentAcreditadorGet));
        } catch (NumberFormatException e) {
            JsfUtil.addErrorMessage("Error al obtener seminario " + idCurrentAcreditadorGet + ":" + e.toString());
        }
    }

    public void registrarCurrentAcreditador() {
        try {
            acreditadorFacade.create(currentAcreditador);
            addSuccessMessage("Registrado satisfactoriamente");
            redirectFromContextPath("admin/gestionarAcreditadoresConstancias.xhtml");
        } catch (Exception e) {
            addErrorMessage("Error al registrar" + e.toString());
        }
    }

    public void prepararEditarCurrentAcreditador() {
        findAcreditadorByIdGet();
    }

    public void prepararRegistrarCurrentAcreditador() {
        this.currentAcreditador = new Acreditador();
        this.currentAcreditador.setNombre("");
        this.currentAcreditador.setApellidoPaterno("");
        this.currentAcreditador.setApellidoMaterno("");
        this.currentAcreditador.setDescripcion("");
    }

    public void editarCurrentAcreditador() {
        try {
            acreditadorFacade.edit(currentAcreditador);
            addSuccessMessage("Editado satisfactoriamente");
            redirectFromContextPath("admin/" + paginaRetornoGet);
        } catch (Exception e) {
            addErrorMessage("Error al editar: " + e.toString());
        }
    }

    public void eliminarCurrentAcreditador() {
        try {
            acreditadorFacade.remove(currentAcreditador);
            findAcreditadores();
            addSuccessMessage("Eliminado correctamente");
        } catch (Exception e) {
            addErrorMessage("Error al eliminar: " + e.toString());
        }
    }

    public List<Acreditador> getAcreditadoresList() {
        return acreditadoresList;
    }

    public void setAcreditadoresList(List<Acreditador> acreditadoresList) {
        this.acreditadoresList = acreditadoresList;
    }

    public Acreditador getCurrentAcreditador() {
        return currentAcreditador;
    }

    public void setCurrentAcreditador(Acreditador currentAcreditador) {
        this.currentAcreditador = currentAcreditador;
    }

    public String getPaginaRetornoGet() {
        return paginaRetornoGet;
    }

    public void setPaginaRetornoGet(String paginaRetornoGet) {
        this.paginaRetornoGet = paginaRetornoGet;
    }

    public String getIdCurrentAcreditadorGet() {
        return idCurrentAcreditadorGet;
    }

    public void setIdCurrentAcreditadorGet(String idCurrentAcreditadorGet) {
        this.idCurrentAcreditadorGet = idCurrentAcreditadorGet;
    }

}
