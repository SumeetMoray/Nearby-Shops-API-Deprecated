package org.nearbyshops.DAOsRemaining;

import org.nearbyshops.JDBCContract;
import org.nearbyshops.ContractClasses.OrderItemContract;
import org.nearbyshops.Model.OrderItem;
import org.nearbyshops.ModelStats.OrderStats;

import java.sql.*;
import java.util.ArrayList;


public class OrderItemService {

	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}
	
	
	
	public int saveOrderItem(OrderItem orderItem)
	{	
		
		Connection conn = null;
		Statement stmt = null;
		int rowCount = -1;



		String insertOrderItem = "INSERT INTO "
				+ OrderItemContract.TABLE_NAME
				+ "("
				+ OrderItemContract.ORDER_ID + ","
				+ OrderItemContract.ITEM_ID + ","
				+ OrderItemContract.ITEM_QUANTITY + ","
				+ OrderItemContract.ITEM_PRICE_AT_ORDER + ""
				+ ") VALUES("
				+ "" + orderItem.getOrderID() + ","
				+ "" + orderItem.getItemID() + ","
				+ "" + orderItem.getItemQuantity() + ","
				+ "" + orderItem.getItemPriceAtOrder() + ")";



		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCount = stmt.executeUpdate(insertOrderItem,Statement.RETURN_GENERATED_KEYS);
			
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
	

	public int updateOrderItem(OrderItem orderItem)
	{

		String updateStatement = "UPDATE "
				+ OrderItemContract.TABLE_NAME
				+ " SET "
				+ OrderItemContract.ITEM_ID + " = " + orderItem.getItemID() + ","
				+ OrderItemContract.ORDER_ID + " = " + orderItem.getOrderID() + ","
				+ OrderItemContract.ITEM_QUANTITY + " = " + orderItem.getItemQuantity() + ","
				+ OrderItemContract.ITEM_PRICE_AT_ORDER + " = " + orderItem.getItemPriceAtOrder()

				+ " WHERE "
				+ OrderItemContract.ORDER_ID + " = " + orderItem.getOrderID()
				+ " AND "
				+ OrderItemContract.ITEM_ID + " = " + orderItem.getItemID();



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
	

	public int deleteOrderItem(int itemID,int orderID)
	{

		String deleteStatement = "DELETE FROM " + OrderItemContract.TABLE_NAME;


		boolean isFirst = true;

		if(itemID > 0)
		{
			deleteStatement = deleteStatement + " WHERE " + OrderItemContract.ITEM_ID + " = " + itemID;
			isFirst = false;
		}

		if(orderID > 0)
		{
			if(isFirst)
			{
				deleteStatement = deleteStatement + " WHERE " + OrderItemContract.ORDER_ID + " = " + orderID;
			}else
			{
				deleteStatement = deleteStatement + " AND " + OrderItemContract.ORDER_ID + " = " + orderID;
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
	
	
	
	
	
	public ArrayList<OrderItem> getOrderItem(int orderID, int itemID)
	{



		String query = "SELECT * FROM " + OrderItemContract.TABLE_NAME;


		boolean isFirst = true;

		if(orderID > 0)
		{
			query = query + " WHERE " + OrderItemContract.ORDER_ID + " = " + orderID;

			isFirst = false;
		}

		if(itemID > 0)
		{
			if(isFirst)
			{
				query = query + " WHERE " + OrderItemContract.ITEM_ID + " = " + itemID;
			}
			else
			{
				query = query + " AND " + OrderItemContract.ITEM_ID + " = " + itemID;
			}

		}




		ArrayList<OrderItem> orderItemList = new ArrayList<OrderItem>();


		
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

				OrderItem orderItem = new OrderItem();

				orderItem.setOrderID(rs.getInt(OrderItemContract.ORDER_ID));
				orderItem.setItemID(rs.getInt(OrderItemContract.ITEM_ID));
				orderItem.setItemPriceAtOrder(rs.getInt(OrderItemContract.ITEM_PRICE_AT_ORDER));
				orderItem.setItemQuantity(rs.getInt(OrderItemContract.ITEM_QUANTITY));

				orderItemList.add(orderItem);

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

		return orderItemList;
	}




	public OrderStats getOrderStats(int orderID)
	{
		String query = "select " +
				"count(item_id) as item_count, " +
				"sum(item_price_at_order * item_quantity) as item_total," +
				" order_id " +
				"from order_item " +
				"where " + "order_id=" +
				orderID +
				" group by order_id";





		OrderStats orderStats = null;

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

				orderStats = new OrderStats();

				orderStats.setOrderID(rs.getInt("order_id"));
				orderStats.setItemCount(rs.getInt("item_count"));
				orderStats.setItemTotal(rs.getInt("item_total"));

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



		return orderStats;
	}



}
