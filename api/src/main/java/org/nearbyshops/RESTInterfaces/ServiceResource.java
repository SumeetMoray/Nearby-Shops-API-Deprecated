package org.nearbyshops.RESTInterfaces;

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;


@Path("/Service")
public class ServiceResource {


	public ServiceResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createService(Service service)
	{

		int idOfInsertedRow = Globals.serviceDAO.saveService(service);

		service.setServiceID(idOfInsertedRow);


		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/Service/" + idOfInsertedRow))
					.entity(service)
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
	public Response updateService(@PathParam("ServiceID")int serviceID, Service service)
	{

		service.setServiceID(serviceID);

		int rowCount = Globals.serviceDAO.updateService(service);

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

		int rowCount = Globals.serviceDAO.deleteService(serviceID);
		
		
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
	public Response getService(@QueryParam("ServiceLevel") int serviceLevel,
								 @QueryParam("ServiceType") int serviceType,
								 @QueryParam("LatCenter") Double latCenterQuery,
								 @QueryParam("LonCenter") Double lonCenterQuery,
							   @QueryParam("SortBy") String sortBy,
							   @QueryParam("Limit") int limit, @QueryParam("Offset") int offset)

	{


		List<Service> servicesList = Globals.serviceDAO.readServices(serviceLevel,serviceType,latCenterQuery,lonCenterQuery,
                                    								sortBy,limit,offset);

		GenericEntity<List<Service>> listEntity = new GenericEntity<List<Service>>(servicesList){
			
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
	@Path("/{ServiceID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getService(@PathParam("ServiceID")int serviceID)
	{

		Service service = Globals.serviceDAO.readService(serviceID);
		
		if(service != null)
		{
			Response response = Response.status(Status.OK)
			.entity(service)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(service)
					.build();
			
			return response;
		}
		
	}	
}
