package com.example.restapi.Model;

/**
 * Created by ikbalkazar on 29/11/16.
 */
public class Challenge {
    // TODO: make status Status instead of int.
    public enum Status {
        WAITING, ACTIVE, ARCHIVED
    }

    private String id;
    private String sender;
    private String receiver;
    private String createdAt;
    private int senderScore;
    private int receiverScore;
    private int status;

    public Challenge(String id, String sender, String reciever, String createdAt, int senderScore, int recieverScore, int status) {
        this.id = id;
        this.sender = sender;
        this.receiver = reciever;
        this.createdAt = createdAt;
        this.senderScore = senderScore;
        this.receiverScore = recieverScore;
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setSenderScore(int senderScore) {
        this.senderScore = senderScore;
    }

    public void setRecieverScore(int recieverScore) {
        this.receiverScore = recieverScore;
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getSenderScore() {
        return senderScore;
    }

    public int getRecieverScore() {
        return receiverScore;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
