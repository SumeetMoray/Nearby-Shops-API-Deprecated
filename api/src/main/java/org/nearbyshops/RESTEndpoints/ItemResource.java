package org.nearbyshops.RESTEndpoints;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.nearbyshops.DAOsPrepared.ItemDAO;
import org.nearbyshops.DAOsPrepared.ItemDAOJoinOuter;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelEndPoints.ItemEndPoint;


@Path("/v1/Item")
public class ItemResource {

	private ItemDAO itemDAO = Globals.itemDAO;
	private ItemDAOJoinOuter itemDAOJoinOuter = Globals.itemDAOJoinOuter;


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveItem(@Valid Item item)
	{	
		int idOfInsertedRow = itemDAO.saveItem(item);
		
		item.setItemID(idOfInsertedRow);
		
		if(idOfInsertedRow >=1)
		{
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/Item/" + idOfInsertedRow))
					.entity(item)
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
	@Path("/{ItemID}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateItem(Item item, @PathParam("ItemID")int itemID)
	{
			
		item.setItemID(itemID);
	
		//System.out.println("ItemCategoryID: " + itemCategoryID + " " + itemCategory.getCategoryName()
		//+ " " + itemCategory.getCategoryDescription());
		
		int rowCount = itemDAO.updateItem(item);
		
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
	public Response updateItemBulk(List<Item> itemList)
	{
		int rowCountSum = 0;

		for(Item item : itemList)
		{
			rowCountSum = rowCountSum + itemDAO.updateItem(item);
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
	
	
	@DELETE
	@Path("/{ItemID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(@PathParam("ItemID")int itemID)
	{
		
		int rowCount = itemDAO.deleteItem(itemID);
		
		
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
	
	
	

//	@Path("/Deprecated")
//	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(
			@QueryParam("ItemCategoryID")Integer itemCategoryID,
			@QueryParam("ShopID")Integer shopID,
			@QueryParam("latCenter") Double latCenter, @QueryParam("lonCenter") Double lonCenter,
			@QueryParam("deliveryRangeMax")Double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")Double deliveryRangeMin,
			@QueryParam("proximity")Double proximity,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset)
	
	{
				
		//Marker
		
		List<Item> list = 
				itemDAO.getItems(
						itemCategoryID,
						shopID,
						latCenter, lonCenter,
						deliveryRangeMin,
						deliveryRangeMax,
						proximity,null,
						sortBy,limit,offset
				);
		
		
		GenericEntity<List<Item>> listEntity = new GenericEntity<List<Item>>(list){
			
			};
		
			
			if(list.size()<=0)
			{

				return Response.status(Status.NO_CONTENT)
						.entity(listEntity)
						.build();
				
			}else
			{

				return Response.status(Status.OK)
						.entity(listEntity)
						.build();
			}	
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(
			@QueryParam("ItemCategoryID")Integer itemCategoryID,
			@QueryParam("ShopID")Integer shopID,
			@QueryParam("latCenter") Double latCenter, @QueryParam("lonCenter") Double lonCenter,
			@QueryParam("deliveryRangeMax")Double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")Double deliveryRangeMin,
			@QueryParam("proximity")Double proximity,
			@QueryParam("SearchString")String searchString,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
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

		ItemEndPoint endPoint = itemDAO.getEndPointMetadata(itemCategoryID,
				shopID,latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity,searchString);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<Item> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					itemDAO.getItems(
							itemCategoryID,
							shopID,
							latCenter, lonCenter,
							deliveryRangeMin,
							deliveryRangeMax,
							proximity,searchString,
							sortBy,set_limit,set_offset
					);

			endPoint.setResults(list);
		}


		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

		//Marker

		return Response.status(Status.OK)
                .entity(endPoint)
                .build();

	}




	@GET
	@Path("/OuterJoin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(
			@QueryParam("ItemCategoryID")Integer itemCategoryID,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
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

		ItemEndPoint endPoint = itemDAOJoinOuter.getEndPointMetadata(itemCategoryID);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<Item> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					itemDAOJoinOuter.getItems(
							itemCategoryID,
							sortBy,set_limit,set_offset
					);

			endPoint.setResults(list);
		}


		//Marker

		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}



	@GET
	@Path("/Async")
	@Produces(MediaType.APPLICATION_JSON)
	public void asyncGet(@Suspended final AsyncResponse asyncResponse,
						 @QueryParam("ItemCategoryID") final Integer itemCategoryID,
						 @QueryParam("SortBy") final String sortBy,
						 @QueryParam("Limit") final Integer limit, @QueryParam("Offset") final Integer offset,
						 @QueryParam("metadata_only") final Boolean metaonly)

	{

		new Thread(new Runnable() {
			@Override
			public void run() {
				Response result = veryExpensiveOperation();
				asyncResponse.resume(result);
			}

			private Response veryExpensiveOperation() {
				// ... very expensive operation

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

				ItemEndPoint endPoint = itemDAOJoinOuter.getEndPointMetadata(itemCategoryID);

				endPoint.setLimit(set_limit);
				endPoint.setMax_limit(max_limit);
				endPoint.setOffset(set_offset);

				List<Item> list = null;


				if(metaonly==null || (!metaonly)) {

					list =
							itemDAOJoinOuter.getItems(
									itemCategoryID,
									sortBy,set_limit,set_offset
							);

					endPoint.setResults(list);
				}


				return Response.status(Status.OK)
						.entity(endPoint)
						.build();
			}

		}).start();
	}




	@GET
	@Path("/{ItemID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(@PathParam("ItemID")int itemID)
	{
		System.out.println("itemID=" + itemID);
		 
		
		//marker
		
		Item item = itemDAO.getItem(itemID);
		
		if(item!= null)
		{

			return Response.status(Status.OK)
			.entity(item)
			.build();
			
		} else 
		{

			return Response.status(Status.NO_CONTENT)
					.entity(item)
					.build();
			
		}
	}
	
}
