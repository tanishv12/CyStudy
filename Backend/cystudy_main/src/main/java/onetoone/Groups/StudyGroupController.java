package onetoone.Groups;

import onetoone.Courses.CourseRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class StudyGroupController {

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path="/groups/all")
    List<StudyGroup> getAllGroups(){return studyGroupRepository.findAll();}

    @GetMapping(path = "/groups/all/users/{group_id}")
    List<User> getAllUsersInGroup (@PathVariable int group_id) {
        StudyGroup studyGroup = studyGroupRepository.findById(group_id);
        if (studyGroup == null) {
            return null;
        }
        return studyGroup.getUsers();
    }

    @GetMapping(path="/groups/{group_id}")
    StudyGroup getAllGroupsById(@PathVariable int group_id){return studyGroupRepository.findById(group_id);}


    @PostMapping(path = "/groups/post/{group_name}")
    String createGroup(@PathVariable String group_name) {
        if (group_name == null) {
            return failure;
        }
        for (StudyGroup group : studyGroupRepository.findAll()) {
            if (group.getGroupName().equals(group_name)){
                return "Group name already exists";
            }
            else{
                StudyGroup studyGroup = new StudyGroup(group_name);
                studyGroupRepository.save(studyGroup);
                return "Group created successfully!";
            }
        }
        return success;
    }

    @PutMapping(path="/groups/update/{group_id}")
    StudyGroup updateGroup(@PathVariable int group_id, @RequestBody StudyGroup updatedGroup) {
        StudyGroup studyGroup = studyGroupRepository.findById(group_id);
        if(studyGroup == null)
            return null;
        studyGroup.setGroupName(updatedGroup.getGroupName());
        return studyGroupRepository.findById(group_id);
    }

    @PutMapping(path="/groups/update/addUser/{group_id}")
    StudyGroup addUserToGroup(@PathVariable int group_id, @RequestBody User user){
        StudyGroup studyGroup = studyGroupRepository.findById(group_id);
        if(studyGroup == null)
            return null;
        studyGroup.addUser(user);
        return studyGroupRepository.findById(group_id);
    }


    @DeleteMapping(path = "/group/delete/{group_id}")
    String deleteGroup(@PathVariable int group_id){
        StudyGroup studyGroup = studyGroupRepository.findById(group_id);
        if(studyGroup == null)
            return failure;
        studyGroupRepository.deleteById(group_id);
        return success;
    }





}
