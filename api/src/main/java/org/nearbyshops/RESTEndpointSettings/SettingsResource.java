package org.nearbyshops.RESTEndpointSettings;

import org.nearbyshops.DAOPreparedSettings.SettingsDAOPrepared;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelSettings.Settings;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


@Path("/Settings")
public class SettingsResource {


	private SettingsDAOPrepared daoPrepared = Globals.settingsDAOPrepared;


	public SettingsResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateService(Settings settings)
	{

		if(settings!=null)
		{
			settings.setSettingsID(1);
		}


		int rowCount =	daoPrepared.updateSettings(settings);

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
	public Response deleteCart()
	{

		//int rowCount = Globals.cartService.deleteCart(cartID);

		int rowCount = daoPrepared.deleteSettings();
		
		
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
	public Response getService()
	{

		Settings settings = daoPrepared.getServiceConfiguration();
		
		if(settings != null)
		{
			Response response = Response.status(Status.OK)
			.entity(settings)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(settings)
					.build();
			
			return response;
		}
		
	}	
}
