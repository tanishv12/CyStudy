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
 * @author Vivek Bengre
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

    @GetMapping(path = "/laptops")
    List<Course> getAllLaptops(){
        return courseRepository.findAll();
    }

    @GetMapping(path = "/laptops/{id}")
    Course getLaptopById(@PathVariable int id){
        return courseRepository.findById(id);
    }

    @PostMapping(path = "/laptops")
    String createLaptop(Course Course){
        if (Course == null)
            return failure;
        courseRepository.save(Course);
        return success;
    }

    @PutMapping(path = "/laptops/{id}")
    Course updateLaptop(@PathVariable int id, @RequestBody Course request){
        Course course = courseRepository.findById(id);
        if(course == null)
            return null;
        courseRepository.save(request);
        return courseRepository.findById(id);
    }

    @DeleteMapping(path = "/laptops/{id}")
    String deleteLaptop(@PathVariable int id){

        // Check if there is an object depending on user and then remove the dependency
        User user = userRepository.findByLaptop_Id(id);
        user.setLaptop(null);
        userRepository.save(user);

        // delete the laptop if the changes have not been reflected by the above statement
        courseRepository.deleteById(id);
        return success;
    }
}
