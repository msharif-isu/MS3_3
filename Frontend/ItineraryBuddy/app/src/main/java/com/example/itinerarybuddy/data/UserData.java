package com.example.itinerarybuddy.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/** This class stores a static JSON object for the current app user. Includes getters for data. */
public class UserData {

    /** JSON object to be used in the various activities. */
    public static JSONObject userInfo;

    /** Extract the username from the stored JSON. */
    public static String getUsername(){
        String username = null;
        try{
           username = userInfo.getString("userName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return username;
    }

    /** Extract the username from the stored JSON. */
    public static String getEmail(){
        String email = null;
        try{
            email = userInfo.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return email;
    }

    /** Extract the usertype from the stored JSON.
     * "user", "ambassador", "admin"
     */
    public static String getUsertype(){
        String usertype = null;
        try{
            usertype = userInfo.getString("userType");
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

    /** Returns a list of groups the user is in. */
    public static ArrayList<String> getGroupIds(){
        ArrayList<String> groupIDs = new ArrayList<String>();
        try{
            JSONArray array = userInfo.getJSONArray("groups");
            for(int i = 0; i < array.length(); i++){
                groupIDs.add(array.getJSONObject(i).getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupIDs;
    }
}
