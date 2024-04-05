package onetoone.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void saveUser(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}
