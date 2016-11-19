package org.nearbyshops.BackupDeliveryGuyEndpoint;

import net.coobird.thumbnailator.Thumbnails;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Image;
import org.nearbyshops.ModelRoles.DeliveryGuySelf;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;


public class DeliveryGuySelfResource {


	public DeliveryGuySelfResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	public Response createVehicle(DeliveryGuySelf deliveryGuySelf)
	{

		/*
		try {

			Image image = uploadImage(in,0,null);

			if(image!=null)
			{
				deliveryGuySelf.setProfileImageURL(image.getPath());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}*/




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
	public Response deleteVehicle(@PathParam("DeliveryGuyID")int deliveryGuyID)
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
	public Response getVehicles(@QueryParam("ShopID")Integer shopID,
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
	public Response getCart(@PathParam("DeliveryGuyID")int deliveryGuyID)
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






	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/DeliveryGuySelf");
	private static final double MAX_IMAGE_SIZE_MB = 2;


	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
	public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
							 @QueryParam("PreviousImageName") String previousImageName
	) throws IOException {


		if(previousImageName!=null)
		{
			Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
		}


		File theDir = new File(BASE_DIR.toString());

		// if the directory does not exist, create it
		if (!theDir.exists()) {

			System.out.println("creating directory: " + BASE_DIR.toString());

			boolean result = false;

			try{
				theDir.mkdir();
				result = true;
			}
			catch(SecurityException se){
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
			Files.deleteIfExists(BASE_DIR.resolve(fileName));
//			throw new WebApplicationException("File size exceeds max size allowed.");

			return Response.status(Status.EXPECTATION_FAILED).build();
		}


//		saveResizedImages(fileName,filesize);
		createThumbnails(fileName);


		Image image = new Image();
		image.setPath("/" + fileName);

		// Return a 201 Created response with the appropriate Location header.

		return Response.status(Status.CREATED).location(URI.create("/api/Images/" + fileName)).entity(image).build();
	}


	void saveResizedImages(String filename, long filesize)
	{

		try {

		File input = new File(BASE_DIR.toString() + "/" + filename);
		BufferedImage image = null;

//			image.getScaledInstance(image.getWidth(),image.getHeight(),2);


		image = ImageIO.read(input);

			File directory = new File(BASE_DIR.toString() + "/500");

			if(!directory.exists())
			{
				directory.mkdir();
			}


		File compressedImageFile = new File(BASE_DIR.toString() + "/500/" +filename);

		OutputStream os =new FileOutputStream(compressedImageFile);

		Iterator<ImageWriter> writers =  ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = (ImageWriter) writers.next();

		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		writer.setOutput(ios);

		ImageWriteParam param = writer.getDefaultWriteParam();

		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(0.5f);
		writer.write(null, new IIOImage(image, null, null), param);

		os.close();
		ios.close();
		writer.dispose();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	void createThumbnails(String filename)
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
