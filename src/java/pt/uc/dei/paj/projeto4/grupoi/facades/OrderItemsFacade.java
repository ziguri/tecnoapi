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
import javax.persistence.TypedQuery;
import pt.uc.dei.paj.projeto4.grupoi.entidades.OrderItems;
import pt.uc.dei.paj.projeto4.grupoi.utilities.ClientNotFoundException;

/**
 *
 * @author Zueb LDA
 */
@Stateless
public class OrderItemsFacade extends AbstractFacade<OrderItems> {

    @PersistenceContext(unitName = "TecnoApiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Inject
    private ClientFacade clientFacade;

    public OrderItemsFacade() {
        super(OrderItems.class);
    }

    public List<OrderItems> findAllItemsFromOrder(Long id, double key) throws ClientNotFoundException {

        clientFacade.getClientByApiKey(key);
        TypedQuery<OrderItems> q = em.createNamedQuery("OrderItems.findItemsFromOrder", OrderItems.class);
        q.setParameter("id", id);
        return q.getResultList();

    }

    public List<OrderItems> getAllItemsFromOrder(Long id) {

        TypedQuery<OrderItems> q = em.createNamedQuery("OrderItems.findItemsFromOrder", OrderItems.class);
        q.setParameter("id", id);
        return q.getResultList();

    }

    public void deleteItemsFromOrder(Long id) {

        TypedQuery<OrderItems> q = em.createNamedQuery("OrderItems.deleteItemsFromOrder", OrderItems.class);
        q.setParameter("id", id);
        q.executeUpdate();
    }

}
