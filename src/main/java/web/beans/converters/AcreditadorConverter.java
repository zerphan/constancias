package web.beans.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import modelo.entidades.Acreditador;
import modelo.facade.AcreditadorFacade;

/**
 *
 * @author Gandhi
 */
@FacesConverter("AcreditadorConverter")
public class AcreditadorConverter implements Converter {

    @Inject
    private AcreditadorFacade facade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.equals("null")) {
            return null;
        }
        return (Acreditador) facade.find(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}