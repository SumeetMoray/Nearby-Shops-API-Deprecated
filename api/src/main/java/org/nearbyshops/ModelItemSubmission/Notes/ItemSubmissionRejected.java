package org.nearbyshops.ModelItemSubmission.Notes;

import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelItemSubmission.ItemSubmission;

import java.sql.Timestamp;

/**
 * Created by sumeet on 15/3/17.
 */
public class ItemSubmissionRejected {

    // Table Name
    public static final String TABLE_NAME = "ITEM_SUBMISSIONS_REJECTED";

    // column names
    public static final String ITEM_SUBMISSION_ID = "ITEM_SUBMISSION_ID";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String ITEM_VERSION = "ITEM_VERSION";
    public static final String TIMESTAMP_REJECTED = "TIMESTAMP_REJECTED";
    public static final String REASON_FOR_REJECTION = "REASON_FOR_REJECTION";


    // Create Table Statement
    public static final String createTablePostgres = "CREATE TABLE IF NOT EXISTS "
            + ItemSubmissionRejected.TABLE_NAME + "("
            + " " + ItemSubmissionRejected.ITEM_SUBMISSION_ID + " INT NOT NULL,"
            + " " + ItemSubmissionRejected.ITEM_ID + " int,"
            + " " + ItemSubmissionRejected.ITEM_VERSION + " int,"
            + " " + ItemSubmissionRejected.TIMESTAMP_REJECTED + "  timestamp with time zone NOT NULL DEFAULT now(),"
            + " " + ItemSubmissionRejected.REASON_FOR_REJECTION + "  text,"

            + " FOREIGN KEY(" + ItemSubmissionRejected.ITEM_SUBMISSION_ID +") REFERENCES " + ItemSubmission.TABLE_NAME + "(" + ItemSubmission.ITEM_SUBMISSION_ID + "),"
            + " FOREIGN KEY(" + ItemSubmissionRejected.ITEM_ID +") REFERENCES " + Item.TABLE_NAME + "(" + Item.ITEM_ID + ")"
            + ")";




    // instance variables

    private int itemSubmissionID;
    private int itemID;
    private int itemVersion;
    private Timestamp timestampRejected;
    private String reasonForRejection;

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

    public Timestamp getTimestampRejected() {
        return timestampRejected;
    }

    public void setTimestampRejected(Timestamp timestampRejected) {
        this.timestampRejected = timestampRejected;
    }

    public String getReasonForRejection() {
        return reasonForRejection;
    }

    public void setReasonForRejection(String reasonForRejection) {
        this.reasonForRejection = reasonForRejection;
    }
}
