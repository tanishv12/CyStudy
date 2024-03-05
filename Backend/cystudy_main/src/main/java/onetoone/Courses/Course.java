package onetoone.Courses;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import onetoone.Groups.StudyGroup;
import onetoone.Users.User;

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


    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
//    @ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Set<User> users;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "USER_COURSE", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userSet;

//    @OneToMany(mappedBy = "course")
//    private Set<StudyGroup> studyGroups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
    private List<StudyGroup> studyGroupList;

    // =============================== Constructors ================================== //

    public Course(String courseName, String courseDepartment,int courseCode) {
        this.courseName = courseName;
        this.courseDepartment = courseDepartment;
        this.courseCode = courseCode;
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
