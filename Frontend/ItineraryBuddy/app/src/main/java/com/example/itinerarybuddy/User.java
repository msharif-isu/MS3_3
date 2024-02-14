package com.example.itinerarybuddy;

import com.android.volley.RequestQueue;
import org.json.JSONException;
import org.json.JSONObject;

/** This class stores a static JSON object for the current app user. Includes getters for data. */
public class User {

    /** JSON object to be used in the various activities. */
    protected static JSONObject userInfo;

    /** Queue for pushing JSON requests. */
    protected static RequestQueue requestQueue;

    /** Extract the username from the stored JSON. */
    public static String getUsername(){
        String username = null;
        try{
           username = userInfo.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return username;
    }

    /** Extract the usertype from the stored JSON. */
    public static String getUsertype(){
        String usertype = null;
        try{
            usertype = userInfo.getString("usertype");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usertype;
    }

    /** Extract the city from the stored JSON. */
    public static String getCity(){
        String city = null;
        try{
            city = userInfo.getString("city");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return city;
    }

    /** Extract the username from the stored JSON. */
    public static String getState(){
        String state = null;
        try{
            state = userInfo.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return state;
    }
}
