/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.facades;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Client;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderItems;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderReceived;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;
import pt.uc.dei.paj.projeto4.grupoi.pojos.Item;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotCreatedException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotFoundException;

/**
 *
 * @author Zueb LDA
 */
@Stateless
public class OrderReceivedFacade extends AbstractFacade<OrderReceived> {

    @PersistenceContext(unitName = "TecnoApiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderReceivedFacade() {
        super(OrderReceived.class);
    }

    @Inject
    private OrderItemsFacade orderItems;
    @Inject
    private ProductFacade productFacade;
    @Inject
    private ClientFacade clientFacade;

    /**
     * Create a new orderReceived
     *
     * @return OrderReceived
     */
    private OrderReceived orderReceived() {

        GregorianCalendar gc = new GregorianCalendar();

        String orderDate = "" + gc.get(Calendar.DAY_OF_MONTH) + " / " + gc.get(Calendar.MONTH) + " / " + gc.get(Calendar.YEAR);
        gc.add(Calendar.DAY_OF_MONTH, 7);
        String deliveryDate = "" + gc.get(Calendar.DAY_OF_MONTH) + " / " + gc.get(Calendar.MONTH) + " / " + gc.get(Calendar.YEAR);
        OrderReceived order = new OrderReceived();

        order.setOrderDate(orderDate);
        order.setDeliveryDate(deliveryDate);

        this.create(order);

        return order;
    }

    /**
     * Receives Product and quantity to create a new order.
     *
     * @param items
     * @param h
     * @param key
     * @return
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotCreatedException
     */
    public String makeOrder(List<Item> items, double key) throws OrderNotCreatedException {

        try {
            Client c = clientFacade.getClientByApiKey(key);
            OrderReceived order = orderReceived();
            order.setClient(c);

            for (Item i : items) {

                Long productId = i.getProductId();
                Integer quantity = i.getQuantity();
                Product p = productFacade.find(productId);
                OrderItems oItems = new OrderItems();
                oItems.setOrderReceivedId(order.getId());
                oItems.setProductId(productId);
                oItems.setProduct_name(p.getBrand() + "-" + p.getModel());
                oItems.setQuantity(quantity);
                oItems.setPrice(p.getSellPrice() * quantity);
                orderItems.create(oItems);
                p.setStockQtt(p.getStockQtt() - quantity);
                productFacade.edit(p);
            }

            this.create(order);
            c.getOrders().add(order);
            return "Order Created Successfuly";
        } catch (Exception e) {
            throw new OrderNotCreatedException();
        }
    }

    public String makeOrderTest(Long prodId, int quantity) {

        try {
            OrderReceived order = orderReceived();
            OrderItems oItems = new OrderItems();
            Product p = (Product) productFacade.find(prodId);
            p.setStockQtt(p.getStockQtt() - quantity);
            oItems.setOrderReceivedId(order.getId());
            oItems.setProductId(prodId);
            oItems.setQuantity(quantity);
            oItems.setPrice(p.getSellPrice() * quantity);
            orderItems.create(oItems);
            return "Order successfuly added";
        } catch (Exception e) {

            return "Exception " + e + " - Unable to fulfill your request.";
        }
    }

    /**
     * Find and return all orders.
     *
     * @param key
     * @return Lis<OrderReceived>
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException
     */
    public List<OrderReceived> findAllOrders(double key) throws ClientNotFoundException {
        clientFacade.getClientByApiKey(key);
        return this.findAll();
    }

    /**
     * Find and return one order
     *
     * @param orderId
     * @param key
     * @return OrderReceived
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException
     */
    public OrderReceived findorder(Long orderId, double key) throws ClientNotFoundException {
        clientFacade.getClientByApiKey(key);
        return (OrderReceived) this.find(orderId);
    }

    public String orderDeliveryDate(Long orderId, double key) throws ClientNotFoundException, OrderNotFoundException {
        clientFacade.getClientByApiKey(key);
        try {
            Query q = em.createNamedQuery("OrderReceived.findOrderDeliveryDateById");
            q.setParameter("id", orderId);
            return (String) q.getSingleResult();
        } catch (Exception e) {
            throw new OrderNotFoundException();
        }
    }

    public List<OrderReceived> findOrdersByClientId(double key) throws ClientNotFoundException {
        Client c = clientFacade.getClientByApiKey(key);

        Query q = em.createNamedQuery("OrderReceived.findOrdersByClientId");
        q.setParameter("id", c.getId());
        return q.getResultList();

    }

    /**
     * Delete order
     *
     * @param id
     * @param key
     * @throws ClientNotFoundException
     */
    public void deleteOrder(Long orderId, double key) throws ClientNotFoundException {

        Client c = clientFacade.getClientByApiKey(key);
        c.getOrders().remove(this.find(orderId));
        orderItems.deleteItemsFromOrder(orderId);
        Query q = em.createNamedQuery("OrderReceived.deleteOrderById");
        q.setParameter("id", orderId);
        q.executeUpdate();
    }

    //Getter´s and Setter´s
    public OrderItemsFacade getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItemsFacade orderItems) {
        this.orderItems = orderItems;
    }

}
