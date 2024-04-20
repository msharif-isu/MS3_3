package MS3_3.Backend.TravelGroupItinerary.Schedule;

import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.Groups.TravelGroupRepository;
//import MS3_3.Backend.TravelGroupItinerary.Days.TravelGroupItineraryDayRepository;
import MS3_3.Backend.TravelGroupItinerary.TravelGroupItinerary;
import MS3_3.Backend.TravelGroupItinerary.TravelGroupItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TravelGroupItineraryScheduleController {
  //  @Autowired
    //TravelGroupItineraryDayRepository travelGroupItineraryDayRepository;

    @Autowired
    TravelGroupItineraryScheduleRepository travelGroupItineraryScheduleRepository;

    @Autowired
    TravelGroupItineraryRepository travelGroupItineraryRepository;

    @Autowired
    TravelGroupRepository travelGroupRepository;
/**
    /**
     *
     * All methods complete
     *
     */
/**
    @GetMapping("/Group/Schedule/{groupId}")
    public TravelGroupItinerarySchedule getTravelGroupItinerary(@PathVariable int groupId) {
        return travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupItinerary().getTravelGroupItinerarySchedule();
    }

    @PutMapping("/Group/Schedule/{groupId}")
    public TravelGroupItinerary updateTravelGroupItinerary(@PathVariable int groupId,
                                                           @RequestBody TravelGroupItinerarySchedule travelGroupItinerarySchedule) {
        travelGroupItineraryRepository.findByTravelGroupItineraryId(groupId).setTravelGroupItinerarySchedule(travelGroupItinerarySchedule);
        return travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupItinerary();
    }
/**
    @DeleteMapping("/Group/Schedule/{groupId}")
    public TravelGroupItinerary deleteTravelGroupItinerary(@PathVariable int groupId) {
        travelGroupItineraryScheduleRepository.findByTravelGroupItineraryScheduleId(groupId).setTravelGroupItinerary(null);
        return travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupItinerary();
    }
    */
}
