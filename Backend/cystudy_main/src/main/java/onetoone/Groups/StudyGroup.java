package onetoone.Groups;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import onetoone.Courses.Course;
import onetoone.Messages.Message;
//import onetoone.Rating.Rating;
import onetoone.Users.User;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * @author Saeshu Karthika
 */
@Entity
@Table(name = "STUDYGROUP")
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String groupName;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp creationTime;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "studyGroup",cascade = CascadeType.ALL)
    private Set<Message> messageSet;

//    @ManyToMany(mappedBy = "studyGroups",fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<User> users;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_STUDYGROUP", joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userList;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studyGroup", cascade = CascadeType.ALL)
//    private List<Rating> ratingList;


    // =============================== Constructors ================================== //

    public StudyGroup(){}

    public StudyGroup(String groupName) {
        this.groupName = groupName;
    }

    // =============================== Getters and Setters for each field ================================== //

    public Course getCourse() {
        return course;
    }

    public Set<Message> getMessageSet() {
        return messageSet;
    }

    public void setMessageSet(Set<Message> messageSet) {
        this.messageSet = messageSet;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<User> getUsers() {
        return userList;
    }

    public void setUsers(List<User> users) {
        this.userList = users;
    }

    public void addUser(User user){
        if(this.userList == null){
            this.userList = new ArrayList<>();
        }
        this.userList.add(user);
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
}
