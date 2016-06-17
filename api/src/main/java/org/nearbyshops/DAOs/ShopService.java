package org.nearbyshops.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.nearbyshops.ContractClasses.*;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.Utility.GeoLocation;

public class ShopService {
	
	
	public int insertShop(Shop shop)
	{
		
		Connection conn = null;
		Statement stmt = null;
		int rowIdOfInsertedRow = -1;

		String insertShop = "INSERT INTO "
				+ ShopContract.TABLE_NAME				
				+ "("  
				+ ShopContract.SHOP_NAME + ","
				+ ShopContract.DELIVERY_CHARGES + ","
				+ ShopContract.DISTRIBUTOR_ID + ","
				+ ShopContract.LAT_CENTER + ","
				+ ShopContract.LON_CENTER + ","
				+ ShopContract.DELIVERY_RANGE + ","
				+ ShopContract.LON_MAX + ","
				+ ShopContract.LAT_MAX + ","
				+ ShopContract.LON_MIN + ","
				+ ShopContract.LAT_MIN + ","
				+ ShopContract.IMAGE_PATH + ","

				+ ShopContract.SHOP_ADDRESS + ","
				+ ShopContract.CITY + ","
				+ ShopContract.PINCODE + ","
				+ ShopContract.LANDMARK + ","
				+ ShopContract.BILL_AMOUNT_FOR_FREE_DELIVERY + ","
				+ ShopContract.CUSTOMER_HELPLINE_NUMBER + ","
				+ ShopContract.DELIVERY_HELPLINE_NUMBER + ","
				+ ShopContract.SHORT_DESCRIPTION + ","
				+ ShopContract.LONG_DESCRIPTION + ","
				+ ShopContract.IS_OPEN + ""

				+ " ) VALUES ("
				+ "'" + shop.getShopName() + "',"
				+ "" + shop.getDeliveryCharges() + ","
				+ "" + shop.getDistributorID() + ","
				+ "" + shop.getLatCenter() + ","
				+ "" + shop.getLonCenter() + ","
				+ "" + shop.getDeliveryRange() + ","
				+ "" + shop.getLonMax() + ","
				+ "" + shop.getLatMax() + ","
				+ "" + shop.getLonMin() + ","
				+ "" + shop.getLatMin() + ","
				+ "'" + shop.getImagePath() + "',"

				+ "'" + shop.getShopAddress() + "',"
				+ "'" + shop.getCity() + "',"
				+ "'" + shop.getPincode() + "',"
				+ "'" + shop.getLandmark() + "',"
				+ "" + shop.getBillAmountForFreeDelivery() + ","
				+ "'" + shop.getCustomerHelplineNumber() + "',"
				+ "'" + shop.getDeliveryHelplineNumber() + "',"
				+ "'" + shop.getShortDescription() + "',"
				+ "'" + shop.getLongDescription() + "',"
				+ "" + shop.getisOpen() + ""
				+ ")";
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowIdOfInsertedRow = stmt.executeUpdate(insertShop,Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if(rs.next())
			{
				rowIdOfInsertedRow = rs.getInt(1);
			}
			
			
			
			System.out.println("Key autogenerated SaveShop: " + rowIdOfInsertedRow);
			
			
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

		
		return rowIdOfInsertedRow;

	}
	
	
	public int updateShop(Shop shop)
	{
		
		
		String updateStatement = "UPDATE " + ShopContract.TABLE_NAME 
				+ " SET "

				+ ShopContract.SHOP_NAME + " = " + "'" + shop.getShopName() + "',"
				+ ShopContract.DELIVERY_CHARGES + " =" + "" + shop.getDeliveryCharges() + ","
				+ ShopContract.DISTRIBUTOR_ID + " =" + "" + shop.getDistributorID() + ","
				+ ShopContract.LAT_CENTER + " =" + "" + shop.getLatCenter() + ","
				+ ShopContract.LON_CENTER + " =" + "" + shop.getLonCenter() + ","
				+ ShopContract.DELIVERY_RANGE + " =" + "" + shop.getDeliveryRange() + ","
				+ ShopContract.LON_MAX + " =" + "" + shop.getLonMax() + ","
				+ ShopContract.LAT_MAX + " =" + "" + shop.getLatMax() + ","
				+ ShopContract.LON_MIN + " =" + "" + shop.getLonMin() + ","
				+ ShopContract.LAT_MIN + " =" + "" + shop.getLatMin() + ","
				+ ShopContract.IMAGE_PATH + " = " + "'" + shop.getImagePath() + "',"

				+ ShopContract.SHOP_ADDRESS + " = " + "'" + shop.getShopAddress() + "',"
				+ ShopContract.CITY + " = " + "'" + shop.getCity() + "',"
				+ ShopContract.PINCODE + " = " + "" + shop.getPincode() + ","
				+ ShopContract.LANDMARK + " = " + "'" + shop.getLandmark() + "',"
				+ ShopContract.BILL_AMOUNT_FOR_FREE_DELIVERY + " =" + "" + shop.getBillAmountForFreeDelivery() + ","
				+ ShopContract.CUSTOMER_HELPLINE_NUMBER + " =" + "'" + shop.getCustomerHelplineNumber() + "',"
				+ ShopContract.DELIVERY_HELPLINE_NUMBER + " =" + "'" + shop.getDeliveryHelplineNumber() + "',"
				+ ShopContract.SHORT_DESCRIPTION + " =" + "'" + shop.getShortDescription() + "',"
				+ ShopContract.LONG_DESCRIPTION + " =" + "'" + shop.getLongDescription() + "',"
				+ ShopContract.IS_OPEN + " = " + "" + shop.getisOpen() + ""

				+ " WHERE " + ShopContract.SHOP_ID + " = "
				+ shop.getShopID();
		
		
		
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
	
	
	public int deleteShop(int shopID)
	{
		
		String deleteStatement = "DELETE FROM " + ShopContract.TABLE_NAME 
				+ " WHERE " + ShopContract.SHOP_ID + "= " 
				+ shopID;
		
		
		Connection conn= null;
		Statement stmt = null;
		int rowCountDeleted = 0;
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCountDeleted = stmt.executeUpdate(deleteStatement);
			
			System.out.println(" Deleted Count: " + rowCountDeleted);	
			
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
	
		
		return rowCountDeleted;
	}



	GeoLocation center;

	GeoLocation[] minMaxArray;
	GeoLocation pointOne;
	GeoLocation pointTwo;




	public ArrayList<Shop> getShops(
			int distributorID,
			int itemCategoryID,
			double latCenter, double lonCenter,
			double deliveryRangeMin,double deliveryRangeMax,
			double proximity,
			String sortBy,
			int limit, int offset
	)
	{

		String query = "";
		String queryJoin = "";

		// flag for tracking whether to put "AND" or "WHERE"
		boolean isFirst = true;


		String queryNormal = "SELECT DISTINCT "
				+ "6371 * acos( cos( radians("
				+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
				+ lonCenter + "))"
				+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) as distance" + ","
				+ " * FROM " + ShopContract.TABLE_NAME;


		queryJoin = "SELECT DISTINCT "
				+ "6371 * acos(cos( radians("
				+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
				+ lonCenter + "))"
				+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) as distance" + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.SHOP_ID + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.SHOP_NAME + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.LON_CENTER + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.LAT_CENTER + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.DELIVERY_RANGE + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.DELIVERY_CHARGES + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.DISTRIBUTOR_ID + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.IMAGE_PATH + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.LAT_MAX + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.LAT_MIN + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.LON_MAX + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.LON_MIN + ","

				+ ShopContract.TABLE_NAME + "." + ShopContract.SHOP_ADDRESS + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.CITY + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.PINCODE + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.LANDMARK + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.BILL_AMOUNT_FOR_FREE_DELIVERY + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.CUSTOMER_HELPLINE_NUMBER + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.DELIVERY_HELPLINE_NUMBER + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.SHORT_DESCRIPTION + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.LONG_DESCRIPTION + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.IS_OPEN + ","
				+ ShopContract.TABLE_NAME + "." + ShopContract.DATE_TIME_STARTED

				+ " FROM "
				+ ShopContract.TABLE_NAME  + "," + ShopItemContract.TABLE_NAME + ","
				+ ItemContract.TABLE_NAME + "," + ItemCategoryContract.TABLE_NAME

				+ " WHERE "
				+ ShopContract.TABLE_NAME + "." + ShopContract.SHOP_ID + "="
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.SHOP_ID

				+ " AND "
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.ITEM_ID + "="
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_ID

				+ " AND "
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_CATEGORY_ID + "="
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_ID;



		if(distributorID > 0)
		{
			queryNormal = queryNormal + " WHERE "
					+ ShopContract.DISTRIBUTOR_ID + " = " + distributorID;

			// reset the flag
			isFirst = false;

			queryJoin = queryJoin + " AND "
					+ ShopContract.TABLE_NAME + "." + ShopContract.DISTRIBUTOR_ID
					+ " = " + distributorID;
		}


		if(latCenter>0 && latCenter>0)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.


			String queryPartlatLonCenter = "";
			String queryPartlatLonCenterTwo = "";

			if(isFirst)
			{
				queryNormal = queryNormal + " WHERE ";

				// reset the flag
				isFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND ";
			}


			queryPartlatLonCenterTwo = queryPartlatLonCenterTwo + ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LAT_MAX
					+ " >= " + latCenter
					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LAT_MIN
					+ " <= " + latCenter
					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LON_MAX
					+ " >= " + lonCenter
					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LON_MIN
					+ " <= " + lonCenter;


			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";

			//+ " BETWEEN " + latmax + " AND " + latmin;

			queryNormal = queryNormal + queryPartlatLonCenter;

			queryJoin = queryJoin + " AND " + queryPartlatLonCenter;

		}



		if(deliveryRangeMin > 0|| deliveryRangeMax>0){

			// apply delivery range filter
			String queryPartDeliveryRange = "";

			if(isFirst)
			{
				queryNormal = queryNormal + " WHERE ";

				// reset the flag
				isFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND ";
			}




			queryPartDeliveryRange = queryPartDeliveryRange + ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
					//+ " <= " + deliveryRange;

			queryNormal = queryNormal + queryPartDeliveryRange;

			queryJoin = queryJoin + " AND " + queryPartDeliveryRange;

		}


		// proximity cannot be greater than the delivery range if the delivery range is supplied. Otherwise this condition is
		// not required.
		if(proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax)))
		{

			String queryPartProximity = "";
			String queryPartProximityTwo = "";

			// generate bounding coordinates for the shop based on the required location and its
			center = GeoLocation.fromDegrees(latCenter,lonCenter);
			minMaxArray = center.boundingCoordinates(proximity,6371.01);

			pointOne = minMaxArray[0];
			pointTwo = minMaxArray[1];

			double latMin = pointOne.getLatitudeInDegrees();
			double lonMin = pointOne.getLongitudeInDegrees();
			double latMax = pointTwo.getLatitudeInDegrees();
			double lonMax = pointTwo.getLongitudeInDegrees();


			// Make sure that shop center lies between the bounding coordinates generated by proximity bounding box

			if(isFirst)
			{
				queryNormal = queryNormal + " WHERE ";

				// reset the flag
				isFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND ";
			}

				// Filtering by proximity using bounding coordinates
			queryPartProximityTwo = queryPartProximityTwo+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LAT_CENTER
					+ " < " + latMax

					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LAT_CENTER
					+ " > " + latMin

					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LON_CENTER
					+ " < " + lonMax

					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.LON_CENTER
					+ " > " + lonMin;


				// filter using Haversine formula using SQL math functions
			queryPartProximity = queryPartProximity
					+ " (6371.01 * acos(cos( radians("
					+ latCenter
					+ ")) * cos( radians("
					+ ShopContract.LAT_CENTER
					+ " )) * cos(radians( "
					+ ShopContract.LON_CENTER
					+ ") - radians("
					+ lonCenter
					+ "))"
					+ " + sin( radians("
					+ latCenter
					+ ")) * sin(radians("
					+ ShopContract.LAT_CENTER
					+ ")))) <= "
					+ proximity ;


			queryNormal = queryNormal + queryPartProximity;

			queryJoin = queryJoin + " AND " + queryPartProximity;


		}


		if(itemCategoryID>0)
		{
			// filter shops by Item Category ID
			queryJoin = queryJoin + " AND "
					+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_ID
					+ " = "
					+ itemCategoryID;
		}



		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				queryNormal = queryNormal + queryPartSortBy;
				queryJoin = queryJoin + queryPartSortBy;
			}
		}



		if(limit > 0)
		{

			String queryPartLimitOffset = "";

			if(offset>0)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}


			queryNormal = queryNormal + queryPartLimitOffset;
			queryJoin = queryJoin + queryPartLimitOffset;
		}



		// use Join query only if filtering requires a join
		if(itemCategoryID>0)
		{
			query = queryJoin;

		}else
		{
			query = queryNormal;
		}




		
		ArrayList<Shop> shopList = new ArrayList<Shop>();
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				
				Shop shop = new Shop();
				shop.setDistance(rs.getDouble("distance"));
				shop.setShopID(rs.getInt(ShopContract.SHOP_ID));
				shop.setShopName(rs.getString(ShopContract.SHOP_NAME));
				shop.setLatCenter(rs.getFloat(ShopContract.LAT_CENTER));
				shop.setLonCenter(rs.getFloat(ShopContract.LON_CENTER));
				shop.setDeliveryCharges(rs.getFloat(ShopContract.DELIVERY_CHARGES));
				shop.setLatMax(rs.getDouble(ShopContract.LAT_MAX));
				shop.setLonMax(rs.getDouble(ShopContract.LON_MAX));
				shop.setLatMin(rs.getDouble(ShopContract.LAT_MIN));
				shop.setLonMin(rs.getDouble(ShopContract.LON_MIN));
				shop.setDistributorID(rs.getInt(ShopContract.DISTRIBUTOR_ID));
				shop.setDeliveryRange(rs.getDouble(ShopContract.DELIVERY_RANGE));
				shop.setImagePath(rs.getString(ShopContract.IMAGE_PATH));

				shop.setShopAddress(rs.getString(ShopContract.SHOP_ADDRESS));
				shop.setCity(rs.getString(ShopContract.CITY));
				shop.setPincode(rs.getLong(ShopContract.PINCODE));
				shop.setLandmark(rs.getString(ShopContract.LANDMARK));
				shop.setBillAmountForFreeDelivery(rs.getInt(ShopContract.BILL_AMOUNT_FOR_FREE_DELIVERY));
				shop.setCustomerHelplineNumber(rs.getString(ShopContract.CUSTOMER_HELPLINE_NUMBER));
				shop.setDeliveryHelplineNumber(rs.getString(ShopContract.DELIVERY_HELPLINE_NUMBER));
				shop.setShortDescription(rs.getString(ShopContract.SHORT_DESCRIPTION));
				shop.setLongDescription(rs.getString(ShopContract.LONG_DESCRIPTION));
				shop.setDateTimeStarted(rs.getTimestamp(ShopContract.DATE_TIME_STARTED));
				shop.setisOpen(rs.getBoolean(ShopContract.IS_OPEN));

				shopList.add(shop);
				
			}
			
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

		return shopList;
	}
	
	
	
	public Shop getShop(int ShopID,
						double latCenter, double lonCenter)
	{

		String query = "SELECT "
						+ " (6371.01 * acos(cos( radians("
						+ latCenter
						+ ")) * cos( radians("
						+ ShopContract.LAT_CENTER
						+ " )) * cos(radians( "
						+ ShopContract.LON_CENTER
						+ ") - radians("
						+ lonCenter
						+ "))"
						+ " + sin( radians("
						+ latCenter
						+ ")) * sin(radians("
						+ ShopContract.LAT_CENTER
						+ "))))"
						+ " as distance , * FROM " + ShopContract.TABLE_NAME
						+ " WHERE "	+  ShopContract.SHOP_ID + "= " + ShopID;


		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		Shop shop = null;
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			
			while(rs.next())
			{
				
				shop = new Shop();
				shop.setDistance(rs.getDouble("distance"));
				shop.setShopID(rs.getInt(ShopContract.SHOP_ID));
				shop.setShopName(rs.getString(ShopContract.SHOP_NAME));
				shop.setLatCenter(rs.getFloat(ShopContract.LAT_CENTER));
				shop.setLonCenter(rs.getFloat(ShopContract.LON_CENTER));
				shop.setDeliveryCharges(rs.getFloat(ShopContract.DELIVERY_CHARGES));
				shop.setLatMax(rs.getDouble(ShopContract.LAT_MAX));
				shop.setLonMax(rs.getDouble(ShopContract.LON_MAX));
				shop.setLatMin(rs.getDouble(ShopContract.LAT_MIN));
				shop.setLonMin(rs.getDouble(ShopContract.LON_MIN));
				shop.setDistributorID(rs.getInt(ShopContract.DISTRIBUTOR_ID));
				shop.setDeliveryRange(rs.getDouble(ShopContract.DELIVERY_RANGE));
				shop.setImagePath(rs.getString(ShopContract.IMAGE_PATH));

				shop.setShopAddress(rs.getString(ShopContract.SHOP_ADDRESS));
				shop.setCity(rs.getString(ShopContract.CITY));
				shop.setPincode(rs.getLong(ShopContract.PINCODE));
				shop.setLandmark(rs.getString(ShopContract.LANDMARK));
				shop.setBillAmountForFreeDelivery(rs.getInt(ShopContract.BILL_AMOUNT_FOR_FREE_DELIVERY));
				shop.setCustomerHelplineNumber(rs.getString(ShopContract.CUSTOMER_HELPLINE_NUMBER));
				shop.setDeliveryHelplineNumber(rs.getString(ShopContract.DELIVERY_HELPLINE_NUMBER));
				shop.setShortDescription(rs.getString(ShopContract.SHORT_DESCRIPTION));
				shop.setLongDescription(rs.getString(ShopContract.LONG_DESCRIPTION));
				shop.setDateTimeStarted(rs.getTimestamp(ShopContract.DATE_TIME_STARTED));
				shop.setisOpen(rs.getBoolean(ShopContract.IS_OPEN));
				
			}
	
			
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
	
		return shop;
	}
	
}
