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
import modelo.entidades.Administrador;

/**
 *
 * @author Gandhi
 */
@Stateless
public class AdministradorFacade extends AbstractFacade<Administrador> {

    @PersistenceContext(unitName = "constancias_MavenSistemaConstancias_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministradorFacade() {
        super(Administrador.class);
    }

    public Administrador doLogin(String username, String password) {
        try {
            TypedQuery<Administrador> query = em.createNamedQuery(
                    "Administrador.doLogin",
                    Administrador.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            Administrador admin = query.getSingleResult();
            return admin;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public Administrador findByUsername(String username) {
        try {
            TypedQuery<Administrador> query = em.createNamedQuery(
                    "Administrador.findByUsername",
                    Administrador.class);
            query.setParameter("username", username);
            Administrador admin = query.getSingleResult();
            return admin;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    public Administrador findByEmail(String email) {
        try {
            TypedQuery<Administrador> query = em.createNamedQuery(
                    "Administrador.findByEmail",
                    Administrador.class);
            query.setParameter("email", email);
            Administrador admin = query.getSingleResult();
            return admin;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

}
