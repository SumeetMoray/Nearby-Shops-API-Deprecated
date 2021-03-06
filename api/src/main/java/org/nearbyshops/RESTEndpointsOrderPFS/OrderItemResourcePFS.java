package org.nearbyshops.RESTEndpointsOrderPFS;

import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.OrderItem;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelEndPoints.OrderItemEndPoint;
import org.nearbyshops.ModelPickFromShop.OrderItemEndPointPFS;
import org.nearbyshops.ModelPickFromShop.OrderItemPFS;
import org.nearbyshops.ModelPickFromShop.OrderPFS;
import org.nearbyshops.ModelRoles.EndUser;
import org.nearbyshops.ModelRoles.ShopAdmin;
import org.nearbyshops.ModelRoles.ShopStaff;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Path("/OrderItemPFS")
public class OrderItemResourcePFS {


	public OrderItemResourcePFS() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response createOrderItem(OrderItemPFS orderItem)
//	{
//
//		int rowCount = Globals.orderItemServicePFS.saveOrderItem(orderItem);
//
//
//		if(rowCount == 1)
//		{
//
//
//			return Response.status(Status.CREATED)
//					.entity(null)
//					.build();
//
//		}else if(rowCount <= 0)
//		{
//
//			return Response.status(Status.NOT_MODIFIED)
//					.entity(null)
//					.build();
//		}
//
//		return null;
//	}
//
//
//
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response updateOrderItem(OrderItem orderItem)
//	{
//
//		int rowCount = Globals.orderItemService.updateOrderItem(orderItem);
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
//
//		return null;
//	}
//
//
//	@DELETE
//	public Response deleteOrderItem(@QueryParam("OrderID")int orderID, @QueryParam("ItemID") int itemID)
//	{
//
//		int rowCount = Globals.orderItemService.deleteOrderItem(itemID,orderID);
//
//
//		if(rowCount>=1)
//		{
//
//			return Response.status(Status.OK)
//					.entity(null)
//					.build();
//		}
//
//		if(rowCount == 0)
//		{
//
//			return Response.status(Status.NOT_MODIFIED)
//					.entity(null)
//					.build();
//		}
//
//
//		return null;
//	}
//


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF,GlobalConstants.ROLE_END_USER})
	public Response getOrderItemPFS(@QueryParam("OrderID")Integer orderID,
								 @QueryParam("ItemID")Integer itemID,
								 @QueryParam("SearchString")String searchString,
								 @QueryParam("SortBy") String sortBy,
								 @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
								 @QueryParam("metadata_only")Boolean metaonly)
	{


		OrderPFS order = Globals.orderServicePFS.readStatusPickFromShop(orderID);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());

			if(order.getShopID()!=shop.getShopID())
			{
				throw new ForbiddenException("Not Permitted !");
			}

		}
		else if(Globals.accountApproved instanceof ShopStaff)
		{
			ShopStaff staff = ((ShopStaff) Globals.accountApproved);

			if(order.getShopID()!=staff.getShopID())
			{
				throw new ForbiddenException("Not Permitted !");
			}
		}
		else if(Globals.accountApproved instanceof EndUser)
		{
			EndUser endUser = (EndUser) Globals.accountApproved;

			if(order.getEndUserID()!= endUser.getEndUserID())
			{
				throw new ForbiddenException("Not Permitted !");
			}

		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}




		////////////////////////////

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

		OrderItemEndPointPFS endPoint = Globals.orderItemServicePFS.getEndPointMetadata(orderID,itemID);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<OrderItemPFS> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					Globals.orderItemServicePFS.getOrderItemPFS(
							orderID,itemID,
							searchString,sortBy,limit,offset
					);

			endPoint.setResults(list);
		}


		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

		//Marker

		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}

}
