package com.example.itinerarybuddy.data;


/**
 * Utility class for managing itinerary information.
 */
public class Itinerary {


    private String destination;
    private String startDate;
    private String endDate;
    private int numDays;
    private int itineraryID;

    public void setItineraryID(int itineraryID) {
        this.itineraryID = itineraryID;
    }

    public int getItineraryID() {
        return itineraryID;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStartDate(String startDate) {

        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {

        this.endDate = endDate;
    }

    public void setNumOfDays(int numOfDays) {

        this.numDays = numOfDays;
    }
}
