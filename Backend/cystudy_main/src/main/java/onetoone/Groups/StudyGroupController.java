package onetoone.Groups;

import jakarta.transaction.Transactional;
import onetoone.Courses.Course;
import onetoone.Courses.CourseRepository;
import onetoone.Rating.Rating;
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

    @GetMapping(path = "/groups/all/users/{group_name}")
    Set<User> getAllUsersInGroup (@PathVariable String group_name) {
        StudyGroup studyGroup = studyGroupRepository.findStudyGroupByGroupName(group_name);
        if (studyGroup == null) {
            return null;
        }
        return studyGroup.getUserSet();
    }

    @GetMapping(path="/groups/{group_id}")
    StudyGroup getAllGroupsById(@PathVariable int group_id){return studyGroupRepository.findById(group_id);}

    @GetMapping(path = "/groups/all/{username}")
    Set<StudyGroup> getUserGroups(@PathVariable String username){
        User user = userRepository.findByUserName(username);
        return user.getGroupSet();
    }

    @GetMapping(path = "/course/groups/{course_id}")
    Set<StudyGroup> getCourseGroups(@PathVariable int course_id){
        Course course = courseRepository.findById(course_id);
        return course.getGroupSet();
    }

    @GetMapping(path ="/groupMasterCheck/{groupname}/{username}")
    boolean groupMasterCheck(@PathVariable String groupname, @PathVariable String username){
        User user = userRepository.findByUserName(username);
        StudyGroup studyGroup = studyGroupRepository.findStudyGroupByGroupName(groupname);
        if(studyGroup.getUserSet().contains(user)){
            return username.equals(studyGroup.getGroupMaster());
        }
        else {
            return false;
        }
    }

    @PutMapping(path = "/groups/update/{groupname}/{updatedGroupName}")
    StudyGroup updateGroup(@PathVariable String groupname, @PathVariable String updatedGroupName){
        StudyGroup studyGroup = studyGroupRepository.findStudyGroupByGroupName(groupname);
        if(studyGroup == null){
            return null;
        }
        studyGroup.setGroupName(updatedGroupName);
        studyGroupRepository.save(studyGroup);
        return studyGroup;
    }


    @PostMapping(path = "/groups/post/{group_name}/{username}/{courseid}/{groupCapacity}")
    String createGroup(@PathVariable String group_name, @PathVariable String username,@PathVariable int courseid,@PathVariable int groupCapacity) {
        Course course = courseRepository.findById(courseid);
        User user = userRepository.findByUserName(username);
        if (group_name == null) {
            return failure;
        }
        for(Course course1 : user.getCourseSet()){
            if(course.getid()==course1.getid()){
                return "Cannot join multiple groups of same course";
            }
        }
        for (StudyGroup group : studyGroupRepository.findAll()) {
            if (group.getGroupName().equals(group_name)){
                return "Group name already exists";
            }
        }
        StudyGroup studyGroup = new StudyGroup(group_name,course,username,groupCapacity);
        studyGroupRepository.save(studyGroup);
        studyGroup.addUser(user);
        user.addStudyGroup(studyGroup);
        studyGroup.setGroupMaster(user.getUserName());
        studyGroupRepository.save(studyGroup);
        userRepository.save(user);
        return "Group created successfully!";
    }

//    @PutMapping(path="/groups/update/{group_id}")
//    StudyGroup updateGroup(@PathVariable int group_id, @RequestBody StudyGroup updatedGroup) {
//        StudyGroup studyGroup = studyGroupRepository.findById(group_id);
//        if(studyGroup == null)
//            return null;
//        studyGroup.setGroupName(updatedGroup.getGroupName());
//        return studyGroupRepository.findById(group_id);
//    }

    @PutMapping(path="/groups/update/addUser/{groupname}/{username}")
    String addUserToGroup(@PathVariable String groupname,@PathVariable String username){
        StudyGroup studyGroup = studyGroupRepository.findStudyGroupByGroupName(groupname);
        User user = userRepository.findByUserName(username);
        Course groupCourse = studyGroup.getCourse();
        if(studyGroup == null || user == null)
            return null;
        for(Course course : user.getCourseSet()) {
            if (course.getid() == groupCourse.getid()) {
                return "Can't join multiple groups of same course";
            }
        }
        studyGroup.addUser(user);
        user.addStudyGroup(studyGroup);
        studyGroupRepository.save(studyGroup);
        userRepository.save(user);
        return "successful!";
    }

    @DeleteMapping(path ="/groups/delete/{groupname}/{username}")
    String deleteUserFromGroup(@PathVariable String username, @PathVariable String groupname){
        User user = userRepository.findByUserName(username);
        StudyGroup studyGroup = studyGroupRepository.findStudyGroupByGroupName(groupname);
        if (user == null || studyGroup == null)
            return failure;
        studyGroup.removeUser(user);
        studyGroupRepository.save(studyGroup);
        return success;
    }


    @Transactional
    @DeleteMapping(path = "/groups/delete/{groupName}")
    String deleteGroup(@PathVariable String groupName){
        StudyGroup studyGroup = studyGroupRepository.findStudyGroupByGroupName(groupName);
        if(studyGroup == null)
            return failure;
//        studyGroupRepository.deleteStudyGroupByGroupName(groupName);
        try {
            studyGroupRepository.deleteById(studyGroup.getid());
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return failure;
        }
        return success;
    }





//@DeleteMapping(path = "/group/delete/{group_name}")
//String deleteGroup(@PathVariable String group_name){
//    StudyGroup studyGroup = studyGroupRepository.findStudyGroupByGroupName(group_name);
//    if(studyGroup == null)
//        return failure;
//    studyGroupRepository.deleteById(studyGroup.getid());
//    return success;
//}







}
