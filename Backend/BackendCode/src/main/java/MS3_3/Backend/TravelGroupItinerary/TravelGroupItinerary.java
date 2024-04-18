package MS3_3.Backend.TravelGroupItinerary;

import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.TravelGroupItinerary.Schedule.TravelGroupItinerarySchedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TravelGroupItinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int travelGroupItineraryId;

    @OneToOne
    @JsonIgnore
    private TravelGroup travelGroup;

    private String itineraryName;

    private String startDate;

    private String endDate;

    private int numDays;

    @OneToMany(mappedBy = "travelGroupItinerary", cascade = CascadeType.ALL)
    private List<TravelGroupItinerarySchedule> travelGroupItinerarySchedule;

    public TravelGroupItinerary() {
        this.travelGroupItinerarySchedule = new ArrayList<TravelGroupItinerarySchedule>();
    }

    public TravelGroupItinerary(String itineraryName, String startDate, String endDate, int numDays) {
        this.itineraryName = itineraryName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numDays = numDays;
        this.travelGroupItinerarySchedule = new ArrayList<TravelGroupItinerarySchedule>();
    }

    public TravelGroupItinerary(String itineraryName, String startDate, String endDate, List<TravelGroupItinerarySchedule> itinerary, int numDays) {
        this.itineraryName = itineraryName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numDays = numDays;
        this.travelGroupItinerarySchedule = itinerary;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public List<TravelGroupItinerarySchedule> getTravelGroupItinerarySchedule() {
        return travelGroupItinerarySchedule;
    }

    public void setTravelGroupItinerarySchedule(List<TravelGroupItinerarySchedule> travelGroupItinerarySchedule) {
        this.travelGroupItinerarySchedule = travelGroupItinerarySchedule;
    }

    public int getId() {
        return travelGroupItineraryId;
    }

    public TravelGroup getTravelGroup() {
        return travelGroup;
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
