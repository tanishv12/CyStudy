package onetoone.Courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import onetoone.Users.User;
import onetoone.Users.UserRepository;

/**
 * 
 * @author Rahul Sudev
 * 
 */ 

@RestController
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/courses")
    List<Course> getAllLaptops(){
        return courseRepository.findAll();
    }

    @GetMapping(path = "/courses/{id}")
    Course getLaptopById(@PathVariable long id){
        return courseRepository.findById(id);
    }

    @PostMapping(path = "/courses")
    String createLaptop(Course course){
        if (course == null)
            return failure;
        courseRepository.save(course);
        return success;
    }

    @PutMapping(path = "/courses/{id}")
    Course updateLaptop(@PathVariable long id, @RequestBody Course request){
        Course course = courseRepository.findById(id);
        if(course == null)
            return null;
        courseRepository.save(request);
        return courseRepository.findById(id);
    }

    @DeleteMapping(path = "/courses/{id}")
    String deleteLaptop(@PathVariable long id){

        // Check if there is an object depending on user and then remove the dependency
        User user = userRepository.findByCourse_Id(id);
        user.setCourse(null);
        userRepository.save(user);

        // delete the laptop if the changes have not been reflected by the above statement
        courseRepository.deleteById(id);
        return success;
    }
}
