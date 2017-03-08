package org.nearbyshops.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.nearbyshops.DAOPreparedCartOrder.*;
import org.nearbyshops.DAOPreparedItemSpecification.*;
import org.nearbyshops.DAOPreparedOrderHomeDelivery.OrderItemService;
import org.nearbyshops.DAOPreparedOrderHomeDelivery.OrderService;
import org.nearbyshops.DAOPreparedOrderHomeDelivery.PlaceOrderHD_DAO;
import org.nearbyshops.DAOPreparedPickFromShop.OrderItemServicePFS;
import org.nearbyshops.DAOPreparedPickFromShop.OrderServicePFS;
import org.nearbyshops.DAOPreparedPickFromShop.PlaceOrderPFS_DAO;
import org.nearbyshops.DAOPreparedReviewItem.FavoriteItemDAOPrepared;
import org.nearbyshops.DAOPreparedReviewItem.ItemReviewDAOPrepared;
import org.nearbyshops.DAOPreparedReviewItem.ItemReviewThanksDAOPrepared;
import org.nearbyshops.DAOPreparedReviewShop.FavoriteBookDAOPrepared;
import org.nearbyshops.DAOPreparedReviewShop.ShopReviewDAOPrepared;
import org.nearbyshops.DAOPreparedReviewShop.ShopReviewThanksDAOPrepared;
import org.nearbyshops.DAOPreparedSecurity.ForbiddenOperationsDAO;
import org.nearbyshops.DAOPreparedSettings.ServiceConfigurationDAOPrepared;
import org.nearbyshops.DAOPreparedSettings.SettingsDAOPrepared;
import org.nearbyshops.BackupDAOsTwo.EndUserService;
import org.nearbyshops.DAOsPreparedRoles.*;
import org.nearbyshops.DAOsPrepared.*;
import org.nearbyshops.DAOsPreparedRoles.Deprecated.DistributorDAOPrepared;
import org.nearbyshops.DAOsPreparedRoles.Deprecated.DistributorStaffDAOPrepared;
import org.nearbyshops.JDBCContract;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;


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
	public static DeliveryGuySelfDAO deliveryGuySelfDAO = new DeliveryGuySelfDAO();
	public static ServiceConfigurationDAOPrepared serviceConfigurationDAO = new ServiceConfigurationDAOPrepared();
	public static SettingsDAOPrepared settingsDAOPrepared = new SettingsDAOPrepared();
	public static AdminDAOPrepared adminDAOPrepared = new AdminDAOPrepared();
	public static StaffDAOPrepared staffDAOPrepared = new StaffDAOPrepared();
	public static DistributorStaffDAOPrepared distributorStaffDAOPrepared = new DistributorStaffDAOPrepared();
	public static EndUserDAOPrepared endUserDAOPrepared = new EndUserDAOPrepared();


	public static ShopReviewDAOPrepared shopReviewDAOPrepared = new ShopReviewDAOPrepared();
	public static FavoriteBookDAOPrepared favoriteBookDAOPrepared = new FavoriteBookDAOPrepared();
	public static ShopReviewThanksDAOPrepared shopReviewThanksDAO = new ShopReviewThanksDAOPrepared();

	public static ItemReviewDAOPrepared itemReviewDAOPrepared = new ItemReviewDAOPrepared();
	public static FavoriteItemDAOPrepared favoriteItemDAOPrepared = new FavoriteItemDAOPrepared();
	public static ItemReviewThanksDAOPrepared itemReviewThanksDAOPrepared = new ItemReviewThanksDAOPrepared();

	public static ShopItemByShopDAO shopItemByShopDAO = new ShopItemByShopDAO();
	public static ShopItemDAO shopItemDAO = new ShopItemDAO();
	public static ShopItemByItemDAO shopItemByItemDAO = new ShopItemByItemDAO();

	public static ShopAdminDAO shopAdminDAO = new ShopAdminDAO();
	public static ShopStaffDAOPrepared shopStaffDAOPrepared = new ShopStaffDAOPrepared();

	// security
	public static ForbiddenOperationsDAO forbiddenOperationsDAO = new ForbiddenOperationsDAO();

	public static PlaceOrderHD_DAO placeOrderHD_dao = new PlaceOrderHD_DAO();


	// DAO Pick for shop
	public static OrderItemServicePFS orderItemServicePFS = new OrderItemServicePFS();
	public static OrderServicePFS orderServicePFS = new OrderServicePFS();
	public static PlaceOrderPFS_DAO placeOrderPFS_dao = new PlaceOrderPFS_DAO();


	public static EndUserDAONew endUserDAONew  = new EndUserDAONew();


	// Item Image and Item Specification DAO'

	public static ItemImagesDAO itemImagesDAO = new ItemImagesDAO();

	public static ItemSpecificationNameDAO itemSpecNameDAO = new ItemSpecificationNameDAO();
	public static ItemSpecNameDAOOuterJoin itemSpecNameDAOOuterJoin = new ItemSpecNameDAOOuterJoin();

	public static ItemSpecificationValueDAO itemSpecificationValueDAO = new ItemSpecificationValueDAO();
	public static ItemSpecValueDAOJoinOuter itemSpecValueDAOJoinOuter = new ItemSpecValueDAOJoinOuter();

	public static ItemSpecificationItemDAO itemSpecificationItemDAO = new ItemSpecificationItemDAO();



	// static reference for holding security accountApproved

	public static Object accountApproved;


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



	// SSE Notifications Support

	public static Map<Integer,SseBroadcaster> broadcasterMap = new HashMap<>();

	public static String broadcastMessageToShop(String message, int shopID) {

		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
		OutboundEvent event = eventBuilder.name("Order Received !")
				.mediaType(MediaType.TEXT_PLAIN_TYPE)
				.data(String.class, message)
				.build();


		if(broadcasterMap.get(shopID)!=null)
		{
			broadcasterMap.get(shopID).broadcast(event);
		}

		return "Message '" + message + "' has been broadcast.";
	}


	// broadcast messages to end user

	public static Map<Integer,SseBroadcaster> broadcasterMapEndUser = new HashMap<>();

	public static String broadcastMessageToEndUser(String title, String message, int endUserID) {

		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
		OutboundEvent event = eventBuilder.name(title)
				.mediaType(MediaType.TEXT_PLAIN_TYPE)
				.data(String.class, message)
				.build();


		if(broadcasterMapEndUser.get(endUserID)!=null)
		{
			broadcasterMapEndUser.get(endUserID).broadcast(event);
		}

		return "Message '" + message + "' has been broadcast.";
	}




//
//	public static SseBroadcaster broadcaster = new SseBroadcaster();
//
//	public static String broadcastMessageToShop(String message) {
//		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
//		OutboundEvent event = eventBuilder.name("message")
//				.mediaType(MediaType.TEXT_PLAIN_TYPE)
//				.data(String.class, message)
//				.build();
//
//		broadcaster.broadcast(event);
//
//		return "Message '" + message + "' has been broadcast.";
//	}





}
