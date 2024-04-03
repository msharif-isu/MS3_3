package com.example.itinerarybuddy.data;

import java.sql.Time;


public class ScheduleItem {

    int day;
    String date;
    Time time;
    String places;
    String notes;
    String tripCode;

    //default constructor
    public ScheduleItem() {
    }

    public ScheduleItem(int day, String date, Time time, String places, String notes) {

        this.day = day;
        this.date = date;
        this.time = time;
        this.places = places;
        this.notes = notes;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public String getTripCode(){
        return tripCode;
    }

    public int getDay() {

        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDate(){

        return date;
    }

    public void setDate(String date) {

        this.date = date;

    }

    public Time getTime() {
        return time;
    }


    public void setTime(Time time){

        this.time = time;
    }

    public String getPlaces(){
        return places;
    }

    public void setPlaces(String places){

        this.places = places;
    }

    public String getNotes(){
        return notes;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }


}
