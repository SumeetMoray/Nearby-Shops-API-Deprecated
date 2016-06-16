	package org.nearbyshops.application;


import org.nearbyshops.ContractClasses.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.ModelStats.DeliveryVehicleSelf;


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


		    	
		    	String createTableItemPostgres = "CREATE TABLE IF NOT EXISTS " 
		    			+ ItemContract.TABLE_NAME + "("
		        		+ " " + ItemContract.ITEM_ID + " SERIAL PRIMARY KEY,"
		        		+ " " + ItemContract.ITEM_NAME + " VARCHAR(40),"
		        		+ " " + ItemContract.ITEM_DESC + " VARCHAR(30),"
						+ " " + ItemContract.ITEM_DESCRIPTION_LONG + " VARCHAR(500),"
		        		+ " " + ItemContract.ITEM_IMAGE_URL + " VARCHAR(100),"
						+ " " + ItemContract.QUANTITY_UNIT + " VARCHAR(40),"
		        		+ " " + ItemContract.ITEM_CATEGORY_ID + " INT,"
						+ " " + ItemContract.DATE_TIME_CREATED + "  timestamp with time zone NOT NULL DEFAULT now(),"
		        		+ " FOREIGN KEY(" + ItemContract.ITEM_CATEGORY_ID +") REFERENCES ITEM_CATEGORY(ID))";
		    	
		    	
		        stmt.executeUpdate(createTableItemPostgres); 
		        


		    	String createTableDistributorPostgres = "CREATE TABLE IF NOT EXISTS " 
		    			+ DistributorContract.TABLE_NAME + "("
		        		+ " " + DistributorContract.DISTRIBUTOR_ID + " SERIAL PRIMARY KEY,"
		        		+ " " + DistributorContract.DISTRIBUTOR_NAME + " VARCHAR(40)"
		        		+ ")";
		    	
		        stmt.executeUpdate(createTableDistributorPostgres); 



		    	
		    	
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
						+ " " + ShopContract.SHOP_ADDRESS + " VARCHAR(100),"
						+ " " + ShopContract.CITY + " VARCHAR(20),"
						+ " " + ShopContract.PINCODE + " INT,"
						+ " " + ShopContract.LANDMARK + " VARCHAR(100),"
						+ " " + ShopContract.BILL_AMOUNT_FOR_FREE_DELIVERY + " INT,"
						+ " " + ShopContract.CUSTOMER_HELPLINE_NUMBER + " VARCHAR(30),"
						+ " " + ShopContract.DELIVERY_HELPLINE_NUMBER + " VARCHAR(30),"
						+ " " + ShopContract.SHORT_DESCRIPTION + " VARCHAR(40),"
						+ " " + ShopContract.LONG_DESCRIPTION + " VARCHAR(500),"
						+ " " + ShopContract.DATE_TIME_STARTED + " timestamp with time zone NOT NULL DEFAULT now(),"
						+ " " + ShopContract.IS_OPEN + " boolean,"
		        		+ " FOREIGN KEY(" + ShopContract.DISTRIBUTOR_ID +") REFERENCES DISTRIBUTOR(ID))";

		        stmt.executeUpdate(createTableShopPostgres);




		    	String createTableShopItemPostgres = "CREATE TABLE IF NOT EXISTS " + ShopItemContract.TABLE_NAME + "("
		        		+ " " + ShopItemContract.ITEM_ID + " INT,"
		        		+ " " + ShopItemContract.SHOP_ID + " INT,"
		        		+ " " + ShopItemContract.AVAILABLE_ITEM_QUANTITY + " INT,"
		        		+ " " + ShopItemContract.ITEM_PRICE + " FLOAT,"
						+ " " + ShopItemContract.LAST_UPDATE_DATE_TIME + " timestamp with time zone,"
						+ " " + ShopItemContract.EXTRA_DELIVERY_CHARGE + " FLOAT,"
						+ " " + ShopItemContract.DATE_TIME_ADDED + " timestamp with time zone NOT NULL DEFAULT now(),"
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


			// create Delivery Address

			String createTableDeliveryAddressPostgres = "CREATE TABLE IF NOT EXISTS " + DeliveryAddressContract.TABLE_NAME + "("
					+ " " + DeliveryAddressContract.ID + " SERIAL PRIMARY KEY,"
					+ " " + DeliveryAddressContract.END_USER_ID + " INT,"
					+ " " + DeliveryAddressContract.CITY + " VARCHAR(40),"
					+ " " + DeliveryAddressContract.DELIVERY_ADDRESS + " VARCHAR(100),"
					+ " " + DeliveryAddressContract.LANDMARK + " VARCHAR(100),"
					+ " " + DeliveryAddressContract.NAME + " VARCHAR(100),"
					+ " " + DeliveryAddressContract.PHONE_NUMBER + " BIGINT,"
					+ " " + DeliveryAddressContract.PINCODE + " BIGINT,"
					+ " FOREIGN KEY(" + DeliveryAddressContract.END_USER_ID +") REFERENCES " + EndUserContract.TABLE_NAME + "(" + EndUserContract.END_USER_ID + ")"
					+ ")";


			stmt.executeUpdate(createTableDeliveryAddressPostgres);



			// Create table Delivery Vehicle Self


			String createtableDeliveryVehicleSelfPostgres = "CREATE TABLE IF NOT EXISTS " + DeliveryVehicleSelfContract.TABLE_NAME + "("
					+ " " + DeliveryVehicleSelfContract.ID + " SERIAL PRIMARY KEY,"
					+ " " + DeliveryVehicleSelfContract.VEHICLE_NAME + " VARCHAR(30),"
					+ " " + DeliveryVehicleSelfContract.SHOP_ID + " INT,"
					+ " FOREIGN KEY(" + DeliveryVehicleSelfContract.SHOP_ID +") REFERENCES " + ShopContract.TABLE_NAME + "(" + ShopContract.SHOP_ID + ")"
					+ ")";


			stmt.executeUpdate(createtableDeliveryVehicleSelfPostgres);




			// Create Table Order In postgres

			String createTableOrderPostgres = "CREATE TABLE IF NOT EXISTS " + OrderContract.TABLE_NAME + "("
					+ " " + OrderContract.ORDER_ID + " SERIAL PRIMARY KEY,"
					+ " " + OrderContract.END_USER_ID + " INT,"
					+ " " + OrderContract.SHOP_ID + " INT,"
					+ " " + OrderContract.STATUS_HOME_DELIVERY + " INT,"
					+ " " + OrderContract.STATUS_PICK_FROM_SHOP + " INT,"
					+ " " + OrderContract.DELIVERY_RECEIVED + " boolean,"
					+ " " + OrderContract.PAYMENT_RECEIVED + " boolean,"
					+ " " + OrderContract.DELIVERY_CHARGES + " INT,"
					+ " " + OrderContract.DELIVERY_ADDRESS_ID + " INT,"
					+ " " + OrderContract.PICK_FROM_SHOP + " boolean,"
					+ " " + OrderContract.DELIVERY_VEHICLE_SELF_ID + " INT,"
					+ " " + OrderContract.DATE_TIME_PLACED + " timestamp with time zone NOT NULL DEFAULT now(),"
					+ " FOREIGN KEY(" + OrderContract.END_USER_ID +") REFERENCES " + EndUserContract.TABLE_NAME + "(" + EndUserContract.END_USER_ID + "),"
					+ " FOREIGN KEY(" + OrderContract.SHOP_ID +") REFERENCES " + ShopContract.TABLE_NAME + "(" + ShopContract.SHOP_ID + "),"
					+ " FOREIGN KEY(" + OrderContract.DELIVERY_ADDRESS_ID +") REFERENCES " + DeliveryAddressContract.TABLE_NAME + "(" + DeliveryAddressContract.ID + "),"
					+ " FOREIGN KEY(" + OrderContract.DELIVERY_VEHICLE_SELF_ID +") REFERENCES " + DeliveryVehicleSelfContract.TABLE_NAME + "(" + DeliveryVehicleSelfContract.ID + ")"
					+ ")";


			stmt.executeUpdate(createTableOrderPostgres);


			// Create table OrderItem in Postgres
			String createtableOrderItemPostgres = "CREATE TABLE IF NOT EXISTS " + OrderItemContract.TABLE_NAME + "("
					+ " " + OrderItemContract.ITEM_ID + " INT,"
					+ " " + OrderItemContract.ORDER_ID + " INT,"
					+ " " + OrderItemContract.ITEM_PRICE_AT_ORDER + " FLOAT,"
					+ " " + OrderItemContract.ITEM_QUANTITY + " INT,"
					+ " FOREIGN KEY(" + OrderItemContract.ITEM_ID +") REFERENCES " + ItemContract.TABLE_NAME + "(" + ItemContract.ITEM_ID + "),"
					+ " FOREIGN KEY(" + OrderItemContract.ORDER_ID +") REFERENCES " + OrderContract.TABLE_NAME + "(" + OrderContract.ORDER_ID + "),"
					+ " PRIMARY KEY (" + OrderItemContract.ITEM_ID + ", " + OrderItemContract.ORDER_ID + "),"
					+ " UNIQUE (" + OrderItemContract.ITEM_ID + "," + OrderItemContract.ORDER_ID  + ")"
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



			System.out.println("Tables Created ... !");



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

