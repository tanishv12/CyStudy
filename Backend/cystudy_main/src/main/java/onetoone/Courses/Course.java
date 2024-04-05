package onetoone.Courses;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import onetoone.Groups.StudyGroup;
import onetoone.Resources.StudyResources;
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


    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
//    @ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Set<User> users;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="course_user", joinColumns = {@JoinColumn(name="course_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")})
    private Set<User> userSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
    private List<StudyGroup> studyGroupList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
    private List<StudyResources> studyResourceList;

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

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public List<StudyGroup> getStudyGroupList() {
        return studyGroupList;
    }

    public void setStudyGroupList(List<StudyGroup> studyGroupList) {
        this.studyGroupList = studyGroupList;
    }

    public List<StudyResources> getStudyResourceList() {
        return studyResourceList;
    }

    public void setStudyResourceList(List<StudyResources> studyResourceList) {
        this.studyResourceList = studyResourceList;
    }
}
