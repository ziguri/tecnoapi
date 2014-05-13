/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.services;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Log;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderItems;
import pt.uc.dei.paj.projeto4.grupoi.facades.ClientFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.LogFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.OrderItemsFacade;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ItemNotFoundException;

/**
 *
 * @author Guilherme Pereira
 */
@Stateless
@Path("/orderitems")
public class OrderItemsFacadeREST {

    @Inject
    private OrderItemsFacade orderItemsFacade;
    @Inject
    private ClientFacade clientFacade;
    @Inject
    private LogFacade logFacade;
    private Log log;
    private String token;
    private Double key;
    private Date today;

    public OrderItemsFacadeREST() {
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public List<OrderItems> findOrderItems(@Context HttpHeaders header, @PathParam("id") Long id) throws ClientNotFoundException, ItemNotFoundException {
        this.log = new Log();
        try {
            token = header.getRequestHeaders().getFirst("key");
            key = Double.parseDouble(token);
            List<OrderItems> orderList = orderItemsFacade.findAllItemsFromOrder(id, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findAllItemsFromOrder() - Success");
            log.setParam("orderId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            return orderList;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findAllItemsFromOrder() - Failed");
            log.setParam("orderId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ItemNotFoundException();

        }
    }

}
