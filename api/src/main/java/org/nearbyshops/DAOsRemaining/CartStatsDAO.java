package org.nearbyshops.DAOsRemaining;

import org.nearbyshops.JDBCContract;
import org.nearbyshops.Model.Cart;
import org.nearbyshops.Model.CartItem;
import org.nearbyshops.Model.ShopItem;
import org.nearbyshops.ModelStats.CartStats;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumeet on 1/6/16.
 */
public class CartStatsDAO {


    public List<CartStats> getCartStats(int endUserID, Integer cartID, Integer shopID)
    {



        /*

        select sum(item_quantity*item_price) as Cart_Total,count(cart_item.item_id) as Items_In_Cart,cart.shop_id
        from shop_item, cart, cart_item where cart.cart_id = cart_item.cart_id and shop_item.shop_id = cart.shop_id
        and shop_item.item_id = cart_item.item_id and end_user_id = 1 group by cart.shop_id

         */


        String query = "select sum(" +
                CartItem.ITEM_QUANTITY +

                "*" +
                ShopItem.ITEM_PRICE +

                ") as Cart_Total,count(" +

                CartItem.TABLE_NAME + "." + CartItem.ITEM_ID +

                ") as Items_In_Cart," +

                Cart.TABLE_NAME + "." +Cart.SHOP_ID + ","

                + Cart.TABLE_NAME + "." + Cart.CART_ID  +

                " from " +

                ShopItem.TABLE_NAME +
                ", " +
                Cart.TABLE_NAME +
                ", " +
                CartItem.TABLE_NAME +
                " " +
                "where " +
                Cart.TABLE_NAME + "." + Cart.CART_ID +
                " = " +
                CartItem.TABLE_NAME + "." + CartItem.CART_ID +

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




        if(cartID != null)
        {
            query = query + "and " + Cart.TABLE_NAME + "." + Cart.CART_ID + " = " + cartID;
        }



        if(shopID != null)
        {
            query = query + " and " + Cart.TABLE_NAME + "." + Cart.SHOP_ID + " = " + shopID;
        }








        String groupByQueryPart =  " group by " +

                                    " cart.shop_id," + Cart.TABLE_NAME + "." + Cart.CART_ID;



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

                cartStats.setCartID(rs.getInt(Cart.CART_ID));
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
