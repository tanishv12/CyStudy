package onetoone.Timing;

import onetoone.Groups.StudyGroup;
import onetoone.Groups.StudyGroupRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TimingController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudyGroupRepository groupRepository;

    @Autowired
    TimingRepository timingRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    @GetMapping(path="/timings/group/{groupName}/end")
    List<Timing> getTimingByGroup(@PathVariable String groupName){
        StudyGroup studyGroup = groupRepository.findStudyGroupByGroupName(groupName);
        if(groupName == null){
            return null;
        }
        return studyGroup.getTimingList();
    }

    @GetMapping(path="/timings/user/{userName}")
    List<Timing> getTimingByUser(@PathVariable String userName){
        User user = userRepository.findByUserName(userName);
        if(user == null){
            return null;
        }
        List<Timing> timings = new ArrayList<>();
        for(StudyGroup group: user.getGroupSet()) {
            for (Timing timing : group.getTimingList()) {
                timings.add(timing);
            }
        }
        return timings;

    }

//    @PostMapping(path="/timings/post")
//    String add
    @PostMapping(path="/timings/post/{groupName}")
    ResponseEntity<String> addTimingToGroup(@RequestBody Timing timing, @PathVariable String groupName){
        StudyGroup group = groupRepository.findStudyGroupByGroupName(groupName);
        if(group == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Group does not exist");
        }
        group.getTimingList().add(timing);
        timing.setGroup(group);
        timingRepository.save(timing);
        groupRepository.save(group);
        return ResponseEntity.ok("Timing added to group successfully");
    }

    @PutMapping("/timings/location/{groupName}/{timingId}") // Adjust the endpoint path
    public ResponseEntity<String> addLocationToTiming(@RequestBody String location,
                                                      @PathVariable String groupName,
                                                      @PathVariable Long timingId) {
        StudyGroup group = groupRepository.findStudyGroupByGroupName(groupName);
        if (group == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Group does not exist");
        }

        Timing timing = timingRepository.findByIdAndStudyGroup(timingId, group);
        if (timing == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Timing does not exist for this group");
        }

        timing.setLocation(location);
        timingRepository.save(timing);

        return ResponseEntity.ok("Location added to timing successfully");
    }



}
