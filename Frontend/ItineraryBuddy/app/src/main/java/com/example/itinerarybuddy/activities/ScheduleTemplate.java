package com.example.itinerarybuddy.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Itinerary;
import com.example.itinerarybuddy.data.ScheduleItem;
import com.example.itinerarybuddy.ui.home.HomeFragment;
import com.example.itinerarybuddy.util.Singleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * The ScheduleTemplate class represents the activity for managing and displaying the schedule template.
 * It allows users to input and save schedule data for a specific day.
 */
public class ScheduleTemplate extends AppCompatActivity {

    private boolean isEditable;
    private int itinerary_ID;
    private boolean isFirstClick = true;
    private int dayInt;
    private String dayString;
    private ScheduleAdapter adapter;

    private Itinerary selectedItinerary;

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
        dayInt = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("TITLE")).substring(4));
        dayString = getIntent().getStringExtra("TITLE");
        isEditable = getIntent().getBooleanExtra("IS_EDITABLE", true);
        selectedItinerary = getIntent().getParcelableExtra("SELECTED_ITINERARY");

        itinerary_ID = selectedItinerary.getItineraryID();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Generate sample schedule data
        List<ScheduleItem> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new ScheduleItem());
        }

        adapter = new ScheduleAdapter(list, dayString, isEditable);
        recyclerView.setAdapter(adapter);

        try {
            GET_schedule();
        } catch (JSONException | ParseException e) {
            Log.e("Error fetching schedule: ", e.toString());
        }


        // Set click listener for the save/update button
        FloatingActionButton btnSaveUpdate = findViewById(R.id.btnSaveUpdate);


        if (!isEditable) {

            btnSaveUpdate.setVisibility(View.INVISIBLE);
        } else {
            btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        PUT_schedule();
                    } catch (JSONException | ParseException e) {
                        Log.e("JSON Exception: ", e.toString());
                    }
                }
            });


        }

    }

    private void PUT_schedule() throws JSONException, ParseException {
        List<ScheduleItem> data = adapter.getScheduleData();
        for(ScheduleItem s: data){
            if(s.getPlaces() != null)
                Log.d("Data", s.getPlaces());
        }

        JSONArray events =  HomeFragment.personalItinerary.getJSONArray(" ");
        for(int i = events.length() - 1; i >= 0; i--){
            if(events.getJSONObject(i).getInt("dayNumber") == dayInt){
                events.remove(i);
            }
        }
        Log.d("Events:", events.toString());

        int index = events.length() - 1;
        for(int i = 0; i < events.length(); i++){
            if(events.getJSONObject(i).getInt("dayNumber") > dayInt){
                index = i;
                break;
            }
        }

        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getTime() != null && data.get(i).getPlaces() != null && data.get(i).getNotes() != null) {
                if(!data.get(i).getPlaces().isEmpty() && !data.get(i).getNotes().isEmpty()){
                    JSONObject item = new JSONObject();
                    item.put("dayNumber", dayInt);
                    item.put("time", data.get(i).getTime().toString());
                    item.put("place", data.get(i).getPlaces());
                    item.put("notes", data.get(i).getNotes());

                    events.put(item);
                }
            }
        }
        Log.d("Events:", events.toString());

        JSONObject oldItinerary =  HomeFragment.personalItinerary;
        JSONObject newItinerary = new JSONObject();
        newItinerary.put("itineraryName", oldItinerary.getString("itineraryName"));
        newItinerary.put("startDate", oldItinerary.getString("startDate"));
        newItinerary.put("endDate", oldItinerary.getString("endDate"));
        newItinerary.put("numDays", oldItinerary.getString("numDays"));
        newItinerary.put("personalItineraryEventsList", events);
        newItinerary.put("startDate", oldItinerary.getString("startDate"));
        Log.d("Itinerary:", newItinerary.toString());

        final String url = "http://coms-309-035.class.las.iastate.edu:8080/Personal/Itinerary/" + selectedItinerary.getItineraryID() ; //TODO
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, newItinerary, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("Volley Response:", jsonObject.toString());
                HomeFragment.personalItinerary = jsonObject;
                Toast.makeText(getApplicationContext(), "Schedule Updated", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Volley Error:", volleyError.toString());
                Toast.makeText(getApplicationContext(), "Error Updating Data", Toast.LENGTH_LONG).show();
            }
        });
        Singleton.getInstance(getApplicationContext()).addRequest(request);
    }
    /**
         * Retrieves schedule data from the server using a GET request.
         * Parses the JSON response and updates the RecyclerView with the fetched data.
         */

    private void GET_schedule() throws JSONException, ParseException {
        JSONArray schedule = HomeFragment.personalItinerary.getJSONArray("personalItineraryEventsList");
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        for (int i = 0; i < schedule.length(); i++) {
            JSONObject event = schedule.getJSONObject(i);
            Log.d("Event:", event.toString());
            if(event.getString("dayNumber").equals(Integer.toString(dayInt))){
                String timeString = event.getString("time");
                String places = event.getString("place");
                String notes = event.getString("notes");

                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
                Date parsedDate = dateFormat.parse(timeString);
                assert parsedDate != null;
                Time time = new Time(parsedDate.getTime());

                ScheduleItem scheduleItem = new ScheduleItem();
                scheduleItem.setDay(dayInt);
                scheduleItem.setTime(time);
                scheduleItem.setPlaces(places);
                scheduleItem.setNotes(notes);

                scheduleItems.add(scheduleItem);
            }
        }

        adapter.prependData(scheduleItems);
    }

}


