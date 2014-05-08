/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.utilities;

/**
 *
 * @author Zueb LDA
 */
public class OrderNotCreatedException extends Exception {

    public OrderNotCreatedException() {
        super("Order not created, please try check your request");
    }

}
