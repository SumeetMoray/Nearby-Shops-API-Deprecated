package org.nearbyshops.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.DAOs.*;
import org.nearbyshops.DAOsPrepared.ItemCategoryDAO;
import org.nearbyshops.DAOsPrepared.ItemDAO;
import org.nearbyshops.JDBCContract;


public class Globals {
	
	//public static ArrayList<ItemCategory> list = new ArrayList<ItemCategory>();
	
	public static ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
	public static ItemDAO itemDAO = new ItemDAO();
	public static DistributorService distributorService = new DistributorService();
	public static ShopService shopService = new ShopService();
	public static ShopItemService shopItemService = new ShopItemService();
	public static EndUserService endUserService = new EndUserService();
	public static CartService cartService = new CartService();
	public static CartItemService cartItemService = new CartItemService();
	public static CartStatsDAO cartStatsDAO = new CartStatsDAO();
	public static OrderService orderService = new OrderService();
	public static DeliveryAddressService deliveryAddressService = new DeliveryAddressService();
	public static OrderItemService orderItemService = new OrderItemService();
	public static DeliveryVehicleSelfDAO deliveryVehicleSelfDAO = new DeliveryVehicleSelfDAO();
	public static ServiceDAO serviceDAO = new ServiceDAO();
	public static ServiceProviderDAO serviceProviderDAO = new ServiceProviderDAO();
	public static ServiceConfigDAO serviceConfigDAO = new ServiceConfigDAO();


	// Configure Connection Pooling

	private static HikariDataSource dataSource;



	public static HikariDataSource getDataSource()
	{
		if(dataSource==null)
		{
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(JDBCContract.CURRENT_CONNECTION_URL);
			config.setUsername(JDBCContract.CURRENT_USERNAME);
			config.setPassword(JDBCContract.CURRENT_PASSWORD);

			dataSource = new HikariDataSource(config);
		}

		return dataSource;
	}




}
