/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.services;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Log;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderReceived;
import pt.uc.dei.paj.projeto4.grupoi.facades.ClientFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.LogFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.OrderReceivedFacade;
import pt.uc.dei.paj.projeto4.grupoi.pojos.Item;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotFoundException;

/**
 *
 * @author Guilherme Pereira
 */
@Stateless
@Path("/orderreceived")
public class OrderReceivedFacadeREST {

    @Inject
    private LogFacade logFacade;
    @Inject
    private OrderReceivedFacade orderReceivedFacade;
    @Inject
    private ClientFacade clientFacade;
    private Log log;
    private String token;
    private Double key;
    private Date today;

    public OrderReceivedFacadeREST() {
    }

    @PostConstruct
    public void init() {

        GregorianCalendar gc = new GregorianCalendar();
        today = gc.getTime();

    }

    @POST
    @Consumes({"application/json"})
    public String makeOrder(@Context HttpHeaders header, List<Item> items) {
        this.log = new Log();
        System.out.println("entrou no server");
        System.out.println("key" + key);
        System.out.println("item" + items);
        try {
            token = header.getRequestHeaders().getFirst("key");
            key = Double.parseDouble(token);
            String message = orderReceivedFacade.makeOrder(items, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("makeOrder() - Success");
            log.setParam("Items - " + items.toString() + " || ApiKey - " + key);
            logFacade.create(log);
            return message;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("makeOrder() - Failed | Cause : " + e.getMessage());
            log.setParam("Items - " + items.toString() + " || ApiKey - " + key);
            logFacade.create(log);
            return "Order was not completed";
        }
    }

//    @PUT
//    @Path("{id}")
//    @Consumes({"application/xml", "application/json"})
//    public void edit(@PathParam("id") Long id, OrderReceived entity) {
//        super.edit(entity);
//    }
//
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {
//        super.remove(super.find(id));
//    }
    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public OrderReceived findOrder(@Context HttpHeaders header, @PathParam("id") Long id) throws ClientNotFoundException, OrderNotFoundException {
        this.log = new Log();
        try {
            token = header.getRequestHeaders().getFirst("key");
            key = Double.parseDouble(token);
            OrderReceived order = orderReceivedFacade.findorder(id, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findOrder() - Success");
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            return order;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findOrder() - Failed | Cause : " + e.getMessage());
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            throw new OrderNotFoundException();
        }
    }

    @GET
    @Produces({"application/json"})
    public List<OrderReceived> findOrderByClienId(@Context HttpHeaders header) throws ClientNotFoundException, OrderNotFoundException {
        this.log = new Log();
        try {
            token = header.getRequestHeaders().getFirst("key");
            key = Double.parseDouble(token);
            List<OrderReceived> orderList = orderReceivedFacade.findOrdersByClientId(id, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findOrdersByClientId() - Success");
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            return orderList;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findOrdersByClientId() - Failed | Cause : " + e.getMessage());
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            throw new OrderNotFoundException();
        }
    }

    @GET
    @Produces({"application/json"})
    public List<OrderReceived> findAllOrders(@Context HttpHeaders header) throws ClientNotFoundException, OrderNotFoundException {
        this.log = new Log();
        try {
            token = header.getRequestHeaders().getFirst("key");
            key = Double.parseDouble(token);
            List<OrderReceived> orderList = orderReceivedFacade.findAllOrders(key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findAllOrders() - Success");
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            return orderList;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findAllOrders() - Failed | Cause : " + e.getMessage());
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            throw new OrderNotFoundException();
        }
    }

    @GET
    @Path("delivery-date/{id}")
    @Produces({"text/plain"})
    public String orderDeliveryDate(@Context HttpHeaders header, @PathParam("id") Long id) throws ClientNotFoundException, OrderNotFoundException {
        this.log = new Log();
        try {
            token = header.getRequestHeaders().getFirst("key");
            key = Double.parseDouble(token);
            String date = orderReceivedFacade.orderDeliveryDate(id, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("orderDeliveryDate() - Success");
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            return date;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("orderDeliveryDate() - Failed | Cause : " + e.getMessage());
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            throw new OrderNotFoundException();
        }
    }

}
