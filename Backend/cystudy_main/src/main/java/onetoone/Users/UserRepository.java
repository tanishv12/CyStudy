package onetoone.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 
 * @author Rahul Sudev
 * 
 */ 

public interface UserRepository extends JpaRepository<User, Long> {
    
    List<User> findByNameContaining(String name);
    User findById(long id);

    void deleteById(long id);

    @Query("SELECT u FROM User u WHERE u.name = :username")
    User findByUsername(String username);


}
