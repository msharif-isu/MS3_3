package com.example.itinerarybuddy.data;

public class Spinner_ItineraryInfo{

    public String destination;
    public String tripCode;
    public int numDays;

    public Spinner_ItineraryInfo(String destination, String tripCode, int numDays){
        this.destination = destination;
        this.tripCode = tripCode;
        this.numDays = numDays;

    }

    public String getDestination(){
        return destination;
    }

    public String getTripCode(){
        return tripCode;
    }

    public int getNumDays(){
        return numDays;
    }

    @Override
    public String toString(){
        return destination + ", " + tripCode ;
    }
}
