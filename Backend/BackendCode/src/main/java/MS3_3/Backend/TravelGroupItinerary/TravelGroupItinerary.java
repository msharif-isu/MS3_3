package MS3_3.Backend.TravelGroupItinerary;

import MS3_3.Backend.Day.Day;
import MS3_3.Backend.Groups.TravelGroup;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TravelGroupItinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupCode;

    @OneToOne
    @JoinColumn(name = "travel_group_id")
    private TravelGroup travelGroup;

    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Day> days;

    private String itineraryName;

    private String startDate;

    private String endDate;

    public TravelGroupItinerary() {
        days = new ArrayList<Day>();
    }

    public TravelGroupItinerary(String itineraryName, String startDate, String endDate) {
        this.itineraryName = itineraryName;
        this.startDate = startDate;
        this.endDate = endDate;
        days = new ArrayList<Day>();
    }

    public TravelGroupItinerary(String itineraryName, String startDate, String endDate, List<Day> days) {
        this.itineraryName = itineraryName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
    }

    public int getGroupCode() {
        return groupCode;
    }

    public TravelGroup getTravelGroup() {
        return travelGroup;
    }

    public List<Day> getDays() {
        return days;
    }

    public String getItineraryName() {
        return itineraryName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }


    public void setDays(List<Day> days) {
        this.days = days;
    }

    public void addDay(Day day) {
        days.add(day);
    }

    public void setItineraryName(String itineraryName) {
        this.itineraryName = itineraryName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
