package onetoone.Rating;

import onetoone.Groups.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
