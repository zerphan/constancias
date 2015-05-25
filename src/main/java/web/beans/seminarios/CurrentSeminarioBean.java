package web.beans.seminarios;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Constantes;
import modelo.entidades.Ponente;
import modelo.entidades.Seminario;
import modelo.facade.PonenteFacade;
import modelo.facade.SeminarioFacade;
import org.joda.time.DateTime;
import web.beans.documentos.DocumentosBean;
import web.beans.util.JsfUtil;

@Named("currentSeminarioBean")
@ViewScoped
public class CurrentSeminarioBean extends JsfUtil
        implements Serializable {

    private String idCurrentSeminarioGet;
    private Seminario currentSeminario;
    private String paginaRetornoGet;
    private Date fechaInicio;

    @Inject
    private SeminarioFacade seminarioFacade;
    @Inject
    private PonenteFacade ponenteFacade;
    @Inject
    private DocumentosBean documentosBean;

    public CurrentSeminarioBean() {
        currentSeminario = new Seminario();
    }

    @PostConstruct
    private void init() {
    }

    public Seminario getCurrentSeminario() {
        return currentSeminario;
    }

    public void setCurrentSeminario(Seminario currentSeminario) {
        this.currentSeminario = currentSeminario;
    }

    public String getIdCurrentSeminarioGet() {
        return idCurrentSeminarioGet;
    }

    public void setIdCurrentSeminarioGet(String idCurrentSeminarioGet) {
        this.idCurrentSeminarioGet = idCurrentSeminarioGet;
    }

    public void findSeminarioByIdGet() {
        try {
            currentSeminario = seminarioFacade.find(Integer.parseInt(idCurrentSeminarioGet));
            fechaInicio = currentSeminario.getFechaInicio().toDate();
        } catch (NumberFormatException e) {
            JsfUtil.addErrorMessage("Error al obtener seminario " + idCurrentSeminarioGet + ":" + e.toString());
        }
    }

    public void prepararEditar() {
        findSeminarioByIdGet();
    }

    public String salvarCambios() {
        try {
            currentSeminario.setFechaInicio(new DateTime(fechaInicio));
            DateTime fecha = new DateTime(currentSeminario.getFechaInicio());
            currentSeminario.setFechaTermino(fecha.plusMinutes(Constantes.MINUTOS_TOLERANCIA));            
            Ponente currentPonente = currentSeminario.getPonente();
            currentPonente.setArchivoConstancia(documentosBean.crearConstanciaPonente(currentSeminario));
            ponenteFacade.edit(currentPonente);
            seminarioFacade.edit(currentSeminario);
            JsfUtil.addSuccessMessage("Seminario editado satisfactoriamente");
            return paginaRetornoGet + "?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al editar el seminario:" + e.toString());
            return null;
        }
    }

    public String getPaginaRetornoGet() {
        return paginaRetornoGet;
    }

    public void setPaginaRetornoGet(String paginaRetornoGet) {
        this.paginaRetornoGet = paginaRetornoGet;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

}
