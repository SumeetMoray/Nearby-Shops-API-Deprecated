package org.nearbyshops.RESTEndpointRoles;

import net.coobird.thumbnailator.Thumbnails;
import org.nearbyshops.DAOsPrepared.ShopDAO;
import org.nearbyshops.DAOsPreparedRoles.EndUserDAONew;
import org.nearbyshops.DAOsPreparedRoles.ShopAdminDAO;
import org.nearbyshops.Globals.APIErrors;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Image;
import org.nearbyshops.ModelErrorMessages.ErrorNBSAPI;
import org.nearbyshops.ModelRoles.EndUser;
import org.nearbyshops.ModelRoles.Endpoints.EndUserEndPoint;
import org.nearbyshops.ModelRoles.Endpoints.ShopAdminEndPoint;
import org.nearbyshops.ModelRoles.ShopAdmin;
import org.nearbyshops.ModelRoles.Staff;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


@Path("/v1/EndUser")
public class EndUserResourceNew {

	private EndUserDAONew endUserDAONew = Globals.endUserDAONew;


	public EndUserResourceNew() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	public Response postEndUser(EndUser endUser)
	{

		int idOfInsertedRow = Globals.endUserDAONew.saveEndUser(endUser);
		endUser.setEndUserID(idOfInsertedRow);


		if(idOfInsertedRow >=1)
		{


			return Response.status(Status.CREATED)
					.location(URI.create("/api/v1/EndUser/" + idOfInsertedRow))
					.entity(endUser)
					.build();
			
		}else if(idOfInsertedRow <=0)
		{

			//Response.status(Status.CREATED).location(arg0)
			
			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}
		
		return null;
	}



	
//	@PUT
//	@Path("/{EndUserID}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
//	public Response updateEndUser(@PathParam("EndUserID")int endUserID, EndUser endUser)
//	{
//
//		// the endpoint for updating ShopAdmin profile by the admin
//
//		if(Globals.accountApproved instanceof Staff) {
//			// checking permission
//			Staff staff = (Staff) Globals.accountApproved;
//			if (!staff.isApproveEndUserAccounts())
//			{
//				// the staff member doesnt have persmission to post Item Category
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
//
//
//		endUser.setEndUserID(endUserID);
//
//		int rowCount = endUserDAONew.updateBySelf(endUser);
//
//		if(rowCount >= 1)
//		{
//
//			return Response.status(Status.OK)
//					.entity(null)
//					.build();
//		}
//		if(rowCount == 0)
//		{
//
//			return Response.status(Status.NOT_MODIFIED)
//					.entity(null)
//					.build();
//		}
//
//		return null;
//	}




	@PUT
	@Path("/UpdateBySelf/{EndUserID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_END_USER})
	public Response updateBySelf(@PathParam("EndUserID")int endUserID, EndUser endUser)
	{

		endUser.setEndUserID(endUserID);

		if(Globals.accountApproved instanceof EndUser)
		{
			if(endUserID != ((EndUser) Globals.accountApproved).getEndUserID())
			{
				// update by wrong account . Throw an Exception
				Response responseError = Response.status(Status.FORBIDDEN)
						.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
						.build();

				throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
			}
		}


		int rowCount = endUserDAONew.updateBySelf(endUser);

		if(rowCount >= 1)
		{

			return Response.status(Status.OK)
					.build();
		}
		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}


		return null;
	}






	@DELETE
	@Path("/{EndUserID}")
	@RolesAllowed({GlobalConstants.ROLE_END_USER})
	public Response deleteShopAdmin(@PathParam("EndUserID")int endUserID)
	{

		if(Globals.accountApproved instanceof EndUser) {
			// checking permission
			EndUser endUser = (EndUser) Globals.accountApproved;
			if (endUser.getEndUserID()!=endUserID)
			{
				// an attempt to delete the account of another person
				throw new ForbiddenException("Not Permitted");
			}
		}


		int rowCount = endUserDAONew.deleteEndUser(endUserID);
		
		
		if(rowCount>=1)
		{

			return Response.status(Status.OK)
					.build();
		}
		
		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}


		return null;
	}


	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response getEndUserForStaff(@QueryParam("SearchString") String searchString,
									 @QueryParam("SortBy") String sortBy,
									 @QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset,
									 @QueryParam("metadata_only")Boolean metaonly)
	{


		if(Globals.accountApproved instanceof Staff) {
			// checking permission
			Staff staff = (Staff) Globals.accountApproved;
			if (!staff.isApproveEndUserAccounts())
			{
				// the staff member doesnt have persmission to post Item Category
				throw new ForbiddenException("Not Permitted");
			}
		}



		final int max_limit = 100;

		if(limit!=null)
		{
			if(limit>=max_limit)
			{
				limit = max_limit;
			}
		}
		else
		{
			limit = 30;
		}



		EndUserEndPoint endPoint = endUserDAONew.getEndpointMetadata(searchString);


		endPoint.setLimit(limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(offset);


		ArrayList<EndUser> endUserArrayList = null;


		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/


		if(metaonly==null || (!metaonly)) {


			endUserArrayList = endUserDAONew.getEndUserForStaff(searchString,sortBy,
					limit,offset);

			endPoint.setResults(endUserArrayList);
		}


		//Marker
		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}





//	@GET
//	@Path("/ForPublic")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getEndUserPublic(@QueryParam("EndUserID")Integer shopID,
//									   @QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset)
//	{
//
//		final int max_limit = 100;
//
//		if(limit!=null)
//		{
//			if(limit>=max_limit)
//			{
//				limit = max_limit;
//			}
//		}
//		else
//		{
//			limit = 30;
//		}
//
//
//
//
////		ShopAdminEndPoint endPoint = shopAdminDAO.getEndpointMetadata(enabled,waitlisted,searchString);
//
//		EndUserEndPoint endPoint = endUserDAONew.getEndUserPublic(null,null,
//				limit,offset);
//
//
//		endPoint.setLimit(limit);
//		endPoint.setMax_limit(max_limit);
//		if(offset!=null)
//		{
//			endPoint.setOffset(offset);
//		}
//
//
//
//		/*try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}*/
//
//
//
//		//Marker
//		return Response.status(Status.OK)
//				.entity(endPoint)
//				.build();
//	}
//



	@GET
	@Path("/CheckUsernameExists/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkUsernameExists(@PathParam("username")String username)
	{
		// Roles allowed not used for this method due to performance and effeciency requirements. Also
		// this endpoint doesnt required to be secured as it does not expose any confidential information

		boolean result = endUserDAONew.checkUsernameExists(username);

		if(result)
		{
			return Response.status(Status.OK)
					.build();

		} else
		{
			return Response.status(Status.NO_CONTENT)
					.build();
		}
	}





	@GET
	@Path("Login")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_END_USER})
	public Response getEndUserLogin()
	{
		EndUser endUser = null;

		if(Globals.accountApproved instanceof EndUser)
		{
			endUser = endUserDAONew
					.getEndUserForLogin(((EndUser) Globals.accountApproved).getEndUserID());
		}


		if(endUser!= null)
		{

			return Response.status(Status.OK)
					.entity(endUser)
					.build();

		} else
		{

			return Response.status(Status.UNAUTHORIZED)
					.entity(null)
					.build();

		}

	}




	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/Enduser");
	private static final double MAX_IMAGE_SIZE_MB = 2;


	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
	@RolesAllowed({GlobalConstants.ROLE_END_USER})
	public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
							 @QueryParam("PreviousImageName") String previousImageName
	) throws Exception
	{


		if(previousImageName!=null)
		{
			Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + previousImageName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + previousImageName + ".jpg"));
		}


		File theDir = new File(BASE_DIR.toString());

		// if the directory does not exist, create it
		if (!theDir.exists()) {

			System.out.println("Creating directory: " + BASE_DIR.toString());

			boolean result = false;

			try{
				theDir.mkdir();
				result = true;
			}
			catch(Exception se){
				//handle it
			}
			if(result) {
				System.out.println("DIR created");
			}
		}



		String fileName = "" + System.currentTimeMillis();

		// Copy the file to its location.
		long filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

		if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
		{
			// delete file if it exceeds the file size limit
			Files.deleteIfExists(BASE_DIR.resolve(fileName));

			return Response.status(Status.EXPECTATION_FAILED).build();
		}


		createThumbnails(fileName);


		Image image = new Image();
		image.setPath(fileName);

		// Return a 201 Created response with the appropriate Location header.

		return Response.status(Status.CREATED).location(URI.create("/api/Images/" + fileName)).entity(image).build();
	}



	private void createThumbnails(String filename)
	{
		try {

				Thumbnails.of(BASE_DIR.toString() + "/" + filename)
					.size(300,300)
					.outputFormat("jpg")
					.toFile(new File(BASE_DIR.toString() + "/" + "three_hundred_" + filename));

						//.toFile(new File("five-" + filename + ".jpg"));

						//.toFiles(Rename.PREFIX_DOT_THUMBNAIL);


				Thumbnails.of(BASE_DIR.toString() + "/" + filename)
					.size(500,500)
					.outputFormat("jpg")
					.toFile(new File(BASE_DIR.toString() + "/" + "five_hundred_" + filename));



		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@GET
	@Path("/Image/{name}")
	@Produces("image/jpeg")
	public InputStream getImage(@PathParam("name") String fileName) {

		//fileName += ".jpg";
		java.nio.file.Path dest = BASE_DIR.resolve(fileName);

		if (!Files.exists(dest)) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}


		try {
			return Files.newInputStream(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}


	@DELETE
	@Path("/Image/{name}")
	@RolesAllowed({GlobalConstants.ROLE_END_USER})
	public Response deleteImageFile(@PathParam("name")String fileName)
	{

		boolean deleteStatus = false;

		Response response;

		System.out.println("Filename: " + fileName);

		try {


			//Files.delete(BASE_DIR.resolve(fileName));
			deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

			// delete thumbnails
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(!deleteStatus)
		{
			response = Response.status(Status.NOT_MODIFIED).build();

		}else
		{
			response = Response.status(Status.OK).build();
		}

		return response;
	}

}
