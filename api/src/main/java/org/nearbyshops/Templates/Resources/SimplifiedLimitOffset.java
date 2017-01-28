package org.nearbyshops.Templates.Resources;

import org.nearbyshops.DAOsPrepared.ShopDAO;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelEndPoints.ShopEndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sumeet on 13/12/16.
 */
public class SimplifiedLimitOffset {

    private ShopDAO shopDAO = Globals.shopDAO;




    @GET // an example of simplified limit and offset
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/QuerySimple")
    public Response getShopListSimple(
            @QueryParam("Enabled")Boolean enabled,
            @QueryParam("Waitlisted") Boolean waitlisted,
            @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
            @QueryParam("deliveryRangeMax")Double deliveryRangeMax,
            @QueryParam("deliveryRangeMin")Double deliveryRangeMin,
            @QueryParam("proximity")Double proximity,
            @QueryParam("SearchString") String searchString,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit") Integer limit, @QueryParam("Offset") int offset
    )
    {

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



        ShopEndPoint endPoint = shopDAO.getShopsListQuerySimple(
                enabled,waitlisted,
                null,
                latCenter,lonCenter,
                deliveryRangeMin,deliveryRangeMax,
                proximity,searchString,
                sortBy,limit,offset);


        endPoint.setLimit(limit);
        endPoint.setMax_limit(max_limit);
        endPoint.setOffset(offset);


		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/



        //Marker
        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }
}
