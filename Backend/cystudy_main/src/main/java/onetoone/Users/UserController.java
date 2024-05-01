package onetoone.Users;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import onetoone.Groups.StudyGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import onetoone.Courses.Course;
import onetoone.Courses.CourseRepository;

/**
 * 
 * @author Rahul Sudev
 * @author Saeshu Karthika
 * 
 */ 

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;


    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    private String loginSuccess = "{\"message\":\"Login Successful\"}";
    private String loginFailure = "{\"message\":\"Login failed\"}";


    @GetMapping(path = "/users/all")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/id/{id}")
    User getUserById( @PathVariable long id){
        return userRepository.findById(id);
    }

   //this get does not return any list because of JSONignore
    @GetMapping(path = "/users/{userName}")
    User getUserByUserName( @PathVariable String userName){
        return userRepository.findByUserName(userName);
    }

    @PostMapping(path = "/users/register")
    ResponseEntity<String> createUser(@Valid @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return a custom response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address. Please check your input.");
        }
        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user credentials");;
        for(User prevUser: this.getAllUsers()){
            if(user.getEmailId().equals(prevUser.getEmailId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email address already exists");
            }
            if((user.getUserName()).equals(prevUser.getUserName())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserName already exists");
            }
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        // Generate session token (JWT) containing user ID
//        String token = generateToken(user.getId());
//
//        // Return the token to the client
//        return ResponseEntity.ok(token);

        return ResponseEntity.ok("User created successfully");
    }


//    @PostMapping(path = "/users/post/{id}/{userName}/{password}")
//    ResponseEntity<String> logUserIn(@PathVariable long id, @PathVariable String userName, @PathVariable String password, @RequestBody User user){
//        if(user == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user credentials");
//        }
//        User storedUser = userRepository.findById(id);
//        if(storedUser == null){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
//        }
//        if (password.equals(storedUser.getPassword()) && userName.equals(storedUser.getName())) {
//            user.setIfActive(true);
//            return ResponseEntity.ok("Login successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
//        }
//    }

    @PostMapping(path = "/users/login/{userName}/{password}")
    ResponseEntity<String> authenticateUser(@PathVariable String userName, @PathVariable String password) {

        Optional<User> optUser = Optional.ofNullable(userRepository.findByUserName(userName));
        if (optUser.isPresent()) {
            User dbUser = optUser.get();
            if (!(userName.equals(dbUser.getUserName()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username credentials");
            }
            if (!(passwordEncoder.matches(password, dbUser.getPassword()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password credentials");
            }
            if(!dbUser.isAdmin()){
                dbUser.setIfActive(true);
                userRepository.save(dbUser);
                return ResponseEntity.ok("Login successful");
            }
            dbUser.setIfActive(true);
            userRepository.save(dbUser);
            return ResponseEntity.ok("ADMIN:                                        Login successful");

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No user found");
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

    @PutMapping("/users/{userName}")
    User updateUser(@PathVariable String userName, @RequestBody User request){
        User user = userRepository.findByUserName(userName);

        if(user == null) {
            throw new RuntimeException("user id does not exist");
        }
        else if (request.getid() != user.getid()){
            throw new RuntimeException("path variable id does not match User request id");
        }

        userRepository.save(request);
        return userRepository.findById(user.getid());
    }

    @PutMapping("/users/username/{userName}/{newUserName}/{password}")
    String updateUserName(@PathVariable String userName, @PathVariable String newUserName, @PathVariable String password){
        User user = userRepository.findByUserName(userName);
        if(user == null) {
            throw new RuntimeException("user does not e");
        }
        for(User prevUser: this.getAllUsers()){
            if((newUserName).equals(prevUser.getUserName())){
                return "Username already exist";
            }
        }
        if (!(passwordEncoder.matches(password, user.getPassword()))) {
            return "Invalid password credentials";
        }
        user.setUserName(newUserName);
        userRepository.save(user);
        return "User name changed successfully";
    }

    @PutMapping("/users/password/{userName}/{oldPassword}/{newPassword}")
    String updatePassword(@PathVariable String userName, @PathVariable String oldPassword, @PathVariable String newPassword){
        User user = userRepository.findByUserName(userName);
        if(user == null) {
            throw new RuntimeException("user does not e");
        }
        if (!(passwordEncoder.matches(oldPassword, user.getPassword()))) {
            return "Invalid password credentials";
        }
        user.setPassword(user.savePassword(newPassword));
        userRepository.save(user);
        return "User name changed successfully";
    }

    @PutMapping("/users/name/{userName}/{oldName}/{newName}")
    String updateName(@PathVariable String userName, @PathVariable String oldName, @PathVariable String newName){
        User user = userRepository.findByUserName(userName);
        if(user == null) {
            throw new RuntimeException("user does not e");
        }
        user.setName(newName);
        userRepository.save(user);
        return "User name changed successfully";
    }


    @PutMapping("/users/logout/{userName}")
    String logOutUser(@PathVariable String userName){
        User user = userRepository.findByUserName(userName);

        if(user == null) {
            throw new RuntimeException("user id does not exist");
        }
        if (!user.isIfActive()){
            return "user is not logged in";
        }
        user.setIfActive(false);
        userRepository.save(user);
        return "Logged out successfully";
    }

    @DeleteMapping(path = "/users/delete/{userName}")
    String deleteUser(@PathVariable String userName){
        User user = userRepository.findByUserName(userName);
        if(user == null){
            return failure;
        }
        for(StudyGroup studyGroup : user.getGroupSet()){
            studyGroup.removeUser(user);
        }
        for(Course course : user.getCourseSet()){
            course.removeUser(user);
        }
//        userRepository.deleteByUserName(userName);
        userRepository.deleteById(user.getid());
        return success;
    }

    @PostMapping("/users/{userName}/courses/{courseId}")
    String addCourseToUser(@PathVariable String userName,@PathVariable long courseId){
        User user = userRepository.findByUserName(userName);
        Course course = courseRepository.findById(courseId);
        if(user == null || course == null)
            return failure;
        for (Course addedCourse: user.getCourseSet()){
            if(addedCourse.equals(course)){
                return failure;
            }
        }
        course.addUser(user);
        user.addCourse(course);
        userRepository.save(user);
        courseRepository.save(course);
        return success;
    }

//    @PostMapping("/users/addCourse/{id}")
//    String addCourse(@RequestBody Course course){
//        User user = userRepository.findById(id);
//        if(user == null || course == null)
//            return failure;
//        course.addUser(user);
//        user.addCourse(course);
//        userRepository.save(user);
//        courseRepository.save(course);
//        return success;
//    }



}
