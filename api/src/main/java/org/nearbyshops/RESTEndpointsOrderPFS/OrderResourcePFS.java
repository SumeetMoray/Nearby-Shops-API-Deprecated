package org.nearbyshops.RESTEndpointsOrderPFS;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelPickFromShop.OrderEndPointPFS;
import org.nearbyshops.ModelPickFromShop.OrderPFS;
import org.nearbyshops.ModelRoles.EndUser;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Singleton
@Path("/OrderPFS")
public class OrderResourcePFS {


	public OrderResourcePFS() {
		super();
		// TODO Auto-generated constructor stub
	}



	@GET
	@Path("/Notifications/{ShopID}")
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput listenToBroadcast(@PathParam("ShopID")int shopID) {
		final EventOutput eventOutput = new EventOutput();

		if(Globals.broadcasterMap.get(shopID)!=null)
		{
			SseBroadcaster broadcasterOne = Globals.broadcasterMap.get(shopID);
			broadcasterOne.add(eventOutput);
		}
		else
		{
			SseBroadcaster broadcasterTwo = new SseBroadcaster();
			broadcasterTwo.add(eventOutput);
			Globals.broadcasterMap.put(shopID,broadcasterTwo);
		}

		return eventOutput;
	}



	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrderPFS(OrderPFS order, @QueryParam("CartID") int cartID)
	{

//		Order orderResult = Globals.orderService.placeOrder(order,cartID);

		int orderId = Globals.placeOrderPFS_dao.placeOrderNew(order,cartID);



		if(orderId!=-1)
		{
			OrderPFS orderResult = Globals.orderServicePFS.readOrder(orderId);

			Globals.broadcastMessageToShop("Pick from Shop - Order Number : " + String.valueOf(orderId) + " Has been received !",orderResult.getShopID());

			return Response.status(Status.CREATED)
//					.entity(orderResult)
					.build();
			
		}
		else
		{

			//Response.status(Status.CREATED).location(arg0)
			
			return Response.status(Status.NOT_MODIFIED)
					.build();
		}
		
	}



	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_END_USER})
	public Response getOrders(@QueryParam("OrderID")Integer orderID,
							  @QueryParam("ShopID")Integer shopID,
							  @QueryParam("StatusPickFromShopStatus")Integer pickFromShopStatus,
							  @QueryParam("PaymentsReceived") Boolean paymentsReceived,
							  @QueryParam("DeliveryReceived") Boolean deliveryReceived,
							  @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
							  @QueryParam("PendingOrders") Boolean pendingOrders,
							  @QueryParam("SearchString") String searchString,
							  @QueryParam("SortBy") String sortBy,
							  @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
							  @QueryParam("metadata_only")Boolean metaonly)
	{
		//							  @QueryParam("EndUserID")Integer endUserID,

		Integer endUserID;

		// *********************** second Implementation

		if(Globals.accountApproved instanceof EndUser)
		{
			EndUser endUser = (EndUser) Globals.accountApproved;
//			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
//			shopID = shop.getShopID();
			endUserID = endUser.getEndUserID();
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
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


		if(offset==null)
		{
			offset = 0;
		}


		OrderEndPointPFS endPoint = Globals.orderServicePFS.endPointMetaDataOrders(
				orderID, endUserID,shopID,
				pickFromShopStatus,
				paymentsReceived,deliveryReceived,
				pendingOrders,searchString
		);

		endPoint.setLimit(limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(offset);

		List<OrderPFS> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					Globals.orderServicePFS.readOrders(
							orderID, endUserID,shopID,
							pickFromShopStatus,
							paymentsReceived,deliveryReceived,
							latCenter,lonCenter,
							pendingOrders, searchString,
							sortBy,limit,offset);


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



	// requires authentication by the Distributor
	@PUT
	@Path("/CancelByUser/{OrderID}")
	@RolesAllowed({GlobalConstants.ROLE_END_USER})
	public Response cancelledByUserPFS(@PathParam("OrderID")int orderID)
	{
		OrderPFS order = Globals.orderServicePFS.readStatusPickFromShop(orderID);

		if(Globals.accountApproved instanceof EndUser)
		{
			EndUser endUser = (EndUser) Globals.accountApproved;

			if(order.getEndUserID()!=endUser.getEndUserID())
			{
				throw new ForbiddenException("Not Permitted !");
			}
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}



		int rowCount = Globals.orderServicePFS.orderCancelledByEndUser(orderID);

		if(rowCount >= 1)
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}



}
