/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;
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
    ProductFacade produtFacade;

    public ProductFacadeREST() {

    }

    @GET
    @Produces({"application/json"})
    public List<Product> findAll() {
        return produtFacade.findAll();
    }

    @GET
    @Path("description/{description}")
    @Produces({"application/json"})
    public List<Product> findByDescription(@PathParam("description") String description) {
        return produtFacade.findproductsByDescription(description);
    }

    @GET
    @Path("category/{category}")
    @Produces({"application/json"})
    @WebMethod(operationName = "getProductsByCategory")
    public List<Product> findByCategory(@PathParam("category") @WebParam(name = "category") String category) {
        return produtFacade.findProductsByCategory(category);
    }

    @GET
    @Path("designation/{designation}")
    @Produces({"application/json"})
    public List<Product> findByDesignation(@PathParam("designation") String designation) {
        return produtFacade.findProductsByDesignation(designation);
    }

    @GET
    @Path("stock/{id}")
    @Produces({"text/plain"})
    public int findStockByProduct(@PathParam("id") Long id) throws ProductNotFoundException {
        return produtFacade.findStockByProduct(id);
    }

    @GET
    @Path("replacement-Date/{id}")
    @Produces({"text/plain"})
    public String findReplacementDateByProduct(@PathParam("id") Long id) throws ProductNotFoundException {
        return produtFacade.findReplacementDateByProduct(id);
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
