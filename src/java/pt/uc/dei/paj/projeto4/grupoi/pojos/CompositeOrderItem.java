/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.pojos;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderReceived;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;

/**
 *
 * @author Zueb LDA
 */
public class CompositeOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "PRODUCTID")
    private Product product;
    @Column(name = "ORDERRECEIVEDID")
    private OrderReceived orderReceived;

    public CompositeOrderItem() {
    }

    public Product getProductId() {
        return product;
    }

    public void setProductId(Product productId) {
        this.product = productId;
    }

    public OrderReceived getOrderReceivedId() {
        return orderReceived;
    }

    public void setOrderReceivedId(OrderReceived orderReceivedId) {
        this.orderReceived = orderReceivedId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.product);
        hash = 97 * hash + Objects.hashCode(this.orderReceived);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompositeOrderItem other = (CompositeOrderItem) obj;
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.orderReceived, other.orderReceived)) {
            return false;
        }
        return true;
    }

}
