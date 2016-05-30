package org.nearbyshops.ContractClasses;

/**
 * Created by sumeet on 29/5/16.
 */
public class OrderContract {

    // Table Name for Distributor
    public static final String TABLE_NAME = "CUST_ORDER";

    // Column names for Distributor

    public static final String ORDER_ID = "ORDER_ID";
    public static final String END_USER_ID = "ID"; // foreign Key
    public static final String SHOP_ID = "SHOP_ID"; // foreign Key
    public static final String ORDER_STATUS = "ORDER_STATUS";
    public static final String DELIVERY_CHARGES = "DELIVERY_CHARGES";
}
