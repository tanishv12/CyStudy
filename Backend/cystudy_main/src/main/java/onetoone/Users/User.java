package onetoone.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import onetoone.Courses.Course;
import onetoone.Groups.StudyGroup;
import onetoone.Messages.Message;
import onetoone.Rating.Rating;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import onetoone.Rating.Rating;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Rahul Sudev
 * @author Saeshu Karthika
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

    @Column(unique = true)
    private String userName;
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String emailId;
    private boolean ifActive;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userSet" )
    @JsonIgnore
    private Set<Course> courseSet;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userSet")
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<StudyGroup> groupSet;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sender", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Message> messageSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Rating> ratingSet;


    // =============================== Constructors ================================== //

    public User(String name, String userName, String emailId, String password) {
        this.name = name;
        this.password = savePassword(password);
        this.userName = userName;
        this.emailId = emailId;
        this.ifActive = true;
        this.courseSet = new HashSet<Course>();
        this.groupSet = new HashSet<StudyGroup>();
        this.messageSet = new HashSet<Message>();
        this.ratingSet = new HashSet<Rating>();
    }

    public User() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public String savePassword(String password){
        return encoder().encode(password);
    }

    public long getid(){
        return id;
    }

    public void setid(long id){
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

    public boolean isIfActive() {
        return ifActive;
    }

    public void setIfActive(boolean ifActive){
        this.ifActive = ifActive;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Set<Course> getCourseSet() {
        return courseSet;
    }

    public void setCourseSet(Set<Course> courseSet) {
        this.courseSet = courseSet;
    }


    public Set<StudyGroup> getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(Set<StudyGroup> groupSet) {
        this.groupSet = groupSet;
    }

    public Set<Message> getMessageSet() {
        return messageSet;
    }

    public void setMessageSet(Set<Message> messageSet) {
        this.messageSet = messageSet;
    }

    public Set<Rating> getRatingSet() {
        return ratingSet;
    }

    public void setRatingSet(Set<Rating> ratingSet) {
        this.ratingSet = ratingSet;
    }

    //Method to add course to hashset
    public void addCourse(Course course) {
        if (courseSet == null) {
            courseSet = new HashSet<Course>();
        }
        courseSet.add(course);
    }
    //Method to add studyGroup to user
    public void addStudyGroup(StudyGroup studyGroup){
        if(groupSet == null){
            groupSet = new HashSet<StudyGroup>();
        }
        groupSet.add(studyGroup);
    }

    public void addRating(Rating rating){
        ratingSet.add(rating);
    }

    //Method to remove course from hashset
    public void removeCourse(Course course){courseSet.remove(course);
    }

    public void addMessage(Message message){messageSet.add(message);}

    public void removeMessage(Message message){messageSet.remove(message);}

    @Bean
    public PasswordEncoder encoder(){

        return new BCryptPasswordEncoder();
    }


}
