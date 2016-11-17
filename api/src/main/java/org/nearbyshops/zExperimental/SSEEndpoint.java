package org.nearbyshops.zExperimental;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;
import org.nearbyshops.Globals.Globals;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sumeet on 21/9/16.
 */

@Singleton
@Path("/broadcast")
public class SSEEndpoint {

//    private SseBroadcaster broadcaster = new SseBroadcaster();



//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.TEXT_PLAIN)
//    public String broadcastMessage(String message) {
//        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
//        OutboundEvent event = eventBuilder.name("message")
//                .mediaType(MediaType.TEXT_PLAIN_TYPE)
//                .data(String.class, message)
//                .build();
//
//        broadcaster.broadcast(event);
//
//        return "Message '" + message + "' has been broadcast.";
//    }


    //    @Path("/{ShopID}")
    //@PathParam("ShopID")int shopID

    @GET
    @Path("/{ShopID}")
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
}
