package onetoone;

import onetoone.Groups.StudyGroup;
import onetoone.Groups.StudyGroupRepository;
import onetoone.Messages.Message;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import onetoone.Courses.Course;
import onetoone.Courses.CourseRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import onetoone.Messages.MessageRepository;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Saeshu Karthika & Rahul Sudev
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
    CommandLineRunner initUser(UserRepository userRepository, CourseRepository courseRepository, MessageRepository messageRepository, StudyGroupRepository studyGroupRepository) {
        return args -> {


//
//            User user1 = new User("John", "john@somemail.com", "lol");
//            User user2 = new User("Jane", "jane@somemail.com", "lol");
//            User user3 = new User("Justin", "justin@somemail.com", "lol");
//
            Course course1 = new Course("Calculus 1", "MATH", 166);
//
//            userRepository.save(user1);
//            userRepository.save(user2);
//            userRepository.save(user3);
//
//            course1.addUser(user1);
//            course1.addUser(user2);
//            course1.addUser(user3);
//
            courseRepository.save(course1);




//            Message message1 = new Message("How are you");
//            message1.setSender(user1);
//            messageRepository.save(message1);
//
//            StudyGroup group1 = new StudyGroup("Group 1");
//            group1.addUser(user1);
//            group1.addUser(user2);
//            group1.addUser(user3);
//            studyGroupRepository.save(group1);


        };
    }

}
