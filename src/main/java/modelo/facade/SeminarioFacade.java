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
import modelo.entidades.Seminario;

/**
 *
 * @author Gandhi
 */
@Stateless
public class SeminarioFacade extends AbstractFacade<Seminario> {

    @PersistenceContext(unitName = "constancias_MavenSistemaConstancias_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeminarioFacade() {
        super(Seminario.class);
    }

    public List<Seminario> findSeminariosProximos() {
        try {
            TypedQuery<Seminario> query = em.createNamedQuery(
                    "Seminario.findSeminariosProximos",
                    Seminario.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
    public List<Seminario> findSeminariosConcluidos() {
        try {
            TypedQuery<Seminario> query = em.createNamedQuery(
                    "Seminario.findSeminariosConcluidos",
                    Seminario.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
     public List<Seminario> findSeminariosEnEjecucion() {
        try {
            TypedQuery<Seminario> query = em.createNamedQuery(
                    "Seminario.findSeminariosEnEjecucion",
                    Seminario.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

}
