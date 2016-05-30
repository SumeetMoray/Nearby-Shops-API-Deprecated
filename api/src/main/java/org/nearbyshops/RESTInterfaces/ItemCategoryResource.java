package org.nearbyshops.RESTInterfaces;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ItemCategory;

@Path("/ItemCategory")
public class ItemCategoryResource {
	
	
	public ItemCategoryResource() {
		super();
		// TODO Auto-generated constructor stub
	}                                 
	
	
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveItemCategory(ItemCategory itemCategory)
	{
		System.out.println(itemCategory.getCategoryName() + " | " + itemCategory.getCategoryDescription());
	
		int idOfInsertedRow = Globals.itemCategoryService.saveItemCategory(itemCategory);
		
		
		itemCategory.setItemCategoryID(idOfInsertedRow);
		
		
		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/ItemCategory/" + idOfInsertedRow))
					.entity(itemCategory)
					.build();
			
			return response;
			
		}else if(idOfInsertedRow <= 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
			
			return response;
		}
		
		
		return null;
	}
	
	
	@PUT
	@Path("/{ItemCategoryID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateItemCategory(@PathParam("ItemCategoryID")int itemCategoryID, ItemCategory itemCategory)
	{
		itemCategory.setItemCategoryID(itemCategoryID);
	
		System.out.println("ItemCategoryID: " + itemCategoryID + " " + itemCategory.getCategoryName()
		+ " " + itemCategory.getCategoryDescription());
		
		int rowCount = Globals.itemCategoryService.updateItemCategory(itemCategory);
		
		if(rowCount >= 1)
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();
			
			return response;
		}
		if(rowCount == 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
			
			return response;
		}

		return null;
	}
	
	
	@DELETE
	@Path("/{ItemCategoryID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItemCategory(@PathParam("ItemCategoryID")int itemCategoryID)
	{
		
		String message = "";
		
		
		int rowCount = Globals.itemCategoryService.deleteItemCategory(itemCategoryID);
		
		message = "Total Deleted : " + rowCount;
		
		if(rowCount>=1)
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();
			
			return response;
		}
		
		if(rowCount == 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
			
			return response;
		}
		
		return null;
	}


	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllItemCategory(
			@QueryParam("ShopID")int shopID,
			@QueryParam("ParentID")int parentID,
			@QueryParam("latCenter")double latCenter,@QueryParam("lonCenter")double lonCenter,
			@QueryParam("deliveryRangeMax")double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")double deliveryRangeMin,
			@QueryParam("proximity")double proximity)
	{



		List<ItemCategory> list = 
				Globals.itemCategoryService.getItemCategories(
						shopID, parentID,
						latCenter,lonCenter,
						deliveryRangeMin,
						deliveryRangeMax,
						proximity);
		
		
		GenericEntity<List<ItemCategory>> listEntity = new GenericEntity<List<ItemCategory>>(list){
			
			};
		
			
			if(list.size()<=0)
			{
				Response response = Response.status(Status.NO_CONTENT)
						.entity(listEntity)
						.build();
				
				return response;
				
			}else
			{
				Response response = Response.status(Status.OK)
						.entity(listEntity)
						.build();
				
				return response;
			}
			
	}
	
	
	
	@GET
	@Path("/{itemCategoryID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemCategory(@PathParam("itemCategoryID")int itemCategoryID)
	{	
		ItemCategory itemCategory = Globals.itemCategoryService.getItemCategory(itemCategoryID);
		
		if(itemCategory!= null)
		{
			Response response = Response.status(Status.OK)
			.entity(itemCategory)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(itemCategory)
					.build();
			
			return response;
			
		}
	}	
	
	
	
}
