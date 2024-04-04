package MS3_3.Backend.PostedItinerary;

import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value = "/PostedItineraries/{username}")
public class PostedItineraryChatSocket {
    private static PostedItineraryRepository postRepo;

    @Autowired
    public void setPostedItineraryRepository(PostedItineraryRepository repo) {
        postRepo = repo;
    }
}
