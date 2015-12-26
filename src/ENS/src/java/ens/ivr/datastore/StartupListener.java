/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ens.ivr.datastore;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Arun Kumar
 */

@WebListener
public class StartupListener implements javax.servlet.ServletContextListener {

  DataStore store;
    
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContextListener started");
        store = DataStore.getInstance();
        store.initCache();
//        Customer customer = new Customer();
//        customer.setFirstName("Arun");
//        customer.setMobileNo("08904716484");
//        customer.setEmergencyNos(new String[]{"08904846220","08904716484"});
//        store.getRegisteredCustomerList().customers.add(customer);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContextListener destroyed");
        store.saveCache();
    }
}
