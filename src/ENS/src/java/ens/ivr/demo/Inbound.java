package ens.ivr.demo;

import com.ozonetel.kookoo.Record;
import com.ozonetel.kookoo.Response;
import ens.demo.twitter.TwittEmergencyMessage;
import ens.ivr.datastore.Customer;
import ens.ivr.datastore.DataStore;
import ens.ivr.datastore.Prompts;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * ENS REST Web Service
 *
 * @author Arun Kumar
 */
@Path("ens")
public class Inbound {

    @Context
    private UriInfo context;

    @Context
    private ServletContext servletContext;

    /**
     * Creates a new instance of Inbound
     */
    public Inbound() {
    }

    @GET
    @Produces("application/xml")
    public void doGetHandleInboundCall(@Context HttpServletRequest request, @Context HttpServletResponse response)
            throws IOException {

        response.setContentType("text/xml;charset=UTF-8");

        DataStore store = DataStore.getInstance();

        String event = request.getParameter("event");

        Response r = new Response(); //create kookoo Response Object

        String the_number_which_trigger_request = request.getParameter("cid");

        if ((null != event) && event.equalsIgnoreCase("NewCall")) {

            if (store.findCustomer(the_number_which_trigger_request) == null) {
                //unregistered customer trying to trigger emergency notification, drop a sms to register first
                OutboundSms sms = new OutboundSms();
                sms.sendSms(Prompts.NEW_USR_REG, the_number_which_trigger_request);
                r.addPlayText("Welcome to " + Prompts.Welcome + " Ops! you are not a registered user");
                r.addHangup();
                sendData(r, response);
                return;
            }
            //registered user, ask to record message.
            r.addPlayText(Prompts.DEFAULT_WELCOME_EN_PROMPT); // add play text object
            Record rec = new Record();
            rec.setFormat("wav");
            rec.setSilence(3);
            rec.setFileName(getAudioFileName());
            rec.setMaxDuration(15);
            r.addRecord(rec);
        } else if ((null != event) && event.equalsIgnoreCase("NewSMS")) {

            String extractedText = request.getParameter("message");
            if (extractedText.startsWith("REG ")) {
                parseNewUserRegistrationMessage(extractedText.substring(4), the_number_which_trigger_request);
            } else if (extractedText.startsWith("CNF ")) {
                parseConferenceMessage(extractedText.substring(4), the_number_which_trigger_request);
            } else {
                sendSms(the_number_which_trigger_request, Prompts.SMS_HELP);
            }
        } else if ((null != event) && event.equalsIgnoreCase("Record")) {

            String audioUrl = request.getParameter("data");
            r.addPlayText(Prompts.CALL_CLOSING);
            r.addHangup();
            sendData(r, response);

            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Inbound.class.getName()).log(Level.SEVERE, null, ex);
            }

            Customer customer = store.findCustomer(the_number_which_trigger_request);
            if (customer != null) {

                //trigger outbound call for all the number
                OutboundCalls calls = new OutboundCalls();
                //Also send an sms to all these emergency number
                OutboundSms sms = new OutboundSms();
                for (String number : customer.getEmergencyNos()) {
                    if (number == null || number.isEmpty()) {
                        continue;
                    }
                    calls.dialNumber(number, customer.getDefaultPrompt(), audioUrl);
                    sms.sendSms(customer.getDefaultPrompt(), number);
                }

                TwittEmergencyMessage message = new TwittEmergencyMessage(customer, " -- TEST MESSAGE --"
                        + "Hi there is an emergency notificatoin from " + customer.getFirstName() + " \n"
                        + " -- " + audioUrl);
                message.start();
            }

            return;

        } else {
            r.addPlayText("call is disconnecting ");
            r.addHangup();
        }

        sendData(r, response);
    }

    private void sendData(Response r, HttpServletResponse response) {
        String kookooResponseOutput = r.getXML();
        try {
            PrintWriter out = response.getWriter();
            out.println(kookooResponseOutput);
            out.flush();
        } catch (Exception ignore) {
            System.out.println(ignore.toString());
        }
    }

    public static String getAudioFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date()).toUpperCase(Locale.getDefault());
    }

    public String generatePIN() {

        //generate a 4 digit integer 1000 <10000
        int randomPIN = (int) (Math.random() * 9000) + 1000;

        //Store integer in a string
        return String.valueOf(randomPIN);
    }

    private void parseNewUserRegistrationMessage(String message, String sender) {

        if (message == null || message.isEmpty()) {
            sendSms(sender, " " + Prompts.NEW_USR_REG);
        }

        String[] details = message.split(" ");

        if (details == null || details.length < 2) {
            sendSms(sender, " " + Prompts.NEW_USR_REG);
        }

        DataStore store = DataStore.getInstance();
        Customer existingDetails = store.findCustomer(sender);
        if (existingDetails != null) {

            String[] tempList = new String[details.length - 1];
            for (int i = 1; i < details.length; i++) {
                tempList[i - 1] = details[i];
            }
            existingDetails.setEmergencyNos(tempList);

        } else {
            Customer customer = new Customer();
            customer.setFirstName(details[0]);
            customer.setMobileNo(sender);
            String[] tempList = new String[details.length - 1];
            for (int i = 1; i < details.length; i++) {
                tempList[i - 1] = details[i];
            }
            customer.setEmergencyNos(tempList);

            store.addCustomer(customer);
        }

        sendSms(sender, " thank you! for sharing your details, please dial to 08025149732 when you need help!");
    }

    private void parseConferenceMessage(String message, String sender) {
        if (message == null || message.isEmpty()) {
            sendSms(sender, " Invalid conference details \n " + Prompts.CREATE_CONF);
        }

        String[] nos = message.split(" ");

        if (nos == null || nos.length < 2) {
            sendSms(sender, " Invalid conference details \n " + Prompts.CREATE_CONF);
        }

        String confNo = generatePIN();
        for (String number : nos) {
            CreateConfCalls calls = new CreateConfCalls();
            calls.dialNumber(number, confNo);
        }

        sendSms(sender, " thank you! Please wait a moment, we are creating your conference");
    }

    private void sendSms(String number, String message) {

        OutboundSms sms = new OutboundSms();
        sms.sendSms(message, number);
    }
}
