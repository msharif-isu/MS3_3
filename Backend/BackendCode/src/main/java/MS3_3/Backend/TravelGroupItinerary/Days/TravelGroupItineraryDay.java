package MS3_3.Backend.TravelGroupItinerary.Days;

import MS3_3.Backend.TravelGroupItinerary.Events.TravelGroupItineraryEvent;
import MS3_3.Backend.TravelGroupItinerary.Schedule.TravelGroupItinerarySchedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TravelGroupItineraryDay {
    /**
     * One schedule has Many days
     * One day has Many events
     */

    @ManyToOne
    @JoinColumn(name = "travel_group_itinerary_schedule_id")
    @JsonIgnore
    private TravelGroupItinerarySchedule travelGroupItinerarySchedule;


    @OneToMany
    private List<TravelGroupItineraryEvent> events;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public TravelGroupItineraryDay() {
        this.events = new ArrayList<>();
    }

    public void addEvent(TravelGroupItineraryEvent event) {
        this.events.add(event);
    }

    public void removeEvent(TravelGroupItineraryEvent event) {
        this.events.remove(event);
    }

    public TravelGroupItinerarySchedule getTravelGroupItinerarySchedule() {
        return travelGroupItinerarySchedule;
    }

    public List<TravelGroupItineraryEvent> getEvents() {
        return events;
    }

    public void setEvents(List<TravelGroupItineraryEvent> events) {
        this.events = events;
    }

    public int getId() {
        return id;
    }
}
