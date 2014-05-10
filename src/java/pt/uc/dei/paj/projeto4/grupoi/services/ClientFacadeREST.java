/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.services;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Log;
import pt.uc.dei.paj.projeto4.grupoi.facades.ClientFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.LogFacade;
import pt.uc.dei.paj.projeto4.grupoi.utilities.LoginInvalidateException;

/**
 *
 * @author Guilherme Pereira
 */
@Stateless
@Path("/client")
public class ClientFacadeREST {

    @Inject
    private ClientFacade clientFacade;
    @Inject
    private LogFacade logFacade;
    private Log log;

    public ClientFacadeREST() {
    }

    @PostConstruct
    public void init() {

        log = new Log();
    }
//TEM Q SER POST!!!! ALTERAR ISTO!!!!

    @GET
    @Path("login/{email}")//FALTA A PASSWORD
    @Produces({"application/json"})
    public double login(@PathParam("email") String email, @PathParam("password") String password) throws LoginInvalidateException {
        try {
            double key = clientFacade.login(email, password);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("login() - Success");
            log.setParam("Email - " + email + " || Password - " + password);
            logFacade.create(log);
            return key;
        } catch (LoginInvalidateException e) {

            log.setClientId(null);
            log.setInvokedService("RestWs");
            log.setTask("login() - Failed | Cause : " + e.getMessage());
            log.setParam("Email - " + email + " || Password - " + password);
            logFacade.create(log);
            throw new LoginInvalidateException();

        }
    }

//    @POST
//    @Override
//    @Consumes({"application/xml", "application/json"})
//    public void create(Client entity) {
//        super.create(entity);
//    }
//
//    @PUT
//    @Path("{id}")
//    @Consumes({"application/xml", "application/json"})
//    public void edit(@PathParam("id") Long id, Client entity) {
//        super.edit(entity);
//    }
//
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {
//        super.remove(super.find(id));
//    }
//
//    @GET
//    @Path("{id}")
//    @Produces({"application/xml", "application/json"})
//    public Client find(@PathParam("id") Long id) {
//        return super.find(id);
//    }
//
//    @GET
//    @Override
//    @Produces({"application/xml", "application/json"})
//    public List<Client> findAll() {
//        return super.findAll();
//    }
//
//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Client> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }
}
