package com.example.itinerarybuddy.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Utility class for managing itinerary information.
 */
public class Itinerary implements Parcelable {

    private String destination;
    private String startDate;
    private String endDate;
    private int numDays;
    private int itineraryID;

    public Itinerary(Parcel in) {
        destination = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        numDays = in.readInt();
        itineraryID = in.readInt();
    }

    public Itinerary() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(destination);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeInt(numDays);
        dest.writeInt(itineraryID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Itinerary> CREATOR = new Creator<Itinerary>() {
        @Override
        public Itinerary createFromParcel(Parcel in) {
            return new Itinerary(in);
        }

        @Override
        public Itinerary[] newArray(int size) {
            return new Itinerary[size];
        }
    };

    public void setItineraryID(int itineraryID) {
        this.itineraryID = itineraryID;
    }

    public int getItineraryID() {
        return itineraryID;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination(){
        return destination;
    }

    public void setStartDate(String startDate) {

        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {

        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setNumOfDays(int numOfDays) {

        this.numDays = numOfDays;
    }

    public int getNumDays(){
        return numDays;
    }
}
