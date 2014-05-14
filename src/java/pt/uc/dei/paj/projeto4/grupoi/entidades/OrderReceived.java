/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zueb LDA
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderReceived.findOrderDeliveryDateById", query = "SELECT o.deliveryDate FROM OrderReceived o WHERE o.id = :id"),
    @NamedQuery(name = "OrderReceived.findOrdersByClientId", query = "SELECT o FROM OrderReceived o WHERE o.client.id = :id"),
    @NamedQuery(name = "OrderReceived.deleteOrderById", query = "DELETE FROM OrderReceived o  WHERE o.id=:id"),})
public class OrderReceived implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderDate;

    private String deliveryDate;
    @ManyToOne
    private Client client;

//    @OneToMany
//    private List<OrderItems> orderItems;
    public OrderReceived() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<OrderItems> getOrderItems() {
//        return orderItems;
//    }
//
//    public void setOrderItems(List<OrderItems> orderItems) {
//        this.orderItems = orderItems;
//    }
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderReceived)) {
            return false;
        }
        OrderReceived other = (OrderReceived) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderReceived{" + "id=" + id + ", orderDate=" + orderDate + ", deliveryDate=" + deliveryDate + '}';
    }

}
