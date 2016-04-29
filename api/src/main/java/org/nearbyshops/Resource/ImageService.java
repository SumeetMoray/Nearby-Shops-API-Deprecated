package org.nearbyshops.Resource;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.nearbyshops.model.Image;

@Path("/Images")
public class ImageService {
    
    /*
     * The maximum allowed file size in megabytes.
     * Files larger than this size will not be uploadable or downloadable.
     */
    private static final int MAX_SIZE_IN_MB = 1;
    
    /*
     * The directory where the images will be stored.
     * Make sure this directory exists before you run the service.
     */
    private static final java.nio.file.Path BASE_DIR = Paths.get("./images");

    ///media/sumeet/development/localAreaDelivery/ImageServer
    /*
     * Download a list of all image file names.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Image> getFileNames() throws IOException {
        
    	
    	/*
        // Filters out all non JPEG or PNG files, as well as files larger than the maximum allowed size.
        DirectoryStream.Filter<java.nio.file.Path> filter = entry -> {
            
        	boolean sizeOk = Files.size(entry) <= 1024 * 1024 * MAX_SIZE_IN_MB;
            boolean extensionOk = entry.getFileName().toString().endsWith("jpg") || entry.getFileName().toString().endsWith("png");
            
            return sizeOk && extensionOk;
        };
        
        */
        
        // Browse the filtered directory and list all the files.
        JsonArrayBuilder results = Json.createArrayBuilder();
        
        List<Image> imagesList = new ArrayList<Image>();
        
        
        
        for (java.nio.file.Path entry : Files.newDirectoryStream(BASE_DIR)) {
        
        //	results.add(entry.getFileName().toString());
        	
        	Image image = new Image();
        	image.setPath("/" + entry.getFileName().toString());
        	imagesList.add(image);
        }
        
        //results.build()
        
        return imagesList;
    }
    
    
    /*
     * Upload a JPEG or PNG file.
     */
    
    
    
    //, @HeaderParam("Content-Type") String fileType
    
    
    //@Consumes({"image/jpeg", "image/png"})

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM) 
    public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize) throws IOException {

        File theDir = new File("./images");

        // if the directory does not exist, create it
        if (!theDir.exists()) {

            System.out.println("creating directory: " + "./images");

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


        // Make sure the file is not larger than the maximum allowed size.
       // if (fileSize > 1024 * 1024 * MAX_SIZE_IN_MB) {
         //   throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Image is larger than " + MAX_SIZE_IN_MB + "MB").build());
        //}
        
        // Generate a random file name based on the current time.
        // This probably isn't 100% safe but works fine for this nearbyshops.
        String fileName = "" + System.currentTimeMillis();

        //fileName += ".jpg";

        
        /*
        if (fileType.equals("image/jpeg")) {
            fileName += ".jpg";
        } else {
            fileName += ".png";
        }
        
        */
           
        // Copy the file to its location.
        Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        
        Image image = new Image();
        image.setPath("/" + fileName);
        
        // Return a 201 Created response with the appropriate Location header.
        	return Response.status(Status.CREATED).location(URI.create("/api/Images/" + fileName)).entity(image).build();
    }
    
    
    /*
     * Download a JPEG file.
     */
    @GET
    @Path("{name}.jpg")
    @Produces("image/jpeg")
    public InputStream getJpegImage(@PathParam("name") String fileName) throws IOException {
        
        //fileName += ".jpg";
        java.nio.file.Path dest = BASE_DIR.resolve(fileName);
        
        if (!Files.exists(dest)) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
        
        return Files.newInputStream(dest);
    }
    
    
    @GET
    @Path("/{name}")
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
    
    /*
     * Download a PNG file.
     */
    @GET
    @Path("{name}.png")
    @Produces("image/png")
    public InputStream getPngImage(@PathParam("name") String fileName) throws IOException {
        
        fileName += ".png";
        java.nio.file.Path dest = BASE_DIR.resolve(fileName);
        
        if (!Files.exists(dest)) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
        
        return Files.newInputStream(dest);
    }
    
    
    
    @DELETE
    @Path("/{name}")
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
    	  	
    	
    	if(deleteStatus == false)
    	{
    		 response = Response.status(Status.NOT_MODIFIED).build();
    	
    	}else
    	{
    		response = Response.status(Status.OK).build();
    	}
    		
		return response;
    }
    
}
