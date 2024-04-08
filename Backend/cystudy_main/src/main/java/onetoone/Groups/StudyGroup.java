package onetoone.Groups;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import onetoone.Courses.Course;
import onetoone.Messages.Message;
import onetoone.Rating.Rating;
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

    @Column(nullable = false)
    private double avgRating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studyGroup", cascade = CascadeType.ALL)
    private Set<Message> messageSet;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_user", joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<User> userSet;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "studyGroup", cascade = CascadeType.ALL)
   private List<Rating> ratingList;


    // =============================== Constructors ================================== //

    public StudyGroup() {
    }

    public StudyGroup(String groupName) {
        this.groupName = groupName;
        this.avgRating = 0;
        this.userSet = new HashSet<User>();
        this.messageSet = new HashSet<Message>();
        this.ratingList = new ArrayList<Rating>();
    }

    // =============================== Getters and Setters for each field ================================== //

    public long getid() {
        return id;
    }

    public void setid(long id) {
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Message> getMessageSet() {
        return messageSet;
    }

    public void setMessageSet(Set<Message> messageSet) {
        this.messageSet = messageSet;
    }


    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public void addUser(User user){
        userSet.add(user);
    }

    public void removeUser(User user){ userSet.remove(user);}

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public void addRating(Rating rating){
        ratingList.add(rating);
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }
}