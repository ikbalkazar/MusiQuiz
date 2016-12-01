package com.example.restapi.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ikbalkazar on 29/11/16.
 */
public class User {
    private String username;
    private String password;
    private String securityQuestion;
    private String securityAnswer;

    public User() {}

    public User(String username, String password, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    @JsonProperty
    public String getSecurityAnswer() {
        return securityAnswer;
    }

}
