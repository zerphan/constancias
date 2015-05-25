package modelo.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.entidades.Acreditador;
import modelo.entidades.Asistente;
import modelo.entidades.Ponente;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-05-25T14:19:51")
@StaticMetamodel(Seminario.class)
public class Seminario_ { 

    public static volatile SingularAttribute<Seminario, Acreditador> acreditador1;
    public static volatile SingularAttribute<Seminario, Acreditador> acreditador2;
    public static volatile SingularAttribute<Seminario, String> codigoSeguridad;
    public static volatile SingularAttribute<Seminario, Date> fechaInicio;
    public static volatile SingularAttribute<Seminario, Date> fechaTermino;
    public static volatile SingularAttribute<Seminario, String> lugar;
    public static volatile SingularAttribute<Seminario, Integer> idSeminario;
    public static volatile SingularAttribute<Seminario, String> direccion;
    public static volatile SingularAttribute<Seminario, Ponente> ponente;
    public static volatile ListAttribute<Seminario, Asistente> asistenteList;
    public static volatile SingularAttribute<Seminario, String> resumen;
    public static volatile SingularAttribute<Seminario, String> tituloPonencia;

}