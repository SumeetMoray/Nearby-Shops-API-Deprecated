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
import org.nearbyshops.Model.Shop;
import org.nearbyshops.Utility.GeoLocation;

@Path("/Shop")
@Produces(MediaType.APPLICATION_JSON)
public class ShopResource {



	GeoLocation center;

	GeoLocation[] minMaxArray;
	GeoLocation pointOne;
	GeoLocation pointTwo;


	
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

		int idOfInsertedRow = Globals.shopService.insertShop(shop);

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
		
		int rowCount = Globals.shopService.updateShop(shop);
		
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
		
		int rowCount = Globals.shopService.deleteShop(shopID);
	
		
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
	public Response getShops(@QueryParam("DistributorID")int distributorID,
							 @QueryParam("latCenter")double latCenter,@QueryParam("lonCenter")double lonCenter,
							 @QueryParam("deliveryRangeMax")double deliveryRangeMax,
							 @QueryParam("deliveryRangeMin")double deliveryRangeMin,
							 @QueryParam("proximity")double proximity)
	{
		
		List<Shop> list = Globals.shopService.getShops(
				distributorID,
				latCenter, lonCenter,
				deliveryRangeMin,deliveryRangeMax,
				proximity
		);
				
		
		GenericEntity<List<Shop>> listEntity = new GenericEntity<List<Shop>>(list){
			
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
	@Path("/{ShopID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShop(@PathParam("ShopID")int shopID)
	{
		Shop shop = Globals.shopService.getShop(shopID);
		
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
