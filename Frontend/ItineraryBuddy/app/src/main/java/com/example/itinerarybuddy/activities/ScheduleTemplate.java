package com.example.itinerarybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.ScheduleItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The ScheduleTemplate class represents the activity for managing and displaying the schedule template.
 * It allows users to input and save schedule data for a specific day.
 */
public class ScheduleTemplate extends AppCompatActivity {

    private boolean isFirstClick = true;
    private ScheduleAdapter adapter;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_template);

        // Retrieve the day title from the intent
        String day = getIntent().getStringExtra("TITLE");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Generate sample schedule data
        List<ScheduleItem> data = generateData();

        // Create and set the adapter for the RecyclerView
        adapter = new ScheduleAdapter(data, day);
        recyclerView.setAdapter(adapter);

        // Fetch schedule data from the server
        GET_schedule();

        // Set click listener for the save/update button
        FloatingActionButton btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<ScheduleItem> scheduleItems = adapter.getScheduleData();

                if(isFirstClick){
                    // Perform POST request to save schedule data
                    POST_schedule(day, scheduleItems);
                    Toast.makeText(ScheduleTemplate.this, "Data saved!", Toast.LENGTH_SHORT).show();
                }

                else{
                    // Perform PUT request to update schedule data
                    UPDATE_schedule(day, scheduleItems);
                    Toast.makeText(ScheduleTemplate.this, "Data updated!", Toast.LENGTH_SHORT).show();
                }

                isFirstClick = false;
            }
        });

    }

    /**
     * Sends a POST request to save schedule data for the specified day.
     *
     * @param day The day for which the schedule data is being saved.
     * @param scheduleData The list of schedule items to be saved.
     */
    private void POST_schedule(String day, List<ScheduleItem> scheduleData){

        // String url = "http://coms-309-035.class.las.iastate.edu:8080/Schedule/Post";
        String url = "https://7557e865-ef05-4e77-beaf-a69fca370355.mock.pstmn.io/Schedule/Post" + day;
        RequestQueue queue = Volley.newRequestQueue(this);

        //Convert ScheduleItem list to JSONArray

        JSONArray jsonArray = new JSONArray();

        for(ScheduleItem item:scheduleData){

            JSONObject jsonItem = new JSONObject();

            try{

                jsonItem.put("time", item.getTime());
                jsonItem.put("place", item.getPlaces());
                jsonItem.put("note", item.getNotes());

                jsonArray.put(jsonItem);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        //Request Body
        JSONObject requestBody = new JSONObject();
        try{

            requestBody.put("scheduleData", jsonArray);
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        //JsonObjectRequest for the POST request
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(ScheduleTemplate.this, "Data saved!", Toast.LENGTH_SHORT).show();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ScheduleTemplate.this, "Error saving data", Toast.LENGTH_SHORT).show();
                    }
                }){

            public Map<String, String> getHeaders(){

                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        //Add JsonObjectRequest to the request queue
        queue.add(jsonObject);

    }


    /**
     * Sends a PUT request to update schedule data for the specified day.
     *
     * @param day The day for which the schedule data is being updated.
     * @param scheduleData The list of schedule items to be updated.
     */
    private void UPDATE_schedule(String day, List<ScheduleItem> scheduleData){

        // String url = "http://coms-309-035.class.las.iastate.edu:8080/Schedule/Update";
        String url = "https://7557e865-ef05-4e77-beaf-a69fca370355.mock.pstmn.io/Schedule/Update" + day;
        RequestQueue queue = Volley.newRequestQueue(this);

        //Convert ScheduleItem list to JSONArray
        JSONArray jsonArray = new JSONArray();

        for(ScheduleItem item : scheduleData){

            JSONObject jsonItem = new JSONObject();

            try{

                jsonItem.put("time", item.getTime());
                jsonItem.put("place", item.getPlaces());
                jsonItem.put("note", item.getNotes());

                jsonArray.put(jsonItem);
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }

        //Request body
        JSONObject requestBody = new JSONObject();

        try{

            requestBody.put("scheduleData", jsonArray);
        }catch(JSONException e){
            e.printStackTrace();
        }

        //JsonObjectRequest for the UPDATE
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ScheduleTemplate.this, "Data updated!", Toast.LENGTH_SHORT).show();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ScheduleTemplate.this, "Error updating data", Toast.LENGTH_SHORT).show();
                    }
                }){

            public Map<String, String> getHeaders(){

                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(jsonObject);
    }

    /**
     * Retrieves schedule data from the server using a GET request.
     * Parses the JSON response and updates the RecyclerView with the fetched data.
     */
    private void GET_schedule(){

        // String url = "http://coms-309-035.class.las.iastate.edu:8080/Schedule/Get";
        String url = "https://7557e865-ef05-4e77-beaf-a69fca370355.mock.pstmn.io/Schedule/Get";

        // Initialize a RequestQueue for the Volley library
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a JsonObjectRequest for the GET request
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray scheduleDataArray = response.getJSONArray("scheduleData");
                            List<ScheduleItem> scheduleItems = new ArrayList<>();

                            for (int i = 0; i < scheduleDataArray.length(); i++) {
                                JSONObject scheduleItemJson = scheduleDataArray.getJSONObject(i);

                                //Extract properties from JSON and create scheduleItem
                                String timeString = scheduleItemJson.getString("time");
                                String places = scheduleItemJson.getString("place");
                                String notes = scheduleItemJson.getString("note");

                                //Parse time string to Time object
                                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                Date parsedDate = dateFormat.parse(timeString);
                                Time time = new Time(parsedDate.getTime());


                                ScheduleItem scheduleItem = new ScheduleItem();
                                scheduleItem.setTime(time);
                                scheduleItem.setPlaces(places);
                                scheduleItem.setNotes(notes);

                                scheduleItems.add(scheduleItem);
                            }

                            /*RecyclerView recyclerView = findViewById(R.id.recyclerView);
                            ScheduleAdapter adapter = new ScheduleAdapter(scheduleItems);
                            recyclerView.setAdapter(adapter);*/

                            adapter.prependData(scheduleItems);

                            Toast.makeText(ScheduleTemplate.this, "Data fetched!", Toast.LENGTH_SHORT).show();
                        } catch (JSONException|ParseException e) {
                            Toast.makeText(ScheduleTemplate.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Log.e("Volley Error", "HTTP Status Code: " + statusCode);
                        }
                        Toast.makeText(ScheduleTemplate.this, "Error fetching data", Toast.LENGTH_SHORT).show();

                    }
                });

        queue.add(jsonObject);
    }

    /**
     * Generates dummy schedule data for testing purposes.
     *
     * @return A list of ScheduleItem objects containing dummy data.
     */
    private List<ScheduleItem> generateData(){

        List<ScheduleItem> data = new ArrayList<>();

        for(int i = 0; i <= 50; i++){

            ScheduleItem item = new ScheduleItem();
            data.add(item);
        }

        return data;
    }
}


