package onetoone.Messages;

import jakarta.persistence.*;
import onetoone.Users.User;

import java.sql.Timestamp;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @ManyToOne // many messages can belong to one group
//    @JoinColumn(name="group_id)")
//    private Group group_id;
    @ManyToOne// many messages can belong to one user
    @JoinColumn(name="user_id")
    private User sender;
    @Column(nullable = false)
    private String messageContent;
    @Column(nullable = false)
    private Timestamp timestamp;

    public Message(String messageContent, Timestamp timestamp) {
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Group getGroup_id() {
//        return group_id;
//    }
//
//    public void setGroup_id(Group group_id) {
//        this.group_id = group_id;
//    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
