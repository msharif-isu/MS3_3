package MS3_3.Backend.PersonalItinerary;

import MS3_3.Backend.Ambassador.Ambassador;
import MS3_3.Backend.PersonalItinerary.Events.PersonalItineraryEvent;
import MS3_3.Backend.TravelGroups.TravelGroup;
import MS3_3.Backend.UserTypes.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PersonalItinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personalItineraryId;

    private String itineraryName;

    private String startDate;

    private String endDate;

    private int numDays;

    @ManyToOne
    @JsonIgnore
    private User user;

    private String userName;

    @OneToMany(mappedBy = "personalItinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalItineraryEvent> personalItineraryEventsList;


    public PersonalItinerary() {
        this.personalItineraryEventsList = new ArrayList<>();
    }

    public PersonalItinerary(User user,String itineraryName, String startDate, String endDate, int numDays) {
        this.user = user;
        this.userName = user.getUserName();
        this.itineraryName = itineraryName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numDays = numDays;
        this.personalItineraryEventsList = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public List<PersonalItineraryEvent> getPersonalItineraryEventsList() {
        return personalItineraryEventsList;
    }

    public void setPersonalItineraryEventsList(List<PersonalItineraryEvent> travelGroupItineraryEventsList) {
        this.personalItineraryEventsList = travelGroupItineraryEventsList;
    }

    public int getId() {
        return personalItineraryId;
    }


    public String getItineraryName() {
        return itineraryName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }


    public void setItineraryName(String itineraryName) {
        this.itineraryName = itineraryName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
