package org.nearbyshops.Model;

/**
 * Created by sumeet on 18/9/16.
 */
public class Settings {

    // Table Name
    public static final String TABLE_NAME = "SETTINGS";

    // column Names
    public static final String SETTING_CONFIGURATION_ID = "SETTING_CONFIGURATION_ID";
    public static final String END_USER_ENABLED_DEFAULT = "END_USER_ENABLED_DEFAULT";
    public static final String DISTRIBUTOR_ENABLED_DEFAULT = "DISTRIBUTOR_ENABLED_DEFAULT";
    public static final String STAFF_ACCOUNT_ENABLED_DEFAULT = "STAFF_ACCOUNT_ENABLED_DEFAULT";


    // Create Table Statement
    public static final String createTableSettingsPostgres
            = "CREATE TABLE IF NOT EXISTS " + Settings.TABLE_NAME + "("
            + " " + Settings.SETTING_CONFIGURATION_ID + " SERIAL PRIMARY KEY,"
            + " " + Settings.END_USER_ENABLED_DEFAULT + " boolean,"
            + " " + Settings.DISTRIBUTOR_ENABLED_DEFAULT + " boolean,"
            + " " + Settings.STAFF_ACCOUNT_ENABLED_DEFAULT + " boolean"
            + ")";



    // Instance Variables
    private int settingsID;
    private String endUserEnabledByDefault;
    private String distributorEnabledByDefault;
    private String staffEnabledByDefault;


    public int getSettingsID() {
        return settingsID;
    }

    public void setSettingsID(int settingsID) {
        this.settingsID = settingsID;
    }

    public String getEndUserEnabledByDefault() {
        return endUserEnabledByDefault;
    }

    public void setEndUserEnabledByDefault(String endUserEnabledByDefault) {
        this.endUserEnabledByDefault = endUserEnabledByDefault;
    }

    public String getDistributorEnabledByDefault() {
        return distributorEnabledByDefault;
    }

    public void setDistributorEnabledByDefault(String distributorEnabledByDefault) {
        this.distributorEnabledByDefault = distributorEnabledByDefault;
    }

    public String getStaffEnabledByDefault() {
        return staffEnabledByDefault;
    }

    public void setStaffEnabledByDefault(String staffEnabledByDefault) {
        this.staffEnabledByDefault = staffEnabledByDefault;
    }
}
