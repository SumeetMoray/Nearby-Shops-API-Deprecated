package org.nearbyshops.RESTEndpointRoles;

import com.sun.net.httpserver.Headers;
import org.glassfish.jersey.internal.util.Base64;
import org.nearbyshops.DAOsPreparedRoles.StaffDAOPrepared;
import org.nearbyshops.Globals.APIErrors;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelErrorMessages.ErrorNBSAPI;
import org.nearbyshops.ModelRoles.Staff;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;
import java.util.StringTokenizer;


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

//		staff.setEnabled(true);
//		staff.setWaitlisted(false);

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
	@Path("/{StaffID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
	public Response updateSTAFF(@PathParam("StaffID")int staffID,
								Staff staff,
								@Context HttpHeaders headers)
	{

		if(!verifyStaffAccount(headers,staffID))
		{

			Response responseError = Response.status(Status.FORBIDDEN)
					.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
					.build();

			throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
		}


		staff.setStaffID(staffID);

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
	@Path("/{ServiceProviderID}")
	//@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response deleteStaff(@PathParam("ServiceProviderID")int staffID,
								@Context HttpHeaders headers)
	{

		if(!verifyStaffAccount(headers,staffID))
		{

			Response responseError = Response.status(Status.FORBIDDEN)
					.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
					.build();

			throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
		}



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
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
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
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
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



	@GET
	@Path("Login")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_STAFF})
	public Response getDistributorLogin(@Context HttpHeaders header)
	{

		//Get request headers
		final MultivaluedMap<String, String> headers = header.getRequestHeaders();

		//Fetch authorization header
		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

		//If no authorization information present; block access
		if (authorization == null || authorization.isEmpty()) {

			Response response = Response.status(Status.UNAUTHORIZED)
					.entity(new ErrorNBSAPI(401, APIErrors.UPDATE_BY_WRONG_USER))
					.build();

			throw new NotAuthorizedException(response);
		}

		//Get encoded username and password

		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		//Decode username and password
		String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

		System.out.println("Username:Password" + usernameAndPassword);

		//Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		Staff staff = daoPrepared.checkStaff(null,username,password);

		if(staff!= null)
		{
			staff.setPassword(null);

			Response response;

			response = Response.status(Status.OK)
					.entity(staff)
					.build();

			return response;

		} else
		{

			Response response = Response.status(Status.UNAUTHORIZED)
					.entity(staff)
					.build();

			return response;

		}

	}



	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final String AUTHORIZATION_PROPERTY = "Authorization";

	private boolean verifyStaffAccount(HttpHeaders header, int staffID)
	{

		boolean result = true;


		//Get request headers
		final MultivaluedMap<String, String> headers = header.getRequestHeaders();

		//Fetch authorization header
		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

		//If no authorization information present; block access
		if (authorization == null || authorization.isEmpty()) {
//                requestContext.abortWith(ACCESS_DENIED);

			return false;
		}

		//Get encoded username and password
		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		//Decode username and password
		String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

		System.out.println("Username:Password" + usernameAndPassword);

		//Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();


		Staff staff = daoPrepared.checkStaff(null,username,password);
		// Distributor account exist and is enabled
		if(staff!=null && staff.getEnabled())
		{
			// If code enters here implies that distributor account is used for update. So we need to check if
			// the distributor is same as the one authorized.

			if(staff.getStaffID()!=staffID)
			{
				// the user doing an update is not the same as the user whose profile is being updated so has to
				// stop this operation, and should throw an unauthorized exception in this situation.
				return false;
			}
		}

		return true;
	}


}
