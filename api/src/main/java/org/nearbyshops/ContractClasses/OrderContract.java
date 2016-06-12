package org.nearbyshops.ContractClasses;

/**
 * Created by sumeet on 29/5/16.
 */
public class OrderContract {

    // Table Name for Distributor
    public static final String TABLE_NAME = "CUSTOMER_ORDER";

    // Column names for Distributor

    public static final String ORDER_ID = "ORDER_ID";
    public static final String END_USER_ID = "END_USER_ID"; // foreign Key
    public static final String SHOP_ID = "SHOP_ID"; // foreign Key
    public static final String ORDER_STATUS = "ORDER_STATUS";
    public static final String DELIVERY_CHARGES = "DELIVERY_CHARGES";
    public static final String DELIVERY_ADDRESS_ID = "DELIVERY_ADDRESS_ID";
    //public static final String RECEIVERS_PHONE_NUMBER = "RECEIVERS_PHONE_NUMBER";
    //public static final String RECEIVERS_NAME = "RECEIVERS_NAME";
    public static final String PICK_FROM_SHOP = "PICK_FROM_SHOP";
    public static final String DATE_TIME_PLACED = "DATE_TIME_PLACED";
}
