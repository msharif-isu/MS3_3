package com.example.itinerarybuddy.data;


import com.android.volley.RequestQueue;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility class for managing itinerary information.
 */
public class Itinerary {


    /**
     * JSONObject containing itinerary information.
     */
    public static JSONObject itineraryInfo;


    /**
     * RequestQueue used for making network requests.
     */
    public static RequestQueue requestQueue;

    /**
     * Retrieves the destination from the itinerary information.
     *
     * @return The destination of the itinerary.
     */
    public static String getDestination(){

        String destination = null;

        try{

            destination = itineraryInfo.getString("destination");
        } catch (JSONException e){

            e.printStackTrace();
        }

        return destination;
    }

    /**
     * Retrieves the trip code from the itinerary information.
     *
     * @return The trip code of the itinerary.
     */
    public static String getTripCode(){

        String tripCode = null;

        try{

            tripCode= itineraryInfo.getString("tripCode");
        } catch (JSONException e){

            e.printStackTrace();
        }

        return tripCode;

    }

    /**
     * Retrieves the start date from the itinerary information.
     *
     * @return The start date of the itinerary.
     */
    public static String getStartDate(){

        String startDate = null;

        try{

            startDate= itineraryInfo.getString("start date");
        } catch (JSONException e){

            e.printStackTrace();
        }

        return startDate;

    }

    /**
     * Retrieves the end date from the itinerary information.
     *
     * @return The end date of the itinerary.
     */
    public static String getEndDate(){

        String endDate = null;

        try{

            endDate= itineraryInfo.getString("end date");
        } catch (JSONException e){

            e.printStackTrace();
        }

        return endDate;

    }

    /**
     * Retrieves the number of days from the itinerary information.
     *
     * @return The number of days of the itinerary.
     */
    public static String getDays(){

        String days = "0";

        try{

            days = itineraryInfo.getString("number of days");

        } catch (JSONException e){

            e.printStackTrace();
        }

        return days;
    }
}
