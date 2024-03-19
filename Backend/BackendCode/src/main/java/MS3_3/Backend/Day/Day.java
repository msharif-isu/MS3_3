package MS3_3.Backend.Day;

import MS3_3.Backend.Place.Place;
import java.util.ArrayList;

public class Day {
    private String destination;
    private ArrayList<Place> places;

    public Day(String destination, ArrayList<Place> places) {
        this.destination = destination;
        this.places = places;
    }

    public String getDestination() { return destination; }
    public ArrayList<Place> getPlaces() { return places; }

    public void setDestination(String destination) { this.destination = destination; };
    public void setPlaces(ArrayList<Place> places) { this.places = places; }


}
