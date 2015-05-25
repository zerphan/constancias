package web.beans.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Constantes;
import modelo.OSValidator;
import org.joda.time.DateTime;
import web.beans.administracion.AdministradorBean;

@Named("jsfUtil")
@RequestScoped
public class JsfUtil implements Serializable {

    @Inject
    AdministradorBean administradorBean;

    public TimeZone getTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone;
    }

    public Date getFechaActual() {
        return new Date();
    }

    public static void redirectFromContextPath(String ruta) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/" + ruta);
        } catch (IOException ex) {
            Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage("Error al redireccionar: " + ruta);
        }
    }

    public DateTime transformarFecha(Date date) {
        return new DateTime(date);
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public void isLoginIn() {
        if (administradorBean == null) {
            redirectFromContextPath("proximosSeminarios.xhtml");
        } else if (administradorBean.getAdministrador() == null) {
            redirectFromContextPath("proximosSeminarios.xhtml");
        } else {
            return;
        }
    }

    public void isLoginInWithRoleAdvance() {
        if (administradorBean == null) {
            redirectFromContextPath("proximosSeminarios.xhtml");
        } else if (administradorBean.getAdministrador() == null) {
            redirectFromContextPath("proximosSeminarios.xhtml");
        } else if (!administradorBean.getAdministrador().getAdministracionAvanzada()) {
            limpiarSesion();
            redirectFromContextPath("proximosSeminarios.xhtml");
        } else {
            return;
        }
    }

    public void limpiarSesion() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
        session.invalidate();
        //JsfUtil.addSuccessMessage("Limpiar sesion");
        System.out.println("Entro a limpiar sesion");
    }

    public static String getPathPublicidadJasper() {
        String path = getExternalContext().getRealPath("/");
        if (OSValidator.isWindows()) {
            path += Constantes.FILE_PUBLICIDAD_SEMINARIO_JASPER_WINDOWS;
        } else if (OSValidator.isUnix()) {
            path += Constantes.FILE_PUBLICIDAD_SEMINARIO_JASPER_UNIX;
        } else {
            return "Sistema Operativo no soportado";
        }
        return path;
    }

    public static String getPathDocumentoProbatorioJasper() {
        String path = getExternalContext().getRealPath("/");
        if (OSValidator.isWindows()) {
            path += Constantes.FILE_DOCUMENTO_PROBATORIO_JASPER_WINDOWS;
        } else if (OSValidator.isUnix()) {
            path += Constantes.FILE_DOCUMENTO_PROBATORIO_JASPER_UNIX;
        } else {
            return "Sistema Operativo no soportado";
        }
        return path;
    }

    public static String getPathConstanciaAsistenciaJasper() {
         String path = getExternalContext().getRealPath("/");
        if (OSValidator.isWindows()) {
            path += Constantes.FILE_CONSTANCIA_ASISTENCIA_JASPER_WINDOWS;
        } else if (OSValidator.isUnix()) {
            path += Constantes.FILE_CONSTANCIA_ASISTENCIA_JASPER_UNIX;
        } else {
            return "Sistema Operativo no soportado";
        }
        return path;        
    }

    public static String getPathConstanciaPonenteJasper() {
         String path = getExternalContext().getRealPath("/");
        if (OSValidator.isWindows()) {
            path += Constantes.FILE_CONSTANCIA_PONENTE_JASPER_WINDOWS;
        } else if (OSValidator.isUnix()) {
            path += Constantes.FILE_CONSTANCIA_PONENTE_JASPER_UNIX;
        } else {
            return "Sistema Operativo no soportado";
        }
        return path;
    }

    private static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static HttpServletResponse getResponse() {
        HttpServletResponse httpServletResponse = (HttpServletResponse) getExternalContext().getResponse();
        return httpServletResponse;
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest httpServletRequest = (HttpServletRequest) getExternalContext().getRequest();
        return httpServletRequest;
    }

    public static ServletOutputStream getServletOutputStream() {
        try {
            return getResponse().getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
