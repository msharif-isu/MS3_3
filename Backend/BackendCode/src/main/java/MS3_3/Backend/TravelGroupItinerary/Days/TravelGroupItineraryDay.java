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
    @JoinColumn(name = "travelGroupItineraryScheduleId")
    @JsonIgnore
    private TravelGroupItinerarySchedule travelGroupItinerarySchedule;


    @OneToMany(mappedBy = "travelGroupDay", cascade = CascadeType.ALL)
    private List<TravelGroupItineraryEvent> events;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int travelGroupItineraryDayId;

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

    public TravelGroupItineraryEvent getEvent(int event) {
        return events.get(event);
    }

    public int getId() {
        return travelGroupItineraryDayId;
    }
}
