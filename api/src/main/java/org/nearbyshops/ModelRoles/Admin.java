package org.nearbyshops.ModelRoles;

/**
 * Created by sumeet on 29/5/16.
 */
public class Admin {

    // Table Name for Distributor
    public static final String TABLE_NAME = "ADMIN";

    // Column names for Distributor

    public static final String ADMIN_ID = "ID";
    public static final String ADMIN_NAME = "ADMIN_NAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String ABOUT = "ABOUT";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";


    // Create Table CurrentServiceConfiguration Provider
    public static final String createTableAdminPostgres =
            "CREATE TABLE IF NOT EXISTS " + Admin.TABLE_NAME + "("
            + " " + Admin.ADMIN_ID + " SERIAL PRIMARY KEY,"
            + " " + Admin.USER_NAME + " text UNIQUE NOT NULL,"
            + " " + Admin.PASSWORD + " text NOT NULL,"
            + " " + Admin.ADMIN_NAME + " text,"

                    + " " + Admin.ABOUT + " text,"
                    + " " + Admin.PROFILE_IMAGE_URL + " text"
                    + ")";



    // Instance Variables
    private int adminID;
    private String administratorName;
    private String username;
    private String password;
    private String about;
    private String profileImageURL;


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

    // Getter and Setters
    public int getAdminID() {
        return adminID;
    }
    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }
    public String getAdministratorName() {
        return administratorName;
    }
    public void setAdministratorName(String administratorName) {
        this.administratorName = administratorName;
    }
}
