package org.nearbyshops.RESTInterfaces;

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Distributor;
import org.nearbyshops.Model.EndUser;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;


@Path("/EndUser")
public class EndUserResource {


	public EndUserResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEndUser(EndUser endUser)
	{

		int idOfInsertedRow = Globals.endUserService.saveEndUser(endUser);

		endUser.setEndUserID(idOfInsertedRow);
		endUser.setPassword(null);

		
		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/Distributor/" + idOfInsertedRow))
					.entity(endUser)
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
	@Path("/{EndUserID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDistributor(@PathParam("EndUserID")int endUserID, EndUser endUser)
	{

		endUser.setEndUserID(endUserID);

		int rowCount = Globals.endUserService.updateEndUser(endUser);
		
		
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
	@Path("/{EndUserID}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response deleteDistributor(@PathParam("EndUserID")int endUserID)
	{

		int rowCount = Globals.endUserService.deleteEndUser(endUserID);
		
		
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
	public Response getEndUsers()
	{


		List<EndUser> list = Globals.endUserService.getEndUser();

		GenericEntity<List<EndUser>> listEntity = new GenericEntity<List<EndUser>>(list){
			
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
	@Path("/{EndUserID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEndUser(@PathParam("EndUserID")int endUserID)
	{

		EndUser endUser = Globals.endUserService.getEndUser(endUserID);
		
		if(endUser!= null)
		{
			Response response = Response.status(Status.OK)
			.entity(endUser)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(endUser)
					.build();
			
			return response;
			
		}
		
	}


	@GET
	@Path("/Validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateDistributor(@QueryParam("Password")String password,@QueryParam("Username")String userName,@QueryParam("ID")Integer id)
	{

		boolean isValid = false;

		EndUser tempEndUser = null;

		if(id!=null)
		{
			tempEndUser = Globals.endUserService.getEndUserPassword(id,null);

			System.out.println(id + " : " + userName);

		}else if(userName !=null)
		{
			tempEndUser = Globals.endUserService.getEndUserPassword(null,userName);
		}


		if(tempEndUser!=null && tempEndUser.getPassword()!=null && tempEndUser.getPassword().equals(password))
		{
			isValid = true;

			tempEndUser.setPassword(null);

		}



		if(isValid)
		{
			Response response = Response.status(Status.OK)
					.entity(tempEndUser)
					.build();

			return response;

		} else
		{

			Response response = Response.status(Status.UNAUTHORIZED)
					.entity(null)
					.build();

			return response;

		}

	}




}
