package onetoone.Users;

import jakarta.persistence.*;

import onetoone.Courses.Course;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Rahul Sudev
 * 
 */ 

@Entity
@Table(name = "USER")
public class User {

     /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String emailId;
    private boolean ifActive;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name ="USER_COURSE", joinColumns = {@JoinColumn(name = "student_id",referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "course_id",referencedColumnName ="id")})
    private Set<Course> courses;

    public User(String name, String emailId) {
        this.name = name;
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
        return courses;
    }

    //Method to add course to hashset
    public void addCourse(Course course) {
        if (courses == null) {
            courses = new HashSet<Course>();
        }
        courses.add(course);
    }

    //Method to remove course from hashset
    public void removeCourse(Course course){
        courses.remove(course);
    }
}
