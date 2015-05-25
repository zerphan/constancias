/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import modelo.entidades.GradoAcademico;
import modelo.facade.GradoAcademicoFacade;

/**
 *
 * @author Gandhi
 */
@FacesConverter("GradoAcademicoConverter")
public class GradoAcademicoConverter implements Converter {

    @Inject
    private GradoAcademicoFacade facade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.equals("null")) {
            return null;
        }
        return (GradoAcademico)facade.find(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
