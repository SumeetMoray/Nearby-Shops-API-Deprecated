package org.nearbyshops.model.Later;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.nearbyshops.contractClasses.AbstractCategoryContract;
import org.nearbyshops.contractClasses.JDBCContract;




public class AbstractCategoryService {
	

	
	
	public int saveAbstractCategory(AbstractCategory abstractCategory)
	{
		
		int rowCount = 0;	
		Connection conn = null;
		Statement stmt = null;
		
		String insertItemCategory = "";

		if(abstractCategory.getParentCategoryID()>0)
		{
		
		
		insertItemCategory = "INSERT INTO "
				+ AbstractCategoryContract.TABLE_NAME				
				+ "("  
				+ AbstractCategoryContract.CATEGORY_IMAGE_URL + "," 
				+ AbstractCategoryContract.CATEGORY_NAME + ","
				+ AbstractCategoryContract.DEPTH + ","
				+ AbstractCategoryContract.PARENT_CATEGORY_ID + "" 
				+ ") VALUES("
				+ "'" + abstractCategory.getImageURL()	+ "'," 
				+ "'" + abstractCategory.getCategoryName() + "'," 
				+ abstractCategory.getDepth() + ","
				+ abstractCategory.getParentCategoryID() + ")";
		
		}else if(abstractCategory.getParentCategoryID()== 0) 
		
		{
			
			insertItemCategory = "INSERT INTO "
					+ AbstractCategoryContract.TABLE_NAME				
					+ "("  
					+ AbstractCategoryContract.CATEGORY_IMAGE_URL + "," 
					+ AbstractCategoryContract.CATEGORY_NAME + ","
					+ AbstractCategoryContract.DEPTH + ","
					+ AbstractCategoryContract.PARENT_CATEGORY_ID + "" 
					+ ") VALUES("
					+ "'" + abstractCategory.getImageURL()	+ "'," 
					+ "'" + abstractCategory.getCategoryName() + "'," 
					+ abstractCategory.getDepth() + ","
					+ "NULL" + ")";
			
		
		}
		
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCount = stmt.executeUpdate(insertItemCategory);
			
			
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
	
	
	
	
	public int deleteAbstractCategory(int abstractCategoryID)
	{
	
		String deleteStatement = "DELETE FROM " + AbstractCategoryContract.TABLE_NAME + " WHERE " 
				+ AbstractCategoryContract.CATEGORY_ID + " = " 
				+ abstractCategoryID;
		
		
		Connection conn= null;
		Statement stmt = null;
		
		int rowCountDeleted = 0;
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
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
	
	
	
	public int updateAbstractCategory(AbstractCategory abstractCategory)
	{
		
		int rowCount = 0;
		
		String updateStatement = "";
		
		if(abstractCategory.getParentCategoryID()>0)
		{
		
		updateStatement = "UPDATE " 
				+ AbstractCategoryContract.TABLE_NAME + " SET " 
				+ AbstractCategoryContract.CATEGORY_IMAGE_URL + " = "
				+ "'" + abstractCategory.getImageURL() + "'"
				+ ","
				+ " " + AbstractCategoryContract.CATEGORY_NAME + " = "
				+ "'" + abstractCategory.getCategoryName() + "'" 
				+ ","
				+ " " + AbstractCategoryContract.DEPTH + " = "
				+ abstractCategory.getDepth()
				+ ","
				+ " " + AbstractCategoryContract.PARENT_CATEGORY_ID + " = "
				+ abstractCategory.getParentCategoryID()
				+ " WHERE " + AbstractCategoryContract.CATEGORY_ID + "="
				+ abstractCategory.getID();

		} else if(abstractCategory.getParentCategoryID()==0)
		
		{
			updateStatement = "UPDATE " 
					+ AbstractCategoryContract.TABLE_NAME + " SET " 
					+ AbstractCategoryContract.CATEGORY_IMAGE_URL + " = "
					+ "'" + abstractCategory.getImageURL() + "'"
					+ ","
					+ " " + AbstractCategoryContract.CATEGORY_NAME + " = "
					+ "'" + abstractCategory.getCategoryName() + "'" 
					+ ","
					+ " " + AbstractCategoryContract.DEPTH + " = "
					+ abstractCategory.getDepth()
					+ ","
					+ " " + AbstractCategoryContract.PARENT_CATEGORY_ID + " = "
					+ "NULL"
					+ " WHERE " + AbstractCategoryContract.CATEGORY_ID + "="
					+ abstractCategory.getID();

			
			
			
		}
		
		
		
		
		System.out.println(updateStatement);
		
		Connection conn = null;
		Statement stmt = null; 
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowCount = stmt.executeUpdate(updateStatement);
			
			
			System.out.println("Total rows updated: " + rowCount);	
			
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
		
		return rowCount;

	}
	
	
	
	
	
	public List<AbstractCategory> getCategories(int parentID)
	{
		
		String query = "";
		
		String queryNormal = "SELECT * FROM " + AbstractCategoryContract.TABLE_NAME ;
				
		String queryFilter = "SELECT * FROM " + AbstractCategoryContract.TABLE_NAME 
								+ " WHERE " + AbstractCategoryContract.CATEGORY_ID
								+ " = " + parentID;
		
		
		if(parentID>0)
		{
			query = queryFilter;
		}else
		{
			query = queryNormal;
		}
		
		
		ArrayList<AbstractCategory> abstractCategoryList = new ArrayList<AbstractCategory>();
		
		
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
	
				AbstractCategory abstractCategory = new AbstractCategory();
				
				abstractCategory.setCategoryName(rs.getString(AbstractCategoryContract.CATEGORY_NAME));
				abstractCategory.setDepth(rs.getInt(AbstractCategoryContract.DEPTH));
				abstractCategory.setID(rs.getInt(AbstractCategoryContract.CATEGORY_ID));
				abstractCategory.setImageURL(rs.getString(AbstractCategoryContract.CATEGORY_IMAGE_URL));
				abstractCategory.setParentCategoryID(rs.getInt(AbstractCategoryContract.PARENT_CATEGORY_ID));
				
				
				
				abstractCategoryList.add(abstractCategory);
							
			}
			
			
			conn.close();
			System.out.println("Total itemCategories queried " + abstractCategoryList.size());	
			
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
		
		return abstractCategoryList;
	}
	
	
	
	
	public AbstractCategory getCategory(int categoryID)
	{
		
	String query = "SELECT * FROM " 
					+ AbstractCategoryContract.TABLE_NAME
					+ " WHERE " 
					+ AbstractCategoryContract.CATEGORY_ID
					+ " = " + categoryID;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		AbstractCategory abstractCategory = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,
					JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				
				abstractCategory = new AbstractCategory();
				
				abstractCategory.setCategoryName(rs.getString(AbstractCategoryContract.CATEGORY_NAME));
				abstractCategory.setDepth(rs.getInt(AbstractCategoryContract.DEPTH));
				abstractCategory.setID(rs.getInt(AbstractCategoryContract.CATEGORY_ID));
				abstractCategory.setImageURL(rs.getString(AbstractCategoryContract.CATEGORY_IMAGE_URL));
				abstractCategory.setParentCategoryID(rs.getInt(AbstractCategoryContract.PARENT_CATEGORY_ID));
				
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
	

		return abstractCategory;
	}
	
	
}
