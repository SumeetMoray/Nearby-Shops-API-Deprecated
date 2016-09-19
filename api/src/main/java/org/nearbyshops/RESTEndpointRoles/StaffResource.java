package org.nearbyshops.RESTEndpointRoles;

import org.nearbyshops.DAOsPreparedRoles.StaffDAOPrepared;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelRoles.Staff;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;


@Path("/v1/Staff")
public class StaffResource {


	private StaffDAOPrepared daoPrepared = Globals.staffDAOPrepared;

	public StaffResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	public Response createstaff(Staff staff)
	{

		int idOfInsertedRow = daoPrepared.saveStaff(staff);


		staff.setStaffID(idOfInsertedRow);

		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/staff/" + idOfInsertedRow))
					.entity(staff)
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
	public Response updateSTAFF(@PathParam("ServiceProviderID")int serviceProviderID, Staff staff)
	{

		staff.setStaffID(serviceProviderID);

		int rowCount = daoPrepared.updateStaff(staff);
		
		
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
	public Response deleteStaff(@PathParam("ServiceProviderID")int staffID)
	{


		int rowCount = daoPrepared.deleteStaff(staffID);
		
		
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
	public Response getStaff()
	{


		List<Staff> list = daoPrepared.getStaff();

		GenericEntity<List<Staff>> listEntity = new GenericEntity<List<Staff>>(list){
			
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
	@Path("/{staffID}")
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStaff(@PathParam("staffID")int staffID)
	{

		Staff staff = daoPrepared.getStaff(staffID);
		
		if(staff != null)
		{
			Response response = Response.status(Status.OK)
			.entity(staff)
			.build();
			
			return response;
			
		} else 
		{
			
			Response response = Response.status(Status.NO_CONTENT)
					.entity(staff)
					.build();
			
			return response;
			
		}
		
	}	
}
