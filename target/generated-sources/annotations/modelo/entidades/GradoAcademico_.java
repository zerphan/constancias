package modelo.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.entidades.Acreditador;
import modelo.entidades.Ponente;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-05-26T11:17:58")
@StaticMetamodel(GradoAcademico.class)
public class GradoAcademico_ { 

    public static volatile ListAttribute<GradoAcademico, Ponente> ponenteList;
    public static volatile SingularAttribute<GradoAcademico, String> abreviacion;
    public static volatile ListAttribute<GradoAcademico, Acreditador> acreditadorList;
    public static volatile SingularAttribute<GradoAcademico, Integer> id;
    public static volatile SingularAttribute<GradoAcademico, String> nombre;

}