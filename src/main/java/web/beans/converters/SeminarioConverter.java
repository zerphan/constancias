package web.beans.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import modelo.entidades.Seminario;
import modelo.facade.SeminarioFacade;

/**
 *
 * @author Gandhi
 */

@FacesConverter("SeminarioConverter")
public class SeminarioConverter implements Converter {

    @Inject
    private SeminarioFacade facade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.equals("null")) {
            return null;
        }
        return (Seminario) facade.find(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}