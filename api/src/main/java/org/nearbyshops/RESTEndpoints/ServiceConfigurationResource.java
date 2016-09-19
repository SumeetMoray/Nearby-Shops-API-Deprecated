package org.nearbyshops.RESTEndpoints;

import org.nearbyshops.DAOsPrepared.ServiceConfigurationDAOPrepared;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.ServiceConfiguration;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;


@Path("/ServiceConfiguration")
public class ServiceConfigurationResource {


	ServiceConfigurationDAOPrepared daoPrepared = Globals.serviceConfigurationDAO;


	public ServiceConfigurationResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createService(ServiceConfiguration serviceConfiguration)
	{

		int idOfInsertedRow = daoPrepared.saveService(serviceConfiguration);

		serviceConfiguration.setServiceID(idOfInsertedRow);

		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/CurrentServiceConfiguration/" + idOfInsertedRow))
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
	@Path("/{ServiceID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateService(@PathParam("ServiceID")int serviceID, ServiceConfiguration serviceConfiguration)
	{

		serviceConfiguration.setServiceID(serviceID);

		int rowCount =	daoPrepared.updateService(serviceConfiguration);

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
	@Path("/{ServiceID}")
	public Response deleteCart(@PathParam("ServiceID")int serviceID)
	{

		//int rowCount = Globals.cartService.deleteCart(cartID);

		int rowCount = daoPrepared.deleteService(serviceID);
		
		
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
	
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
	public Response getService(	@QueryParam("ServiceLevel") int serviceLevel,
								   @QueryParam("ServiceType") int serviceType,
								   @QueryParam("LatCenter") Double latCenterQuery,
								   @QueryParam("LonCenter") Double lonCenterQuery,
							   @QueryParam("SortBy") String sortBy,
							   @QueryParam("Limit") int limit, @QueryParam("Offset") int offset)

	{


		List<ServiceConfiguration> servicesList = daoPrepared.readServices(serviceLevel,serviceType,latCenterQuery,lonCenterQuery,
                                    								sortBy,limit,offset);

		GenericEntity<List<ServiceConfiguration>> listEntity = new GenericEntity<List<ServiceConfiguration>>(servicesList){
			
		};
	
		
		if(servicesList.size()<=0)
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
//	@Path("/{ServiceID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getService()
	{
//		@PathParam("ServiceID")int service

		ServiceConfiguration serviceConfiguration = daoPrepared.readServiceConfiguration();
		
		if(serviceConfiguration != null)
		{
			Response response = Response.status(Status.OK)
			.entity(serviceConfiguration)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(serviceConfiguration)
					.build();
			
			return response;
		}
		
	}	
}
