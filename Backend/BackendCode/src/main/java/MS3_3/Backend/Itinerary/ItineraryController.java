package MS3_3.Backend.Itinerary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ItineraryController {
    @Autowired
    ItineraryRepository itineraryRepository;

    @PostMapping("/Itinerary/Create")
    public Itinerary createItinerary(@RequestBody Itinerary itinerary) {
        itineraryRepository.save(itinerary);
        return itinerary;
    }

    @DeleteMapping("/Itinerary/Delete/{shareCode}")
    public String deleteItinerary(@PathVariable String shareCode) {
        String name = itineraryRepository.findByShareCode(shareCode).getItineraryName();
        itineraryRepository.deleteByShareCode(shareCode);
        return "Itinerary " + name + " successfully deleted";
    }

    @PutMapping("/Itinerary/Update/{shareCode}")
    public Itinerary updateItinerary(@PathVariable String shareCode, @RequestBody Itinerary updatedItinerary) {
        Itinerary existingItinerary = itineraryRepository.findByShareCode(shareCode);
        if (existingItinerary != null) {
            existingItinerary.setItineraryName(updatedItinerary.getItineraryName());
            existingItinerary.setStartDate(updatedItinerary.getStartDate());
            existingItinerary.setEndDate(updatedItinerary.getEndDate());
            existingItinerary.setDays(updatedItinerary.getDays());
            itineraryRepository.save(existingItinerary);
            return existingItinerary;
        } else {
            return null;
        }
    }

    @GetMapping("/Itinerary/{shareCode}")
    public Itinerary getItinerary(@PathVariable String shareCode) {
        return itineraryRepository.findByShareCode(shareCode);
    }

    @GetMapping("/Itinerary")
    public List<Itinerary> getAllItineraries() {
        return itineraryRepository.findAll();
    }
}
