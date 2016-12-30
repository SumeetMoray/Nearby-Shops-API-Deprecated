package org.nearbyshops.RESTEndpointRoles;

import net.coobird.thumbnailator.Thumbnails;
import org.jvnet.hk2.annotations.Service;
import org.nearbyshops.DAOsPrepared.ShopDAO;
import org.nearbyshops.DAOsPreparedRoles.ShopStaffDAOPrepared;
import org.nearbyshops.Globals.APIErrors;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Image;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelErrorMessages.ErrorNBSAPI;
import org.nearbyshops.ModelRoles.ShopAdmin;
import org.nearbyshops.ModelRoles.ShopStaff;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Path("/v1/ShopStaff")
@Service
public class ShopStaffResource {

	private ShopStaffDAOPrepared daoPrepared = Globals.shopStaffDAOPrepared;
	private ShopDAO shopDAO = Globals.shopDAO;


//	@Inject
//	private ShopStaffDAOPrepared daoPrepared;

//	@Inject
//	ShopDAO shopDAO;


	public ShopStaffResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(GlobalConstants.ROLE_SHOP_ADMIN)
	public Response createShopStaff(ShopStaff shopStaff)
	{

//		staff.setEnabled(true);
//		staff.setWaitlisted(false);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
			shopStaff.setShopID(shop.getShopID());
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}



		int idOfInsertedRow = daoPrepared.saveShopStaff(shopStaff);

		shopStaff.setStaffID(idOfInsertedRow);

		if(idOfInsertedRow >=1)
		{


			return Response.status(Status.CREATED)
					.location(URI.create("/api/staff/" + idOfInsertedRow))
					.entity(shopStaff)
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



//	@Path("/{StaffID}")
	//@PathParam("StaffID")int staffID,
	
	@PUT
	@Path("/UpdateByShopAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response updateSTAFF(ShopStaff shopStaff)
	{
		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
			shopStaff.setShopID(shop.getShopID());

//			if(shopStaff.getShopID()!=shop.getShopID())
//			{
//				// an attempt to update the shop which shop admin does not own
//				throw new ForbiddenException("Not permitted !");
//			}
		}


		int rowCount = daoPrepared.updateShopStaff(shopStaff);

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



	@PUT
	@Path("/UpdateBySelf")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_STAFF_DISABLED})
	public Response updateBySelf(ShopStaff staff)
	{
		//@Path("/UpdateBySelf/{StaffID}")

		if(Globals.accountApproved instanceof ShopStaff)
		{
			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;

			if(shopStaff.getStaffID()!=staff.getStaffID())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			staff.setShopID(shopStaff.getShopID());
		}



		int rowCount = daoPrepared.updateStaffBySelf(staff);

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
	@Path("/{StaffID}")
	//@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF_DISABLED})
	public Response deleteStaff(@PathParam("StaffID")int staffID)
	{
		//		@Context HttpHeaders headers


		if(Globals.accountApproved instanceof ShopAdmin)
		{

			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());

			ShopStaff shopStaff = daoPrepared.getShopIDforShopStaff(staffID);

			if(shopStaff.getShopID()!=shop.getShopID())
			{
				// an attempt to update the staff member who does not belong to the shop you own
				throw new ForbiddenException("Not permitted. An attempt to update the staff member who does not belong to the shop you own !");
			}
		}


		if(Globals.accountApproved instanceof ShopStaff)
		{
			if(((ShopStaff) Globals.accountApproved).getStaffID()!=staffID)
			{
				// an attempt to delete the account of other staff member. Which is not permitted !
				Response responseError = Response.status(Status.FORBIDDEN)
						.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
						.build();

				throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
			}
		}


		int rowCount = daoPrepared.deleteShopStaff(staffID);
		
		
		if(rowCount>=1)
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
	
	
	@GET
	@Path("/ForShopAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(GlobalConstants.ROLE_SHOP_ADMIN)
	public Response getShopStaff(@QueryParam("IsEnabled") Boolean isEnabled)
	{
		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());

			List<ShopStaff> list = daoPrepared.getShopStaffForShopAdmin(isEnabled,shop.getShopID());

			GenericEntity<List<ShopStaff>> listEntity = new GenericEntity<List<ShopStaff>>(list){
			};


			if(list.size()<=0)
			{
				return Response.status(Status.NO_CONTENT)
						.build();
			}else
			{
				return Response.status(Status.OK)
						.entity(listEntity)
						.build();
			}
		}
		else
		{
			throw new ForbiddenException("Not Permitted ! ");
		}
	}
	
	
	/*@GET
	@Path("/{staffID}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
	public Response getStaffForSelf(@PathParam("staffID")int staffID)
	{

		Staff staff = daoPrepared.getStaffForSelf(staffID);
		
		if(staff != null)
		{

			return Response.status(Status.OK)
			.entity(staff)
			.build();
			
		} else 
		{

			return Response.status(Status.NO_CONTENT)
					.build();
			
		}
		
	}*/





	@GET
	@Path("/CheckUsernameExists/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkUsername(@PathParam("username")String username)
	{
		// Roles allowed not used for this method due to performance and effeciency requirements. Also
		// this endpoint doesnt required to be secured as it does not expose any confidential information

		boolean result = daoPrepared.checkUsernameExists(username);

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
	@RolesAllowed({GlobalConstants.ROLE_SHOP_STAFF_DISABLED})
	public Response getStaffLogin()
	{

		ShopStaff staff = null;

		if(Globals.accountApproved instanceof ShopStaff)
		{
			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
			staff = daoPrepared.getShopStaffForSelf(shopStaff.getStaffID());
		}

		if(staff != null)
		{

			return Response.status(Status.OK)
					.entity(staff)
					.build();

		}
		else
		{

			return Response.status(Status.UNAUTHORIZED)
					.build();

		}

	}





	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/ShopStaff");
	private static final double MAX_IMAGE_SIZE_MB = 2;


	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF_DISABLED})
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
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF_DISABLED})
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
