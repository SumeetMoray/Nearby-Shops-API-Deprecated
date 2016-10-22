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
    //public static final String ORDER_STATUS = "ORDER_STATUS";
    public static final String DELIVERY_CHARGES = "DELIVERY_CHARGES";
    public static final String DELIVERY_ADDRESS_ID = "DELIVERY_ADDRESS_ID";
    //public static final String RECEIVERS_PHONE_NUMBER = "RECEIVERS_PHONE_NUMBER";
    //public static final String RECEIVERS_NAME = "RECEIVERS_NAME";
    public static final String PICK_FROM_SHOP = "PICK_FROM_SHOP";
    public static final String DATE_TIME_PLACED = "DATE_TIME_PLACED";

    public static final String STATUS_HOME_DELIVERY = "STATUS_HOME_DELIVERY";
    public static final String STATUS_PICK_FROM_SHOP = "STATUS_PICK_FROM_SHOP";
    public static final String DELIVERY_RECEIVED = "DELIVERY_RECEIVED";
    public static final String PAYMENT_RECEIVED = "PAYMENT_RECEIVED";

    public static final String DELIVERY_VEHICLE_SELF_ID = "DELIVERY_VEHICLE_SELF_ID";


    public static final String ORDER_TOTAL = "ORDER_TOTAL";
    public static final String TAX_AMOUNT = "TAX_AMOUNT";
    public static final String ITEM_COUNT = "ITEM_COUNT";
    public static final String APP_SERVICE_CHARGE = "APP_SERVICE_CHARGE";



    public static final String REASON_FOR_CANCELLED_BY_USER = "REASON_FOR_CANCELLED_BY_USER";
    public static final String REASON_FOR_CANCELLED_BY_SHOP = "REASON_FOR_CANCELLED_BY_SHOP";
    public static final String REASON_FOR_ORDER_RETURNED = "REASON_FOR_ORDER_RETURNED";

}
