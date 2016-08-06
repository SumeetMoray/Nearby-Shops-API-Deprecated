package org.nearbyshops.DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.nearbyshops.ContractClasses.ItemCategoryContract;
import org.nearbyshops.ContractClasses.ItemContract;
import org.nearbyshops.ContractClasses.JDBCContract;
import org.nearbyshops.ContractClasses.ShopContract;
import org.nearbyshops.ContractClasses.ShopItemContract;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.ModelEndPoints.ItemCategoryEndPoint;
import org.nearbyshops.Utility.GeoLocation;

import javax.ws.rs.QueryParam;


public class ItemCategoryService {


	GeoLocation center;

	GeoLocation[] minMaxArray;
	GeoLocation pointOne;
	GeoLocation pointTwo;


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}
	
	
	public int saveItemCategory(ItemCategory itemCategory)
	{
		
		int rowCount = 0;	
		Connection conn = null;
		Statement stmt = null;
		
		String insertItemCategory = "";
		
		System.out.println("isLeaf : " + itemCategory.getIsLeafNode());

		
		if(itemCategory.getParentCategoryID()!=null)
		{
		
			insertItemCategory = "INSERT INTO "
					+ ItemCategoryContract.TABLE_NAME				
					+ "("  
					+ ItemCategoryContract.ITEM_CATEGORY_NAME + ","
					+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","
					+ ItemCategoryContract.PARENT_CATEGORY_ID + ","
					+ ItemCategoryContract.IMAGE_PATH + ","

					+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
					+ ItemCategoryContract.IS_ABSTRACT + ","

					+ ItemCategoryContract.IS_LEAF_NODE + ") VALUES("
					+ "'" + itemCategory.getCategoryName()	+ "'," 
					+ "'" + itemCategory.getCategoryDescription() + "',"
					+ "" + itemCategory.getParentCategoryID() + ","
					+ "'" + itemCategory.getImagePath() + "',"

					+ "'" + itemCategory.getDescriptionShort() + "',"
					+ "'" + itemCategory.getisAbstractNode() + "',"

					+ "'" + itemCategory.getIsLeafNode() + "'"
					+ ")";
		}
		
		
		if(itemCategory.getParentCategoryID() == null)
		{
			
			insertItemCategory = "INSERT INTO "
					+ ItemCategoryContract.TABLE_NAME				
					+ "("  
					+ ItemCategoryContract.ITEM_CATEGORY_NAME + ","
					+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","
					+ ItemCategoryContract.PARENT_CATEGORY_ID + ","
					+ ItemCategoryContract.IMAGE_PATH + ","

					+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
					+ ItemCategoryContract.IS_ABSTRACT + ","

					+ ItemCategoryContract.IS_LEAF_NODE + ") VALUES("
					+ "'" + itemCategory.getCategoryName()	+ "'," 
					+ "'" + itemCategory.getCategoryDescription() + "',"
					+ "" + "NULL" + ","
					+ "'" + itemCategory.getImagePath() + "',"

					+ "'" + itemCategory.getDescriptionShort() + "',"
					+ "'" + itemCategory.getisAbstractNode() + "',"

					+ "'" + itemCategory.getIsLeafNode() + "'"
					+ ")";
		
		}
		
		
		int idOfInsertedRow = 0;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
		
			
			
			rowCount = stmt.executeUpdate(insertItemCategory,Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if(rs.next())
			{
				idOfInsertedRow = rs.getInt(1);
			}
			
			
			
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
		
		return idOfInsertedRow;
	}

	

	public int updateItemCategory(ItemCategory itemCategory)
	{
		int rowCount = 0;



		if(itemCategory.getParentCategoryID() == itemCategory.getItemCategoryID())
		{

			// an Item category cannot have itself as its own parent so abort this operation and return

			return 0;
		}
		
		System.out.println("isLeaf : " + itemCategory.getIsLeafNode());
		
		String updateStatement = "";
		
		
		if(itemCategory.getParentCategoryID()!=null)
		{

			if(itemCategory.getParentCategoryID()==-1)
			{
				updateStatement = "UPDATE "

						+ ItemCategoryContract.TABLE_NAME

						+ " SET "

						+ ItemCategoryContract.ITEM_CATEGORY_NAME + " = " + "'" + itemCategory.getCategoryName() + "',"
						+ " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + " = " + "'" + itemCategory.getCategoryDescription() + "',"
						+ " " + ItemCategoryContract.IMAGE_PATH + " = " + "'" + itemCategory.getImagePath() + "',"
						+ " " + ItemCategoryContract.PARENT_CATEGORY_ID + " = " + "" + "NULL" + ","

						+ " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT + " = " + "'" + itemCategory.getDescriptionShort() + "',"
						+ " " + ItemCategoryContract.IS_ABSTRACT + " = " + "'" + itemCategory.getisAbstractNode() + "',"

						+ " " + ItemCategoryContract.IS_LEAF_NODE + " = " + "'" + itemCategory.getIsLeafNode() + "'"

						+ " WHERE " +  ItemCategoryContract.ITEM_CATEGORY_ID + "="
						+ itemCategory.getItemCategoryID();

			}else
			{
				updateStatement = "UPDATE "

						+ ItemCategoryContract.TABLE_NAME

						+ " SET "

						+ ItemCategoryContract.ITEM_CATEGORY_NAME + " = " + "'" + itemCategory.getCategoryName() + "',"
						+ " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + " = " + "'" + itemCategory.getCategoryDescription() + "',"
						+ " " + ItemCategoryContract.IMAGE_PATH + " = " + "'" + itemCategory.getImagePath() + "',"
						+ " " + ItemCategoryContract.PARENT_CATEGORY_ID + " = " + "" + itemCategory.getParentCategoryID() + ","

						+ " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT + " = " + "'" + itemCategory.getDescriptionShort() + "',"
						+ " " + ItemCategoryContract.IS_ABSTRACT + " = " + "'" + itemCategory.getisAbstractNode() + "',"

						+ " " + ItemCategoryContract.IS_LEAF_NODE + " = " + "'" + itemCategory.getIsLeafNode() + "'"

						+ " WHERE " +  ItemCategoryContract.ITEM_CATEGORY_ID + "="
						+ itemCategory.getItemCategoryID();
			}

		}
		else
		{
			
			updateStatement = "UPDATE " 
					
				+ ItemCategoryContract.TABLE_NAME 
				
				+ " SET "

				+ ItemCategoryContract.ITEM_CATEGORY_NAME + " = " + "'" + itemCategory.getCategoryName() + "',"
				+ " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + " = " + "'" + itemCategory.getCategoryDescription() + "',"
				+ " " + ItemCategoryContract.IMAGE_PATH + " = " + "'" + itemCategory.getImagePath() + "',"
				+ " " + ItemCategoryContract.PARENT_CATEGORY_ID + " = " + "" + "NULL" + ","

				+ " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT + " = " + "'" + itemCategory.getDescriptionShort() + "',"
				+ " " + ItemCategoryContract.IS_ABSTRACT + " = " + "'" + itemCategory.getisAbstractNode() + "',"

					+ " " + ItemCategoryContract.IS_LEAF_NODE + " = " + "'" + itemCategory.getIsLeafNode() + "'"
				
				+ " WHERE " +  ItemCategoryContract.ITEM_CATEGORY_ID + "="
				+ itemCategory.getItemCategoryID();
			
		}
		
		
		
		
		
		Connection conn = null;
		Statement stmt = null; 
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
					,JDBCContract.CURRENT_USERNAME
					,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCount = stmt.executeUpdate(updateStatement);
			
			
			System.out.println("Total rows updated: " + rowCount);	
			
			
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


	

	public int deleteItemCategory(int itemCategoryID)
	{
		
		String deleteStatement = "DELETE FROM ITEM_CATEGORY WHERE ID = " 
				+ itemCategoryID;
		
		
		Connection conn= null;
		Statement stmt = null;
		int rowCountDeleted = 0;
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
					,JDBCContract.CURRENT_USERNAME
					,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCountDeleted = stmt.executeUpdate(deleteStatement);
			
			System.out.println("row Count Deleted: " + rowCountDeleted);	
			
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




	
	
	public ArrayList<ItemCategory> getItemCategories(
			Integer shopID, Integer parentID, Boolean parentIsNull,
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin, Double deliveryRangeMax,
			Double proximity,
			String sortBy,
			Integer limit, Integer offset)
	{

		String query = "";
		
		String queryNormal = "SELECT * FROM " + ItemCategoryContract.TABLE_NAME;


		boolean queryNormalFirst = true;

		if(parentID!=null)
		{
			queryNormal = queryNormal + " WHERE " 
					+ ItemCategoryContract.PARENT_CATEGORY_ID 
					+ "=" + parentID ;

			queryNormalFirst = false;
		}


		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = ItemCategoryContract.PARENT_CATEGORY_ID + " IS NULL";

			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryNormalPart;

			}else
			{
				queryNormal = queryNormal + " AND " + queryNormalPart;

			}
		}
		
		
		
		// a recursive CTE (Common table Expression) query. This query is used for retrieving hierarchical / tree set data. 
		
		
		String withRecursiveStart = "WITH RECURSIVE category_tree(" 
					+ ItemCategoryContract.ITEM_CATEGORY_ID + ","
					+ ItemCategoryContract.PARENT_CATEGORY_ID + "," 
					+ ItemCategoryContract.IMAGE_PATH + ","
					+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","

					+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
					+ ItemCategoryContract.IS_ABSTRACT + ","

					+ ItemCategoryContract.IS_LEAF_NODE + ","
					+ ItemCategoryContract.ITEM_CATEGORY_NAME
					+ ") AS (";
		
		
		String queryJoin = "SELECT DISTINCT " 
		
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_ID + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.PARENT_CATEGORY_ID + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.IMAGE_PATH + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","

				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.IS_ABSTRACT + ","

				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.IS_LEAF_NODE + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_NAME
				
				
				+ " FROM " 
				+ ShopContract.TABLE_NAME  + "," + ShopItemContract.TABLE_NAME + "," 
				+ ItemContract.TABLE_NAME + "," + ItemCategoryContract.TABLE_NAME
				+ " WHERE " 
				+ ShopContract.TABLE_NAME + "." + ShopContract.SHOP_ID 
				+ "="
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.SHOP_ID
				+ " AND "
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.ITEM_ID
				+ "="
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_ID
				+ " AND "
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_CATEGORY_ID
				+ "="
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_ID;
		
		

		if(shopID!=null)
		{
				queryJoin = queryJoin + " AND "
						+ ShopContract.TABLE_NAME 
						+ "."
						+ ShopContract.SHOP_ID + " = " + shopID; 	
			
		}
		
		if(latCenter!=null && lonCenter!=null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.



			String queryPartlatLonCenterTwo = "";

			queryPartlatLonCenterTwo = queryPartlatLonCenterTwo
					+ " AND "
					+ ShopContract.TABLE_NAME
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

			//+ " BETWEEN " + latmax + " AND " + latmin;

			String queryPartlatLonCenter = "";

			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";


			queryJoin = queryJoin + " AND " + queryPartlatLonCenter;


		}



		if(deliveryRangeMax !=null || deliveryRangeMin != null)
		{

			// apply delivery range filter
			queryJoin = queryJoin
					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
		}


		// proximity cannot be greater than the delivery range if the delivery range is supplied. Otherwise this condition is
		// not required.
		if(proximity != null)
		{

			/*

			(proximity != null) &&
				(deliveryRangeMax == null || (deliveryRangeMax !=null && proximity <= deliveryRangeMax))

			 */


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

			String queryPartProximityHaversine = "";
			String queryPartProximityBounding = "";


			queryPartProximityBounding = queryPartProximityBounding

					+ " AND "
					+ ShopContract.TABLE_NAME
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
			queryPartProximityHaversine = queryPartProximityHaversine
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


			queryJoin = queryJoin + " AND " + queryPartProximityHaversine;

		}

		
		String union = " UNION ";
		
		String querySelect = " SELECT "
				
				+ "cat." + ItemCategoryContract.ITEM_CATEGORY_ID + ","
				+ "cat." + ItemCategoryContract.PARENT_CATEGORY_ID + ","
				+ "cat." + ItemCategoryContract.IMAGE_PATH + ","
				+ "cat." + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","

				+ "cat." + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ "cat." + ItemCategoryContract.IS_ABSTRACT + ","

				+ "cat." + ItemCategoryContract.IS_LEAF_NODE + ","
				+ "cat." + ItemCategoryContract.ITEM_CATEGORY_NAME

				+ " FROM category_tree tempCat," + 	ItemCategoryContract.TABLE_NAME + " cat"
				+ " WHERE cat." + ItemCategoryContract.ITEM_CATEGORY_ID
				+ " = tempcat." + ItemCategoryContract.PARENT_CATEGORY_ID
				+ " )";
		
		
		String queryLast = " SELECT "
				+ ItemCategoryContract.ITEM_CATEGORY_ID + ","
				+ ItemCategoryContract.PARENT_CATEGORY_ID + ","
				+ ItemCategoryContract.IMAGE_PATH + ","
				+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","

				+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ ItemCategoryContract.IS_ABSTRACT + ","

				+ ItemCategoryContract.IS_LEAF_NODE + ","
				+ ItemCategoryContract.ITEM_CATEGORY_NAME
				+ " FROM category_tree";

		
		if(parentID!=null)
		{
			queryLast = queryLast + " WHERE " 
					+ ItemCategoryContract.PARENT_CATEGORY_ID 
					+ "=" + parentID ; 
		}
		
		String queryRecursive = withRecursiveStart + queryJoin + union + querySelect +  queryLast;




		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				queryNormal = queryNormal + queryPartSortBy;
				queryRecursive = queryRecursive + queryPartSortBy;
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
			queryRecursive = queryRecursive + queryPartLimitOffset;
		}


		
		if(shopID==null && latCenter == null && lonCenter == null)
		{
			query = queryNormal;
			
		}else
		{
			query = queryRecursive;
		}



//		System.out.println(query);
		
		
		ArrayList<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();
		
		
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
				ItemCategory itemCategory = new ItemCategory();
				
				itemCategory.setItemCategoryID(rs.getInt(ItemCategoryContract.ITEM_CATEGORY_ID));
				itemCategory.setParentCategoryID(rs.getInt(ItemCategoryContract.PARENT_CATEGORY_ID));
				itemCategory.setIsLeafNode(rs.getBoolean(ItemCategoryContract.IS_LEAF_NODE));
				itemCategory.setImagePath(rs.getString(ItemCategoryContract.IMAGE_PATH));
				itemCategory.setCategoryName(rs.getString(ItemCategoryContract.ITEM_CATEGORY_NAME));

				itemCategory.setisAbstractNode(rs.getBoolean(ItemCategoryContract.IS_ABSTRACT));
				itemCategory.setDescriptionShort(rs.getString(ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT));

				itemCategory.setCategoryDescription(rs.getString(ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION));
				itemCategoryList.add(itemCategory);		
			}



			if(parentIsNull!=null&& parentIsNull)
			{
				// exclude the root category
				for(ItemCategory itemCategory : itemCategoryList)
				{
					if(itemCategory.getItemCategoryID()==1)
					{
						itemCategoryList.remove(itemCategory);
						break;
					}
				}
			}


//			conn.close();

			System.out.println("Total itemCategories queried " + itemCategoryList.size());	
			
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
		
		return itemCategoryList;
	}



	public ItemCategoryEndPoint getEndPointMetadata(
			Integer shopID, Integer parentID, Boolean parentIsNull,
			Double latCenter, Double lonCenter,
			Double deliveryRangeMin, Double deliveryRangeMax,
			Double proximity)
	{

		String query = "";

		String queryNormal = "SELECT * FROM " + ItemCategoryContract.TABLE_NAME;


		boolean queryNormalFirst = true;

		if(parentID!=null)
		{
			queryNormal = queryNormal + " WHERE "
					+ ItemCategoryContract.PARENT_CATEGORY_ID
					+ "=" + parentID ;

			queryNormalFirst = false;
		}


		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = ItemCategoryContract.PARENT_CATEGORY_ID + " IS NULL";

			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryNormalPart;

			}else
			{
				queryNormal = queryNormal + " AND " + queryNormalPart;

			}
		}



		// a recursive CTE (Common table Expression) query. This query is used for retrieving hierarchical / tree set data.


		String withRecursiveStart = "WITH RECURSIVE category_tree("
				+ ItemCategoryContract.ITEM_CATEGORY_ID + ","
				+ ItemCategoryContract.PARENT_CATEGORY_ID
				+ ") AS (";


		String queryJoin = "SELECT DISTINCT "

				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_ID + ","
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.PARENT_CATEGORY_ID

				+ " FROM "
				+ ShopContract.TABLE_NAME  + "," + ShopItemContract.TABLE_NAME + ","
				+ ItemContract.TABLE_NAME + "," + ItemCategoryContract.TABLE_NAME
				+ " WHERE "
				+ ShopContract.TABLE_NAME + "." + ShopContract.SHOP_ID
				+ "="
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.SHOP_ID
				+ " AND "
				+ ShopItemContract.TABLE_NAME + "." + ShopItemContract.ITEM_ID
				+ "="
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_ID
				+ " AND "
				+ ItemContract.TABLE_NAME + "." + ItemContract.ITEM_CATEGORY_ID
				+ "="
				+ ItemCategoryContract.TABLE_NAME + "." + ItemCategoryContract.ITEM_CATEGORY_ID;



		if(shopID!=null)
		{
			queryJoin = queryJoin + " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.SHOP_ID + " = " + shopID;

		}

		if(latCenter!=null && lonCenter!=null)
		{
			// Applying shop visibility filter. Gives all the shops which are visible at the given location defined by
			// latCenter and lonCenter. For more information see the API documentation.


			String queryPartlatLonCenter = "";

			queryPartlatLonCenter = queryPartlatLonCenter + " 6371.01 * acos( cos( radians("
					+ latCenter + ")) * cos( radians( lat_center) ) * cos(radians( lon_center ) - radians("
					+ lonCenter + "))"
					+ " + sin( radians(" + latCenter + ")) * sin(radians(lat_center))) <= delivery_range ";


			queryJoin = queryJoin + " AND " + queryPartlatLonCenter;


		}



		if(deliveryRangeMax !=null || deliveryRangeMin != null)
		{

			// apply delivery range filter
			queryJoin = queryJoin
					+ " AND "
					+ ShopContract.TABLE_NAME
					+ "."
					+ ShopContract.DELIVERY_RANGE
					+ " BETWEEN " + deliveryRangeMin + " AND " + deliveryRangeMax;
		}


		// proximity cannot be greater than the delivery range if the delivery range is supplied. Otherwise this condition is
		// not required.
		if(proximity != null)
		{


			String queryPartProximityHaversine = "";


			// filter using Haversine formula using SQL math functions
			queryPartProximityHaversine = queryPartProximityHaversine
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


			queryJoin = queryJoin + " AND " + queryPartProximityHaversine;

		}


		String union = " UNION ";

		String querySelect = " SELECT "

				+ "cat." + ItemCategoryContract.ITEM_CATEGORY_ID + ","
				+ "cat." + ItemCategoryContract.PARENT_CATEGORY_ID

				+ " FROM category_tree tempCat," + 	ItemCategoryContract.TABLE_NAME + " cat"
				+ " WHERE cat." + ItemCategoryContract.ITEM_CATEGORY_ID
				+ " = tempcat." + ItemCategoryContract.PARENT_CATEGORY_ID
				+ " )";


		String queryLast = " SELECT "
				+ ItemCategoryContract.ITEM_CATEGORY_ID + ","
				+ ItemCategoryContract.PARENT_CATEGORY_ID
				+ " FROM category_tree";


		if(parentID!=null)
		{
			queryLast = queryLast + " WHERE "
					+ ItemCategoryContract.PARENT_CATEGORY_ID
					+ "=" + parentID ;
		}

		String queryRecursive = withRecursiveStart + queryJoin + union + querySelect +  queryLast;



		if(shopID==null && latCenter == null && lonCenter == null)
		{
			query = queryNormal;

		}else
		{
			query = queryRecursive;
		}



//		System.out.println(query);


		ArrayList<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();


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
				ItemCategory itemCategory = new ItemCategory();

				itemCategory.setItemCategoryID(rs.getInt(ItemCategoryContract.ITEM_CATEGORY_ID));
				itemCategory.setParentCategoryID(rs.getInt(ItemCategoryContract.PARENT_CATEGORY_ID));

				itemCategoryList.add(itemCategory);
			}



			if(parentIsNull!=null&& parentIsNull)
			{
				// exclude the root category
				for(ItemCategory itemCategory : itemCategoryList)
				{
					if(itemCategory.getItemCategoryID()==1)
					{
						itemCategoryList.remove(itemCategory);
						break;
					}
				}
			}




//			conn.close();

			System.out.println("Total itemCategories queried " + itemCategoryList.size());

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

		return null;
	}


	public ItemCategoryEndPoint getEndPointMetaDataTwo(Integer parentID, Boolean parentIsNull)
	{

		String query = "";

		String queryNormal = "SELECT count(*) as item_count FROM " + ItemCategoryContract.TABLE_NAME;


		boolean queryNormalFirst = true;

		if(parentID!=null)
		{
			queryNormal = queryNormal + " WHERE "
					+ ItemCategoryContract.PARENT_CATEGORY_ID
					+ "=" + parentID ;

			queryNormalFirst = false;
		}


		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = ItemCategoryContract.PARENT_CATEGORY_ID + " IS NULL";

			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryNormalPart;

			}else
			{
				queryNormal = queryNormal + " AND " + queryNormalPart;

			}
		}


			query = queryNormal;




		ItemCategoryEndPoint endPoint = new ItemCategoryEndPoint();


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
				endPoint.setItemCount(rs.getInt("item_count"));
			}


			if(parentIsNull!=null&& parentIsNull)
			{
				// exclude the root category
				endPoint.setItemCount(endPoint.getItemCount()-1);
			}


			System.out.println("Item Category EndPoint call count :  " + endPoint.getItemCount());

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

		return endPoint;
	}




	public ItemCategory getItemCategory(int itemCategoryID)
	{
		
		String query = "SELECT * FROM " 
				+ ItemCategoryContract.TABLE_NAME 
				+ " WHERE " +  ItemCategoryContract.ITEM_CATEGORY_ID +  "= " + itemCategoryID;
		
		
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		ItemCategory itemCategory = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				itemCategory = new ItemCategory();
				itemCategory.setItemCategoryID(rs.getInt(ItemCategoryContract.ITEM_CATEGORY_ID));
				itemCategory.setCategoryName(rs.getString(ItemCategoryContract.ITEM_CATEGORY_NAME));
				itemCategory.setCategoryDescription(rs.getString(ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION));
				itemCategory.setParentCategoryID(rs.getInt(ItemCategoryContract.PARENT_CATEGORY_ID));
				itemCategory.setIsLeafNode(rs.getBoolean(ItemCategoryContract.IS_LEAF_NODE));

				itemCategory.setisAbstractNode(rs.getBoolean(ItemCategoryContract.IS_ABSTRACT));
				itemCategory.setDescriptionShort(rs.getString(ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION_SHORT));

				itemCategory.setImagePath(rs.getString(ItemCategoryContract.IMAGE_PATH));
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
	
		return itemCategory;
	}
	
}
