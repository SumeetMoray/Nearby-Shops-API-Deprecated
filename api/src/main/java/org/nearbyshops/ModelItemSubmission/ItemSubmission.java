package org.nearbyshops.ModelItemSubmission;

import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.ModelStats.ItemStats;

import java.sql.Timestamp;

/**
 * Created by sumeet on 15/3/17.
 */
public class ItemSubmission {

    // Table Name
    public static final String TABLE_NAME = "ITEM_SUBMISSION";

    // column names
    public static final String ITEM_SUBMISSION_ID = "ITEM_SUBMISSION_ID";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_DESC = "ITEM_DESC";

    public static final String ITEM_IMAGE_URL = "ITEM_IMAGE_URL";
    public static final String ITEM_CATEGORY_ID = "ITEM_CATEGORY_ID";

    // recently added
    public static final String QUANTITY_UNIT = "QUANTITY_UNIT";
    public static final String DATE_TIME_CREATED = "DATE_TIME_CREATED";
    public static final String TIMESTAMP_UPDATED = "TIMESTAMP_UPDATED";
    public static final String ITEM_DESCRIPTION_LONG = "ITEM_DESCRIPTION_LONG";

    public static final String LIST_PRICE = "LIST_PRICE";
    public static final String BARCODE = "BARCODE";
    public static final String BARCODE_FORMAT = "BARCODE_FORMAT";
    public static final String IMAGE_COPYRIGHTS = "IMAGE_COPYRIGHTS";

    public static final String GIDB_ITEM_ID = "GIDB_ITEM_ID";
    public static final String GIDB_SERVICE_URL = "GIDB_SERVICE_URL";


    // columns that keep submission record
    public static final String ITEM_ID = "ITEM_ID";
    public static final String TIMESTAMP_SUBMITTED = "TIMESTAMP_SUBMITTED";
    public static final String TIMESTAMP_APPROVED = "TIMESTAMP_APPROVED";
    public static final String STATUS = "STATUS";
    public static final String USER_ID = "USER_ID";




    // Create Table Statement
    public static final String createTableItemSubmissionPostgres = "CREATE TABLE IF NOT EXISTS "
            + ItemSubmission.TABLE_NAME + "("
            + " " + ItemSubmission.ITEM_SUBMISSION_ID + " SERIAL PRIMARY KEY,"
            + " " + ItemSubmission.ITEM_NAME + " text,"
            + " " + ItemSubmission.ITEM_DESC + " text,"

            + " " + ItemSubmission.ITEM_IMAGE_URL + " text,"
            + " " + ItemSubmission.ITEM_CATEGORY_ID + " INT,"

            + " " + ItemSubmission.QUANTITY_UNIT + " text,"
            + " " + ItemSubmission.DATE_TIME_CREATED + "  timestamp with time zone NOT NULL DEFAULT now(),"
            + " " + ItemSubmission.TIMESTAMP_UPDATED + "  timestamp with time zone ,"
            + " " + ItemSubmission.ITEM_DESCRIPTION_LONG + " text,"

            + " " + ItemSubmission.LIST_PRICE + " float,"
            + " " + ItemSubmission.BARCODE + " text,"
            + " " + ItemSubmission.BARCODE_FORMAT + " text,"
            + " " + ItemSubmission.IMAGE_COPYRIGHTS + " text,"

            + " " + ItemSubmission.GIDB_ITEM_ID + " INT,"
            + " " + ItemSubmission.GIDB_SERVICE_URL + " text,"

            + " " + ItemSubmission.ITEM_ID + " int,"
            + " " + ItemSubmission.TIMESTAMP_SUBMITTED + "  timestamp with time zone NOT NULL DEFAULT now(),"
            + " " + ItemSubmission.TIMESTAMP_APPROVED + "  timestamp with time zone ,"
            + " " + ItemSubmission.STATUS + " int,"
            + " " + ItemSubmission.USER_ID + " int,"

            + " FOREIGN KEY(" + ItemSubmission.ITEM_ID +") REFERENCES " + Item.TABLE_NAME + "(" + Item.ITEM_ID + ") ON DELETE CASCADE,"
            + " FOREIGN KEY(" + ItemSubmission.ITEM_CATEGORY_ID +") REFERENCES " + ItemCategory.TABLE_NAME + "(" + ItemCategory.ITEM_CATEGORY_ID + ") ON DELETE SET NULL "
            + ")";





    // Instance Variables

    private Integer itemID;
    private int itemSubmissionID;
    private Timestamp timestampApproved;
    private Timestamp timestampSubmitted;
    private int status;
    private int userID;

    private Item item;


    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public int getItemSubmissionID() {
        return itemSubmissionID;
    }

    public void setItemSubmissionID(int itemSubmissionID) {
        this.itemSubmissionID = itemSubmissionID;
    }

    public Timestamp getTimestampApproved() {
        return timestampApproved;
    }

    public void setTimestampApproved(Timestamp timestampApproved) {
        this.timestampApproved = timestampApproved;
    }

    public Timestamp getTimestampSubmitted() {
        return timestampSubmitted;
    }

    public void setTimestampSubmitted(Timestamp timestampSubmitted) {
        this.timestampSubmitted = timestampSubmitted;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
