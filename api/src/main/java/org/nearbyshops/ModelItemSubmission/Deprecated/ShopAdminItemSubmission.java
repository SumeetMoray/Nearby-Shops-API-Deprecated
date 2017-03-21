package org.nearbyshops.ModelItemSubmission.Deprecated;

import org.nearbyshops.ModelItemSubmission.ItemSubmission;
import org.nearbyshops.ModelRoles.ShopAdmin;

/**
 * Created by sumeet on 15/3/17.
 */
public class ShopAdminItemSubmission {


    // Table Name
    public static final String TABLE_NAME = "STAFF_ADMIN_ITEM_SUBMISSION";

    // column names
    public static final String SHOP_ADMIN_ID = "SHOP_ADMIN_ID";
    public static final String ITEM_SUBMISSION_ID = "ITEM_SUBMISSION_ID";


    // Create Table Statement
    public static final String createTableItemPostgres = "CREATE TABLE IF NOT EXISTS "
            + ShopAdminItemSubmission.TABLE_NAME + "("
            + " " + ShopAdminItemSubmission.ITEM_SUBMISSION_ID + " INT,"
            + " " + ShopAdminItemSubmission.SHOP_ADMIN_ID + " INT,"
            + " FOREIGN KEY(" + ShopAdminItemSubmission.SHOP_ADMIN_ID +") REFERENCES " + ShopAdmin.TABLE_NAME + "(" + ShopAdmin.SHOP_ADMIN_ID + "),"
            + " FOREIGN KEY(" + ShopAdminItemSubmission.ITEM_SUBMISSION_ID +") REFERENCES " + ItemSubmission.TABLE_NAME + "(" + ItemSubmission.ITEM_SUBMISSION_ID + ")"
            + ")";




    // instance variables

    private int shopAdminID;
    private int itemSubmissionID;





    public int getShopAdminID() {
        return shopAdminID;
    }

    public void setShopAdminID(int shopAdminID) {
        this.shopAdminID = shopAdminID;
    }

    public int getItemSubmissionID() {
        return itemSubmissionID;
    }

    public void setItemSubmissionID(int itemSubmissionID) {
        this.itemSubmissionID = itemSubmissionID;
    }
}
