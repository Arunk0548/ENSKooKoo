/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ens.ivr.demo;

import ens.ivr.datastore.DataStore;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author Arun Kumar
 */
@Path("users")
public class Userdetails {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Userdetails
     */
    public Userdetails() {
    }

    /**
     * Retrieves representation of an instance of ens.ivr.demo.Userdetails
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public void getUserDetails(@Context HttpServletRequest request, @Context HttpServletResponse response)
            throws IOException {
        DataStore store = DataStore.getInstance();
        sendData(store.getRegisteredCustomerList().customers.toString(), response);
    }

   
    private void sendData(String r, HttpServletResponse response) {
        String kookooResponseOutput = r;
        try {
            PrintWriter out = response.getWriter();
            out.println(kookooResponseOutput);
            out.flush();
        } catch (Exception ignore) {
            System.out.println(ignore.toString());
        }
    }
}
