package org.nearbyshops.RESTEndpoints;

import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.OrderItem;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Path("/OrderItem")
public class OrderItemResource {


	public OrderItemResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrderItem(OrderItem orderItem)
	{

		int rowCount = Globals.orderItemService.saveOrderItem(orderItem);



		if(rowCount == 1)
		{
			
			
			Response response = Response.status(Status.CREATED)
					.entity(null)
					.build();
			
			return response;
			
		}else if(rowCount <= 0)
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
	public Response updateOrderItem(OrderItem orderItem)
	{

		int rowCount = Globals.orderItemService.updateOrderItem(orderItem);

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


	@DELETE
	public Response deleteOrderItem(@QueryParam("OrderID")int orderID, @QueryParam("ItemID") int itemID)
	{

		int rowCount = Globals.orderItemService.deleteOrderItem(itemID,orderID);


		if(rowCount>=1)
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
	public Response getOrderItem(@QueryParam("OrderID")int orderID, @QueryParam("ItemID")int itemID)
	{


		List<OrderItem> orderList = Globals.orderItemService.getOrderItem(orderID,itemID);


		GenericEntity<List<OrderItem>> listEntity = new GenericEntity<List<OrderItem>>(orderList){
		};
	
		
		if(orderList.size()<=0)
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

}
