package org.nearbyshops.RESTEndpoints;

import java.net.URI;
import java.util.ArrayList;
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

import org.nearbyshops.DAOsPrepared.ItemCategoryDAO;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ItemCategory;
import org.nearbyshops.ModelEndPoints.ItemCategoryEndPoint;

@Path("/v1/ItemCategory")
public class ItemCategoryResource {


	private ItemCategoryDAO itemCategoryDAO = Globals.itemCategoryDAO;


	
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
	
		int idOfInsertedRow = itemCategoryDAO.saveItemCategory(itemCategory);
		
		
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
	
	
	@DELETE
	@Path("/{ItemCategoryID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItemCategory(@PathParam("ItemCategoryID")int itemCategoryID)
	{

		String message = "";


		int rowCount = itemCategoryDAO.deleteItemCategory(itemCategoryID);

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


	@PUT
	@Path("/{ItemCategoryID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateItemCategory(@PathParam("ItemCategoryID")int itemCategoryID, ItemCategory itemCategory)
	{
		itemCategory.setItemCategoryID(itemCategoryID);

		System.out.println("ItemCategoryID: " + itemCategoryID + " " + itemCategory.getCategoryName()
				+ " " + itemCategory.getCategoryDescription() + " Parent ID : " +  itemCategory.getParentCategoryID());

		int rowCount = itemCategoryDAO.updateItemCategory(itemCategory);

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



	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateItemCategoryBulk(List<ItemCategory> itemCategoryList)
	{
		int rowCountSum = 0;

		for(ItemCategory itemCategory : itemCategoryList)
		{
			rowCountSum = rowCountSum + itemCategoryDAO.updateItemCategory(itemCategory);
		}

		if(rowCountSum ==  itemCategoryList.size())
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();

			return response;
		}
		else if( rowCountSum < itemCategoryList.size() && rowCountSum > 0)
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
	public Response getItemCategories(
			@QueryParam("ShopID")Integer shopID,
			@QueryParam("ParentID")Integer parentID,@QueryParam("IsDetached")Boolean parentIsNull,
			@QueryParam("latCenter")Double latCenter,@QueryParam("lonCenter")Double lonCenter,
			@QueryParam("deliveryRangeMax")Double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")Double deliveryRangeMin,
			@QueryParam("proximity")Double proximity,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset)
	{


		List<ItemCategory> list = 
				itemCategoryDAO.getItemCategories(
						shopID, parentID,parentIsNull,
						latCenter,lonCenter,
						deliveryRangeMin,
						deliveryRangeMax,
						proximity,
						sortBy,
						limit,offset);
		
		
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemCategories(
			@QueryParam("ShopID")Integer shopID,
			@QueryParam("ParentID")Integer parentID,@QueryParam("IsDetached")Boolean parentIsNull,
			@QueryParam("latCenter")Double latCenter,@QueryParam("lonCenter")Double lonCenter,
			@QueryParam("deliveryRangeMax")Double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")Double deliveryRangeMin,
			@QueryParam("proximity")Double proximity,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset,
			@QueryParam("metadata_only")Boolean metaonly)
	{


		int set_limit = 30;
		int set_offset = 0;
		final int max_limit = 100;

		if(limit!= null)
		{

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

		ItemCategoryEndPoint endPoint = itemCategoryDAO
				.getEndPointMetaDataTwo(parentID,parentIsNull);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		ArrayList<ItemCategory> list = null;


		if(metaonly==null || (!metaonly)) {
			list = itemCategoryDAO.getItemCategories(
					shopID, parentID, parentIsNull,
					latCenter, lonCenter,
					deliveryRangeMin,
					deliveryRangeMax,
					proximity,
					sortBy,
					set_limit, set_offset);

			endPoint.setResults(list);
		}

//		GenericEntity<List<ItemCategory>> listEntity = new GenericEntity<List<ItemCategory>>(list){
//
//		};


		return Response.status(Status.OK)
                .entity(endPoint)
                .build();

	}



	@GET
	@Path("/{itemCategoryID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemCategory(@PathParam("itemCategoryID")Integer itemCategoryID)
	{	
		ItemCategory itemCategory = itemCategoryDAO.getItemCategory(itemCategoryID);
		
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
