/**
 * Created by ikbalkazar on 26/12/16.
 */
public class Challenge {
    private String id;
    private String sender;
    private String receiver;
    private String createdAt;
    private int senderScore;
    private int receiverScore;
    // Status of the challenge, 0 -> pending confirmation, 1 -> accepted by opponent, 2 -> both played and finished.
    private int status;

    public Challenge(String id, String sender, String receiver, String createdAt, int senderScore, int receiverScore, int status) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.createdAt = createdAt;
        this.senderScore = senderScore;
        this.receiverScore = receiverScore;
        this.status = status;
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

    public int getReceiverScore() {
        return receiverScore;
    }

    public int getStatus() {
        return status;
    }
}
