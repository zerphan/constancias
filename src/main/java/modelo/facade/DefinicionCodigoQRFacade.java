package modelo.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.entidades.DefinicionCodigoQR;

/**
 *
 * @author Gandhi
 */
@Stateless
public class DefinicionCodigoQRFacade extends AbstractFacade<DefinicionCodigoQR> {

    @PersistenceContext(unitName = "constancias_MavenSistemaConstancias_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DefinicionCodigoQRFacade() {
        super(DefinicionCodigoQR.class);
    }

    public DefinicionCodigoQR findDefinicionCodigoQR() {
        try {
            List<DefinicionCodigoQR> list;
            list = findAll();
            if (!list.isEmpty()) {
                return this.findAll().get(0);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
