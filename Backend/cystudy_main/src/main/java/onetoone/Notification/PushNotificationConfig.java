package onetoone.Notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class PushNotificationConfig {

    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}

