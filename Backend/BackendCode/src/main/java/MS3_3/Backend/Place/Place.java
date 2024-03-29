package MS3_3.Backend.Place;

import MS3_3.Backend.Day.Day;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uniqueCode;

    @ManyToOne
    @JoinColumn(name = "day_id")
    @JsonIgnore
    private Day day;

    private String placeName;

    private String startTime;

    private String endTime;


    public Place() {
    }

    public Place(String placeName, String startTime, String endTime, Day day) {
        this.placeName = placeName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }


    public int getUniqueCode() {
        return uniqueCode;
    }

    public Day getDay() {
        return day;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }


    public void setUniqueCode(int uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public void setDay(Day day) {
        this.day = day;
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
}
