package org.nearbyshops.ModelRoles;

/**
 * Created by sumeet on 29/5/16.
 */
public class Staff {

    // Table Name for Distributor
    public static final String TABLE_NAME = "STAFF";

    // Column names for Distributor

    public static final String STAFF_ID = "ID";
    public static final String STAFF_NAME = "STAFF_NAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";

    // to be Implemented
    public static final String IS_ENABLED = "IS_ENABLED";
    public static final String IS_WAITLISTED = "IS_WAITLISTED";


    // Create Table CurrentServiceConfiguration Provider
    public static final String createTableStaffPostgres =
            "CREATE TABLE IF NOT EXISTS " + Staff.TABLE_NAME + "("
            + " " + Staff.STAFF_ID + " SERIAL PRIMARY KEY,"
            + " " + Staff.USER_NAME + " text UNIQUE NOT NULL,"
            + " " + Staff.PASSWORD + " text NOT NULL,"
            + " " + Staff.IS_ENABLED + " boolean,"
            + " " + Staff.IS_WAITLISTED + " boolean,"
            + " " + Staff.STAFF_NAME + " text" + ")";


    
    // Instance Variables
    private int staffID;
    private String staffName;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
