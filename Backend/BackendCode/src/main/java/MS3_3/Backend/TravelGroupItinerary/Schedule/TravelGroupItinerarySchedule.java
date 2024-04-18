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
    private int travelGroupItineraryScheduleId;

    @OneToMany(mappedBy = "travelGroupItinerarySchedule", cascade = CascadeType.ALL)
    private List<TravelGroupItineraryDay> days;

    @ManyToOne
    @JoinColumn(name = "travelGroupItineraryId")
    @JsonIgnore
    private TravelGroupItinerary travelGroupItinerary;

    public TravelGroupItinerarySchedule() {
        days = new ArrayList<>();
    }

    public TravelGroupItineraryDay getDay(int day) {
        return this.days.get(day);
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
