package org.nearbyshops.DAOItemSubmissions;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelEndPoints.ItemEndPoint;
import org.nearbyshops.ModelItemSubmission.ItemSubmission;
import org.nearbyshops.ModelItemSubmission.Deprecated.ItemSubmissionDetails;
import org.nearbyshops.ModelItemSubmission.Deprecated.ShopAdminItemSubmission;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sumeet on 16/3/17.
 */
public class DAOItemSubmissions {

    private HikariDataSource dataSource = Globals.getDataSource();


    public DAOItemSubmissions() {
    }


    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }




    public int submitItemByShopAdmin(Item item,int shopAdminID, boolean getRowCount)
    {

            Connection connection = null;
            PreparedStatement statementDetails = null;
            PreparedStatement statementSubmission = null;
            PreparedStatement statementRecord = null;

            int idOfInsertedRow = -1;
            int rowCountItems = -1;

            String insertItemSubmission = "INSERT INTO "
                    + ItemSubmission.TABLE_NAME
                    + "("
                    + ItemSubmission.ITEM_NAME + ","
                    + ItemSubmission.ITEM_DESC + ","

                    + ItemSubmission.ITEM_IMAGE_URL + ","
                    + ItemSubmission.ITEM_CATEGORY_ID + ","

                    + ItemSubmission.QUANTITY_UNIT + ","
                    + ItemSubmission.TIMESTAMP_UPDATED + ","
                    + ItemSubmission.ITEM_DESCRIPTION_LONG + ","

                    + ItemSubmission.LIST_PRICE + ","
                    + ItemSubmission.BARCODE + ","
                    + ItemSubmission.BARCODE_FORMAT + ","
                    + ItemSubmission.IMAGE_COPYRIGHTS + ","

                    + ItemSubmission.GIDB_ITEM_ID + ","
                    + ItemSubmission.GIDB_SERVICE_URL + ""

                    + ") VALUES(?,? ,?,?, ?,?,?, ?,?,?,?, ?,?)";




        String insertDetails = "INSERT INTO "
                + ItemSubmissionDetails.TABLE_NAME
                + "("
                + ItemSubmissionDetails.ITEM_SUBMISSION_ID + ","
                + ItemSubmissionDetails.STATUS + ""
                + ") VALUES(?,?)";




        String insertShopAdminRecord = "INSERT INTO "
                + ShopAdminItemSubmission.TABLE_NAME
                + "("
                + ShopAdminItemSubmission.ITEM_SUBMISSION_ID + ","
                + ShopAdminItemSubmission.SHOP_ADMIN_ID + ""
                + ") VALUES(?,?)";







        try {

                connection = dataSource.getConnection();
                connection.setAutoCommit(false);


                statementSubmission = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
                int i = 0;
                statementSubmission.setString(++i,item.getItemName());
                statementSubmission.setString(++i,item.getItemDescription());

                statementSubmission.setString(++i,item.getItemImageURL());
                statementSubmission.setInt(++i,item.getItemCategoryID());

                statementSubmission.setString(++i,item.getQuantityUnit());
                statementSubmission.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));
                statementSubmission.setString(++i,item.getItemDescriptionLong());

                statementSubmission.setFloat(++i,item.getListPrice());
                statementSubmission.setString(++i,item.getBarcode());
                statementSubmission.setString(++i,item.getBarcodeFormat());
                statementSubmission.setString(++i,item.getImageCopyrights());

                statementSubmission.setObject(++i,item.getGidbItemID());
                statementSubmission.setString(++i,item.getGidbServiceURL());


                rowCountItems = statementSubmission.executeUpdate();

                ResultSet rs = statementSubmission.getGeneratedKeys();

                if(rs.next())
                {
                    idOfInsertedRow = rs.getInt(1);
                }





                statementDetails = connection.prepareStatement(insertDetails, Statement.RETURN_GENERATED_KEYS);

                int j = 0;
                statementDetails.setInt(++j,idOfInsertedRow);
                statementDetails.setInt(++j,1);

                statementDetails.executeUpdate();




            statementRecord = connection.prepareStatement(insertShopAdminRecord, Statement.RETURN_GENERATED_KEYS);

            int k = 0;
            statementRecord.setInt(++k,idOfInsertedRow);
            statementRecord.setInt(++k,shopAdminID);

            statementRecord.executeUpdate();





            connection.commit();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                if (connection != null) {
                    try {

                        idOfInsertedRow=-1;
                        rowCountItems = 0;

                        connection.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            finally
            {

                if (statementSubmission != null) {
                    try {
                        statementSubmission.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                try {

                    if(statementDetails!=null)
                    {statementDetails.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(connection!=null)
                    {connection.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if(getRowCount)
            {
                return rowCountItems;
            }
            else
            {
                return idOfInsertedRow;
            }
    }







    public int approveInsertShopAdmin(boolean getRowCount, int itemSubmissionID)
    {

        Connection connection = null;

//        PreparedStatement statementUpdate = null;
        PreparedStatement statementCopyItemSubmissionToItem = null;
        PreparedStatement statementTwo = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;


        String insertItemSubmission =

                "INSERT INTO " + Item.TABLE_NAME
                + "("
                + Item.ITEM_NAME + ","
                + Item.ITEM_DESC + ","

                + Item.ITEM_IMAGE_URL + ","
                + Item.ITEM_CATEGORY_ID + ","

                + Item.QUANTITY_UNIT + ","
                + Item.TIMESTAMP_UPDATED + ","
                + Item.ITEM_DESCRIPTION_LONG + ","

                + Item.LIST_PRICE + ","
                + Item.BARCODE + ","
                + Item.BARCODE_FORMAT + ","
                + Item.IMAGE_COPYRIGHTS + ","

                + Item.GIDB_ITEM_ID + ","
                + Item.GIDB_SERVICE_URL + ","
                + Item.VERSION + " "

                + ")"
                + " SELECT "
                + ItemSubmission.ITEM_NAME + ","
                + ItemSubmission.ITEM_DESC + ","

                + ItemSubmission.ITEM_IMAGE_URL + ","
                + ItemSubmission.ITEM_CATEGORY_ID + ","

                + ItemSubmission.QUANTITY_UNIT + ","
                + ItemSubmission.TIMESTAMP_UPDATED + ","
                + ItemSubmission.ITEM_DESCRIPTION_LONG + ","

                + ItemSubmission.LIST_PRICE + ","
                + ItemSubmission.BARCODE + ","
                + ItemSubmission.BARCODE_FORMAT + ","
                + ItemSubmission.IMAGE_COPYRIGHTS + ","

                + ItemSubmission.GIDB_ITEM_ID + ","
                + ItemSubmission.GIDB_SERVICE_URL + ","
                + " 1 "

                + " FROM " + ItemSubmission.TABLE_NAME
                + " WHERE " + ItemSubmission.ITEM_SUBMISSION_ID + " =?";




//        String updateItemSubmissionReview =
//
//                " UPDATE " + ItemSubmissionDetails.TABLE_NAME
//                + " SET "
//                + ItemSubmissionDetails.ITEM_ID + "=?"
//                + " WHERE " + ItemSubmissionDetails.ITEM_SUBMISSION_ID + "=?";


//
//        String copyReviewToApproved =
//
//                "INSERT INTO " + ItemSubmissionApproved.TABLE_NAME
//                        + "("
//                        + ItemSubmissionApproved.ITEM_SUBMISSION_ID + ","
//                        + ItemSubmissionApproved.ITEM_ID + ","
//                        + ItemSubmissionApproved.ITEM_VERSION + ""
//                        + ")"
//                        + " SELECT "
//                        + ItemSubmissionDetails.ITEM_SUBMISSION_ID + ","
//                        + idOfInsertedRow + ","
//                        + ItemSubmissionDetails.ITEM_VERSION + ""
//                        + " FROM " + ItemSubmissionDetails.TABLE_NAME
//                        + " WHERE " + ItemSubmissionDetails.ITEM_SUBMISSION_ID + " =?";



//        String deleteUnderReview =
//                " DELETE FROM " + ItemSubmissionDetails.TABLE_NAME
//                + " WHERE " + ItemSubmissionDetails.ITEM_SUBMISSION_ID + "= ?";




        String updateRecords =
                " UPDATE " + ItemSubmissionDetails.TABLE_NAME
                + " SET "
                + ItemSubmissionDetails.ITEM_ID + "=?,"
                + ItemSubmissionDetails.TIMESTAMP_APPROVED + "=?,"
                + ItemSubmissionDetails.STATUS + "=?"
                + " WHERE "
                + ItemSubmissionDetails.ITEM_SUBMISSION_ID + "=?";




        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementCopyItemSubmissionToItem = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementCopyItemSubmissionToItem.setInt(++i,itemSubmissionID);
            rowCountItems = statementCopyItemSubmissionToItem.executeUpdate();


            ResultSet rs = statementCopyItemSubmissionToItem.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }


            statementTwo = connection.prepareStatement(updateRecords,Statement.RETURN_GENERATED_KEYS);

            int j = 0;
            statementTwo.setInt(++j,idOfInsertedRow);
            statementTwo.setTimestamp(++j,new Timestamp(System.currentTimeMillis()));
            statementTwo.setInt(++j,2);
            statementTwo.setInt(++j,itemSubmissionID);

            statementTwo.executeUpdate();




            connection.commit();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            if (connection != null) {
                try {
                    idOfInsertedRow=-1;
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        finally
        {

            if (statementCopyItemSubmissionToItem != null) {
                try {
                    statementCopyItemSubmissionToItem.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }





            if (statementTwo != null) {
                try {
                    statementTwo.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }




            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(getRowCount)
        {
            return rowCountItems;
        }
        else
        {
            return idOfInsertedRow;
        }

    }





    public int updateItem(Item item)
    {

        String updateStatement = "UPDATE " + ItemSubmission.TABLE_NAME

                + " SET "

                + ItemSubmission.ITEM_NAME + "=?,"
                + ItemSubmission.ITEM_DESC + "=?,"

                + ItemSubmission.ITEM_IMAGE_URL + "=?,"
                + ItemSubmission.ITEM_CATEGORY_ID + "=?,"

                + ItemSubmission.QUANTITY_UNIT + "=?,"
                + ItemSubmission.TIMESTAMP_UPDATED + "=?,"
                + ItemSubmission.ITEM_DESCRIPTION_LONG + "=?,"

                + ItemSubmission.LIST_PRICE + "=?,"
                + ItemSubmission.BARCODE + "=?,"
                + ItemSubmission.BARCODE_FORMAT + "=?,"
                + ItemSubmission.IMAGE_COPYRIGHTS + "=?,"

                + ItemSubmission.GIDB_ITEM_ID + "=?,"
                + ItemSubmission.GIDB_SERVICE_URL + "=?"

                + " WHERE " + ItemSubmission.ITEM_SUBMISSION_ID + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;
            statement.setString(++i,item.getItemName());
            statement.setString(++i,item.getItemDescription());

            statement.setString(++i,item.getItemImageURL());
            statement.setInt(++i,item.getItemCategoryID());

            statement.setString(++i,item.getQuantityUnit());
            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));
            statement.setString(++i,item.getItemDescriptionLong());

            statement.setFloat(++i,item.getListPrice());
            statement.setString(++i,item.getBarcode());
            statement.setString(++i,item.getBarcodeFormat());
            statement.setString(++i,item.getImageCopyrights());

            statement.setObject(++i,item.getGidbItemID());
            statement.setString(++i,item.getGidbServiceURL());

            statement.setObject(++i,item.getItemID());



            rowCountUpdated = statement.executeUpdate();
            System.out.println("Total rows updated: " + rowCountUpdated);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return rowCountUpdated;
    }







    // SubmitInsert
    // approveInsert

    // SubmitUpdate
    // ApproveUpdate

    // UpdateSubmission

    // RejectUpdate

    // MySubmissions for Shop Admin
    // Item Submissions for Shop Admin




    public ItemEndPoint getSubmissions(
            Integer shopAdminID,
            Integer status,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount
    ) {


        boolean isfirst = true;



        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


        String queryJoin = "SELECT DISTINCT "

                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_SUBMISSION_ID + ","

                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_NAME + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_DESC + ","

                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_IMAGE_URL + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_CATEGORY_ID + ","

                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.QUANTITY_UNIT + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_DESCRIPTION_LONG + ","

                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.TIMESTAMP_UPDATED + ","

                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.LIST_PRICE + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.BARCODE + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.BARCODE_FORMAT + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.IMAGE_COPYRIGHTS + ""

                + " FROM " + ItemSubmission.TABLE_NAME
                + " INNER JOIN " + ItemSubmissionDetails.TABLE_NAME + " ON (" + ItemSubmissionDetails.TABLE_NAME + "." + ItemSubmissionDetails.ITEM_SUBMISSION_ID + "=" + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_SUBMISSION_ID + ") ";

        if(shopAdminID!=null)
        {
            queryJoin = queryJoin
                    + " INNER JOIN " + ShopAdminItemSubmission.TABLE_NAME + " ON ( " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_SUBMISSION_ID + " = " + ShopAdminItemSubmission.TABLE_NAME + "." + ShopAdminItemSubmission.ITEM_SUBMISSION_ID + ")";
        }





        if(shopAdminID != null)
        {
            queryJoin = queryJoin
                    + " WHERE "
                    + ShopAdminItemSubmission.TABLE_NAME
                    + "."
                    + ShopAdminItemSubmission.SHOP_ADMIN_ID + " = ?";

            isfirst = false;
        }


        if(status !=null)
        {
            String queryPartStatus = ItemSubmissionDetails.TABLE_NAME + "." + ItemSubmissionDetails.STATUS + " = ?";

            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryPartStatus;
            }
            else
            {
                queryJoin = queryJoin + " AND " + queryPartStatus;
            }
        }


        queryCount = queryJoin;

        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_SUBMISSION_ID ;





        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

//				queryNormal = queryNormal + queryPartSortBy;
                queryJoin = queryJoin + queryPartSortBy;
            }
        }



        if(limit != null)
        {

            String queryPartLimitOffset = "";

            if(offset>0)
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

            }else
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
            }


//			queryNormal = queryNormal + queryPartLimitOffset;
            queryJoin = queryJoin + queryPartLimitOffset;
        }






		/*

		Applying filters Ends

		 */

        // Applying filters




        queryCount = "SELECT COUNT(*) as item_count FROM (" + queryCount + ") AS temp";


        ItemEndPoint endPoint = new ItemEndPoint();
        ArrayList<Item> itemList = new ArrayList<Item>();
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statementCount = null;

        ResultSet rs = null;
        ResultSet resultSetCount = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(queryJoin);

            int i = 0;
            if(shopAdminID!=null)
            {
                statement.setInt(++i,shopAdminID);
            }

            if(status!=null)
            {
                statement.setInt(++i,status);
            }

            rs = statement.executeQuery();

            while(rs.next())
            {
                Item item = new Item();


                item.setItemID(rs.getInt(ItemSubmission.ITEM_SUBMISSION_ID));
                item.setItemName(rs.getString(ItemSubmission.ITEM_NAME));
                item.setItemDescription(rs.getString(ItemSubmission.ITEM_DESC));

                item.setItemImageURL(rs.getString(ItemSubmission.ITEM_IMAGE_URL));
                item.setItemCategoryID(rs.getInt(ItemSubmission.ITEM_CATEGORY_ID));

                item.setItemDescriptionLong(rs.getString(ItemSubmission.ITEM_DESCRIPTION_LONG));
//				item.setDateTimeCreated(rs.getTimestamp(Item.DATE_TIME_CREATED));
                item.setQuantityUnit(rs.getString(ItemSubmission.QUANTITY_UNIT));

                item.setListPrice(rs.getFloat(ItemSubmission.LIST_PRICE));
                item.setBarcode(rs.getString(ItemSubmission.BARCODE));
                item.setBarcodeFormat(rs.getString(ItemSubmission.BARCODE_FORMAT));
                item.setImageCopyrights(rs.getString(ItemSubmission.IMAGE_COPYRIGHTS));

                item.setTimestampUpdated(rs.getTimestamp(ItemSubmission.TIMESTAMP_UPDATED));

                itemList.add(item);
            }




            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                int j = 0;
                if(shopAdminID!=null)
                {
                    statementCount.setInt(++j,shopAdminID);
                }

                if(status!=null)
                {
                    statementCount.setInt(++j,status);
                }

                resultSetCount = statementCount.executeQuery();

                while(resultSetCount.next())
                {
                    endPoint.setItemCount(resultSetCount.getInt("item_count"));
                }
            }


            endPoint.setResults(itemList);



        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return endPoint;
    }







}
