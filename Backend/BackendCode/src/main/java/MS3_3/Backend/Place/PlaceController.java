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

    @DeleteMapping("/Place/delete/{uniqueCode}")
    public String deletePlace(@PathVariable int uniqueCode) {
        placeRepository.deleteByUniqueCode(uniqueCode);
        return "Place with name " + uniqueCode + " successfully deleted";
    }

    @PutMapping("/Place/Update/{uniqueCode}")
    public Place updatePlace(@PathVariable int uniqueCode, @RequestBody Place updatedPlace) {
        Place existingPlace = placeRepository.findByUniqueCode(uniqueCode);
        if (existingPlace != null) {
            existingPlace.setStartTime(updatedPlace.getStartTime());
            existingPlace.setEndTime(updatedPlace.getEndTime());
            placeRepository.save(existingPlace);
            return existingPlace;
        } else {
            return null;
        }
    }

    @GetMapping("/Place/{uniqueCode}")
    public Place getPlace(@PathVariable int uniqueCode) {
        return placeRepository.findByUniqueCode(uniqueCode);
    }

    @GetMapping("/Place/List")
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }
}

