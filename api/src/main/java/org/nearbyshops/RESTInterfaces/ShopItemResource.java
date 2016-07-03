package org.nearbyshops.RESTInterfaces;

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

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.Model.ShopItem;
import org.nearbyshops.ModelEndPoints.ItemEndPoint;
import org.nearbyshops.ModelEndPoints.ShopItemEndPoint;


@Path("/v1/ShopItem")
public class ShopItemResource {




	@PUT
	@Path("/UpdateBulk")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateShopItemBulk(List<ShopItem> itemList)
	{
		int rowCountSum = 0;

		for(ShopItem shopItem : itemList)
		{
			rowCountSum = rowCountSum + Globals.shopItemService.updateShopItem(shopItem);
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
			rowCountSum = rowCountSum + Globals.shopItemService.insertShopItem(shopItem);
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
		int rowCount = Globals.shopItemService.insertShopItem(shopItem);
		
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
		
		//shopItem.setShopID(ShopID);
		//shopItem.setItemID(itemID);
		
		int rowCount = Globals.shopItemService.updateShopItem(shopItem);
		
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
	    int rowCount =	Globals.shopItemService.deleteShopItem(ShopID, itemID);
		
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
			rowCountSum = rowCountSum + Globals.shopItemService
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
		List<ShopItem> shopItemsList = Globals.shopItemService.getShopItems(
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
				shopItem.setShop(Globals.shopService.getShop(shopItem.getShopID(),latCenter,lonCenter));
			}

			if(itemID == null)
			{
				shopItem.setItem(Globals.itemService.getItem(shopItem.getItemID()));
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

		ShopItemEndPoint endPoint = Globals.shopItemService.getEndpointMetadata(
				ItemCategoryID,
				ShopID,itemID,
				latCenter,lonCenter,
				deliveryRangeMin,deliveryRangeMax,
				proximity,endUserID,
				isFilledCart,isOutOfStock,priceEqualsZero);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);


		ArrayList<ShopItem> shopItemsList = null;


		if(metaonly==null || (!metaonly)) {

			shopItemsList = Globals.shopItemService.getShopItems(
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
					shopItem.setShop(Globals.shopService.getShop(shopItem.getShopID(),latCenter,lonCenter));
				}

				if(itemID == null)
				{
					shopItem.setItem(Globals.itemService.getItem(shopItem.getItemID()));
				}

			}

			endPoint.setResults(shopItemsList);
		}

		//Marker
		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}
	
}
