package onetoone.Courses;

import onetoone.Resources.StudyResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 
 * @author Rahul Sudev
 * 
 */ 

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findCourseByCourseDepartment(String courseDepartment);
    Course findById(long id);

    Course findCourseBystudyResourceList(StudyResources sr);

    @Transactional
    void deleteById(long id);
}
