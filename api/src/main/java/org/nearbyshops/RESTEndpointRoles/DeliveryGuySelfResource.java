package org.nearbyshops.RESTEndpointRoles;

import net.coobird.thumbnailator.Thumbnails;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Image;
import org.nearbyshops.ModelRoles.DeliveryGuySelf;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Path("/DeliveryGuySelf")
public class DeliveryGuySelfResource {


	public DeliveryGuySelfResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	public Response createDelivery(DeliveryGuySelf deliveryGuySelf)
	{


		int idOfInsertedRow = Globals.deliveryGuySelfDAO.saveDeliveryVehicleSelf(deliveryGuySelf);

		deliveryGuySelf.setDeliveryGuyID(idOfInsertedRow);


		if(idOfInsertedRow >=1)
		{


			return Response.status(Status.CREATED)
					.location(URI.create("/api/DeliveryGuySelf/" + idOfInsertedRow))
					.entity(deliveryGuySelf)
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

	
	@PUT
	@Path("/{DeliveryGuyID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCart(@PathParam("DeliveryGuyID")int deliveryGuyID, DeliveryGuySelf deliveryGuySelf)
	{

		deliveryGuySelf.setDeliveryGuyID(deliveryGuyID);
		int rowCount = Globals.deliveryGuySelfDAO.updateDeliveryVehicleSelf(deliveryGuySelf);

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
	@Path("/{DeliveryGuyID}")
	public Response deleteDelivery(@PathParam("DeliveryGuyID")int deliveryGuyID)
	{

		int rowCount = Globals.deliveryGuySelfDAO.deleteDeliveryVehicleSelf(deliveryGuyID);
		
		
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeliveryGuyList(@QueryParam("ShopID")Integer shopID,
									   @QueryParam("IsEnabled") Boolean isEnabled)
	{


		List<DeliveryGuySelf> vehicleSelfList = Globals.deliveryGuySelfDAO.readDeliveryVehicleSelf(shopID,isEnabled);

		GenericEntity<List<DeliveryGuySelf>> listEntity = new GenericEntity<List<DeliveryGuySelf>>(vehicleSelfList){
			
		};
	
		
		if(vehicleSelfList.size()<=0)
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
	@Path("/{DeliveryGuyID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeliveryGuy(@PathParam("DeliveryGuyID")int deliveryGuyID)
	{

		DeliveryGuySelf vehicleSelf = Globals.deliveryGuySelfDAO.readVehicle(deliveryGuyID);
		
		if(vehicleSelf != null)
		{

			return Response.status(Status.OK)
			.entity(vehicleSelf)
			.build();
			
		} else 
		{

			return Response.status(Status.NO_CONTENT)
					.entity(vehicleSelf)
					.build();
			
		}
		
	}


	@GET
	@Path("/CheckUsernameExists/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCart(@PathParam("username")String username)
	{

		boolean result = Globals.deliveryGuySelfDAO.checkUsernameExists(username);

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





	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/DeliveryGuySelf");
	private static final double MAX_IMAGE_SIZE_MB = 2;


	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
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
