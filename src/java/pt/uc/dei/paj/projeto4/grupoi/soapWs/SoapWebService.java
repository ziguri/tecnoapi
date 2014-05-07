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
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;
import pt.uc.dei.paj.projeto4.grupoi.facades.OrderReceivedFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.ProductFacade;

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

    /**
     * This is a sample web service operation
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

}
