	package org.nearbyshops.application;


import org.nearbyshops.ContractClasses.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;


import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Main class.
 *
 */

public class Main implements ActionListener {
    // Base URI the Grizzly HTTP server will listen on
	
	//"http://localhost:8080/myapp/"
    public static final String BASE_URI = "http://0.0.0.0:5000/api";
    
    HttpServer server;
    
    boolean isServerStart = false;
    
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.sumeet.restsamples.Sample package
        final ResourceConfig rc = new ResourceConfig().packages("org.nearbyshops.RESTInterfaces","org.nearbyshops");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
    	//server = startServer();
    	Main main = new Main();
    	//main.go();
    	//main.hibernateTest();
    	
    	main.start();
    	//main.isServerStart = true;
    	
    
    }
    
    public void go()
    {

        JFrame frame = new JFrame();
        
        JButton button = new JButton("Start/Stop Server");
        JButton buttontwo = new JButton("Stop Server");
        
        button.addActionListener(this);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.NORTH,button);
        frame.getContentPane().add(BorderLayout.SOUTH, buttontwo);
        frame.setSize(300, 300);
        frame.setVisible(true);
    
    }
    

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(isServerStart == true)
        {
        	stopServer();
        }else if(isServerStart == false)
        {
        	start();
        }
	}
	
	
	
	public void start()
	{
		
		server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "\nHit enter to stop it...", BASE_URI));
        //System.in.read();
        isServerStart = true;
        
        createTables();

	}

	public void stopServer()
	{
		server.stop();
		isServerStart = false;
	}
	
	
	
	public void createTables()
	{
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
					,JDBCContract.CURRENT_USERNAME
					,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			//DatabaseMetaData dmd = conn.getMetaData(); 
			

			// create table ITEM_CATEGORY			
			//ResultSet rs = dmd.getTables(null,"public", ItemCategoryContract.TABLE_NAME,null); 
		    //System.out.println("Outside the create table : " + rs.next() + rs.getRow());
		    
		    //if (!rs.next()) { 
		    	
		    	
		    System.out.println("Into the create table");
		    	
		    	String query2 = "CREATE TABLE ITEM_CATEGORY("
		        		+ " ID INT PRIMARY KEY "
		        		+ "GENERATED ALWAYS AS IDENTITY"
		        		+ "(START WITH 1, INCREMENT BY 1),"
		        		+ " ITEM_CATEGORY_NAME VARCHAR(40),"
		        		+ " ITEM_CATEGORY_DESC VARCHAR(500)"
		        		+ ")";
		    	

		    	String createTableItemCategoryDerby = "CREATE TABLE "  
		    			+ ItemCategoryContract.TABLE_NAME + "("
		        		+ " " + ItemCategoryContract.ITEM_CATEGORY_ID + " INT PRIMARY KEY "
		        		+ "GENERATED ALWAYS AS IDENTITY"
		        		+ "(START WITH 1, INCREMENT BY 1),"
		        		+ " " + ItemCategoryContract.ITEM_CATEGORY_NAME + " VARCHAR(40),"
		        		+ " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + " VARCHAR(500)"
		        		+ ")";
		    	
		    	String createTableItemCategoryPostgres = "CREATE TABLE IF NOT EXISTS "  
		    			+ ItemCategoryContract.TABLE_NAME + "("
		        		+ " " + ItemCategoryContract.ITEM_CATEGORY_ID + " SERIAL PRIMARY KEY,"
		        		+ " " + ItemCategoryContract.ITEM_CATEGORY_NAME + " VARCHAR(40),"
		        		+ " " + ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + " VARCHAR(500),"
		        		+ " " + ItemCategoryContract.PARENT_CATEGORY_ID + " INT,"
		        		+ " " + ItemCategoryContract.IS_LEAF_NODE + " boolean,"
		        		+ " " + ItemCategoryContract.IMAGE_PATH + " VARCHAR(100),"
		        		+ " FOREIGN KEY(" + ItemCategoryContract.PARENT_CATEGORY_ID +") REFERENCES " 
		        		+ ItemCategoryContract.TABLE_NAME + "(" + ItemCategoryContract.ITEM_CATEGORY_ID + ")" 
		        		+ ")"; 
		    	
		        stmt.executeUpdate(createTableItemCategoryPostgres);


		    //} 
		    
			// create table ITEM
		    //rs = dmd.getTables(null,null, ItemContract.TABLE_NAME,null); 
		    System.out.println("Outside the create table");
		    
		    //if (!rs.next()) { 
		    	
		    	
		    System.out.println("Into the create table");
		    	
		    	String createTableItem = "CREATE TABLE ITEM("
		        		+ " ITEM_ID INT PRIMARY KEY "
		        		+ "GENERATED ALWAYS AS IDENTITY"
		        		+ "(START WITH 1, INCREMENT BY 1),"
		        		+ " ITEM_NAME VARCHAR(40),"
		        		+ " ITEM_DESC VARCHAR(500),"
		        		+ " ITEM_IMAGE_URL VARCHAR(100),"
		        		+ " ITEM_BRAND_NAME VARCHAR(100),"
		        		+ " ITEM_CATEGORY_ID INT,"
		        		+ " FOREIGN KEY(ITEM_CATEGORY_ID) REFERENCES ITEM_CATEGORY(ID))";
		    	
		    	String createTableItemDerby = "CREATE TABLE " 
		    			+ ItemContract.TABLE_NAME + "("
		        		+ " " + ItemContract.ITEM_ID + " INT PRIMARY KEY "
		        		+ "GENERATED ALWAYS AS IDENTITY"
		        		+ "(START WITH 1, INCREMENT BY 1),"
		        		+ " " + ItemContract.ITEM_NAME + " VARCHAR(40),"
		        		+ " " + ItemContract.ITEM_DESC + " VARCHAR(500),"
		        		+ " " + ItemContract.ITEM_IMAGE_URL + " VARCHAR(100),"
		        		+ " " + ItemContract.ITEM_BRAND_NAME + " VARCHAR(100),"
		        		+ " " + ItemContract.ITEM_CATEGORY_ID + " INT,"
		        		+ " FOREIGN KEY(" + ItemContract.ITEM_CATEGORY_ID +") REFERENCES ITEM_CATEGORY(ID))";
		    	
		    	String createTableItemPostgres = "CREATE TABLE IF NOT EXISTS " 
		    			+ ItemContract.TABLE_NAME + "("
		        		+ " " + ItemContract.ITEM_ID + " SERIAL PRIMARY KEY,"
		        		+ " " + ItemContract.ITEM_NAME + " VARCHAR(40),"
		        		+ " " + ItemContract.ITEM_DESC + " VARCHAR(500),"
		        		+ " " + ItemContract.ITEM_IMAGE_URL + " VARCHAR(100),"
		        		+ " " + ItemContract.ITEM_BRAND_NAME + " VARCHAR(100),"
						+ " " + ItemContract.QUANTITY_UNIT + " VARCHAR(40),"
		        		+ " " + ItemContract.ITEM_CATEGORY_ID + " INT,"
		        		+ " FOREIGN KEY(" + ItemContract.ITEM_CATEGORY_ID +") REFERENCES ITEM_CATEGORY(ID))";
		    	
		    	
		        stmt.executeUpdate(createTableItemPostgres); 
		        
		    //}
		    
		        //create table Distributor
		    //rs = dmd.getTables(null,null, DistributorContract.TABLE_NAME,null); 
		    //System.out.println("Outside the create table");
		    
		    //if (!rs.next()) { 
		    	
		    	
		    System.out.println("Into the create table");
		    	
		    	
		    	String createTableDistributorDerby = "CREATE TABLE " 
		    			+ DistributorContract.TABLE_NAME + "("
		        		+ " " + DistributorContract.DISTRIBUTOR_ID + " INT PRIMARY KEY "
		        		+ "GENERATED ALWAYS AS IDENTITY"
		        		+ "(START WITH 1, INCREMENT BY 1),"
		        		+ " " + DistributorContract.DISTRIBUTOR_NAME + " VARCHAR(40)"
		        		+ ")";
		    	

		    	String createTableDistributorPostgres = "CREATE TABLE IF NOT EXISTS " 
		    			+ DistributorContract.TABLE_NAME + "("
		        		+ " " + DistributorContract.DISTRIBUTOR_ID + " SERIAL PRIMARY KEY,"
		        		+ " " + DistributorContract.DISTRIBUTOR_NAME + " VARCHAR(40)"
		        		+ ")";
		    	
		        stmt.executeUpdate(createTableDistributorPostgres); 
		        
		    //}
		    
		    
		 // create table SHOP
		    //rs = dmd.getTables(null,null, ShopContract.TABLE_NAME,null); 
		    //System.out.println("Outside the create table");
		    
		    //if (!rs.next()) { 
		    	
		    	
		    System.out.println("Into the create table");
		    	
		    	
		    	String createTableShopDerby = "CREATE TABLE " + ShopContract.TABLE_NAME + "("
		        		+ " " + ShopContract.SHOP_ID + " INT PRIMARY KEY "
		        		+ "GENERATED ALWAYS AS IDENTITY"
		        		+ "(START WITH 1, INCREMENT BY 1),"
		        		+ " " + ShopContract.SHOP_NAME + " VARCHAR(40),"
		        		+ " " + ShopContract.DELIVERY_CHARGES + " FLOAT,"
		        		+ " " + ShopContract.DISTRIBUTOR_ID + " INT,"
		        		+ " " + ShopContract.IMAGE_PATH + " VARCHAR(60),"
		        		+ " FOREIGN KEY(" + ShopContract.DISTRIBUTOR_ID +") REFERENCES DISTRIBUTOR(ID))";
		    	
		    	
		    	String createTableShopPostgres = "CREATE TABLE IF NOT EXISTS " + ShopContract.TABLE_NAME + "("
		        		+ " " + ShopContract.SHOP_ID + " SERIAL PRIMARY KEY,"
		        		+ " " + ShopContract.SHOP_NAME + " VARCHAR(40),"
		        		+ " " + ShopContract.DELIVERY_RANGE + " FLOAT,"
		        		+ " " + ShopContract.LON_CENTER + " FLOAT,"
		        		+ " " + ShopContract.LAT_CENTER + " FLOAT,"
						+ " " + ShopContract.LON_MAX + " FLOAT,"
						+ " " + ShopContract.LAT_MAX + " FLOAT,"
						+ " " + ShopContract.LON_MIN + " FLOAT,"
						+ " " + ShopContract.LAT_MIN + " FLOAT,"
		        		+ " " + ShopContract.DELIVERY_CHARGES + " FLOAT,"
		        		+ " " + ShopContract.DISTRIBUTOR_ID + " INT,"
		        		+ " " + ShopContract.IMAGE_PATH + " VARCHAR(60),"
		        		+ " FOREIGN KEY(" + ShopContract.DISTRIBUTOR_ID +") REFERENCES DISTRIBUTOR(ID))";

		        stmt.executeUpdate(createTableShopPostgres);



		    System.out.println("Into the create table");



		    	String createTableShopItemDerby = "CREATE TABLE " + ShopItemContract.TABLE_NAME + "("
		        		+ " " + ShopItemContract.ITEM_ID + " INT,"
		        		+ " " + ShopItemContract.SHOP_ID + " INT,"
		        		+ " " + ShopItemContract.AVAILABLE_ITEM_QUANTITY + " INT,"
		        		+ " " + ShopItemContract.ITEM_PRICE + " FLOAT,"
		        		+ " FOREIGN KEY(" + ShopItemContract.SHOP_ID +") REFERENCES SHOP(SHOP_ID),"
		        		+ " FOREIGN KEY(" + ShopItemContract.ITEM_ID +") REFERENCES ITEM(ITEM_ID)," 
		        		+ " PRIMARY KEY (" + ShopItemContract.SHOP_ID + ", " + ShopItemContract.ITEM_ID + ")"
		        		+ ")";


					//+ " " + ShopItemContract.QUANTITY_UNIT + " VARCHAR(40),"
					//+ " " + ShopItemContract.QUANTITY_MULTIPLE + " INT,"


		    	String createTableShopItemPostgres = "CREATE TABLE IF NOT EXISTS " + ShopItemContract.TABLE_NAME + "("
		        		+ " " + ShopItemContract.ITEM_ID + " INT,"
		        		+ " " + ShopItemContract.SHOP_ID + " INT,"
		        		+ " " + ShopItemContract.AVAILABLE_ITEM_QUANTITY + " INT,"
		        		+ " " + ShopItemContract.ITEM_PRICE + " FLOAT,"
		        		+ " FOREIGN KEY(" + ShopItemContract.SHOP_ID +") REFERENCES SHOP(SHOP_ID),"
		        		+ " FOREIGN KEY(" + ShopItemContract.ITEM_ID +") REFERENCES ITEM(ITEM_ID)," 
		        		+ " PRIMARY KEY (" + ShopItemContract.SHOP_ID + ", " + ShopItemContract.ITEM_ID + ")"
		        		+ ")";
		    	
		    	
		        stmt.executeUpdate(createTableShopItemPostgres);



			// Create Table EndUser

			String createTableEndUserPostgres = "CREATE TABLE IF NOT EXISTS " + EndUserContract.TABLE_NAME + "("
					+ " " + EndUserContract.END_USER_ID + " SERIAL PRIMARY KEY,"
					+ " " + EndUserContract.END_USER_NAME + " VARCHAR(40)" + ")";

			stmt.executeUpdate(createTableEndUserPostgres);



			// Create Table Order In postgres

			String createTableOrderPostgres = "CREATE TABLE IF NOT EXISTS " + OrderContract.TABLE_NAME + "("
					+ " " + OrderContract.ORDER_ID + " SERIAL PRIMARY KEY,"
					+ " " + OrderContract.DELIVERY_CHARGES + " INT,"
					+ " " + OrderContract.ORDER_STATUS + " INT,"
					+ " " + OrderContract.END_USER_ID + " INT,"
					+ " " + OrderContract.SHOP_ID + " INT,"
					+ " FOREIGN KEY(" + OrderContract.END_USER_ID +") REFERENCES " + EndUserContract.TABLE_NAME + "(" + EndUserContract.END_USER_ID + "),"
					+ " FOREIGN KEY(" + OrderContract.SHOP_ID +") REFERENCES " + ShopContract.TABLE_NAME + "(" + ShopContract.SHOP_ID + ")"
					+ ")";

			//System.out.println("Into the create table");

			//System.out.println(createTableOrderPostgres);

			stmt.executeUpdate(createTableOrderPostgres);


			// Create table OrderItem in Postgres
			String createtableOrderItemPostgres = "CREATE TABLE IF NOT EXISTS " + OrderItemContract.TABLE_NAME + "("
					+ " " + OrderItemContract.ITEM_ID + " INT,"
					+ " " + OrderItemContract.ORDER_ID + " INT,"
					+ " " + OrderItemContract.ITEM_PRICE_AT_ORDER + " FLOAT,"
					+ " " + OrderItemContract.ITEM_QUANTITY + " INT,"
					+ " FOREIGN KEY(" + OrderItemContract.ITEM_ID +") REFERENCES " + ItemContract.TABLE_NAME + "(" + ItemContract.ITEM_ID + "),"
					+ " FOREIGN KEY(" + OrderItemContract.ORDER_ID +") REFERENCES " + OrderContract.TABLE_NAME + "(" + OrderContract.ORDER_ID + "),"
					+ " PRIMARY KEY (" + OrderItemContract.ITEM_ID + ", " + OrderItemContract.ORDER_ID + ")"
					+ ")";


			stmt.executeUpdate(createtableOrderItemPostgres);



			String createTableCartPostgres = "CREATE TABLE IF NOT EXISTS " + CartContract.TABLE_NAME + "("
					+ " " + CartContract.CART_ID + " SERIAL PRIMARY KEY,"
					+ " " + CartContract.END_USER_ID + " INT,"
					+ " " + CartContract.SHOP_ID + " INT,"
					+ " FOREIGN KEY(" + CartContract.END_USER_ID +") REFERENCES " + EndUserContract.TABLE_NAME + "(" + EndUserContract.END_USER_ID + "),"
					+ " FOREIGN KEY(" + CartContract.SHOP_ID +") REFERENCES " + ShopContract.TABLE_NAME + "(" + ShopContract.SHOP_ID + "),"
					+ " UNIQUE (" + CartContract.END_USER_ID + "," + CartContract.SHOP_ID + ")"
					+ ")";

			stmt.executeUpdate(createTableCartPostgres);

			String createtableCartItemPostgres = "CREATE TABLE IF NOT EXISTS " + CartItemContract.TABLE_NAME + "("
					+ " " + CartItemContract.ITEM_ID + " INT,"
					+ " " + CartItemContract.CART_ID + " INT,"
					+ " " + CartItemContract.ITEM_QUANTITY + " INT,"
					+ " FOREIGN KEY(" + CartItemContract.ITEM_ID +") REFERENCES " + ItemContract.TABLE_NAME + "(" + ItemContract.ITEM_ID + "),"
					+ " FOREIGN KEY(" + CartItemContract.CART_ID +") REFERENCES " + CartContract.TABLE_NAME + "(" + CartContract.CART_ID + "),"
					+ " PRIMARY KEY (" + CartItemContract.ITEM_ID + ", " + CartItemContract.CART_ID + ")"
					+ ")";


			stmt.executeUpdate(createtableCartItemPostgres);


			// Insert the root category whose ID is 1

			String insertItemCategory = "";

			// The root ItemCategory has id 1. If the root category does not exist then insert it.
			if(Globals.itemCategoryService.getItemCategory(1) == null)
			{

				insertItemCategory = "INSERT INTO "
						+ ItemCategoryContract.TABLE_NAME
						+ "("
						+ ItemCategoryContract.ITEM_CATEGORY_ID + ","
						+ ItemCategoryContract.ITEM_CATEGORY_NAME + ","
						+ ItemCategoryContract.PARENT_CATEGORY_ID + ","
						+ ItemCategoryContract.ITEM_CATEGORY_DESCRIPTION + ","
						+ ItemCategoryContract.IMAGE_PATH + ","
						+ ItemCategoryContract.IS_LEAF_NODE + ") VALUES("
						+ "" + "1"	+ ","
						+ "'" + "ROOT"	+ "',"
						+ "" + "NULL" + ","
						+ "'" + "This is the root Category. Do not modify it." + "',"
						+ "'" + " " + "',"
						+ "'" + "FALSE" + "'"
						+ ")";

				stmt.executeUpdate(insertItemCategory);

			}

			//ItemCategory itemCategory = new ItemCategory();
			//itemCategory.setCategoryName("Root");

			//Globals.itemCategoryService.saveItemCategory(itemCategory);


			//+ ItemCategoryContract.PARENT_CATEGORY_ID + ","

			// + "" + "NULL" + ","
	    	/*

	    	String queryPostgresAbstractCategory = "CREATE TABLE IF NOT EXISTS " + AbstractCategoryContract.TABLE_NAME + "("
	        		+ " " + AbstractCategoryContract.CATEGORY_ID + " SERIAL PRIMARY KEY,"
	        		+ " " + AbstractCategoryContract.CATEGORY_IMAGE_URL + " VARCHAR(100),"
	        		+ " " + AbstractCategoryContract.CATEGORY_NAME + " VARCHAR(40),"
	        		+ " " + AbstractCategoryContract.DEPTH + " INT,"
	        		+ " " + AbstractCategoryContract.PARENT_CATEGORY_ID + " INT,"
	        		+ " FOREIGN KEY(" + AbstractCategoryContract.PARENT_CATEGORY_ID +") REFERENCES " 
	        		+ AbstractCategoryContract.TABLE_NAME + "(" + AbstractCategoryContract.CATEGORY_ID + ")"
	        		+ ")";
	    	
	    	
	        int status = stmt.executeUpdate(queryPostgresAbstractCategory); 
	        
		    
	        System.out.println(queryPostgresAbstractCategory + "\n" + " Status : " + status);
	        
	        */

		   // String insert = "INSERT INTO SAMP (ITEM_NAME,ITEM_DESC) VALUES('Rakesh','Kumar')";
	        
	        //stmt.executeUpdate(insert);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
			
			
			// close the connection and statement object

			if(stmt !=null)
			{
				
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			

			if(conn!=null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
}

