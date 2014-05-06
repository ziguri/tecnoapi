/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.facades;

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

    public List<Product> findProductsByCategory(String word) {

        Query q = em.createNamedQuery("Product.findByCategory");
        q.setParameter("word", word);
        return (List<Product>) q.getResultList();

    }

}
