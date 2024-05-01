package onetoone.Courses;

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

    Course findCourseByCourseName(String coursename);

    @Transactional
    void deleteById(long id);
}
