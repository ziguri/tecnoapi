/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.entidades;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zueb LDA
 */
@Entity
@XmlRootElement
@Table(name = "PRODUCT")
@NamedQueries({
    @NamedQuery(name = "Product.findByDesignation", query = "SELECT p FROM Product p WHERE p.brand LIKE :word OR p.model LIKE :word OR p.version LIKE :word"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description LIKE :word"),
    @NamedQuery(name = "Product.findByCategory", query = "SELECT p FROM Product p WHERE p.category LIKE :word"),
    @NamedQuery(name = "Product.findStockByProduct", query = "SELECT p.stockQtt FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findRepositionDate", query = "SELECT p.repoDate FROM Product p WHERE p.id = :id"),})
public class Product implements Serializable {

    @ManyToMany(mappedBy = "products")
    private List<Attributes> attributess;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BRAND", nullable = false)
    private String brand;
    @Column(name = "MODEL", nullable = false)
    private String model;
    @Column(name = "VERSION", nullable = false)
    private String version;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @Column(name = "CATEGORY", nullable = false)
    private String category;
    @Column(name = "STOCK_QUANTITY", nullable = false)
    private int stockQtt;
    @Column(name = "SELL_PRICE", nullable = false)
    private double sellPrice;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "RESPOSITION_DATE", nullable = false)
    private GregorianCalendar repoDate;

//    @OneToMany
//    private List<OrderItems> orderItems;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockQtt() {
        return stockQtt;
    }

    public void setStockQtt(int stockQtt) {
        this.stockQtt = stockQtt;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public GregorianCalendar getRepoDate() {
        return repoDate;
    }

    public List<Attributes> getAttributess() {
        return attributess;
    }

    public void setAttributess(List<Attributes> attributess) {
        this.attributess = attributess;
    }

//
//    public void setRepoDate(GregorianCalendar repoDate) {
//        this.repoDate = repoDate;
//    }
//    public List<OrderItems> getOrderItems() {
//        return orderItems;
//    }
//
//    public void setOrderItems(List<OrderItems> orderItems) {
//        this.orderItems = orderItems;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pt.uc.dei.paj.projeto4.grupoi.entidades.Products[ id=" + id + " ]";
    }

}
