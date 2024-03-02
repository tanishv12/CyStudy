package onetoone.Courses;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import onetoone.Groups.StudyGroup;
import onetoone.Users.User;

import java.util.Set;

/**
 * 
 * @author Rahul Sudev
 */ 

@Entity
@Table(name = "COURSE")
public class Course {
    
    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int courseCode;
    private String courseName;
    private String courseDepartment;




    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
    @ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;

    @OneToMany(mappedBy = "course")
    private Set<StudyGroup> studyGroups;

    public Course(String courseName, String courseDepartment,int courseCode, Set<User> users) {
        this.courseName = courseName;
        this.courseDepartment = courseDepartment;
        this.courseCode = courseCode;
        this.users = users;
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
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
