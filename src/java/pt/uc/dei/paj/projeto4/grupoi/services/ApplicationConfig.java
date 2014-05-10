/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.paj.projeto4.grupoi.services;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Guilherme Pereira
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(pt.uc.dei.paj.projeto4.grupoi.services.ClientFacadeREST.class);
        resources.add(pt.uc.dei.paj.projeto4.grupoi.services.ProductFacadeREST.class);
    }

}
