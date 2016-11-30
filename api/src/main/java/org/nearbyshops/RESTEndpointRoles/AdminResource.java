package org.nearbyshops.RESTEndpointRoles;

import org.glassfish.jersey.internal.util.Base64;
import org.nearbyshops.DAOsPreparedRoles.AdminDAOPrepared;
import org.nearbyshops.Globals.APIErrors;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelErrorMessages.ErrorNBSAPI;
import org.nearbyshops.ModelRoles.Admin;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;
import java.util.StringTokenizer;


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
	public Response updateAdmin(@PathParam("ServiceProviderID")int adminID,
								Admin admin,
								@Context HttpHeaders headers)
	{


		if(!verifyAdminAccount(headers,adminID))
		{

			Response responseError = Response.status(Status.FORBIDDEN)
					.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
					.build();

			throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
		}



		admin.setAdminID(adminID);

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
	public Response deleteAdmin(@PathParam("ServiceProviderID")int adminID,
								@Context HttpHeaders headers)
	{


		if(!verifyAdminAccount(headers,adminID))
		{

			Response responseError = Response.status(Status.FORBIDDEN)
					.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
					.build();

			throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
		}


		int rowCount = daoPrepared.deleteAdmin(adminID);
		
		
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



	@GET
	@Path("Login")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
	public Response getAdminLogin(@Context HttpHeaders header)
	{
/*
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

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



		Admin admin = daoPrepared.checkAdmin(null,username,password);



		if(admin!= null)
		{
			admin.setPassword(null);

			Response response;

			response = Response.status(Status.OK)
					.entity(admin)
					.build();


			return response;

		} else
		{

			Response response = Response.status(Status.UNAUTHORIZED)
					.entity(admin)
					.build();

			return response;

		}

	}







	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final String AUTHORIZATION_PROPERTY = "Authorization";

	private boolean verifyAdminAccount(HttpHeaders header, int adminID)
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

		Admin admin = daoPrepared.checkAdmin(null,username,password);

		// Distributor account exist and is enabled
		if(admin!=null)
		{
			// If code enters here implies that distributor account is used for update. So we need to check if
			// the distributor is same as the one authorized.

			if(admin.getAdminID()!=adminID)
			{
				// the user doing an update is not the same as the user whose profile is being updated so has to
				// stop this operation, and should throw an unauthorized exception in this situation.
				return false;
			}
		}

		return true;
	}



}
