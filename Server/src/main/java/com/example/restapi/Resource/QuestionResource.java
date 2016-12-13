package com.example.restapi.Resource;

import com.example.restapi.Database.Questions;
import com.example.restapi.Model.Question;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by ikbalkazar on 13/12/16.
 */

@Path("/question")
public class QuestionResource {
    private Questions questionDB;

    public QuestionResource(Questions questionDB) {
        this.questionDB = questionDB;
    }

    @GET
    @Path("/{challengeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestions(@PathParam("challengeId") String challengeId) {
        List<Question> questions = questionDB.getForChallenge(challengeId);
        if (questions == null || questions.size() != 10) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(questions).build();
    }
}
