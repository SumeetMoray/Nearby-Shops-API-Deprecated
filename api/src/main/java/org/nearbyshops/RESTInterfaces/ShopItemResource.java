package org.nearbyshops.RESTInterfaces;

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
import org.nearbyshops.Model.ShopItem;

@Path("/ShopItem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShopItemResource {

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
	
	

	@GET
	public Response getShopItems(
			@QueryParam("ShopID")int ShopID, @QueryParam("ItemID") int itemID,
			@QueryParam("ItemCategoryID")int itemCategoryID,
			@QueryParam("latCenter")double latCenter,@QueryParam("lonCenter")double lonCenter,
			@QueryParam("deliveryRangeMax")double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")double deliveryRangeMin,
			@QueryParam("proximity")double proximity
	)
	{
		List<ShopItem> shopItemsList = Globals.shopItemService.getShopItems(
				ShopID, itemID,itemCategoryID,
				latCenter, lonCenter,
				deliveryRangeMin,deliveryRangeMax, proximity);




		for(ShopItem shopItem: shopItemsList)
		{
			if(ShopID == 0)
			{
				shopItem.setShop(Globals.shopService.getShop(shopItem.getShopID()));
			}

			if(itemID == 0)
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
	
}
