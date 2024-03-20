package MS3_3.Backend.Place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaceController {
    @Autowired
    PlaceRepository placeRepository;

    @PostMapping("/Place/Create")
    public Place createPlace(@RequestBody Place place) {
        placeRepository.save(place);
        return place;
    }

    @DeleteMapping("/Place/delete/{placeName}")
    public String deletePlace(@PathVariable String placeName) {
        placeRepository.deleteById(placeName);
        return "Place with name " + placeName + " successfully deleted";
    }

    @PutMapping("/Place/Update/{placeName}")
    public Place updatePlace(@PathVariable String placeName, @RequestBody Place updatedPlace) {
        Place existingPlace = placeRepository.findById(placeName).orElse(null);
        if (existingPlace != null) {
            existingPlace.setStartTime(updatedPlace.getStartTime());
            existingPlace.setEndTime(updatedPlace.getEndTime());
            placeRepository.save(existingPlace);
            return existingPlace;
        } else {
            return null;
        }
    }

    @GetMapping("/Place/{placeName}")
    public Place getPlace(@PathVariable String placeName) {
        return placeRepository.findById(placeName).orElse(null);
    }

    @GetMapping("/Place/List")
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }
}

