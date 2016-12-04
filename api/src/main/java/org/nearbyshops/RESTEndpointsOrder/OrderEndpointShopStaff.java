package org.nearbyshops.RESTEndpointsOrder;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.Model.Shop;
import org.nearbyshops.ModelEndPoints.OrderEndPoint;
import org.nearbyshops.ModelOrderStatus.OrderStatusHomeDelivery;
import org.nearbyshops.ModelRoles.DeliveryGuySelf;
import org.nearbyshops.ModelRoles.ShopAdmin;
import org.nearbyshops.ModelSecurity.ForbiddenOperations;

import javax.annotation.security.RolesAllowed;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Singleton
@Path("/Order/ShopStaff")
public class OrderEndpointShopStaff {


	public OrderEndpointShopStaff() {
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
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response confirmOrder(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopForShopAdmin(shopAdmin.getShopAdminID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}


		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.ORDER_PLACED)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_CONFIRMED);

			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);


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
	@Path("/SetOrderPacked/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response setOrderPacked(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopForShopAdmin(shopAdmin.getShopAdminID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}


		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.ORDER_CONFIRMED)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_PACKED);

			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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
	@Path("/HandoverToDelivery/{DeliveryGuySelfID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response handoverToDelivery(@PathParam("DeliveryGuySelfID")int deliveryGuyID,List<Order> ordersList)
	{

		int rowCount = 0;

//		order.setDeliveryGuySelfID(orderReceived.getDeliveryGuySelfID());
		DeliveryGuySelf deliveryGuySelf = Globals.deliveryGuySelfDAO.readDeliveryGuySelf(deliveryGuyID);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopForShopAdmin(shopAdmin.getShopAdminID());


			// verify the account of delivery guy being assigned
			if(deliveryGuySelf!=null)
			{
				if(deliveryGuySelf.getShopID()!= shop.getShopID())
				{
					// an attempt to assign a delivery guy which doesnt belong to the shop for the given order
					throw new ForbiddenException("an attempt to assign a delivery guy which doesnt belong to the shop for the given order !");
				}

			}
			else
			{
				throw new ForbiddenException("Unable to verify Delivery ID");
			}


			for(Order orderReceived : ordersList)
			{
				Order order = Globals.orderService.readStatusHomeDelivery(orderReceived.getOrderID());

				if(order.getShopID()!=shop.getShopID())
				{
					// An attempt to update an order for shop you do not own
					ForbiddenOperations activity = new ForbiddenOperations();
					activity.setShopAdminID(shopAdmin.getShopAdminID());
					activity.setActivityInfo("An attempt to update order for shop you do not own !");
					activity.setEndpointInfo("PUT : /Order/ShopStaff/HandoverToDelivery/{OrderID}");
					Globals.forbiddenOperationsDAO.saveForbiddenActivity(activity);

					throw new ForbiddenException("An attempt to update order for shop you do not own !");
				}


				if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.ORDER_PACKED) {

					order.setStatusHomeDelivery(OrderStatusHomeDelivery.PENDING_HANDOVER);
					order.setDeliveryGuySelfID(deliveryGuyID);
					rowCount = Globals.orderService.updateDeliveryGuySelfID(order) + rowCount;
				}


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
		else
		{
			return Response.status(Status.NOT_MODIFIED)
					.build();
		}
	}






	@PUT
	@Path("/UndoHandover/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response undoHandover(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopForShopAdmin(shopAdmin.getShopAdminID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}


		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.PENDING_HANDOVER)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_PACKED);
			order.setDeliveryGuySelfID(null);

			int rowCount = Globals.orderService.updateDeliveryGuySelfID(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response markDelivered(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopForShopAdmin(shopAdmin.getShopAdminID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}


		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT)
		{
//			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_PACKED);
//			order.setDeliveryGuySelfID(null);
			order.setDeliveryReceived(true);

			int rowCount = Globals.orderService.updateDeliveryReceived(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response paymentReceived(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopForShopAdmin(shopAdmin.getShopAdminID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}


		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT)
		{
//			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_PACKED);
//			order.setDeliveryGuySelfID(null);
//			order.setDeliveryReceived(true);
			order.setPaymentReceived(true);

			int rowCount = Globals.orderService.updatePaymentReceived(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response paymentReceivedBulk(List<Order> ordersList)
	{
		int rowCount = 0;




		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopForShopAdmin(shopAdmin.getShopAdminID());

			for(Order orderItem : ordersList)
			{
				Order order = Globals.orderService.readStatusHomeDelivery(orderItem.getOrderID());

				if(order.getShopID()!=shop.getShopID())
				{
					// An attempt to update an order for shop you do not own
					throw new ForbiddenException("An attempt to update order for shop you do not own !");
				}
			}
		}



		for(Order order : ordersList)
		{
			if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.PENDING_DELIVERY_ACCEPT_PENDING_PAYMENT)
			{
//			order.setStatusHomeDelivery(OrderStatusHomeDelivery.ORDER_PACKED);
//			order.setDeliveryGuySelfID(null);
//				order.setDeliveryReceived(true);
				order.setPaymentReceived(true);

				rowCount = Globals.orderService.updatePaymentReceived(order) + rowCount;
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




	// requires authentication by the Distributor
	@PUT
	@Path("/CancelByShop/{OrderID}")
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response cancelledByShop(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopForShopAdmin(shopAdmin.getShopAdminID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}


		int rowCount = Globals.orderService.orderCancelledByShop(orderID);

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




	@PUT
	@Path("/AcceptReturnCancelledByShop/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response acceptReturnCancelledByShop(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopForShopAdmin(shopAdmin.getShopAdminID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}


		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.CANCELLED_BY_SHOP_RETURN_PENDING)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.CANCELLED_BY_SHOP);

			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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
	@Path("/AcceptReturn/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_SHOP_ADMIN})
	public Response acceptReturn(@PathParam("OrderID")int orderID)
	{
		Order order = Globals.orderService.readStatusHomeDelivery(orderID);

		if(Globals.accountApproved instanceof ShopAdmin)
		{
			ShopAdmin shopAdmin = (ShopAdmin) Globals.accountApproved;
			Shop shop = Globals.shopDAO.getShopForShopAdmin(shopAdmin.getShopAdminID());

			if(order.getShopID()!=shop.getShopID())
			{
				// An attempt to update an order for shop you do not own
				throw new ForbiddenException("An attempt to update order for shop you do not own !");
			}
		}


		if(order.getStatusHomeDelivery()== OrderStatusHomeDelivery.RETURN_PENDING)
		{
			order.setStatusHomeDelivery(OrderStatusHomeDelivery.RETURNED);

			int rowCount = Globals.orderService.updateStatusHomeDelivery(order);


			if(rowCount >= 1)
			{

				return Response.status(Status.OK)
						.entity(null)
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






	// Permissions : General
	// Submit Item Categories
	// Submit Items
	// Add / Remove Items From Shop
	// Update Stock

	// Permissions : Home Delivery Inventory
	// 0. Cancel Order
	// 1. Confirm Orders
	// 2. Set Orders Packed
	// 3. Handover to Delivery
	// 4. Mark Order Delivered
	// 5. Payment Received | Collect Payments from Delivery Guy
	// 6. Accept Return | Cancelled By Shop

	// 7. Accept Return | Returned by Delivery Guy | Not required


}
