package onetoone.Groups;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer> {

    StudyGroup findById(int id);
}
