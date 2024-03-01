package onetoone.Messages;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findById(int id);

    void deleteById(int id);
}
