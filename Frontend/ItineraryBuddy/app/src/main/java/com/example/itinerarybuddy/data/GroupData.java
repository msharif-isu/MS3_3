package com.example.itinerarybuddy.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/** Stores a list of JSON objects for a user. */
public class GroupData {

    public static ArrayList<JSONObject> groups;

    private static RequestQueue queue;

    /** Initializes the list of groups with the JSON for each group the user is in. */
    public static void InitializeGroups(){
        groups = new ArrayList<>();
        ArrayList<String> ids = UserData.getGroupIds();
        String url;
        String id;
        for(int i = 0; i < ids.size(); i++){
            id = ids.get(i);
            url = "http://coms-309-035.class.las.iastate.edu:8080/"; //TODO: fix url.
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Volley Response: ", response.toString());
                    groups.add(response);
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

    /** Get the group's id given the index passed for the list. */
    public static String getGroupID(int index){
        String id = null;
        try{
            id = groups.get(index).getString("id");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return id;
    }

    /** Get the group's name given the index passed for the list. */
    public static String getGroupName(int index){
        String name = null;
        try{
            name = groups.get(index).getString("groupName");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return name;
    }

    /** Get the group's name given the index passed for the list. */
    public static String getGroupDestination(int index){
        String destination = null;
        try{
            destination = groups.get(index).getString("groupName");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return destination;
    }

    /** Get the group's description given the index passed for the list. */
    public static String getGroupDescription(int index){
        String description = null;
        try{
            description = groups.get(index).getString("groupDescription");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return description;
    }

    /** Get the group members given the index passed for the list. */
    public static ArrayList<String> getMembers(int index){
        //TODO- implement json array processing
        /*
        ArrayList<String> members = new ArrayList<String>();
        try{

        }catch(JSONException e){
            e.printStackTrace();
        }
        return members;
         */
        return null;
    }

    /** Get the group chat data given the index passed for the list. */
    public static JSONObject getChat(int index){
        //TODO (future work)
        /*
        try{

        }catch(JSONException e){
            e.printStackTrace();
        }
        return members;
         */
        return null;
    }

    /** Get the url for the group image. */
    public static String getGroupImageUrl(int index){
        String url = null;
        try{
            url = groups.get(index).getString("groupDescription");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return url;
    }
}
