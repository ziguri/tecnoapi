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
import javax.persistence.ManyToOne;
import pt.uc.dei.paj.projeto4.grupoi.pojos.CompositeOrderItem;

/**
 *
 * @author Zueb LDA
 */
@Entity
@IdClass(CompositeOrderItem.class)
public class OrderItems implements Serializable {

    private static final long serialVersionUID = 1L;

    //VErificar situação com chave composta
    @Id
    @JoinColumn(name = "PRODUCTID")
    @ManyToOne
    private Product product;

    @Id
    @JoinColumn(name = "ORDERRECEIVEDID")
    @ManyToOne
    private OrderReceived orderReceived;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Column(name = "PRICE", nullable = false)
    private double price;

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof OrderItems)) {
//            return false;
//        }
//        OrderItems other = (OrderItems) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "pt.uc.dei.paj.projeto4.grupoi.entidades.OrderItems[ id=" + id + " ]";
//    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderReceived getOrderReceived() {
        return orderReceived;
    }

    public void setOrderReceived(OrderReceived orderReceived) {
        this.orderReceived = orderReceived;
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

}
