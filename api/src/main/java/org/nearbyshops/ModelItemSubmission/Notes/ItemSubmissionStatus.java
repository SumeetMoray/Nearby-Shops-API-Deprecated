package org.nearbyshops.ModelItemSubmission.Notes;

import org.glassfish.grizzly.http.util.TimeStamp;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by sumeet on 13/3/17.
 */
public class ItemSubmissionStatus {


    /*

    // ItemVersionHistory [ItemSubmissionsApproved]
    (Table which keeps Record of Submissions Not yet approved and which are approved)
        - Item Submission ID
        - Item ID
        - ItemVersion
        - timestampApproved
        Unique (Item ID, Item Version)

- TimestampApproved

    // Item Submissions Details (Table which keeps details of all submissions)
        - Item Submission ID
        - Item ID
        - TimestampReviewed
        - TimestampCreated
        - ItemVersion
        - IsApproved

        // ItemSubmissionDetails (KeepsBoth UnderReview and Approved)

        - ItemSubmissionID
        - Item ID
        - ItemVersion
        - Status
        - TimestampApproved
        - TimestampSubmitted
        Unique(ItemID, ItemVersion)


- IsApproved


        // ItemSubmissionRejected (Keeps only record of rejected Submissions)
        - ItemSubmissionID
        - Item ID
        - ItemVersion
        - TimestampRejected
        - reasonForRejection


    // Shop Admin Item Submission (Join Table to keep record of Submissions Made by Shop Admin)
    // Shop Staff Item Submission (Join Table to keep record of Submissions made by Shop Staff)
    // Item Submission (The actual Submission Made)

    */








    // instance variables

    Timestamp timestampCreated;
    Timestamp timestampUpdated;



}
