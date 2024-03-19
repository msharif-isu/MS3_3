package MS3_3.Backend.Place;

public class Place {
    private String placeName;
    private String startTime;
    private String endTime;

    public Place(String placeName, String startTime, String endTime) {
        this.placeName = placeName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getPlaceName() { return placeName; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

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
