package onetoone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import onetoone.Courses.Course;
import onetoone.Courses.CourseRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@SpringBootApplication
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Create 3 users with their machines
    /**
     * 
     * @param userRepository repository for the User entity
     * @param courseRepository repository for the Course entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in User.java just associating the Laptop object with the User will save it into the database because of the CascadeType
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, CourseRepository courseRepository) {
        return args -> {
            User user1 = new User("John", "john@somemail.com","lol");
            User user2 = new User("Jane", "jane@somemail.com","lol");
            User user3 = new User("Justin", "justin@somemail.com","lol");
            Course course1 = new Course( "Calculus 1","MATH",166);
            Course course2 = new Course( "Intro to Object Oriented Programming","COM S", 227);
            Course course3 = new Course( "Written,oral,Visual, and Electronic compostition","ENG",250);
            user1.addCourse(course1);
            user2.addCourse(course2);
            user3.addCourse(course3);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

        };
    }

}
