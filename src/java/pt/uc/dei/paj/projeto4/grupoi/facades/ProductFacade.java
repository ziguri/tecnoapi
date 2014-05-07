/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.facades;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;

/**
 *
 * @author Zueb LDA
 */
@Stateless
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "TecnoApiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }

    /**
     * Receives String with word(s) to search all the products by designation
     *
     * @param word
     * @return
     */
    public List<Product> findProductsByDesignation(String word) {

        Query q = em.createNamedQuery("Product.findByDesignation");
        q.setParameter("word", word);
        return (List<Product>) q.getResultList();
    }

    /**
     * Receives String with word(s) to search all the products where word(s)
     * inserted matches with product description.
     *
     * @param word
     * @return
     */
    public List<Product> findproductsByDescription(String word) {

        Query q = em.createNamedQuery("Product.findByDescription");
        q.setParameter("word", "%" + word + "%");
        return (List<Product>) q.getResultList();
    }

    /**
     * Receives String to find all the products by category
     *
     * @param word
     * @return
     *
     */
    public List<Product> findProductsByCategory(String word) {

        Query q = em.createNamedQuery("Product.findByCategory");
        q.setParameter("word", word);
        return (List<Product>) q.getResultList();

    }

    /**
     * Receives product id in order to find the product stock by product id.
     *
     * @param id
     * @return
     */
    public int findStockByProduct(Long id) {

        try {
            Query q = em.createNamedQuery("Product.findStockByProduct");
            q.setParameter("id", id);
            return (int) q.getSingleResult();
        } catch (Exception e) {

            return 0;
        }
    }

    /**
     * Receives product id in order to find product replacement date
     *
     * @param id
     * @return
     */
    public String findReplacementDateByProduct(Long id) {

        String date = "";
        try {
            Query q = em.createNamedQuery("Product.findRepositionDate");
            q.setParameter("id", id);
            date += (Date) q.getSingleResult();
            return date;
        } catch (Exception e) {

            return date;
        }
    }

}
