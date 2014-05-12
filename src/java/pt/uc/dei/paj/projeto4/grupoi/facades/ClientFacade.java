/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.facades;

import java.util.Random;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Client;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.LoginInvalidateException;

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

    public ClientFacade() {
        super(Client.class);
    }

    /**
     * Validate login.
     *
     * @param email
     * @return Client
     */
    private Client validateLogin(String email, String password) throws LoginInvalidateException {

        Query q = em.createNamedQuery("Client.findClientByEmail");
        q.setParameter("email", email).setParameter("password", password);

        try {
            return (Client) q.getSingleResult();
        } catch (Exception e) {
            throw new LoginInvalidateException();
        }
    }

    /**
     * Check if the API Key already exist registered in data base.
     *
     * @param apiKey
     * @return Long
     */
    public Long checkApiExistence(double apiKey) {

        try {
            Query q = em.createNamedQuery("Client.findClientIdByApiKey");
            q.setParameter("apikey", apiKey);
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Generates a unique API Key in data model.
     *
     * @return double
     */
    private double generateApiKey() {

        Random rg = new Random();
        double apiKey;

        do {
            apiKey = rg.nextDouble();
        } while (checkApiExistence(apiKey) != null);

        return apiKey;

    }

    /**
     * Performes login action. Check if the user already exists in database, if
     * so, check if password matches. If yes, user is saved in session bean.
     *
     * @param email
     * @param password
     * @return boolean
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.LoginInvalidateException
     */
    public double login(String email, String password) throws LoginInvalidateException {
        Client c = validateLogin(email, password);
        double key = generateApiKey();
        c.setApiKey(key);
        return key;
    }

    public Client getClientByApiKey(double apiKey) throws ClientNotFoundException {
        Query q = em.createNamedQuery("Client.findClientByApiKey");
        q.setParameter("apikey", apiKey);
        return (Client) q.getSingleResult();
    }
}
