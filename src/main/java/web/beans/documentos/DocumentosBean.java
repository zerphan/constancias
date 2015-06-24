package web.beans.documentos;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.CodigoQR;
import model.dto.PublicidadSeminario;
import modelo.entidades.Asistente;
import modelo.entidades.DefinicionCodigoQR;
import modelo.entidades.Ponente;
import modelo.entidades.Seminario;
import modelo.facade.DefinicionCodigoQRFacade;
import modelo.facade.PonenteFacade;
import net.glxn.qrgen.image.ImageType;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.joda.time.DateTime;
import web.beans.asistentes.AsistentesBean;
import web.beans.util.JsfUtil;
import javax.servlet.http.HttpServletResponse;
import static web.beans.util.JsfUtil.addSuccessMessage;
import static web.beans.util.JsfUtil.getPathConstanciaAsistenciaJasper;
import static web.beans.util.JsfUtil.getResponse;

@Named("documentosBean")
@RequestScoped
public class DocumentosBean extends JsfUtil implements Serializable {

    @Inject
    private DefinicionCodigoQRFacade definicionCodigoQRFacade;
    private JasperPrint print;

    @Inject
    private PonenteFacade ponenteFacade;
    private Seminario seminario;

    public DocumentosBean() {
        //System.out.println("Entro al constructor");
    }

    public void eventoExportConstanciaPonente(ActionEvent actionEvent) {
        System.out.println("Entro a exportar constancia ponente");
        crearConstanciaPonente(seminario);

    }

    public void exportarConstanciaPonente(Ponente ponente) {
        System.out.println("Entro a exportar constancia asistente:" + ponente.getNombre());
        OutputStream os = null;
        try {
            FacesContext.getCurrentInstance().responseComplete();
            //getResponse().getWriter().flush();   
            getResponse().addHeader("Content-disposition", "attachment; filename=constanciaPonente"
                    + ponente.getIdPonente() + ".pdf");
            os = getResponse().getOutputStream();
            os.write(ponente.getArchivoConstancia());
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

    public byte[] crearConstanciaPonente(Seminario currentSeminario) {
        Map hm = new HashMap();
        System.out.println(currentSeminario.getTituloPonencia());
        hm.put("REPORT_LOCALE", new Locale("ES"));
        hm.put("NOMBRE_PONENTE", currentSeminario.getPonente().getNombreCompleto());
        hm.put("TITULO_SEMINARIO", currentSeminario.getTituloPonencia());
        hm.put("LUGAR_SEMINARIO", currentSeminario.getDireccion());
        hm.put("FECHA_SEMINARIO", currentSeminario.getFechaInicio().toDate());
        hm.put("NOMBRE_ACREDITADOR_IZQUIERDA", currentSeminario.getAcreditador1().getNombreCompleto());
        hm.put("NOMBRE_ACREDITADOR_DERECHA", currentSeminario.getAcreditador2().getNombreCompleto());
        hm.put("PUESTO_ACREDITADOR_IZQUIERDA", currentSeminario.getAcreditador1().getDescripcion());
        hm.put("PUESTO_ACREDITADOR_DERECHA", currentSeminario.getAcreditador2().getDescripcion());
        try {
            this.print = JasperFillManager.fillReport(getPathConstanciaPonenteJasper(), hm, new JREmptyDataSource());
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(this.print);
            return pdfBytes;
        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Method than handles request of download the orator's certificate
     *
     * @param seminar
     */
    public void eventDownloadOratorCertificate(Seminario seminar) {
        exportOratorCertificate(seminar);
    }

    /**
     * Method than generates the orator's certificate and response with a PDF
     * file
     */
    private void exportOratorCertificate(Seminario seminar) {
        JasperPrint jasperPrint;
        Map hm = (Map) new HashMap();
        byte[] pdfBytes;
        List list;
        JRExporter exporter;
        JRBeanCollectionDataSource dataSource;
        // Getting HttpServletResponse
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        // Setting parameters
        hm.put("REPORT_LOCALE", new Locale("ES"));
        hm.put("NOMBRE_PONENTE", seminar.getPonente().getNombreCompleto());
        hm.put("TITULO_SEMINARIO", seminar.getTituloPonencia());
        hm.put("LUGAR_SEMINARIO", seminar.getDireccion());
        hm.put("FECHA_SEMINARIO", seminar.getFechaInicio().toDate());
        hm.put("NOMBRE_ACREDITADOR_IZQUIERDA", seminar.getAcreditador1().getNombreCompleto());
        hm.put("NOMBRE_ACREDITADOR_DERECHA", seminar.getAcreditador2().getNombreCompleto());
        hm.put("PUESTO_ACREDITADOR_IZQUIERDA", seminar.getAcreditador1().getDescripcion());
        hm.put("PUESTO_ACREDITADOR_DERECHA", seminar.getAcreditador2().getDescripcion());
        try {
            jasperPrint = JasperFillManager.fillReport(getPathConstanciaPonenteJasper(), hm, new JREmptyDataSource());
        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage("Error al generar la lista de asistencia: " + ex.getMessage());
            return;
        }
        // Response settings
        response.reset();
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment;filename=oratorCertificate" + seminar.getIdSeminario() + ".pdf");
        exporter = new JRPdfExporter();
        // Export the file
        try {
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            exporter.exportReport();
        } catch (IOException | JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            context.responseComplete();
        }
    }

    /**
     * Method than handles request of download the attendance list
     *
     * @param actionEvent
     */
    public void eventDownloadAttendanceList(ActionEvent actionEvent) {
        if (seminario.getFechaTermino().isBeforeNow()) {// If the seminar is completed then we can download the attendance list
            exportAttendanceList();
        }

    }

    /**
     * Method than generates the attendance list and response with a PDF file
     */
    private void exportAttendanceList() {
        JasperPrint jasperPrint;
        Map hm = (Map) new HashMap();
        byte[] pdfBytes;
        List list;
        JRExporter exporter;
        JRBeanCollectionDataSource dataSource;
        // Getting HttpServletResponse
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        // Setting parameters
        hm.put(JRParameter.REPORT_LOCALE, new Locale("ES"));
        hm.put("NOMBRE_PONENTE", seminario.getPonente().getNombreCompleto());
        hm.put("TITULO_SEMINARIO", seminario.getTituloPonencia());
        hm.put("TOTAL_ASISTENTES", "" + seminario.getAsistenteList().size());
        hm.put("FECHA_SEMINARIO", seminario.getFechaInicio().toDate());
        hm.put("FECHA_ACTUAL", new Date());
        InputStream inputStreamImagenCodigoQR = new ByteArrayInputStream(
                generarCodigoQRSeminario());
        hm.put("IMAGEN_CODIGOQR", inputStreamImagenCodigoQR);
        list = seminario.getAsistenteList();
        dataSource = new JRBeanCollectionDataSource(list, false);
        try {
            jasperPrint = JasperFillManager.fillReport(getPathAttendanceListJasper(), hm, dataSource);
        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage("Error al generar la lista de asistencia: " + ex.getMessage());
            return;
        }
        // Response settings
        response.reset();
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment;filename=listaAsistentes" + seminario.getIdSeminario() + ".pdf");
        exporter = new JRPdfExporter();
        // Export the file
        try {
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            exporter.exportReport();
        } catch (IOException | JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            context.responseComplete();
        }
    }

    /**
     * Method than handles request of download seminar's publicity
     *
     * @param seminar
     */
    public void eventExportPublicity(Seminario seminar) {
        PublicidadSeminario publicidad = new PublicidadSeminario(seminar);
        try {
            exportPublicity(publicidad);
        } catch (IOException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportarConstancia(Asistente asistente) {
        OutputStream os = null;
        try {
            FacesContext.getCurrentInstance().responseComplete();
            getResponse().getWriter().close();
            getResponse().addHeader("Content-disposition", "attachment; filename=constanciaAsistencia"
                    + asistente.getIdAsistente() + ".pdf");
            os = getResponse().getOutputStream();
            os.write(asistente.getArchivoConstancia());
        } catch (IOException ex) {
            Logger.getLogger(AsistentesBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(AsistentesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Export publicity of an specific seminar
     *
     * @param publicidadSeminario
     * @throws IOException
     */
    public void exportPublicity(PublicidadSeminario publicidadSeminario) throws IOException {
        //Init Variables
        Map hm = (Map) new HashMap();
        JRExporter exporter = new JRPdfExporter();
        JasperPrint jasperPrint = null;
        JRBeanCollectionDataSource dataSource;
        InputStream inputStreamImagenCodigoQR;
        // Getting HttpServletResponse
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        // Setting Parameters
        hm.put(JRParameter.REPORT_LOCALE, new Locale("ES"));
        hm.put("idSeminarioParametro", publicidadSeminario.getIdSeminario());
        hm.put("imagenCodigoQR", new ByteArrayInputStream(
                generarCodigoQRSeminarioPublicidad(publicidadSeminario)));
        {
            List list = new ArrayList();
            list.add(publicidadSeminario);
            dataSource = new JRBeanCollectionDataSource(list, false);
        }
        try {
            jasperPrint = JasperFillManager.fillReport(getPathPublicidadJasper(), hm, dataSource);
        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        // Response settings
        response.reset();
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=seminario" + publicidadSeminario.getIdSeminario() + ".pdf");
        // Export the file
        try {
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            exporter.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            context.responseComplete();
        }
    }

    public byte[] generarCodigoQRSeminarioPublicidad(PublicidadSeminario publicidad) {
        DefinicionCodigoQR definicionCodigoQR = definicionCodigoQRFacade.findDefinicionCodigoQR();
        String texto = definicionCodigoQR.getPreIdentificador() + "/"
                + publicidad.getIdSeminario() + "/"
                + new DateTime(publicidad.getFechaInicio()).getYear();
        return new CodigoQR().generar(texto, ImageType.PNG, 200, 200);
    }

    public byte[] generarCodigoQRSeminario() {
        DefinicionCodigoQR definicionCodigoQR = definicionCodigoQRFacade.findDefinicionCodigoQR();
        String texto = definicionCodigoQR.getPreIdentificador() + "/"
                + seminario.getIdSeminario() + "/"
                + new DateTime(seminario.getFechaInicio()).getYear();
        return new CodigoQR().generar(texto, ImageType.PNG, 200, 200);
    }

    public Seminario getSeminario() {
        return seminario;
    }

    public void setSeminario(Seminario seminario) {
        this.seminario = seminario;
    }

}
