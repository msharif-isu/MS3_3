package com.example.itinerarybuddy.data;

import androidx.annotation.NonNull;

/** Stores a list of JSON objects for a user. */
public class Group {

    public String travelGroupName;

    public String travelGroupCode;

    public String travelGroupDestination;

    public String travelGroupDescription;

    public Group(String name, String code, String destination, String description){
        this.travelGroupName = name;
        this.travelGroupCode = code;
        this.travelGroupDestination = destination;
        this.travelGroupDescription = description;
    }

    public String getTravelGroupName() {
        return travelGroupName;
    }

    public void setTravelGroupName(String travelGroupName) {
        this.travelGroupName = travelGroupName;
    }

    public String getTravelGroupCode() {
        return travelGroupCode;
    }

    public void setTravelGroupCode(String travelGroupCode) {
        this.travelGroupCode = travelGroupCode;
    }

    public String getTravelGroupDestination() {
        return travelGroupDestination;
    }

    public void setTravelGroupDestination(String travelGroupDestination) {
        this.travelGroupDestination = travelGroupDestination;
    }

    public String getTravelGroupDescription() {
        return travelGroupDescription;
    }

    public void setTravelGroupDescription(String travelGroupDescription) {
        this.travelGroupDescription = travelGroupDescription;
    }

    @NonNull
    @Override
    public String toString(){
        return travelGroupName;
    }
/*
    public static ArrayList<Integer> groups;

    public static RequestQueue queue;

    public static void InitializeGroups(){
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
                    GroupData.groups.add(1);
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


    public static String getGroupID(int index){
        String id = null;
        try{
            id = GroupData.groups.get(index).getString("travelGroupCode");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return id;
    }


    public static String getGroupName(int index){
        String name = null;
        try{
            name = GroupData.groups.get(index).getString("travelGroupName");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return name;
    }


    public static String getGroupDestination(int index){
        String destination = null;
        try{
            destination = GroupData.groups.get(index).getString("travelGroupDestination");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return destination;
    }


    public static String getGroupDescription(int index){
        String description = null;
        try{
            description = GroupData.groups.get(index).getString("travelGroupDescription");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return description;
    }


    public static ArrayList<String> getMembers(int index){
        //TODO- implement json array processing

        ArrayList<String> members = new ArrayList<String>();
        try{

        }catch(JSONException e){
            e.printStackTrace();
        }
        return members;

        return null;
    }


    public static JSONObject getChat(int index){
        //TODO (future work)

        try{

        }catch(JSONException e){
            e.printStackTrace();
        }
        return members;

        return null;
    }


    public static String getGroupImageUrl(int index){
        String url = null;
        try{
            url = groups.get(index).getString("groupDescription");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return url;
    }
    */
}
