/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Log;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;
import pt.uc.dei.paj.projeto4.grupoi.facades.ClientFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.LogFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.ProductFacade;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ProductNotFoundException;

/**
 *
 * @author Guilherme Pereira
 */
@Stateless
@Path("/product")
public class ProductFacadeREST {

    @Inject
    ProductFacade productFacade;
    @Inject
    private LogFacade logFacade;
    private Log log;
    private String token;
    private Double key;
    @Inject
    private ClientFacade clientFacade;
    private Date today;

    public ProductFacadeREST() {

    }

    @PostConstruct
    public void init() {

        GregorianCalendar gc = new GregorianCalendar();
        today = gc.getTime();

    }

    @GET
    @Produces({"application/json"})
    public List<Product> findProducts(@Context HttpHeaders header, @DefaultValue("") @QueryParam("description") String description,
            @DefaultValue("") @QueryParam("designation") String designation,
            @DefaultValue("") @PathParam("category") String category) throws ProductNotFoundException {
        this.log = new Log();

        try {
            List<Product> p = new ArrayList();
            token = header.getRequestHeaders().getFirst("key");
            key = Double.parseDouble(token);
            if (description.isEmpty() && designation.isEmpty() && category.isEmpty()) {
                p = productFacade.findAllProducts(key);
            } else if (!designation.isEmpty()) {
                p = productFacade.findProductsByDesignation(designation, key);
            } else if (!category.isEmpty()) {
                p = productFacade.findProductsByCategory(category, key);
            } else {
                p = productFacade.findproductsByDescription(description, key);
            }
            //log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findProducts() - Success");
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {
            //log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findProducts() - Failed | Cause : " + e.getMessage());
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    @GET
    @Path("{id}/stock")
    @Produces({"text/plain"})
    public int findStockByProduct(@Context HttpHeaders header, @PathParam("id") Long id) throws ProductNotFoundException {
        this.log = new Log();
        try {
            token = header.getRequestHeaders().getFirst("key");
            key = Double.parseDouble(token);
            int stock = productFacade.findStockByProduct(id, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findStockByProduct() - Success");
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            return stock;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findStockByProduct() - Failed | Cause : " + e.getMessage());
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    @GET
    @Path("replacement-Date/{id}")
    @Produces({"text/plain"})
    public String findReplacementDateByProduct(@Context HttpHeaders header, @PathParam("id") Long id) throws ProductNotFoundException {
        this.log = new Log();
        try {
            token = header.getRequestHeaders().getFirst("key");
            key = Double.parseDouble(token);
            String replaceDate = productFacade.findReplacementDateByProduct(id, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findReplacementDateByProduct() - Success");
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            return replaceDate;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findReplacementDateByProduct() - Failed | Cause : " + e.getMessage());
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Product find(@Context HttpHeaders header, @PathParam("id") Long id) throws ProductNotFoundException {
        this.log = new Log();
        try {
            token = header.getRequestHeaders().getFirst("key");
            key = Double.parseDouble(token);
            Product p = productFacade.findProductById(id, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findProductById() - Success");
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("RestWs");
            log.setTask("findProductById() - Failed | Cause : " + e.getMessage());
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }
}
