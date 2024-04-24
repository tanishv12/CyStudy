package onetoone.Messages;

import onetoone.Groups.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message findById(int id);

    void deleteById(int id);

    List<Message> findAllByStudyGroupOrderByTimestampAsc(StudyGroup group);
}
