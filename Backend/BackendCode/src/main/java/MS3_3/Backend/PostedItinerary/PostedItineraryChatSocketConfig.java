package MS3_3.Backend.PostedItinerary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class PostedItineraryChatSocketConfig {
    @Bean
    public ServerEndpointExporter PostedItineraryEndpointExporter() {
        return new ServerEndpointExporter();
    }

}