package web.beans.asistentes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.CodigoQR;
import modelo.entidades.Asistente;
import modelo.entidades.DefinicionCodigoQR;
import modelo.entidades.Seminario;
import modelo.facade.AsistenteFacade;
import modelo.facade.ConstantesFacade;
import modelo.facade.DefinicionCodigoQRFacade;
import modelo.facade.SeminarioFacade;
import net.glxn.qrgen.image.ImageType;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.FilterEvent;
import web.beans.documentos.DocumentosBean;
import web.beans.seminarios.SeminariosBean;
import web.beans.util.JsfUtil;
import static web.beans.util.JsfUtil.getResponse;

/**
 *
 * @author Gandhi
 */
@Named("asistentesBean")
@ViewScoped
public class AsistentesBean extends JsfUtil
        implements Serializable {

    private final String PAGINA_REGISTRO_ASISTENCIA_EN_TIEMPO = "registrarAsistencia.xhtml";

    private Seminario currentSeminario;
    private List<Seminario> seminariosEnEjecucionList;
    private Asistente currentAsistente;
    private String codigoSeguridad;
    private List<Asistente> asistentesList;
    private List<Asistente> listFilteredAsistentes;
    private JasperPrint print;
    private String idGet;
    private String paginaRetornoGet;
    private Float creditos;

    @Inject
    private DefinicionCodigoQRFacade definicionCodigoQRFacade;
    @Inject
    AsistenteFacade asistenteFacade;
    @Inject
    SeminarioFacade seminarioFacade;
    @Inject
    DocumentosBean documentosBean;
    @Inject
    ConstantesFacade constantesFacade;
    @Inject
    private SeminariosBean seminariosBean;

    private float horasAsistencia; // Esta variable contendra el valor del numero de asistencias x 1.5 horas, es decir las horas totales de asistencia, usado en la consulta de creditos

    public AsistentesBean() {
        currentSeminario = new Seminario();
        currentAsistente = new Asistente();
        seminariosEnEjecucionList = new ArrayList<>();
        listFilteredAsistentes = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
    }

    public void findSeminariosEnEjecucion() {
        //System.out.println("Entro a find Seminarios en ejecucion");
        seminariosEnEjecucionList = seminarioFacade.findSeminariosEnEjecucion();
        //System.out.println("Size en ejecucion:"+seminariosEnEjecucionList);
        if (!seminariosEnEjecucionList.isEmpty() && seminariosEnEjecucionList != null) {
            currentSeminario = seminariosEnEjecucionList.get(0);
            currentAsistente.setSeminario(currentSeminario);
            findAllByIdSeminario();
            // System.out.println("No es nulo:"+currentSeminario.getTituloPonencia());
        } else {
            //System.out.println("Es nulo");
            currentSeminario = null;
        }

    }

    public void filterListener(FilterEvent filterEvent) {
        calcularCreditos();
        addSuccessMessage("Total de asistencias: " + listFilteredAsistentes.size());
    }

    public void findByEmail() {
        try {
            asistentesList = asistenteFacade.findAllByEmail(currentAsistente.getEmail());
            if (asistentesList == null) {
                JsfUtil.addErrorMessage("No existen constancias para este email");
                asistentesList.clear();
            } else {
                if (asistentesList.isEmpty()) {
                    JsfUtil.addErrorMessage("No existen constancias para este email");
                    asistentesList.clear();
                } else {
                    listFilteredAsistentes = asistentesList;
                    calcularCreditos();
                    JsfUtil.addSuccessMessage(listFilteredAsistentes.size() + " Constancias encontradas satisfactoriamente");
                }
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error: " + e.toString());
            asistentesList.clear();
        }
    }

    private void capitalizarNombreCurrentAsistente() {
        currentAsistente.setNombre(WordUtils.capitalize(currentAsistente.getNombre().toLowerCase()));
        currentAsistente.setApellidoPaterno(WordUtils.capitalize(currentAsistente.getApellidoPaterno().toLowerCase()));
        currentAsistente.setApellidoMaterno(WordUtils.capitalize(currentAsistente.getApellidoMaterno().toLowerCase()));
    }

    public void findByIdGet() {
        try {
            currentAsistente = asistenteFacade.find(Integer.parseInt(idGet));
            currentSeminario = currentAsistente.getSeminario();
        } catch (NumberFormatException e) {
            JsfUtil.addErrorMessage("Error al obtener el asistente" + idGet + ":" + e.toString());
            redirectFromContextPath(PAGINA_REGISTRO_ASISTENCIA_EN_TIEMPO);
        }
    }

    public void registrarEnTiempo() {
        boolean isAfterTime = new DateTime(currentSeminario.getFechaTermino()).isAfterNow();
        if ((currentSeminario.getCodigoSeguridad().equals(codigoSeguridad))
                && (isAfterTime)
                && !estaRegistrado(currentAsistente)) {
            if (registrar()) {
                RequestContext.getCurrentInstance().execute("hideModal()");
                redirectFromContextPath("descargarConstanciaAsistencia.xhtml?id=" + currentAsistente.getIdAsistente());
            } else {
                addErrorMessage("Error al registrar el asistente");
            }
        } else {
            if (!isAfterTime) {
                addErrorMessage("A concluido el periodo permitido para generar las constancias.");
                redirectFromContextPath(PAGINA_REGISTRO_ASISTENCIA_EN_TIEMPO);
            } else {
                addErrorMessage("Código de seguridad erroneo");
            }
        }

    }

    public void registrarExtratemporal() {
        if (estaRegistrado(currentAsistente)) {
            addErrorMessage("El nombre ó email del asistente ya está registrado");
        } else {
            if (registrar()) {                
                addSuccessMessage("El asistente registrado correctamente");
            } else {
                addErrorMessage("Error al registrar el asistente");
            }
        }
    }

    public boolean registrar() {
        capitalizarNombreCurrentAsistente();
        currentAsistente.setFecha(new Date());
        asistenteFacade.create(currentAsistente);
        
        Map hm = (Map) new HashMap();
        byte[] pdfBytes;
        hm.put("NOMBRE_ASISTENTE", currentAsistente.getNombre()
                + " " + currentAsistente.getApellidoPaterno()
                + " " + currentAsistente.getApellidoMaterno());
        hm.put("TITULO_SEMINARIO", currentSeminario.getTituloPonencia());
        hm.put("LUGAR_SEMINARIO", currentSeminario.getDireccion());
        hm.put("FECHA_SEMINARIO", currentSeminario.getFechaInicio().toDate());
        InputStream inputStreamImagenCodigoQR = new ByteArrayInputStream(
                generarCodigoQR(currentAsistente));
        hm.put("IMAGEN_CODIGOQR", inputStreamImagenCodigoQR);
        try {
            print = JasperFillManager.fillReport(getPathConstanciaAsistenciaJasper(), hm, new JREmptyDataSource());
            pdfBytes = JasperExportManager.exportReportToPdf(print);
            currentAsistente.setArchivoConstancia(pdfBytes);
            asistenteFacade.edit(currentAsistente);
            /*
             File archivo = new File("C:\\Users\\Gandhi\\Desktop\\Constancia.pdf");
             FileOutputStream fos = new FileOutputStream(archivo);
             fos.write(currentAsistente.getArchivoConstancia());
             fos.flush();
             fos.close();
             */
            return true;
        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }


    public void exportarConstancia() {
        if (currentAsistente == null) {
            System.out.println("Es nulo el asistente");
            return;
        }
        OutputStream os = null;
        try {
            FacesContext.getCurrentInstance().responseComplete();
            //getResponse().getWriter().flush();   
            getResponse().setContentType("application/pdf");
            getResponse().setContentLength(currentAsistente.getArchivoConstancia().length);
            getResponse().setHeader("Content-Transfer-Encoding", "binary");
            getResponse().addHeader("Content-disposition", "attachment; filename=constancia"
                    + currentAsistente.getIdAsistente() + ".pdf");
            os = getResponse().getOutputStream();
            os.write(currentAsistente.getArchivoConstancia());
            os.close();
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

    public boolean estaRegistrado(Asistente a) {
        findAllByIdSeminario();
        for (Asistente asistente : asistentesList) {
            if (asistente.getNombre().equalsIgnoreCase(a.getNombre())
                    && asistente.getApellidoPaterno().equalsIgnoreCase(a.getApellidoPaterno())
                    && asistente.getApellidoMaterno().equalsIgnoreCase(a.getApellidoMaterno())
                    || asistente.getEmail().equals(a.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public byte[] generarCodigoQR(Asistente asistente) {
        DefinicionCodigoQR definicionCodigoQR = definicionCodigoQRFacade.findDefinicionCodigoQR();
        String texto = definicionCodigoQR.getPreIdentificador() + ":"
                + asistente.getSeminario().getIdSeminario()
                + "/CA:" + asistente.getIdAsistente() + "/"
                + new DateTime(currentSeminario.getFechaInicio()).getYear();
        //System.out.println(texto);
        return new CodigoQR().generar(texto, ImageType.PNG, 200, 200);
    }

    public void prepararRegistrar() {
        //CONDICIONES PARA QUE SEA UN REGISTRO VALIDO: ESTE DENTRO DEL LIMITE DE TIEMPO, NO HABER ESTADO REGISTRADO ANTES
        boolean isAfterTime = new DateTime(currentSeminario.getFechaTermino()).isAfterNow();
        if (!isAfterTime) {
            addErrorMessage("El tiempo para generar constancias a concluido");
            redirectFromContextPath(PAGINA_REGISTRO_ASISTENCIA_EN_TIEMPO);
        } else if (estaRegistrado(currentAsistente)) {
            addErrorMessage("Tu nombre ó email ya está registrado");
            redirectFromContextPath(PAGINA_REGISTRO_ASISTENCIA_EN_TIEMPO);
        } else if ((currentSeminario.getCodigoSeguridad().equals(codigoSeguridad)) && isAfterTime) {
            RequestContext.getCurrentInstance().execute("showModal()");
        } else {
            addErrorMessage("Código de Seguridad Incorrecto");
        }
    }

    public void prepararCreditosAsistentes() {
        listFilteredAsistentes = asistentesList;
        calcularCreditos();
    }

    private void calcularHorasAsistenciaListaFiltrada() {
        horasAsistencia = listFilteredAsistentes.size() * 1.5f;
    }

    private void calcularCreditos() {
        calcularHorasAsistenciaListaFiltrada();
        creditos = horasAsistencia * constantesFacade.findCreditosPorHora().getValor();
    }

    public void findAll() {
        asistentesList = asistenteFacade.findAll();
    }

    public void findAllByIdSeminario() {
        asistentesList = asistenteFacade.findAllByIdSeminario(currentSeminario.getIdSeminario());
    }

    public Seminario getCurrentSeminario() {
        return currentSeminario;
    }

    public void setCurrentSeminario(Seminario currentSeminario) {
        this.currentSeminario = currentSeminario;
    }

    public Asistente getCurrentAsistente() {
        return currentAsistente;
    }

    public void setCurrentAsistente(Asistente currentAsistente) {
        this.currentAsistente = currentAsistente;
    }

    public List<Seminario> getSeminariosEnEjecucionList() {
        return seminariosEnEjecucionList;
    }

    public String getPaginaRetornoGet() {
        return paginaRetornoGet;
    }

    public void setPaginaRetornoGet(String paginaRetornoGet) {
        this.paginaRetornoGet = paginaRetornoGet;
    }

    public List<Asistente> getAsistentesList() {
        return asistentesList;
    }

    public void setAsistentesList(List<Asistente> asistentesList) {
        this.asistentesList = asistentesList;
    }

    public void setSeminariosEnEjecucionList(List<Seminario> seminariosEnEjecucionList) {
        this.seminariosEnEjecucionList = seminariosEnEjecucionList;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getIdGet() {
        return idGet;
    }

    public void setIdGet(String idGet) {
        this.idGet = idGet;
    }

    public List<Asistente> getListFilteredAsistentes() {
        return listFilteredAsistentes;
    }

    public void setListFilteredAsistentes(List<Asistente> listFilteredAsistentes) {
        this.listFilteredAsistentes = listFilteredAsistentes;
    }

    public float getHorasAsistencia() {
        return horasAsistencia;
    }

    public void setHorasAsistencia(float horasAsistencia) {
        this.horasAsistencia = horasAsistencia;
    }

    public Float getCreditos() {
        return creditos;
    }

    public void setCreditos(Float creditos) {
        this.creditos = creditos;
    }

}
