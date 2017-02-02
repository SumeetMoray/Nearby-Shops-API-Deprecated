package org.nearbyshops.RESTEndpointsOrderPFS;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelEndPoints.OrderEndPoint;
import org.nearbyshops.ModelOrderStatus.OrderStatusHomeDelivery;
import org.nearbyshops.ModelPickFromShop.OrderEndPointPFS;
import org.nearbyshops.ModelPickFromShop.OrderPFS;
import org.nearbyshops.ModelPickFromShop.OrderStatusPickFromShop;
import org.nearbyshops.ModelRoles.DeliveryGuySelf;
import org.nearbyshops.ModelRoles.ShopAdmin;
import org.nearbyshops.ModelRoles.ShopStaff;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Singleton
@Path("/OrderPFS/ShopStaff")
public class OrderEndpointShopStaffPFS {


	public OrderEndpointShopStaffPFS() {
		super();
		// TODO Auto-generated constructor stub
	}


	@GET
	@Path("/Notifications/{ShopID}")
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
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





	@PUT
	@Path("/SetConfirmed/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF})
	public Response confirmOrder(@PathParam("OrderID")int orderID)
	{

		OrderPFS order = Globals.orderServicePFS.readStatusPickFromShop(orderID);

		System.out.println("Set Confirmed : ShopID : Order : " + order.getShopID());


		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());

			System.out.println("ShopID : Order : " + order.getShopID() + "ShopID : Shop : " + shop.getShopID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else if(Globals.accountApproved instanceof ShopStaff)
		{
			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;

			if(!shopStaff.isPermitConfirmOrdersPFS())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=shopStaff.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}


		if(order.getStatusPickFromShop()== OrderStatusPickFromShop.ORDER_PLACED)
		{
			order.setStatusPickFromShop(OrderStatusPickFromShop.ORDER_CONFIRMED);
			int rowCount = Globals.orderServicePFS.updateStatusPickFromShop(order);

			if(rowCount >= 1)
			{

				// generate a notification for the user - customer
				Globals.broadcastMessageToEndUser("Order Confirmed (Pick From Shop)","Order Number " + String.valueOf(orderID) + " (PFS) has been confirmed !",order.getEndUserID());

					return Response.status(Status.OK)
						.build();
			}
			if(rowCount <= 0)
			{

				return Response.status(Status.NOT_MODIFIED)
						.build();
			}

		}
		else
		{
			throw new ForbiddenException("Invalid operation !");
		}

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}




	@PUT
	@Path("/SetOrderPacked/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF})
	public Response setOrderPackedPFS(@PathParam("OrderID")int orderID)
	{

		OrderPFS order = Globals.orderServicePFS.readStatusPickFromShop(orderID);

		System.out.println("Set Confirmed : ShopID : Order : " + order.getShopID());


		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());

			System.out.println("ShopID : Order : " + order.getShopID() + "ShopID : Shop : " + shop.getShopID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else if(Globals.accountApproved instanceof ShopStaff)
		{
			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;

			if(!shopStaff.isPermitSetOrdersPackedPFS())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=shopStaff.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}


		if(order.getStatusPickFromShop()== OrderStatusPickFromShop.ORDER_CONFIRMED)
		{
			order.setStatusPickFromShop(OrderStatusPickFromShop.ORDER_PACKED);
			int rowCount = Globals.orderServicePFS.updateStatusPickFromShop(order);

			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.build();
			}
			if(rowCount <= 0)
			{

				return Response.status(Status.NOT_MODIFIED)
						.build();
			}

		}
		else
		{
			throw new ForbiddenException("Invalid operation !");
		}

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}



	@PUT
	@Path("/SetOrderReadyForPickup/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF})
	public Response setOrderReadyForPickupPFS(@PathParam("OrderID")int orderID)
	{

		OrderPFS order = Globals.orderServicePFS.readStatusPickFromShop(orderID);

		System.out.println("Set Confirmed : ShopID : Order : " + order.getShopID());


		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());

			System.out.println("ShopID : Order : " + order.getShopID() + "ShopID : Shop : " + shop.getShopID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else if(Globals.accountApproved instanceof ShopStaff)
		{
			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;

			if(!shopStaff.isPermitSetReadyForPickupPFS())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=shopStaff.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}


		if(order.getStatusPickFromShop()== OrderStatusPickFromShop.ORDER_PACKED)
		{
			order.setStatusPickFromShop(OrderStatusPickFromShop.READY_FOR_PICKUP);
			int rowCount = Globals.orderServicePFS.updateStatusPickFromShop(order);

			if(rowCount >= 1)
			{

				Globals.broadcastMessageToEndUser("Order Ready For Pickup (Pick From Shop)","Order Number " + String.valueOf(orderID) + " (PFS) is Ready to Pickup !",order.getEndUserID());
				return Response.status(Status.OK)
						.build();
			}
			if(rowCount <= 0)
			{

				return Response.status(Status.NOT_MODIFIED)
						.build();
			}

		}
		else
		{
			throw new ForbiddenException("Invalid operation !");
		}

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}






	@PUT
	@Path("/MarkDelivered/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF})
	public Response markDeliveredPFS(@PathParam("OrderID")int orderID)
	{

		OrderPFS order = Globals.orderServicePFS.readStatusPickFromShop(orderID);

		System.out.println("Set Confirmed : ShopID : Order : " + order.getShopID());


		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());

			System.out.println("ShopID : Order : " + order.getShopID() + "ShopID : Shop : " + shop.getShopID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else if(Globals.accountApproved instanceof ShopStaff)
		{
			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;

			if(!shopStaff.isPermitMarkDeliveredPFS())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=shopStaff.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}


		if(order.getStatusPickFromShop()== OrderStatusPickFromShop.READY_FOR_PICKUP)
		{
			order.setDeliveryReceived(true);
			int rowCount = Globals.orderServicePFS.updateDeliveryReceived(order);

			if(rowCount >= 1)
			{

				Globals.broadcastMessageToEndUser("Order Delivered (Pick From Shop)","Order Number " + String.valueOf(orderID) + " (PFS) is Delivered !",order.getEndUserID());
				return Response.status(Status.OK)
						.build();
			}
			if(rowCount <= 0)
			{

				return Response.status(Status.NOT_MODIFIED)
						.build();
			}

		}
		else
		{
			throw new ForbiddenException("Invalid operation !");
		}

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}




	@PUT
	@Path("/PaymentReceived/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF})
	public Response paymentReceivedPFS(@PathParam("OrderID")int orderID)
	{

		OrderPFS order = Globals.orderServicePFS.readStatusPickFromShop(orderID);

		System.out.println("Set Confirmed : ShopID : Order : " + order.getShopID());


		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());

			System.out.println("ShopID : Order : " + order.getShopID() + "ShopID : Shop : " + shop.getShopID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else if(Globals.accountApproved instanceof ShopStaff)
		{
			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;

			if(!shopStaff.isPermitSetPaymentReceivedPFS())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=shopStaff.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}


		if(order.getStatusPickFromShop()== OrderStatusPickFromShop.READY_FOR_PICKUP)
		{
			order.setPaymentReceived(true);
			int rowCount = Globals.orderServicePFS.updatePaymentReceived(order);

			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.build();
			}
			if(rowCount <= 0)
			{

				return Response.status(Status.NOT_MODIFIED)
						.build();
			}

		}
		else
		{
			throw new ForbiddenException("Invalid operation !");
		}

//		order.setOrderID(orderID);
//		int rowCount = Globals.orderService.updateOrder(order);


		return null;
	}






	@PUT
	@Path("/PaymentReceived")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF})
	public Response paymentReceivedBulk(List<OrderPFS> ordersList)
	{
		int rowCount = 0;
		Shop shop = null;

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());

//			for(OrderPFS orderItem : ordersList)
//			{
//				OrderPFS order = Globals.orderService.readStatusHomeDelivery(orderItem.getOrderID());
//
//				if(order.getShopID()!=shop.getShopID())
//				{
//					// An attempt to update an order for shop you do not own
//					throw new ForbiddenException("An attempt to update order for shop you do not own !");
//				}
//			}
		}
		else if(Globals.accountApproved instanceof ShopStaff)
		{
			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;
			shop = new Shop();
			shop.setShopID(shopStaff.getShopID());

			if(!shopStaff.isPermitSetPaymentReceivedPFS())
			{
				throw new ForbiddenException("Not Permitted !");
			}

		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}




		for(OrderPFS order : ordersList)
		{

			if(order.getShopID()!=shop.getShopID())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getStatusPickFromShop()== OrderStatusPickFromShop.READY_FOR_PICKUP)
			{

				order.setPaymentReceived(true);
				rowCount = Globals.orderServicePFS.updatePaymentReceived(order) + rowCount;
			}

		}



		if(rowCount==ordersList.size())
		{

			return Response.status(Status.OK)
					.build();

		}
		else if (rowCount>0 && rowCount<ordersList.size())
		{
			return Response.status(Status.PARTIAL_CONTENT)
					.build();

		}
		else if(rowCount<=0)
		{
			return Response.status(Status.NOT_MODIFIED)
					.build();
		}


		return null;
	}





	@PUT
	@Path("/CancelByShop/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF})
	public Response cancelByShopPFS(@PathParam("OrderID")int orderID)
	{

		OrderPFS order = Globals.orderServicePFS.readStatusPickFromShop(orderID);

		System.out.println("Set Confirmed : ShopID : Order : " + order.getShopID());


		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());

			System.out.println("ShopID : Order : " + order.getShopID() + "ShopID : Shop : " + shop.getShopID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else if(Globals.accountApproved instanceof ShopStaff)
		{
			ShopStaff shopStaff = (ShopStaff) Globals.accountApproved;

			if(!shopStaff.isPermitCancelOrdersPFS())
			{
				throw new ForbiddenException("Not Permitted !");
			}

			if(order.getShopID()!=shopStaff.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}


		int rowCount = Globals.orderServicePFS.orderCancelledByShop(orderID);

		if(rowCount >= 1)
		{

			Globals.broadcastMessageToEndUser("Order Cancelled (Pick From Shop)","Order Number " + String.valueOf(orderID) + " (PFS) is Cancelled By Shop !",order.getEndUserID());
			return Response.status(Status.OK)
					.build();
		}
		if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}


		return null;
	}





	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN,GlobalConstants.ROLE_SHOP_STAFF})
	public Response getOrders(@QueryParam("OrderID")Integer orderID,
							  @QueryParam("EndUserID")Integer endUserID,
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

		// *********************** second Implementation

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopIDForShopAdmin(shopAdmin.getShopAdminID());
			shopID = shop.getShopID();
		}
		else if(Globals.accountApproved instanceof ShopStaff)
		{
			shopID = ((ShopStaff) Globals.accountApproved).getShopID();
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
				pendingOrders,
				searchString
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
							pendingOrders,
							searchString,
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



	// Permissions : General
	// Submit Item Categories
	// Submit Items
	// Add / Remove Items From Shop
	// Update Stock

	// Permissions : Home Delivery Inventory
	// 0. Cancel OrderPFS's
	// 1. Confirm OrderPFS's
	// 2. Set OrderPFS's Packed
	// 3. Handover to Delivery
	// 4. Mark OrderPFS Delivered
	// 5. Payment Received | Collect Payments from Delivery Guy
	// 6. Accept Return's | Cancelled By Shop

	// 7. Accept Return | Returned by Delivery Guy | Not required

}
