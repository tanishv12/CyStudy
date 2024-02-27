package onetoone.Courses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Rahul Sudev
 * 
 */ 

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findById(long id);

    @Transactional
    void deleteById(long id);
}
