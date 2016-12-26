package com.example.restapi.Resource;

import com.example.restapi.Database.Challenges;
import com.example.restapi.Database.Questions;
import com.example.restapi.Database.Songs;
import com.example.restapi.Model.Challenge;
import com.example.restapi.Model.Question;
import com.example.restapi.Model.Song;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ikbalkazar on 29/11/16.
 */
@Path("/challenge")
public class ChallengeResource {
    private static final int NUM_QUESTIONS = 10;

    private final Challenges challengeDB;
    private final Questions questionDB;
    private final Songs songDB;
    private AtomicInteger challengeCounter;
    private AtomicInteger questionCounter;

    public ChallengeResource(Challenges challengeDB, Questions questionDB, Songs songDB) {
        this.challengeDB = challengeDB;
        this.questionDB = questionDB;
        this.songDB = songDB;
        this.challengeCounter = new AtomicInteger();
        this.questionCounter = new AtomicInteger();
    }

    public class ChallengeWrapper {
        @JsonProperty
        private List<Challenge> challenges;
        public ChallengeWrapper(List<Challenge> challenges) {
            this.challenges = challenges;
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("me") String me) {
        List<Challenge> res = challengeDB.getFor(me);
        ChallengeWrapper wrapper = new ChallengeWrapper(res);
        return Response.status(Response.Status.OK).entity(wrapper).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@QueryParam("me") String me, @QueryParam("her") String her) {
        Challenge challenge = buildChallenge(me, her);
        challengeDB.insertChallenge(challenge);
        return Response.status(Response.Status.OK).build();
    }

    private Challenge buildChallenge(String me, String her) {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        String dateString = formatter.format(date);
        final int id = challengeCounter.getAndIncrement();
        generateQuestionSet(id);
        return new Challenge(String.valueOf(id), me, her, dateString, -1, -1, Challenge.Status.WAITING.ordinal());
    }

    private void generateQuestionSet(final int challengeId) {
        for (int i = 0; i < NUM_QUESTIONS; i++) {
            generateQuestion(challengeId);
        }
    }

    private void generateQuestion(final int challengeId) {
        String id = String.valueOf(questionCounter.getAndIncrement());

        int songCount = songDB.songCount();
        ArrayList<Song> chosenSongs = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String songId;
            while (true) {
                songId = String.valueOf((int)(Math.random() * songCount));
                boolean alreadyUsed = false;
                for (Song chosen: chosenSongs) {
                    if (chosen.getId().equals(songId)) {
                        alreadyUsed = true;
                    }
                }
                if (!alreadyUsed) {
                    break;
                }
            }
            chosenSongs.add(songDB.getSong(songId));
        }

        String[] choices = new String[4];
        if (Math.random() < 0.5) {
            for (int i = 0; i < 4; i++) {
                choices[i] = chosenSongs.get(i).getName();
            }
        } else {
            for (int i = 0; i < 4; i++) {
                choices[i] = chosenSongs.get(i).getArtist();
            }
        }

        Question question = new Question(id, String.valueOf(challengeId), choices, chosenSongs.get(0).getUrl());
        questionDB.addQuestion(question);
    }

    @PUT
    @Path("/accept")
    public Response accept(@QueryParam("me") String me, @QueryParam("id") String id) {
        Challenge challenge = challengeDB.getById(id);
        if (!challenge.getReceiver().equals(me) || challenge.getStatus() != Challenge.Status.WAITING.ordinal()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
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
