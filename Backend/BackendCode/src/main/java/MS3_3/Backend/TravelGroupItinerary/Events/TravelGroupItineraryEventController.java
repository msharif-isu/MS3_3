package MS3_3.Backend.TravelGroupItinerary.Events;

import MS3_3.Backend.Groups.TravelGroupRepository;
//import MS3_3.Backend.TravelGroupItinerary.Days.TravelGroupItineraryDay;
//import MS3_3.Backend.TravelGroupItinerary.Days.TravelGroupItineraryDayRepository;
import MS3_3.Backend.TravelGroupItinerary.TravelGroupItinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TravelGroupItineraryEventController {
    //@Autowired
    //TravelGroupItineraryDayRepository travelGroupItineraryDayRepository;

    @Autowired
    TravelGroupRepository travelGroupRepository;
/**
    @GetMapping("/Group/Itinerary/Event/{groupId}")
    public List<TravelGroupItineraryEvent> getTravelGroupItinerary(@PathVariable int groupId,@PathVariable int dayId) {
        return travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().getDay(dayId).getEvents();
    }

    @GetMapping("/Group/Itinerary/Event/{groupId}/{dayId}/{eventId}")
    public TravelGroupItineraryEvent getTravelGroupItineraryByDay(@PathVariable int groupId, @PathVariable int dayId,@PathVariable int eventId) {
        return travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().getDay(dayId).getEvents().get(eventId);
    }

    @PutMapping("/Group/Itinerary/Event/{groupId}/{dayId}/{eventId}")
    public TravelGroupItineraryEvent updateWholeTravelGroupItineraryByDay(@PathVariable int groupId, @PathVariable int dayId,  @PathVariable int eventId,
                                                                              @RequestBody TravelGroupItineraryEvent travelGroupEvent) {
        travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().getDay(1).getEvent(eventId).setEvent(travelGroupEvent);

        return travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().getDays().get(dayId).getEvent(eventId);
    }

    @DeleteMapping("/Group/Itinerary/Event/{groupId}/{dayId}/{eventId}")
    public TravelGroupItineraryDay deleteTravelGroupItinerary(@PathVariable int groupId, @PathVariable int dayId, @PathVariable int eventId) {
        travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().getDay(dayId).getEvents().remove(eventId);
        return travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().getDays().get(dayId);
    }
*/
}
