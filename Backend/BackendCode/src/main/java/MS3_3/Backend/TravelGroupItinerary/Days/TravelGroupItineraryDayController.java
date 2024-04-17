package MS3_3.Backend.TravelGroupItinerary.Days;

import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.Groups.TravelGroupRepository;
import MS3_3.Backend.TravelGroupItinerary.TravelGroupItinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TravelGroupItineraryDayController {
    @Autowired
    TravelGroupItineraryDayRepository travelGroupItineraryDayRepository;

    @Autowired
    TravelGroupRepository travelGroupRepository;
    /**
    @GetMapping("/Group/Itinerary/Day/{groupId}/{dayId}")
    public TravelGroupItineraryDay getTravelGroupItineraryByDay(@PathVariable int groupId, @PathVariable int dayId) {
        return travelGroupRepository.findById(groupId).getTravelGroupItinerary()
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
    */
}
