package onetoone.Rating;

import onetoone.Groups.StudyGroup;
import onetoone.Groups.StudyGroupRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RatingController {
    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RatingRepository ratingRepository;

    @GetMapping(path = "/rating/{group_id}")
    ResponseEntity<List<Rating>> ratingOfTheGroup(@PathVariable int group_id) {
        Optional<StudyGroup> optionalStudyGroup = Optional.ofNullable(studyGroupRepository.findById(group_id));
        if (optionalStudyGroup.isPresent()) {
            StudyGroup studyGroup = optionalStudyGroup.get();
            List<Rating> ratings = studyGroup.getRatingList();
            return ResponseEntity.ok(ratings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/rating/{group_id}/{user_id}")
    ResponseEntity<?> ratingOfUserInGroup(@PathVariable int group_id, @PathVariable int user_id) {
        Optional<StudyGroup> optionalStudyGroup = Optional.ofNullable(studyGroupRepository.findById(group_id));
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(user_id));
        if (optionalStudyGroup.isPresent()) {
            StudyGroup studyGroup = optionalStudyGroup.get();
            List<Rating> ratings = studyGroup.getRatingList();
            for (Rating rating : ratings) {
                if (rating.getUser().equals(optionalUser.get())) {
                    return ResponseEntity.ok(rating);
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not in group");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //    @PostMapping(path = "/rating/post/{group_id}/{user_id}/{rating}")
//    ResponseEntity<?> rateGroup(@PathVariable int group_id,@PathVariable int user_id, @PathVariable int rating) {
//        Optional<StudyGroup> optionalStudyGroup = Optional.ofNullable(studyGroupRepository.findById(group_id));
//        Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(user_id));
//        if (optionalStudyGroup.isPresent()) {
//            StudyGroup studyGroup = optionalStudyGroup.get();
//            List<Rating> ratings = studyGroup.getRatingList();
//            for (Rating eachRating : ratings) {
//                if (eachRating.getUser().equals(optionalUser.get())){
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You rated already");
//                }
//            }
//            Rating newRating = new Rating();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not in group");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
    @PostMapping(path = "/rating/post/{group_id}/{user_id}/{rating}")
    Rating rateGroup(@PathVariable int group_id, @PathVariable int user_id, @PathVariable int rating) {

        StudyGroup studyGroup = studyGroupRepository.findById(group_id);
        User user = userRepository.findById(user_id);
        Rating rating1 = new Rating(user,studyGroup,rating);
        studyGroup.getRatingList().add(rating1);
        user.getRatingSet().add(rating1);
        ratingRepository.save(rating1);
        userRepository.save(user);
        studyGroupRepository.save(studyGroup);
        return rating1;
    }
}
