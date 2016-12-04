package org.nearbyshops.ModelSecurity;

import org.nearbyshops.ModelRoles.*;

import java.sql.Timestamp;

/**
 * Created by sumeet on 14/6/16.
 */
public class ForbiddenOperations {

    // Note : ShopAdmin has one to one relationship with Shop therefore the columns of ShopAdmin has been
    // merged inside the Shop table for preserving data and relationship consistency.
    // The entity remains distinct and not the same as Shop which in simple terms imply that although both
    // entity columns are merged in one table the entity remains distinct.

    // Table Name : Table does not exist for ShopAdmin because the columns are merged in Shop Table

    // Table Name
    public static final String TABLE_NAME = "FORBIDDEN_OPERATIONS";

    // column Names
    public static final String OPERATION_ID = "OPERATION_ID"; // primary key

    public static final String ADMIN_ID = "ADMIN_ID"; // foreign key
    public static final String STAFF_ID = "STAFF_ID"; // foreign key
    public static final String SHOP_ADMIN_ID = "SHOP_ADMIN_ID"; // foreign key
    public static final String SHOP_STAFF_ID = "SHOP_STAFF_ID"; // foreign key
    public static final String DELIVERY_GUY_SELF_ID = "DELIVERY_GUY_SELF_ID"; // foreign key
    public static final String END_USER_ID = "END_USER_ID"; // foreign key

    public static final String ACTIVITY_INFO = "ACTIVITY_INFO";
    public static final String ENDPOINT_INFO = "ENDPOINT_INFO";

    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";




//    public static final String NAME = "NAME";
//    public static final String SHOP_ID = "SHOP_ID";

//    public static final String USERNAME = "USERNAME";
//    public static final String PASSWORD = "PASSWORD";

//    public static final String ABOUT = "ABOUT";
//    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
//    public static final String PHONE_NUMBER = "PHONE_NUMBER";

    // to be Implemented
//    public static final String DESIGNATION = "DESIGNATION";
//    public static final String ADMIN_ENABLED = "ADMIN_ENABLED";
//    public static final String ADMIN_WAITLISTED = "ADMIN_WAITLISTED";

    // to be Implemented
//    public static final String IS_ENABLED = "IS_ENABLED";
//    public static final String IS_WAITLISTED = "IS_WAITLISTED";

//    public static final String GOVERNMENT_ID_NUMBER = "GOVERNMENT_ID_NUMBER";
//    public static final String GOVERNMENT_ID_NAME = "GOVERNMENT_ID_NAME";





    // create table statement
    public static final String createTableForbiddenOperationPostgres
            = "CREATE TABLE IF NOT EXISTS " + ForbiddenOperations.TABLE_NAME + "("
            + " " + ForbiddenOperations.OPERATION_ID + " SERIAL PRIMARY KEY,"

            + " " + ForbiddenOperations.ACTIVITY_INFO + " text,"
            + " " + ForbiddenOperations.ENDPOINT_INFO + " text,"
            + " " + ForbiddenOperations.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"

            + " " + ForbiddenOperations.ADMIN_ID + " INT,"
            + " " + ForbiddenOperations.STAFF_ID + " INT,"
            + " " + ForbiddenOperations.SHOP_ADMIN_ID + " INT,"
            + " " + ForbiddenOperations.SHOP_STAFF_ID + " INT,"
            + " " + ForbiddenOperations.DELIVERY_GUY_SELF_ID + " INT,"
            + " " + ForbiddenOperations.END_USER_ID + " INT,"

            + " FOREIGN KEY(" + ForbiddenOperations.ADMIN_ID +") REFERENCES " + Admin.TABLE_NAME + "(" + Admin.ADMIN_ID + "),"
            + " FOREIGN KEY(" + ForbiddenOperations.STAFF_ID +") REFERENCES " + Staff.TABLE_NAME + "(" + Staff.STAFF_ID + "),"
            + " FOREIGN KEY(" + ForbiddenOperations.SHOP_ADMIN_ID +") REFERENCES " + ShopAdmin.TABLE_NAME + "(" + ShopAdmin.SHOP_ADMIN_ID + "),"
            + " FOREIGN KEY(" + ForbiddenOperations.DELIVERY_GUY_SELF_ID +") REFERENCES " + DeliveryGuySelf.TABLE_NAME + "(" + DeliveryGuySelf.ID + "),"
            + " FOREIGN KEY(" + ForbiddenOperations.END_USER_ID +") REFERENCES " + EndUser.TABLE_NAME + "(" + EndUser.END_USER_ID + ")"
            + ")";



//            + " " + ForbiddenOperations.IS_ENABLED + " boolean NOT NULL,"
//            + " " + ForbiddenOperations.IS_WAITLISTED + " boolean NOT NULL"

//            + " FOREIGN KEY(" + ShopAdmin.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + ")"
//            + ")";


    // instance Variables

    private int operationID;
    private String activityInfo;
    private String endpointInfo;
    private Timestamp timestampCreated;

    private Integer adminID;
    private Integer staffID;
    private Integer shopAdminID;
    private Integer shopStaffID;
    private Integer deliveryGuySelfID;
    private Integer endUserID;

    // getter and setters


    public int getOperationID() {
        return operationID;
    }

    public void setOperationID(int operationID) {
        this.operationID = operationID;
    }

    public String getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(String activityInfo) {
        this.activityInfo = activityInfo;
    }

    public String getEndpointInfo() {
        return endpointInfo;
    }

    public void setEndpointInfo(String endpointInfo) {
        this.endpointInfo = endpointInfo;
    }

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public Integer getStaffID() {
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public Integer getShopAdminID() {
        return shopAdminID;
    }

    public void setShopAdminID(Integer shopAdminID) {
        this.shopAdminID = shopAdminID;
    }

    public Integer getShopStaffID() {
        return shopStaffID;
    }

    public void setShopStaffID(Integer shopStaffID) {
        this.shopStaffID = shopStaffID;
    }

    public Integer getDeliveryGuySelfID() {
        return deliveryGuySelfID;
    }

    public void setDeliveryGuySelfID(Integer deliveryGuySelfID) {
        this.deliveryGuySelfID = deliveryGuySelfID;
    }

    public Integer getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(Integer endUserID) {
        this.endUserID = endUserID;
    }
}
