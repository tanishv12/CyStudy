package onetoone.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import onetoone.Groups.StudyGroup;
import onetoone.Users.User;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

/**
 * @author Saeshu Karthika
 * @author Rahul Sudev
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

    @ManyToOne(cascade = CascadeType.MERGE)// many messages can belong to one studyGroup
    @JoinColumn(name="group_id")
    @JsonIgnore
    private StudyGroup studyGroup;

    @ManyToOne(fetch = FetchType.LAZY)// many messages can belong to one user
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User sender;

    // =============================== Constructors ================================== //

    public Message(String messageContent, User user, StudyGroup group) {
        this.messageContent = messageContent;
        this.sender = user;
        this.studyGroup = group;

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

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }
}
