/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.facades;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Product;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ProductNotFoundException;

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

    @Inject
    private ClientFacade clientFacade;

    public ProductFacade() {
        super(Product.class);
    }

    /**
     * Receives String with word(s) to search all the products by designation
     *
     * @param word
     * @param key
     * @return
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException
     */
    public List<Product> findProductsByDesignation(String word, double key) throws ClientNotFoundException {

        clientFacade.getClientByApiKey(key);
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
     * @throws pt.uc.dei.paj.projeto4.grupoi.utilities.ProductNotFoundException
     */
    public int findStockByProduct(Long id) throws ProductNotFoundException {

        try {
            Query q = em.createNamedQuery("Product.findStockByProduct");
            q.setParameter("id", id);
            return (int) q.getSingleResult();
        } catch (Exception e) {

            throw new ProductNotFoundException();
        }
    }

    /**
     * Receives product id in order to find product replacement date
     *
     * @param id
     * @return
     */
    public String findReplacementDateByProduct(Long id) throws ProductNotFoundException {

        String date = "";
        try {
            Query q = em.createNamedQuery("Product.findRepositionDate");
            q.setParameter("id", id);
            Product p = (Product) q.getSingleResult();
            date += p.getRepoDate().getTime();
            return date;
        } catch (Exception e) {

            throw new ProductNotFoundException();
        }
    }

    /**
     * Find and return all products
     *
     * @return List<Product>
     */
    public List<Product> findAllProducts() {

        return this.findAll();
    }

    public Product findProductById(Long id) throws ProductNotFoundException {

        try {
            return (Product) this.find(id);
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }

}
