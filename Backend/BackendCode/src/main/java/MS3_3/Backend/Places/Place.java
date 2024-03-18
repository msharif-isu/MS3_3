package MS3_3.Backend.Places;

public class Place {
    private String placeName;
    private int startDate;
    private int endDate;
    private int startTime;
    private int endTime;

    public Place(String placeName, int startDate, int endDate, int startTime, int endTime) {
        this.placeName = placeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getPlaceName() {
        return placeName;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setPlaceName(String placeName) { this.placeName = placeName; }

    public void setStartDate(int startDate) { this.startDate = startDate; }

    public void setEndDate(int endDate) { this.endDate = endDate; }

    public void setStartTime(int startTime) {this.startTime = startTime; }

    public void setEndTime(int endTime) {this.endTime = endTime; }

}
