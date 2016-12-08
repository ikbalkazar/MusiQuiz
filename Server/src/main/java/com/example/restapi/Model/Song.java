package com.example.restapi.Model;

/**
 * Created by ikbalkazar on 08/12/16.
 */
public class Song {
    private String id;
    private String name;
    private String artist;
    private String url;

    public Song(String id, String name, String artist, String url) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
