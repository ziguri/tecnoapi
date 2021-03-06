/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Attributes;

/**
 *
 * @author Zueb LDA
 */
@Stateless
public class AttributesFacade extends AbstractFacade<Attributes> {
    @PersistenceContext(unitName = "TecnoApiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttributesFacade() {
        super(Attributes.class);
    }

}
