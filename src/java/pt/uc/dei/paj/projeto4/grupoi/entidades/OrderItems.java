/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import pt.uc.dei.paj.projeto4.grupoi.pojos.CompositeOrderItem;

/**
 *
 * @author Zueb LDA
 */
@Entity
@IdClass(CompositeOrderItem.class)
@NamedQueries({
    @NamedQuery(name = "OrderItems.findItemsFromOrder", query = "SELECT i FROM OrderItems i  WHERE i.orderReceivedId=:id"),
    @NamedQuery(name = "OrderItems.deleteItemsFromOrder", query = "DELETE FROM OrderItems i  WHERE i.orderReceivedId=:id"),})
@XmlRootElement
public class OrderItems implements Serializable {

    private static final long serialVersionUID = 1L;

    //VErificar situação com chave composta
    @Id
    @JoinColumn(name = "PRODUCTID")
    private Long productId;
    @Id
    @JoinColumn(name = "ORDERRECEIVEDID")
    private Long orderReceivedId;

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String product_name;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Column(name = "PRICE", nullable = false)
    private double price;

    public OrderItems() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderReceivedId() {
        return orderReceivedId;
    }

    public void setOrderReceivedId(Long orderReceivedId) {
        this.orderReceivedId = orderReceivedId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    //És um palhaço!!!!!!!
    @Override
    public String toString() {
        return "OrderItems{" + "productId=" + productId + ", orderReceivedId=" + orderReceivedId + ", quantity=" + quantity + ", price=" + price + '}';
    }

}
