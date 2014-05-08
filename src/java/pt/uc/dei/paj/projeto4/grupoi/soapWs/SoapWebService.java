/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.soapWs;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;
import pt.uc.dei.paj.projeto4.grupoi.facades.ClientFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.OrderReceivedFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.ProductFacade;
import pt.uc.dei.paj.projeto4.grupoi.utilities.LoginInvalidateException;

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
     * This is a sample web service operation
     *
     * @param txt
     * @return
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

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
     */
    @WebMethod(operationName = "login")
    public double login(@WebParam(name = "email") String email, @WebParam(name = "password") String password) {
        try {
            return clientFacade.login(email, password);
        } catch (LoginInvalidateException ex) {
            throw new NotAuthorizedException(Response.Status.UNAUTHORIZED);
        }
    }

}
