package org.nearbyshops.model.Later;

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

import org.nearbyshops.database.Globals;

@Path("/Category")
public class AbstractCategoryResource {

	
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveAbstractCategory(AbstractCategory abstractCategory)
	{
		System.out.println(abstractCategory.getCategoryName() + " | " + abstractCategory.getImageURL());
	
		int rowCount = Globals.abstractCategoryService.saveAbstractCategory(abstractCategory);
		
		
		if(rowCount >=1)
		{
			Response response = Response.status(Status.CREATED)
					.entity(null)
					.build();
			
			return response;
			
		}else if(rowCount<=0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
			
			return response;
		}
		
		
		return null;
	}
	
	
	@PUT
	@Path("/{AbstractCategoryID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAbstractCategory(@PathParam("AbstractCategoryID")int abstractCategoryID, AbstractCategory abstractCategory)
	{
		
		abstractCategory.setID(abstractCategoryID);
	
		System.out.println("AbstractCategoryID: " + abstractCategoryID + " " + abstractCategory.getCategoryName());
		
		int rowCount = Globals.abstractCategoryService.updateAbstractCategory(abstractCategory);
		
		
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
	@Path("/{AbstractCategoryID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAbstractCategory(@PathParam("AbstractCategoryID")int abstractCategoryID)
	{
		
		String message = "";
		
		int rowCount = Globals.abstractCategoryService.deleteAbstractCategory(abstractCategoryID);
		
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
	public Response getAbstractCategories(@QueryParam("ParentID")int parentID)
	{	
		//List<ItemCategory> list = Globals.itemCategoryService.readAllItemCategories(shopID);
		
		List<AbstractCategory> list = Globals.abstractCategoryService.getCategories(parentID);
		
		
		GenericEntity<List<AbstractCategory>> listEntity = new GenericEntity<List<AbstractCategory>>(list){
			
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
	@Path("/{AbstractCategoryID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAbstractCategory(@PathParam("AbstractCategoryID")int abstractCategoryID)
	{	
		//ItemCategory itemCategory = Globals.itemCategoryService.getItemCategory(itemCategoryID);
		
		AbstractCategory abstractCategory = Globals.abstractCategoryService.getCategory(abstractCategoryID);
		
		if(abstractCategory!= null)
		{
			Response response = Response.status(Status.OK)
			.entity(abstractCategory)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(abstractCategory)
					.build();
			
			return response;
			
		}
	}	
	
}
