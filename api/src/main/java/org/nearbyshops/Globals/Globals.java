package org.nearbyshops.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.DAOPreparedReview.FavoriteBookDAOPrepared;
import org.nearbyshops.DAOPreparedReview.ShopReviewDAOPrepared;
import org.nearbyshops.DAOPreparedReview.ShopReviewThanksDAOPrepared;
import org.nearbyshops.DAOPreparedSettings.ServiceConfigurationDAOPrepared;
import org.nearbyshops.DAOPreparedSettings.SettingsDAOPrepared;
import org.nearbyshops.DAOs.EndUserService;
import org.nearbyshops.DAOsPreparedRoles.*;
import org.nearbyshops.DAOsRemaining.*;
import org.nearbyshops.DAOsPrepared.*;
import org.nearbyshops.JDBCContract;


public class Globals {


	//public static ArrayList<ItemCategory> list = new ArrayList<ItemCategory>();
	
	public static ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
	public static ItemDAO itemDAO = new ItemDAO();
	public static ItemDAOJoinOuter itemDAOJoinOuter = new ItemDAOJoinOuter();
	public static DistributorDAOPrepared distributorDAOPrepared = new DistributorDAOPrepared();
	public static ShopDAO shopDAO = new ShopDAO();
//	public static ShopItemService shopItemService = new ShopItemService();
	public static EndUserService endUserService = new EndUserService();
	public static CartService cartService = new CartService();
	public static CartItemService cartItemService = new CartItemService();
	public static CartStatsDAO cartStatsDAO = new CartStatsDAO();
	public static OrderService orderService = new OrderService();
	public static DeliveryAddressService deliveryAddressService = new DeliveryAddressService();
	public static OrderItemService orderItemService = new OrderItemService();
	public static DeliveryVehicleSelfDAO deliveryVehicleSelfDAO = new DeliveryVehicleSelfDAO();
	public static ServiceConfigurationDAOPrepared serviceConfigurationDAO = new ServiceConfigurationDAOPrepared();
	public static SettingsDAOPrepared settingsDAOPrepared = new SettingsDAOPrepared();
	public static AdminDAOPrepared adminDAOPrepared = new AdminDAOPrepared();
	public static StaffDAOPrepared staffDAOPrepared = new StaffDAOPrepared();
	public static DistributorStaffDAOPrepared distributorStaffDAOPrepared = new DistributorStaffDAOPrepared();
	public static EndUserDAOPrepared endUserDAOPrepared = new EndUserDAOPrepared();


	public static ShopReviewDAOPrepared shopReviewDAOPrepared = new ShopReviewDAOPrepared();
	public static FavoriteBookDAOPrepared favoriteBookDAOPrepared = new FavoriteBookDAOPrepared();

	public static ShopReviewThanksDAOPrepared shopReviewThanksDAO = new ShopReviewThanksDAOPrepared();


	public static ShopItemByShopDAO shopItemByShopDAO = new ShopItemByShopDAO();
	public static ShopItemDAO shopItemDAO = new ShopItemDAO();
	public static ShopItemByItemDAO shopItemByItemDAO = new ShopItemByItemDAO();

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
