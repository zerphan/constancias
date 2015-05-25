/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import modelo.entidades.Asistente;

/**
 *
 * @author Gandhi
 */
@Stateless
public class AsistenteFacade extends AbstractFacade<Asistente> {

    @PersistenceContext(unitName = "constancias_MavenSistemaConstancias_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsistenteFacade() {
        super(Asistente.class);
    }

    public List<Asistente> findAllByIdSeminario(Integer idSeminario) {
        try {
            TypedQuery<Asistente> query = em.createNamedQuery(
                    "Asistente.findAllByIdSeminario",
                    Asistente.class);
            query.setParameter("idSeminario", idSeminario);
            List<Asistente> list = query.getResultList();
            return list;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public List<Asistente> findAllByEmail(String email) {
        try {
            TypedQuery<Asistente> query = em.createNamedQuery(
                    "Asistente.findByEmail",
                    Asistente.class);
            query.setParameter("email", email);
            List<Asistente> list = query.getResultList();
            return list;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
