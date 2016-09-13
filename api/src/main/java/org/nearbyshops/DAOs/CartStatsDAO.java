package org.nearbyshops.DAOs;

import org.nearbyshops.ContractClasses.*;
import org.nearbyshops.JDBCContract;
import org.nearbyshops.ModelStats.CartStats;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumeet on 1/6/16.
 */
public class CartStatsDAO {


    public List<CartStats> getCartStats(int endUserID, int cartID)
    {



        /*

        select sum(item_quantity*item_price) as Cart_Total,count(cart_item.item_id) as Items_In_Cart,cart.shop_id
        from shop_item, cart, cart_item where cart.cart_id = cart_item.cart_id and shop_item.shop_id = cart.shop_id
        and shop_item.item_id = cart_item.item_id and end_user_id = 1 group by cart.shop_id

         */


        String query = "select sum(" +
                CartItemContract.ITEM_QUANTITY +

                "*" +
                ShopItemContract.ITEM_PRICE +

                ") as Cart_Total,count(" +

                CartItemContract.TABLE_NAME + "." + CartItemContract.ITEM_ID +

                ") as Items_In_Cart," +

                CartContract.TABLE_NAME + "." +CartContract.SHOP_ID + ","

                + CartContract.TABLE_NAME + "." + CartContract.CART_ID  +

                " from " +

                ShopItemContract.TABLE_NAME +
                ", " +
                CartContract.TABLE_NAME +
                ", " +
                CartItemContract.TABLE_NAME +
                " " +
                "where " +
                CartContract.TABLE_NAME + "." + CartContract.CART_ID +
                " = " +
                CartItemContract.TABLE_NAME + "." + CartItemContract.CART_ID +

                " and " +

                "shop_item.shop_id" +

                " = " +

                "cart.shop_id " +

                "and " +

                "shop_item.item_id" +

                " = " +

                "cart_item.item_id "

                + "and " + "end_user_id = " +

                endUserID ;




        if(cartID > 0)
        {
            query = query + "and " + CartContract.TABLE_NAME + "." + CartContract.CART_ID + " = " + cartID;
        }





        String groupByQueryPart =  " group by " +

                                    " cart.shop_id," + CartContract.TABLE_NAME + "." + CartContract.CART_ID;



        query = query + groupByQueryPart;


        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;


        ArrayList<CartStats> cartStatsList = new ArrayList<>();

        try {

            conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
                    JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);

            stmt = conn.createStatement();

            rs = stmt.executeQuery(query);

            while(rs.next())
            {
                CartStats cartStats = new CartStats();

                cartStats.setCartID(rs.getInt(CartContract.CART_ID));
                cartStats.setShopID(rs.getInt("shop_id"));
                cartStats.setItemsInCart(rs.getInt("items_in_cart"));
                cartStats.setCart_Total(rs.getDouble("cart_total"));

                cartStatsList.add(cartStats);
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

                if(stmt!=null)
                {stmt.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(conn!=null)
                {conn.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return cartStatsList;
    }

}
