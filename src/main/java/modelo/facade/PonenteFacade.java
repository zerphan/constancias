/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.entidades.Ponente;

/**
 *
 * @author Gandhi
 */
@Stateless
public class PonenteFacade extends AbstractFacade<Ponente> {
    @PersistenceContext(unitName = "constancias_MavenSistemaConstancias_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PonenteFacade() {
        super(Ponente.class);
    }
    
}
