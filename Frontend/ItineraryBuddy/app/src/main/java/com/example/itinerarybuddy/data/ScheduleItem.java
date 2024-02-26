package com.example.itinerarybuddy.data;

import java.util.Map;

public class ScheduleItem {

    int day;
    String date;
    Map<String, String> time;
    Map<String, String> places;
    Map<String, String> notes;

    //default constructor
    public ScheduleItem() {
    }


    public ScheduleItem(int day, String date, Map<String, String> time, Map<String, String> places, Map<String, String> notes) {

        this.day = day;
        this.date = date;
        this.time = time;
        this.places = places;
        this.notes = notes;
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

    public Map<String, String> getTime(){
        return time;
    }

    public void setTime(Map<String, String> time){

        this.time = time;
    }

    public Map<String, String> getPlaces(){
        return places;
    }

    public void setPlaces(Map<String, String> places){

        this.places = places;
    }

    public Map<String, String> getNotes(){
        return notes;
    }

    public void setNotes(Map<String, String> notes){
        this.notes = notes;
    }


}
