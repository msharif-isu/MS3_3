package com.example.itinerarybuddy.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/** This class stores a static JSON object for the current app user. Includes getters for data. */
public class UserData {

    /** JSON object to be used in the various activities. */
    public static JSONObject userInfo;

    public static RequestQueue queue;

    /** Extract the username from the stored JSON. */
  /*  public static String getUsername(){
        String username = null;
        try{
           username = userInfo.getString("userName");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return username;
    }*/

    public static String getUsername() {
        String username = null;
        if (userInfo != null) {
            try {
                username = userInfo.getString("userName");
            } catch (JSONException e) {
                Log.e("Error: ", e.toString());
            }
        } else {
            Log.e("Error: ", "userInfo is null");
        }
        return username;
    }


    /** Extract the username from the stored JSON. */
  /*  public static String getEmail(){
        String email = null;
        try{
            email = userInfo.getString("email");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return email;
    }*/

    public static String getEmail() {
        String email = null;
        if (userInfo != null) { // Assuming userInfo is your JSONObject
            try {
                email = userInfo.getString("email");
            } catch (JSONException e) {
                Log.e("Error: ", e.toString());
            }
        } else {
            Log.e("Error: ", "userInfo is null");
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
            Log.e("Error: ", e.toString());
        }
        return usertype;
    }

    /** Returns a list of groups the user is in. */
    public static ArrayList<String> getGroupIds(){
        ArrayList<String> groupIDs = new ArrayList<String>();
        try{
            JSONArray array = userInfo.getJSONArray("userCodes");
            for(int i = 0; i < array.length(); i++){
                groupIDs.add(array.getString(i));
                Log.d("Group found: ", array.getString(i));
            }
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupIDs;
    }

    /**
     * Used to make a request to the server to update the current user's data by resetting the userInfo field.
     */
    public static void updateUserData(){
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Users/" + getUsername();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());

                userInfo = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error: ", error.toString());
            }
        });
        queue.add(req);
    }
}
