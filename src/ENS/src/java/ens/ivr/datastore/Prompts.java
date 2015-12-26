/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ens.ivr.datastore;

/**
 *
 * @author Arun Kumar
 */
public class Prompts {

    public static final String Welcome = " Emergency Notification System ";
    public static final String API_KEY = "KOOKOO_API_KEY";

    public static final String NEW_USR_REG = "Ops! Invalid details, please register your details by sending an sms\n"
            + " HACKAR REG FirstName EMERGENCYNO1 EMERGENCYNO2 EMERGENCYNO3 "
            + "\n and send it to 09227507512";

    public static final String CALL_CLOSING = " disconnecting call, THANK YOU!";

    public static final String CREATE_CONF = " To create conference, send an sms by typing HACKAR CNF Number1 Number2 Number3 "
            + " and send it to 09227507512";

    public static final String CONF_CALL_PROMPT = "Hi, You are in emergency conference call system";

    public static final String DEFAULT_EN_PROMPT = " Hi there is an emergency! notification ";

    public static final String SMS_HELP = " Ops Invalid message, to register for emergency notification send and sms \n"
            + " HACKAR REG FirstName EMERGENCYNO1 EMERGENCYNO2 EMERGENCYNO3 to  09227507512 Or to start an emergency "
            + "conference send an sms HACKAR CNF Number1 Number2 Number3  to 09227507512 .";

    public static final String DEFAULT_WELCOME_EN_PROMPT = " Hi this is emergency notification system, please record your message after the beep";

}
