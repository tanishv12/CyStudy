package onetoone.Resources;

import onetoone.Courses.Course;
import onetoone.Courses.CourseRepository;
import onetoone.Groups.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class StudyResourceController {

    @Autowired
    StudyResourceRepository studyResourceRepo;

    @Autowired
    CourseRepository courseRepo;

    @GetMapping(path = "/studyResource/course/{resource_id}")
    Course getCourse(@PathVariable int resource_id){
     StudyResources studyResource = studyResourceRepo.findById(resource_id);
     return courseRepo.findCourseBystudyResourceList(studyResource);
    }

    @PostMapping(path ="/studyResource/post/{course_id}")
    ResponseEntity<String> postResource(@RequestBody StudyResources updatedResource,@PathVariable int course_id){
        updatedResource.setCourse(courseRepo.findById(course_id));
        studyResourceRepo.save(updatedResource);
        return ResponseEntity.ok("Study Resource created successfully");
    }
}
