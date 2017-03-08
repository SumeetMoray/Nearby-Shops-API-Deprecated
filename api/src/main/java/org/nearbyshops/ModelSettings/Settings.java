package org.nearbyshops.ModelSettings;

import org.nearbyshops.Model.Item;

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
//    public static final String STAFF_ACCOUNT_ENABLED_DEFAULT = "STAFF_ACCOUNT_ENABLED_DEFAULT";
    public static final String SHOP_ENABLED_BY_DEFAULT = "SHOP_ENABLED_BY_DEFAULT";
    public static final String GOOGLE_MAPS_API_KEY = "GOOGLE_MAPS_API_KEY";



    // Create Table Statement
    public static final String createTableSettingsPostgres
            = "CREATE TABLE IF NOT EXISTS " + Settings.TABLE_NAME + "("
            + " " + Settings.SETTING_CONFIGURATION_ID + " SERIAL PRIMARY KEY,"
            + " " + Settings.END_USER_ENABLED_DEFAULT + " boolean,"
            + " " + Settings.DISTRIBUTOR_ENABLED_DEFAULT + " boolean,"
            + " " + Settings.SHOP_ENABLED_BY_DEFAULT + " boolean,"
            + " " + Settings.GOOGLE_MAPS_API_KEY + " text"
            + ")";


    public static final String upgradeTableSchema =
            "ALTER TABLE IF EXISTS " + Settings.TABLE_NAME
                    + " ADD COLUMN IF NOT EXISTS " + Settings.SHOP_ENABLED_BY_DEFAULT + " boolean";






    // Instance Variables
    private int settingsID;
    private Boolean endUserEnabledByDefault;
    private Boolean distributorEnabledByDefault;
    private boolean shopEnabledByDefault;
//    private String staffEnabledByDefault;
    private String googleMapsAPIKey;


    public boolean isShopEnabledByDefault() {
        return shopEnabledByDefault;
    }

    public void setShopEnabledByDefault(boolean shopEnabledByDefault) {
        this.shopEnabledByDefault = shopEnabledByDefault;
    }

    public int getSettingsID() {
        return settingsID;
    }

    public void setSettingsID(int settingsID) {
        this.settingsID = settingsID;
    }

    public Boolean getEndUserEnabledByDefault() {
        return endUserEnabledByDefault;
    }

    public void setEndUserEnabledByDefault(Boolean endUserEnabledByDefault) {
        this.endUserEnabledByDefault = endUserEnabledByDefault;
    }

    public Boolean getDistributorEnabledByDefault() {
        return distributorEnabledByDefault;
    }

    public void setDistributorEnabledByDefault(Boolean distributorEnabledByDefault) {
        this.distributorEnabledByDefault = distributorEnabledByDefault;
    }

    public String getGoogleMapsAPIKey() {
        return googleMapsAPIKey;
    }

    public void setGoogleMapsAPIKey(String googleMapsAPIKey) {
        this.googleMapsAPIKey = googleMapsAPIKey;
    }
}
