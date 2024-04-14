package MS3_3.Backend.TravelGroupItinerary;

import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.Groups.TravelGroupRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TravelGroupItineraryController {

    @Autowired
    TravelGroupRepository travelGroupRepository;

    @Autowired
    TravelGroupItineraryRepository travelGroupItineraryRepository;

    @PostMapping("/Group/Itinerary")
    public TravelGroupItinerary createTravelGroupItinerary(@RequestBody TravelGroupItinerary travelGroupItinerary) {
        TravelGroup travelGroup = travelGroupRepository.findById(travelGroupItinerary.getGroupCode());
        travelGroup.setTravelGroupItinerary(travelGroupItinerary);
        return travelGroup.getTravelGroupItinerary();
    }

    @GetMapping("/Group/Itinerary/{groupId}")
    public TravelGroupItinerary getTravelGroupItinerary(@PathVariable int groupId) {
        return travelGroupRepository.findById(groupId).getTravelGroupItinerary();
    }

    @PutMapping()
    public TravelGroupItinerary updateTravelGroupItinerary(@PathVariable int groupId,
                                                           @RequestBody TravelGroupItinerary travelGroupItinerary) {
        TravelGroup travelGroup = travelGroupRepository.findById(groupId);
        travelGroup.setTravelGroupItinerary(travelGroupItinerary);
        return travelGroupRepository.findById(groupId).getTravelGroupItinerary();
    }

    @DeleteMapping("/Group/Itinerary/{groupId}")
    public TravelGroupItinerary deleteTravelGroupItinerary(@PathVariable int groupId) {
        TravelGroup travelGroup = travelGroupRepository.findById(groupId);
        travelGroup.setTravelGroupItinerary(null);
        return travelGroupRepository.findById(groupId).getTravelGroupItinerary();
    }

    @GetMapping("/Group/Itinerary")
    public TravelGroupItineraryRepository getTravelGroupItineraryRepository() {
        return travelGroupItineraryRepository;
    }
}
