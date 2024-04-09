package onetoone.Groups;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer> {

    StudyGroup findById(long id);

    StudyGroup findStudyGroupByGroupName(String groupName);


    void deleteById(long id);

    void deleteStudyGroupByGroupName(String name);


}
