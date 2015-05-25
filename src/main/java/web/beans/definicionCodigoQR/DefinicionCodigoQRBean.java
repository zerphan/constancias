package web.beans.definicionCodigoQR;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.entidades.DefinicionCodigoQR;
import modelo.facade.DefinicionCodigoQRFacade;
import org.joda.time.DateTime;
import web.beans.util.JsfUtil;

/**
 *
 * @author Gandhi
 */
@Named("definicionCodigoQRBean")
@ViewScoped
public class DefinicionCodigoQRBean extends JsfUtil
        implements Serializable {
    
    private DefinicionCodigoQR definicionCodigoQR;
    @Inject
    private DefinicionCodigoQRFacade definicionCodigoQRFacade;
    
    public DefinicionCodigoQRBean() {
    }
    
    @PostConstruct
    private void init() {
    }
    
    public void find() {
        definicionCodigoQR = definicionCodigoQRFacade.findDefinicionCodigoQR();
        if (definicionCodigoQR == null) {
            definicionCodigoQR = new DefinicionCodigoQR();
        }
    }
    
    public void editar() {
        try {
            definicionCodigoQR.setPostIdentificador( new DateTime().getYear()+"");
            definicionCodigoQRFacade.edit(definicionCodigoQR);
            addSuccessMessage("Editado Satisfactoriamente");
        } catch (Exception e) {
            addErrorMessage("Error al editar: " + e.toString());
        }
    }
    
    public DefinicionCodigoQR getDefinicionCodigoQR() {
        return definicionCodigoQR;
    }
    
    public void setDefinicionCodigoQR(DefinicionCodigoQR definicionCodigoQR) {
        this.definicionCodigoQR = definicionCodigoQR;
    }
    
}
