package onetoone.Messages;

import java.io.IOException;
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
@ServerEndpoint(value = "/chat/{username}/{groupname}")  // this is Websocket url
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
	private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
	private static Map<String, Session> usernameSessionMap = new Hashtable<>();

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
				if (usernameSessionMap.containsKey(username)) {
					session.getBasicRemote().sendText("Student already exists in group");
					session.close();
				} else {
					// store connecting user information
					sessionUsernameMap.put(session, user.getUserName());
					usernameSessionMap.put(user.getUserName(), session);
					System.out.println("User session map " + user);
					//Send chat history to the newly connected user
					sendMessageToPArticularUser(username, "Welcome to "+groupName+" " + username);

					// broadcast that new user joined
					String message = "Student:" + username + " has Joined the "+groupName;
					broadcast(message);

				}
			}
		}
	}


	@OnMessage
	public void onMessage(Session session, String message,@PathParam("groupname") String groupName) throws IOException {

		// Handle new messages
		logger.info("Entered into Message: Got Message:" + message);
		String userName = sessionUsernameMap.get(session);
		// Direct message to a user using the format "@username <message>"
		 if (message.startsWith("@")) {
			String destUsername = message.split(" ")[0].substring(1);

			// send the message to the sender and receiver
			sendMessageToPArticularUser(destUsername, "[DM] " + userName + ": " + message);
			sendMessageToPArticularUser(userName, "[DM] " + userName + ": " + message);

		}
		else { // broadcast
			broadcast(userName+ ": " + message);
		}

		//User user = userRepo.findByUserName(userName);
//		StudyGroup group1 = studyGroupRepository.findStudyGroupByGroupName(groupName);
		// Saving chat history to repository
		Message userMessage = new Message(message,user,group);
		user.addMessage(userMessage);
		group.addMessage(userMessage);
//		userRepo.save(user);
//		studyGroupRepository.save(group);
		msgRepo.save(userMessage);


		System.out.println(user.getName());
	}


	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

		// remove the user connection information
		String userName = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(userName);

		// broadcase that the user disconnected
		String message = userName + " exited group1";
		broadcast(message);
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}


	private void sendMessageToPArticularUser(String username, String message) {
		User user = userRepo.findByUserName(username);
		System.out.println("Send message to particular user"+user);
		System.out.println(usernameSessionMap.containsKey(username));
		if (user != null && usernameSessionMap.containsKey(username)) {
			try {
				Session session = usernameSessionMap.get(username);
				session.getBasicRemote().sendText(message);

			}
			catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}
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

