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

    @GetMapping(path = "/group/users/{id}")
    List<User> getAllUsersInGroup (@PathVariable int id) {
        StudyGroup studyGroup = studyGroupRepository.findById(id);
        if (studyGroup == null) {
            return null;
        }
        return studyGroup.getUsers();
    }


    @GetMapping(path="/group/all")
    List<StudyGroup> getAllGroups(){return studyGroupRepository.findAll();}

    @GetMapping(path="/group/{group_id}")
    StudyGroup getAllGroupsById(@PathVariable int group_id){return studyGroupRepository.findById(group_id);}

//    @PostMapping(path="/group/post/{group_name}")
//    String createGroup(@PathVariable String group_name){
//        if(group_name == null)
//            return failure;
//        for(StudyGroup studyGroup: studyGroupRepository.findAll()){
//            if(studyGroup.getGroupName() == group_name){
//                return "Group name already exists";
//            }
//        }
//        StudyGroup newStudyGroup = new StudyGroup(group_name);
//        studyGroupRepository.save(newStudyGroup);
//        return success;
//    }


//    @PostMapping(path = "/groups")
//    String createGroup( @RequestBody StudyGroup studyGroup){
//        if(studyGroup == null){
//            return failure;
//        }
//        studyGroupRepository.save(studyGroup);
//        return success;
//    }

    @PostMapping(path = "/group/post")
    String createGroup( @RequestBody StudyGroup studyGroup){
        if(studyGroup == null){
            return failure;
        }
        for(StudyGroup group: studyGroupRepository.findAll()) {
            if (group.getGroupName() == studyGroup.getGroupName()) {
                return "Group name already exists";
            }
        }
        studyGroupRepository.save(studyGroup);
        return success;
//            return failure;
//        for(StudyGroup studyGroup: studyGroupRepository.findAll()){
//            if(studyGroup.getGroupName() == group_name){
//                return "Group name already exists";
//            }
//        }
//        StudyGroup newStudyGroup = new StudyGroup(group_name);
//        studyGroupRepository.save(newStudyGroup);
//        return success;
    }



    @PutMapping(path="/group/update/{group_id}")
    StudyGroup updateGroup(@PathVariable int group_Id, @RequestBody String updatedName) {
        StudyGroup studyGroup = studyGroupRepository.findById(group_Id);
        if(studyGroup == null)
            return null;
        studyGroup.setGroupName(updatedName);
        return studyGroupRepository.findById(group_Id);
    }

    @PutMapping(path="/group/update/addUser/{group_id}")
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
