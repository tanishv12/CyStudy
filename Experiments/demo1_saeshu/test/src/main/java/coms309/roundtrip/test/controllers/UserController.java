package coms309.roundtrip.test.controllers;

import coms309.roundtrip.test.model.User;
import coms309.roundtrip.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    
    @GetMapping("/users")
    List<User> getallUsers(){ return userRepository.findAll();}

    @GetMapping("/users/{id}")
    User getUserById(@PathVariable Long id){
        return userRepository.findById(id).get();
    }

    @PostMapping(path = "/users")
    String createUser(User user){
        if (user == null)
            return failure;
        userRepository.save(user);
        return success;
    }

    @PutMapping("/users/{id}")
    Optional<User> updateUser(@PathVariable Long id, @RequestBody User request){
        Optional<User> user = userRepository.findById(id);

        if(user == null) {
            throw new RuntimeException("user id does not exist");
        }
        else if (user.get().getId()!= id){
            throw new RuntimeException("path variable id does not match User request id");
        }

        userRepository.save(request);
        return userRepository.findById(id);
    }

}

