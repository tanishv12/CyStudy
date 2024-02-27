package onetoone.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Rahul Sudev
 * 
 */ 

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findById(long id);

    void deleteById(long id);

    User findByCourse_Id(long id);
}
