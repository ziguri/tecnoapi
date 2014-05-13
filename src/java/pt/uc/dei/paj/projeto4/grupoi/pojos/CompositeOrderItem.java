/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.pojos;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

/**
 *
 * @author Zueb LDA
 */
public class CompositeOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "PRODUCTID")
    private Long productId;
    @Column(name = "ORDERRECEIVEDID")
    private Long orderReceivedId;

    public CompositeOrderItem() {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.productId);
        hash = 97 * hash + Objects.hashCode(this.orderReceivedId);
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
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        if (!Objects.equals(this.orderReceivedId, other.orderReceivedId)) {
            return false;
        }
        return true;
    }

}
