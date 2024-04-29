package MS3_3.Backend.PersonalItinerary.Events;


import MS3_3.Backend.PersonalItinerary.PersonalItinerary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class PersonalItineraryEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personalItineraryEventId;

    @ManyToOne
    @JoinColumn(name = "personalItineraryId")
    @JsonIgnore
    private PersonalItinerary personalItinerary;

    private int dayNumber;
    private String time;

    private String place;

    private String notes;

    public PersonalItineraryEvent(int dayNumber, String time, String place, String notes) {
        this.dayNumber = dayNumber;
        this.time = time;
        this.place = place;
        this.notes = notes;
    }

    public PersonalItineraryEvent(PersonalItinerary groupsItinerary, int dayNumber, String time, String place, String notes) {
        this.personalItinerary = groupsItinerary;
        this.dayNumber = dayNumber;
        this.time = time;
        this.place = place;
        this.notes = notes;
    }

    public PersonalItinerary getPersonalItinerary() {
        return personalItinerary;
    }

    public void setPersonalItinerary(PersonalItinerary groupItinerary) {
        this.personalItinerary = groupItinerary;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public PersonalItineraryEvent() {
    }

    public int getId() {
        return personalItineraryEventId;
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

    public void setEvent(PersonalItineraryEvent event) {
        this.dayNumber = dayNumber;
        this.time = event.time;
        this.notes = event.notes;
        this.place = event.place;
    }

}
