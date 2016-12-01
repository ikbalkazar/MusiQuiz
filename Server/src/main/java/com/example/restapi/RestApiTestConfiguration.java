package com.example.restapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.validation.Validated;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

public class RestApiTestConfiguration extends Configuration {
    @Valid
    @JsonProperty
    private DatabaseConfiguration database = new DatabaseConfiguration();

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }
}
