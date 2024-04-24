package MS3_3.Backend.TravelGroupItinerary;

import MS3_3.Backend.TravelGroups.TravelGroup;
import MS3_3.Backend.TravelGroupItinerary.Events.TravelGroupItineraryEvent;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TravelGroupItinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int travelGroupItineraryId;

    private String itineraryName;

    private String startDate;

    private String endDate;

    private int numDays;

    @OneToOne(mappedBy = "travelGroupItinerary")
    private TravelGroup travelGroup;

    @OneToMany(mappedBy = "groupItinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelGroupItineraryEvent> travelGroupItineraryEventsList;


    public TravelGroupItinerary() {
        this.travelGroupItineraryEventsList = new ArrayList<>();
    }

    public TravelGroupItinerary(String itineraryName, String startDate, String endDate, int numDays) {
        this.itineraryName = itineraryName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numDays = numDays;
        this.travelGroupItineraryEventsList = new ArrayList<>();
    }


    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public List<TravelGroupItineraryEvent> getTravelGroupItineraryEventsList() {
        return travelGroupItineraryEventsList;
    }

    public void setTravelGroupItineraryEventsList(List<TravelGroupItineraryEvent> travelGroupItineraryEventsList) {
        this.travelGroupItineraryEventsList = travelGroupItineraryEventsList;
    }

    public int getId() {
        return travelGroupItineraryId;
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
