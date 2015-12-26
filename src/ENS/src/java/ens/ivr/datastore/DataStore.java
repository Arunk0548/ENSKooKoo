/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ens.ivr.datastore;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arun Kumar
 */
public class DataStore {

    private static DataStore store = null;

    private Customers registeredCustomerList;

    private final Object _cust_object_lock = new Object();

    private DataStore() {

    }

    public static DataStore getInstance() {
        if (store == null) {
            store = new DataStore();
        }
        return store;
    }

    public void initCache() {
        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(context.getResourceAsStream("/WEB-INF/json/customer.json")));
            BufferedReader br = new BufferedReader(new FileReader(new File("customer.json")));
            StringBuilder inputStringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                inputStringBuilder.append(line);
            }
            br.close();
            registeredCustomerList = new Gson().fromJson(inputStringBuilder.toString(), Customers.class);
            if (registeredCustomerList.customers != null) {
                Collections.sort(registeredCustomerList.customers, new Customer.CustomerComparator());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveCache() {
        try {
            FileWriter writer = new FileWriter(new File("customer.json"));
            String json = new Gson().toJson(registeredCustomerList);
            writer.write(json);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the registeredCustomerList
     */
    public Customers getRegisteredCustomerList() {

        if (registeredCustomerList == null) {
            registeredCustomerList = new Customers();
            registeredCustomerList.customers = new ArrayList<Customer>();
        }

        return registeredCustomerList;
    }

    /**
     * @param registeredCustomerList the registeredCustomerList to set
     */
    public void setRegisteredCustomerList(Customers registeredCustomerList) {
        this.registeredCustomerList = registeredCustomerList;
    }

    public Customer findCustomerbyId(long id) {
        synchronized (_cust_object_lock) {
            for (Customer customer : getRegisteredCustomerList().customers) {
                if (customer.getUserId() == id) {
                    return customer;
                }
            }
        }

        return null;
    }

    public Customer findCustomer(String registeredNumber) {
        if (registeredNumber == null || registeredNumber.isEmpty()) {
            return null;
        }

        if (!registeredNumber.startsWith("91")) {
            registeredNumber = "91" + registeredNumber;
        }

        synchronized (_cust_object_lock) {
            for (Customer customer : getRegisteredCustomerList().customers) {
                if (customer.getMobileNo().equals(registeredNumber)) {
                    return customer;
                }
            }
        }
        return null;
    }

    public void addCustomer(Customer customer) {
        synchronized (_cust_object_lock) {
            getRegisteredCustomerList().customers.add(customer);
        }
    }
}
