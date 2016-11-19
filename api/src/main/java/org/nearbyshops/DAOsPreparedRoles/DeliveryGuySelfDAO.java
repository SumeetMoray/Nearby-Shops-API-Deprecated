package org.nearbyshops.DAOsPreparedRoles;


import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.DeliveryGuySelf;

import java.sql.*;
import java.util.ArrayList;


public class DeliveryGuySelfDAO {

	private HikariDataSource dataSource = Globals.getDataSource();

	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}


	public int saveDeliveryVehicleSelf(DeliveryGuySelf deliveryGuySelf)
	{

		Connection connection = null;
		PreparedStatement statement = null;
		int rowIdOfInsertedRow = -1;

		String insertItemCategory = "INSERT INTO "
				+ DeliveryGuySelf.TABLE_NAME
				+ "("

				+ DeliveryGuySelf.USERNAME + ","
				+ DeliveryGuySelf.PASSWORD + ","
				+ DeliveryGuySelf.ABOUT + ","
				+ DeliveryGuySelf.PROFILE_IMAGE_URL + ","
				+ DeliveryGuySelf.PHONE_NUMBER + ","

				+ DeliveryGuySelf.IS_ENABLED + ","
				+ DeliveryGuySelf.IS_WAITLISTED + ","
				+ DeliveryGuySelf.NAME + ","
				+ DeliveryGuySelf.SHOP_ID + ""

				+ " ) VALUES (?,?,?,?, ?,?,?,? ,?)";
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertItemCategory,Statement.RETURN_GENERATED_KEYS);

			statement.setString(1,deliveryGuySelf.getUsername());
			statement.setString(2,deliveryGuySelf.getPassword());
			statement.setString(3,deliveryGuySelf.getAbout());
			statement.setString(4,deliveryGuySelf.getProfileImageURL());
			statement.setString(5,deliveryGuySelf.getPhoneNumber());

			statement.setObject(6,deliveryGuySelf.getEnabled());
			statement.setObject(7,deliveryGuySelf.getWaitlisted());
			statement.setString(8,deliveryGuySelf.getName());
			statement.setObject(9,deliveryGuySelf.getShopID());

			rowIdOfInsertedRow = statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();

			if(rs.next())
			{
				rowIdOfInsertedRow = rs.getInt(1);
			}
			
			
			
			System.out.println("Key autogenerated SaveDistributor: " + rowIdOfInsertedRow);
			
			
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

		
		return rowIdOfInsertedRow;
	}
	


	public int updateDeliveryVehicleSelf(DeliveryGuySelf deliveryGuySelf)
	{	
		String updateStatement = "UPDATE " + DeliveryGuySelf.TABLE_NAME
				+ " SET "
				+ DeliveryGuySelf.USERNAME + " = ?,"
				+ DeliveryGuySelf.PASSWORD + " = ?,"
				+ DeliveryGuySelf.ABOUT + " = ?,"
				+ DeliveryGuySelf.PROFILE_IMAGE_URL + " = ?,"
				+ DeliveryGuySelf.PHONE_NUMBER + " = ?,"

				+ DeliveryGuySelf.IS_ENABLED + " = ?,"
				+ DeliveryGuySelf.IS_WAITLISTED + " = ?,"
				+ DeliveryGuySelf.NAME + " = ?,"
				+ DeliveryGuySelf.SHOP_ID + " = ?"

				+ " WHERE " + DeliveryGuySelf.ID + " = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		int updatedRows = -1;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			statement.setString(1,deliveryGuySelf.getUsername());
			statement.setString(2,deliveryGuySelf.getPassword());
			statement.setString(3,deliveryGuySelf.getAbout());
			statement.setString(4,deliveryGuySelf.getProfileImageURL());
			statement.setString(5,deliveryGuySelf.getPhoneNumber());

			statement.setObject(6,deliveryGuySelf.getEnabled());
			statement.setObject(7,deliveryGuySelf.getWaitlisted());
			statement.setString(8,deliveryGuySelf.getName());
			statement.setObject(9,deliveryGuySelf.getShopID());

			statement.setObject(10,deliveryGuySelf.getDeliveryGuyID());
			
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
	


	public int deleteDeliveryVehicleSelf(int vehicleID)
	{
		
		String deleteStatement = "DELETE FROM " + DeliveryGuySelf.TABLE_NAME
				+ " WHERE " + DeliveryGuySelf.ID + " = ?";
		
		
		Connection connection= null;
		PreparedStatement statement = null;
		int rowsCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);

			statement.setObject(1,vehicleID);

			rowsCountDeleted = statement.executeUpdate();
			
			System.out.println(" Deleted Count: " + rowsCountDeleted);	
			
			connection.close();
			
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
	
		
		return rowsCountDeleted;
	}
	
	



	
	
	public ArrayList<DeliveryGuySelf> readDeliveryVehicleSelf(Integer shopID, Boolean isEnabled)
	{
		String query = "SELECT * FROM " + DeliveryGuySelf.TABLE_NAME;

		boolean isFirst = true;

		if(shopID != null)
		{
			query = query + " WHERE " + DeliveryGuySelf.SHOP_ID + " = " + shopID;

				isFirst = false;
		}

		if(isEnabled !=null)
		{
			if(isFirst)
			{
				query = query + " WHERE " + DeliveryGuySelf.IS_ENABLED + " = "  + isEnabled;

				isFirst = false;
			}
			else
			{
				query = query + " AND " + DeliveryGuySelf.IS_ENABLED + " = "  + isEnabled;
			}
		}



		ArrayList<DeliveryGuySelf> vehiclesList = new ArrayList<DeliveryGuySelf>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			rs = statement.executeQuery(query);
			
			while(rs.next())
			{

				DeliveryGuySelf deliveryGuySelf = new DeliveryGuySelf();

				deliveryGuySelf.setDeliveryGuyID(rs.getInt(DeliveryGuySelf.ID));
				deliveryGuySelf.setName(rs.getString(DeliveryGuySelf.NAME));
				deliveryGuySelf.setShopID(rs.getInt(DeliveryGuySelf.SHOP_ID));

				deliveryGuySelf.setUsername(rs.getString(DeliveryGuySelf.USERNAME));
				deliveryGuySelf.setPassword(rs.getString(DeliveryGuySelf.PASSWORD));

				deliveryGuySelf.setAbout(rs.getString(DeliveryGuySelf.ABOUT));
				deliveryGuySelf.setProfileImageURL(rs.getString(DeliveryGuySelf.PROFILE_IMAGE_URL));
				deliveryGuySelf.setPhoneNumber(rs.getString(DeliveryGuySelf.PHONE_NUMBER));

				deliveryGuySelf.setEnabled(rs.getBoolean(DeliveryGuySelf.IS_ENABLED));
				deliveryGuySelf.setWaitlisted(rs.getBoolean(DeliveryGuySelf.IS_WAITLISTED));

				vehiclesList.add(deliveryGuySelf);
				
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


		return vehiclesList;
	}



	public boolean checkUsernameExists(String username)
	{

		String query = "SELECT " + DeliveryGuySelf.USERNAME
					+ " FROM " + DeliveryGuySelf.TABLE_NAME
					+ " WHERE " + DeliveryGuySelf.USERNAME + " = '" + username + "'";

		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

//		System.out.println(query);

		DeliveryGuySelf deliveryGuySelf = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);

//			if(rs.getFetchSize()==0)
//			{
//				return false;
//			}
//			else
//			{
//				return true;
//			}


			while(rs.next())
			{

				return true;
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

		return false;
	}

	
	public DeliveryGuySelf readVehicle(int deliveryGuyID)
	{
		
		String query = "SELECT * FROM " + DeliveryGuySelf.TABLE_NAME
						+ " WHERE " + DeliveryGuySelf.ID + " = " + deliveryGuyID;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;


		DeliveryGuySelf deliveryGuySelf = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				deliveryGuySelf = new DeliveryGuySelf();


				deliveryGuySelf.setDeliveryGuyID(rs.getInt(DeliveryGuySelf.ID));
				deliveryGuySelf.setName(rs.getString(DeliveryGuySelf.NAME));
				deliveryGuySelf.setShopID(rs.getInt(DeliveryGuySelf.SHOP_ID));

				deliveryGuySelf.setUsername(rs.getString(DeliveryGuySelf.USERNAME));
				deliveryGuySelf.setPassword(rs.getString(DeliveryGuySelf.PASSWORD));

				deliveryGuySelf.setAbout(rs.getString(DeliveryGuySelf.ABOUT));
				deliveryGuySelf.setProfileImageURL(rs.getString(DeliveryGuySelf.PROFILE_IMAGE_URL));
				deliveryGuySelf.setPhoneNumber(rs.getString(DeliveryGuySelf.PHONE_NUMBER));

				deliveryGuySelf.setEnabled((Boolean) rs.getObject(DeliveryGuySelf.IS_ENABLED));
				deliveryGuySelf.setWaitlisted((Boolean) rs.getObject(DeliveryGuySelf.IS_WAITLISTED));

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
	
		return deliveryGuySelf;
	}	
}
