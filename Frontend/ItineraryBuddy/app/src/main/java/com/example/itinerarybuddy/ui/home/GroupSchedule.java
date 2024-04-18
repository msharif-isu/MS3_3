package com.example.itinerarybuddy.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.ScheduleAdapter;
import com.example.itinerarybuddy.data.ScheduleItem;
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
public class GroupSchedule extends AppCompatActivity {

    private boolean isEditable;
    private String tripCode;
    private boolean isFirstClick = true;
    private ScheduleAdapter adapter;

    /**
     * Contains the string title "Day x".
     */
    private String dayString;

    /**
     * Extracts the day as an int.
     */
    private int dayInt;

    private static JSONArray days;

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
        isEditable = getIntent().getBooleanExtra("IS_EDITABLE", false);
        tripCode = getIntent().getStringExtra("TRIPCODE");


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<ScheduleItem> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new ScheduleItem());
        }

        adapter = new ScheduleAdapter(list, dayString, isEditable);
        recyclerView.setAdapter(adapter);

        try {
            getSchedule(dayInt);
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
                        putSchedule();
                    } catch (JSONException e) {
                        Log.e("JSON Exception: ", e.toString());
                    }
                }
            });
        }

    }

    private void putSchedule() throws JSONException {
        List<ScheduleItem> data = adapter.getScheduleData();
        JSONObject schedule = new JSONObject();
        JSONArray newData = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getTime() != null && data.get(i).getPlaces() != null && data.get(i).getNotes() != null) {

                JSONObject item = new JSONObject();
                item.put("time", data.get(i).getTime().toString());
                item.put("place", data.get(i).getPlaces());
                item.put("note", data.get(i).getNotes());

                newData.put(item);
                Log.d("Schedule Item", newData.toString());
            }
        }
        schedule.put("scheduleData", newData);

        JSONObject newItinerary = new JSONObject();
        newItinerary.put("groupCode", LoadGroup.groupItinerary.get("groupCode"));

        //Add current schedule data to main json
        JSONArray days = new JSONArray();
        for(int i = 0; i < LoadGroup.groupItinerary.getJSONArray("days").length(); i++){
            if(i == dayInt - 1){
                days.put(schedule);
            }
            else{
                days.put(LoadGroup.groupItinerary.getJSONArray("days").getJSONObject(i));
            }
        }
        newItinerary.put("days", days);
        newItinerary.put("itineraryName", LoadGroup.groupItinerary.get("itineraryName"));
        newItinerary.put("startDate", LoadGroup.groupItinerary.get("startDate"));
        newItinerary.put("endDate", LoadGroup.groupItinerary.get("endDate"));
        newItinerary.put("numDays", LoadGroup.groupItinerary.get("itineraryName"));
        Log.d("JSON: ", newItinerary.toString());


        final String url = ""; //TODO
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, newItinerary, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("Volley Response:", jsonObject.toString());
                LoadGroup.groupItinerary = jsonObject;
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

    private void getSchedule(int day) throws JSONException, ParseException {
        days = LoadGroup.groupItinerary.getJSONArray("days");
        JSONObject scheduleDay = days.getJSONObject(day - 1);
        JSONArray dayItems = scheduleDay.getJSONArray("scheduleData");
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        for (int i = 0; i < dayItems.length(); i++) {
            JSONObject itineraryDay = dayItems.getJSONObject(i);

            String timeString = itineraryDay.getString("time");
            String places = itineraryDay.getString("place");
            String notes = itineraryDay.getString("note");

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
            Date parsedDate = dateFormat.parse(timeString);
            assert parsedDate != null;
            Time time = new Time(parsedDate.getTime());

            ScheduleItem scheduleItem = new ScheduleItem();
            scheduleItem.setTime(time);
            scheduleItem.setPlaces(places);
            scheduleItem.setNotes(notes);

            scheduleItems.add(scheduleItem);
        }

        adapter.prependData(scheduleItems);

        /*
        {
    "travelGroupItinerary": {
        "groupCode": 1,
        "days": [
            {
                "scheduleData": [
                    {
                        "time": "",
                        "place": "",
                        "note": ""
                    },
                    {
                        "time": "",
                        "place": "",
                        "note": ""
                    }
                ]
            },
            {
                "scheduleData": [
                    {
                        "time": "",
                        "place": "",
                        "note": ""
                    }
                ]
            }
        ],
        "itineraryName": "",
        "startDate": "",
        "endDate": "",
        "numDays": ""
    }
    */
    }
}



