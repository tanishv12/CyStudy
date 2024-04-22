package onetoone;

import onetoone.Groups.StudyGroup;
import onetoone.Groups.StudyGroupRepository;
import onetoone.Messages.Message;
import onetoone.Rating.Rating;
import onetoone.Rating.RatingRepository;
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
    CommandLineRunner initUser(UserRepository userRepository, CourseRepository courseRepository, MessageRepository messageRepository, StudyGroupRepository studyGroupRepository, RatingRepository ratingRepository) {
        return args -> {

            User user1 = new User("John", "john123", "john@somemail.com", "lol");
            User user2 = new User("Sam", "sam123", "sam@somemail.com", "lol");

// Save users
            userRepository.save(user1);
            userRepository.save(user2);

// Create course
            Course course1 = new Course("Calculus 1", "MATH", 166);

// Add users to the course
            course1.addUser(user1);
            course1.addUser(user2);

// Save the course
            courseRepository.save(course1);

// Create study groups
            StudyGroup group1 = new StudyGroup("Group1");
            StudyGroup group2 = new StudyGroup("Group2");

// Save study groups
            studyGroupRepository.save(group1);
            studyGroupRepository.save(group2);

// Add users to study groups
            group1.addUser(user1);
            group1.addUser(user2);
            group2.addUser(user1);
            group2.addUser(user2);

// Save the study groups after adding users
            studyGroupRepository.save(group1);
            studyGroupRepository.save(group2);

// Create ratings
            Rating rating1 = new Rating(user1, group1, 2);
            Rating rating2 = new Rating(user1, group2, 1.5);
            Rating rating3 = new Rating(user2, group1, 2);
            Rating rating4 = new Rating(user2, group2, 1.5);

// Save ratings
            ratingRepository.save(rating1);
            ratingRepository.save(rating2);
            ratingRepository.save(rating3);
            ratingRepository.save(rating4);

// Save users after adding ratings
            userRepository.save(user1);
            userRepository.save(user2);



//
//            User user1 = new User("John", "john123","john@somemail.com", "lol");
//
//            User user2 = new User("Sam", "sam123","sam@somemail.com", "lol");
////            User user2 = new User("Jane", "jane@somemail.com", "lol");
////            User user3 = new User("Justin", "justin@somemail.com", "lol");
////
//            Course course1 = new Course("Calculus 1", "MATH", 166);
////
//            userRepository.save(user1);
//            userRepository.save(user2);
//
////            userRepository.save(user2);
////            userRepository.save(user3);
////
//            course1.addUser(user1);
//            course1.addUser(user2);
////            course1.addUser(user3);
////
//            courseRepository.save(course1);
//
//
//
//
////            Message message1 = new Message("How are you");
////            message1.setSender(user1);
////            messageRepository.save(message1);
////
//            StudyGroup group1 = new StudyGroup("Group 1");
//            StudyGroup group2 = new StudyGroup("Group 2");
//
//            studyGroupRepository.save(group1);
//            studyGroupRepository.save(group2);
//
//            group1.addUser(user1);
//            group1.addUser(user2);
//            group2.addUser(user1);
//            group2.addUser(user2);
//
//            studyGroupRepository.save(group1);
//            studyGroupRepository.save(group2);
//
//            Rating rating1 = new Rating(user1,group1,2);
//            Rating rating2 = new Rating(user1,group2, 1.5);
//
//
//            Rating rating3 = new Rating(user2, group1, 2);
//            Rating rating4 = new Rating(user2,group2, 1.5);
//
//            ratingRepository.save(rating1);
//            ratingRepository.save(rating2);
//            ratingRepository.save(rating3);
//            ratingRepository.save(rating4);
//
//            userRepository.save(user1);
//            userRepository.save(user2);
////
////            group1.addRating(rating1);
////            group1.addRating(rating2);
////            group2.addRating(rating1);
////            group2.addRating(rating2);
//
////            user1.addRating(rating1);
////            user1.addRating(rating2);
////
////            user2.addRating(rating1);
////            user2.addRating(rating2);
//
//
////          group1.addUser(user2);
////           group1.addUser(user3);
//            studyGroupRepository.save(group1);
//            studyGroupRepository.save(group2);
//           userRepository.save(user1);
//           userRepository.save(user2);



        };
    }

}
