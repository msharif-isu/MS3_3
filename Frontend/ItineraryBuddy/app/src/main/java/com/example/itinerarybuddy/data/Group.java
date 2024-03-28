package com.example.itinerarybuddy.data;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/** Represents a travel group object that the user may be a member of. All fields are final because the group objects are all recreated upon modification. */
public class Group {

    /**
     * The name for the travel group.
     */
    private final String travelGroupName;

    /**
     * The ID for the travel group.
     */
    private final String travelGroupID;

    /**
     * The destination for the travel group.
     */
    private final String travelGroupDestination;

    /**
     * The description for the travel group.
     */
    private final String travelGroupDescription;

    /**
     * The creator of the travel group.
     */
    private final String travelGroupAmbassador;

    /**
     * The members of the travel group.
     */
    private final ArrayList<String> members;

    /**
     * The constructor for a travel group.
     * @param name of the travel group.
     * @param id of the travel group.
     * @param destination of the travel group.
     * @param description of the travel group.
     * @param travelGroupAmbassador of the travel group.
     * @param members of the travel group.
     */
    public Group(String name, String id, String destination, String description, String travelGroupAmbassador, ArrayList<String> members) {
        this.travelGroupName = name;
        this.travelGroupID = id;
        this.travelGroupDestination = destination;
        this.travelGroupDescription = description;
        this.travelGroupAmbassador = travelGroupAmbassador;
        this.members = members;
    }

    /**
     * Getter for the group name.
     * @return travelGroupName.
     */
    public String getTravelGroupName() {
        return travelGroupName;
    }

    /**
     * Getter for the group ID.
     * @return travelGroupID.
     */
    public String getTravelGroupID() {
        return travelGroupID;
    }

    /**
     * Getter for the group destination.
     * @return travelGroupDestination.
     */
    public String getTravelGroupDestination() {
        return travelGroupDestination;
    }

    /**
     * Getter for the group description.
     * @return travelGroupDescription.
     */
    public String getTravelGroupDescription() {
        return travelGroupDescription;
    }

    /**
     * Getter for the group ambassador.
     * @return travelGroupAmbassador.
     */
    public String getTravelGroupAmbassador() {
        return travelGroupAmbassador;
    }

    /**
     * Getter for the group members.
     * @return members.
     */
    public ArrayList<String> getMembers() {
        return members;
    }

    /**
     * The string representation of the group for the list adapter.
     * @return group details.
     */
    @NonNull
    @Override
    public String toString() {
        return travelGroupName + "\nDestination: " + travelGroupDestination;
    }

    /**
     * Static method to parse the group JSON for the group name.
     * @param json containing group data.
     * @return travelGroupName from the passed in JSON.
     */
    public static String getGroupName(JSONObject json){
        String groupName = null;
        try{
            groupName = json.getString("travelGroupName");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupName;
    }

    /**
     * Static method to parse the group JSON for the group ID.
     * @param json containing group data.
     * @return travelGroupId from the passed in JSON.
     */
    public static String getGroupID(JSONObject json){
        String groupCode = null;
        try{
            groupCode = json.getString("travelGroupId");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupCode;
    }

    /**
     * Static method to parse the group JSON for the group destination.
     * @param json containing group data.
     * @return travelGroupDestination from the passed in JSON.
     */
    public static String getGroupDestination(JSONObject json){
        String groupDestination = null;
        try{
            groupDestination = json.getString("travelGroupDestination");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupDestination;
    }

    /**
     * Static method to parse the group JSON for the group description.
     * @param json containing group data.
     * @return travelGroupDescription from the passed in JSON.
     */
    public static String getGroupDescription(JSONObject json){
        String groupDescription = null;
        try{
            groupDescription = json.getString("travelGroupDescription");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupDescription;
    }

    /**
     * Static method to parse the group JSON for the group ambassador.
     * @param json containing group data.
     * @return travelGroupAmbassador from the passed in JSON.
     */
    public static String getGroupAmbassador(JSONObject json){
        String GroupAmbassador = null;
        try{
            GroupAmbassador = json.getString("travelGroupAmbassador");
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return GroupAmbassador;
    }

    /**
     * Static method to parse the group JSON for the group members.
     * @param json containing group data.
     * @return list of members from the passed in JSON.
     */
    public static ArrayList<String> getGroupMembers(JSONObject json){
        ArrayList<String> groupMembers = new ArrayList<String>();
        try{
            JSONArray array = json.getJSONArray("members");
            for(int i = 0; i < array.length(); i++){
                groupMembers.add(array.getJSONObject(i).getString("userName"));
            }
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }
        return groupMembers;
    }
}