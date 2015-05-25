package web.beans.catalogoGradosAcademicos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.entidades.GradoAcademico;
import modelo.facade.GradoAcademicoFacade;
import web.beans.interfaces.GestionBeanInterface;
import web.beans.util.JsfUtil;

/**
 *
 * @author Gandhi
 */
@Named("gradosAcademicosBean")
@ViewScoped
public class GradosAcademicosBean extends JsfUtil
        implements Serializable, GestionBeanInterface {

    private GradoAcademico currentGradoAcademico;
    private List<GradoAcademico> gradosAcademicosList;
    @Inject
    private GradoAcademicoFacade gradosAcademicosFacade;
    private String paginaRetornoGet;
    private String idGet;

    public GradosAcademicosBean() {
        currentGradoAcademico = new GradoAcademico();
        gradosAcademicosList = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
    }

    @Override
    public void findAll() {
        gradosAcademicosList = gradosAcademicosFacade.findAll();
    }

    @Override
    public void findByIdGet() {
        try {
            currentGradoAcademico = gradosAcademicosFacade.find(Integer.parseInt(idGet));
        } catch (NumberFormatException e) {
            JsfUtil.addErrorMessage("Error al obtener seminario " + idGet + ":" + e.toString());
        }
    }

    @Override
    public void registrar() {
        try {
            gradosAcademicosFacade.create(currentGradoAcademico);
            addSuccessMessage("Registrado satisfactoriamente");
            redirectFromContextPath("admin/gestionarGradosAcademicos.xhtml");
        } catch (Exception e) {
            addErrorMessage("Error al registrar" + e.toString());
        }
    }

    @Override
    public void prepararEditar() {
        findByIdGet();
    }

    @Override
    public void prepararRegistrar() {
        this.currentGradoAcademico = new GradoAcademico();
        this.currentGradoAcademico.setAbreviacion("");
        this.currentGradoAcademico.setAbreviacion("");
    }

    @Override
    public void editar() {
        try {
            gradosAcademicosFacade.edit(currentGradoAcademico);
            addSuccessMessage("Editado satisfactoriamente");
            redirectFromContextPath("admin/" + paginaRetornoGet);
        } catch (Exception e) {
            addErrorMessage("Error al editar: " + e.toString());
        }
    }

    @Override
    public void eliminar() {
        try {
            gradosAcademicosFacade.remove(currentGradoAcademico);
            findAll();
            addSuccessMessage("Eliminado correctamente");
        } catch (Exception e) {
            addErrorMessage("Error al eliminar: " + e.toString());
        }
    }

    public GradoAcademico getCurrentGradoAcademico() {
        return currentGradoAcademico;
    }

    public void setCurrentGradoAcademico(GradoAcademico currentGradoAcademico) {
        this.currentGradoAcademico = currentGradoAcademico;
    }

    public List<GradoAcademico> getGradosAcademicosList() {
        return gradosAcademicosList;
    }

    public void setGradosAcademicosList(List<GradoAcademico> gradosAcademicosList) {
        this.gradosAcademicosList = gradosAcademicosList;
    }

    public String getPaginaRetornoGet() {
        return paginaRetornoGet;
    }

    public void setPaginaRetornoGet(String paginaRetornoGet) {
        this.paginaRetornoGet = paginaRetornoGet;
    }

    public String getIdGet() {
        return idGet;
    }

    public void setIdGet(String idGet) {
        this.idGet = idGet;
    }

}
