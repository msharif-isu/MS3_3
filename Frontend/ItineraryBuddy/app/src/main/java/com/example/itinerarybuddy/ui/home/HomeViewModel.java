package com.example.itinerarybuddy.ui.home;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {


    /**
     * The list of itineraries stored in the ViewModel.
     */
    private final List<String> itineraries = new ArrayList<>();

    /**
     * Retrieves the list of itineraries.
     *
     * @return The list of itineraries stored in the ViewModel.
     */
    public List<String> getItineraries(){
        return itineraries;
    }

    /**
     * Adds a new itinerary to the list.
     * The new itinerary is added to the beginning of the list, making it the most recent itinerary.
     *
     * @param itinerary The itinerary to add to the list.
     */
    public void addItinerary(String itinerary){
        itineraries.add(0, itinerary);
    }
}