/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ens.ivr.demo;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import ens.ivr.datastore.Prompts;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arun Kumar
 */
public class CreateConfCalls {

    private OkHttpClient client;

    public CreateConfCalls() {
        client = new OkHttpClient();
    }

    private final String dialUrl = " http://www.kookoo.in/outbound/outbound.php?";

    public void dialNumber(String phone, String confno) {

        if (phone == null || phone.isEmpty()) {
            return;
        }

        if (confno == null || confno.isEmpty()) {
            return;
        }

        try {
            com.ozonetel.kookoo.Response r = new com.ozonetel.kookoo.Response();
            r.addConference(confno);
            r.addPlayText(Prompts.CONF_CALL_PROMPT);
            String extra_data = r.getXML();

            URL url = new URL(dialUrl + "phone_no=" + phone + "&api_key=" + Prompts.API_KEY + "&outbound_version=2&extra_data=" + extra_data);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            String jsonResponse = response.body().string();
        } catch (MalformedURLException ex) {
            Logger.getLogger(OutboundSms.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OutboundSms.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
