package MS3_3.Backend.PostedItinerary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static MS3_3.Backend.PostedItinerary.PostedItineraryChatSocket.userBookmarksMap;

@RestController
@RequestMapping
public class BookmarkController {

    @Autowired
    private PostedItineraryRepository postRepo;

    @GetMapping("/bookmarks/{username}")
    public List<PostedItinerary> getUserBookmarkedItineraries(@PathVariable String username) {
        // Query the database for the user's bookmarked itineraries
        Set<String> bookmarkedPostIds = userBookmarksMap.getOrDefault(username, Collections.emptySet());
        List<PostedItinerary> bookmarkedItineraries = new ArrayList<>();
        for (String postId : bookmarkedPostIds) {
            PostedItinerary itinerary = postRepo.findByPostID(postId);
            if (itinerary != null) {
                bookmarkedItineraries.add(itinerary);
            }
        }
        return bookmarkedItineraries;
    }
}
