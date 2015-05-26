package modelo.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.entidades.GradoAcademico;
import modelo.entidades.Seminario;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-05-26T11:17:58")
@StaticMetamodel(Ponente.class)
public class Ponente_ { 

    public static volatile SingularAttribute<Ponente, String> apellidoPaterno;
    public static volatile SingularAttribute<Ponente, Date> fecha;
    public static volatile SingularAttribute<Ponente, String> cv;
    public static volatile SingularAttribute<Ponente, Seminario> seminario;
    public static volatile SingularAttribute<Ponente, byte[]> archivoConstancia;
    public static volatile SingularAttribute<Ponente, String> nombre;
    public static volatile SingularAttribute<Ponente, GradoAcademico> gradoAcademico;
    public static volatile SingularAttribute<Ponente, String> email;
    public static volatile SingularAttribute<Ponente, Integer> idPonente;
    public static volatile SingularAttribute<Ponente, String> apellidoMaterno;

}