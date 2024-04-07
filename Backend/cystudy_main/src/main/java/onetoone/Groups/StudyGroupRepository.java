package onetoone.Groups;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer> {

    StudyGroup findById(long id);

    StudyGroup findStudyGroupByGroupName(String name);


    void deleteById(long id);
}
