package org.nearbyshops.RESTEndpoints;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jdk.nashorn.internal.objects.Global;
import net.coobird.thumbnailator.Thumbnails;
import org.nearbyshops.DAOsPrepared.ItemDAO;
import org.nearbyshops.DAOsPrepared.ItemDAOJoinOuter;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Image;
import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelEndPoints.ItemEndPoint;


@Path("/v1/Item")
public class ItemResource {

	private ItemDAO itemDAO = Globals.itemDAO;
	private ItemDAOJoinOuter itemDAOJoinOuter = Globals.itemDAOJoinOuter;


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response saveItem(@Valid Item item)
	{	
		int idOfInsertedRow = itemDAO.saveItem(item);
		
		item.setItemID(idOfInsertedRow);
		
		if(idOfInsertedRow >=1)
		{

			return Response.status(Status.CREATED)
					.location(URI.create("/api/Item/" + idOfInsertedRow))
					.entity(item)
					.build();
			
		}else if(idOfInsertedRow <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}
		
		
		return null;
	}



	@PUT
	@Path("/ChangeParent/{ItemID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response changeParent(Item item, @PathParam("ItemID")int itemID)
	{

		item.setItemID(itemID);

		//System.out.println("ItemCategoryID: " + itemCategoryID + " " + itemCategory.getCategoryName()
		//+ " " + itemCategory.getCategoryDescription());

		int rowCount = itemDAO.changeParent(item);

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
	@Path("/ChangeParent")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response changeParentBulk(List<Item> itemList)
	{
		int rowCountSum = 0;

//		for(Item item : itemList)
//		{
//			rowCountSum = rowCountSum + itemDAO.updateItem(item);
//		}

		rowCountSum = itemDAO.changeParentBulk(itemList);

		if(rowCountSum ==  itemList.size())
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		else if( rowCountSum < itemList.size() && rowCountSum > 0)
		{

			return Response.status(Status.PARTIAL_CONTENT)
					.entity(null)
					.build();
		}
		else if(rowCountSum == 0 ) {

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}



	@PUT
	@Path("/{ItemID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response updateItem(Item item, @PathParam("ItemID")int itemID)
	{
			
		item.setItemID(itemID);
	
		//System.out.println("ItemCategoryID: " + itemCategoryID + " " + itemCategory.getCategoryName()
		//+ " " + itemCategory.getCategoryDescription());
		
		int rowCount = itemDAO.updateItem(item);
		
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
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response updateItemBulk(List<Item> itemList)
	{
		int rowCountSum = 0;

//		for(Item item : itemList)
//		{
//			rowCountSum = rowCountSum + itemDAO.updateItem(item);
//		}

		rowCountSum = itemDAO.updateItemBulk(itemList);

		if(rowCountSum ==  itemList.size())
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		else if( rowCountSum < itemList.size() && rowCountSum > 0)
		{

			return Response.status(Status.PARTIAL_CONTENT)
					.entity(null)
					.build();
		}
		else if(rowCountSum == 0 ) {

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}
	
	
	@DELETE
	@Path("/{ItemID}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response deleteItem(@PathParam("ItemID")int itemID)
	{
		
		int rowCount = itemDAO.deleteItem(itemID);
		
		
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
	
	
	

//	@Path("/Deprecated")
//	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(
			@QueryParam("ItemCategoryID")Integer itemCategoryID,
			@QueryParam("ShopID")Integer shopID,
			@QueryParam("latCenter") Double latCenter, @QueryParam("lonCenter") Double lonCenter,
			@QueryParam("deliveryRangeMax")Double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")Double deliveryRangeMin,
			@QueryParam("proximity")Double proximity,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset)
	
	{
				
		//Marker
		
		List<Item> list = 
				itemDAO.getItems(
						itemCategoryID,
						shopID,
						latCenter, lonCenter,
						deliveryRangeMin,
						deliveryRangeMax,
						proximity,null,
						sortBy,limit,offset
				);
		
		
		GenericEntity<List<Item>> listEntity = new GenericEntity<List<Item>>(list){
			
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
	public Response getItems(
			@QueryParam("ItemCategoryID")Integer itemCategoryID,
			@QueryParam("ShopID")Integer shopID,
			@QueryParam("latCenter") Double latCenter, @QueryParam("lonCenter") Double lonCenter,
			@QueryParam("deliveryRangeMax")Double deliveryRangeMax,
			@QueryParam("deliveryRangeMin")Double deliveryRangeMin,
			@QueryParam("proximity")Double proximity,
			@QueryParam("SearchString")String searchString,
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

		ItemEndPoint endPoint = itemDAO.getEndPointMetadata(itemCategoryID,
				shopID,latCenter,lonCenter,deliveryRangeMin,deliveryRangeMax,proximity,searchString);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<Item> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					itemDAO.getItems(
							itemCategoryID,
							shopID,
							latCenter, lonCenter,
							deliveryRangeMin,
							deliveryRangeMax,
							proximity,searchString,
							sortBy,set_limit,set_offset
					);

			endPoint.setResults(list);
		}


//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		//Marker

		return Response.status(Status.OK)
                .entity(endPoint)
                .build();

	}



	//@QueryParam("ShopID")Integer shopID,

	@GET
	@Path("/OuterJoin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(
			@QueryParam("ItemCategoryID")Integer itemCategoryID,
			@QueryParam("IsDetached")Boolean parentIsNull,
			@QueryParam("SearchString") String searchString,
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

		ItemEndPoint endPoint = itemDAOJoinOuter.getEndPointMetadata(itemCategoryID,parentIsNull,searchString);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<Item> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					itemDAOJoinOuter.getItems(
							itemCategoryID,
							parentIsNull,searchString,
							sortBy,set_limit,set_offset
					);

			endPoint.setResults(list);
		}


//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


		//Marker

		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}



//	@GET
//	@Path("/Async")
//	@Produces(MediaType.APPLICATION_JSON)
	public void asyncGet(@Suspended final AsyncResponse asyncResponse,
						 @QueryParam("ItemCategoryID") final Integer itemCategoryID,
						 @QueryParam("SortBy") final String sortBy,
						 @QueryParam("Limit") final Integer limit, @QueryParam("Offset") final Integer offset,
						 @QueryParam("metadata_only") final Boolean metaonly)

	{

		new Thread(new Runnable() {
			@Override
			public void run() {
				Response result = veryExpensiveOperation();
				asyncResponse.resume(result);
			}

			private Response veryExpensiveOperation() {
				// ... very expensive operation

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

				ItemEndPoint endPoint = itemDAOJoinOuter.getEndPointMetadata(itemCategoryID,null,null);

				endPoint.setLimit(set_limit);
				endPoint.setMax_limit(max_limit);
				endPoint.setOffset(set_offset);

				List<Item> list = null;


				if(metaonly==null || (!metaonly)) {

					list =
							itemDAOJoinOuter.getItems(
									itemCategoryID,null,null,
									sortBy,set_limit,set_offset
							);

					endPoint.setResults(list);
				}


				return Response.status(Status.OK)
						.entity(endPoint)
						.build();
			}

		}).start();
	}




	@GET
	@Path("/{ItemID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(@PathParam("ItemID")int itemID)
	{
		System.out.println("itemID=" + itemID);
		 
		
		//marker
		
		Item item = itemDAO.getItem(itemID);
		
		if(item!= null)
		{

			return Response.status(Status.OK)
			.entity(item)
			.build();
			
		} else 
		{

			return Response.status(Status.NO_CONTENT)
					.build();
			
		}
	}





	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/Item");
	private static final double MAX_IMAGE_SIZE_MB = 2;


	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
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
	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
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
