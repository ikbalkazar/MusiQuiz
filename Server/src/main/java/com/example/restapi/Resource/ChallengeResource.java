package com.example.restapi.Resource;

import com.example.restapi.Database.Challenges;
import com.example.restapi.Model.Challenge;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by ikbalkazar on 29/11/16.
 */
@Path("/challenge")
public class ChallengeResource {
    private final Challenges challengeDB;

    public ChallengeResource(Challenges challengeDB) {
        this.challengeDB = challengeDB;
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("me") String me) {
        List<Challenge> res = challengeDB.getFor(me);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@QueryParam("me") String me, @QueryParam("her") String her) {
        String nextChallengeId = String.valueOf(challengeDB.generateId());
        Challenge challenge = new Challenge(nextChallengeId, me, her, "shallbedateandtime", -1, -1, Challenge.Status.WAITING.ordinal());
        // TODO : create and link 10 random questions to this challenge as well
        challengeDB.insertChallenge(challenge);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/accept")
    public Response accept(@QueryParam("me") String me, @QueryParam("id") String id) {
        Challenge challenge = challengeDB.getById(id);
        if (!challenge.getReceiver().equals(me) || challenge.getStatus() != Challenge.Status.WAITING.ordinal()) {
            Response.status(Response.Status.BAD_REQUEST).build();
        }
        challengeDB.setStatus(Challenge.Status.ACTIVE.ordinal(), id);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/finish")
    public Response finish(@QueryParam("me") String me, @QueryParam("id") String id, @QueryParam("score") int score) {
        Challenge challenge = challengeDB.getById(id);
        if (challenge.getStatus() != Challenge.Status.ACTIVE.ordinal()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (challenge.getSender().equals(me)) {
            if (challenge.getSenderScore() != -1) {
                return Response.status(Response.Status.CONFLICT).build();
            }
            challengeDB.setSenderScore(id, score);
        } else {
            if (challenge.getRecieverScore() != -1) {
                return Response.status(Response.Status.CONFLICT).build();
            }
            challengeDB.setReceiverScore(id, score);
        }
        checkIfFinalized(id);
        return Response.status(Response.Status.OK).build();
    }

    private void checkIfFinalized(String id) {
        Challenge challenge = challengeDB.getById(id);
        if (challenge.getSenderScore() != -1 && challenge.getRecieverScore() != -1) {
            challengeDB.setStatus(Challenge.Status.ARCHIVED.ordinal(), id);
        }
    }
}
