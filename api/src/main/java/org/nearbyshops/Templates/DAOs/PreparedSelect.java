package org.nearbyshops.Templates.DAOs;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.Model.ShopItem;
import org.nearbyshops.ModelEndPoints.ShopEndPoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sumeet on 13/12/16.
 */
public class PreparedSelect {

    private HikariDataSource dataSource = Globals.getDataSource();





    // An Example for using prepared statements with SELECT queries
    public ArrayList<Shop> getShopsForShopFiltersPrepared(
            Double latCenter, Double lonCenter,
            Double deliveryRangeMin,Double deliveryRangeMax,
            Double proximity)
    {

        String query = "";
        String queryJoin = "";

        queryJoin = "SELECT DISTINCT "
                + Shop.TABLE_NAME + "." + Shop.SHOP_ID + ","
                + Shop.SHOP_NAME + ","

                + Shop.DELIVERY_RANGE + ","
                + Shop.LAT_CENTER + ","
                + Shop.LON_CENTER + ""

                + " FROM " + Shop.TABLE_NAME + "," + ShopItem.TABLE_NAME + "," + Item.TABLE_NAME + "," + ItemCategory.TABLE_NAME
                + " WHERE " + Shop.TABLE_NAME + "." + Shop.SHOP_ID + "=" + ShopItem.TABLE_NAME + "." + ShopItem.SHOP_ID
                + " AND " + ShopItem.TABLE_NAME + "." + ShopItem.ITEM_ID + " = " + Item.TABLE_NAME + "." + Item.ITEM_ID
                + " AND " + Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + " = " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_ID

                + " AND " // Visibility Filter
                + " ((6371.01 * acos(cos( radians( ? )) * cos( radians( " + Shop.LAT_CENTER + " )) * cos(radians( "
                + Shop.LON_CENTER + " ) - radians( ? )) " + " + sin( radians( ? )) * sin(radians( "
                + Shop.LAT_CENTER + " )))) <= " + Shop.DELIVERY_RANGE + " ) ";


//		if(latCenter!=null && lonCenter!=null)
//		{
//			queryJoin = queryJoin
//		}


        if(deliveryRangeMin!=null && deliveryRangeMax!=null)
        {
            queryJoin = queryJoin + " AND "  // Delivery Range Filter
                    + Shop.TABLE_NAME + "." + Shop.DELIVERY_RANGE + " BETWEEN ? AND ? ";
        }


        if(proximity!=null)
        {
            queryJoin = queryJoin + " AND " // Proximity Filter
                    + " (6371.01 * acos(cos( radians( ? )) * cos( radians(" + Shop.LAT_CENTER + " )) * cos(radians( "
                    + Shop.LON_CENTER + ") - radians( ? ))" + " + sin( radians( ? )) * sin(radians("
                    + Shop.LAT_CENTER + ")))) <= ?";
        }



        //VisibilityFilter 1. latCenter : 2. lonCenter : 3. latCenter
        //Proximity Filter 1. latCenter : 2. lonCenter : 3. latCenter 4: proximity


        String queryCount = "SELECT COUNT(*) as item_count FROM (" + query + ") AS temp";

        query = queryJoin;

//		ShopEndPoint endPoint = getShopsForShopFiltersPrepared(latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity);
        ArrayList<Shop> shopList = new ArrayList<Shop>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);


            // case 1 : No filters
            // Case 2 : Delivery Range Filter OFF and Proximity Filter ON
            // Case 3 : Proximity Filter OFF and Delivery Range FIlter ON
            // Case 4 : Both filters ON



            statement.setDouble(1,latCenter);
            statement.setDouble(2,lonCenter);
            statement.setDouble(3,latCenter);

            int i = 3;
            if(deliveryRangeMin!=null && deliveryRangeMax!=null)
            {
                statement.setObject(++i,deliveryRangeMin);
                statement.setObject(++i,deliveryRangeMax);
            }

            if(proximity!=null)
            {
                statement.setDouble(++i,latCenter);
                statement.setDouble(++i,lonCenter);
                statement.setDouble(++i,latCenter);

                statement.setObject(++i,proximity);
            }




			/*if(!(deliveryRangeMin!=null && deliveryRangeMax!=null) && (proximity != null))
			{
				statement.setDouble(4,latCenter);
				statement.setDouble(5,lonCenter);
				statement.setDouble(6,latCenter);

				statement.setObject(7,proximity);
			}
			else if((deliveryRangeMin!=null && deliveryRangeMax!=null) && (proximity==null))
			{
				statement.setObject(4,deliveryRangeMin);
				statement.setObject(5,deliveryRangeMax);
			}
			else if((deliveryRangeMin!=null && deliveryRangeMax!=null) && (proximity!=null))
			{
				statement.setObject(4,deliveryRangeMin);
				statement.setObject(5,deliveryRangeMax);

				statement.setDouble(6,latCenter);
				statement.setDouble(7,lonCenter);
				statement.setDouble(8,latCenter);

				statement.setObject(9,proximity);
			}
				*/


            rs = statement.executeQuery();


            while(rs.next())
            {

                Shop shop = new Shop();
                shop.setShopID(rs.getInt(Shop.SHOP_ID));
                shop.setShopName(rs.getString(Shop.SHOP_NAME));
                shop.setDeliveryRange(rs.getDouble(Shop.DELIVERY_RANGE));
                shop.setLatCenter(rs.getFloat(Shop.LAT_CENTER));
                shop.setLonCenter(rs.getFloat(Shop.LON_CENTER));

//				endPoint.setItemCount(rs.getInt("full_count"));

                shopList.add(shop);
            }


//			endPoint.setResults(shopList);

            System.out.println("Total Shops queried " + shopList.size());

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

        return shopList;
    }


}
