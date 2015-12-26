/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ens.demo.twitter;

import ens.ivr.datastore.Customer;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Arun Kumar
 */
public class TwittEmergencyMessage extends Thread {

    private Customer toCustomer;

    private String message;

    public TwittEmergencyMessage(Customer to, String message) {
        this.toCustomer = to;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            if (toCustomer.getToken() == null || toCustomer.getToken().isEmpty()) {
                return;
            }
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream("twitter4j.properties"));
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(properties.getProperty("twitter4j.oauth.consumerKey"))
                    .setOAuthConsumerSecret(properties.getProperty("twitter4j.oauth.consumerSecret"))
                    .setOAuthAccessToken(toCustomer.getToken())
                    .setOAuthAccessTokenSecret(toCustomer.getTokenSecret());
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
            StatusUpdate update = new StatusUpdate(message);
            Status status = twitter.updateStatus(update);
            System.out.println("Successfully updated the status to [" + status.getText() + "].");
        } catch (TwitterException ex) {
            Logger.getLogger(TwittEmergencyMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TwittEmergencyMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
