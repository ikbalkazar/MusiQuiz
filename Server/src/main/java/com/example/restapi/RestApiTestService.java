package com.example.restapi;

import com.example.restapi.Database.*;
import com.example.restapi.Model.Challenge;
import com.example.restapi.Model.Question;
import com.example.restapi.Model.Song;
import com.example.restapi.Resource.*;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;

import java.util.List;

public class RestApiTestService extends Service<RestApiTestConfiguration> {
    public static void main(String[] args) throws Exception {
        new RestApiTestService().run(args);
    }

    @Override
    public void initialize(Bootstrap<RestApiTestConfiguration> bootstrap) {
        bootstrap.setName("restapitest");
    }

    @Override
    public void run(RestApiTestConfiguration configuration, Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        final DBI dbi = factory.build(environment, configuration.getDatabaseConfiguration(), "postgresql");

        final Users userManager = dbi.onDemand(Users.class);
        environment.addResource(new UserResource(userManager));

        final Friends friendsManager = dbi.onDemand(Friends.class);
        environment.addResource(new FriendResource(friendsManager));

        final Challenges challengeManager = dbi.onDemand(Challenges.class);
        final Questions questionManager = dbi.onDemand(Questions.class);
        final Songs songManager = dbi.onDemand(Songs.class);
        environment.addResource(new ChallengeResource(challengeManager, questionManager, songManager));

        environment.addResource(new QuestionResource(questionManager));

        environment.addResource(new SongResource(songManager));
    }

    private void testQuestions(final DBI dbi) throws Exception {
        final Questions manager = dbi.onDemand(Questions.class);
        String[] choices = new String[4];
        for (int i = 0; i < 4; i++) {
            choices[i] = "choice" + i;
        }
        manager.addQuestion(new Question("question1", "challenge1", choices, "song1"));
        List<Question> res = manager.getForChallenge("challenge1");
        if (res.size() != 1) {
            throw new Exception("Questions Failed");
        }
        System.out.println("Res = " + res.get(0).getId());
    }

    private void testSongs(final DBI dbi) throws Exception {
        final Songs songs = dbi.onDemand(Songs.class);
        Song song = songs.getSong("3");
        if (song == null || !song.getId().equals("3")) {
            throw new Exception("Songs failed");
        }
    }

    private void testChallenges(final DBI dbi) throws Exception {
        final Challenges challenges = dbi.onDemand(Challenges.class);
        Challenge challenge = new Challenge("1", "some-sender", "some-reciever", "some-date", 1, 3, Challenge.Status.ARCHIVED.ordinal());
        challenges.insertChallenge(challenge);
    }
}
