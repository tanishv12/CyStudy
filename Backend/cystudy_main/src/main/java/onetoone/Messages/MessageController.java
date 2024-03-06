package onetoone.Messages;

import onetoone.Groups.StudyGroup;
import onetoone.Users.UserRepository;
import onetoone.Groups.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path="/messages/all/group/{group_id}")
    Set<Message> getAllMessagesByGroup(@PathVariable int group_id){
        StudyGroup group = studyGroupRepository.findById(group_id);
        return group.getMessageSet();
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

    @PostMapping(path = "/messages/post")
    String createMessage(@RequestBody Message message){
        if(message == null)
            return failure;
        messageRepository.save(message);
        return success;
    }



    @PutMapping("/messages/update/{message_id}")
    Message updateMessage(@PathVariable int message_id, @RequestBody Message updatedMessage) {
        Message message = messageRepository.findById(message_id);
        if (message == null)
            return null;
        messageRepository.save(updatedMessage);
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
