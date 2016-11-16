package org.nearbyshops.RESTEndpoints;

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelDelivery.DeliveryGuySelf;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;


@Path("/DeliveryGuySelf")
public class DeliveryGuySelfResource {


	public DeliveryGuySelfResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createVehicle(DeliveryGuySelf vehicleSelf)
	{

		int idOfInsertedRow = Globals.deliveryGuySelfDAO.saveDeliveryVehicleSelf(vehicleSelf);

		vehicleSelf.setDeliveryGuyID(idOfInsertedRow);


		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/DeliveryGuySelf/" + idOfInsertedRow))
					.entity(vehicleSelf)
					.build();
			
			return response;
			
		}else if(idOfInsertedRow <=0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
			
			//Response.status(Status.CREATED).location(arg0)
			
			return response;
		}
		
		return null;
		
	}

	
	@PUT
	@Path("/{VehicleID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCart(@PathParam("VehicleID")int vehicleID, DeliveryGuySelf vehicleSelf)
	{

		vehicleSelf.setDeliveryGuyID(vehicleID);

		int rowCount = Globals.deliveryGuySelfDAO.updateDeliveryVehicleSelf(vehicleSelf);


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
	@Path("/{VehicleID}")
	public Response deleteVehicle(@PathParam("VehicleID")int vehicleID)
	{

		int rowCount = Globals.deliveryGuySelfDAO.deleteDeliveryVehicleSelf(vehicleID);
		
		
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
	public Response getVehicles(@QueryParam("ShopID")int shopID)

	{


		List<DeliveryGuySelf> vehicleSelfList = Globals.deliveryGuySelfDAO.readDeliveryVehicleSelf(shopID);

		GenericEntity<List<DeliveryGuySelf>> listEntity = new GenericEntity<List<DeliveryGuySelf>>(vehicleSelfList){
			
		};
	
		
		if(vehicleSelfList.size()<=0)
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
	@Path("/{VehicleID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCart(@PathParam("VehicleID")int vehicleID)
	{


		DeliveryGuySelf vehicleSelf = Globals.deliveryGuySelfDAO.readVehicle(vehicleID);
		
		if(vehicleSelf != null)
		{
			Response response = Response.status(Status.OK)
			.entity(vehicleSelf)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(vehicleSelf)
					.build();
			
			return response;
			
		}
		
	}	
}
