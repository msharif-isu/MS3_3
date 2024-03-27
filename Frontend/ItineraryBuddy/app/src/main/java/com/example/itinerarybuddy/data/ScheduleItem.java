package com.example.itinerarybuddy.data;

import java.sql.Time;


/**
 * Represents a schedule item with various attributes such as day, date, time, places, and notes.
 */
public class ScheduleItem {

    int day;
    String date;
    Time time;
    String places;
    String notes;

    /**
     * Default constructor.
     */
    public ScheduleItem() {
    }


    /**
     * Constructs a ScheduleItem with specified attributes.
     *
     * @param day     The day of the schedule item.
     * @param date    The date of the schedule item.
     * @param time    The time of the schedule item.
     * @param places  The places associated with the schedule item.
     * @param notes   The notes associated with the schedule item.
     */
    public ScheduleItem(int day, String date, Time time, String places, String notes) {

        this.day = day;
        this.date = date;
        this.time = time;
        this.places = places;
        this.notes = notes;
    }

    /**
     * Retrieves the day of the schedule item.
     *
     * @return The day of the schedule item.
     */
    public int getDay() {

        return day;
    }

    /**
     * Sets the day of the schedule item.
     *
     * @param day The day to set.
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Retrieves the date of the schedule item.
     *
     * @return The date of the schedule item.
     */
    public String getDate(){

        return date;
    }

    /**
     * Sets the date of the schedule item.
     *
     * @param date The date to set.
     */
    public void setDate(String date) {

        this.date = date;

    }

    /**
     * Retrieves the time of the schedule item.
     *
     * @return The time of the schedule item.
     */
    public Time getTime() {
        return time;
    }



    /**
     * Sets the time of the schedule item.
     *
     * @param time The time to set.
     */
    public void setTime(Time time){

        this.time = time;
    }


    /**
     * Retrieves the places associated with the schedule item.
     *
     * @return The places associated with the schedule item.
     */
    public String getPlaces(){
        return places;
    }

    /**
     * Sets the places associated with the schedule item.
     *
     * @param places The places to set.
     */
    public void setPlaces(String places){

        this.places = places;
    }


    /**
     * Retrieves the notes associated with the schedule item.
     *
     * @return The notes associated with the schedule item.
     */
    public String getNotes(){
        return notes;
    }

    /**
     * Sets the notes associated with the schedule item.
     *
     * @param notes The notes to set.
     */
    public void setNotes(String notes){
        this.notes = notes;
    }


}
