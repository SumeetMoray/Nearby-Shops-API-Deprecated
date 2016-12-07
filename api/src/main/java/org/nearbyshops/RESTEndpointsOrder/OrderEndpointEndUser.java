package org.nearbyshops.RESTEndpointsOrder;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.ModelEndPoints.OrderEndPoint;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Singleton
@Path("/Order/EndUser")
public class OrderEndpointEndUser {


	public OrderEndpointEndUser() {
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
	public Response createOrder(Order order, @QueryParam("CartID") int cartID)
	{

		Order orderResult = Globals.orderService.placeOrder(order,cartID);


		if(orderResult!=null)
		{

			Globals.broadcastMessage(String.valueOf(orderResult.getOrderID()) + "Has been received !",order.getShopID());

			return Response.status(Status.CREATED)
					.entity(orderResult)
					.build();

		}
		else
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();

			//Response.status(Status.CREATED).location(arg0)

			return response;
		}

	}


	@PUT
	@Path("/{OrderID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrder(@PathParam("OrderID")int orderID, Order order)
	{

		order.setOrderID(orderID);

		int rowCount = Globals.orderService.updateOrder(order);


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
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrderBulk(List<Order> ordersList)
	{

		int rowCount = 0;

		for(Order orderItem: ordersList)
		{
			rowCount = rowCount + Globals.orderService.updateOrder(orderItem);
		}




		if(rowCount <= 0)
		{
			Response response = Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();

			return response;
		}
		else if(rowCount >= ordersList.size())
		{
			Response response = Response.status(Status.OK)
					.entity(null)
					.build();

			return response;
		}


		return null;
	}



	// requires authentication by the Distributor
	@PUT
	@Path("/ReturnOrder/{OrderID}")
	public Response returnOrder(@PathParam("OrderID")int orderID)
	{

		int rowCount = Globals.orderService.returnOrderByDeliveryGuy(orderID);

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



	// requires authentication by the Distributor
	@PUT
	@Path("/CancelByShop/{OrderID}")
	public Response cancelledByShop(@PathParam("OrderID")int orderID)
	{

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



	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrders(@QueryParam("OrderID")Integer orderID,
							  @QueryParam("EndUserID")Integer endUserID,
							  @QueryParam("ShopID")Integer shopID,
							  @QueryParam("PickFromShop") Boolean pickFromShop,
							  @QueryParam("StatusHomeDelivery")Integer homeDeliveryStatus,
							  @QueryParam("StatusPickFromShopStatus")Integer pickFromShopStatus,
							  @QueryParam("DeliveryGuyID")Integer deliveryGuyID,
							  @QueryParam("PaymentsReceived") Boolean paymentsReceived,
							  @QueryParam("DeliveryReceived") Boolean deliveryReceived,
							  @QueryParam("GetDeliveryAddress")Boolean getDeliveryAddress,
							  @QueryParam("GetStats")Boolean getStats,
							  @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
							  @QueryParam("SortBy") String sortBy,
							  @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
							  @QueryParam("metadata_only")Boolean metaonly)

	{

		// *********************** second Implementation

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

		OrderEndPoint endPoint = Globals.orderService.endPointMetaDataOrders(orderID,
				endUserID,shopID, pickFromShop,
				homeDeliveryStatus,pickFromShopStatus,
				deliveryGuyID,
				paymentsReceived,deliveryReceived,null
				);

		endPoint.setLimit(set_limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(set_offset);

		List<Order> list = null;


		if(metaonly==null || (!metaonly)) {

			list =
					Globals.orderService.readOrders(orderID,
							endUserID,shopID, pickFromShop,
							homeDeliveryStatus,pickFromShopStatus,
							deliveryGuyID,
							paymentsReceived,deliveryReceived,
							latCenter,lonCenter,
							null,
							sortBy,limit,offset);


			/*

			if(getDeliveryAddress!=null && getDeliveryAddress)
			{
				for(Order order: list)
				{
					order.setDeliveryAddress(
							Globals.deliveryAddressService
									.readAddress(order.getDeliveryAddressID())
					);
				}

			}
*/

/*

			if(getStats!=null && getStats) {

				for (Order order : list) {

					order.setOrderStats(Globals.orderItemService.getOrderStats(order.getOrderID()));
				}

			}
*/


			endPoint.setResults(list);
		}


/*
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
*/

		//Marker

		return Response.status(Status.OK)
				.entity(endPoint)
				.build();


	}





	@GET
	@Path("/{OrderID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrder(@PathParam("OrderID")int orderID)
	{

		Order order = Globals.orderService.readOrder(orderID);

		if(order != null)
		{
			Response response = Response.status(Status.OK)
					.entity(order)
					.build();

			return response;

		} else
		{

			Response response = Response.status(Status.NO_CONTENT)
					.entity(order)
					.build();

			return response;

		}

	}




/*
	@GET
	@Path("/Stats/{OrderID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderStats(@PathParam("OrderID")int orderID)
	{

		OrderStats orderStats = Globals.orderItemService.getOrderStats(orderID);

		if(orderStats != null)
		{
			Response response = Response.status(Status.OK)
					.entity(orderStats)
					.build();

			return response;

		} else
		{

			Response response = Response.status(Status.NO_CONTENT)
					.entity(orderStats)
					.build();

			return response;

		}

	}*/

}
