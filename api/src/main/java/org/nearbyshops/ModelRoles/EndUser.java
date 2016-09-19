package org.nearbyshops.ModelRoles;

/**
 * Created by sumeet on 29/5/16.
 */
public class EndUser {


    // Table Name for EndUser
    public static final String TABLE_NAME = "END_USER";

    // Column names for EndUser

    public static final String END_USER_ID = "ID";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    // to be Implemented
    public static final String IS_ENABLED = "IS_ENABLED";
    public static final String IS_WAITLISTED = "IS_WAITLISTED";



    // Create Table EndUser

    public static final String createTableEndUserPostgres
            = "CREATE TABLE IF NOT EXISTS " + EndUser.TABLE_NAME + "("
            + " " + EndUser.END_USER_ID + " SERIAL PRIMARY KEY,"
            + " " + EndUser.USERNAME + " text UNIQUE,"
            + " " + EndUser.PASSWORD + " text,"
            + " " + Distributor.IS_ENABLED + " boolean,"
            + " " + Distributor.IS_WAITLISTED + " boolean"
            + ")";




    // Instance Variables
    private int endUserID;
    private String username;
    private String password;
    private Boolean isEnabled;
    private Boolean isWaitlisted;


    // Getter and Setters


    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getWaitlisted() {
        return isWaitlisted;
    }

    public void setWaitlisted(Boolean waitlisted) {
        isWaitlisted = waitlisted;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getEndUserID() {
        return endUserID;
    }
    public void setEndUserID(int endUserID) {
        this.endUserID = endUserID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
