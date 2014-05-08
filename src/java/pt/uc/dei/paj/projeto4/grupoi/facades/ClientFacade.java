/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.facades;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import pt.uc.dei.paj.projeto4.grupoi.ejbs.SessionBean;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Client;
import pt.uc.dei.paj.projeto4.grupoi.utilities.Encrypt;

/**
 *
 * @author Zueb LDA
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "TecnoApiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private SessionBean sessionBean;

    public ClientFacade() {
        super(Client.class);
    }

    /**
     * ends session
     */
    private void invalidateSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
    }

    /**
     * Verify if client exists in data base.
     *
     * @param email
     * @return Client
     */
    private Client existUser(String email) {

        Query q = em.createNamedQuery("Client.findClientByEmail");
        q.setParameter("email", email);

        try {
            return (Client) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Performes login action. Check if the user already exists in database, if
     * so, check if password matches. If yes, user is saved in session bean.
     *
     * @param email
     * @param password
     * @return boolean
     */
    public boolean login(String email, String password) {

        Client c = existUser(email);

        if (c != null) {

            String passEncripted = Encrypt.cryptWithMD5(password);

            if (passEncripted.equals(c.getPassword())) {

                sessionBean.setClient(c);
                return true;
            } else {
                return false;
            }

        } else {

            return false;
        }

    }

    /**
     * Client logout method.
     */
    public void logOut() {

        sessionBean.setClient(null);
        invalidateSession();
    }
}
