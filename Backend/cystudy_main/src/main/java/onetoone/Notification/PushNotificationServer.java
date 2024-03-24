package onetoone.Notification;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import onetoone.Groups.*;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/user/{username}")  // this is Websocket url
public class PushNotificationServer {

    private static StudyGroupRepository groupRepo;
    @Autowired
    public void setStudyGroupRepository(StudyGroupRepository repo){groupRepo = repo;}

    private static UserRepository userRepo;
    @Autowired
    public void setUserRepository(UserRepository repo){userRepo = repo;}


    private static Map<Session, User> sessionUsernameMap = new Hashtable<>();
    private static Map<User, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(PushNotificationServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        logger.info("Entered into Open");

        // store connecting user information
        sessionUsernameMap.put(session, userRepo.findByUsername(username));
        usernameSessionMap.put(userRepo.findByUsername(username), session);

        // broadcast that new user joined
        String message = "User:" + username + " has Joined the Group";
        broadcast(message);
    }

//    @OnMessage
//    public void onMessage(Session session, String message) throws IOException {
//
//        // Handle new messages
//        logger.info("Entered into Message: Got Message:" + message);
//        String username = sessionUsernameMap.get(session);
//
//        // Direct message to a user using the format "@username <message>"
//        if (message.startsWith("@")) {
//            String destUsername = message.split(" ")[0].substring(1);
//
//            // send the message to the sender and receiver
//            sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
//            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
//            msgRepo.save(new Message(username, message));
//        }
//        else { // broadcast
//            broadcast(username + ": " + message);
//            commonMsgRepo.save(new Message(username, message));
//        }
//
//        // Saving chat history to repository
////		msgRepo.save(new Message(username, message));
//    }


    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        // remove the user connection information
        User user = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(user);

        // broadcase that the user disconnected
        String message = user.getName() + " disconnected";
        broadcast(message);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }


    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }


    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }


    // Gets the Chat history from the repository
//    private String getChatHistory() {
//        List<Message> messages = msgRepo.findAll();
//
//        // convert the list to a string
//        StringBuilder sb = new StringBuilder();
//        if(messages != null && messages.size() != 0) {
//            for (Message message : messages) {
//                sb.append(message.getUserName() + ": " + message.getContent() + "\n");
//            }
//        }
//        return sb.toString();
//    }

	/*
		private String getChatHistory(String username) {
		Long id = User.getId(username);
		List<Message> messages = msgRepo.findAllById();

    // convert the list to a string
		StringBuilder sb = new StringBuilder();
		if(messages != null && messages.size() != 0) {
			for (Message message : messages) {
				sb.append(message.getUserName() + ": " + message.getContent() + "\n");
			}
		}
		return sb.toString();
	}
	 */

//    private String getCommonChatHistory(){
//        List<Message> messages = commonMsgRepo.findAll();
//
//        // convert the list to a string
//        StringBuilder sb = new StringBuilder();
//        if(messages != null && messages.size() != 0) {
//            for (Message message : messages) {
//                sb.append(message.getUserName() + ": " + message.getContent() + "\n");
//            }
//        }
//        return sb.toString();
//    }


} // end of Class


