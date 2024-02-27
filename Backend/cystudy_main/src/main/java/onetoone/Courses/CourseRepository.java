package onetoone.Courses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findById(int id);

    @Transactional
    void deleteById(int id);
}
