package org.nearbyshops.ModelDelivery;

import org.nearbyshops.Model.Shop;

/**
 * Created by sumeet on 14/6/16.
 */
public class DeliveryGuySelf {

    // Table Name
    public static final String TABLE_NAME = "DELIVERY_VEHICLE_SELF";

    // column Names
    public static final String ID = "deliveryGuyID";
    public static final String VEHICLE_NAME  = "VEHICLE_NAME";
    public static final String SHOP_ID = "SHOP_ID";




    // create table statement
    public static final String createtableDeliveryGuySelfPostgres = "CREATE TABLE IF NOT EXISTS " + DeliveryGuySelf.TABLE_NAME + "("
            + " " + DeliveryGuySelf.ID + " SERIAL PRIMARY KEY,"
            + " " + DeliveryGuySelf.VEHICLE_NAME + " VARCHAR(30),"
            + " " + DeliveryGuySelf.SHOP_ID + " INT,"
            + " FOREIGN KEY(" + DeliveryGuySelf.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + ")"
            + ")";


    // instance Variables

    private int deliveryGuyID;
    private String vehicleName;
    private int shopID;



    public int getDeliveryGuyID() {
        return deliveryGuyID;
    }

    public void setDeliveryGuyID(int deliveryGuyID) {
        this.deliveryGuyID = deliveryGuyID;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
}
