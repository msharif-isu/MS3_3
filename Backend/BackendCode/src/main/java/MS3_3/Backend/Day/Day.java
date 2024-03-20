package MS3_3.Backend.Day;

import MS3_3.Backend.Itinerary.Itinerary;
import MS3_3.Backend.Place.Place;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uniqueCode;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Place> places;

    @ManyToOne
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    private String destination;


    public Day() {
        places = new ArrayList<Place>();
    }
    public Day(String destination, List<Place> places, Itinerary itinerary) {
        this.destination = destination;
        this.places = places;
        this.itinerary = itinerary;
    }


    public int getUniqueCode() {
        return uniqueCode;
    }

    public List<Place> getPlaces() { return places; }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public String getDestination() { return destination; }


    public void setUniqueCode(int uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
    
    public void setPlaces(List<Place> places) { this.places = places; }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public void setDestination(String destination) { this.destination = destination; }
}
