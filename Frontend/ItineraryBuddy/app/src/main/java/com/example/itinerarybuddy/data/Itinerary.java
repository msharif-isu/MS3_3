package com.example.itinerarybuddy.data;


import com.android.volley.RequestQueue;
import org.json.JSONException;
import org.json.JSONObject;

public class Itinerary {

    public static JSONObject itineraryInfo;

    public static RequestQueue requestQueue;

    public static String getDestination(){

        String destination = null;

        try{

            destination = itineraryInfo.getString("destination");
        } catch (JSONException e){

            e.printStackTrace();
        }

        return destination;
    }

    public static String getTripCode(){

        String tripCode = null;

        try{

            tripCode= itineraryInfo.getString("tripCode");
        } catch (JSONException e){

            e.printStackTrace();
        }

        return tripCode;

    }

    public static String getStartDate(){

        String startDate = null;

        try{

            startDate= itineraryInfo.getString("start date");
        } catch (JSONException e){

            e.printStackTrace();
        }

        return startDate;

    }

    public static String getEndDate(){

        String endDate = null;

        try{

            endDate= itineraryInfo.getString("end date");
        } catch (JSONException e){

            e.printStackTrace();
        }

        return endDate;

    }
}
