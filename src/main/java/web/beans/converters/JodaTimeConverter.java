package web.beans.converters;

import java.util.Date;
import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.joda.time.DateTime;

/**
 *
 * @author Gandhi
 */
@FacesConverter("JodaTimeConverter")
public class JodaTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("Entro a get as Object: "+value);
        System.err.println("Entro a get as Object: "+value);
        if (value.equals("null")) {
            return null;
        }
        Date fecha = new Date(value);
        DateTime fechaJoda = new DateTime(fecha);
        return fecha;
        //return (DateTime) new DateTime(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println("Entro a get as String:"+value.toString());
        System.out.println(new Date());
        System.out.println(new DateTime());        
//        Date fecha = new Date(value.toString());
        DateTime fechaJoda = new DateTime(value);
        String fechaDateString = fechaJoda.toDate().toString();
        System.out.println("Convertido:"+fechaDateString);
        //return fecha.toString();
       // return fechaJoda.toCalendar(Locale.getDefault()).toString();
        return new Date().toGMTString();
    }

}
