package MS3_3.Backend.CommunityPost;

import MS3_3.Backend.CommunityPost.Events.CommunityPostEvent;
import MS3_3.Backend.CommunityPost.Events.CommunityPostEventRepository;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommunityPostController {
    @Autowired
    CommunityPostRepository communityPostRepository;

    @Autowired
    CommunityPostEventRepository communityPostEventRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/CommunityPost/{userName}")
    public CommunityPost createCommunityPost(@PathVariable String userName, @RequestBody CommunityPost communityPost) {
        CommunityPost newCommunityPost = new CommunityPost(userRepository.findByUserName(userName), communityPost.getTitle(), communityPost.getNumLikes(), communityPost.getComments(), communityPost.getPostedDate());
        for(CommunityPostEvent event : communityPost.getCommunityPostEventsList()) {
            CommunityPostEvent newEvent = new CommunityPostEvent(newCommunityPost, event.getDayNum(), event.getTime(), event.getPlace(), event.getNotes());
            newCommunityPost.getCommunityPostEventsList().add(newEvent);
        }
        CommunityPost saved = communityPostRepository.save(newCommunityPost);
        return saved;
    }

    @GetMapping("/CommunityPost/{communityPostId}")
    public CommunityPost getCommunityPostItinerary(@PathVariable int communityPostId) {
        return communityPostRepository.findByCommunityPostId(communityPostId);
    }

    @PutMapping("/CommunityPost/{communityPostId}")
    public CommunityPost updateCommunityPostItinerary(@PathVariable int communityPostId, @RequestBody CommunityPost updatedItinerary) {
        CommunityPost existingItinerary = communityPostRepository.findByCommunityPostId(communityPostId);

        existingItinerary.setTitle(updatedItinerary.getTitle());
        existingItinerary.setNumLikes(updatedItinerary.getNumLikes());
        existingItinerary.setComments(updatedItinerary.getComments());
        existingItinerary.setPostedDate(updatedItinerary.getPostedDate());

        existingItinerary.getCommunityPostEventsList().clear();
        for (CommunityPostEvent updatedEvent : updatedItinerary.getCommunityPostEventsList()) {
            CommunityPostEvent newEvent = new CommunityPostEvent(existingItinerary,
                    updatedEvent.getDayNum(), updatedEvent.getTime(),
                    updatedEvent.getPlace(), updatedEvent.getNotes());
            existingItinerary.getCommunityPostEventsList().add(newEvent);
        }

        // Save the updated itinerary
        CommunityPost output = communityPostRepository.save(existingItinerary);
        return output;
    }

    @DeleteMapping("/CommunityPost/{communityPostId}")
    public String deleteCommunityPostItinerary(@PathVariable int communityPostId) {
        User user = communityPostRepository.findByCommunityPostId(communityPostId).getCreator();
        user.getPersonalItineraries().remove(communityPostRepository.findByCommunityPostId(communityPostId));
        userRepository.save(user);
        communityPostRepository.deleteByCommunityPostId(communityPostId);
        return "Deleted personal itinerary " + communityPostId;
    }

    @GetMapping("/CommunityPost")
    public List<CommunityPost> getTravelGroupItineraryRepository() {
        return communityPostRepository.findAll();
    }
}