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

import org.nearbyshops.DAOsPrepared.ShopDAO;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelEndPoints.ShopEndPoint;
import org.nearbyshops.Utility.GeoLocation;


@Path("/v1/Shop")
@Produces(MediaType.APPLICATION_JSON)
public class ShopResource {

	private GeoLocation center;
	private GeoLocation[] minMaxArray;
	private GeoLocation pointOne;
	private GeoLocation pointTwo;


	private ShopDAO shopDAO = Globals.shopDAO;


	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveShop(Shop shop)
	{

		// generate bounding coordinates for the shop based on its center and delivery range
		center = GeoLocation.fromDegrees(shop.getLatCenter(),shop.getLonCenter());
		minMaxArray = center.boundingCoordinates(shop.getDeliveryRange(),6371.01);

		pointOne = minMaxArray[0];
		pointTwo = minMaxArray[1];

		shop.setLatMin(pointOne.getLatitudeInDegrees());
		shop.setLonMin(pointOne.getLongitudeInDegrees());
		shop.setLatMax(pointTwo.getLatitudeInDegrees());
		shop.setLonMax(pointTwo.getLongitudeInDegrees());

		int idOfInsertedRow = shopDAO.insertShop(shop);

		shop.setShopID(idOfInsertedRow);
		
		
		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/Shop/" + idOfInsertedRow))
					.entity(shop)
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
	@Path("/{ShopID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateShop(Shop shop, @PathParam("ShopID")int ShopID)
	{

		// generate bounding coordinates for the shop based on its center and delivery range
		center = GeoLocation.fromDegrees(shop.getLatCenter(),shop.getLonCenter());
		minMaxArray = center.boundingCoordinates(shop.getDeliveryRange(),6371.01);

		pointOne = minMaxArray[0];
		pointTwo = minMaxArray[1];

		shop.setLatMin(pointOne.getLatitudeInDegrees());
		shop.setLonMin(pointOne.getLongitudeInDegrees());
		shop.setLatMax(pointTwo.getLatitudeInDegrees());
		shop.setLonMax(pointTwo.getLongitudeInDegrees());


		shop.setShopID(ShopID);
		
		int rowCount = shopDAO.updateShop(shop);
		
		if(rowCount >= 1)
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();
			
			return response;
		}
		if(rowCount <= 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
			
			return response;
		}

		return null;
	}
	
	
	@DELETE
	@Path("/{ShopID}")
	public Response deleteShop(@PathParam("ShopID")int shopID)
	{
		
		int rowCount = shopDAO.deleteShop(shopID);
	
		
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
	@Path("/Deprecated")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShops(@QueryParam("DistributorID")Integer distributorID,
							 @QueryParam("LeafNodeItemCategoryID")Integer itemCategoryID,
							 @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
							 @QueryParam("deliveryRangeMax")Double deliveryRangeMax,
							 @QueryParam("deliveryRangeMin")Double deliveryRangeMin,
							 @QueryParam("proximity")Double proximity,
							 @QueryParam("SearchString") String searchString,
							 @QueryParam("SortBy") String sortBy,
							 @QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset)
	{
		
		List<Shop> list = shopDAO.getShops(
				distributorID,itemCategoryID,
				latCenter, lonCenter,
				deliveryRangeMin,deliveryRangeMax,
				proximity,searchString,sortBy,limit,offset
		);
				
		
		GenericEntity<List<Shop>> listEntity = new GenericEntity<List<Shop>>(list){
			
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
	public Response getShopItems(
			@QueryParam("DistributorID")Integer distributorID,
			@QueryParam("LeafNodeItemCategoryID")Integer itemCategoryID,
			@QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
			@QueryParam("deliveryRangeMax")Double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")Double deliveryRangeMin,
			@QueryParam("proximity")Double proximity,
			@QueryParam("SearchString") String searchString,
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


		ShopEndPoint endPoint = shopDAO.getEndPointMetadata(distributorID,
				itemCategoryID, latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity,searchString);


		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);


		ArrayList<Shop> shopsList = null;


		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/


		if(metaonly==null || (!metaonly)) {


			shopsList = shopDAO.getShops(distributorID,itemCategoryID,
					latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity,searchString,sortBy,
					limit,offset);

			endPoint.setResults(shopsList);
		}


		//Marker
		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}





	@GET
	@Path("/FilterByItemCat/{ItemCategoryID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response filterShopsByItemCategory(
							 @PathParam("ItemCategoryID")Integer itemCategoryID,
							 @QueryParam("DistributorID")Integer distributorID,
							 @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
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


		ShopEndPoint endPoint = shopDAO.endPointMetadataFilterShops(itemCategoryID,distributorID,
				 latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity);


		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);


		ArrayList<Shop> shopsList = null;


		if(metaonly==null || (!metaonly)) {


			shopsList = shopDAO.filterShopsByItemCategory(
					itemCategoryID, distributorID,
					latCenter,lonCenter,
					deliveryRangeMin,deliveryRangeMax,
					proximity,sortBy, limit,offset
			);

			endPoint.setResults(shopsList);
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


	

	@GET
	@Path("/{ShopID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShop(@PathParam("ShopID")int shopID,
							@QueryParam("latCenter")double latCenter, @QueryParam("lonCenter")double lonCenter)
	{
		Shop shop = shopDAO.getShop(shopID,latCenter,lonCenter);
		
		if(shop!= null)
		{
			Response response = Response.status(Status.OK)
			.entity(shop)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(shop)
					.build();
			
			return response;
			
		}	
	}
	
}
