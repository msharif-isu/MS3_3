package MS3_3.Backend.TravelGroupItinerary.Schedule;

//import MS3_3.Backend.TravelGroupItinerary.Days.TravelGroupItineraryDay;
import MS3_3.Backend.TravelGroupItinerary.Events.TravelGroupItineraryEvent;
import MS3_3.Backend.TravelGroupItinerary.TravelGroupItinerary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TravelGroupItinerarySchedule {

    /**
     * ONE travel group has ONE itinerary
     * ONE itinerary has MANY schedules
     * ONE schedule has MANY Events
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int travelGroupItineraryScheduleId;

    @OneToMany(mappedBy = "travelGroupsSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelGroupItineraryEvent> events;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "travelGroupItineraryId")
    private TravelGroupItinerary travelGroupsItinerary;

    public TravelGroupItinerarySchedule() {
        events = new ArrayList<>();
    }

    public void setTravelGroupItinerary(TravelGroupItinerary travelGroupItinerary) {
        //this.travelGroupItinerary = travelGroupItinerary;
    }

    public TravelGroupItinerary getTravelGroupsItinerary() {
        return travelGroupsItinerary;
    }

    public void setTravelGroupItineraryScheduleId(int travelGroupItineraryScheduleId) {
        this.travelGroupItineraryScheduleId = travelGroupItineraryScheduleId;
    }

    public List<TravelGroupItineraryEvent> getDays() {
        return events;
    }

    public void setDays(List<TravelGroupItineraryEvent> days) {
        this.events = days;
    }


}
