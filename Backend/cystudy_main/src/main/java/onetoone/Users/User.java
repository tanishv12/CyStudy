package onetoone.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import onetoone.Courses.Course;
import onetoone.Groups.StudyGroup;
import onetoone.Messages.Message;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Rahul Sudev
 * 
 */ 

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;
    private String userName;
    private String emailId;
    private boolean ifActive;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userSet" )
    private Set<Course> courseSet;


    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "USER_GROUP", joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "group_id",referencedColumnName = "id")})
    @JsonIgnore
    private Set<StudyGroup> studyGroups;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> messageList;

    // =============================== Constructors ================================== //

    public User(String name, String userName, String emailId, String password) {
        this.name = name;
        this.password = password;
        this.userName = userName;
        this.emailId = emailId;
        this.ifActive = true;
    }

    public User() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isIfActive() {
        return ifActive;
    }

    public Set<Course> getCourseSet() {
        return courseSet;
    }

    public void setCourseSet(Set<Course> courseSet) {
        this.courseSet = courseSet;
    }

    public Set<StudyGroup> getStudyGroups() {
        return studyGroups;
    }

    public void setStudyGroups(Set<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId(){
        return emailId;
    }

    public void setEmailId(String emailId){
        this.emailId = emailId;
    }

    public boolean getIsActive(){
        return ifActive;
    }

    public void setIfActive(boolean ifActive){
        this.ifActive = ifActive;
    }

    @JsonIgnore
    public Set<Course> getCourses() {
        return courseSet;
    }

    //Method to add course to hashset
    public void addCourse(Course course) {
        if (courseSet == null) {
            courseSet = new HashSet<Course>();
        }
        courseSet.add(course);
    }

    //Method to remove course from hashset
    public void removeCourse(Course course){courseSet.remove(course);
    }
}
