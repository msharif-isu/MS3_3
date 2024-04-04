package MS3_3.Backend.Itinerary;

import MS3_3.Backend.UserTypes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItineraryController {
    @Autowired
    ItineraryRepository itineraryRepository;

    @PostMapping("/Itinerary")
    public Itinerary createItinerary(@RequestBody Itinerary itinerary) {
        itineraryRepository.save(itinerary);
        return itinerary;
    }

    @DeleteMapping("/Itinerary/{tripCode}")
    public String deleteItinerary(@PathVariable String tripCode) {
        String name = itineraryRepository.findByTripCode(tripCode).getDestination();
        itineraryRepository.deleteByTripCode(tripCode);
        return "Itinerary " + name + " successfully deleted";
    }

    @PutMapping("/Itinerary/{tripCode}")
    public Itinerary updateItinerary(@PathVariable String tripCode, @RequestBody Itinerary updatedItinerary) {
        Itinerary existingItinerary = itineraryRepository.findByTripCode(tripCode);
        if (existingItinerary != null) {
            existingItinerary.setTripCode(updatedItinerary.getTripCode());
            existingItinerary.setTravelGroup(updatedItinerary.getTravelGroup());
            existingItinerary.setDays(updatedItinerary.getDays());
            existingItinerary.setCreator(updatedItinerary.getCreator());
            existingItinerary.setNumDays(updatedItinerary.getNumDays());
            existingItinerary.setDestination(updatedItinerary.getDestination());
            existingItinerary.setStartDate(updatedItinerary.getStartDate());
            existingItinerary.setEndDate(updatedItinerary.getEndDate());
            itineraryRepository.save(existingItinerary);
            return existingItinerary;
        } else {
            return null;
        }
    }

    @GetMapping("/Itinerary/{tripCode}")
    public Itinerary getItinerary(@PathVariable String tripCode) {
        return itineraryRepository.findByTripCode(tripCode);
    }

    @GetMapping("Itinerary/User/{creator}")
    public List<Itinerary> getUserItineraries(@PathVariable User creator) {
        return itineraryRepository.findByCreator(creator);
    }

    @GetMapping("/Itinerary/List")
    public List<Itinerary> getAllItineraries() {
        return itineraryRepository.findAll();
    }
}
