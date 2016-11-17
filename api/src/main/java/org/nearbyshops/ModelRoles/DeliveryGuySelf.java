package org.nearbyshops.ModelRoles;

import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelDelivery.DeliveryAddress;

/**
 * Created by sumeet on 14/6/16.
 */
public class DeliveryGuySelf {

    // Table Name
    public static final String TABLE_NAME = "DELIVERY_GUY_SELF";

    // column Names
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String SHOP_ID = "SHOP_ID";

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String ABOUT = "ABOUT";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";

    // to be Implemented
    public static final String IS_ENABLED = "IS_ENABLED";
    public static final String IS_WAITLISTED = "IS_WAITLISTED";



    // create table statement
    public static final String createtableDeliveryGuySelfPostgres = "CREATE TABLE IF NOT EXISTS " + DeliveryGuySelf.TABLE_NAME + "("
            + " " + DeliveryGuySelf.ID + " SERIAL PRIMARY KEY,"
            + " " + DeliveryGuySelf.NAME + " text,"
            + " " + DeliveryGuySelf.SHOP_ID + " INT,"
            + " " + DeliveryGuySelf.USERNAME + " text UNIQUE,"
            + " " + DeliveryGuySelf.PASSWORD + " text,"

            + " " + DeliveryGuySelf.ABOUT + " text,"
            + " " + DeliveryGuySelf.PROFILE_IMAGE_URL + " text,"
            + " " + DeliveryGuySelf.PHONE_NUMBER + " text,"

            + " " + DeliveryGuySelf.IS_ENABLED + " boolean,"
            + " " + DeliveryGuySelf.IS_WAITLISTED + " boolean,"

            + " FOREIGN KEY(" + DeliveryGuySelf.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + ")"
            + ")";


    // instance Variables

    private int deliveryGuyID;
    private String name;
    private int shopID;

    private String username;
    private String password;

    private String about;
    private String profileImageURL;
    private String phoneNumber;

    private Boolean isEnabled;
    private Boolean isWaitlisted;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDeliveryGuyID() {
        return deliveryGuyID;
    }

    public void setDeliveryGuyID(int deliveryGuyID) {
        this.deliveryGuyID = deliveryGuyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getWaitlisted() {
        return isWaitlisted;
    }

    public void setWaitlisted(Boolean waitlisted) {
        isWaitlisted = waitlisted;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
}
