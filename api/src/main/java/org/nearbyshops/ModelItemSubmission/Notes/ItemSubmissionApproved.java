package org.nearbyshops.ModelItemSubmission.Notes;

import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelItemSubmission.ItemSubmission;

import java.sql.Timestamp;

/**
 * Created by sumeet on 15/3/17.
 */
public class ItemSubmissionApproved {

    // Table Name
    public static final String TABLE_NAME = "ITEM_SUBMISSIONS_APPROVED";

    // column names
    public static final String ITEM_SUBMISSION_ID = "ITEM_SUBMISSION_ID";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String ITEM_VERSION = "ITEM_VERSION";
    public static final String TIMESTAMP_APPROVED = "TIMESTAMP_APPROVED";


    // Create Table Statement
    public static final String createTablePostgres = "CREATE TABLE IF NOT EXISTS "
            + ItemSubmissionApproved.TABLE_NAME + "("
            + " " + ItemSubmissionApproved.ITEM_SUBMISSION_ID + " INT NOT NULL,"
            + " " + ItemSubmissionApproved.ITEM_ID + " int,"
            + " " + ItemSubmissionApproved.ITEM_VERSION + " int,"
            + " " + ItemSubmissionApproved.TIMESTAMP_APPROVED + "  timestamp with time zone NOT NULL DEFAULT now(),"

            + " FOREIGN KEY(" + ItemSubmissionApproved.ITEM_SUBMISSION_ID +") REFERENCES " + ItemSubmission.TABLE_NAME + "(" + ItemSubmission.ITEM_SUBMISSION_ID + "),"
            + " FOREIGN KEY(" + ItemSubmissionApproved.ITEM_ID +") REFERENCES " + Item.TABLE_NAME + "(" + Item.ITEM_ID + "),"
            + " UNIQUE (" + ItemSubmissionApproved.ITEM_ID + "," + ItemSubmissionApproved.ITEM_VERSION + ")"
            + ")";




    // instance variables

    private int itemSubmissionID;
    private int itemID;
    private int itemVersion;
    private Timestamp timestampApproved;


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

    public int getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(int itemVersion) {
        this.itemVersion = itemVersion;
    }

    public Timestamp getTimestampApproved() {
        return timestampApproved;
    }

    public void setTimestampApproved(Timestamp timestampApproved) {
        this.timestampApproved = timestampApproved;
    }

}
