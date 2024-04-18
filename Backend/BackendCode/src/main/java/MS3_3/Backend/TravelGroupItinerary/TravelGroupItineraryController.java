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
        TravelGroup travelGroup = travelGroupRepository.findById(travelGroupItinerary.getId());
        travelGroup.setTravelGroupItinerary(travelGroupItinerary);
        return travelGroup.getTravelGroupItinerary();
    }

    @GetMapping("/Group/Itinerary/{groupId}")
    public TravelGroupItinerary getTravelGroupItinerary(@PathVariable int groupId) {
        return travelGroupRepository.findById(groupId).getTravelGroupItinerary();
    }

    @PutMapping("/Group/Itinerary/{groupId}")
    public TravelGroupItinerary updateTravelGroupItinerary(@PathVariable int groupId,
                                                           @RequestBody TravelGroupItinerary travelGroupItinerary) {
        // Retrieve the existing TravelGroupItinerary from the repository
        TravelGroupItinerary existingItinerary = travelGroupItineraryRepository.findByTravelGroupItineraryId(groupId);

        travelGroupItinerary.getTravelGroupItinerarySchedule();
        // Update the fields of the existing itinerary with the values from the request body
        existingItinerary.setTravelGroupItinerarySchedule(travelGroupItinerary.getTravelGroupItinerarySchedule());
        existingItinerary.setItineraryName(travelGroupItinerary.getItineraryName());
        existingItinerary.setStartDate(travelGroupItinerary.getStartDate());
        existingItinerary.setEndDate(travelGroupItinerary.getEndDate());
        existingItinerary.setNumDays(travelGroupItinerary.getNumDays());

        // Save the updated TravelGroupItinerary
        travelGroupItineraryRepository.save(existingItinerary);

        // It seems like you're also updating the TravelGroup with the new TravelGroupItinerary,
        // so ensure that both TravelGroup and TravelGroupItinerary are properly updated and saved.

        // Retrieve the corresponding TravelGroup from the repository
        TravelGroup travelGroup = travelGroupRepository.findById(groupId);

        // Update the TravelGroup with the new TravelGroupItinerary
        travelGroup.setTravelGroupItinerary(existingItinerary);

        // Save the updated TravelGroup
        travelGroupRepository.save(travelGroup);

        // Return the updated TravelGroupItinerary
        return existingItinerary;
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
