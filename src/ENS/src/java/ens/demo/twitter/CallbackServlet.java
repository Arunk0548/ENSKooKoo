/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ens.demo.twitter;

import ens.ivr.datastore.Customer;
import ens.ivr.datastore.DataStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author Arun Kumar
 */
@WebServlet(name = "callback", urlPatterns = {"/callback"})
public class CallbackServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        if (twitter == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        Customer customer = (Customer) request.getSession().getAttribute("cust");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        String verifier = request.getParameter("oauth_verifier");
        try {
            AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
            System.out.println("Access token: " + accessToken.getToken());
            System.out.println("Access token secret: " + accessToken.getTokenSecret());
            if (customer == null) {
                customer = new Customer();
                request.getSession().setAttribute("cust", customer);
            }
            customer.setFirstName(twitter.getScreenName());
            customer.setToken(accessToken.getToken());
            customer.setTokenSecret(accessToken.getTokenSecret());
            customer.setUserId(twitter.getId());
            DataStore store = DataStore.getInstance();
            Customer existingCustomer = store.findCustomerbyId(twitter.getId());
          
            if (existingCustomer == null) {
                existingCustomer = new Customer();

                existingCustomer.setFirstName(twitter.getScreenName());
                existingCustomer.setToken(accessToken.getToken());
                existingCustomer.setTokenSecret(accessToken.getTokenSecret());
                existingCustomer.setUserId(twitter.getId());

                store.addCustomer(existingCustomer);

            } else {
                existingCustomer.setFirstName(twitter.getScreenName());
                existingCustomer.setToken(accessToken.getToken());
                existingCustomer.setTokenSecret(accessToken.getTokenSecret());
                
                customer.setMobileNo(existingCustomer.getMobileNo());
                customer.setEmergencyNo1(existingCustomer.getEmergencyNo1());
                customer.setEmergencyNo2(existingCustomer.getEmergencyNo2());
                customer.setEmergencyNo3(existingCustomer.getEmergencyNo3());
            }
            request.getSession().removeAttribute("requestToken");
        } catch (TwitterException e) {
            throw new ServletException(e);
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
