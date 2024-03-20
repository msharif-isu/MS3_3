package MS3_3.Backend.Day;

import MS3_3.Backend.Itinerary.Itinerary;
import MS3_3.Backend.Place.Place;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Day {
    private String destination;
    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Place> places;
    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;
    @Id
    private String uniqueCode;

    public Day(String destination, List<Place> places, Itinerary itinerary, String uniqueCode) {
        this.destination = destination;
        this.places = places;
        this.itinerary = itinerary;
        this.uniqueCode = uniqueCode;
    }

    public String getDestination() { return destination; }
    public List<Place> getPlaces() { return places; }

    public Itinerary getItinerary() {
        return itinerary;
    }
    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setDestination(String destination) { this.destination = destination; };
    public void setPlaces(List<Place> places) { this.places = places; }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
}
