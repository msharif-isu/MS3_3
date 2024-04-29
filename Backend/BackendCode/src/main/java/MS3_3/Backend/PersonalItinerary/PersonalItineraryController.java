package MS3_3.Backend.PersonalItinerary;

import MS3_3.Backend.PersonalItinerary.Events.PersonalItineraryEvent;
import MS3_3.Backend.PersonalItinerary.Events.PersonalItineraryEventRepository;
import MS3_3.Backend.TravelGroupItinerary.TravelGroupItinerary;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonalItineraryController {

    @Autowired
    PersonalItineraryRepository personalItineraryRepository;

    @Autowired
    PersonalItineraryEventRepository personalItineraryEventRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/Personal/Itinerary/{userName}")
    public PersonalItinerary createPersonalItinerary(@PathVariable String userName, @RequestBody PersonalItinerary personalItinerary) {
        // Create a new PersonalItinerary
        PersonalItinerary newItinerary = new PersonalItinerary(
                userRepository.findByUserName(userName),
                personalItinerary.getItineraryName(),
                personalItinerary.getStartDate(),
                personalItinerary.getEndDate(),
                personalItinerary.getNumDays()
        );

        // Iterate through the events in the request body and add them to the new itinerary
        for (PersonalItineraryEvent event : personalItinerary.getPersonalItineraryEventsList()) {
            PersonalItineraryEvent newEvent = new PersonalItineraryEvent(
                    newItinerary,
                    event.getDayNumber(),
                    event.getTime(),
                    event.getPlace(),
                    event.getNotes()
            );
            newItinerary.getPersonalItineraryEventsList().add(newEvent);
        }

        // Save the new itinerary
        PersonalItinerary output = personalItineraryRepository.save(newItinerary);
        return output;
    }

    @GetMapping("/Personal/Itinerary/{personalItineraryId}")
    public PersonalItinerary getTravelGroupItinerary(@PathVariable int personalItineraryId) {
        return personalItineraryRepository.findByPersonalItineraryId(personalItineraryId);
    }

    /**     Example Json for Put Request
     {
         "itineraryName": "The UK",
         "startDate": "1/12/2025",
         "endDate": "1/14/2025",
         "numDays": 2,
         "personalItineraryEventsList": [
             {
                 "dayNumber": 1,
                 "time": "12:00 pm",
                 "place": "St James' Park",
                 "notes": "New Castle United Game"
             },
             {
                 "dayNumber": 1,
                 "time": "6:00 pm",
                 "place": "Hotel Name",
                 "notes": "Check in Time"
             },
             {
                 "dayNumber": 2,
                 "time": "12:00 pm",
                 "place": "Hotel Name",
                 "notes": "Checkout Time"
             }
         ]
     }
     */

    @PutMapping("/Personal/Itinerary/{personalItineraryId}")
    public PersonalItinerary updatePersonalItinerary(@PathVariable int personalItineraryId,
                                                           @RequestBody PersonalItinerary updatedItinerary) {

        // Retrieve the corresponding TravelGroup from the repository
        PersonalItinerary existingItinerary = personalItineraryRepository.findByPersonalItineraryId(personalItineraryId);

        // Update the existing itinerary with the values from the updated itinerary
        existingItinerary.setItineraryName(updatedItinerary.getItineraryName());
        existingItinerary.setStartDate(updatedItinerary.getStartDate());
        existingItinerary.setEndDate(updatedItinerary.getEndDate());
        existingItinerary.setNumDays(updatedItinerary.getNumDays());

        // Clear the existing events list
        existingItinerary.getPersonalItineraryEventsList().clear();

        // Add the updated events to the existing itinerary
        for (PersonalItineraryEvent updatedEvent : updatedItinerary.getPersonalItineraryEventsList()) {
            PersonalItineraryEvent newEvent = new PersonalItineraryEvent(existingItinerary,
                    updatedEvent.getDayNumber(), updatedEvent.getTime(),
                    updatedEvent.getPlace(), updatedEvent.getNotes());
            existingItinerary.getPersonalItineraryEventsList().add(newEvent);
        }

        // Save the updated itinerary
        PersonalItinerary output = personalItineraryRepository.save(existingItinerary);
        return output;
    }

    @DeleteMapping("/Personal/Itinerary/{personalItineraryId}")
    public String deleteTravelGroupItinerary(@PathVariable int personalItineraryId) {
        User user = personalItineraryRepository.findByPersonalItineraryId(personalItineraryId).getUser();
        user.getPersonalItineraries().remove(personalItineraryRepository.findByPersonalItineraryId(personalItineraryId));
        userRepository.save(user);
        personalItineraryRepository.deleteByPersonalItineraryId(personalItineraryId);
        return "Deleted personal itinerary " + personalItineraryId;
    }

    @GetMapping("/Personal/Itineraries/{userName}")
    public List<PersonalItinerary> getPersonalItinerariesByUsername(@PathVariable String userName) {
        return userRepository.findByUserName(userName).getPersonalItineraries();
    }

    @GetMapping("/Personal/Itineraries")
    public List<PersonalItinerary> getTravelGroupItineraryRepository() {
        return personalItineraryRepository.findAll();
    }

}
