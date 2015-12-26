package com.ozonetel.kookoo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestClient {

    public static void main(String[] args) {
        try {
            Response resp = new Response();
            resp.sendSms("Hi KooKoo", "9490607378");
            resp.sayAs(Response.SayAs.DIGITS, "1234");
            System.out.println("" + resp.getXML());
        
        } catch (Exception ex) {
            System.out.println("Exception");
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
