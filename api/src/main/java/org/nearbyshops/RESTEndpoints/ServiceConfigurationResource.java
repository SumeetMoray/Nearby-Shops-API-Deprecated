package org.nearbyshops.RESTEndpoints;

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ServiceConfiguration;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;


@Path("/ServiceConfiguration")
public class ServiceConfigurationResource {


	public ServiceConfigurationResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createServiceConfig(ServiceConfiguration serviceConfiguration)
	{

		int idOfInsertedRow = Globals.serviceConfigDAO.saveConfig(serviceConfiguration);


		serviceConfiguration.setServiceID(idOfInsertedRow);


		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/ServiceConfig/" + idOfInsertedRow))
					.entity(serviceConfiguration)
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
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateServiceConfig(ServiceConfiguration config)
	{

		//cart.setCartID(cartID);
		config.setConfigurationID(1);

		int rowCount = Globals.serviceConfigDAO.updateServiceConfig(config);

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


	public Response deleteServiceConfig()
	{

		int rowCount = Globals.serviceConfigDAO.deleteServiceConfiguration(1);
		
		
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
	
	
	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCarts(@QueryParam("EndUserID")int endUserID,
							 @QueryParam("ShopID")int shopID)

	{

		List<Cart> cartList = Globals.cartService.readCarts(endUserID,shopID);

		GenericEntity<List<Cart>> listEntity = new GenericEntity<List<Cart>>(cartList){
			
		};
	
		
		if(cartList.size()<=0)
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

	*/
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCart()
	{

		ServiceConfiguration configuration = Globals.serviceConfigDAO.readConfig(1);

		configuration.setService(Globals.serviceDAO.readService(configuration.getServiceID()));

		
		if(configuration != null)
		{
			Response response = Response.status(Status.OK)
			.entity(configuration)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(configuration)
					.build();
			
			return response;
			
		}
		
	}	
}
