package MS3_3.Backend;

public class Place {
    private String place;
    private int startDate;
    private int endDate;
    private int startTime;
    private int endTime;

    public Place(String place, int startDate, int endDate, int startTime, int endTime) {
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
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

    public void setPlace(String place) { this.place = place; }

    public void setStartDate(int startDate) { this.startDate = startDate; }

    public void setEndDate(int endDate) { this.endDate = endDate; }

    public void setStartTime(int startTime) {this.startTime = startTime; }

    public void setEndTime(int endTime) {this.endTime = endTime; }

}
