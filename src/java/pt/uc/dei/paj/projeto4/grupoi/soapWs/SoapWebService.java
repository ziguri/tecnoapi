/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.soapWs;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Log;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderItems;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderReceived;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;
import pt.uc.dei.paj.projeto4.grupoi.facades.ClientFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.LogFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.OrderItemsFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.OrderReceivedFacade;
import pt.uc.dei.paj.projeto4.grupoi.facades.ProductFacade;
import pt.uc.dei.paj.projeto4.grupoi.pojos.Item;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ItemNotFoundException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.LoginInvalidateException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotCreatedException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotFoundException;
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
    @Inject
    private LogFacade logFacade;
    @Inject
    private OrderItemsFacade itemsFacade;
    private Log log;

    private Date today;

    @PostConstruct
    public void init() {

        GregorianCalendar gc = new GregorianCalendar();
        today = gc.getTime();
    }

    /**
     * Find and return all products from one category.
     *
     * @param category
     * @param key
     * @return List<Product>
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ProductNotFoundException
     */
    @WebMethod(operationName = "getProductsByCategory")
    public List<Product> getProductsByCategory(@WebParam(name = "category") String category, @WebParam(name = "key") double key) throws ProductNotFoundException {
        log = new Log();
        try {
            List<Product> p = productFacade.findProductsByCategory(category, key);

            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("GetProductsByCategory - Success");
            log.setParam("Category - " + category + " || ApiKey - " + key);
            logFacade.create(log);
            return p;

        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("GetProductsByCategory - Failed | Cause : " + e.getMessage());
            log.setParam("Category - " + category + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    /**
     * Used to test orders manually
     *
     * @param productId
     * @param quantity
     * @return String
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
        log = new Log();
        try {
            double key = clientFacade.login(email, password);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("login() - Success");
            log.setParam("Email - " + email + " || Password - " + password);
            logFacade.create(log);
            return key;
        } catch (LoginInvalidateException e) {

            log.setClientId(null);
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("login() - Failed | Cause : " + e.getMessage());
            log.setParam("Email - " + email + " || Password - " + password);
            logFacade.create(log);
            throw new LoginInvalidateException();

        }

        //          throw new NotAuthorizedException(Response.Status.UNAUTHORIZED);
    }

    /**
     * Product designation results from 3 aspects. Brand, model and version.
     * This will search in all of this three aspects in order to return the
     * result List<Product>
     *
     * @param word
     * @param key
     * @return List <Product>
     */
    @WebMethod(operationName = "findProductByDesignation")
    public List<Product> findProductsByDesignation(@WebParam(name = "word") String word, @WebParam(name = "key") double key) throws ProductNotFoundException {
        log = new Log();
        try {
            List<Product> p = productFacade.findProductsByDesignation(word, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findProductByDesignation() - Success");
            log.setParam("Word - " + word + " || ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {

            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findProductByDesignation() - Failed | Cause : " + e.getMessage());
            log.setParam("Word - " + word + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();

        }
    }

    /**
     * Search for products by description. Returns List<Product> as result of
     * search query to data base.
     *
     * @param word
     * @return List<Product>
     */
    @WebMethod(operationName = "findProductsByDescription")
    public List<Product> findProductsByDescription(@WebParam(name = "word") String word, @WebParam(name = "key") double key) throws ProductNotFoundException {

        log = new Log();
        try {
            List<Product> p = productFacade.findproductsByDescription(word, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findProductsByDescription() - Success");
            log.setParam("Word - " + word + " || ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {

            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findProductsByDescription() - Failed | Cause : " + e.getMessage());
            log.setParam("Word - " + word + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    /**
     * Search for products by category. Returns List<Product> as result of
     * search query to data base.
     *
     * @param word
     * @param key
     * @return List<Product>
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ProductNotFoundException
     */
    @WebMethod(operationName = "findProductsByCategory")
    public List<Product> findProductsByCategory(@WebParam(name = "word") String word, @WebParam(name = "key") double key) throws ProductNotFoundException {

        log = new Log();
        try {
            List<Product> p = productFacade.findProductsByCategory(word, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findProductsByCategory() - Success");
            log.setParam("Word - " + word + " || ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findProductsByCategory() - Failed | Cause : " + e.getMessage());
            log.setParam("Word - " + word + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();

        }
    }

    /**
     * Find and return all products
     *
     * @param key
     * @return List <Product>
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ProductNotFoundException
     */
    @WebMethod(operationName = "findAllProducts")
    public List<Product> findAllProducts(@WebParam(name = "key") double key) throws ProductNotFoundException {
        log = new Log();
        try {
            List<Product> p = productFacade.findAllProducts(key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findAllProducts() - Success");
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            return p;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findAllProducts() - Failed | Cause : " + e.getMessage());
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();

        }
    }

    /**
     * Receives product Id in order to search and return stock quantity of that
     * product
     *
     * @param productId
     * @param key
     * @return int
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ProductNotFoundException
     */
    @WebMethod(operationName = "findStockByProduct")
    public int findStockByProduct(@WebParam(name = "productId") long productId, @WebParam(name = "key") double key) throws ProductNotFoundException {

        log = new Log();
        try {
            int stock = productFacade.findStockByProduct(productId, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findStockByProduct() - Success");
            log.setParam("ProductId - " + productId + " || ApiKey - " + key);
            logFacade.create(log);
            return stock;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findStockByProduct() - Failed | Cause : " + e.getMessage());
            log.setParam("ProductId - " + productId + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    /**
     * Receives product Id in order to search and return product replacement
     * date.
     *
     * @param productId
     * @param key
     * @return String
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ProductNotFoundException
     */
    @WebMethod(operationName = "replacementDateByProduct")
    public String replacementDateByProduct(@WebParam(name = "productId") long productId, @WebParam(name = "key") double key) throws ProductNotFoundException, ClientNotFoundException {

        log = new Log();
        try {
            String date = productFacade.findReplacementDateByProduct(productId, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("replacementDateByProduct() - Success");
            log.setParam("ProductId - " + productId + " || ApiKey - " + key);
            logFacade.create(log);
            return date;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("replacementDateByProduct() - Failed | Cause : " + e.getMessage());
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();

        }
    }

    /**
     * Web service operation
     *
     * @param productId
     * @param key
     * @return Product
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ProductNotFoundException
     */
    @WebMethod(operationName = "findProductById")
    public Product findProductById(@WebParam(name = "productId") long productId, @WebParam(name = "key") double key) throws ProductNotFoundException {
        log = new Log();
        try {
            Product p = productFacade.findProductById(productId, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findProductById() - Success");
            log.setParam("ProductId - " + productId + " || ApiKey - " + key);
            logFacade.create(log);

            System.out.println("PRODUTO P ----> " + p);
            return p;
        } catch (ProductNotFoundException | ClientNotFoundException e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findProductById() - Failed | Cause : " + e.getMessage());
            log.setParam("ProductId - " + productId + " || ApiKey - " + key);
            logFacade.create(log);
            throw new ProductNotFoundException();
        }
    }

    /**
     * Receives hashMap with Product Id as key and quantity as value.
     *
     * @param items
     * @param map
     * @param key
     * @return
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotCreatedException
     */
    @WebMethod(operationName = "makeOrder")
    public String makeOrder(@WebParam(name = "items") List<Item> items, @WebParam(name = "key") double key) throws OrderNotCreatedException {

        log = new Log();
        try {
            String message = orderReceivedFacade.makeOrder(items, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("makeOrder() - Success");
            log.setParam("Items - " + items.toString() + " || ApiKey - " + key);
            logFacade.create(log);
            //new HashMap().put(1, 2);
            return message;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("makeOrder() - Failed | Cause : " + e.getMessage());
            log.setParam("Items - " + items.toString() + " || ApiKey - " + key);
            logFacade.create(log);
            return "Order was not completed";

        }
    }

    /**
     * Web service operation
     *
     * @param key
     * @return List<OrderReceived>
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotFoundException
     */
    @WebMethod(operationName = "findAllOrders")
    public List<OrderReceived> findAllOrders(@WebParam(name = "key") double key) throws ClientNotFoundException, OrderNotFoundException {

        log = new Log();
        try {
            List<OrderReceived> order = orderReceivedFacade.findAllOrders(key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findAllOrders() - Success");
            log.setParam(" ApiKey - " + key);
            logFacade.create(log);
            return order;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findAllOrders() - Failed | Cause : " + e.getMessage());
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            throw new OrderNotFoundException();

        }
    }

    /**
     * Web service operation
     *
     * @param orderId
     * @param key
     * @return OrderReceived
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotFoundException
     */
    @WebMethod(operationName = "findOrder")
    public OrderReceived findOrder(@WebParam(name = "orderId") long orderId, @WebParam(name = "key") double key) throws ClientNotFoundException, OrderNotFoundException {

        log = new Log();
        try {
            OrderReceived order = orderReceivedFacade.findorder(orderId, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findOrder() - Success");
            log.setParam("orderId - " + orderId + " || ApiKey - " + key);
            logFacade.create(log);
            return order;
        } catch (Exception e) {

            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findOrder() - Failed | Cause : " + e.getMessage());
            log.setParam("orderId - " + orderId + " || ApiKey - " + key);
            logFacade.create(log);
            throw new OrderNotFoundException();
        }
    }

    @WebMethod(operationName = "findOrderByClienId")
    public List<OrderReceived> findOrderByClienId(@WebParam(name = "key") double key) throws ClientNotFoundException, OrderNotFoundException {

        log = new Log();
        try {
            List<OrderReceived> orderList = orderReceivedFacade.findOrdersByClientId(key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findOrdersByClientId() - Success");
            log.setParam("ApiKey - " + key);
            logFacade.create(log);

            return orderList;
        } catch (Exception e) {

            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findOrdersByClientId() - Failed | Cause : " + e.getMessage());
            log.setParam("ApiKey - " + key);
            logFacade.create(log);
            throw new OrderNotFoundException();
        }
    }

    @WebMethod(operationName = "orderDeliveryDate")
    public String orderDeliveryDate(@WebParam(name = "orderId") long orderId, @WebParam(name = "key") double key) throws ClientNotFoundException, OrderNotFoundException {

        log = new Log();
        try {
            String orderDate = orderReceivedFacade.orderDeliveryDate(orderId, key);
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("orderDeliveryDate() - Success");
            log.setParam("orderId - " + orderId + " || ApiKey - " + key);
            logFacade.create(log);
            return orderDate;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(key));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("orderDeliveryDate() - Failed | Cause : " + e.getMessage());
            log.setParam("orderId - " + orderId + " || ApiKey - " + key);
            logFacade.create(log);
            throw new OrderNotFoundException();
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "findOrderItems")
    public List<OrderItems> findOrderItems(@WebParam(name = "orderId") long orderId, @WebParam(name = "apiKey") double apiKey) throws ClientNotFoundException, ItemNotFoundException {

        log = new Log();
        try {
            List<OrderItems> result = itemsFacade.findAllItemsFromOrder(orderId, apiKey);
            log.setClientId(clientFacade.checkApiExistence(apiKey));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findOrderItems() - Success");
            log.setParam("orderId - " + orderId + " || ApiKey - " + apiKey);
            logFacade.create(log);
            return result;
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(apiKey));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("findOrderItems() - Failed");
            log.setParam("orderId - " + orderId + " || ApiKey - " + apiKey);
            logFacade.create(log);
            throw new ItemNotFoundException();

        }

    }

    /**
     * Delete order by id
     *
     * @param orderId
     * @param apiKey
     * @return
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotFoundException
     */
    @WebMethod(operationName = "deleteOrderById")
    public String deleteOrderById(@WebParam(name = "orderId") long orderId, @WebParam(name = "apiKey") double apiKey) throws ClientNotFoundException, OrderNotFoundException {
        try {
            orderReceivedFacade.deleteOrder(orderId, apiKey);
            log.setClientId(clientFacade.checkApiExistence(apiKey));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("deleteOrderById() - Success");
            log.setParam("orderId - " + orderId + " || ApiKey - " + apiKey);
            return "Deleted";
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(apiKey));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("deleteOrderById() - Failed");
            log.setParam("orderId - " + orderId + " || ApiKey - " + apiKey);
            logFacade.create(log);
            throw new OrderNotFoundException();

        }

    }

    /**
     * Web service operation
     *
     * @param newList
     * @param orderId
     * @param apiKey
     * @return
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotFoundException
     */
    @WebMethod(operationName = "editOrder")
    public String editOrder(@WebParam(name = "newList") List<OrderItems> newList, @WebParam(name = "orderId") long orderId, @WebParam(name = "apiKey") double apiKey) throws OrderNotFoundException {

        try {
            orderReceivedFacade.editOrder(orderId, newList, apiKey);
            log.setClientId(clientFacade.checkApiExistence(apiKey));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("editOrder() - Success");
            log.setParam("orderId - " + orderId + " || ApiKey - " + apiKey);
            return "Edited";
        } catch (Exception e) {
            log.setClientId(clientFacade.checkApiExistence(apiKey));
            log.setLogDate(today);
            log.setInvokedService("SoapWs");
            log.setTask("deleteOrderById() - Failed");
            log.setParam("orderId - " + orderId + " || ApiKey - " + apiKey);
            logFacade.create(log);
            throw new OrderNotFoundException();
        }

    }

}
