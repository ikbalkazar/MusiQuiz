package com.example.restapi.Model;

/**
 * Created by ikbalkazar on 29/11/16.
 */
public class Question {
    private String id;
    private String challengeId;
    private String[] choices;
    private String songUrl;

    public Question(String id, String challengeId, String[] choices, String songUrl) {
        this.id = id;
        this.challengeId = challengeId;
        this.choices = choices;
        this.songUrl = songUrl;
    }

    public Question() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}
