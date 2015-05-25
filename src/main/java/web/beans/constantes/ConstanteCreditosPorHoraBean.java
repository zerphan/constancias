/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.constantes;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.entidades.Constantes;
import modelo.facade.ConstantesFacade;
import web.beans.util.JsfUtil;

/**
 *
 * @author Gandhi
 */
@Named("constanteCreditosPorHoraBean")
@ViewScoped
public class ConstanteCreditosPorHoraBean extends JsfUtil
        implements Serializable {
    
    @Inject
    ConstantesFacade constantesFacade;
    
    private Constantes creditosPorHora;
    
    public ConstanteCreditosPorHoraBean() {
        creditosPorHora = new Constantes();
    }
    
    public void findCreditosPorHora() {
        creditosPorHora = constantesFacade.findCreditosPorHora();
        if (creditosPorHora == null) {
            creditosPorHora = new Constantes();
        }
    }
    
    public void editar() {
        try {
            constantesFacade.edit(creditosPorHora);
            addSuccessMessage("Editado satisfactoriamente");
        } catch (Exception e) {
            addErrorMessage("Error al editar: "+e.toString());
        }
    }

    public Constantes getCreditosPorHora() {
        return creditosPorHora;
    }
    
    public void setCreditosPorHora(Constantes creditosPorHora) {
        this.creditosPorHora = creditosPorHora;
    }
    
}
