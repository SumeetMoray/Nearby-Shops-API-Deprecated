package org.nearbyshops.RESTInterfaces;

import org.nearbyshops.DAOs.CartStatsDAO;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.CartItem;
import org.nearbyshops.Model.Distributor;
import org.nearbyshops.ModelStats.CartStats;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;


@Path("/CartStats")
public class CartStatsResource {


	public CartStatsResource() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	@GET
	@Path("/{EndUserID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCartStats(@PathParam("EndUserID")int endUserID,@QueryParam("CartID") int cartID,
									   @QueryParam("latCenter")double latCenter, @QueryParam("lonCenter")double lonCenter)
	{

		List<CartStats> cartStats = Globals.cartStatsDAO.getCartStats(endUserID,cartID);


		for(CartStats cartStatsItem: cartStats)
		{
			cartStatsItem.setShop(Globals.shopService.getShop(cartStatsItem.getShopID(),latCenter,lonCenter));
		}


		GenericEntity<List<CartStats>> listEntity = new GenericEntity<List<CartStats>>(cartStats){
		};
		
		if(cartStats.size()<=0)
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
