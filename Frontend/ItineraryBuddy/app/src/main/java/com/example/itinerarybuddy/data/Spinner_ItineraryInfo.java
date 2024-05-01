package com.example.itinerarybuddy.data;

public class Spinner_ItineraryInfo{

    public String destination;

    public int numDays;

    public int id;

    public Spinner_ItineraryInfo(String destination, int numDays, int id){
        this.destination = destination;
        this.numDays = numDays;
        this.id = id;

    }

    public String getDestination(){
        return destination;
    }


    public int getNumDays(){
        return numDays;
    }


}
