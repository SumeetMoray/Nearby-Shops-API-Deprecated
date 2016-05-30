package org.nearbyshops.ContractClasses;

/**
 * Created by sumeet on 29/5/16.
 */
public class OrderItemContract {

    // Table Name for Distributor
    public static final String TABLE_NAME = "ORDER_ITEM";

    // Column names for Distributor

    public static final String ITEM_ID = "ITEM_ID";     // FOREIGN KEY
    public static final String ORDER_ID = "ORDER_ID";   // Foreign KEY
    public static final String ITEM_QUANTITY = "ITEM_QUANTITY";
    public static final String ITEM_PRICE_AT_ORDER = "ITEM_PRICE_AT_ORDER";

}
