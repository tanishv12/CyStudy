package onetoone.Messages;

import onetoone.Groups.StudyGroup;
import onetoone.Users.UserRepository;
import onetoone.Users.User;
import onetoone.Groups.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @GetMapping(path = "/messages/all")
    List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    @GetMapping(path="/messages/all/group/{groupname}")
    List<Message> getAllMessagesByGroup(@PathVariable String groupname){
        StudyGroup group = studyGroupRepository.findStudyGroupByGroupName(groupname);
        return messageRepository.findAllByStudyGroupOrderByTimestampAsc(group);
//        return group.getMessageSet();
    }

    @GetMapping(path ="/messages/all/user/{username}")
    Set<Message> getAllMessagesByUser(@PathVariable String username){
        User user = userRepository.findByUserName(username);
        return user.getMessageSet();
    }


    @GetMapping(path = "/messages/{message_id}")
    ResponseEntity<?> getMessageById(@PathVariable int message_id) {
        Optional<Message> optionalMessage = Optional.ofNullable(messageRepository.findById(message_id));

        if (optionalMessage.isPresent()) {
            // Message found, return it
            return ResponseEntity.ok(optionalMessage.get());
        } else {
            // Message not found, return an appropriate response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found for ID: " + message_id);
        }
    }

    @PostMapping(path = "/messages/post/{groupname}/{username}")
    String createMessage(@RequestBody Message message, @PathVariable String username, @PathVariable String groupname){
        if(message == null)
            return failure;
        User user = userRepository.findByUserName(username);
        StudyGroup studyGroup = studyGroupRepository.findStudyGroupByGroupName(groupname);
        user.addMessage(message);
        studyGroup.addMessage(message);
        userRepository.save(user);
        studyGroupRepository.save(studyGroup);
        messageRepository.save(message);
        return success;
    }

    @PutMapping("/messages/update/{message_id}")
    Message updateMessage(@PathVariable int message_id, @RequestBody Message updatedMessage) {
        Message message = messageRepository.findById(message_id);
        if (message == null)
            return null;
        message.setMessageContent(updatedMessage.getMessageContent());
//        messageRepository.save(message);
        return messageRepository.findById(message_id);
    }
//    @PutMapping("/messages/{id}")
//    Message updateMessage(@PathVariable int messageId, @RequestBody Message updatedMessage) {
//        Message existingMessage = messageRepository.findById(messageId);
//        if (existingMessage == null) {
//            return null; // or throw an exception
//        }
//
//        // Update the existing message with the data from updatedMessage
//        existingMessage.setMessageContent(updatedMessage.getMessageContent());
//        // Update other fields as needed
//
//        // Save the updated message
//        messageRepository.save(existingMessage);
//
//        // Return the updated message
//        return existingMessage;
//    }

    @DeleteMapping(path = "/messages/delete/{message_id}")
    String deleteMessage(@PathVariable int message_id){
        if(messageRepository.findById(message_id) == null){
            return failure;
        }
        else {
            messageRepository.deleteById(message_id);
            return success;
        }
    }




}
