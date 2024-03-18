package MS3_3.Backend.Itinerary;

import java.util.ArrayList;
import MS3_3.Backend.Places.Place;

public class Itinerary {
    private String name;

    private boolean isPublic;

    private String shareCode;

    private ArrayList<Place> places;

    public Itinerary(String name, boolean isPublic, String shareCode, ArrayList<Place> places) {
        this.name = name;
        this.isPublic = isPublic;
        this.shareCode = shareCode;
        this.places = places;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void changeShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public boolean getPublic() {
        return isPublic;
    }

    public void changePublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getName() {
        return name;
    }
    public void changeName(String name) {
        this.name = name;
    }
}
