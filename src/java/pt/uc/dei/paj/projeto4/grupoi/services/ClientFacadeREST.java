/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.services;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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
    private LogFacade logFacade;
    @Inject
    private ClientFacade clientFacade;
    private Log log;
    private String password;
    private Date today;

    public ClientFacadeREST() {

    }

    @PostConstruct
    public void init() {

        GregorianCalendar gc = new GregorianCalendar();
        today = gc.getTime();

    }

    @GET
    @Path("{email}")
    @Produces({"application/json"})
    public double findByDescription(@Context HttpHeaders header, @PathParam("email") String email) throws LoginInvalidateException {
        log = new Log();
        try {
            password = header.getRequestHeaders().getFirst("password");
            double key = clientFacade.login(email, password);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("login() - Success");
            log.setParam("Email - " + email + " || Password - " + password);
            logFacade.create(log);
            return key;
        } catch (LoginInvalidateException e) {

            log.setClientId(null);
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("login() - Failed | Cause : " + e.getMessage());
            log.setParam("Email - " + email + " || Password - " + password);
            logFacade.create(log);
            throw new LoginInvalidateException();

        }
    }

}
