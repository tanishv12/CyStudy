package onetoone.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import onetoone.Courses.Course;
import onetoone.Groups.StudyGroup;
import onetoone.Messages.Message;
//import onetoone.Rating.Rating;

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

    //@JsonBackReference

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userSet" )
    private Set<Course> courseSet;




    // =============================== Constructors ================================== //

    public User(String name, String userName, String emailId, String password) {
        this.name = name;
        this.password = password;
        this.userName = userName;
        this.emailId = emailId;
        this.ifActive = true;
        this.courseSet = new HashSet<Course>();
    }

    public User() {
    }

    // =============================== Getters and Setters for each field ================================== //

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


    @JsonIgnore
    public Set<Course> getCourseSet() {
        return courseSet;
    }

    public void setCourseSet(Set<Course> courseSet) {
        this.courseSet = courseSet;
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
