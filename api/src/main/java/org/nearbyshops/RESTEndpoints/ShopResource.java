package org.nearbyshops.RESTEndpoints;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.coobird.thumbnailator.Thumbnails;
import org.nearbyshops.DAOsPrepared.ShopDAO;
import org.nearbyshops.Globals.APIErrors;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Image;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelEndPoints.ShopEndPoint;
import org.nearbyshops.ModelErrorMessages.ErrorNBSAPI;
import org.nearbyshops.ModelRoles.ShopAdmin;
import org.nearbyshops.Utility.GeoLocation;


@Path("/v1/Shop")
@Produces(MediaType.APPLICATION_JSON)
public class ShopResource {

//	private GeoLocation center;
//	private GeoLocation[] minMaxArray;
//	private GeoLocation pointOne;
//	private GeoLocation pointTwo;


	private ShopDAO shopDAO = Globals.shopDAO;


	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_ADMIN})
	public Response saveShop(Shop shop)
	{

		// generate bounding coordinates for the shop based on its center and delivery range
//		center = GeoLocation.fromDegrees(shop.getLatCenter(),shop.getLonCenter());
//		minMaxArray = center.boundingCoordinates(shop.getDeliveryRange(),6371.01);
//
//		pointOne = minMaxArray[0];
//		pointTwo = minMaxArray[1];
//
//		shop.setLatMin(pointOne.getLatitudeInDegrees());
//		shop.setLonMin(pointOne.getLongitudeInDegrees());
//		shop.setLatMax(pointTwo.getLatitudeInDegrees());
//		shop.setLonMax(pointTwo.getLongitudeInDegrees());


		if (Globals.accountApproved instanceof ShopAdmin)
		{
			// We need to make sure that SHop Admin Creates shop only on his account not on the account of others
			shop.setShopAdminID(((ShopAdmin) Globals.accountApproved).getShopAdminID());
		}


		int idOfInsertedRow = shopDAO.insertShop(shop);

		shop.setShopID(idOfInsertedRow);
		
		
		if(idOfInsertedRow >=1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.location(URI.create("/api/Shop/" + idOfInsertedRow))
					.entity(shop)
					.build();
			
			return response;
			
		}else if(idOfInsertedRow <= 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
			
			return response;
		}
		
		
		return null;	
		
	}
	

	@PUT
	@Path("/{ShopID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_ADMIN})
	public Response updateShop(Shop shop, @PathParam("ShopID")int ShopID)
	{

		// generate bounding coordinates for the shop based on its center and delivery range
//		center = GeoLocation.fromDegrees(shop.getLatCenter(),shop.getLonCenter());
//		minMaxArray = center.boundingCoordinates(shop.getDeliveryRange(),6371.01);
//
//		pointOne = minMaxArray[0];
//		pointTwo = minMaxArray[1];
//
//		shop.setLatMin(pointOne.getLatitudeInDegrees());
//		shop.setLonMin(pointOne.getLongitudeInDegrees());
//		shop.setLatMax(pointTwo.getLatitudeInDegrees());
//		shop.setLonMax(pointTwo.getLongitudeInDegrees());

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			if(ShopID != ((ShopAdmin) Globals.accountApproved).getShopID())
			{

				// update by wrong account . Throw an Exception
				Response responseError = Response.status(Status.FORBIDDEN)
						.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_SHOP_OWNER))
						.build();

				throw new ForbiddenException("You are not allowed to update the shop you do not own !",responseError);
			}

		}




		shop.setShopID(ShopID);
		
		int rowCount = shopDAO.updateShop(shop);
		
		if(rowCount >= 1)
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();
			
			return response;
		}
		if(rowCount <= 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
			
			return response;
		}

		return null;
	}
	
	
	@DELETE
	@Path("/{ShopID}")
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_ADMIN})
	public Response deleteShop(@PathParam("ShopID")int shopID)
	{
		if(Globals.accountApproved instanceof ShopAdmin)
		{
			if(shopID != ((ShopAdmin) Globals.accountApproved).getShopID())
			{

				// update by wrong account . Throw an Exception
				Response responseError = Response.status(Status.FORBIDDEN)
						.entity(new ErrorNBSAPI(403, APIErrors.DELETE_BY_WRONG_SHOP_OWNER))
						.build();

				throw new ForbiddenException(APIErrors.DELETE_BY_WRONG_SHOP_OWNER,responseError);
			}

		}

		
		int rowCount = shopDAO.deleteShop(shopID);
	
		
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
	


//	@GET
//	@Path("/Deprecated")
//	@Produces(MediaType.APPLICATION_JSON)
	public Response getShops(@QueryParam("DistributorID")Integer distributorID,
							 @QueryParam("LeafNodeItemCategoryID")Integer itemCategoryID,
							 @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
							 @QueryParam("deliveryRangeMax")Double deliveryRangeMax,
							 @QueryParam("deliveryRangeMin")Double deliveryRangeMin,
							 @QueryParam("proximity")Double proximity,
							 @QueryParam("SearchString") String searchString,
							 @QueryParam("SortBy") String sortBy,
							 @QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset)
	{
		
		List<Shop> list = shopDAO.getShops(
				itemCategoryID,
				latCenter, lonCenter,
				deliveryRangeMin,deliveryRangeMax,
				proximity,searchString,sortBy,limit,offset
		);
				
		
		GenericEntity<List<Shop>> listEntity = new GenericEntity<List<Shop>>(list){
			
			};
		
			
			if(list.size()<=0)
			{

				return Response.status(Status.NO_CONTENT)
						.entity(listEntity)
						.build();
				
			}else
			{

				return Response.status(Status.OK)
						.entity(listEntity)
						.build();
			}
	
	}





	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShopItems(
			@QueryParam("DistributorID")Integer distributorID,
			@QueryParam("LeafNodeItemCategoryID")Integer itemCategoryID,
			@QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
			@QueryParam("deliveryRangeMax")Double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")Double deliveryRangeMin,
			@QueryParam("proximity")Double proximity,
			@QueryParam("SearchString") String searchString,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset,
			@QueryParam("metadata_only")Boolean metaonly
	)
	{

		int set_limit = 30;
		int set_offset = 0;
		final int max_limit = 100;


		if(limit!= null) {

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


		ShopEndPoint endPoint = shopDAO.getEndPointMetadata(itemCategoryID,
				latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity,searchString);


		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);


		ArrayList<Shop> shopsList = null;


		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/


		if(metaonly==null || (!metaonly)) {


			shopsList = shopDAO.getShops(itemCategoryID,
					latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity,searchString,sortBy,
					limit,offset);

			endPoint.setResults(shopsList);
		}


		//Marker
		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}





	@GET
	@Path("/FilterByItemCat/{ItemCategoryID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response filterShopsByItemCategory(
							 @PathParam("ItemCategoryID")Integer itemCategoryID,
							 @QueryParam("DistributorID")Integer distributorID,
							 @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
							 @QueryParam("deliveryRangeMax")Double deliveryRangeMax,
							 @QueryParam("deliveryRangeMin")Double deliveryRangeMin,
							 @QueryParam("proximity")Double proximity,
							 @QueryParam("SortBy") String sortBy,
							 @QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset,
							 @QueryParam("metadata_only")Boolean metaonly)
	{


		int set_limit = 30;
		int set_offset = 0;
		final int max_limit = 100;


		if(limit!= null) {

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


		ShopEndPoint endPoint = shopDAO.endPointMetadataFilterShops(itemCategoryID,distributorID,
				 latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity);


		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);


		ArrayList<Shop> shopsList = null;


		if(metaonly==null || (!metaonly)) {


			shopsList = shopDAO.filterShopsByItemCategory(
					itemCategoryID, distributorID,
					latCenter,lonCenter,
					deliveryRangeMin,deliveryRangeMax,
					proximity,sortBy, limit,offset
			);

			endPoint.setResults(shopsList);
		}

/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/


		//Marker
		return Response.status(Status.OK)
				.entity(endPoint)
				.build();

	}


	

	@GET
	@Path("/{ShopID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShop(@PathParam("ShopID")int shopID,
							@QueryParam("latCenter")double latCenter, @QueryParam("lonCenter")double lonCenter)
	{
		Shop shop = shopDAO.getShop(shopID,latCenter,lonCenter);
		
		if(shop!= null)
		{

			return Response.status(Status.OK)
			.entity(shop)
			.build();
			
		} else 
		{

			return Response.status(Status.NO_CONTENT)
					.entity(shop)
					.build();
			
		}	
	}





	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/Shop");
	private static final double MAX_IMAGE_SIZE_MB = 2;


	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
	@RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY_SELF,GlobalConstants.ROLE_SHOP_ADMIN})
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
	@RolesAllowed({GlobalConstants.ROLE_DELIVERY_GUY_SELF,GlobalConstants.ROLE_SHOP_ADMIN})
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
