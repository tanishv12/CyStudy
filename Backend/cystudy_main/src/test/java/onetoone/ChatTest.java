package onetoone;

import onetoone.Messages.ChatSocket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ChatSocket chatSocket; // Assuming you have configured your WebSocket endpoint correctly in Spring

    @Test
    public void testChatSocket() throws Exception {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new StringMessageConverter());

        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

        StompSessionHandler sessionHandler = new TestSessionHandler();

        StompSession stompSession = stompClient.connect("ws://localhost:" + port + "/chat/username/groupname/end", headers, sessionHandler).get(1, TimeUnit.SECONDS);

        stompSession.send("/app/chat/username/groupname/end", "Hello, WebSocket server!");

//        assertEquals("Hello, WebSocket server!");

        stompSession.disconnect();
    }

    static class TestSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println("Received message: " + payload.toString());
        }
    }
}
