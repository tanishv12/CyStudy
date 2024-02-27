package onetoone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import onetoone.Courses.Course;
import onetoone.Courses.CourseRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;

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
     * @param courseRepository repository for the Laptop entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in User.java just associating the Laptop object with the User will save it into the database because of the CascadeType
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, CourseRepository courseRepository) {
        return args -> {
            User user1 = new User("John", "john@somemail.com");
            User user2 = new User("Jane", "jane@somemail.com");
            User user3 = new User("Justin", "justin@somemail.com");
            Course course1 = new Course( 2.5, 4, 8, "Lenovo", 300);
            Course course2 = new Course( 4.1, 8, 16, "Hp", 800);
            Course course3 = new Course( 3.5, 32, 32, "Dell", 2300);
            user1.setLaptop(course1);
            user2.setLaptop(course2);
            user3.setLaptop(course3);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

        };
    }

}
