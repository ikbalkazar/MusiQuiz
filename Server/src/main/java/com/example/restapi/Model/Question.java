package com.example.restapi.Model;

/**
 * Created by ikbalkazar on 29/11/16.
 */
public class Question {
    // TODO: add challengeId here
    private String id;
    private String[] choices;
    private String url;
    private String songName;
    private String artistName;

    public Question() {}

    public Question(String id, String[] choices, String playUrl, String songName, String artistName) {
        this.id = id;
        this.choices = choices;
        this.url = playUrl;
        this.songName = songName;
        this.artistName = artistName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public void setPlayUrl(String playUrl) {
        this.url = playUrl;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getId() {
        return id;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getPlayUrl() {
        return url;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }
}
