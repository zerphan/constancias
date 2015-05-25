package web.beans.documentos;

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
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.CodigoQR;
import modelo.PublicidadSeminario;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.joda.time.DateTime;
import web.beans.asistentes.AsistentesBean;
import web.beans.util.JsfUtil;
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
        Map hm = (Map) new HashMap();
        byte[] pdfBytes;
        System.out.println(currentSeminario.getTituloPonencia());
        hm.put("NOMBRE_PONENTE", currentSeminario.getPonente().getNombreCompleto());
        System.out.println("5");
        hm.put("TITULO_SEMINARIO", currentSeminario.getTituloPonencia());
        hm.put("LUGAR_SEMINARIO", currentSeminario.getDireccion());
        hm.put("FECHA_SEMINARIO", currentSeminario.getFechaInicio().toDate());
        hm.put("NOMBRE_ACREDITADOR_IZQUIERDA", currentSeminario.getAcreditador1().getNombreCompleto());
        hm.put("NOMBRE_ACREDITADOR_DERECHA", currentSeminario.getAcreditador2().getNombreCompleto());
        hm.put("PUESTO_ACREDITADOR_IZQUIERDA", currentSeminario.getAcreditador1().getDescripcion());
        hm.put("PUESTO_ACREDITADOR_DERECHA", currentSeminario.getAcreditador2().getDescripcion());
        // InputStream inputStreamImagenCodigoQR = new ByteArrayInputStream(
        //       generarCodigoQRSeminario()); 
        //hm.put("IMAGEN_CODIGOQR", inputStreamImagenCodigoQR);
        try {
            System.out.println("5");
            print = JasperFillManager.fillReport(getPathConstanciaPonenteJasper(), hm, new JREmptyDataSource());
            pdfBytes = JasperExportManager.exportReportToPdf(print);
            System.out.println("6");
            //Ponente ponente = currentSeminario.getPonente();
            //ponente.setArchivoConstancia(pdfBytes);
            //ponenteFacade.edit(ponente);
            return pdfBytes;
        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public void eventoExportDocumentoProbatorio(ActionEvent actionEvent) {
        //System.out.println("Entro a exportar documento probatorio");
        if (seminario.getFechaTermino().isBeforeNow()) {
            crearDocumentoProbatorio();
        }

    }

    private void crearDocumentoProbatorio() {
        Map hm = (Map) new HashMap();
        byte[] pdfBytes;
        hm.put("NOMBRE_PONENTE", seminario.getPonente().getNombreCompleto());
        hm.put("TITULO_SEMINARIO", seminario.getTituloPonencia());
        hm.put("TOTAL_ASISTENTES", "" + seminario.getAsistenteList().size());
        hm.put("FECHA_SEMINARIO", seminario.getFechaInicio().toDate());
        hm.put("FECHA_ACTUAL", new Date());
        InputStream inputStreamImagenCodigoQR = new ByteArrayInputStream(
                generarCodigoQRSeminario());
        hm.put("IMAGEN_CODIGOQR", inputStreamImagenCodigoQR);
        List list = seminario.getAsistenteList();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list, false);
        try {
            print = JasperFillManager.fillReport(getPathDocumentoProbatorioJasper(), hm, dataSource);
        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().responseComplete();
        getResponse().setContentType("application/pdf");
        getResponse().addHeader("Content-disposition", "attachment; filename=listaAsistentes"
                + seminario.getIdSeminario() + ".pdf");
        JRExporter exporter = new JRPdfExporter();
        //exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outPutFileName);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, getServletOutputStream());
        try {
            //exporter.exportReport();
            JasperExportManager.exportReportToPdfStream(print, getServletOutputStream());

        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            FacesContext.getCurrentInstance().responseComplete();
        }
        //addSuccessMessage("Publicidad generada satisfactoriamente");

    }

    public void eventoExportPublicidadPDF(ActionEvent actionEvent) {
        PublicidadSeminario publicidadSeminario = new PublicidadSeminario(seminario);
        try {
            exportPublicidadPDF(publicidadSeminario);
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

    public void exportPublicidadPDF(PublicidadSeminario publicidad) throws IOException {
        initPublicidad(publicidad);

        getResponse().addHeader("Content-disposition", "attachment; filename=seminario"
                + publicidad.getIdSeminario() + ".pdf");
        JRExporter exporter = new JRPdfExporter();
        //exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outPutFileName);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, getServletOutputStream());
        try {
            exporter.exportReport();
            //JasperExportManager.exportReportToPdfStream(print, getServletOutputStream());
        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        addSuccessMessage("Publicidad generada satisfactoriamente");

    }

    public void initPublicidad(PublicidadSeminario publicidad) {
        InputStream inputStreamImagenCodigoQR = new ByteArrayInputStream(
                generarCodigoQRSeminarioPublicidad(publicidad));
        Map hm = (Map) new HashMap();
        hm.put("idSeminarioParametro", publicidad.getIdSeminario());
        hm.put("imagenCodigoQR", inputStreamImagenCodigoQR);
        List list = new ArrayList();
        list.add(publicidad);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list, false);
        try {
            print = JasperFillManager.fillReport(getPathPublicidadJasper(), hm, dataSource);
        } catch (JRException ex) {
            Logger.getLogger(DocumentosBean.class.getName()).log(Level.SEVERE, null, ex);
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
