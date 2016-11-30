package org.nearbyshops.ModelRoles;

import java.sql.Timestamp;

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

    public static final String ABOUT = "ABOUT";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String DESIGNATION = "DESIGNATION";

    // to be Implemented
    public static final String IS_ENABLED = "IS_ENABLED";
//    public static final String IS_WAITLISTED = "IS_WAITLISTED";

    public static final String GOVERNMENT_ID_NUMBER = "GOVERNMENT_ID_NUMBER";
    public static final String GOVERNMENT_ID_NAME = "GOVERNMENT_ID_NAME";
    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";


    // Create Table CurrentServiceConfiguration Provider
    public static final String createTableStaffPostgres =
            "CREATE TABLE IF NOT EXISTS " + Staff.TABLE_NAME + "("
            + " " + Staff.STAFF_ID + " SERIAL PRIMARY KEY,"
            + " " + Staff.USER_NAME + " text UNIQUE NOT NULL,"
            + " " + Staff.PASSWORD + " text NOT NULL,"

            + " " + Staff.DESIGNATION + " text,"

            + " " + Staff.PHONE_NUMBER + " text,"

            + " " + Staff.GOVERNMENT_ID_NAME + " text,"
            + " " + Staff.GOVERNMENT_ID_NUMBER + " text,"
            + " " + Staff.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"

                    + Staff.ABOUT + " text,"
                    + Staff.PROFILE_IMAGE_URL + " text,"

            + " " + Staff.IS_ENABLED + " boolean NOT NULL,"
//            + " " + Staff.IS_WAITLISTED + " boolean,"
            + " " + Staff.STAFF_NAME + " text"
                    + ")";


    
    // Instance Variables
    private int staffID;
    private String staffName;
    private String username;
    private String password;
    private String designation;
    private String phone;
    private String govtIDName;
    private String govtIDNumber;
    private Timestamp timestampCreated;
    private String about;
    private String profileImageURL;
    private Boolean isEnabled;
//    private Boolean isWaitlisted;


    // Getter and Setters


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGovtIDName() {
        return govtIDName;
    }

    public void setGovtIDName(String govtIDName) {
        this.govtIDName = govtIDName;
    }

    public String getGovtIDNumber() {
        return govtIDNumber;
    }

    public void setGovtIDNumber(String govtIDNumber) {
        this.govtIDNumber = govtIDNumber;
    }

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
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
