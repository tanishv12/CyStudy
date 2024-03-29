package onetoone.Resources;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyResourceRepository extends JpaRepository<StudyResources, Long> {

    StudyResources findById(long id);


}
