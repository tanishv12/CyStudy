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

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

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

    @GetMapping(path = "/rating/average/{groupname}")
    double averageRatingByGroup(String groupname){
        StudyGroup studyGroup = studyGroupRepository.findStudyGroupByGroupName(groupname);
        if(studyGroup == null){
            return 0;
        }
       return studyGroup.getAvgRating();
    }

    @PutMapping(path = "/update/rating/{new_rating}")
    String updateRating(@RequestBody Rating rating, @PathVariable double new_rating){
        if(rating == null) {
            return failure;
        }
            rating.setRating(new_rating);
        return success;
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
    @PostMapping(path = "/rating/post/{groupname}/{username}/{rating}")
    String rateGroup(@PathVariable String groupname, @PathVariable String username, @PathVariable double rating) {

        StudyGroup studyGroup = studyGroupRepository.findStudyGroupByGroupName(groupname);
        User user = userRepository.findByUserName(username);
        if(studyGroup.getUserSet().contains(user)) {
            Rating rating1 = new Rating(user, studyGroup, rating);
            studyGroup.addRating(rating1);
            user.addRating(rating1);
            ratingRepository.save(rating1);
            userRepository.save(user);
            studyGroupRepository.save(studyGroup);
            double sum = 0;
            for(Rating rating2: studyGroup.getRatingList()){
                sum = sum + rating2.getRating();
            }
            double avg = sum/studyGroup.getRatingList().size();
            studyGroup.setAvgRating(avg);
            studyGroupRepository.save(studyGroup);
            return success;
        }
        else{
            return failure;
        }
    }
}