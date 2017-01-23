package org.nearbyshops.DAOPreparedPickFromShop;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.OrderItem;
import org.nearbyshops.ModelDelivery.DeliveryAddress;
import org.nearbyshops.ModelEndPoints.OrderEndPoint;
import org.nearbyshops.ModelOrderStatus.OrderStatusHomeDelivery;
import org.nearbyshops.ModelPickFromShop.*;
import org.nearbyshops.ModelStats.OrderStats;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sumeet on 7/6/16.
 */
public class OrderServicePFS {


    private HikariDataSource dataSource = Globals.getDataSource();

//    private ShopDAO shopDAO = Globals.shopDAO;
//    private ShopItemDAO shopItemDAO = Globals.shopItemDAO;


    public OrderPFS readOrder(int orderID)
    {

        String query = "SELECT "

                 + OrderPFS.ORDER_ID_PFS + ","
                 + OrderPFS.END_USER_ID + ","
                 + OrderPFS.SHOP_ID + ","

                + OrderPFS.ORDER_TOTAL + ","
                + OrderPFS.ITEM_COUNT + ","
                + OrderPFS.APP_SERVICE_CHARGE + ","

                + OrderPFS.DELIVERY_ADDRESS_ID + ","

                + OrderPFS.STATUS_PICK_FROM_SHOP + ","

                + OrderPFS.DELIVERY_RECEIVED + ","
                + OrderPFS.PAYMENT_RECEIVED + ","

                + OrderPFS.REASON_FOR_CANCELLED_BY_SHOP + ","
                + OrderPFS.REASON_FOR_CANCELLED_BY_USER + ","

                + OrderPFS.TIMESTAMP_PLACED + ","
                + OrderPFS.TIMESTAMP_PFS_CONFIRMED + ","
                + OrderPFS.TIMESTAMP_PFS_PACKED + ","
                + OrderPFS.TIMESTAMP_PFS_READY_FOR_PICKUP + ","
                + OrderPFS.TIMESTAMP_PFS_DELIVERED + ""

                + " FROM " + Order.TABLE_NAME
                + " WHERE " + Order.ORDER_ID + " = " + orderID;

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        OrderPFS order = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while(rs.next())
            {
                order = new OrderPFS();

                order.setOrderID(rs.getInt(OrderPFS.ORDER_ID_PFS));
                order.setEndUserID(rs.getInt(OrderPFS.END_USER_ID));
                order.setShopID(rs.getInt(OrderPFS.SHOP_ID));

                order.setOrderTotal(rs.getInt(OrderPFS.ORDER_TOTAL));
                order.setItemCount(rs.getInt(OrderPFS.ITEM_COUNT));
                order.setAppServiceCharge(rs.getInt(OrderPFS.APP_SERVICE_CHARGE));

                order.setDeliveryAddressID(rs.getInt(OrderPFS.DELIVERY_ADDRESS_ID));

                order.setStatusPickFromShop(rs.getInt(OrderPFS.STATUS_PICK_FROM_SHOP));

                order.setDeliveryReceived(rs.getBoolean(OrderPFS.DELIVERY_RECEIVED));
                order.setPaymentReceived(rs.getBoolean(OrderPFS.PAYMENT_RECEIVED));

                order.setReasonCancelledByShop(rs.getString(OrderPFS.REASON_FOR_CANCELLED_BY_SHOP));
                order.setReasonCancelledByUser(rs.getString(OrderPFS.REASON_FOR_CANCELLED_BY_USER));

                order.setTimestampPlaced(rs.getTimestamp(OrderPFS.TIMESTAMP_PLACED));
                order.setTimestampConfirmed(rs.getTimestamp(OrderPFS.TIMESTAMP_PFS_CONFIRMED));
                order.setTimestampPacked(rs.getTimestamp(OrderPFS.TIMESTAMP_PFS_PACKED));
                order.setTimestampReadyToPickup(rs.getTimestamp(OrderPFS.TIMESTAMP_PFS_READY_FOR_PICKUP));
                order.setTimestampDelivered(rs.getTimestamp(OrderPFS.TIMESTAMP_PFS_DELIVERED));
            }


            //System.out.println("Total itemCategories queried " + itemCategoryList.size());



        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

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

        return order;
    }





    //    Boolean getDeliveryAddress,
//    Boolean getStats
    public ArrayList<OrderPFS> readOrders(Integer orderID, Integer endUserID, Integer shopID,
                                          Integer statusPickFromShop,
                                          Boolean paymentsReceived,
                                          Boolean deliveryReceived,
                                          Double latCenter, Double lonCenter,
                                          Boolean pendingOrders,
                                          String searchString,
                                          String sortBy,
                                          Integer limit, Integer offset)
    {


        String query = "SELECT "

                + "6371 * acos( cos( radians("
                + latCenter + ")) * cos( radians( " + DeliveryAddress.LATITUDE + ") ) * cos(radians( " + DeliveryAddress.LONGITUDE + " ) - radians("
                + lonCenter + "))"
                + " + sin( radians(" + latCenter + ")) * sin(radians( " + DeliveryAddress.LATITUDE + " ))) as distance" + ","


                + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + ","
                + OrderPFS.TABLE_NAME + "." + OrderPFS.END_USER_ID + ","
                + OrderPFS.TABLE_NAME + "." + OrderPFS.SHOP_ID + ","

                + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_TOTAL + ","
                + OrderPFS.TABLE_NAME + "." + OrderPFS.ITEM_COUNT + ","
                + OrderPFS.TABLE_NAME + "." + OrderPFS.APP_SERVICE_CHARGE + ","

                + OrderPFS.TABLE_NAME + "." + OrderPFS.DELIVERY_ADDRESS_ID + ","

                + OrderPFS.TABLE_NAME + "." + OrderPFS.STATUS_PICK_FROM_SHOP + ","

                + OrderPFS.TABLE_NAME + "." + OrderPFS.DELIVERY_RECEIVED + ","
                + OrderPFS.TABLE_NAME + "." + OrderPFS.PAYMENT_RECEIVED + ","

                + OrderPFS.TABLE_NAME + "." + OrderPFS.REASON_FOR_CANCELLED_BY_SHOP + ","
                + OrderPFS.TABLE_NAME + "." + OrderPFS.REASON_FOR_CANCELLED_BY_USER + ","

                + OrderPFS.TABLE_NAME + "." + OrderPFS.TIMESTAMP_PLACED + ","
                + OrderPFS.TABLE_NAME + "." + OrderPFS.TIMESTAMP_PFS_CONFIRMED + ","
                + OrderPFS.TABLE_NAME + "." + OrderPFS.TIMESTAMP_PFS_PACKED + ","
                + OrderPFS.TABLE_NAME + "." + OrderPFS.TIMESTAMP_PFS_READY_FOR_PICKUP + ","
                + OrderPFS.TABLE_NAME + "." + OrderPFS.TIMESTAMP_PFS_DELIVERED + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.END_USER_ID + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.CITY + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.DELIVERY_ADDRESS + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LANDMARK + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.NAME + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LATITUDE + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LONGITUDE + ","

                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.PHONE_NUMBER + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.PINCODE + ","

//                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LATITUDE + ","
//                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.LONGITUDE + ","

                + " count( " + OrderItemPFS.ITEM_ID + " ) as item_count, "
                + " sum( " + OrderItemPFS.ITEM_PRICE_AT_ORDER + " * " + OrderItemPFS.ITEM_QUANTITY + ") as item_total "
                + " FROM " + OrderPFS.TABLE_NAME
                + " LEFT OUTER JOIN " + OrderItemPFS.TABLE_NAME + " ON (" + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + " = " + OrderItemPFS.TABLE_NAME + "." + OrderItemPFS.ORDER_ID + " ) "
                + " LEFT OUTER JOIN " + DeliveryAddress.TABLE_NAME + " ON (" + OrderPFS.TABLE_NAME + "." + OrderPFS.DELIVERY_ADDRESS_ID + " = " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID + ")";



        boolean isFirst = true;

        if(endUserID !=null)
        {
            query = query + " WHERE " + OrderPFS.TABLE_NAME + "." + OrderPFS.END_USER_ID + " = " + endUserID;

            isFirst = false;
        }

        if(shopID != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + OrderPFS.SHOP_ID + " = " + shopID;

                isFirst = false;

            }else
            {
                query = query + " AND " + OrderPFS.SHOP_ID + " = " + shopID;
            }

        }


        if(searchString != null)
        {

//            String queryPart = ;

            String queryPartSearch = " ( " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.NAME +" ilike '%" + searchString + "%'"
                    + " or CAST ( " + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + " AS text )" + " ilike '%" + searchString + "%'" + ") ";

            //+ " or " + Item.TABLE_NAME + "." + Item.ITEM_NAME + " ilike '%" + searchString + "%'"

            if(isFirst)
            {
                query = query + " WHERE " + queryPartSearch;

                isFirst = false;

            }else
            {
                query = query + " AND " + queryPartSearch;
            }

        }




        if(pendingOrders!=null)
        {
            String queryPartPending = "";


            if(pendingOrders)
            {
                queryPartPending =
                        "( " + "(" + OrderPFS.STATUS_PICK_FROM_SHOP + " <= " + OrderStatusPickFromShop.ORDER_PACKED + ")"
                                + " OR "
                                + "((" + OrderPFS.STATUS_PICK_FROM_SHOP + " = " + OrderStatusPickFromShop.READY_FOR_PICKUP + ")"
                                + " AND " + "((" + OrderPFS.PAYMENT_RECEIVED + " = " + " FALSE ) OR " + " (" + OrderPFS.DELIVERY_RECEIVED + " = " + " FALSE ))" + " )" + " )";

            }
            else
            {
                queryPartPending =
                        "( " + "((" + OrderPFS.STATUS_PICK_FROM_SHOP + " = " + OrderStatusPickFromShop.READY_FOR_PICKUP + ")"
                                + " AND " + "((" + OrderPFS.PAYMENT_RECEIVED + " = " + " TRUE ) AND " + " (" + OrderPFS.DELIVERY_RECEIVED + " = " + " TRUE ))" + " )" + " )";

            }


            if(isFirst)
            {
                query = query + " WHERE " + queryPartPending;

                isFirst = false;

            }else
            {
                query = query + " AND " + queryPartPending;
            }
        }




        if(orderID!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + " = " + orderID;

                isFirst = false;

            }else
            {
                query = query + " AND " + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + " = " + orderID;

            }
        }



        if(statusPickFromShop != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + OrderPFS.STATUS_PICK_FROM_SHOP + " = " + statusPickFromShop;

                isFirst = false;

            }else
            {
                query = query + " AND " + OrderPFS.STATUS_PICK_FROM_SHOP + " = " + statusPickFromShop;

            }
        }



        if(paymentsReceived!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + OrderPFS.PAYMENT_RECEIVED + " = " + paymentsReceived;

                isFirst = false;

            }else
            {
                query = query + " AND " + OrderPFS.PAYMENT_RECEIVED + " = " + paymentsReceived;
            }

        }



        if(deliveryReceived!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + OrderPFS.DELIVERY_RECEIVED + " = " + deliveryReceived;

                isFirst = false;

            }else
            {
                query = query + " AND " + OrderPFS.DELIVERY_RECEIVED + " = " + deliveryReceived;
            }

        }







        // all the non-aggregate columns which are present in select must be present in group by also.
        query = query
                + " group by "
                + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + ","
                + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID ;



        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

                query = query + queryPartSortBy;
            }
        }



        if(limit != null)
        {

            String queryPartLimitOffset = "";

            if(offset!=null)
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

            }else
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
            }

            query = query + queryPartLimitOffset;
        }




        ArrayList<OrderPFS> ordersList = new ArrayList<OrderPFS>();

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();


            rs = statement.executeQuery(query);

            while(rs.next())
            {
                OrderPFS order = new OrderPFS();


                order.setOrderID(rs.getInt(OrderPFS.ORDER_ID_PFS));
                order.setEndUserID(rs.getInt(OrderPFS.END_USER_ID));
                order.setShopID(rs.getInt(OrderPFS.SHOP_ID));

                order.setOrderTotal(rs.getInt(OrderPFS.ORDER_TOTAL));
                order.setItemCount(rs.getInt(OrderPFS.ITEM_COUNT));
                order.setAppServiceCharge(rs.getInt(OrderPFS.APP_SERVICE_CHARGE));

                order.setDeliveryAddressID(rs.getInt(OrderPFS.DELIVERY_ADDRESS_ID));

                order.setStatusPickFromShop(rs.getInt(OrderPFS.STATUS_PICK_FROM_SHOP));

                order.setDeliveryReceived(rs.getBoolean(OrderPFS.DELIVERY_RECEIVED));
                order.setPaymentReceived(rs.getBoolean(OrderPFS.PAYMENT_RECEIVED));

                order.setReasonCancelledByShop(rs.getString(OrderPFS.REASON_FOR_CANCELLED_BY_SHOP));
                order.setReasonCancelledByUser(rs.getString(OrderPFS.REASON_FOR_CANCELLED_BY_USER));

                order.setTimestampPlaced(rs.getTimestamp(OrderPFS.TIMESTAMP_PLACED));
                order.setTimestampConfirmed(rs.getTimestamp(OrderPFS.TIMESTAMP_PFS_CONFIRMED));
                order.setTimestampPacked(rs.getTimestamp(OrderPFS.TIMESTAMP_PFS_PACKED));
                order.setTimestampReadyToPickup(rs.getTimestamp(OrderPFS.TIMESTAMP_PFS_READY_FOR_PICKUP));
                order.setTimestampDelivered(rs.getTimestamp(OrderPFS.TIMESTAMP_PFS_DELIVERED));


                DeliveryAddress address = new DeliveryAddress();

                address.setEndUserID(rs.getInt(DeliveryAddress.END_USER_ID));
                address.setCity(rs.getString(DeliveryAddress.CITY));
                address.setDeliveryAddress(rs.getString(DeliveryAddress.DELIVERY_ADDRESS));

                address.setId(rs.getInt(DeliveryAddress.ID));
                address.setLandmark(rs.getString(DeliveryAddress.LANDMARK));
                address.setName(rs.getString(DeliveryAddress.NAME));

                address.setPhoneNumber(rs.getLong(DeliveryAddress.PHONE_NUMBER));
                address.setPincode(rs.getLong(DeliveryAddress.PINCODE));


                address.setLatitude(rs.getDouble(DeliveryAddress.LATITUDE));
                address.setLongitude(rs.getDouble(DeliveryAddress.LONGITUDE));
                address.setRt_distance(rs.getDouble("distance"));

                order.setDeliveryAddress(address);


                OrderStatsPFS orderStats = new OrderStatsPFS();
                orderStats.setOrderID(rs.getInt("order_id"));
                orderStats.setItemCount(rs.getInt("item_count"));
                orderStats.setItemTotal(rs.getInt("item_total"));
                order.setOrderStats(orderStats);


                ordersList.add(order);
            }



        } catch (SQLException e) {
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


        return ordersList;
    }






    public OrderEndPointPFS endPointMetaDataOrders(Integer orderID, Integer endUserID, Integer shopID,
                                                Integer statusPickFromShop,
                                                Boolean paymentsReceived,
                                                Boolean deliveryReceived,
                                                Boolean pendingOrders,
                                                String searchString)
    {

        String query = "SELECT " +
                        "count( DISTINCT " + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + ") as item_count" +
                        " FROM " + OrderPFS.TABLE_NAME
                        + " LEFT OUTER JOIN " + OrderItemPFS.TABLE_NAME + " ON (" + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + " = " + OrderItemPFS.TABLE_NAME + "." + OrderItemPFS.ORDER_ID + " ) "
                        + " LEFT OUTER JOIN " + DeliveryAddress.TABLE_NAME + " ON (" + OrderPFS.TABLE_NAME + "." + OrderPFS.DELIVERY_ADDRESS_ID + " = " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.ID + ")";



/*        if(getDeliveryAddress!=null && getDeliveryAddress)
        {

            String addressJoin = " INNER JOIN "
                    + DeliveryAddress.TABLE_NAME
                    + " ON (" + OrderPFS.TABLE_NAME + "." + OrderPFS.DELIVERY_ADDRESS_ID
                    + " = " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.DELIVERY_GUY_SELF_ID + ")";

            query = query + addressJoin;
        }*/


        boolean isFirst = true;

        if(endUserID !=null)
        {
            query = query + " WHERE " + OrderPFS.TABLE_NAME + "." + OrderPFS.END_USER_ID + " = " + endUserID;

            isFirst = false;
        }

        if(shopID != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + OrderPFS.SHOP_ID + " = " + shopID;

                isFirst = false;

            }else
            {
                query = query + " AND " + OrderPFS.SHOP_ID + " = " + shopID;

            }

        }



        if(searchString != null)
        {

//            String queryPart = ;

            String queryPartSearch = " ( " + DeliveryAddress.TABLE_NAME + "." + DeliveryAddress.NAME +" ilike '%" + searchString + "%'"
                    + " or CAST ( " + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + " AS text )" + " ilike '%" + searchString + "%'" + ") ";

            //+ " or " + Item.TABLE_NAME + "." + Item.ITEM_NAME + " ilike '%" + searchString + "%'"

            if(isFirst)
            {
                query = query + " WHERE " + queryPartSearch;

                isFirst = false;

            }else
            {
                query = query + " AND " + queryPartSearch;
            }

        }



        if(pendingOrders!=null)
        {
            String queryPartPending = "";


            if(pendingOrders)
            {
                queryPartPending =
                        "( " + "(" + OrderPFS.STATUS_PICK_FROM_SHOP + " <= " + OrderStatusPickFromShop.ORDER_PACKED + ")"
                                + " OR "
                                + "((" + OrderPFS.STATUS_PICK_FROM_SHOP + " = " + OrderStatusPickFromShop.READY_FOR_PICKUP + ")"
                                + " AND " + "((" + OrderPFS.PAYMENT_RECEIVED + " = " + " FALSE ) OR " + " (" + OrderPFS.DELIVERY_RECEIVED + " = " + " FALSE ))" + " )" + " )";

            }
            else
            {
                queryPartPending =
                        "( " + "((" + OrderPFS.STATUS_PICK_FROM_SHOP + " = " + OrderStatusPickFromShop.READY_FOR_PICKUP + ")"
                                + " AND " + "((" + OrderPFS.PAYMENT_RECEIVED + " = " + " TRUE ) AND " + " (" + OrderPFS.DELIVERY_RECEIVED + " = " + " TRUE ))" + " )" + " )";

            }


            if(isFirst)
            {
                query = query + " WHERE " + queryPartPending;

                isFirst = false;

            }else
            {
                query = query + " AND " + queryPartPending;
            }
        }


        if(orderID!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + " = " + orderID;

                isFirst = false;

            }else
            {
                query = query + " AND " + OrderPFS.TABLE_NAME + "." + OrderPFS.ORDER_ID_PFS + " = " + orderID;

            }
        }


        if(statusPickFromShop != null)
        {
            if(isFirst)
            {
                query = query + " WHERE " + OrderPFS.STATUS_PICK_FROM_SHOP + " = " + statusPickFromShop;

                isFirst = false;

            }else
            {
                query = query + " AND " + OrderPFS.STATUS_PICK_FROM_SHOP + " = " + statusPickFromShop;
            }
        }



        if(paymentsReceived!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + OrderPFS.PAYMENT_RECEIVED + " = " + paymentsReceived;

                isFirst = false;

            }else
            {
                query = query + " AND " + OrderPFS.PAYMENT_RECEIVED + " = " + paymentsReceived;
            }

        }



        if(deliveryReceived!=null)
        {

            if(isFirst)
            {
                query = query + " WHERE " + OrderPFS.DELIVERY_RECEIVED + " = " + deliveryReceived;

                isFirst = false;

            }else
            {
                query = query + " AND " + OrderPFS.DELIVERY_RECEIVED + " = " + deliveryReceived;
            }

        }



        OrderEndPointPFS endPoint = new OrderEndPointPFS();


        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);


            while(rs.next())
            {
                endPoint.setItemCount(rs.getInt("item_count"));
            }

            System.out.println("Item Count : " + endPoint.getItemCount());



        } catch (SQLException e) {
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



    public int updateOrder(OrderPFS order)
    {
        String updateStatement = "UPDATE " + OrderPFS.TABLE_NAME

                + " SET "

                + OrderPFS.END_USER_ID + "=?,"
                + OrderPFS.SHOP_ID + "=?,"

                + OrderPFS.ORDER_TOTAL + "=?,"
                + OrderPFS.ITEM_COUNT + "=?,"
                + OrderPFS.APP_SERVICE_CHARGE + "=?,"

                + OrderPFS.DELIVERY_ADDRESS_ID + "=?,"

                + OrderPFS.STATUS_PICK_FROM_SHOP + "=?,"

                + OrderPFS.DELIVERY_RECEIVED + "=?,"
                + OrderPFS.PAYMENT_RECEIVED + "=?,"

                + OrderPFS.REASON_FOR_CANCELLED_BY_SHOP + "=?,"
                + OrderPFS.REASON_FOR_CANCELLED_BY_USER + "=?,"

//                + OrderPFS.TIMESTAMP_PLACED + "=?,"
                + OrderPFS.TIMESTAMP_PFS_CONFIRMED + "=?,"
                + OrderPFS.TIMESTAMP_PFS_PACKED + "=?,"
                + OrderPFS.TIMESTAMP_PFS_READY_FOR_PICKUP + "=?,"
                + OrderPFS.TIMESTAMP_PFS_DELIVERED + "=?"

                + " WHERE " + OrderPFS.ORDER_ID_PFS + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);


            int i = 0;
            statement.setObject(++i,order.getEndUserID());
            statement.setObject(++i,order.getShopID());

            statement.setObject(++i,order.getOrderTotal());
            statement.setObject(++i,order.getItemCount());
            statement.setObject(++i,order.getAppServiceCharge());

            statement.setObject(++i,order.getDeliveryAddressID());

            statement.setObject(++i,order.getStatusPickFromShop());

            statement.setObject(++i,order.getDeliveryReceived());
            statement.setObject(++i,order.getPaymentReceived());

            statement.setString(++i,order.getReasonCancelledByShop());
            statement.setString(++i,order.getReasonCancelledByUser());

            statement.setTimestamp(++i,order.getTimestampConfirmed());
            statement.setTimestamp(++i,order.getTimestampPacked());
            statement.setTimestamp(++i,order.getTimestampReadyToPickup());
            statement.setTimestamp(++i,order.getTimestampDelivered());

            statement.setObject(++i,order.getOrderID());


            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }



    public OrderPFS readStatusPickFromShop(int orderID)
    {

        String query = "SELECT "

//                + OrderPFS.ORDER_ID + ","
//                + OrderPFS.DELIVERY_ADDRESS_ID + ","
//                + OrderPFS.DATE_TIME_PLACED + ","

//                + OrderPFS.DELIVERY_CHARGES + ","
                + OrderPFS.DELIVERY_RECEIVED + ","
//                + OrderPFS.PAYMENT_RECEIVED + ","

//                + OrderPFS.END_USER_ID + ","
//                + OrderPFS.PICK_FROM_SHOP + ","

                + OrderPFS.SHOP_ID + ","
                + OrderPFS.END_USER_ID + ","
                + OrderPFS.STATUS_PICK_FROM_SHOP + ""
//                + OrderPFS.STATUS_PICK_FROM_SHOP + ""

                + " FROM " + OrderPFS.TABLE_NAME
                + " WHERE " + OrderPFS.ORDER_ID_PFS + " = " + orderID;

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        OrderPFS order = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while(rs.next())
            {
                order = new OrderPFS();

                order.setOrderID(orderID);
                order.setDeliveryReceived(rs.getBoolean(OrderPFS.DELIVERY_RECEIVED));
                order.setShopID(rs.getInt(OrderPFS.SHOP_ID));
                order.setStatusPickFromShop(rs.getInt(OrderPFS.STATUS_PICK_FROM_SHOP));
                order.setEndUserID(rs.getInt(OrderPFS.END_USER_ID));
            }


            //System.out.println("Total itemCategories queried " + itemCategoryList.size());



        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally

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

        return order;
    }




    public int updateDeliveryReceived(OrderPFS order)
    {
        String updateStatement = "UPDATE " + OrderPFS.TABLE_NAME

                + " SET "
                + " " + OrderPFS.DELIVERY_RECEIVED + " = ?"
                + " WHERE " + OrderPFS.ORDER_ID_PFS + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;
            statement.setObject(++i,order.getDeliveryReceived());
            statement.setObject(++i,order.getOrderID());

            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }



    public int updatePaymentReceived(OrderPFS order)
    {
        String updateStatement = "UPDATE " + OrderPFS.TABLE_NAME

                + " SET "
                + " " + OrderPFS.PAYMENT_RECEIVED + " = ?"
                + " WHERE " + OrderPFS.ORDER_ID_PFS + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;
            statement.setObject(++i,order.getPaymentReceived());
            statement.setObject(++i,order.getOrderID());

            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }






    public int updateStatusPickFromShop(OrderPFS order)
    {
        String updateStatement = "UPDATE " + OrderPFS.TABLE_NAME

                + " SET "
//                + OrderPFS.END_USER_ID + " = ?,"
//                + " " + OrderPFS.SHOP_ID + " = ?,"
                + " " + OrderPFS.STATUS_PICK_FROM_SHOP + " = ?"
//                + " " + OrderPFS.STATUS_PICK_FROM_SHOP + " = ?"
//                + " " + OrderPFS.PAYMENT_RECEIVED + " = ?,"
//                + " " + OrderPFS.DELIVERY_RECEIVED + " = ?"
//                + " " + OrderPFS.DELIVERY_CHARGES + " = ?,"
//                + " " + OrderPFS.DELIVERY_ADDRESS_ID + " = ?,"
//                + OrderPFS.DELIVERY_GUY_SELF_ID + " = ?"
//                + OrderPFS.PICK_FROM_SHOP + " = ?"
                + " WHERE " + OrderPFS.ORDER_ID_PFS + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);

            int i = 0;

//            statement.setObject(1,order.getEndUserID());
//            statement.setObject(1,order.getShopID());
            statement.setObject(++i,order.getStatusPickFromShop());
//            statement.setObject(4,order.getStatusPickFromShop());
//            statement.setObject(2,order.getPaymentReceived());
//            statement.setObject(3,order.getDeliveryReceived());
//            statement.setObject(7,order.getDeliveryCharges());
//            statement.setObject(8,order.getDeliveryAddressID());
//            statement.setObject(2,order.getDeliveryGuySelfID());
//            statement.setObject(10,order.getPickFromShop());
            statement.setObject(++i,order.getOrderID());


            updatedRows = statement.executeUpdate();
            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

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

        return updatedRows;
    }





    public int orderCancelledByShop(Integer orderID)
    {
        OrderPFS order = readStatusPickFromShop(orderID);

        if(order!=null) {

            int status = order.getStatusPickFromShop();

            if (status == 1 || status == 2 || status == 3 || status == 4)
            {
                order.setStatusPickFromShop(OrderStatusPickFromShop.CANCELLED_BY_SHOP);
            }
            else
            {
                return 0;
            }

            return updateStatusPickFromShop(order);
        }

        return 0;
    }






    public int orderCancelledByEndUser(Integer orderID)
    {
        OrderPFS order = readStatusPickFromShop(orderID);

        if(order!=null) {

            int status = order.getStatusPickFromShop();

            if (status == 1 || status == 2 || status == 3)
            {
                order.setStatusPickFromShop(OrderStatusPickFromShop.CANCELLED_BY_USER);
            }
            else
            {
                return 0;
            }

            return updateStatusPickFromShop(order);
        }

        return 0;
    }

}
