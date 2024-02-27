package onetoone.Courses;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import onetoone.Users.User;

/**
 * 
 * @author Rahul Sudev
 */ 

@Entity
public class Course {
    
    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courseId;
    private int courseCode;
    private String courseName;
    private String courseDepartment;




    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
    @OneToOne
    @JsonIgnore
    private User user;

    public Course(String courseName, String courseDepartment,int courseCode) {
        this.courseName = courseName;
        this.courseDepartment = courseDepartment;
        this.courseCode = courseCode;
    }

    public Course() {
    }

    // =============================== Getters and Setters for each field ================================== //


    public long getCourseIdId() {
        return courseId;
    }

    public void setCourseCodeId(long courseId) {
        this.courseId = courseId;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
