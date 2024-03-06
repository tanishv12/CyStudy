package onetoone.Messages;

import jakarta.persistence.*;
import onetoone.Groups.StudyGroup;
import onetoone.Users.User;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

/**
 * @author Saeshu Karthika
 */

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String messageContent;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.ALL)// many messages can belong to one studyGroup
    @JoinColumn(name="group_id")
    private StudyGroup studyGroup;

    @ManyToOne(cascade = CascadeType.ALL)// many messages can belong to one user
    @JoinColumn(name="user_id")
    private User sender;


    // =============================== Constructors ================================== //

    public Message(String messageContent, User user) {
        this.messageContent = messageContent;
        this.sender = user;

    }

    public Message() {
    }

    // =============================== Getters and Setters for each field ================================== //

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
