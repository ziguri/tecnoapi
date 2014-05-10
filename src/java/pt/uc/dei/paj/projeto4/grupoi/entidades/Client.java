/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Zueb LDA
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Client.findClientByEmail", query = "SELECT c FROM Client c  WHERE c.email=:email AND c.password=:password"),
    @NamedQuery(name = "Client.findClientIdByApiKey", query = "SELECT c.id FROM Client c WHERE c.apiKey=:apikey"),
    @NamedQuery(name = "Client.findClientByApiKey", query = "SELECT c FROM Client c WHERE c.apiKey=:apikey"),})
@XmlRootElement
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "APIKEY", nullable = true, unique = true)
    private double apiKey;

    @OneToMany(mappedBy = "client")
    private List<OrderReceived> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getApiKey() {
        return apiKey;
    }

    public void setApiKey(double apiKey) {
        this.apiKey = apiKey;
    }

    @XmlTransient
    public List<OrderReceived> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderReceived> orders) {
        this.orders = orders;
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
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name=" + name + ", email=" + email + '}';
    }

}
