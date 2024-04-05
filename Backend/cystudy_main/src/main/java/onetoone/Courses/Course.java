package onetoone.Courses;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import onetoone.Groups.StudyGroup;
import onetoone.Users.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Rahul Sudev
 */ 

@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int courseCode;
    private String courseName;
    private String courseDepartment;


    //@JsonManagedReference
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="course_user", joinColumns = {@JoinColumn(name="course_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")})
    private Set<User> userSet;

    
    // =============================== Constructors ================================== //

    public Course(String courseName, String courseDepartment,int courseCode) {
        this.courseName = courseName;
        this.courseDepartment = courseDepartment;
        this.courseCode = courseCode;
        this.userSet = new HashSet<>();
    }

    public Course() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDepartment() {
        return courseDepartment;
    }

    public void setCourseDepartment(String courseDepartment) {
        this.courseDepartment = courseDepartment;
    }

    public Set<User> getStudents() {
        return userSet;
    }

    public void addUser(User user) {
        userSet.add(user);
    }
}
