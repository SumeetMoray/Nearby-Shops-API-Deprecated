package org.nearbyshops.DAOItemSubmissions;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelEndPoints.ItemEndPoint;
import org.nearbyshops.ModelItemSubmission.Deprecated.ItemSubmissionDetails;
import org.nearbyshops.ModelItemSubmission.Deprecated.ShopAdminItemSubmission;
import org.nearbyshops.ModelItemSubmission.Endpoints.ItemSubmissionEndPoint;
import org.nearbyshops.ModelItemSubmission.ItemSubmission;
import org.nearbyshops.ModelStats.ItemStats;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sumeet on 19/3/17.
 */
public class DAOItemSubmissionNew {

    private HikariDataSource dataSource = Globals.getDataSource();


    public DAOItemSubmissionNew() {
    }



    // insert item Submission
    // update item Submission

    // approveInsert
    // approveUpdate

    // delete Item Submission

    // getItemSubmissions
    // getItemsForItemSubmission


    public int updateItemSubmission(ItemSubmission itemSubmission)
    {
        Item item = itemSubmission.getItem();

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

            statement.setObject(++i,itemSubmission.getItemSubmissionID());



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





    public int insertItemSubmission(ItemSubmission itemSubmission, boolean getRowCount)
    {

        Connection connection = null;
        PreparedStatement statementSubmission = null;

        Item item = itemSubmission.getItem();
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
                + ItemSubmission.GIDB_SERVICE_URL + ","

                + ItemSubmission.ITEM_ID + ","
                + ItemSubmission.STATUS + ","
                + ItemSubmission.USER_ID + ""

                + ") VALUES(?,? ,?,?, ?,?,?, ?,?,?,?, ?,? ,?,?,?)";



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


            statementSubmission.setObject(++i,itemSubmission.getItemID());
            statementSubmission.setObject(++i,itemSubmission.getStatus());
            statementSubmission.setObject(++i,itemSubmission.getUserID());



            rowCountItems = statementSubmission.executeUpdate();

            ResultSet rs = statementSubmission.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }



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







    public int approveInsert(int itemSubmissionID)
    {
        Connection connection = null;

        PreparedStatement statementOne = null;
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
                        + Item.GIDB_SERVICE_URL + ""

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
                        + ItemSubmission.GIDB_SERVICE_URL + ""

                        + " FROM " + ItemSubmission.TABLE_NAME
                        + " WHERE " + ItemSubmission.ITEM_SUBMISSION_ID + " =?";



        String update =
                        " UPDATE " + ItemSubmission.TABLE_NAME
                        + " SET "
                        + ItemSubmission.ITEM_ID + "=?,"
                        + ItemSubmission.TIMESTAMP_APPROVED + "=?,"
                        + ItemSubmission.STATUS + "=?"
                        + " WHERE "
                        + ItemSubmission.ITEM_SUBMISSION_ID + "=?";



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementOne = connection.prepareStatement(insertItemSubmission,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementOne.setInt(++i,itemSubmissionID);
            rowCountItems = statementOne.executeUpdate();

            ResultSet rs = statementOne.getGeneratedKeys();

            if(rs.next())
            {
                idOfInsertedRow = rs.getInt(1);
            }


            statementTwo = connection.prepareStatement(update,Statement.RETURN_GENERATED_KEYS);

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

            if (statementOne != null) {
                try {
                    statementOne.close();
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


        return rowCountItems;
    }





    public int approveUpdate(int itemSubmissionID)
    {
        Connection connection = null;

        PreparedStatement statementOne = null;
        PreparedStatement statementTwo = null;

        int idOfInsertedRow = -1;
        int rowCountItems = -1;



//        String insertItemSubmission =
//
//                "UPDATE " + Item.TABLE_NAME
//                        + " SET ("
//
//                        + Item.ITEM_NAME + ","
//                        + Item.ITEM_NAME + ","
//                        + Item.ITEM_DESC + ","
//
//                        + Item.ITEM_IMAGE_URL + ","
//                        + Item.ITEM_CATEGORY_ID + ","
//
//                        + Item.QUANTITY_UNIT + ","
//                        + Item.ITEM_DESCRIPTION_LONG + ","
//
//                        + Item.LIST_PRICE + ","
//                        + Item.BARCODE + ","
//                        + Item.BARCODE_FORMAT + ","
//                        + Item.IMAGE_COPYRIGHTS + ","
//
//                        + Item.GIDB_ITEM_ID + ","
//                        + Item.GIDB_SERVICE_URL + ""
//
//                        + ") = "
//                        + "( SELECT "
//                        + ItemSubmission.ITEM_NAME + ","
//                        + ItemSubmission.ITEM_DESC + ","
//
//                        + ItemSubmission.ITEM_IMAGE_URL + ","
//                        + ItemSubmission.ITEM_CATEGORY_ID + ","
//
//                        + ItemSubmission.QUANTITY_UNIT + ","
//                        + ItemSubmission.ITEM_DESCRIPTION_LONG + ","
//
//                        + ItemSubmission.LIST_PRICE + ","
//                        + ItemSubmission.BARCODE + ","
//                        + ItemSubmission.BARCODE_FORMAT + ","
//                        + ItemSubmission.IMAGE_COPYRIGHTS + ","
//
//                        + ItemSubmission.GIDB_ITEM_ID + ","
//                        + ItemSubmission.GIDB_SERVICE_URL + ""
//
//                        + " FROM " + ItemSubmission.TABLE_NAME
//                        + " WHERE " + ItemSubmission.ITEM_SUBMISSION_ID + " =?)" ;
//


        String insertItemSubmissionTwo =

                "UPDATE " + Item.TABLE_NAME
                        + " SET "

                        + Item.ITEM_NAME + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_NAME + ","
                        + Item.ITEM_DESC + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_DESC + ","

                        + Item.ITEM_IMAGE_URL + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_IMAGE_URL + ","
                        + Item.ITEM_CATEGORY_ID + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_CATEGORY_ID + ","

                        + Item.QUANTITY_UNIT + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.QUANTITY_UNIT + ","
                        + Item.ITEM_DESCRIPTION_LONG + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_DESCRIPTION_LONG+  ","

                        + Item.LIST_PRICE + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.LIST_PRICE +","
                        + Item.BARCODE + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.BARCODE + ","
                        + Item.BARCODE_FORMAT + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.BARCODE_FORMAT + ","
                        + Item.IMAGE_COPYRIGHTS + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.IMAGE_COPYRIGHTS + ","

                        + Item.GIDB_ITEM_ID + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.GIDB_ITEM_ID + ","
                        + Item.GIDB_SERVICE_URL + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.GIDB_SERVICE_URL

                        + " FROM " + ItemSubmission.TABLE_NAME
                        + " WHERE " + Item.TABLE_NAME + "." + Item.ITEM_ID + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_ID
                        + " AND " + ItemSubmission.ITEM_SUBMISSION_ID + " = ? ";


        String update =
                " UPDATE " + ItemSubmission.TABLE_NAME
                        + " SET "
                        + ItemSubmission.TIMESTAMP_APPROVED + "=?,"
                        + ItemSubmission.STATUS + "=?"
                        + " WHERE "
                        + ItemSubmission.ITEM_SUBMISSION_ID + "=?";



        try {

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);


            statementOne = connection.prepareStatement(insertItemSubmissionTwo,PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 0;

            statementOne.setInt(++i,itemSubmissionID);
            rowCountItems = statementOne.executeUpdate();


            statementTwo = connection.prepareStatement(update,Statement.RETURN_GENERATED_KEYS);

            int j = 0;
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

            if (statementOne != null) {
                try {
                    statementOne.close();
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


        return rowCountItems;
    }



    public int rejectItemSubmission(int itemSubmissionID)
    {

        String updateStatement = "UPDATE " + ItemSubmission.TABLE_NAME

                + " SET "

                + ItemSubmission.STATUS + "=?,"
                + ItemSubmission.TIMESTAMP_APPROVED + "=?"

                + " WHERE " + ItemSubmission.ITEM_SUBMISSION_ID + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;

        int rowCountUpdated = 0;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;
            statement.setObject(++i,3);
            statement.setTimestamp(++i,new Timestamp(System.currentTimeMillis()));
            statement.setObject(++i,itemSubmissionID);


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






    public ItemSubmissionEndPoint getSubmissions(
            Integer userID,
            Integer status,
            Boolean itemIDNULL,
            Integer itemID,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount
    ) {


        boolean isfirst = true;



        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


        String queryJoin = "SELECT DISTINCT "

                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_SUBMISSION_ID + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_ID + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.USER_ID + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.TIMESTAMP_SUBMITTED + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.TIMESTAMP_APPROVED + ","
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.STATUS + ","

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

                + " FROM " + ItemSubmission.TABLE_NAME;




        if(userID != null)
        {
            queryJoin = queryJoin
                    + " WHERE "
                    + ItemSubmission.TABLE_NAME
                    + "."
                    + ItemSubmission.USER_ID + " = ?";

            isfirst = false;
        }


        if(status !=null)
        {
            String queryPartStatus = ItemSubmission.TABLE_NAME + "." + ItemSubmission.STATUS + " = ?";

            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryPartStatus;

                isfirst = false;
            }
            else
            {
                queryJoin = queryJoin + " AND " + queryPartStatus;
            }
        }


        if(itemIDNULL!=null && itemIDNULL)
        {

            String queryPartStatus = ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_ID + " IS NULL ";

            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryPartStatus;

                isfirst = false;
            }
            else
            {
                queryJoin = queryJoin + " AND " + queryPartStatus;
            }

        }

        if(itemID !=null)
        {

            String queryPart = ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_ID + " = ? ";

            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryPart;

                isfirst = false;
            }
            else
            {
                queryJoin = queryJoin + " AND " + queryPart;
            }

        }






        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_SUBMISSION_ID ;


        queryCount = queryJoin;



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


        ItemSubmissionEndPoint endPoint = new ItemSubmissionEndPoint();
        ArrayList<ItemSubmission> itemList = new ArrayList<ItemSubmission>();
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statementCount = null;

        ResultSet rs = null;
        ResultSet resultSetCount = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(queryJoin);

            int i = 0;
            if(userID!=null)
            {
                statement.setInt(++i,userID);
            }

            if(status!=null)
            {
                statement.setInt(++i,status);
            }

            if(itemID!=null)
            {
                statement.setInt(++i,itemID);
            }


            rs = statement.executeQuery();

            while(rs.next())
            {
                ItemSubmission itemSubmission = new ItemSubmission();

                Item item = new Item();

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

                itemSubmission.setItem(item);

                itemSubmission.setItemID(rs.getInt(ItemSubmission.ITEM_ID));
                itemSubmission.setItemSubmissionID(rs.getInt(ItemSubmission.ITEM_SUBMISSION_ID));
                itemSubmission.setUserID(rs.getInt(ItemSubmission.USER_ID));
                itemSubmission.setStatus(rs.getInt(ItemSubmission.STATUS));
                itemSubmission.setTimestampApproved(rs.getTimestamp(ItemSubmission.TIMESTAMP_APPROVED));
                itemSubmission.setTimestampSubmitted(rs.getTimestamp(ItemSubmission.TIMESTAMP_SUBMITTED));


                itemList.add(itemSubmission);
            }




            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                int j = 0;
                if(userID!=null)
                {
                    statementCount.setInt(++j,userID);
                }

                if(status!=null)
                {
                    statementCount.setInt(++j,status);
                }


                if(itemID!=null)
                {
                    statementCount.setInt(++j,itemID);
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







    public ItemEndPoint getItemsUpdated(
            Integer userID,
            Integer status,
            Boolean itemIDNULL,
            String sortBy,
            Integer limit, Integer offset,
            boolean getRowCount
    ) {


        boolean isfirst = true;



        String queryCount = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


        String queryJoin = "SELECT DISTINCT "

                + Item.TABLE_NAME + "." + Item.ITEM_ID + ","
                + Item.TABLE_NAME + "." + Item.ITEM_NAME + ","
                + Item.TABLE_NAME + "." + Item.ITEM_DESC + ","

                + Item.TABLE_NAME + "." + Item.ITEM_IMAGE_URL + ","
                + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + ","

                + Item.TABLE_NAME + "." + Item.QUANTITY_UNIT + ","
                + Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + ","

                + Item.TABLE_NAME + "." + Item.TIMESTAMP_UPDATED + ","

                + Item.TABLE_NAME + "." + Item.LIST_PRICE + ","
                + Item.TABLE_NAME + "." + Item.BARCODE + ","
                + Item.TABLE_NAME + "." + Item.BARCODE_FORMAT + ","
                + Item.TABLE_NAME + "." + Item.IMAGE_COPYRIGHTS + ","

                + " max(" + ItemSubmission.TIMESTAMP_SUBMITTED + ") as time_latest , "
                + " count(" + ItemSubmission.ITEM_SUBMISSION_ID + ") as submissions_count "

                + " FROM " + Item.TABLE_NAME
                + " INNER JOIN " + ItemSubmission.TABLE_NAME + " ON (" + Item.TABLE_NAME + "." + Item.ITEM_ID + " = " + ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_ID + ")";




        if(userID != null)
        {
            queryJoin = queryJoin
                    + " WHERE "
                    + ItemSubmission.TABLE_NAME
                    + "."
                    + ItemSubmission.USER_ID + " = ?";

            isfirst = false;
        }


        if(status !=null)
        {
            String queryPartStatus = ItemSubmission.TABLE_NAME + "." + ItemSubmission.STATUS + " = ?";

            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryPartStatus;

                isfirst = false;
            }
            else
            {
                queryJoin = queryJoin + " AND " + queryPartStatus;
            }
        }


        if(itemIDNULL!=null)
        {

            String queryPartStatus = "";

            if(itemIDNULL)
            {
                queryPartStatus = ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_ID + " IS NULL ";
            }
            else
            {
                queryPartStatus = ItemSubmission.TABLE_NAME + "." + ItemSubmission.ITEM_ID + " IS NOT NULL ";
            }


            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryPartStatus;

                isfirst = false;
            }
            else
            {
                queryJoin = queryJoin + " AND " + queryPartStatus;
            }

        }





        // all the non-aggregate columns which are present in select must be present in group by also.
        queryJoin = queryJoin

                + " group by "
                + Item.TABLE_NAME + "." + Item.ITEM_ID ;


        queryCount = queryJoin;



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
            if(userID!=null)
            {
                statement.setInt(++i,userID);
            }

            if(status!=null)
            {
                statement.setInt(++i,status);
            }

            rs = statement.executeQuery();

            while(rs.next())
            {

                Item item = new Item();

                item.setItemID(rs.getInt(ItemSubmission.ITEM_ID));
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

                ItemStats itemStats = new ItemStats();

                itemStats.setSubmissionsCount(rs.getInt("submissions_count"));
                itemStats.setLatestUpdate(rs.getTimestamp("time_latest"));

                item.setItemStats(itemStats);

                itemList.add(item);
            }




            if(getRowCount)
            {
                statementCount = connection.prepareStatement(queryCount);

                int j = 0;
                if(userID!=null)
                {
                    statementCount.setInt(++j,userID);
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
