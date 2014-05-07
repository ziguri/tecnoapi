/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.facades;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderItems;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderReceived;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;

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

        System.err.println("################################################ORDER DATE --->" + orderDate);
        System.err.println("################################################DELIVERY DATE --->" + deliveryDate);
        OrderReceived order = new OrderReceived();

        order.setOrderDate(orderDate);
        order.setDeliveryDate(deliveryDate);

        System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++ORDER --->" + order);

        this.create(order);

        System.err.println("################################################ORDER --->" + order);

        return order;
    }

    public void makeOrder(Product p, int quantity) {

        OrderReceived order = orderReceived();
        OrderItems oItems = new OrderItems();

        oItems.setOrderReceivedId(order.getId());
        oItems.setProductId(p.getId());
        oItems.setQuantity(quantity);
        oItems.setPrice(p.getSellPrice() * quantity);

        order.getOrderItems().add(oItems);
        orderItems.create(oItems);
    }

    public String makeOrderTest(Long prodId, int quantity) {

        try {
            //System.err.println("################################################ENTRA AQUI --->");
            OrderReceived order = orderReceived();
            OrderItems oItems = new OrderItems();

        //System.err.println("################################################ORDER RECEIVED --->" + order);
            Product p = (Product) productFacade.find(prodId);

        //System.err.println("#################################################PRODUCT PRODUCT --->" + p);
            oItems.setOrderReceivedId(order.getId());
            oItems.setProductId(prodId);
            oItems.setQuantity(quantity);
            oItems.setPrice(p.getSellPrice() * quantity);
        //order.getOrderItems().add(oItems);
            //System.err.println("++++++++++++++oItems ---> " + oItems);
            orderItems.create(oItems);
            return "Order successfuly added";
        } catch (Exception e) {

            return "Exception " + e + " - Unable to fulfill your request.";
        }
    }

    public OrderItemsFacade getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItemsFacade orderItems) {
        this.orderItems = orderItems;
    }

}
