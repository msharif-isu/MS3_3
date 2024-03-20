package MS3_3.Backend.Itinerary;

import java.util.ArrayList;
import java.util.List;

import MS3_3.Backend.Day.Day;
import jakarta.persistence.*;

@Entity
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shareCode;

    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Day> days;

    private String itineraryName;

    private String startDate;

    private String endDate;


    public Itinerary() {
        days = new ArrayList<Day>();
    }

    public Itinerary(String itineraryName, String startDate, String endDate) {
        this.itineraryName = itineraryName;
        this.startDate = startDate;
        this.endDate = endDate;
        days = new ArrayList<Day>();
    }

    public Itinerary(String itineraryName, String startDate, String endDate, List<Day> days) {
        this.itineraryName = itineraryName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
    }

    public int getShareCode() { return shareCode; }

    public List<Day> getDays() { return days; }

    public String getItineraryName() { return itineraryName; }

    public String getStartDate() { return startDate; }

    public String getEndDate() { return endDate; }


    public void setShareCode(int shareCode) {
        this.shareCode = shareCode;
    }

    public void setDays(List<Day> days) {
        this.days = days;
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
