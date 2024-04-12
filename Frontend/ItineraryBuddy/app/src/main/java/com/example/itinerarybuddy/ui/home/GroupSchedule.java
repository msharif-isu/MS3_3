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
import com.example.itinerarybuddy.activities.ScheduleTemplate;
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


/**
 * The ScheduleTemplate class represents the activity for managing and displaying the schedule template.
 * It allows users to input and save schedule data for a specific day.
 */
public class GroupSchedule extends AppCompatActivity {

    private boolean isEditable;
    private String tripCode;
    private boolean isFirstClick = true;
    private ScheduleAdapter adapter;

    private String day;

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
        day = getIntent().getStringExtra("TITLE");
        isEditable = getIntent().getBooleanExtra("IS_EDITABLE", false);
        tripCode = getIntent().getStringExtra("TRIPCODE");


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<ScheduleItem> list = new ArrayList<>(50);
        for(int i = 0; i < 50; i++){
            list.add(new ScheduleItem());
        }

        adapter = new ScheduleAdapter(list ,day, isEditable);
        recyclerView.setAdapter(adapter);

        getSchedule();

        // Set click listener for the save/update button
        FloatingActionButton btnSaveUpdate = findViewById(R.id.btnSaveUpdate);

        if (!isEditable) {
            btnSaveUpdate.setVisibility(View.INVISIBLE);
        }
        else {
            btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    putSchedule();
                }
            });
        }

    }

    private void putSchedule() {

    }

    private void getSchedule() {
        String url = "https://443da8f0-75e2-4be2-8e84-834c5d63eda6.mock.pstmn.io/schedule?id=1"; //TODO
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray scheduleDataArray = response.getJSONArray("scheduleData");
                            List<ScheduleItem> scheduleItems = new ArrayList<>();

                            for (int i = 0; i < scheduleDataArray.length(); i++) {
                                JSONObject scheduleItemJson = scheduleDataArray.getJSONObject(i);

                                String timeString = scheduleItemJson.getString("time");
                                String places = scheduleItemJson.getString("place");
                                String notes = scheduleItemJson.getString("note");

                                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                                Date parsedDate = dateFormat.parse(timeString);
                                Time time = new Time(parsedDate.getTime());

                                ScheduleItem scheduleItem = new ScheduleItem();
                                scheduleItem.setTime(time);
                                scheduleItem.setPlaces(places);
                                scheduleItem.setNotes(notes);

                                scheduleItems.add(scheduleItem);
                            }

                            adapter.prependData(scheduleItems);

                        } catch (JSONException | ParseException e) {
                            Log.e("JSON Exception: ", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                        Log.e("Volley Error", error.toString());
                    }
                });
        Singleton.getInstance(getApplicationContext()).addRequest(request);
    }
}


