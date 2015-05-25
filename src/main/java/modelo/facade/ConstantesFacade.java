/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import modelo.entidades.Constantes;

/**
 *
 * @author Gandhi
 */
@Stateless
public class ConstantesFacade extends AbstractFacade<Constantes> {

    @PersistenceContext(unitName = "constancias_MavenSistemaConstancias_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConstantesFacade() {
        super(Constantes.class);
    }

    public Constantes findCreditosPorHora() {
        try {
            TypedQuery<Constantes> query = em.createNamedQuery("Constantes.creditosPorHora", Constantes.class);
            //query.setParameter("nombre", "creditosporhora");
            Constantes c = query.getSingleResult();
            return c;
        } catch (Exception e) {
            return null;
        }
    }

}
