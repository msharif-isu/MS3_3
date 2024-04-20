package MS3_3.Backend.TravelGroupItinerary;

import MS3_3.Backend.FileUpload.Image;
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

    private String itineraryName;

    private String startDate;

    private String endDate;

    private int numDays;

    @OneToOne(mappedBy = "travelGroupsItinerary", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private TravelGroupItinerarySchedule travelGroupItinerarySchedule;

    public TravelGroupItinerary() {
    }

    public TravelGroupItinerary(String itineraryName, String startDate, String endDate, int numDays) {
        this.itineraryName = itineraryName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numDays = numDays;
    }

    public TravelGroupItinerary(String itineraryName, String startDate, String endDate, TravelGroupItinerarySchedule schedule, int numDays) {
        this.itineraryName = itineraryName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numDays = numDays;
        this.travelGroupItinerarySchedule = schedule;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public TravelGroupItinerarySchedule getTravelGroupItinerarySchedule() {
        return travelGroupItinerarySchedule;
    }

    public void setTravelGroupItinerarySchedule(TravelGroupItinerarySchedule travelGroupItinerarySchedule) {
        this.travelGroupItinerarySchedule = travelGroupItinerarySchedule;
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
