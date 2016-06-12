package org.nearbyshops.DAOs;

import org.nearbyshops.ContractClasses.*;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.CartItem;
import org.nearbyshops.Model.EndUser;

import java.sql.*;
import java.util.ArrayList;


public class CartItemService {

	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}
	
	
	
	public int saveCartItem(CartItem cartItem)
	{	
		
		Connection conn = null;
		Statement stmt = null;
		int rowCount = -1;

		String insertEndUser = "INSERT INTO "
				+ CartItemContract.TABLE_NAME
				+ "("  
				+ CartItemContract.CART_ID + ","
				+ CartItemContract.ITEM_ID + ","
				+ CartItemContract.ITEM_QUANTITY + ""
				+ ") VALUES("
				+ "" + cartItem.getCartID() + ","
				+ "" + cartItem.getItemID() + ","
				+ "" + cartItem.getItemQuantity()
				+ ")";
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCount = stmt.executeUpdate(insertEndUser,Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.getGeneratedKeys();

			//if(rs.next())
			//{
			//	rowCount = rs.getInt(1);
			//}
			

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
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

		
		return rowCount;
	}
	

	public int updateCartItem(CartItem cartItem)
	{	
		String updateStatement = "UPDATE "
				+ CartItemContract.TABLE_NAME
				+ " SET "
				+ CartItemContract.ITEM_ID + " = " + cartItem.getItemID() + ","
				+ CartItemContract.CART_ID + " = " + cartItem.getCartID() + ","
				+ CartItemContract.ITEM_QUANTITY + " = " + cartItem.getItemQuantity()
				+ " WHERE " + CartItemContract.CART_ID + " = "
				+ cartItem.getCartID() + " AND "
				+ CartItemContract.ITEM_ID + " = "
				+ cartItem.getItemID();


		Connection conn = null;
		Statement stmt = null;
		int updatedRows = -1;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			updatedRows = stmt.executeUpdate(updateStatement);
			
			
			System.out.println("Total rows updated: " + updatedRows);	
			
			//conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		
		{
			
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

		return updatedRows;
		
	}
	

	public int deleteCartItem(int itemID,int cartID)
	{

		String deleteStatement = "DELETE FROM " + CartItemContract.TABLE_NAME;


		boolean isFirst = true;

		if(itemID > 0)
		{
			deleteStatement = deleteStatement + " WHERE " + CartItemContract.ITEM_ID + " = " + itemID;
			isFirst = false;
		}

		if(cartID > 0)
		{
			if(isFirst)
			{
				deleteStatement = deleteStatement + " WHERE " + CartItemContract.CART_ID + " = " + cartID;
			}else
			{
				deleteStatement = deleteStatement + " AND " + CartItemContract.CART_ID + " = " + cartID;
			}

		}




		Connection conn= null;
		Statement stmt = null;
		int rowsCountDeleted = 0;
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowsCountDeleted = stmt.executeUpdate(deleteStatement);
			
			System.out.println(" Deleted Count: " + rowsCountDeleted);	
			
			conn.close();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		
		{
			
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
	
		
		return rowsCountDeleted;
	}
	
	
	
	
	
	public ArrayList<CartItem> getCartItem(int cartID, int itemID, int endUserID)
	{
		String query = "SELECT * FROM " + CartItemContract.TABLE_NAME + "," + CartContract.TABLE_NAME
				+ " WHERE " + CartItemContract.TABLE_NAME + "."+ CartItemContract.CART_ID  + " = "
				+ CartContract.TABLE_NAME + "." + CartContract.CART_ID ;


		if(endUserID > 0)
		{
			query = query + " AND " + CartContract.END_USER_ID + " = " + endUserID;
		}




		ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();

		//boolean isFirst = true;

		if(cartID > 0)
		{
			query = query + " AND " + CartContract.TABLE_NAME + "." + CartItemContract.CART_ID + " = " + cartID;

		//	isFirst = false;
		}


		if(itemID > 0)
		{

			query = query + " AND " + CartItemContract.ITEM_ID + " = " + itemID;

			/*
			if(isFirst)
			{
				query = query + " AND " + CartItemContract.ITEM_ID + " = " + itemID;

				isFirst = false;

			}else
			{

			}*/

		}

		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
					,JDBCContract.CURRENT_USERNAME
					, JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				CartItem cartItem = new CartItem();

				cartItem.setCartID(rs.getInt(CartItemContract.CART_ID));
				cartItem.setItemID(rs.getInt(CartItemContract.ITEM_ID));
				cartItem.setItemQuantity(rs.getInt(CartItemContract.ITEM_QUANTITY));

				cartItemList.add(cartItem);
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

		return cartItemList;
	}



	public ArrayList<CartItem> getCartItem(int endUserID,int shopID)
	{


		String query = "SELECT cart.cart_id,cart_item.item_id," +
						" available_item_quantity, item_price, item_quantity, quantity_unit ," +
						" (item_quantity * item_price) as Item_total" +
						" FROM " +
						" shop_item, cart_item, cart " +
						" Where " +
						"shop_item.shop_id = cart.shop_id " +
						" and " +
						" shop_item.item_id = cart_item.item_id " +
						" and " +
						" cart.cart_id = cart_item.cart_id " +
						" and end_user_id = " +

								endUserID +

						"and cart.shop_id = " +

								shopID;



		ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
					,JDBCContract.CURRENT_USERNAME
					, JDBCContract.CURRENT_PASSWORD);

			stmt = conn.createStatement();

			rs = stmt.executeQuery(query);

			while(rs.next())
			{
				CartItem cartItem = new CartItem();

				cartItem.setCartID(rs.getInt(CartItemContract.CART_ID));
				cartItem.setItemID(rs.getInt(CartItemContract.ITEM_ID));
				cartItem.setItemQuantity(rs.getInt(CartItemContract.ITEM_QUANTITY));
				cartItem.setRt_availableItemQuantity(rs.getInt("available_item_quantity"));
				cartItem.setRt_itemPrice(rs.getDouble("item_price"));
				cartItem.setRt_quantityUnit(rs.getString("quantity_unit"));


				//cartItem.setItem(Globals.itemService.getItem(cartItem.getItemID()));

				cartItemList.add(cartItem);
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


		return cartItemList;
	}

}
