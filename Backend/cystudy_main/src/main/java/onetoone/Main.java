package onetoone;

import onetoone.Groups.StudyGroup;
import onetoone.Groups.StudyGroupRepository;
import onetoone.Messages.Message;
import onetoone.Rating.Rating;
import onetoone.Rating.RatingRepository;
import onetoone.Timing.Timing;
import onetoone.Timing.TimingRepository;
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


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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
    CommandLineRunner initUser(UserRepository userRepository, CourseRepository courseRepository, MessageRepository messageRepository, StudyGroupRepository studyGroupRepository, RatingRepository ratingRepository, TimingRepository timingRepository) {
        return args -> {

            User user1 = new User("John", "john123", "john@osomemail.com", "lol");
            User user2 = new User("Sam", "sam123", "sam@somemail.com", "lol");
            User user3 = new User("Rahul", "rahul123", "rahul@somemail.com", "lol");
            User user4 = new User("Tanish", "tan123", "tan@somemail.com", "lol");
            User user5 = new User("Gavin", "gav123", "gav@somemail.com", "lol");
            User user6 = new User("Saeshu Karthika", "saeshu123", "saesh@somemail.com", "lol");
            User user7 = new User("Ben", "ben123", "ben@somemail.com", "lol");
            User user8 = new User("Shaun","shaun123","shaun@somemail.com","lol");
            User user9 = new User("Jack","jack123","jack@somemail.com","lol");
            User user10 = new User("Ethan","ethan123","ethan@somemail.com","lol");
            User user11 = new User("monmon","monmon123","mon@somemail.com","lol");
            User user12 = new User("dhoomi","dom123","dom@somemail.com","dappa");


// Save users
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);
            userRepository.save(user8);
            userRepository.save(user9);
            userRepository.save(user10);
            userRepository.save(user11);
            userRepository.save(user12);


// Create course
            Course course1 = new Course("Calculus 1", "MATH", 165);
            Course course2 = new Course("Calculus 2","MATH",166);
            Course course3 = new Course("Software Development Practices","COMS",309);
            Course course4 = new Course("Introduction to Design and Analysis of algorithms","COM S",311);
            Course course5 = new Course("Principles of Microeconomics","ECON",101);
            Course course6 = new Course("Economics of Discrimination","ECON",321);
            Course course7 = new Course("Intro to music listening","MUSIC",102);
            Course course8 = new Course("Introduction to classical physics","PHYS",231);
            Course course9 = new Course("General Chemsitry 1","CHEM",177);
            Course course10 = new Course("Principles of Biology 1", "BIOL",211);

// Add users to the course
            course1.addUser(user1);
            user1.addCourse(course1);
            course1.addUser(user11);
            user11.addCourse(course1);
            course1.addUser(user12);
            user12.addCourse(course1);
            course2.addUser(user1);
            user1.addCourse(course2);
            course3.addUser(user1);
            user1.addCourse(course3);

            userRepository.save(user1);
            userRepository.save(user11);
            userRepository.save(user12);

// Save the course
            courseRepository.save(course1);
            courseRepository.save(course2);
            courseRepository.save(course3);
            courseRepository.save(course4);
            courseRepository.save(course5);
            courseRepository.save(course6);
            courseRepository.save(course7);
            courseRepository.save(course8);
            courseRepository.save(course9);
            courseRepository.save(course10);

// Create study groups

            //Study groups for course1
            StudyGroup group1 = new StudyGroup("MATH 165 GROUP 1",course1,"john123",5);
            group1.addUser(user1);
            group1.addUser(user11);
            group1.addUser(user12);
            StudyGroup group2 = new StudyGroup("MATH 165 GROUP 2",course1,"sam123",5);
            group2.addUser(user2);
            StudyGroup group3 = new StudyGroup("MATH 165 GROUP 3",course1,"rahul123",5);
            group3.addUser(user3);

            //Study groups for course2
            StudyGroup group4 = new StudyGroup("MATH 166 GROUP 1",course2,"gav123",5);
            group4.addUser(user4);
            StudyGroup group5 = new StudyGroup("MATH 166 GROUP 2",course2,"tan123",5);
            group5.addUser(user5);
            StudyGroup group6 = new StudyGroup("MATH 166 GROUP 3",course2,"saeshu123",5);
            group6.addUser(user6);

            //Study groups for course3
            StudyGroup group7 = new StudyGroup("COM S 309 GROUP 1",course3,"ethan123",5);
            group7.addUser(user7);
            StudyGroup group8 = new StudyGroup("COM S 309 GROUP 2",course3,"shaun123",5);
            group8.addUser(user8);
            StudyGroup group9 = new StudyGroup("COM S 309 GROUP 3",course3,"ben123",5);
            group9.addUser(user9);

//            //Study groups for course4
//            StudyGroup group10 = new StudyGroup("COM S 311 GROUP 1",course4,10);
//            StudyGroup group11 = new StudyGroup("COM S 311 GROUP 2",course4,10);
//            StudyGroup group12 = new StudyGroup("COM S 311 GROUP 3",course4,10);
//
//            //Study groups for course5
//            StudyGroup group13 = new StudyGroup("ECON 101 Group1",course5,10);
//            StudyGroup group14 = new StudyGroup("ECON 101 Group2",course5,10);
//            StudyGroup group15 = new StudyGroup("ECON 101 Group3",course5,10);
//
//            //Study groups for course6
//            StudyGroup group16 = new StudyGroup("ECON 321 Group1",course6,10);
//            StudyGroup group17 = new StudyGroup("ECON 321 Group2",course6,10);
//            StudyGroup group18 = new StudyGroup("ECON 321 Group3",course6,10);
//
//            //Study groups for course7
//            StudyGroup group19 = new StudyGroup("MUSIC 102 Group1",course7,10);
//            StudyGroup group20 = new StudyGroup("MUSIC 102 Group2",course7,10);
//            StudyGroup group21 = new StudyGroup("MUSIC 102 Group3",course7,10);
//
//            //Study groups for course8
//            StudyGroup group22 = new StudyGroup("PHYS 231 Group1",course8,10);
//            StudyGroup group23 = new StudyGroup("PHYS 231 Group2",course8,10);
//            StudyGroup group24 = new StudyGroup("PHYS 231 Group3",course8,10);
//
//            //Study groups for course9
//            StudyGroup group25 = new StudyGroup("CHEM 177 Group1",course9,10);
//            StudyGroup group26 = new StudyGroup("CHEM 177 Group2",course9,10);
//            StudyGroup group27 = new StudyGroup("CHEM 177 Group3",course9,10);
//
//            //Study groups for course 10
//            StudyGroup group28 = new StudyGroup("BIOL 211 Group1",course10,10);
//            StudyGroup group29 = new StudyGroup("BIOL 211 Group2",course10,10);
//            StudyGroup group30 = new StudyGroup("BIOL 211 Group3",course10,10);




// Save study groups
            studyGroupRepository.save(group1);
            studyGroupRepository.save(group2);
            studyGroupRepository.save(group3);
            studyGroupRepository.save(group4);
            studyGroupRepository.save(group5);
            studyGroupRepository.save(group6);
            studyGroupRepository.save(group7);
            studyGroupRepository.save(group8);
            studyGroupRepository.save(group9);
//            studyGroupRepository.save(group10);
//            studyGroupRepository.save(group11);
//            studyGroupRepository.save(group12);
//            studyGroupRepository.save(group13);
//            studyGroupRepository.save(group14);
//            studyGroupRepository.save(group15);
//            studyGroupRepository.save(group16);
//            studyGroupRepository.save(group17);
//            studyGroupRepository.save(group18);
//            studyGroupRepository.save(group19);
//            studyGroupRepository.save(group20);
//            studyGroupRepository.save(group21);
//            studyGroupRepository.save(group22);
//            studyGroupRepository.save(group23);
//            studyGroupRepository.save(group24);
//            studyGroupRepository.save(group25);
//            studyGroupRepository.save(group26);
//            studyGroupRepository.save(group27);
//            studyGroupRepository.save(group28);
//            studyGroupRepository.save(group29);
//            studyGroupRepository.save(group30);
//

            LocalDate date = LocalDate.parse("2024-04-30");
            LocalTime time = LocalTime.parse("19:00:00");
            DayOfWeek day = date.getDayOfWeek();
            //Timing
            Timing timing = new Timing(date,time,2, day,"Dungeons");
            timing.setGroup(group1);
            group1.getTimingList().add(timing);
            timingRepository.save(timing);



// Add users to study groups
            group1.addUser(user1);
            group1.addUser(user2);
            group2.addUser(user1);
            group2.addUser(user2);

// Save the study groups after adding users
            studyGroupRepository.save(group1);
            studyGroupRepository.save(group2);


            

//Create ratings
//            Rating rating1 = new Rating(user1, group1, 2);
//            group1.addRating(rating1);
//            double avg = (group1.getAvgRating() + 2)/ group1.getRatingList().size();
//            group1.setAvgRating(avg);
//            studyGroupRepository.save(group1);

//            Rating rating2 = new Rating(user1, group2, 1.5);
//            Rating rating3 = new Rating(user2, group1, 2);
//            Rating rating4 = new Rating(user2, group2, 1.5);
//
//// Save ratings
//            ratingRepository.save(rating1);
//            ratingRepository.save(rating2);
//            ratingRepository.save(rating3);
//            ratingRepository.save(rating4);
//
//// Save users after adding ratings
//            userRepository.save(user1);
//            userRepository.save(user2);



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
