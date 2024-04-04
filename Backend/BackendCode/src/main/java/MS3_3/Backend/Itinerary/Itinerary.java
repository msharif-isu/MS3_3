package MS3_3.Backend.Itinerary;

import MS3_3.Backend.Day.Day;
import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.UserTypes.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Itinerary {
    @Id
    private String tripCode;

    @OneToOne
    @JsonIgnore
    private TravelGroup travelGroup;

    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private Day[] days;

    @ManyToOne
    private User creator;

    private int numDays;

    private String destination;

    private String startDate;

    private String endDate;


    public Itinerary() {}

    public Itinerary(int numDays) {
        this.days = new Day[numDays];
        this.numDays = numDays;
        for(int i = 0; i < numDays - 1; i++) {
            days[i] = new Day();
            days[i].setItinerary(this);
        }
    }

    public Itinerary(String tripCode, User creator, int numDays, String destination, String startDate, String endDate) {
        this.tripCode = tripCode;
        this.creator = creator;
        this.days = new Day[numDays];
        this.numDays = numDays;
        for(int i = 0; i < numDays - 1; i++) {
            days[i] = new Day();
            days[i].setItinerary(this);
        }
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Itinerary(String tripCode, User creator, int numDays, Day[] days, String destination, String startDate, String endDate) {
        this.tripCode = tripCode;
        this.creator = creator;
        this.days = days;
        this.numDays = numDays;
        for(int i = 0; i < numDays - 1; i++) {
            days[i] = new Day();
            days[i].setItinerary(this);
        }
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTripCode() {
        return tripCode;
    }

    public TravelGroup getTravelGroup() {
        return travelGroup;
    }

    public Day[] getDays() {
        return days;
    }

    public User getCreator() {
        return creator;
    }

    public int getNumDays() {
        return numDays;
    }

    public String getDestination() {
        return destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public void setTravelGroup(TravelGroup travelGroup) {
        this.travelGroup = travelGroup;
    }

    public void setDays(Day[] days) {
        this.days = days;
        for(int i = 0; i < days.length-1; i++) {
            days[i].setItinerary(this);
        }
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
        days = new Day[numDays];
        for(int i = 0; i < numDays-1; i++) {
            days[i] = new Day();
            days[i].setItinerary(this);
        }
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
