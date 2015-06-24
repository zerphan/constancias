package web.beans.seminarios;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Constantes;
import modelo.entidades.Ponente;
import modelo.entidades.Seminario;
import modelo.facade.PonenteFacade;
import modelo.facade.SeminarioFacade;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import web.beans.asistentes.AsistentesBean;
import web.beans.documentos.DocumentosBean;
import web.beans.interfaces.GestionBeanInterface;
import web.beans.util.JsfUtil;
import static web.beans.util.JsfUtil.getResponse;

/**
 *
 * @author Gandhi
 */
@Named("seminariosBean")
@ViewScoped
public class SeminariosBean extends JsfUtil
        implements GestionBeanInterface, Serializable {

    private boolean lecturaTerminos;
    private int horaMin;
    private Date fechaHoraInicio_aux;
    private Date fechaInicio;
    private List<Seminario> listSeminarios;
    private Seminario currentSeminario;
    private Ponente currentPonente;
    @Inject
    private SeminarioFacade seminarioFacade;
    @Inject
    private PonenteFacade ponenteFacade;
    private int tiempoTolerancia;
    private List<Seminario> listFilteredSeminarios;
    @Inject
    private DocumentosBean documentosBean;
    @Inject
    private AsistentesBean asistentesBean;

    public SeminariosBean() {
        listSeminarios = new ArrayList<>();
        currentSeminario = new Seminario();
        currentPonente = new Ponente();
        listFilteredSeminarios = new ArrayList<>();
        currentSeminario = new Seminario();
    }

    @PostConstruct
    private void init() {
        lecturaTerminos = false;
    }

    public void findSeminariosConcluidos() {
        listSeminarios = seminarioFacade.findSeminariosConcluidos();
        listFilteredSeminarios = listSeminarios;
    }

    @Override
    public void findAll() {
        listSeminarios = seminarioFacade.findAll();
    }

    public void findProximosSeminarios() {
        listSeminarios = seminarioFacade.findSeminariosProximos();
        if (listSeminarios == null) {
            listSeminarios = new ArrayList<>();
            listSeminarios.clear();
        }
    }

    public void prepararAgregarAsistenteExtraTemporal() {
        asistentesBean.setCurrentSeminario(currentSeminario);
        asistentesBean.getCurrentAsistente().setSeminario(currentSeminario);
    }

    public void registrarExtratemporal() {
        RequestContext.getCurrentInstance().execute("hideModalAgregarAsistente()");
        asistentesBean.registrarExtratemporal();
        currentSeminario.getAsistenteList().add(asistentesBean.getCurrentAsistente());
        seminarioFacade.edit(currentSeminario);
        findSeminariosConcluidos();
    }

    private void inicializarPropuestaFechaInicio() {
        fechaHoraInicio_aux = new Date();
        DateTime hoy = new DateTime();
        //DateTime mañana = new DateTime(hoy.getTime() + (Constantes.HORA_EN_MILISEGUNDOS * 24L));
        DateTime mañana = new DateTime().plusDays(1);
        if (yaPasoHoraDefault(hoy)) { // Si hoy ya pasan de la 13:30 PROPONEMOS que sea mañana
            fechaInicio = mañana.toDate();
            horaMin = Constantes.HORA_LIMITE_INFERIOR_SEMINARIO;
        } else {
            fechaInicio = hoy.toDate();
            horaMin = hoy.getHourOfDay();
        }
        establecerHoraDefault(fechaHoraInicio_aux);
    }

    public void eventoSeleccionCalendario(SelectEvent event) {
        DateTime date = new DateTime((Date) event.getObject());
        DateTime hoy = new DateTime();
        int horaActual = hoy.getHourOfDay();
        int minutoActual = hoy.getMinuteOfHour();
        if (laFechaEsDeHoy(date) && yaPasoHoraDefault(hoy)) {
            if (minutoActual < 30) {
                fechaHoraInicio_aux.setHours(horaActual);
                fechaHoraInicio_aux.setMinutes(Constantes.MINUTO_DEFAULT_SEMINARIO);
            } else {
                fechaHoraInicio_aux.setHours(horaActual + 1);
                fechaHoraInicio_aux.setMinutes(0);
            }
            horaMin = horaActual;
        } else {
            establecerHoraDefault(fechaHoraInicio_aux);
            horaMin = Constantes.HORA_LIMITE_INFERIOR_SEMINARIO;
        }
    }

    private boolean laFechaEsDeHoy(DateTime fecha) {
        DateTime hoy = new DateTime();
        return hoy.getDayOfMonth() == fecha.getDayOfMonth()
                && hoy.getMonthOfYear() == fecha.getMonthOfYear()
                && hoy.getYear() == fecha.getYear();
    }

    private boolean yaPasoHoraDefault(DateTime date) {
        int hora = date.getHourOfDay();
        int minuto = date.getMinuteOfHour();
        return hora > Constantes.HORA_DEFAULT_SEMINARIO || (hora == Constantes.HORA_DEFAULT_SEMINARIO && minuto > Constantes.MINUTO_DEFAULT_SEMINARIO);
    }

    private Date establecerHoraDefault(Date date) {
        date.setHours(Constantes.HORA_DEFAULT_SEMINARIO);
        date.setMinutes(Constantes.MINUTO_DEFAULT_SEMINARIO);
        return date;
    }

    public void prepararEliminarSeminario() {
        lecturaTerminos = false;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public String registrarCurrentSeminario() {
        try {
            this.fechaInicio.setHours(this.fechaHoraInicio_aux.getHours());
            this.fechaInicio.setMinutes(this.fechaHoraInicio_aux.getMinutes());
            this.currentSeminario.setFechaInicio(new DateTime(this.fechaInicio));
            this.currentSeminario.setFechaTermino(this.currentSeminario.getFechaInicio().plusMinutes(150));// 150 minutes of tolerance
            this.ponenteFacade.create(this.currentPonente);
            this.currentSeminario.setPonente(this.currentPonente);
            this.seminarioFacade.create(this.currentSeminario);
            this.currentPonente.setArchivoConstancia(this.documentosBean.crearConstanciaPonente(this.currentSeminario));
            this.ponenteFacade.edit(this.currentPonente);
            this.seminarioFacade.edit(this.currentSeminario);
            addSuccessMessage("Seminario creado satisfactoriamente");
            redirectFromContextPath("admin/gestionarProximosSeminarios.xhtml");
        } catch (Exception e) {
            addErrorMessage("Error al crear el seminario:" + e.toString());
        }
        return "";
    }

    @Override
    public void eliminar() {
        try {
            seminarioFacade.remove(currentSeminario);
            findProximosSeminarios();
            addSuccessMessage("Eliminado satisfactoriamente");
        } catch (Exception e) {
            addErrorMessage("Error al eliminar:" + e.toString());
        }
    }

    public void eliminarCurrentSeminarioConcluido() {
        try {
            seminarioFacade.remove(currentSeminario);
            findSeminariosConcluidos();
            addSuccessMessage("Eliminado satisfactoriamente");
        } catch (Exception e) {
            addErrorMessage("Error al eliminar:" + e.toString());
        }
        RequestContext.getCurrentInstance().execute("hideModalConfirmarEliminarSeminario()");
    }

    public void exportarConstanciaPonente() {
        System.out.println("Entro a exportar constancia asistente:" + currentSeminario.getPonente().getNombre());
        OutputStream os = null;
        try {
            FacesContext.getCurrentInstance().responseComplete();
            //getResponse().getWriter().flush(); 
            getResponse().setContentType("application/pdf");
            getResponse().setContentLength(currentSeminario.getPonente().getArchivoConstancia().length);
            getResponse().setHeader("Content-Transfer-Encoding", "binary");
            getResponse().addHeader("Content-disposition", "attachment; filename=constanciaPonente"
                    + currentSeminario.getPonente().getIdPonente() + ".pdf");
            os = getResponse().getOutputStream();
            if (currentSeminario.getPonente().getArchivoConstancia() == null) {
                System.out.println("es nulo");
            }
            IOUtils.write(currentSeminario.getPonente().getArchivoConstancia(), getResponse().getOutputStream());

            //RequestDispatcher rd= getRequest().getRequestDispatcher("/generarConstancia.html");
            //rd.forward(getRequest(), getResponse());
        } catch (IOException ex) {
            Logger.getLogger(AsistentesBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AsistentesBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(AsistentesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void findByIdGet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepararEditar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepararRegistrar() {
        inicializarPropuestaFechaInicio();
    }

    @Override
    public void editar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /* ---------------- GETTERS AND SETTERS-----------------*/
    public List<Seminario> getListFilteredSeminarios() {
        return listFilteredSeminarios;
    }

    public void setListFilteredSeminarios(List<Seminario> listFilteredSeminarios) {
        this.listFilteredSeminarios = listFilteredSeminarios;
    }

    public List<Seminario> getListSeminarios() {
        return listSeminarios;
    }

    public void setListSeminarios(List<Seminario> listSeminarios) {
        this.listSeminarios = listSeminarios;
    }

    public int getHoraMin() {
        return horaMin;
    }

    public void setHoraMin(int horaMin) {
        this.horaMin = horaMin;
    }

    public boolean isLecturaTerminos() {
        return lecturaTerminos;
    }

    public void setLecturaTerminos(boolean lecturaTerminos) {
        this.lecturaTerminos = lecturaTerminos;
    }

    public Seminario getCurrentSeminario() {
        return currentSeminario;
    }

    public int getTiempoTolerancia() {
        return tiempoTolerancia;
    }

    public void setTiempoTolerancia(int tiempoTolerancia) {
        this.tiempoTolerancia = tiempoTolerancia;
    }

    public void setFechaHoraInicio_aux(Date fechaHoraInicio_aux) {
        this.fechaHoraInicio_aux = fechaHoraInicio_aux;
    }

    public Date getFechaHoraInicio_aux() {
        return this.fechaHoraInicio_aux;
    }

    public void setCurrentSeminario(Seminario currentSeminario) {
        this.currentSeminario = currentSeminario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Ponente getCurrentPonente() {
        return currentPonente;
    }

    public void setCurrentPonente(Ponente currentPonente) {
        this.currentPonente = currentPonente;
    }
}
