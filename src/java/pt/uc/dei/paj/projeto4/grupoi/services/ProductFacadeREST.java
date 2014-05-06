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
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;
import pt.uc.dei.paj.projeto4.grupoi.facades.ProductFacade;

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
    @GET
    @Produces({"application/json"})
    public List<Product> findAll() {
        return produtFacade.findAll();
    }

//    @GET
//    @Produces({"application/json"})
//    public List<Product> findByDesignation() {
//        return produtFacade.findAll();
//    }
//
//    @GET
//    @Produces({"application/json"})
//    public List<Product> findByDescription() {
//        return produtFacade.findAll();
//    }
//
    @GET
    @Path("{category}")
    @Produces({"application/json"})
    public List<Product> findByCategory(@PathParam("category") String category) {
        return produtFacade.findProductsByCategory(category);
    }
//
//    @GET
//    @Produces({"application/json"})
//    public List<Product> findStockByProduct() {
//        return produtFacade.findAll();
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
