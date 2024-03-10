package com.example.itinerarybuddy.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
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

    //public static ArrayList<Group> groups;

    public static RequestQueue queue;

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
                groupIDs.add(array.getJSONObject(i).getString("travelGroupCode"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupIDs;
    }

    public static ArrayList<Group> getGroups(){
        ArrayList<Group> groups = new ArrayList<Group>();
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
                    String groupName = getGroupName(response);
                    String groupCode = getGroupCode(response);
                    String groupDestination = getGroupDestination(response);
                    String groupDescription = getGroupDescription(response);
                    groups.add(new Group(groupName, groupCode, groupDestination, groupDescription));
                    //Log.d(groups.get(groups.size()-1).toString(), response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error: ", error.toString());
                }
            });
            queue.add(req);
            //Log.d("Group: ", groups.get(i).toString());
        }
        Log.d("Group: ", Integer.toString(groups.size()));
        return groups;
    }

    public static String getGroupName(JSONObject json){
        String groupName = null;
        try{
            groupName = json.getString("travelGroupName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupName;
    }

    public static String getGroupCode(JSONObject json){
        String groupCode = null;
        try{
            groupCode = json.getString("travelGroupCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupCode;
    }

    public static String getGroupDestination(JSONObject json){
        String groupDestination = null;
        try{
            groupDestination = json.getString("travelGroupDestination");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupDestination;
    }

    public static String getGroupDescription(JSONObject json){
        String groupDescription = null;
        try{
            groupDescription = json.getString("travelGroupDescription");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupDescription;
    }
}
