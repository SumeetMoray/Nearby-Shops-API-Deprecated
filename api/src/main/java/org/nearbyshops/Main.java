	package org.nearbyshops;


import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ServerProperties;
import org.nearbyshops.ContractClasses.*;


import org.glassfish.jersey.server.ResourceConfig;
import org.nearbyshops.DAOPreparedSettings.SettingsDAOPrepared;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.*;
import org.nearbyshops.ModelReview.FavouriteShop;
import org.nearbyshops.ModelReview.ShopReview;
import org.nearbyshops.ModelRoles.*;
import org.nearbyshops.ModelSettings.ServiceConfiguration;
import org.nearbyshops.ModelSettings.Settings;

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
    
    private HttpServer server_;
    
    private boolean isServerStart = false;
    
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.sumeet.restsamples.Sample package
        final ResourceConfig rc = new ResourceConfig();
		rc.packages(true,"org.nearbyshops");

		// Now you can expect validation errors to be sent to the client.
    	rc.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
				// @ValidateOnExecution annotations on subclasses won't cause errors.
		rc.property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

		rc.register(GSONJersey.class);

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
		
		server_ = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "\nHit enter to stop it...", BASE_URI));
        //System.in.read();
        isServerStart = true;

		createDB();
        createTables();

	}

	public void stopServer()
	{
		server_.stop();
		isServerStart = false;
	}

	public void createDB()
	{

		Connection conn = null;
		Statement stmt = null;

		try {

			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres"
					, JDBCContract.CURRENT_USERNAME
					, JDBCContract.CURRENT_PASSWORD);

			stmt = conn.createStatement();

			String createDB = "CREATE DATABASE \"NearbyShopsDB\" WITH ENCODING='UTF8' OWNER=postgres CONNECTION LIMIT=-1";

			stmt.executeUpdate(createDB);

		}
		catch (SQLException e) {
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
	
	
	
	private void createTables()
	{
		
		Connection connection = null;
		Statement statement = null;
		
		try {
			
			connection = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
					,JDBCContract.CURRENT_USERNAME
					,JDBCContract.CURRENT_PASSWORD);

			statement = connection.createStatement();



		        statement.executeUpdate(ItemCategory.createTableItemCategoryPostgres);
		        statement.executeUpdate(Item.createTableItemPostgres);
		        statement.executeUpdate(Distributor.createTableDistributorPostgres);
				statement.executeUpdate(DistributorStaff.createTableDistributorStaffPostgres);

		        statement.executeUpdate(Shop.createTableShopPostgres);



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
		    	
		    	
		        statement.executeUpdate(createTableShopItemPostgres);



			statement.executeUpdate(EndUser.createTableEndUserPostgres);


			// Create Table Admin
			statement.executeUpdate(Admin.createTableAdminPostgres);
			// Create Table Staff
			statement.executeUpdate(Staff.createTableStaffPostgres);


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
					+ " FOREIGN KEY(" + DeliveryAddressContract.END_USER_ID +") REFERENCES " + EndUser.TABLE_NAME + "(" + EndUser.END_USER_ID + ")"
					+ ")";


			statement.executeUpdate(createTableDeliveryAddressPostgres);



			// Create table Delivery Vehicle Self


			String createtableDeliveryVehicleSelfPostgres = "CREATE TABLE IF NOT EXISTS " + DeliveryVehicleSelfContract.TABLE_NAME + "("
					+ " " + DeliveryVehicleSelfContract.ID + " SERIAL PRIMARY KEY,"
					+ " " + DeliveryVehicleSelfContract.VEHICLE_NAME + " VARCHAR(30),"
					+ " " + DeliveryVehicleSelfContract.SHOP_ID + " INT,"
					+ " FOREIGN KEY(" + DeliveryVehicleSelfContract.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + ")"
					+ ")";


			statement.executeUpdate(createtableDeliveryVehicleSelfPostgres);




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
					+ " FOREIGN KEY(" + OrderContract.END_USER_ID +") REFERENCES " + EndUser.TABLE_NAME + "(" + EndUser.END_USER_ID + "),"
					+ " FOREIGN KEY(" + OrderContract.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + "),"
					+ " FOREIGN KEY(" + OrderContract.DELIVERY_ADDRESS_ID +") REFERENCES " + DeliveryAddressContract.TABLE_NAME + "(" + DeliveryAddressContract.ID + "),"
					+ " FOREIGN KEY(" + OrderContract.DELIVERY_VEHICLE_SELF_ID +") REFERENCES " + DeliveryVehicleSelfContract.TABLE_NAME + "(" + DeliveryVehicleSelfContract.ID + ")"
					+ ")";



			statement.executeUpdate(createTableOrderPostgres);


			// Create table OrderItem in Postgres
			String createtableOrderItemPostgres = "CREATE TABLE IF NOT EXISTS " + OrderItemContract.TABLE_NAME + "("
					+ " " + OrderItemContract.ITEM_ID + " INT,"
					+ " " + OrderItemContract.ORDER_ID + " INT,"
					+ " " + OrderItemContract.ITEM_PRICE_AT_ORDER + " FLOAT,"
					+ " " + OrderItemContract.ITEM_QUANTITY + " INT,"
					+ " FOREIGN KEY(" + OrderItemContract.ITEM_ID +") REFERENCES " + Item.TABLE_NAME + "(" + Item.ITEM_ID + "),"
					+ " FOREIGN KEY(" + OrderItemContract.ORDER_ID +") REFERENCES " + OrderContract.TABLE_NAME + "(" + OrderContract.ORDER_ID + "),"
					+ " PRIMARY KEY (" + OrderItemContract.ITEM_ID + ", " + OrderItemContract.ORDER_ID + "),"
					+ " UNIQUE (" + OrderItemContract.ITEM_ID + "," + OrderItemContract.ORDER_ID  + ")"
					+ ")";

			statement.executeUpdate(createtableOrderItemPostgres);



			String createTableCartPostgres = "CREATE TABLE IF NOT EXISTS " + CartContract.TABLE_NAME + "("
					+ " " + CartContract.CART_ID + " SERIAL PRIMARY KEY,"
					+ " " + CartContract.END_USER_ID + " INT,"
					+ " " + CartContract.SHOP_ID + " INT,"
					+ " FOREIGN KEY(" + CartContract.END_USER_ID +") REFERENCES " + EndUser.TABLE_NAME + "(" + EndUser.END_USER_ID + "),"
					+ " FOREIGN KEY(" + CartContract.SHOP_ID +") REFERENCES " + Shop.TABLE_NAME + "(" + Shop.SHOP_ID + "),"
					+ " UNIQUE (" + CartContract.END_USER_ID + "," + CartContract.SHOP_ID + ")"
					+ ")";

			statement.executeUpdate(createTableCartPostgres);



			String createtableCartItemPostgres = "CREATE TABLE IF NOT EXISTS " + CartItemContract.TABLE_NAME + "("
					+ " " + CartItemContract.ITEM_ID + " INT,"
					+ " " + CartItemContract.CART_ID + " INT,"
					+ " " + CartItemContract.ITEM_QUANTITY + " INT,"
					+ " FOREIGN KEY(" + CartItemContract.ITEM_ID +") REFERENCES " + Item.TABLE_NAME + "(" + Item.ITEM_ID + "),"
					+ " FOREIGN KEY(" + CartItemContract.CART_ID +") REFERENCES " + CartContract.TABLE_NAME + "(" + CartContract.CART_ID + "),"
					+ " PRIMARY KEY (" + CartItemContract.ITEM_ID + ", " + CartItemContract.CART_ID + ")"
					+ ")";


			statement.executeUpdate(createtableCartItemPostgres);


			statement.executeUpdate(ServiceConfiguration.createTableServiceConfigurationPostgres);
			statement.executeUpdate(Settings.createTableSettingsPostgres);


			// tables for reviews

			statement.executeUpdate(ShopReview.createTableShopReviewPostgres);
			statement.executeUpdate(FavouriteShop.createTableFavouriteBookPostgres);


			System.out.println("Tables Created ... !");




			// developers Note: whenever adding a table please check that its dependencies are already created.



			// Insert the default administrator if it does not exit

			if(Globals.adminDAOPrepared.getAdmin().size()==0)
			{
				Admin defaultAdmin = new Admin();

				defaultAdmin.setPassword("password");
				defaultAdmin.setUsername("username");
				defaultAdmin.setAdministratorName("default name");

				Globals.adminDAOPrepared.saveAdmin(defaultAdmin);
			}




			// Insert the root category whose ID is 1

			String insertItemCategory = "";

			// The root ItemCategory has id 1. If the root category does not exist then insert it.
			if(Globals.itemCategoryDAO.getItemCategory(1) == null)
			{

				insertItemCategory = "INSERT INTO "
						+ ItemCategory.TABLE_NAME
						+ "("
						+ ItemCategory.ITEM_CATEGORY_ID + ","
						+ ItemCategory.ITEM_CATEGORY_NAME + ","
						+ ItemCategory.PARENT_CATEGORY_ID + ","
						+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
						+ ItemCategory.IMAGE_PATH + ","
						+ ItemCategory.IS_LEAF_NODE + ") VALUES("
						+ "" + "1"	+ ","
						+ "'" + "ROOT"	+ "',"
						+ "" + "NULL" + ","
						+ "'" + "This is the root Category. Do not modify it." + "',"
						+ "'" + " " + "',"
						+ "'" + "FALSE" + "'"
						+ ")";

				statement.executeUpdate(insertItemCategory);

			}



			// Insert Default Settings
			SettingsDAOPrepared settingsDAO = Globals.settingsDAOPrepared;
			if(settingsDAO.getServiceConfiguration()==null){
				settingsDAO.saveSettings(settingsDAO.getDefaultConfiguration());
			}




			// Insert Default Service Configuration

			String insertServiceConfig = "";

			if(Globals.serviceConfigurationDAO.getServiceConfiguration()==null)
			{

				ServiceConfiguration defaultConfiguration = new ServiceConfiguration();

				defaultConfiguration.setServiceLevel(GlobalConstants.SERVICE_LEVEL_CITY);
				defaultConfiguration.setServiceType(GlobalConstants.SERVICE_TYPE_NONPROFIT);
				defaultConfiguration.setServiceID(1);
				defaultConfiguration.setServiceName("DEFAULT_CONFIGURATION");

				Globals.serviceConfigurationDAO.saveService(defaultConfiguration);

/*
				insertServiceConfig = "INSERT INTO "
						+ ServiceConfiguration.TABLE_NAME
						+ "("
						+ ServiceConfiguration.SERVICE_CONFIGURATION_ID + ","
						+ ServiceConfiguration.SERVICE_NAME + ") VALUES ("
						+ "" + "1" + ","
						+ "'" + "ROOT_CONFIGURATION" + "')";


				stmt.executeUpdate(insertServiceConfig);*/
			}



			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
			
			
			// close the connection and statement object

			if(statement !=null)
			{
				
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			

			if(connection!=null)
			{
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
}

