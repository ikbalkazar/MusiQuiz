package com.example.restapi.Resource;

import com.example.restapi.Database.Friends;
import com.example.restapi.Database.Users;
import com.example.restapi.Model.User;
import com.sun.jersey.core.impl.provider.entity.StreamingOutputProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by ikbalkazar on 29/11/16.
 */
@Path("/user")
public class UserResource {
    private final Users userDB;

    public UserResource(Users users) {
        this.userDB = users;
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {
        User res = userDB.getUser(username);
        if (res == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @POST
    @Path("/create/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        if (userDB.getUser(user.getUsername()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        userDB.insertUser(user.getUsername(), user.getPassword(), user.getSecurityQuestion(), user.getSecurityAnswer());
        return Response.status(Response.Status.OK).build();
    }
}
