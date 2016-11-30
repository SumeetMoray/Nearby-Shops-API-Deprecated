package org.nearbyshops.ModelSubmission;

import java.sql.Timestamp;

/**
 * Created by sumeet on 25/11/16.
 */
public class ItemSubmission {

    // instance variables
    int SubmissionID;
    Timestamp timestampCreated;
    Timestamp timestampAccepted;
    int SubmittedByShopAdminID;
    int SubmittedByStaffID;
    int SubmittedByShopStaffID;
    int itemID ; // null if its an add or a value if its an edit // foreign key nulls allowed
    boolean submissionApproved;


    // Version History is suggestions sorted by timestampAccepted field
}
