package onetoone.Messages;

import jakarta.persistence.*;
import onetoone.Groups.StudyGroup;
import onetoone.Users.User;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne // many messages can belong to one studyGroup
    @JoinColumn(name="group_id")
    private StudyGroup studyGroup;

    @ManyToOne// many messages can belong to one user
    @JoinColumn(name="user_id")
    private User sender;


    @Column(nullable = false)
    private String messageContent;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp timestamp;



    public Message(String messageContent) {
        this.messageContent = messageContent;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StudyGroup getGroup_id() {
        return studyGroup;
    }

    public void setGroup_id(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

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
