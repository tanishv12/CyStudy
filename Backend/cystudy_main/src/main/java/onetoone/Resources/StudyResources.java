package onetoone.Resources;
import jakarta.persistence.*;

import onetoone.Courses.Course;

/**
 *
 * @author Rahul Sudev
 */

@Entity
@Table(name = "STUDYRESOURCES")

public class StudyResources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String resourceName;

    private ResourceCategory resourceCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    public StudyResources() {
    }

    public StudyResources(long id, String resourceName, ResourceCategory resourceCategory,Course course) {
        this.id = id;
        this.resourceName = resourceName;
        this.resourceCategory = resourceCategory;
        this.course = course;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }

    public void setResourceCategory(ResourceCategory resourceCategory) {
        this.resourceCategory = resourceCategory;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}