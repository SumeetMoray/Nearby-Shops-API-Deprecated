package org.nearbyshops.RESTEndpointRoles;

import org.glassfish.jersey.internal.util.Base64;
import org.nearbyshops.DAOsPreparedRoles.EndUserDAOPrepared;
import org.nearbyshops.Globals.APIErrors;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndPoints.DistributorEndPoint;
import org.nearbyshops.ModelEndPoints.EndUserEndPoint;
import org.nearbyshops.ModelErrorMessages.ErrorNBSAPI;
import org.nearbyshops.ModelRoles.Distributor;
import org.nearbyshops.ModelRoles.EndUser;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;
import java.util.StringTokenizer;


@Path("/v1/EndUser")
public class EndUserResource {


	private EndUserDAOPrepared endUserDAOPrepared = Globals.endUserDAOPrepared;


	public EndUserResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEndUser(EndUser endUser)
	{

		endUser.setEnabled(false);
		endUser.setWaitlisted(false);
//		System.out.println(distributor.getName() + " | " + distributor.getDistributorID());
		
		int idOfInsertedRow = endUserDAOPrepared.saveEndUser(endUser);
		
//		System.out.println(distributor.getName() + " | " + distributor.getDistributorID());
	
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
	@RolesAllowed({GlobalConstants.ROLE_END_USER,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
	public Response updateEndUser(@PathParam("EndUserID")int endUserID,
									  EndUser endUser,
									  @Context HttpHeaders headers)
	{

		if(!verifyEndUserAccount(headers,endUserID))
		{

			Response responseError = Response.status(Status.FORBIDDEN)
					.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
					.build();

			throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
		}



		endUser.setEndUserID(endUserID);
		
//		System.out.println("distributorID: " + distributorID + " " + distributor.getName()
//		+ " " + distributor.getDistributorID());
		
		int rowCount = endUserDAOPrepared.updateEndUserNoPassword(endUser);
		
		
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
	@RolesAllowed({GlobalConstants.ROLE_END_USER,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
	public Response deleteEndUser(@PathParam("EndUserID")int endUserID,
									  @Context HttpHeaders headers)
	{


		if(!verifyEndUserAccount(headers,endUserID))
		{

			Response responseError = Response.status(Status.FORBIDDEN)
					.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
					.build();

			throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
		}



		int rowCount = endUserDAOPrepared.deleteEndUser(endUserID);
		
		
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
	

	// Public GET List
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllDistributors()
	{	
		List<EndUser> list = endUserDAOPrepared.getEndUsers(null,null,null,null,null,null);

		for(EndUser endUser: list)
		{
			endUser.setPassword(null);
			endUser.setUsername(null);
		}

		
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


	// Public GET single
	@GET
	@Path("/{EndUserID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDistributor(@PathParam("EndUserID")int endUserID,@QueryParam("Password")String password)
	{

		EndUser endUser = endUserDAOPrepared.getEndUser(endUserID);

		if(endUser!=null)
		{
			endUser.setUsername(null);
			endUser.setPassword(null);
		}


		if(endUser!= null)
		{

			Response response;

			response = Response.status(Status.OK)
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







//	@GET
//	@Path("/Private")
//	@Produces(MediaType.APPLICATION_JSON)
//	@RolesAllowed({GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
	public Response getDistributorsPrivate()
	{
		List<EndUser> list = endUserDAOPrepared.getEndUsers(null,null,null,null,null,null);




		for(EndUser endUser: list)
		{
			endUser.setPassword(null);
		}

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
	@Path("/Private")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
	public Response getEndUser(
			@QueryParam("DistributorID")Integer distributorID,
			@QueryParam("IsEnabled") Boolean isEnabled,
			@QueryParam("IsWaitlisted") Boolean isWaitlisted,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
			@QueryParam("metadata_only")Boolean metaonly)
	{

		int set_limit = 30;
		int set_offset = 0;
		final int max_limit = 100;

		if(limit!= null)
		{

			if (limit >= max_limit) {

				set_limit = max_limit;
			}
			else
			{

				set_limit = limit;
			}

		}

		if(offset!=null)
		{
			set_offset = offset;
		}

		EndUserEndPoint endPoint = endUserDAOPrepared.getEndpointMetadata(distributorID,
																isEnabled,isWaitlisted);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<EndUser> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					endUserDAOPrepared.getEndUsers(
							distributorID,
							isEnabled,isWaitlisted,
							sortBy,set_limit,set_offset
					);


			for(EndUser endUser: list)
			{
				endUser.setPassword(null);
			}


			endPoint.setResults(list);
		}


		//Marker

		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}






	@GET
	@Path("Login")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_END_USER})
	public Response getEndUserLogin(@Context HttpHeaders header)
	{

		//Get request headers
		final MultivaluedMap<String, String> headers = header.getRequestHeaders();

		//Fetch authorization header
		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

		//If no authorization information present; block access
		if (authorization == null || authorization.isEmpty()) {

			Response response = Response.status(Status.UNAUTHORIZED)
										.entity(new ErrorNBSAPI(401,APIErrors.UPDATE_BY_WRONG_USER))
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



		EndUser endUser = endUserDAOPrepared.checkEndUser(null,username,password);



		if(endUser!= null)
		{
			endUser.setPassword(null);

			Response response;

			response = Response.status(Status.OK)
					.entity(endUser)
					.build();


			return response;

		} else
		{

			Response response = Response.status(Status.UNAUTHORIZED)
					.entity(endUser)
					.build();

			return response;

		}

	}




	// @PathParam("DistributorID")int distributorID,

//	@GET
//	@Path("/Validate")
	/*public Response validateDistributor(@QueryParam("Password")String password,@QueryParam("Username")String userName,@QueryParam("ID")Integer id)
	{

		boolean isValid = false;

		Distributor tempDistributor = null;

		if(id!=null)
		{
			tempDistributor = endUserDAOPrepared.getDistributorPassword(id,null);

			System.out.println(id + " : " + userName);

		}else if(userName !=null)
		{
			tempDistributor = endUserDAOPrepared.getDistributorPassword(null,userName);
		}


		if(tempDistributor!=null && tempDistributor.getPassword()!=null && tempDistributor.getPassword().equals(password))
		{
			isValid = true;
		}

		tempDistributor.setPassword(null);


		if(isValid)
		{
			Response response = Response.status(Status.OK)
					.entity(tempDistributor)
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
*/

	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final String AUTHORIZATION_PROPERTY = "Authorization";

	private boolean verifyEndUserAccount(HttpHeaders header, int endUserID)
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


		EndUser endUser = endUserDAOPrepared.checkEndUser(null,username,password);
		// Distributor account exist and is enabled
		if(endUser!=null && endUser.getEnabled())
		{
			// If code enters here implies that distributor account is used for update. So we need to check if
			// the distributor is same as the one authorized.

			if(endUser.getEndUserID()!=endUserID)
			{
				// the user doing an update is not the same as the user whose profile is being updated so has to
				// stop this operation, and should throw an unauthorized exception in this situation.
				return false;
			}
		}

		return true;
	}

}
