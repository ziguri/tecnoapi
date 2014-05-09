/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.facades;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderItems;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderReceived;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotCreatedException;

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
     * @param h
     * @return
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.OrderNotCreatedException
     */
    public String makeOrder(Map<Long, Integer> h) throws OrderNotCreatedException {

        try {
            OrderReceived order = orderReceived();

            for (Map.Entry items : h.entrySet()) {

                Long productId = (Long) items.getKey(); //Produto ID
                Integer quantity = (Integer) items.getValue(); //Quantidade

                Product p = productFacade.find(productId);//Get full Object

                OrderItems oItems = new OrderItems();
                oItems.setOrderReceivedId(order.getId());
                oItems.setProductId(productId);
                oItems.setQuantity(quantity);
                oItems.setPrice(p.getSellPrice() * quantity);
                orderItems.create(oItems);
                p.setStockQtt(p.getStockQtt() - quantity);

            }

//            OrderItems oItems = new OrderItems();
//            oItems.setOrderReceivedId(order.getId());
//            oItems.setProductId(p.getId());
//            oItems.setQuantity(quantity);
//            oItems.setPrice(p.getSellPrice() * quantity);
//
//            orderItems.create(oItems);
//
//            p.setStockQtt(p.getStockQtt() - quantity);
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

    //Getter´s and Setter´s
    public OrderItemsFacade getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItemsFacade orderItems) {
        this.orderItems = orderItems;
    }

}
