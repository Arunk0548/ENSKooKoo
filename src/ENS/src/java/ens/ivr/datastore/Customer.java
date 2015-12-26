package ens.ivr.datastore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Comparator;

/**
 *
 * @author Arun Kumar
 */
public class Customer {

    private String token;
    private String tokenSecret;
    private long userId;
    private String firstName;
    private String mobileNo;

    private String emergencyNo1;

    private String emergencyNo2;

    private String emergencyNo3;

    /**
     * @return the defaultPrompt
     */
    public String getDefaultPrompt() {
        return Prompts.DEFAULT_EN_PROMPT + " from " + getFirstName() + " Please take necessary action at earliest";
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        Gson json = new Gson();
        return json.toJson(this);
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the emergencyNo1
     */
    public String getEmergencyNo1() {
        return emergencyNo1;
    }

    /**
     * @param emergencyNo1 the emergencyNo1 to set
     */
    public void setEmergencyNo1(String emergencyNo1) {
        this.emergencyNo1 = emergencyNo1;
    }

    /**
     * @return the emergencyNo2
     */
    public String getEmergencyNo2() {
        return emergencyNo2;
    }

    /**
     * @param emergencyNo2 the emergencyNo2 to set
     */
    public void setEmergencyNo2(String emergencyNo2) {
        this.emergencyNo2 = emergencyNo2;
    }

    /**
     * @return the emergencyNo3
     */
    public String getEmergencyNo3() {
        return emergencyNo3;
    }

    /**
     * @param emergencyNo3 the emergencyNo3 to set
     */
    public void setEmergencyNo3(String emergencyNo3) {
        this.emergencyNo3 = emergencyNo3;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the tokenSecret
     */
    public String getTokenSecret() {
        return tokenSecret;
    }

    /**
     * @param tokenSecret the tokenSecret to set
     */
    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    /**
     * @return the emergencyNos
     */
    public String[] getEmergencyNos() {
        return new String[]{getEmergencyNo1(), getEmergencyNo2(), getEmergencyNo3()};
    }

    /**
     * @param emergencyNos the emergencyNos to set
     */
    public void setEmergencyNos(String[] emergencyNos) {

        if (emergencyNos == null || emergencyNos.length == 0) {
            return;
        }

        if (emergencyNos.length == 1) {
            setEmergencyNo1(emergencyNos[0]);
        } else if (emergencyNos.length == 2) {
            setEmergencyNo2(emergencyNos[1]);
        } else if (emergencyNos.length > 2) {
            setEmergencyNo1(emergencyNos[0]);
            setEmergencyNo2(emergencyNos[1]);
            setEmergencyNo3(emergencyNos[2]);
        }

    }

    public static class CustomerComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.getMobileNo().compareTo(o2.getMobileNo());
        }
    }
}
