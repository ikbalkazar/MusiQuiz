package com.example.restapi;

import com.example.restapi.Database.Friends;
import com.example.restapi.Database.Users;
import com.example.restapi.Resource.FriendResource;
import com.example.restapi.Resource.UserResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;

public class RestApiTestService extends Service<RestApiTestConfiguration> {
    public static void main(String[] args) throws Exception {
        new RestApiTestService().run(args);
    }

    @Override
    public void initialize(Bootstrap<RestApiTestConfiguration> bootstrap) {
        bootstrap.setName("restapitest");
    }

    @Override
    public void run(RestApiTestConfiguration configuration, Environment environment) throws ClassNotFoundException {
        final DBIFactory factory = new DBIFactory();
        final DBI dbi = factory.build(environment, configuration.getDatabaseConfiguration(), "postgresql");

        final Users userManager = dbi.onDemand(Users.class);
        environment.addResource(new UserResource(userManager));

        final Friends friendsManager = dbi.onDemand(Friends.class);
        environment.addResource(new FriendResource(friendsManager));
    }
}
