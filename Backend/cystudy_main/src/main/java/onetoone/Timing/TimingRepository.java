package onetoone.Timing;

import onetoone.Groups.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimingRepository extends JpaRepository<Timing, Long> {

    Timing findByIdAndStudyGroup(Long timingId, StudyGroup group);
}
