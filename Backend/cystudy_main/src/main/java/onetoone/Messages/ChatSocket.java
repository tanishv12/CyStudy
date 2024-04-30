package onetoone.Messages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import onetoone.Groups.StudyGroup;
import onetoone.Groups.StudyGroupRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/chat/{username}/{groupname}/end")  // this is Websocket url
public class ChatSocket {


	// cannot autowire static directly (instead we do it by the below
	// method
	private static MessageRepository msgRepo;
	private static UserRepository userRepo;
	private static StudyGroupRepository studyGroupRepository;

	private StudyGroup group;

	private User user;


	/*
	 * Grabs the MessageRepository singleton from the Spring Application
	 * Context.  This works because of the @Controller annotation on this
	 * class and because the variable is declared as static.
	 * There are other ways to set this. However, this approach is
	 * easiest.
	 */
	@Autowired
	public void setMessageRepository(MessageRepository repo) {
		msgRepo = repo;  // we are setting the static variable
	}
	 @Autowired
	 public void setUserRepository(UserRepository repo){userRepo = repo;}

	@Autowired
	public void setStudyGroupRepository(StudyGroupRepository studyGroupRepository) {
		ChatSocket.studyGroupRepository = studyGroupRepository;
	}

	// Store all socket session and their corresponding username.
	private static Map<Session, String> sessionGroupnameMap = new Hashtable<>();
	private static Map<String, List<Session>> groupnameSessionMap = new Hashtable<>();


	private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username, @PathParam("groupname") String groupName)
			throws IOException {

		group = studyGroupRepository.findStudyGroupByGroupName(groupName);
		System.out.println(group.getGroupName());

		user = userRepo.findByUserName(username);
		System.out.println(user);

		for (User u : group.getUserSet()) {
			if (u.getid() == user.getid()) {
				logger.info("Entered into Open");
				if (groupnameSessionMap.containsKey(groupName)) {
//					session.getBasicRemote().sendText("Group already exists");
					groupnameSessionMap.get(groupName).add(session);
					sessionGroupnameMap.put(session, group.getGroupName());

//					String message = "Student:" + username + " has Joined the " + groupName;
//					broadcast(message, groupName);
				} else {
					// store connecting user information
					List<Session> sessions = new ArrayList<>();
					sessions.add(session);
					sessionGroupnameMap.put(session, group.getGroupName());
					groupnameSessionMap.put(group.getGroupName(), sessions);
					System.out.println("User session map " + user);
					//Send chat history to the newly connected user
					//sendMessageToPArticularUser(username, "Welcome to "+groupName+" " + username);

					// broadcast that new user joined
					String message = "Student:" + username + " has Joined the " + groupName;
					broadcast(message, groupName);

				}
			}
		}
	}



	@OnMessage
	public void onMessage(Session session, String message, @PathParam("username") String username, @PathParam("groupname") String groupName) throws IOException {

		// Handle new messages
		logger.info("Entered into Message: Got Message:" + message);

//
//		}
		 // broadcast
			broadcast(username+ ": " + message, groupName);

		// Saving chat history to repository
		Message userMessage = new Message(message,user,group);
		msgRepo.save(userMessage);
		user.addMessage(userMessage);
		group.addMessage(userMessage);




		System.out.println(user.getName());
	}


	@OnClose
	public void onClose(Session session,@PathParam("username") String username) throws IOException {
		logger.info("Entered into Close");

		// remove the user connection information
		String groupName = sessionGroupnameMap.get(session);
		sessionGroupnameMap.remove(session);
		groupnameSessionMap.remove(groupName);

		// broadcase that the user disconnected
		String message = username + " exited group1";
		broadcast(message, groupName);
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}

	


	private void broadcast(String message, String groupName) {
		List<Session> sessions = (List<Session>) groupnameSessionMap.get(groupName);
		if (sessions != null) {
			for (Session session : sessions) {
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					logger.info("Exception: " + e.getMessage());
					e.printStackTrace();
				}
			}
		} else {
			logger.info("No sessions found for group: " + groupName);
		}
	}


	// Gets the Chat history from the repository
	private String getChatHistory() {
		List<Message> messages = msgRepo.findAll();

		// convert the list to a string
		StringBuilder sb = new StringBuilder();
		if(messages != null && messages.size() != 0) {
			for (Message message : messages) {
				sb.append(message.getSender() + ": " + message.getMessageContent() + "\n");
			}
		}
		return sb.toString();
	}

} // end of Class


