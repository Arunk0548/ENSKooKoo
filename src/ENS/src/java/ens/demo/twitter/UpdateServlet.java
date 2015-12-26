/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ens.demo.twitter;

import ens.ivr.datastore.Customer;
import ens.ivr.datastore.DataStore;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 * @author Arun Kumar
 */
public class UpdateServlet extends HttpServlet {

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            Customer customer = (Customer) request.getSession().getAttribute("cust");
            Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
            if (customer == null) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            String mobile = request.getParameter("mobile");
            String eno1 = request.getParameter("eno1");
            String eno2 = request.getParameter("eno2");
            String eno3 = request.getParameter("eno3");

            customer.setMobileNo(mobile);
            customer.setEmergencyNo1(eno1);
            customer.setEmergencyNo2(eno2);
            customer.setEmergencyNo3(eno3);

            DataStore store = DataStore.getInstance();
            Customer existing = store.findCustomerbyId(twitter.getId());
            if (existing != null) {
                existing.setMobileNo(mobile);
                existing.setEmergencyNo1(eno1);
                existing.setEmergencyNo2(eno2);
                existing.setEmergencyNo3(eno3);
            }

            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } catch (TwitterException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
