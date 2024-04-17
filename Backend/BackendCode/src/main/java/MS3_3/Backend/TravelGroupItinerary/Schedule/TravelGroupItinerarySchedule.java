package MS3_3.Backend.TravelGroupItinerary.Schedule;

import MS3_3.Backend.TravelGroupItinerary.Days.TravelGroupItineraryDay;
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
    private int id;

    @OneToMany
    private List<TravelGroupItineraryDay> days;

    @ManyToOne
    @JoinColumn(name = "TravelGroupItinerary")
    @JsonIgnore
    private TravelGroupItinerary travelGroupItinerary;

    public TravelGroupItinerarySchedule() {
        days = new ArrayList<>();
    }

    public TravelGroupItinerary getTravelGroupItinerary() {
        return travelGroupItinerary;
    }

    public void setTravelGroupItinerary(TravelGroupItinerary travelGroupItinerary) {
        this.travelGroupItinerary = travelGroupItinerary;
    }

    public List<TravelGroupItineraryDay> getDays() {
        return days;
    }

    public void setDays(List<TravelGroupItineraryDay> days) {
        this.days = days;
    }
}
