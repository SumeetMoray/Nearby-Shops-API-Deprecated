package org.nearbyshops.ModelItemSubmission.Deprecated;

import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelItemSubmission.ItemSubmission;

import java.sql.Timestamp;

/**
 * Created by sumeet on 15/3/17.
 */
public class ItemSubmissionDetails {


    // Table Name
    public static final String TABLE_NAME = "ITEM_SUBMISSIONS_DETAILS";

    // column names
    public static final String ITEM_SUBMISSION_ID = "ITEM_SUBMISSION_ID";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String TIMESTAMP_SUBMITTED = "TIMESTAMP_SUBMITTED";
    public static final String TIMESTAMP_APPROVED = "TIMESTAMP_APPROVED";
    public static final String STATUS = "STATUS";
    public static final String USER_ID = "USER_ID";

    // removed columns
//    public static final String ITEM_VERSION = "ITEM_VERSION";


    // Create Table Statement
    public static final String createTablePostgres = "CREATE TABLE IF NOT EXISTS "
            + ItemSubmissionDetails.TABLE_NAME + "("
            + " " + ItemSubmissionDetails.ITEM_SUBMISSION_ID + " INT NOT NULL,"
            + " " + ItemSubmissionDetails.ITEM_ID + " int,"
            + " " + ItemSubmissionDetails.TIMESTAMP_SUBMITTED + "  timestamp with time zone NOT NULL DEFAULT now(),"
            + " " + ItemSubmissionDetails.TIMESTAMP_APPROVED + "  timestamp with time zone ,"
            + " " + ItemSubmissionDetails.STATUS + " int,"
            + " FOREIGN KEY(" + ItemSubmissionDetails.ITEM_SUBMISSION_ID +") REFERENCES " + ItemSubmission.TABLE_NAME + "(" + ItemSubmission.ITEM_SUBMISSION_ID + "),"
            + " FOREIGN KEY(" + ItemSubmissionDetails.ITEM_ID +") REFERENCES " + Item.TABLE_NAME + "(" + Item.ITEM_ID + ")"
            + ")";


//    + " UNIQUE (" + ItemSubmissionDetails.ITEM_ID + "," + ItemSubmissionDetails.ITEM_VERSION + ")"




    // instance variables

    private int itemSubmissionID;
    private int itemID;
    private Timestamp timestampSubmitted;
    private Timestamp timestampApproved;
    private int status;





    public Timestamp getTimestampApproved() {
        return timestampApproved;
    }

    public void setTimestampApproved(Timestamp timestampApproved) {
        this.timestampApproved = timestampApproved;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getItemSubmissionID() {
        return itemSubmissionID;
    }

    public void setItemSubmissionID(int itemSubmissionID) {
        this.itemSubmissionID = itemSubmissionID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public Timestamp getTimestampSubmitted() {
        return timestampSubmitted;
    }

    public void setTimestampSubmitted(Timestamp timestampSubmitted) {
        this.timestampSubmitted = timestampSubmitted;
    }
}
