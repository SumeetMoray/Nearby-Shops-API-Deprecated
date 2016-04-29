package org.nearbyshops.Resource;

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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.nearbyshops.database.Globals;
import org.nearbyshops.model.Distributor;


@Path("/Distributor")
public class DistributorResource {

	
	public DistributorResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDistributor(Distributor distributor)
	{
		System.out.println(distributor.getDistributorName() + " | " + distributor.getDistributorID());
		
		int idOfInsertedRow = Globals.distributorService.saveDistributor(distributor);
		
		System.out.println(distributor.getDistributorName() + " | " + distributor.getDistributorID());
	
		distributor.setDistributorID(idOfInsertedRow);
		
		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/Distributor/" + idOfInsertedRow))
					.entity(distributor)
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
	@Path("/{DistributorID}")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response updateDistributor(@PathParam("DistributorID")int distributorID, Distributor distributor)
	{

		distributor.setDistributorID(distributorID);
		
		System.out.println("distributorID: " + distributorID + " " + distributor.getDistributorName()
		+ " " + distributor.getDistributorID());
		
		int rowCount = Globals.distributorService.updateDistributor(distributor);
		
		
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
	@Path("/{DistributorID}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response deleteDistributor(@PathParam("DistributorID")int distributorID)
	{
		int rowCount = Globals.distributorService.deleteDistributor(distributorID);
		
		
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
	public Response getAllDistributors()
	{	
		List<Distributor> list = Globals.distributorService.readAllDistributor();
		
		GenericEntity<List<Distributor>> listEntity = new GenericEntity<List<Distributor>>(list){
			
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
	@Path("/{DistributorID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDistributor(@PathParam("DistributorID")int distributorID)
	{		
		
		Distributor distributor = Globals.distributorService.getDistributor(distributorID);
		
		if(distributor!= null)
		{
			Response response = Response.status(Status.OK)
			.entity(distributor)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(distributor)
					.build();
			
			return response;
			
		}
		
	}	
}
