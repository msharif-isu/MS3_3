package com.example.itinerarybuddy.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    /*private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }*/

    private final List<String> itineraries = new ArrayList<>();

    public List<String> getItineraries(){
        return itineraries;
    }

    public void addItinerary(String itinerary){
        itineraries.add(0, itinerary);
    }
}