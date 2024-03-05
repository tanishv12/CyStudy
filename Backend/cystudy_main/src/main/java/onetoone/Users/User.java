package onetoone.Users;

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
    private String emailId;
    private boolean ifActive;


//    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinTable(name ="USER_COURSE", joinColumns = {@JoinColumn(name = "student_id",referencedColumnName = "id")},
//    inverseJoinColumns = {@JoinColumn(name = "course_id",referencedColumnName ="id")})
//    @JsonIgnore
//    private Set<Course> courses;
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "USER_COURSE", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courseSet;

//    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinTable(name = "USER_GROUP", joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
//    inverseJoinColumns = {@JoinColumn(name = "group_id",referencedColumnName = "id")})
//    @JsonIgnore
//    private Set<StudyGroup> studyGroups;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "USER_STUDYGROUP", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<StudyGroup> studyGroupList;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> messageList;

    // =============================== Constructors ================================== //

    public User(String name, String emailId, String password) {
        this.name = name;
        this.password = password;
        this.emailId = emailId;
        this.ifActive = true;
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

    public boolean getIsActive(){
        return ifActive;
    }

    public void setIfActive(boolean ifActive){
        this.ifActive = ifActive;
    }

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
