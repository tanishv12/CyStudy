package onetoone.Courses;
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
    private String courseTitle;
    private String courseDepartment;

    @Column(unique = true)
    private String courseName = "";

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="course_user", joinColumns = {@JoinColumn(name="course_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")})
    @JsonIgnore
    private Set<User> userSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<StudyGroup> groupSet;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
//    private Set<StudyResources> studyResourcesSet;




    // =============================== Constructors ================================== //

    public Course(String courseTitle, String courseDepartment,int courseCode) {
        this.courseTitle = courseTitle;
        this.courseDepartment = courseDepartment;
        this.courseCode = courseCode;
        this.userSet = new HashSet<User>();
        this.groupSet = new HashSet<StudyGroup>();
        courseName = courseDepartment + " " + courseCode;
    }

    public Course() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public long getid() {
        return id;
    }

    public void setid(long id) {
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

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseName) {
        this.courseTitle = courseName;
    }

    public String getCourseDepartment() {
        return courseDepartment;
    }

    public void setCourseDepartment(String courseDepartment) {
        this.courseDepartment = courseDepartment;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public void addUser(User user) {
        userSet.add(user);
    }

    public void removeUser(User user){userSet.remove(user);}

    public Set<StudyGroup> getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(Set<StudyGroup> groupSet) {
        this.groupSet = groupSet;
    }
}
