package MS3_3.Backend.Place;

import MS3_3.Backend.Day.Day;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Place {
    private String placeName;
    private String startTime;
    private String endTime;
    @ManyToOne
    @JoinColumn(name = "day_id")
    private Day day;
    @Id
    private String uniqueCode;

    public Place(String placeName, String startTime, String endTime, Day day, String uniqueCode) {
        this.placeName = placeName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.uniqueCode = uniqueCode;
    }

    public String getPlaceName() { return placeName; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

    public Day getDay() {
        return day;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
