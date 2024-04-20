package MS3_3.Backend.TravelGroupItinerary;

import MS3_3.Backend.Day.Day;
import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.Groups.TravelGroupRepository;
import MS3_3.Backend.TravelGroupItinerary.Events.TravelGroupItineraryEvent;
import MS3_3.Backend.TravelGroupItinerary.Events.TravelGroupItineraryEventRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TravelGroupItineraryController {

    @Autowired
    TravelGroupRepository travelGroupRepository;

    @Autowired
    TravelGroupItineraryRepository travelGroupItineraryRepository;

    @Autowired
    TravelGroupItineraryEventRepository travelGroupItineraryEventRepository;


    @PostMapping("/Group/Itinerary")
    public TravelGroupItinerary createTravelGroupItinerary(@RequestBody TravelGroupItinerary travelGroupItinerary) {
        travelGroupItineraryRepository.save(travelGroupItinerary);
        return travelGroupItinerary;
    }

    @GetMapping("/Group/Itinerary/{groupId}")
    public TravelGroupItinerary getTravelGroupItinerary(@PathVariable int groupId) {
        return travelGroupItineraryRepository.findByTravelGroupItineraryId(groupId);
    }

    /**
     {
     "id": 1,
     "itineraryName": "London",
     "startDate": "1/11/2025",
     "endDate": "1/13/2025",
     "numDays": 2,
     "travelGroupItinerarySchedule":
     {
     "days": [
     {
     "events": [
     {
     "time": "12:00 pm",
     "place": "St James' Park",
     "notes": "New Castle United Game"
     },
     {
     "time": "1:00 pm",
     "place": "St James' Park",
     "notes": "Halftime get a drink"
     }
     ]
     },
     {
     "events": [
     {
     "time": "6:00 pm",
     "place": "Hotel Name",
     "notes": "Check in Time"
     }
     ]
     }
     ]
     },
     {
     "days": [
     {
     "events": [
     {
     "time": "12:00 pm",
     "place": "Hotel Name",
     "notes": "Checkout Time"
     },
     {
     "time": "12:30 pm",
     "place": "Train Station",
     "notes": "Go somewhere and explore"
     }
     ]
     }
     ]
     }
     }
     */

    @PutMapping("/Group/Itinerary/{groupId}")
    public TravelGroupItinerary updateTravelGroupItinerary(@PathVariable int groupId,
                                                           @RequestBody TravelGroupItinerary updatedItinerary) {

        // Retrieve the corresponding TravelGroup from the repository
        TravelGroupItinerary existingItinerary = travelGroupItineraryRepository.findByTravelGroupItineraryId(groupId);

        // Update the existing itinerary with the values from the updated itinerary
        existingItinerary.setItineraryName(updatedItinerary.getItineraryName());
        existingItinerary.setStartDate(updatedItinerary.getStartDate());
        existingItinerary.setEndDate(updatedItinerary.getEndDate());
        existingItinerary.setNumDays(updatedItinerary.getNumDays());

        // Clear the existing events list
        existingItinerary.getTravelGroupItineraryEventsList().clear();

        // Add the updated events to the existing itinerary
        for (TravelGroupItineraryEvent updatedEvent : updatedItinerary.getTravelGroupItineraryEventsList()) {
            TravelGroupItineraryEvent newEvent = new TravelGroupItineraryEvent(existingItinerary,
                    updatedEvent.getDayNumber(), updatedEvent.getTime(),
                    updatedEvent.getPlace(), updatedEvent.getNotes());
            existingItinerary.getTravelGroupItineraryEventsList().add(newEvent);
        }

        // Save the updated itinerary
        TravelGroupItinerary output = travelGroupItineraryRepository.save(existingItinerary);
        return output;
    }





    /** Working Constant add
     @PutMapping("/Group/Itinerary/{groupId}")
     public TravelGroupItinerary updateTravelGroupItinerary(@PathVariable int groupId,
     @RequestBody TravelGroupItinerary updatedItinerary) {

     // Retrieve the corresponding TravelGroup from the repository
     TravelGroupItinerary existingItinerary = travelGroupItineraryRepository.findByTravelGroupItineraryId(groupId);

     int oldLength = existingItinerary.getTravelGroupItineraryEventsList().size();
     // Update the existing itinerary with the values from the updated itinerary
     existingItinerary.setItineraryName(updatedItinerary.getItineraryName());
     existingItinerary.setStartDate(updatedItinerary.getStartDate());
     existingItinerary.setEndDate(updatedItinerary.getEndDate());
     existingItinerary.setNumDays(updatedItinerary.getNumDays());
     existingItinerary.getTravelGroupItineraryEventsList().clear();

     List<TravelGroupItineraryEvent> temp = existingItinerary.getTravelGroupItineraryEventsList();

     for (int i = 0; i < updatedItinerary.getTravelGroupItineraryEventsList().size(); i++) {
     temp.add(new TravelGroupItineraryEvent(existingItinerary,updatedItinerary.getTravelGroupItineraryEventsList().get(i).getDayNumber(),
     updatedItinerary.getTravelGroupItineraryEventsList().get(i).getPlace(),updatedItinerary.getTravelGroupItineraryEventsList().get(i).getPlace(),
     updatedItinerary.getTravelGroupItineraryEventsList().get(i).getNotes()));
     existingItinerary.getTravelGroupItineraryEventsList().add(temp.get(i));
     }

     existingItinerary.setTravelGroupItineraryEventsList(temp);

     TravelGroupItinerary output = travelGroupItineraryRepository.save(existingItinerary);
     return travelGroupItineraryRepository.findByTravelGroupItineraryId(groupId);
     }
     */

/**
    @DeleteMapping("/Group/Itinerary/{groupId}")
    public TravelGroupItinerary deleteTravelGroupItinerary(@PathVariable int groupId) {
        TravelGroup travelGroup = travelGroupRepository.findByTravelGroupId(groupId);
        travelGroup.setTravelGroupItinerary(null);
        return travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupItinerary();
    }
*/
    @GetMapping("/Group/Itinerary")
    public TravelGroupItineraryRepository getTravelGroupItineraryRepository() {
        return travelGroupItineraryRepository;
    }
}
