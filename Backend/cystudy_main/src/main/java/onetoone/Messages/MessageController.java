package onetoone.Messages;

import onetoone.Users.UserRepository;
import onetoone.Groups.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/messages")
    List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

//    @GetMapping(path="/messages/{group_id}")
//    List<Message> getAllMessagesByGroup(@PathVariable int id){
//        StudyGroup group = studyGroupRepository.findById(id);
//        return group.
//    }

    @PostMapping(path = "/messages/post")
    String createMessage(@RequestBody Message message){
        if(message == null)
            return failure;
        messageRepository.save(message);
        return success;
    }

    @GetMapping(path = "/messages/{id}")
    Message getMessageById(@PathVariable int id){
        return messageRepository.findById(id);
    }

    @PutMapping("/messages/{id}")
    Message updateMessage(@PathVariable int messageId, @RequestBody Message updatedMessage) {
        Message message = messageRepository.findById(messageId);
        if(message == null)
            return null;
        messageRepository.save(updatedMessage);
        return messageRepository.findById(messageId);
    }

    @DeleteMapping(path = "/messages/{id}")
    String deleteLaptop(@PathVariable int id){
        messageRepository.deleteById(id);
        return success;
    }




//    @PutMapping("/users/{id}")
//    User updateUser(@PathVariable int id, @RequestBody User request){
//        User user = userRepository.findById(id);
//        if(user == null)
//            return null;
//        userRepository.save(request);
//        return userRepository.findById(id);
//    }
//
//    @PutMapping("/users/{userId}/laptops/{laptopId}")
//    String assignLaptopToUser(@PathVariable int userId,@PathVariable int laptopId){
//        User user = userRepository.findById(userId);
//        Laptop laptop = laptopRepository.findById(laptopId);
//        if(user == null || laptop == null)
//            return failure;
//        laptop.setUser(user);
//        user.setLaptop(laptop);
//        userRepository.save(user);
//        return success;
//    }
//
//    @DeleteMapping(path = "/users/{id}")
//    String deleteLaptop(@PathVariable int id){
//        userRepository.deleteById(id);
//        return success;
//    }

}
