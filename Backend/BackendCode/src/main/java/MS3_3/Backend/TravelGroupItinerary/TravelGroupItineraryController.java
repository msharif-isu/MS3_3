package MS3_3.Backend.TravelGroupItinerary;

import MS3_3.Backend.Day.Day;
import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.Groups.TravelGroupRepository;
//import MS3_3.Backend.TravelGroupItinerary.Days.TravelGroupItineraryDay;
//import MS3_3.Backend.TravelGroupItinerary.Days.TravelGroupItineraryDayRepository;
import MS3_3.Backend.TravelGroupItinerary.Events.TravelGroupItineraryEvent;
import MS3_3.Backend.TravelGroupItinerary.Events.TravelGroupItineraryEventRepository;
import MS3_3.Backend.TravelGroupItinerary.Schedule.TravelGroupItinerarySchedule;
import MS3_3.Backend.TravelGroupItinerary.Schedule.TravelGroupItineraryScheduleRepository;
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

    //@Autowired
    //TravelGroupItineraryDayRepository travelGroupItineraryDayRepository;

    @Autowired
    TravelGroupItineraryEventRepository travelGroupItineraryEventRepository;

    @Autowired
    TravelGroupItineraryScheduleRepository travelGroupItineraryScheduleRepository;

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
        existingItinerary.setTravelGroupItinerarySchedule(updatedItinerary.getTravelGroupItinerarySchedule());
        TravelGroupItinerary output = travelGroupItineraryRepository.save(existingItinerary);
        return travelGroupItineraryRepository.findByTravelGroupItineraryId(groupId);
    }





    /**
     @PutMapping("/Group/Itinerary/{groupId}")
     public TravelGroup updateTravelGroupItinerary(@PathVariable int groupId,
     @RequestBody TravelGroupItinerary updatedItinerary) {
     // Retrieve the corresponding TravelGroup from the repository
     TravelGroup travelGroup = travelGroupRepository.findByTravelGroupId(groupId);

     // Get the existing TravelGroupItinerary
     TravelGroupItinerary existingItinerary = travelGroup.getTravelGroupItinerary();

     // Update the existing itinerary with the values from the updated itinerary
     existingItinerary.setItineraryName(updatedItinerary.getItineraryName());
     existingItinerary.setStartDate(updatedItinerary.getStartDate());
     existingItinerary.setEndDate(updatedItinerary.getEndDate());
     existingItinerary.setNumDays(updatedItinerary.getNumDays());

     // Clear existing days in the itinerary schedule
     existingItinerary.getTravelGroupItinerarySchedule().getDays().clear();

     // Copy days and events from the updated itinerary to the existing itinerary's schedule
     List<TravelGroupItineraryDay> updatedDays = updatedItinerary.getTravelGroupItinerarySchedule().getDays();
     List<TravelGroupItineraryDay> newDays = updatedDays.stream()
     .map(updatedDay -> {
     TravelGroupItineraryDay newDay = new TravelGroupItineraryDay();
     List<TravelGroupItineraryEvent> updatedEvents = updatedDay.getEvents();
     List<TravelGroupItineraryEvent> newEvents = updatedEvents.stream()
     .map(updatedEvent -> {
     TravelGroupItineraryEvent newEvent = new TravelGroupItineraryEvent();
     newEvent.setTime(updatedEvent.getTime());
     newEvent.setPlace(updatedEvent.getPlace());
     newEvent.setNotes(updatedEvent.getNotes());
     return newEvent;
     })
     .collect(Collectors.toList());
     newDay.setEvents(newEvents);
     return newDay;
     })
     .collect(Collectors.toList());

     existingItinerary.getTravelGroupItinerarySchedule().setDays(newDays);

     // Update the itinerary in the travel group
     travelGroup.setTravelGroupItinerary(existingItinerary);

     // Save the updated TravelGroup
     travelGroupRepository.save(travelGroup);

     return travelGroup;
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
