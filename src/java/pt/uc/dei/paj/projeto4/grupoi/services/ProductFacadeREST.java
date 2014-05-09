/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    @Inject
    private ClientFacade clientFacade;

    public ProductFacadeREST() {

    }

    @GET
    @Produces({"application/json"})
    public List<Product> findAllProducts(@PathParam("key") double key) throws ProductNotFoundException {
        try {
            List<Product> p = productFacade.findAll();
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findAllProducts() - Success");
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findAllProducts() - Failed | Cause : " + e.getMessage());
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    @GET
    @Path("description/{description}")
    @Produces({"application/json"})
    public List<Product> findByDescription(@PathParam("description") String description, @PathParam("key") double key) throws ProductNotFoundException {
        try {
            List<Product> p = productFacade.findproductsByDescription(description, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findProductsByDescription() - Success");
            log.setParam("Description - " + description + " || ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {

            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findProductsByDescription() - Failed | Cause : " + e.getMessage());
            log.setParam("Description - " + description + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    @GET
    @Path("category/{category}")
    @Produces({"application/json"})
    public List<Product> findByCategory(@PathParam("category") String category, @PathParam("key") double key) throws ProductNotFoundException {
        try {
            List<Product> p = productFacade.findProductsByCategory(category, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findProductsByCategory() - Success");
            log.setParam("Category - " + category + " || ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {

            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findProductsByCategory() - Failed | Cause : " + e.getMessage());
            log.setParam("Category - " + category + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    @GET
    @Path("designation/{designation}")
    @Produces({"application/json"})
    public List<Product> findByDesignation(@PathParam("designation") String designation, @PathParam("key") double key) throws ProductNotFoundException {
        try {
            List<Product> p = productFacade.findProductsByDesignation(designation, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findProductsByDesignation() - Success");
            log.setParam("Designation - " + designation + " || ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findProductsByDesignation() - Failed | Cause : " + e.getMessage());
            log.setParam("Designation - " + designation + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    @GET
    @Path("stock/{id}")
    @Produces({"text/plain"})
    public int findStockByProduct(@PathParam("id") Long id, @PathParam("key") double key) throws ProductNotFoundException {

        try {
            int stock = productFacade.findStockByProduct(id, key);
            log.setClientId(clientFacade.checkApiExistence(key));
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
    public String findReplacementDateByProduct(@PathParam("id") Long id, @PathParam("key") double key) throws ProductNotFoundException {
        try {
            String replaceDate = productFacade.findReplacementDateByProduct(id, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findReplacementDateByProduct() - Success");
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            return replaceDate;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
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
    public Product find(@PathParam("id") Long id, @PathParam("key") double key) throws ProductNotFoundException {

        try {
            Product p = productFacade.findProductById(id, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findProductById() - Success");
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setInvokedService("RestWs");
            log.setTask("findProductById() - Failed | Cause : " + e.getMessage());
            log.setParam("ProductId - " + id + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    //    @POST
//    @Override
//    @Consumes({"application/xml", "application/json"})
//    public void create(Product entity) {
//        super.create(entity);
//    }
//
//    @PUT
//    @Path("{id}")
//    @Consumes({"application/xml", "application/json"})
//    public void edit(@PathParam("id") Long id, Product entity) {
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
//    public Product find(@PathParam("id") Long id) {
//        return super.find(id);
//    }
//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/json"})
//    public List<Product> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
}
