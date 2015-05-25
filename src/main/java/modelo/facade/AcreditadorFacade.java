package modelo.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.entidades.Acreditador;

/**
 *
 * @author Gandhi
 */
@Stateless
public class AcreditadorFacade extends AbstractFacade<Acreditador> {

    @PersistenceContext(unitName = "constancias_MavenSistemaConstancias_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AcreditadorFacade() {
        super(Acreditador.class);
    }

    public Acreditador findByDescripcion() {
        try {
            List<Acreditador> personaList;
            personaList = findAll();
            if (!personaList.isEmpty()) {
                return this.findAll().get(0);
            }
            return null;
        } catch (Exception e) {
            //System.out.println(e.toString());
            return null;
        }
    }
}
