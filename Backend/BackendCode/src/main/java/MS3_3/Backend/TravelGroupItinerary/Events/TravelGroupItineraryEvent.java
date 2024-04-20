package MS3_3.Backend.TravelGroupItinerary.Events;


import MS3_3.Backend.TravelGroupItinerary.TravelGroupItinerary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class TravelGroupItineraryEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int travelGroupItineraryEventId;

    @ManyToOne
    @JoinColumn(name = "groupItineraryId")
    @JsonIgnore
    private TravelGroupItinerary groupItinerary;

    private int dayNumber;
    private String time;

    private String place;

    private String notes;

    public TravelGroupItineraryEvent(int dayNumber, String time, String place, String notes) {
        this.dayNumber = dayNumber;
        this.time = time;
        this.place = place;
        this.notes = notes;
    }

    public TravelGroupItineraryEvent(TravelGroupItinerary groupsItinerary, int dayNumber, String time, String place, String notes) {
        this.groupItinerary = groupsItinerary;
        this.dayNumber = dayNumber;
        this.time = time;
        this.place = place;
        this.notes = notes;
    }

    public TravelGroupItinerary getGroupItinerary() {
        return groupItinerary;
    }

    public void setGroupItinerary(TravelGroupItinerary groupItinerary) {
        this.groupItinerary = groupItinerary;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public TravelGroupItineraryEvent() {
    }
/**
    public TravelGroupItineraryDay getTravelGroupDay() {
        return travelGroupDay;
    }
*/
    public int getId() {
        return travelGroupItineraryEventId;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getNotes() {
        return notes;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setEvent(TravelGroupItineraryEvent event) {
        this.dayNumber = dayNumber;
        this.time = event.time;
        this.notes = event.notes;
        this.place = event.place;
    }

}
