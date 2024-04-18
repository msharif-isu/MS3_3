package MS3_3.Backend.TravelGroupItinerary.Days;

import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.Groups.TravelGroupRepository;
import MS3_3.Backend.TravelGroupItinerary.Schedule.TravelGroupItinerarySchedule;
import MS3_3.Backend.TravelGroupItinerary.TravelGroupItinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TravelGroupItineraryDayController {
    @Autowired
    TravelGroupItineraryDayRepository travelGroupItineraryDayRepository;

    @Autowired
    TravelGroupRepository travelGroupRepository;

    @GetMapping("/Group/Itinerary/Day/{groupId}")
    public List<TravelGroupItineraryDay> getTravelGroupItinerary(@PathVariable int groupId) {
        return travelGroupRepository.findById(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().get(groupId).getDays();
    }

    @GetMapping("/Group/Itinerary/Day/{groupId}/{dayId}")
    public TravelGroupItineraryDay getTravelGroupItineraryByDay(@PathVariable int groupId, @PathVariable int dayId) {
        return travelGroupRepository.findById(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().get(groupId).getDay(dayId);
    }

    @PutMapping("/Group/Itinerary/Day/{groupId}/{dayId}")
    public List<TravelGroupItineraryDay> updateWholeTravelGroupItineraryByDay(@PathVariable int groupId,
                                                           @RequestBody List<TravelGroupItineraryDay> travelGroupDays) {
        travelGroupRepository.findById(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().get(groupId).setDays(travelGroupDays);

        return travelGroupRepository.findById(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().get(groupId).getDays();
    }

    @PutMapping("/Group/Itinerary/Day/Add/{groupId}/{dayId}")
    public List<TravelGroupItineraryDay> addTravelGroupItineraryByDay(@PathVariable int groupId,
                                                                              @RequestBody TravelGroupItineraryDay travelGroupDays) {
        travelGroupRepository.findById(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().get(groupId).getDays().add(travelGroupDays);

        return travelGroupRepository.findById(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().get(groupId).getDays();
    }

    @PutMapping("/Group/Itinerary/Day/Delete/{groupId}/{dayId}")
    public List<TravelGroupItineraryDay> deleteTravelGroupItineraryByDay(@PathVariable int groupId,
                                                                         @PathVariable int dayId) {
        travelGroupRepository.findById(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().get(groupId).getDays().remove(dayId);

        return travelGroupRepository.findById(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().get(groupId).getDays();
    }

    @DeleteMapping("/Group/Itinerary/Day/{groupId}")
    public TravelGroupItinerary deleteTravelGroupItinerary(@PathVariable int groupId,@PathVariable int dayId) {
        travelGroupRepository.findById(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule().get(dayId).setDays(null);
        return travelGroupRepository.findById(groupId).getTravelGroupItinerary();
    }

}
