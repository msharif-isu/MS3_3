package com.example.itinerarybuddy.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/** Stores a list of JSON objects for a user. */
public class Group {

    private String travelGroupName;

    private String travelGroupCode;

    private String travelGroupDestination;

    private String travelGroupDescription;

    private ArrayList<String> members;

    public Group(String name, String code, String destination, String description, ArrayList<String> members) {
        this.travelGroupName = name;
        this.travelGroupCode = code;
        this.travelGroupDestination = destination;
        this.travelGroupDescription = description;
        this.members = members;
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

    public ArrayList<String> getTravelGroupMembers() {
        return members;
    }

    @NonNull
    @Override
    public String toString() {
        return travelGroupName + "\nDestination: " + travelGroupDestination;
    }
}