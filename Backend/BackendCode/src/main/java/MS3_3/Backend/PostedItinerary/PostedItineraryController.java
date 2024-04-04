package MS3_3.Backend.PostedItinerary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostedItineraryController {

    @Autowired
    PostedItineraryRepository  postedItineraryRepository;

    @PostMapping("/PostItinerary")
    public PostedItinerary postItinerary(@RequestBody PostedItinerary postedItinerary) {
        postedItineraryRepository.save(postedItinerary);
        return postedItinerary;
    }

    @DeleteMapping("/PostedItinerary/{postID}")
    public String deletePostedItinerary(@PathVariable String postID) {
        postedItineraryRepository.deleteByPostID(postID);
        return "Posted Itinerary with code" + postID + "successfully deleted";
    }

    @PutMapping("/PostedItinerary/{postID}")
    public PostedItinerary updatePostedItinerary(@PathVariable String postID, @RequestBody PostedItinerary updatedPostedItinerary) {
        PostedItinerary existingPostedItinerary = postedItineraryRepository.findByPostID(postID);
        if(existingPostedItinerary != null) {
            existingPostedItinerary.setCreator(updatedPostedItinerary.getCreator());
            existingPostedItinerary.setTimePosted(updatedPostedItinerary.getTimePosted());
            existingPostedItinerary.setPostFile(updatedPostedItinerary.getPostFile());
            existingPostedItinerary.setCaption(updatedPostedItinerary.getCaption());
            existingPostedItinerary.setLikeCount(updatedPostedItinerary.getLikeCount());
            existingPostedItinerary.setLikeValue(updatedPostedItinerary.getLikeValue());
            existingPostedItinerary.setSaveCount(updatedPostedItinerary.getSaveCount());
            existingPostedItinerary.setSaveValue(updatedPostedItinerary.getSaveValue());
            existingPostedItinerary.setTripCode(updatedPostedItinerary.getTripCode());
            existingPostedItinerary.setNumDays(updatedPostedItinerary.getNumDays());
            existingPostedItinerary.setPostID(updatedPostedItinerary.getPostID());
            existingPostedItinerary.setComments(updatedPostedItinerary.getComments());
            existingPostedItinerary.setDays(updatedPostedItinerary.getDays());
            postedItineraryRepository.save(existingPostedItinerary);
            return existingPostedItinerary;
        } else {
            return null;
        }
    }

    @GetMapping("/PostedItinerary/{postID}")
    public PostedItinerary getPostedItinerary(@PathVariable String postID) {
        return postedItineraryRepository.findByPostID(postID);
    }

    @GetMapping("/PostedItinerary/List")
    public List<PostedItinerary> getAllPostedItineraries() {
        return postedItineraryRepository.findAll();
    }
}
