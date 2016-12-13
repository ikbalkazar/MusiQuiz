package com.example.restapi.Resource;

import com.example.restapi.Database.Songs;
import com.example.restapi.Model.Song;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by ikbalkazar on 13/12/16.
 */

@Path("/songs")
public class SongResource {
    private Songs songDB;

    public SongResource(Songs songDB) {
        this.songDB = songDB;
    }

    @GET
    @Path("/url/{id}")
    public Response getUrl(@PathParam("id") String id) {
        Song song = songDB.getSong(id);
        if (song == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(song.getUrl()).build();
    }
}
