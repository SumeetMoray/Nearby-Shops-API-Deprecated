package org.nearbyshops.Templates.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelEndPoints.ShopEndPoint;
import org.nearbyshops.ModelReviewShop.ShopReview;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by sumeet on 13/12/16.
 */
public class CountOver {

    private HikariDataSource dataSource = Globals.getDataSource();


    public ShopEndPoint getShopsListQuerySimple(
            Boolean enabled, Boolean waitlisted,
            Double latCenter, Double lonCenter,
            Double deliveryRangeMin,Double deliveryRangeMax,
            Double proximity,
            String searchString,
            String sortBy,
            Integer limit, Integer offset
    )
    {

        String query = "";
        String queryJoin = "";

        // flag for tracking whether to put "AND" or "WHERE"
        boolean isFirst = true;


        String queryNormal = "SELECT "
                + "6371 * acos( cos( radians("
                + latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
                + lonCenter + "))"
                + " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) as distance" + ","

                +  "avg(" + ShopReview.TABLE_NAME + "." + ShopReview.RATING + ") as avg_rating" + ","
                +  "count( DISTINCT " + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + ") as rating_count" + ","

                + "count(*) over() AS full_count " + ","
                + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ","
                + Shop.SHOP_NAME + ","

                + Shop.DELIVERY_RANGE + ","
                + Shop.TABLE_NAME + "." + Shop.LAT_CENTER + ","
                + Shop.TABLE_NAME + "." + Shop.LON_CENTER + ","

                + Shop.DELIVERY_CHARGES + ","
                + Shop.BILL_AMOUNT_FOR_FREE_DELIVERY + ","
                + Shop.PICK_FROM_SHOP_AVAILABLE + ","
                + Shop.HOME_DELIVERY_AVAILABLE + ","

                + Shop.SHOP_ENABLED + ","
                + Shop.SHOP_WAITLISTED + ","

                + Shop.LOGO_IMAGE_PATH + ","

                + Shop.SHOP_ADDRESS + ","
                + Shop.CITY + ","
                + Shop.PINCODE + ","
                + Shop.LANDMARK + ","

                + Shop.CUSTOMER_HELPLINE_NUMBER + ","
                + Shop.DELIVERY_HELPLINE_NUMBER + ","

                + Shop.SHORT_DESCRIPTION + ","
                + Shop.LONG_DESCRIPTION + ","

                + Shop.TIMESTAMP_CREATED + ","
                + Shop.IS_OPEN + ""

//				+  "avg(" + ShopReview.TABLE_NAME + "." + ShopReview.RATING + ") as avg_rating" + ","
//				+  "count( DISTINCT " + ShopReview.TABLE_NAME + "." + ShopReview.END_USER_ID + ") as rating_count" + ""

                + " FROM " + Shop.TABLE_NAME
                + " LEFT OUTER JOIN " + ShopReview.TABLE_NAME
                + " ON (" + ShopReview.TABLE_NAME + "." + ShopReview.SHOP_ID + " = " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ")";



        // Visibility Filter : Apply
        if(latCenter != null && lonCenter != null)
        {

            String queryPartlatLonCenter = "";

            queryNormal = queryNormal + " WHERE ";

            // reset the flag
            isFirst = false;

            queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
                    + latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
                    + lonCenter + "))"
                    + " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";



            queryNormal = queryNormal + queryPartlatLonCenter;

        }



        // Delivery Range Filter : apply
        if(deliveryRangeMin != null || deliveryRangeMax != null){

            // apply delivery range filter
            String queryPartDeliveryRange = "";

            queryPartDeliveryRange = queryPartDeliveryRange
                    + Shop.TABLE_NAME + "." + Shop.DELIVERY_RANGE + " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;

            if(isFirst)
            {
                queryNormal = queryNormal + " WHERE " + queryPartDeliveryRange;

                // reset the flag
                isFirst = false;

            }else
            {
                queryNormal = queryNormal + " AND " + queryPartDeliveryRange;
            }

        }


        // Proximity Filter
        if(proximity != null)
        {
            // proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))

            String queryPartProximity = "";
//			String queryPartProximityTwo = "";


            // filter using Haversine formula using SQL math functions
            queryPartProximity = queryPartProximity
                    + " (6371.01 * acos(cos( radians(" + latCenter + ")) * cos( radians(" + Shop.LAT_CENTER + " )) * cos(radians( "
                    + Shop.LON_CENTER + ") - radians(" + lonCenter + "))" + " + sin( radians(" + latCenter + ")) * sin(radians("
                    + Shop.LAT_CENTER + ")))) <= " + proximity ;


            if(isFirst)
            {
                queryNormal = queryNormal + " WHERE " + queryPartProximity;

                // reset the flag
                isFirst = false;

            }else
            {
                queryNormal = queryNormal + " AND " + queryPartProximity;
            }

        }


        if(searchString !=null)
        {
            String queryPartSearch = Shop.TABLE_NAME + "." + Shop.SHOP_NAME +" ilike '%" + searchString + "%'"
                    + " or " + Shop.TABLE_NAME + "." + Shop.LONG_DESCRIPTION + " ilike '%" + searchString + "%'"
                    + " or " + Shop.TABLE_NAME + "." + Shop.SHOP_ADDRESS + " ilike '%" + searchString + "%'";



            if(isFirst)
            {
//				queryJoin = queryJoin + " WHERE " + queryPartSearch;

                queryNormal = queryNormal + " WHERE " + queryPartSearch;

                isFirst = false;
            }
            else
            {
                queryNormal = queryNormal + " AND " + queryPartSearch;
            }
        }


        if(enabled !=null)
        {

            if(isFirst)
            {
                queryNormal = queryNormal + " WHERE " + Shop.SHOP_ENABLED + " = "  + enabled;

                isFirst = false;
            }
            else
            {
                queryNormal = queryNormal + " AND " + Shop.SHOP_ENABLED + " = "  + enabled;
            }


        }


        if(waitlisted !=null)
        {
            if(isFirst)
            {
                queryNormal = queryNormal + " WHERE " + Shop.SHOP_WAITLISTED + " = "  + waitlisted;

                isFirst = false;
            }
            else
            {
                queryNormal = queryNormal + " AND " + Shop.SHOP_WAITLISTED + " = "  + waitlisted;
            }
        }




		/*


		*/

        String queryGroupBy = "";

        queryGroupBy = queryGroupBy + " group by "
                + Shop.TABLE_NAME + "." + Shop.SHOP_ID ;


        queryNormal = queryNormal + queryGroupBy;




        // Applying Filters



        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

                queryNormal = queryNormal + queryPartSortBy;
            }
        }



        if(limit !=null)
        {

            String queryPartLimitOffset = "";

            if(offset!=null)
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

            }else
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
            }


            queryNormal = queryNormal + queryPartLimitOffset;
        }



        query = queryNormal;





        ShopEndPoint endPoint = new ShopEndPoint();
        endPoint.setItemCount(0);

        ArrayList<Shop> shopList = new ArrayList<Shop>();


        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();

            rs = statement.executeQuery(query);

            while(rs.next())
            {

                Shop shop = new Shop();

                shop.setRt_distance(rs.getDouble("distance"));
                shop.setRt_rating_avg(rs.getFloat("avg_rating"));
                shop.setRt_rating_count(rs.getFloat("rating_count"));

                shop.setShopID(rs.getInt(Shop.SHOP_ID));

                shop.setShopName(rs.getString(Shop.SHOP_NAME));
                shop.setDeliveryRange(rs.getDouble(Shop.DELIVERY_RANGE));
                shop.setLatCenter(rs.getFloat(Shop.LAT_CENTER));
                shop.setLonCenter(rs.getFloat(Shop.LON_CENTER));

                shop.setDeliveryCharges(rs.getFloat(Shop.DELIVERY_CHARGES));
                shop.setBillAmountForFreeDelivery(rs.getInt(Shop.BILL_AMOUNT_FOR_FREE_DELIVERY));
                shop.setPickFromShopAvailable(rs.getBoolean(Shop.PICK_FROM_SHOP_AVAILABLE));
                shop.setHomeDeliveryAvailable(rs.getBoolean(Shop.HOME_DELIVERY_AVAILABLE));

                shop.setShopEnabled(rs.getBoolean(Shop.SHOP_ENABLED));
                shop.setShopWaitlisted(rs.getBoolean(Shop.SHOP_WAITLISTED));

                shop.setLogoImagePath(rs.getString(Shop.LOGO_IMAGE_PATH));

                shop.setShopAddress(rs.getString(Shop.SHOP_ADDRESS));
                shop.setCity(rs.getString(Shop.CITY));
                shop.setPincode(rs.getLong(Shop.PINCODE));
                shop.setLandmark(rs.getString(Shop.LANDMARK));

                shop.setCustomerHelplineNumber(rs.getString(Shop.CUSTOMER_HELPLINE_NUMBER));
                shop.setDeliveryHelplineNumber(rs.getString(Shop.DELIVERY_HELPLINE_NUMBER));

                shop.setShortDescription(rs.getString(Shop.SHORT_DESCRIPTION));
                shop.setLongDescription(rs.getString(Shop.LONG_DESCRIPTION));

                shop.setTimestampCreated(rs.getTimestamp(Shop.TIMESTAMP_CREATED));
                shop.setOpen(rs.getBoolean(Shop.IS_OPEN));


                endPoint.setItemCount(rs.getInt("full_count"));



                shopList.add(shop);

            }

            System.out.println("Total Shops queried " + shopList.size());


            endPoint.setResults(shopList);


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



}
