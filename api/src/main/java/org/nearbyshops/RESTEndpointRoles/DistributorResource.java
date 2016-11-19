package org.nearbyshops.RESTEndpointRoles;

import java.net.URI;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import org.glassfish.jersey.internal.util.Base64;
import org.nearbyshops.CustomSecurityContext;
import org.nearbyshops.DAOsPreparedRoles.DistributorDAOPrepared;
import org.nearbyshops.Globals.APIErrors;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelEndPoints.DistributorEndPoint;
import org.nearbyshops.ModelEndPoints.ItemEndPoint;
import org.nearbyshops.ModelErrorMessages.ErrorNBSAPI;
import org.nearbyshops.ModelRoles.Distributor;


@Path("/v1/Distributor")
public class DistributorResource {


	private DistributorDAOPrepared distributorDAOPrepared  = Globals.distributorDAOPrepared;

	
	public DistributorResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDistributor(Distributor distributor)
	{

		distributor.setEnabled(false);
		distributor.setWaitlisted(false);
//		System.out.println(distributor.getName() + " | " + distributor.getDistributorID());
		
		int idOfInsertedRow = distributorDAOPrepared.saveDistributor(distributor);
		
//		System.out.println(distributor.getName() + " | " + distributor.getDistributorID());
	
		distributor.setDistributorID(idOfInsertedRow);

		distributor.setPassword(null);
		
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
	@RolesAllowed({GlobalConstants.ROLE_DISTRIBUTOR,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
	public Response updateDistributor(@PathParam("DistributorID")int distributorID,
									  Distributor distributor,
									  @Context HttpHeaders headers,@Context SecurityContext sc)
	{


		if(sc instanceof CustomSecurityContext)
		{
			System.out.println("SC instance of CustomSecurityContext");
			Object object = ((CustomSecurityContext)sc).getAccountInformation();

			if(object instanceof Distributor)
			{
				System.out.println("Object instance of Distributor");

				if(((Distributor)object).getDistributorID()!=distributorID)
				{
					// attempt to update the account of another distributor

					Response responseError = Response.status(Status.FORBIDDEN)
							.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
							.build();

					throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
				}
			}
		}




		if(Globals.object instanceof Distributor)
		{
			if(((Distributor)Globals.object).getDistributorID()!=distributorID)
			{
				// attempt to update the account of another distributor

				Response responseError = Response.status(Status.FORBIDDEN)
						.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
						.build();

				throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
			}
		}

/*
		if(!verifyDistributorAccount(headers,distributorID))
		{

			Response responseError = Response.status(Status.FORBIDDEN)
					.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
					.build();

			throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
		}*/



		distributor.setDistributorID(distributorID);
		
//		System.out.println("distributorID: " + distributorID + " " + distributor.getName()
//		+ " " + distributor.getDistributorID());
		
		int rowCount = distributorDAOPrepared.updateDistributorNoPassword(distributor);
		
		
		if(rowCount >= 1)
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}
		
		
		return null;
		
	}



	@DELETE
	@Path("/{DistributorID}")
	@RolesAllowed({GlobalConstants.ROLE_DISTRIBUTOR,GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
	public Response deleteDistributor(@PathParam("DistributorID")int distributorID,
									  @Context HttpHeaders headers)
	{


		if(!verifyDistributorAccount(headers,distributorID))
		{

			Response responseError = Response.status(Status.FORBIDDEN)
					.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
					.build();

			throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
		}



		int rowCount = distributorDAOPrepared.deleteDistributor(distributorID);
		
		
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
		List<Distributor> list = distributorDAOPrepared.getDistributors(null,null,null,null,null,null);

		for(Distributor distributor: list)
		{
			distributor.setPassword(null);
			distributor.setUsername(null);
		}

		
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


	// Public GET single
	@GET
	@Path("/{DistributorID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDistributor(@PathParam("DistributorID")int distributorID,@QueryParam("Password")String password)
	{

		Distributor distributor = distributorDAOPrepared.getDistributor(distributorID);

		if(distributor!=null)
		{
			distributor.setUsername(null);
			distributor.setPassword(null);
		}


		if(distributor!= null)
		{

			Response response;

			response = Response.status(Status.OK)
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







//	@GET
//	@Path("/Private")
//	@Produces(MediaType.APPLICATION_JSON)
//	@RolesAllowed({GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
	public Response getDistributorsPrivate()
	{
		List<Distributor> list = distributorDAOPrepared.getDistributors(null,null,null,null,null,null);




		for(Distributor distributor: list)
		{
			distributor.setPassword(null);
		}

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
	@Path("/Private")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_ADMIN})
	public Response getItems(
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

		DistributorEndPoint endPoint = distributorDAOPrepared.getEndpointMetadata(distributorID,
																isEnabled,isWaitlisted);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<Distributor> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					distributorDAOPrepared.getDistributors(
							distributorID,
							isEnabled,isWaitlisted,
							sortBy,set_limit,set_offset
					);


			for(Distributor distributor: list)
			{
				distributor.setPassword(null);
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
	@RolesAllowed({GlobalConstants.ROLE_DISTRIBUTOR})
	public Response getDistributorLogin(@Context HttpHeaders header)
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



		Distributor distributor = distributorDAOPrepared.checkDistributor(null,username,password);



		if(distributor!= null)
		{
			distributor.setPassword(null);

			Response response;

			response = Response.status(Status.OK)
					.entity(distributor)
					.build();


			return response;

		} else
		{

			return Response.status(Status.UNAUTHORIZED)
					.entity(distributor)
					.build();

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
			tempDistributor = distributorDAOPrepared.getDistributorPassword(id,null);

			System.out.println(id + " : " + userName);

		}else if(userName !=null)
		{
			tempDistributor = distributorDAOPrepared.getDistributorPassword(null,userName);
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

	private boolean verifyDistributorAccount(HttpHeaders header, int distributorID)
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


		Distributor distributor = distributorDAOPrepared.checkDistributor(null,username,password);
		// Distributor account exist and is enabled
		if(distributor!=null && distributor.getEnabled())
		{
			// If code enters here implies that distributor account is used for update. So we need to check if
			// the distributor is same as the one authorized.

			if(distributor.getDistributorID()!=distributorID)
			{
				// the user doing an update is not the same as the user whose profile is being updated so has to
				// stop this operation, and should throw an unauthorized exception in this situation.
				return false;
			}
		}

		return true;
	}

}
