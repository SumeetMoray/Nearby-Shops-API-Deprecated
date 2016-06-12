package org.nearbyshops.RESTInterfaces;

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Cart;
import org.nearbyshops.Model.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
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




	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrders(@QueryParam("EndUserID")int endUserID,
							 @QueryParam("ShopID")int shopID)

	{

		List<Order> ordersList = Globals.orderService.readOrders(endUserID,shopID);

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
	public Response getCart(@PathParam("OrderID")int orderID)
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


}
