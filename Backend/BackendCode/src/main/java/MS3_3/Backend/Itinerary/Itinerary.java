package MS3_3.Backend.Itinerary;

import java.util.List;

import MS3_3.Backend.Day.Day;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Itinerary {
    private String itineraryName;
    @Id
    private String shareCode;
    private String startDate;
    private String endDate;
    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Day> days;

    public Itinerary() {
        
    }

    public Itinerary(String itineraryName, String shareCode, String startDate, String endDate, List<Day> days) {
        this.itineraryName = itineraryName;
        this.shareCode = shareCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
    }


    public String getItineraryName() { return itineraryName; }
    public String getShareCode() { return shareCode; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public List<Day> getDays() { return days; }

    public void setDays(List<Day> days) {
        this.days = days;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public void setItineraryName(String itineraryName) {
        this.itineraryName = itineraryName;
    }
    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
