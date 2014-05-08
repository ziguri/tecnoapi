/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.ejbs;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import pt.uc.dei.paj.projeto4.grupoi.entidades.Client;

/**
 *
 * @author Zueb LDA
 */
@Stateful
@SessionScoped
public class SessionBean {

    private Client client;

    public SessionBean(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
