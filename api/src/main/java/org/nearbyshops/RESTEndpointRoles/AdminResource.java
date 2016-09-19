package org.nearbyshops.RESTEndpointRoles;

import org.nearbyshops.DAOsPreparedRoles.AdminDAOPrepared;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.Admin;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;


@Path("/v1/Admin")
public class AdminResource {


	private AdminDAOPrepared daoPrepared = Globals.adminDAOPrepared;

	public AdminResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	public Response createAdmin(Admin admin)
	{

		int idOfInsertedRow = daoPrepared.saveAdmin(admin);


		admin.setAdminID(idOfInsertedRow);

		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/Admin/" + idOfInsertedRow))
					.entity(admin)
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
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	@Path("/{ServiceProviderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAdmin(@PathParam("ServiceProviderID")int serviceProviderID, Admin admin)
	{

		admin.setAdminID(serviceProviderID);

		int rowCount = daoPrepared.updateAdmin(admin);
		
		
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
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	@Path("/{ServiceProviderID}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAdmin(@PathParam("ServiceProviderID")int serviceProviderID)
	{


		int rowCount = daoPrepared.deleteAdmin(serviceProviderID);
		
		
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
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAdmins()
	{


		List<Admin> list = daoPrepared.getAdmin();

		GenericEntity<List<Admin>> listEntity = new GenericEntity<List<Admin>>(list){
			
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
	@Path("/{adminID}")
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAdmin(@PathParam("adminID")int adminID)
	{

		Admin admin = daoPrepared.getAdmin(adminID);
		
		if(admin != null)
		{
			Response response = Response.status(Status.OK)
			.entity(admin)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(admin)
					.build();
			
			return response;
			
		}
		
	}	
}
