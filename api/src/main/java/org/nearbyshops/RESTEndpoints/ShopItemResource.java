package org.nearbyshops.RESTEndpoints;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.nearbyshops.DAOsPrepared.*;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ShopItem;
import org.nearbyshops.ModelEndPoints.ShopItemEndPoint;


@Path("/v1/ShopItem")
public class ShopItemResource {


	private ItemDAO itemDAO = Globals.itemDAO;
	private ShopDAO shopDAO = Globals.shopDAO;


	private ShopItemByShopDAO shopItemByShopDAO = Globals.shopItemByShopDAO;
	private ShopItemByItemDAO shopItemByItemDAO = Globals.shopItemByItemDAO;
	private ShopItemDAO shopItemDAO = Globals.shopItemDAO;



	@PUT
	@Path("/UpdateBulk")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateShopItemBulk(List<ShopItem> itemList)
	{
		int rowCountSum = 0;

		for(ShopItem shopItem : itemList)
		{
			rowCountSum = rowCountSum + shopItemDAO.updateShopItem(shopItem);
		}


		if(rowCountSum ==  itemList.size())
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();

			return response;
		}
		else if( rowCountSum < itemList.size() && rowCountSum > 0)
		{
			Response response = Response.status(Status.PARTIAL_CONTENT)
					.entity(null)
					.build();

			return response;
		}
		else if(rowCountSum == 0 ) {

			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();

			return response;
		}

		return null;
	}


	@POST
	@Path("/CreateBulk")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createShopItemBulk(List<ShopItem> itemList)
	{
		int rowCountSum = 0;

		for(ShopItem shopItem : itemList)
		{
			rowCountSum = rowCountSum + shopItemDAO.insertShopItem(shopItem);
		}


		if(rowCountSum ==  itemList.size())
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();

			return response;
		}
		else if( rowCountSum < itemList.size() && rowCountSum > 0)
		{
			Response response = Response.status(Status.PARTIAL_CONTENT)
					.entity(null)
					.build();

			return response;
		}
		else if(rowCountSum == 0 ) {

			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();

			return response;
		}

		return null;
	}




	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveShopItem(ShopItem shopItem)
	{
		int rowCount = shopItemDAO.insertShopItem(shopItem);
		
		if(rowCount == 1)
		{
			
			Response response = Response.status(Status.CREATED)
								.entity(null)
								.build();
			
			return response;
			
		}else if(rowCount <= 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();			
			
			return response;
		}

		
		return null;
	}
	

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateShopItem(ShopItem shopItem)
	{
		//, @QueryParam("ShopID")int ShopID, @QueryParam("ItemID") int itemID
		
		//shopItem.setItemID(ShopID);
		//shopItem.setItemID(itemID);
		
		int rowCount = shopItemDAO.updateShopItem(shopItem);
		
		if(rowCount == 1)
		{
			
			Response response = Response.status(Status.OK)
								.entity(null)
								.build();
			
			return response;
			
		}else if(rowCount <= 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();			
			
			return response;
		}
	
		
		return null;
	}
	
	
	@DELETE
	public Response deleteShopItem(@QueryParam("ShopID")int ShopID, @QueryParam("ItemID") int itemID)
	{		
	    int rowCount =	shopItemDAO.deleteShopItem(ShopID, itemID);
		
		if(rowCount == 1)
		{
			
			Response response = Response.status(Status.OK)
								.entity(null)
								.build();
			
			return response;
			
		}else if(rowCount <= 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();			
			
			return response;
		}
	
		return null;
	}


	@POST
	@Path("/DeleteBulk")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteShopItemBulk(List<ShopItem> itemList)
	{
		int rowCountSum = 0;

		for(ShopItem shopItem : itemList)
		{
			rowCountSum = rowCountSum + shopItemDAO
					.deleteShopItem(shopItem.getShopID(),shopItem.getItemID());
		}


		if(rowCountSum ==  itemList.size())
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();

			return response;
		}
		else if( rowCountSum < itemList.size() && rowCountSum > 0)
		{
			Response response = Response.status(Status.PARTIAL_CONTENT)
					.entity(null)
					.build();

			return response;
		}
		else if(rowCountSum == 0 ) {

			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();

			return response;
		}

		return null;
	}
	
	

	@GET
	@Path("/Deprecated")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShopItems(
			@QueryParam("ItemCategoryID")Integer ItemCategoryID,
			@QueryParam("ShopID")Integer ShopID, @QueryParam("ItemID") Integer itemID,
			@QueryParam("latCenter")Double latCenter,@QueryParam("lonCenter")Double lonCenter,
			@QueryParam("deliveryRangeMax")Double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")Double deliveryRangeMin,
			@QueryParam("proximity")Double proximity,
			@QueryParam("EndUserID") Integer endUserID,@QueryParam("IsFilledCart") Boolean isFilledCart,
			@QueryParam("IsOutOfStock") Boolean isOutOfStock,@QueryParam("PriceEqualsZero")Boolean priceEqualsZero,
			@QueryParam("MinPrice")Integer minPrice,@QueryParam("MaxPrice")Integer maxPrice,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset
	)
	{
		List<ShopItem> shopItemsList = shopItemDAO.getShopItems(
				ItemCategoryID,
				ShopID, itemID,
				latCenter, lonCenter,
				deliveryRangeMin,deliveryRangeMax,
				proximity, endUserID,
				isFilledCart,
				isOutOfStock,
				priceEqualsZero,
				sortBy,
				limit,offset);


		for(ShopItem shopItem: shopItemsList)
		{
			if(ShopID == null)
			{
				shopItem.setShop(shopDAO.getShop(shopItem.getShopID(),latCenter,lonCenter));
			}

			if(itemID == null)
			{
				shopItem.setItem(itemDAO.getItem(shopItem.getItemID()));
			}

		}

		
		GenericEntity<List<ShopItem>> list = new GenericEntity<List<ShopItem>>(shopItemsList){
			
		};
		
		
		if(shopItemsList.size()== 0)
		{
			return Response.status(Status.NO_CONTENT)
					.type(MediaType.APPLICATION_JSON)
			.entity(list)
			.build(); 
			
		
		
		}else
		{
			return Response.status(Status.OK)
					.type(MediaType.APPLICATION_JSON)
					.entity(list)
					.build();
		
			
		}
	}




	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShopItems(
			@QueryParam("ItemCategoryID")Integer ItemCategoryID,
			@QueryParam("ShopID")Integer ShopID, @QueryParam("ItemID") Integer itemID,
			@QueryParam("latCenter")Double latCenter,@QueryParam("lonCenter")Double lonCenter,
			@QueryParam("deliveryRangeMax")Double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")Double deliveryRangeMin,
			@QueryParam("proximity")Double proximity,
			@QueryParam("EndUserID") Integer endUserID,@QueryParam("IsFilledCart") Boolean isFilledCart,
			@QueryParam("IsOutOfStock") Boolean isOutOfStock,@QueryParam("PriceEqualsZero")Boolean priceEqualsZero,
			@QueryParam("MinPrice")Integer minPrice,@QueryParam("MaxPrice")Integer maxPrice,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset,
			@QueryParam("metadata_only")Boolean metaonly
	)
	{

		int set_limit = 30;
		int set_offset = 0;
		final int max_limit = 100;


		if(limit!= null) {

			if (limit >= max_limit) {

				set_limit = max_limit;
			}
			else
			{

				set_limit = limit;
			}

		}

		if(offset!=null)
		{
			set_offset = offset;
		}

		ShopItemEndPoint endPoint;


		if(ShopID!=null && itemID == null)
		{
			endPoint = shopItemByShopDAO.getEndpointMetadata(
					ItemCategoryID,
					ShopID,
					latCenter,lonCenter,
					deliveryRangeMin,deliveryRangeMax,
					proximity,endUserID,
					isFilledCart,isOutOfStock,priceEqualsZero
			);
		}
		else if(itemID !=null && ShopID==null)
		{
			endPoint = shopItemByItemDAO.getEndpointMetadata(
					ItemCategoryID,
					itemID,
					latCenter,lonCenter,
					deliveryRangeMin,deliveryRangeMax,
					proximity,endUserID,
					isFilledCart,isOutOfStock,priceEqualsZero
			);

		}
		else
		{
			endPoint = shopItemDAO.getEndpointMetadata(
					ItemCategoryID,
					ShopID,itemID,
					latCenter,lonCenter,
					deliveryRangeMin,deliveryRangeMax,
					proximity,endUserID,
					isFilledCart,isOutOfStock,priceEqualsZero);
		}



		/*if(itemID!=null && ShopID==null)
		{

		}

		if(itemID==null && ShopID == null)
		{

		}

		if(ShopID!=null && itemID !=null)
		{

		}
*/


		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);


		ArrayList<ShopItem> shopItemsList = null;


		if(metaonly==null || (!metaonly)) {


			if(ShopID!=null && itemID == null)
			{

				shopItemsList = shopItemByShopDAO.getShopItems(
						ItemCategoryID,
						ShopID,
						latCenter, lonCenter,
						deliveryRangeMin,deliveryRangeMax,
						proximity, endUserID,
						isFilledCart,
						isOutOfStock,
						priceEqualsZero,
						sortBy,
						limit,offset
				);
			}
			else if(itemID !=null && ShopID==null)
			{

				shopItemsList = shopItemByItemDAO.getShopItems(
						ItemCategoryID,
						itemID,
						latCenter, lonCenter,
						deliveryRangeMin,deliveryRangeMax,
						proximity, endUserID,
						isFilledCart,
						isOutOfStock,
						priceEqualsZero,
						sortBy,
						limit,offset
				);

			}
			else
			{

				shopItemsList = shopItemDAO.getShopItems(
						ItemCategoryID,
						ShopID, itemID,
						latCenter, lonCenter,
						deliveryRangeMin,deliveryRangeMax,
						proximity, endUserID,
						isFilledCart,
						isOutOfStock,
						priceEqualsZero,
						sortBy,
						limit,offset);

			}



/*
			for(ShopItem shopItem: shopItemsList)
			{
				if(ShopID == null)
				{
					shopItem.setShop(shopDAO.getShop(shopItem.getItemID(),latCenter,lonCenter));
				}

				if(itemID == null)
				{
					shopItem.setItem(itemDAO.getItem(shopItem.getItemID()));
				}

			}
*/


			endPoint.setResults(shopItemsList);
		}

/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/



		//Marker
		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}
	
}
