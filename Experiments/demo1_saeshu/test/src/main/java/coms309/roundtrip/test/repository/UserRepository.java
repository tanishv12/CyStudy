package coms309.roundtrip.test.repository;

import coms309.roundtrip.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findbyID(Long id);
}
