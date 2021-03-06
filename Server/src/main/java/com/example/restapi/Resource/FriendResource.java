package com.example.restapi.Resource;

import com.example.restapi.Database.Friends;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by ikbalkazar on 29/11/16.
 */
@Path("/friends")
public class FriendResource {
    private final Friends friendDB;

    public FriendResource(Friends friends) {
        this.friendDB = friends;
    }

    @GET
    @Path("/{me}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFriends(@PathParam("me") String me) {
        List<String> friends = friendDB.getFriendsOf(me);
        FriendsWrapper wrapper = new FriendsWrapper(friends);
        return Response.status(Response.Status.OK).entity(wrapper).build();
    }

    @POST
    @Path("/{me}/{her}")
    public Response addFriend(@PathParam("me") String me, @PathParam("her") String her) {
        friendDB.addFriend(me, her);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{me}/{her}")
    public Response removeFriend(@PathParam("me") String me, @PathParam("her") String her) {
        friendDB.removeFriend(me, her);
        return Response.status(Response.Status.OK).build();
    }

    public class FriendsWrapper {
        @JsonProperty
        private List<String> friends;
        public FriendsWrapper(List<String> friends) {
            this.friends = friends;
        }
    }
}
