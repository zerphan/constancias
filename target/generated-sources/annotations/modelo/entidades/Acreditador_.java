package modelo.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.entidades.GradoAcademico;
import modelo.entidades.Seminario;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-05-25T14:19:51")
@StaticMetamodel(Acreditador.class)
public class Acreditador_ { 

    public static volatile SingularAttribute<Acreditador, String> descripcion;
    public static volatile SingularAttribute<Acreditador, String> apellidoPaterno;
    public static volatile ListAttribute<Acreditador, Seminario> seminarioList2;
    public static volatile ListAttribute<Acreditador, Seminario> seminarioList1;
    public static volatile SingularAttribute<Acreditador, Date> fechaTermino;
    public static volatile SingularAttribute<Acreditador, Integer> id;
    public static volatile SingularAttribute<Acreditador, String> nombre;
    public static volatile SingularAttribute<Acreditador, GradoAcademico> gradoAcademico;
    public static volatile SingularAttribute<Acreditador, String> apellidoMaterno;

}