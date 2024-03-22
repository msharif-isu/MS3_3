package com.example.itinerarybuddy.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;

/** This class stores a static JSON object for the current app user. Includes getters for data. */
public class UserData {

    /** JSON object to be used in the various activities. */
    public static JSONObject userInfo;

    public static ArrayAdapter<Group> adapter;

    public static RequestQueue queue;

    /** Extract the username from the stored JSON. */
    public static String getUsername(){
        String username = null;
        try{
           username = userInfo.getString("userName");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return username;
    }

    /** Extract the username from the stored JSON. */
    public static String getEmail(){
        String email = null;
        try{
            email = userInfo.getString("email");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
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

    /** Extract the city from the stored JSON. */
    public static String getCity(){
        String city = null;
        try{
            city = userInfo.getString("city");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return city;
    }

    /** Extract the username from the stored JSON. */
    public static String getState(){
        String state = null;
        try{
            state = userInfo.getString("username");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return state;
    }

    /** Returns a list of groups the user is in. */
    public static ArrayList<String> getGroupIds(){
        ArrayList<String> groupIDs = new ArrayList<String>();
        try{
            JSONArray array = userInfo.getJSONArray("groups");
            for(int i = 0; i < array.length(); i++){
                groupIDs.add(array.getJSONObject(i).getString("travelGroupCode"));
            }
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupIDs;
    }

    public static void initializeGroups(ArrayAdapter<Group> a){
        UserData.adapter = a;
        ArrayList<String> ids = UserData.getGroupIds();
        String url;
        String id;
        for(int i = 0; i < ids.size(); i++){
            id = ids.get(i);
            url = "https://443da8f0-75e2-4be2-8e84-834c5d63eda6.mock.pstmn.io/group?travelGroupCode=" + id; //TODO: fix url.
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Volley Response: ", response.toString());

                    UserData.appendAdapter(response);
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

    public static void appendAdapter(JSONObject response){
        String groupName = getGroupName(response);
        String groupCode = getGroupCode(response);
        String groupDestination = getGroupDestination(response);
        String groupDescription = getGroupDescription(response);
        ArrayList<String> members = getGroupMembers(response);
        Group g = new Group(groupName, groupCode, groupDestination, groupDescription, members);
        UserData.adapter.add(g);
    }

    public static String getGroupName(JSONObject json){
        String groupName = null;
        try{
            groupName = json.getString("travelGroupName");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupName;
    }

    public static String getGroupCode(JSONObject json){
        String groupCode = null;
        try{
            groupCode = json.getString("travelGroupCode");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupCode;
    }

    public static String getGroupDestination(JSONObject json){
        String groupDestination = null;
        try{
            groupDestination = json.getString("travelGroupDestination");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupDestination;
    }

    public static String getGroupDescription(JSONObject json){
        String groupDescription = null;
        try{
            groupDescription = json.getString("travelGroupDescription");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupDescription;
    }

    public static ArrayList<String> getGroupMembers(JSONObject json){
        ArrayList<String> groupMembers = new ArrayList<String>();
        try{
            JSONArray array = json.getJSONArray("travelGroupMembers");
            for(int i = 0; i < array.length(); i++){
                groupMembers.add(array.getJSONObject(i).getString("userName"));
            }
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupMembers;
    }

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
