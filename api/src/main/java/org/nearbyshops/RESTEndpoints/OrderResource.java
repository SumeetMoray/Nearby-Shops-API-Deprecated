package org.nearbyshops.RESTEndpoints;

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Order;
import org.nearbyshops.ModelStats.OrderStats;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Path("/Order")
public class OrderResource {


	public OrderResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrder(Order order, @QueryParam("CartID") int cartID)
	{

		Order orderResult = Globals.orderService.placeOrder(order,cartID);


		if(orderResult!=null)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.entity(orderResult)
					.build();
			
			return response;
			
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


	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrderBulk(List<Order> ordersList)
	{

		int rowCount = 0;

		for(Order orderItem: ordersList)
		{
			rowCount = rowCount + Globals.orderService.updateOrder(orderItem);
		}




		if(rowCount == 0)
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



	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrders(@QueryParam("EndUserID")int endUserID,
							  @QueryParam("ShopID")int shopID,
							  @QueryParam("PickFromShop") Boolean pickFromShop,
							  @QueryParam("StatusHomeDelivery")int homeDeliveryStatus,
							  @QueryParam("StatusPickFromShopStatus")int pickFromShopStatus,
							  @QueryParam("VehicleSelfID")int vehicleSelfID,
							  @QueryParam("PaymentsReceived") Boolean paymentsReceived,
							  @QueryParam("DeliveryReceived") Boolean deliveryReceived,
							  @QueryParam("GetDeliveryAddress")boolean getDeliveryAddress,
							  @QueryParam("GetStats")boolean getStats)

	{

		List<Order> ordersList = Globals.orderService.readOrders(endUserID,shopID,
				pickFromShop,homeDeliveryStatus,pickFromShopStatus,vehicleSelfID,
				paymentsReceived,deliveryReceived);


		if(getDeliveryAddress)
		{
			for(Order order: ordersList)
			{

				order.setDeliveryAddress(
						Globals.deliveryAddressService
								.readAddress(order.getDeliveryAddressID())
				);

			}

		}


		if(getStats) {

			for (Order order : ordersList) {

				order.setOrderStats(Globals.orderItemService.getOrderStats(order.getOrderID()));
			}

		}



		GenericEntity<List<Order>> listEntity = new GenericEntity<List<Order>>(ordersList){

		};


		if(ordersList.size()<=0)
		{
			Response response = Response.status(Status.NO_CONTENT)
					.entity(listEntity)
					.build();

			return response;

		}else
		{
			Response response = Response.status(Status.OK)
					.entity(listEntity)
					.build();

			return response;
		}

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

	}

}
