package onetoone.Users;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import onetoone.Courses.Course;
import onetoone.Courses.CourseRepository;

/**
 * 
 * @author Rahul Sudev
 * 
 */ 

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    private String loginSuccess = "{\"message\":\"Login Successful\"}";
    private String loginFailure = "{\"message\":\"Login failed\"}";


    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable long id){
        return userRepository.findById(id);
    }

    @PostMapping(path = "/users/post")
    ResponseEntity<String> createUser(@RequestBody User user){
        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user credentials");;
        for(User prevUser: this.getAllUsers()){
            if(user.getEmailId().equals(prevUser.getEmailId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
            }
        }
        userRepository.save(user);
        System.out.println(user.getId());
        return ResponseEntity.ok("User created successfully");
    }
    @GetMapping(path = "/users/login")
    ResponseEntity<String> logUserIn(@RequestBody User user){
        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user credentials");
        }
        User storedUser = userRepository.findById(user.getId());
        if(storedUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        if (user.getPassword().equals(storedUser.getPassword()) && user.getName().equals(storedUser.getName())) {
            user.setIfActive(true);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }
    }

    /* not safe to update */
//    @PutMapping("/users/{id}")
//    User updateUser(@PathVariable int id, @RequestBody User request){
//        User user = userRepository.findById(id);
//        if(user == null)
//            return null;
//        userRepository.save(request);
//        return userRepository.findById(id);
//    }

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable long id, @RequestBody User request){
        User user = userRepository.findById(id);

        if(user == null) {
            throw new RuntimeException("user id does not exist");
        }
        else if (request.getId() != id){
            throw new RuntimeException("path variable id does not match User request id");
        }

        userRepository.save(request);
        return userRepository.findById(id);
    }

    @PostMapping("/users/{userId}/courses/{courseId}")
    String addCourseToUser(@PathVariable long userId,@PathVariable long courseId){
        User user = userRepository.findById(userId);
        Course course = courseRepository.findById(courseId);
        if(user == null || course == null)
            return failure;
        course.addUser(user);
        user.addCourse(course);
        userRepository.save(user);
        courseRepository.save(course);
        return success;
    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable long id){
       User user = userRepository.findById(id);
       if(user == null){
           return failure;
       }
        userRepository.deleteById(id);
        return success;
    }
}
