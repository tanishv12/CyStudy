package onetoone.Users;
<<<<<<< HEAD

=======
>>>>>>> create-rating-table
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import onetoone.Courses.Course;
import onetoone.Groups.StudyGroup;
import onetoone.Messages.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import onetoone.Rating.Rating;

import java.util.HashSet;
import java.util.List;
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
    private String userName;
    private String emailId;
    private boolean ifActive;

<<<<<<< HEAD

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name ="USER_COURSE", joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "course_id",referencedColumnName ="id")})
    @JsonIgnore
    private Set<Course> courseSet;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
//    @JoinTable(name = "USER_COURSE", joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id"))
//    private Set<Course> courseSet;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "USER_GROUP", joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "group_id",referencedColumnName = "id")})
    @JsonIgnore
    private Set<StudyGroup> studyGroups;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
//    @JoinTable(name = "USER_STUDYGROUP", joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "group_id"))
//    private Set<StudyGroup> studyGroupList;

=======
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userSet" )
    private Set<Course> courseSet;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userSet")
    private Set<StudyGroup> groupSet;
>>>>>>> create-rating-table

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Message> messageSet;


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
    }

    public User() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public String savePassword(String password){
        return encoder().encode(password);
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

    @Bean
    public PasswordEncoder encoder(){

        return new BCryptPasswordEncoder();
    }


}
