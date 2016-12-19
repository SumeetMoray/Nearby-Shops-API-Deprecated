package org.nearbyshops.RESTEndpointRoles.Deprecated;

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


//@Path("/v1/Admin")
public class AdminResourceDeprecated {


	private AdminDAOPrepared daoPrepared = Globals.adminDAOPrepared;

	public AdminResourceDeprecated() {
		super();
		// TODO Auto-generated constructor stub
	}



//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
//	public Response createAdmin(Admin admin)
//	{
//		// creating an admin account is not allowed. Service has only one Admin Account and all others are staff accounts.
//
//		int idOfInsertedRow = daoPrepared.saveAdmin(admin);
//
//
//		admin.setAdminID(idOfInsertedRow);
//
//		if(idOfInsertedRow >=1)
//		{
//
//
//			Response response = Response.status(Status.CREATED)
//					.location(URI.create("/api/Admin/" + idOfInsertedRow))
//					.entity(admin)
//					.build();
//
//			return response;
//
//		}else if(idOfInsertedRow <=0)
//		{
//			Response response = Response.status(Status.NOT_MODIFIED)
//					.entity(null)
//					.build();
//
//			//Response.status(Status.CREATED).location(arg0)
//
//			return response;
//		}
//
//		return null;
//
//	}


	//	@Path("/{ServiceProviderID}")
	//	@PathParam("ServiceProviderID")int adminID
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	public Response updateAdmin(Admin admin)
	{
		if(Globals.accountApproved instanceof Admin)
		{
			Admin adminApproved = (Admin) Globals.accountApproved;

			admin.setAdminID(adminApproved.getAdminID());

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
		}

//		@Context HttpHeaders headers
		
		return null;
		
	}


//	@PathParam("ServiceProviderID")int adminID
//@Path("/{ServiceProviderID}")


//	@DELETE
//	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
//	public Response deleteAdmin()
//	{
////		@Context HttpHeaders headers
//
//		if(Globals.accountApproved instanceof Admin)
//		{
//			Admin admin = (Admin) Globals.accountApproved;
//
////			int rowCount = daoPrepared.deleteAdmin(admin.getAdminID());
//
//			if(rowCount>=1)
//			{
//
//				return Response.status(Status.OK)
//						.build();
//			}
//
//			if(rowCount == 0)
//			{
//
//				return Response.status(Status.NOT_MODIFIED)
//						.build();
//			}
//		}
//
//		return null;
//	}
	
	
//	@GET
//	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getAdmins()
//	{
//
//
//		List<Admin> list = daoPrepared.getAdmin();
//
//		GenericEntity<List<Admin>> listEntity = new GenericEntity<List<Admin>>(list){
//
//		};
//
//
//		if(list.size()<=0)
//		{
//			Response response = Response.status(Status.NO_CONTENT)
//					.entity(listEntity)
//					.build();
//
//			return response;
//
//		}else
//		{
//			Response response = Response.status(Status.OK)
//					.entity(listEntity)
//					.build();
//
//			return response;
//		}
//
//	}



	
//	@GET
//	@Path("/{adminID}")
//	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getAdmin(@PathParam("adminID")int adminID)
//	{
//
//		Admin admin = daoPrepared.getAdmin(adminID);
//
//		if(admin != null)
//		{
//
//			return Response.status(Status.OK)
//			.entity(admin)
//			.build();
//
//		} else
//		{
//
//			return Response.status(Status.NO_CONTENT)
//					.entity(admin)
//					.build();
//
//		}
//
//	}





//	@GET
//	@Path("Login")
//	@Produces(MediaType.APPLICATION_JSON)
//	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
//	public Response getAdminLoginDeprecated(@Context HttpHeaders header)
//	{
///*
//		try {
//			Thread.sleep(0);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}*/
//
//		//Get request headers
//		final MultivaluedMap<String, String> headers = header.getRequestHeaders();
//
//		//Fetch authorization header
//		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
//
//		//If no authorization information present; block access
//		if (authorization == null || authorization.isEmpty()) {
//
//			Response response = Response.status(Status.UNAUTHORIZED)
//					.entity(new ErrorNBSAPI(401, APIErrors.UPDATE_BY_WRONG_USER))
//					.build();
//
//			throw new NotAuthorizedException(response);
//		}
//
//		//Get encoded username and password
//
//
//
//
//		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
//
//
//
//		//Decode username and password
//		String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));
//
//
//		System.out.println("Username:Password" + usernameAndPassword);
//
//		//Split username and password tokens
//		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
//		final String username = tokenizer.nextToken();
//		final String password = tokenizer.nextToken();
//
//
//
//		Admin admin = daoPrepared.checkAdmin(null,username,password);
//
//
//
//		if(admin!= null)
//		{
//			admin.setPassword(null);
//
//			Response response;
//
//			response = Response.status(Status.OK)
//					.entity(admin)
//					.build();
//
//
//			return response;
//
//		} else
//		{
//
//			Response response = Response.status(Status.UNAUTHORIZED)
//					.entity(admin)
//					.build();
//
//			return response;
//
//		}
//
//	}







//	private static final String AUTHENTICATION_SCHEME = "Basic";
//	private static final String AUTHORIZATION_PROPERTY = "Authorization";
//
//	private boolean verifyAdminAccount(HttpHeaders header, int adminID)
//	{
//
//		boolean result = true;
//
//
//		//Get request headers
//		final MultivaluedMap<String, String> headers = header.getRequestHeaders();
//
//		//Fetch authorization header
//		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
//
//		//If no authorization information present; block access
//		if (authorization == null || authorization.isEmpty()) {
////                requestContext.abortWith(ACCESS_DENIED);
//
//			return false;
//		}
//
//		//Get encoded username and password
//
//
//
//
//		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
//
//
//
//		//Decode username and password
//		String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));
//
//
//		System.out.println("Username:Password" + usernameAndPassword);
//
//		//Split username and password tokens
//		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
//		final String username = tokenizer.nextToken();
//		final String password = tokenizer.nextToken();
//
//		Admin admin = daoPrepared.checkAdmin(null,username,password);
//
//		// Distributor account exist and is enabled
//		if(admin!=null)
//		{
//			// If code enters here implies that distributor account is used for update. So we need to check if
//			// the distributor is same as the one authorized.
//
//			if(admin.getAdminID()!=adminID)
//			{
//				// the user doing an update is not the same as the user whose profile is being updated so has to
//				// stop this operation, and should throw an unauthorized exception in this situation.
//				return false;
//			}
//		}
//
//		return true;
//	}



}
