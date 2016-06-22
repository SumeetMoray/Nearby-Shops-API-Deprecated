package org.nearbyshops.RESTInterfaces;

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.EndUser;
import org.nearbyshops.Model.ServiceProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;


@Path("/ServiceProvider")
public class ServiceProviderResource {


	public ServiceProviderResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createServiceProvider(ServiceProvider serviceProvider)
	{

		int idOfInsertedRow = Globals.serviceProviderDAO.saveServiceProvider(serviceProvider);


		serviceProvider.setServiceProviderID(idOfInsertedRow);

		
		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/ServiceProvider/" + idOfInsertedRow))
					.entity(serviceProvider)
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
	@Path("/{ServiceProviderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDistributor(@PathParam("ServiceProviderID")int serviceProviderID, ServiceProvider serviceProvider)
	{

		serviceProvider.setServiceProviderID(serviceProviderID);

		int rowCount = Globals.serviceProviderDAO.updateServiceProvider(serviceProvider);
		
		
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
	@Path("/{ServiceProviderID}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response deleteServiceProvider(@PathParam("ServiceProviderID")int serviceProviderID)
	{

		int rowCount = Globals.serviceProviderDAO.deleteServiceProvider(serviceProviderID);
		
		
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
	public Response getServiceProviders()
	{


		List<ServiceProvider> list = Globals.serviceProviderDAO.getServiceProvider();

		GenericEntity<List<ServiceProvider>> listEntity = new GenericEntity<List<ServiceProvider>>(list){
			
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
	@Path("/{ServiceProviderID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServiceProvider(@PathParam("ServiceProviderID")int serviceProviderID)
	{

		ServiceProvider serviceProvider = Globals.serviceProviderDAO.getServiceProvider(serviceProviderID);
		
		if(serviceProvider!= null)
		{
			Response response = Response.status(Status.OK)
			.entity(serviceProvider)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(serviceProvider)
					.build();
			
			return response;
			
		}
		
	}	
}
