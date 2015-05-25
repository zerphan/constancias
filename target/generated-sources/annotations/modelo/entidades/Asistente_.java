package modelo.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.entidades.Seminario;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-05-25T14:19:51")
@StaticMetamodel(Asistente.class)
public class Asistente_ { 

    public static volatile SingularAttribute<Asistente, String> apellidoPaterno;
    public static volatile SingularAttribute<Asistente, Date> fecha;
    public static volatile SingularAttribute<Asistente, Seminario> seminario;
    public static volatile SingularAttribute<Asistente, Integer> idAsistente;
    public static volatile SingularAttribute<Asistente, String> noBoleta;
    public static volatile SingularAttribute<Asistente, byte[]> archivoConstancia;
    public static volatile SingularAttribute<Asistente, String> nombre;
    public static volatile SingularAttribute<Asistente, String> email;
    public static volatile SingularAttribute<Asistente, String> apellidoMaterno;

}