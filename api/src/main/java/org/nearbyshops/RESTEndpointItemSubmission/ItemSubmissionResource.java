package org.nearbyshops.RESTEndpointItemSubmission;

import org.nearbyshops.DAOItemSubmissions.DAOItemSubmissionNew;
import org.nearbyshops.DAOItemSubmissions.DAOItemSubmissions;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Item;
import org.nearbyshops.ModelEndPoints.ItemEndPoint;
import org.nearbyshops.ModelItemSpecification.EndPoints.ItemSpecValueEndPoint;
import org.nearbyshops.ModelItemSubmission.Endpoints.ItemSubmissionEndPoint;
import org.nearbyshops.ModelItemSubmission.ItemSubmission;
import org.nearbyshops.ModelRoles.ShopAdmin;
import org.nearbyshops.ModelRoles.Staff;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by sumeet on 17/3/17.
 */




@Path("/v1/ItemSubmission")
public class ItemSubmissionResource {


    private DAOItemSubmissionNew daoItemSubmissions = Globals.daoItemSubmissions;



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
    public Response insertItem(ItemSubmission itemSubmission)
    {

        if(Globals.accountApproved instanceof ShopAdmin)
        {
            ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;

            itemSubmission.setUserID(shopAdmin.getShopAdminID());

        }



        itemSubmission.setStatus(1);
        int idOfInsertedRow = daoItemSubmissions.insertItemSubmission(itemSubmission,false);

        itemSubmission.setItemSubmissionID(idOfInsertedRow);

        if(idOfInsertedRow >=1)
        {

            return Response.status(Response.Status.CREATED)
                    .entity(itemSubmission)
                    .build();

        }else if(idOfInsertedRow <= 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;
    }







    @PUT
    @Path("/{ItemSubmissionID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
    public Response updateItem(ItemSubmission itemSubmission, @PathParam("ItemSubmissionID")int itemSubmissionID)
    {

        itemSubmission.setItemSubmissionID(itemSubmissionID);
        int rowCount = daoItemSubmissions.updateItemSubmission(itemSubmission);

        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .entity(null)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .entity(null)
                    .build();
        }

        return null;

    }



    @PUT
    @Path("/ApproveItemInsert/{ItemSubmissionID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN})
    public Response approveInsert(@PathParam("ItemSubmissionID")int itemSubmissionID)
    {
        int rowCount = daoItemSubmissions.approveInsert(itemSubmissionID);

        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .entity(null)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .entity(null)
                    .build();
        }

        return null;

    }




    @PUT
    @Path("/ApproveItemUpdate/{ItemSubmissionID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN})
    public Response approveUpdate(@PathParam("ItemSubmissionID")int itemSubmissionID)
    {
        int rowCount = daoItemSubmissions.approveUpdate(itemSubmissionID);


        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .entity(null)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .entity(null)
                    .build();
        }

        return null;

    }




    @PUT
    @Path("/RejectSubmission/{ItemSubmissionID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN})
    public Response rejectSubmission(@PathParam("ItemSubmissionID")int itemSubmissionID)
    {


        int rowCount = daoItemSubmissions.rejectItemSubmission(itemSubmissionID);


        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .entity(null)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .entity(null)
                    .build();
        }

        return null;

    }










    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubmissions(
            @QueryParam("UserID")Integer userID,
            @QueryParam("Status")Integer statusID,
            @QueryParam("ItemIDNull")Boolean itemIDNULL,
            @QueryParam("ItemID")Integer itemID,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount)
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

        ItemSubmissionEndPoint endPoint = new ItemSubmissionEndPoint();

        endPoint.setLimit(set_limit);
        endPoint.setMax_limit(max_limit);
        endPoint.setOffset(set_offset);


//        if(getRowCount!=null && getRowCount)
//        {
////            endPoint.setItemCount(daoItemSubmissions.getRowCount(itemSpecID,itemCatID));
//        }

        endPoint = daoItemSubmissions.getSubmissions(
                userID,statusID,itemIDNULL,itemID,sortBy,limit,offset,getRowCount
        );




//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


        //Marker

        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }





    @GET
    @Path("/ItemsUpdated")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsUpdated(
            @QueryParam("UserID")Integer userID,
            @QueryParam("Status")Integer statusID,
            @QueryParam("ItemIDNull")Boolean itemIDNULL,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
            @QueryParam("GetRowCount")boolean getRowCount)
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

        ItemEndPoint endPoint = new ItemEndPoint();

        endPoint.setLimit(set_limit);
        endPoint.setMax_limit(max_limit);
        endPoint.setOffset(set_offset);


//        if(getRowCount!=null && getRowCount)
//        {
////            endPoint.setItemCount(daoItemSubmissions.getRowCount(itemSpecID,itemCatID));
//        }

        endPoint = daoItemSubmissions.getItemsUpdated(
                userID,statusID,itemIDNULL,sortBy,limit,offset,getRowCount
        );




//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


        //Marker

        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }







}
