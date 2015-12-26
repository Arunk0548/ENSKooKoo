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
public class OutboundSms {

    private OkHttpClient client;

    public OutboundSms() {
        client = new OkHttpClient();
    }

    private final String smsUrl = " http://www.kookoo.in/outbound/outbound_sms.php?";

    public void sendSms(String message, String phone) {

        message = Prompts.Welcome + "\n" + message;

        if (message.length() > 150) {

            int totalMessage = message.length() / 150;
            for (int i = 0; i <= totalMessage; i++) {

                int startIndex = 150 * i;
                int endIndex = startIndex + 150;
                if (endIndex > message.length()) {
                    endIndex = message.length();
                }
                String subMessage = message.substring(startIndex, endIndex);
                sms(subMessage, phone);
            }
        } else {
            sms(message, phone);
        }
    }

    private void sms(String message, String phone) {
        try {

            URL url = new URL(smsUrl + "phone_no=" + phone + "&api_key=" + Prompts.API_KEY + "&message=" + message);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            String jsonResponse = response.body().string();
            Thread.sleep(1000);
        } catch (MalformedURLException ex) {
            Logger.getLogger(OutboundSms.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OutboundSms.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(OutboundSms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
