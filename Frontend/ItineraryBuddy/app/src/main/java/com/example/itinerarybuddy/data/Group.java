package com.example.itinerarybuddy.data;

import androidx.annotation.NonNull;

/** Stores a list of JSON objects for a user. */
public class Group {

    private String travelGroupName;

    private String travelGroupCode;

    private String travelGroupDestination;

    private String travelGroupDescription;

    public Group(String name, String code, String destination, String description) {
        this.travelGroupName = name;
        this.travelGroupCode = code;
        this.travelGroupDestination = destination;
        this.travelGroupDescription = description;
    }

    public String getTravelGroupName() {
        return travelGroupName;
    }

    public void setTravelGroupName(String travelGroupName) {
        this.travelGroupName = travelGroupName;
    }

    public String getTravelGroupCode() {
        return travelGroupCode;
    }

    public void setTravelGroupCode(String travelGroupCode) {
        this.travelGroupCode = travelGroupCode;
    }

    public String getTravelGroupDestination() {
        return travelGroupDestination;
    }

    public void setTravelGroupDestination(String travelGroupDestination) {
        this.travelGroupDestination = travelGroupDestination;
    }

    public String getTravelGroupDescription() {
        return travelGroupDescription;
    }

    public void setTravelGroupDescription(String travelGroupDescription) {
        this.travelGroupDescription = travelGroupDescription;
    }

    @NonNull
    @Override
    public String toString() {
        return travelGroupName + "\nDestination: " + travelGroupDestination;
    }
}