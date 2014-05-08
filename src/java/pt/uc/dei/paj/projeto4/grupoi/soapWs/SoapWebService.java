/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.soapWs;

import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderReceived;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;
import pt.uc.dei.paj.projeto4.grupoi.facades.ClientFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.OrderReceivedFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.ProductFacade;
import pt.uc.dei.paj.projeto4.grupoi.utilities.LoginInvalidateException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotCreatedException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ProductNotFoundException;

/**
 *
 * @author Zueb LDA
 */
@WebService(serviceName = "SoapWebService")
@Stateless()
public class SoapWebService {

    @Inject
    private ProductFacade productFacade;
    @Inject
    private OrderReceivedFacade orderReceivedFacade;
    @Inject
    private ClientFacade clientFacade;

    /**
     * Web service operation
     *
     * @param category
     * @return List<Product>
     */
    @WebMethod(operationName = "getProductsByCategory")
    public List<Product> getProductsByCategory(@WebParam(name = "category") String category) {
        return productFacade.findProductsByCategory(category);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "makeOrderTest")
    public String makeOrderTest(@WebParam(name = "productId") long productId, @WebParam(name = "quantity") int quantity) {

        return orderReceivedFacade.makeOrderTest(productId, quantity);
    }

    /**
     * Login Method. Returns API Key to client if the login process went well
     * the method will return API Key to client, if went wrong
     *
     * @param email
     * @param password
     * @return
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.LoginInvalidateException
     */
    @WebMethod(operationName = "login")
    public double login(@WebParam(name = "email") String email, @WebParam(name = "password") String password) throws LoginInvalidateException {

        return clientFacade.login(email, password);

//            throw new NotAuthorizedException(Response.Status.UNAUTHORIZED);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findProductByDesignation")
    public List<Product> findProductsByDesignation(@WebParam(name = "word") String word) {

        return productFacade.findProductsByDesignation(word);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findProductsByDescription")
    public List<Product> findProductsByDescription(@WebParam(name = "word") String word) {

        return productFacade.findproductsByDescription(word);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findProductsByCategory")
    public List<Product> findProductsByCategory(@WebParam(name = "word") String word) {

        return productFacade.findProductsByCategory(word);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findAllProducts")
    public List<Product> findAllProducts() {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findStockByProduct")
    public int findStockByProduct(@WebParam(name = "productId") long productId) throws ProductNotFoundException {

        return productFacade.findStockByProduct(productId);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "replacementDateByProduct")
    public String replacementDateByProduct(@WebParam(name = "productId") long productId) throws ProductNotFoundException {

        return productFacade.findReplacementDateByProduct(productId);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findProductById")
    public Product findProductById(@WebParam(name = "productId") long productId) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "makeOrder")
    public String makeOrder(@WebParam(name = "parameter") Map<Long, Integer> map) throws OrderNotCreatedException {

        return orderReceivedFacade.makeOrder(map);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findAllOrders")
    public List<OrderReceived> findAllOrders() {

        return orderReceivedFacade.findAll();
    }

}
