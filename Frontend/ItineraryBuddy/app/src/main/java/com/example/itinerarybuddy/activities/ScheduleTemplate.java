package com.example.itinerarybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class ScheduleTemplate extends AppCompatActivity {

    private boolean isEditable;
    private boolean isFirstClick = true;
    private ScheduleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_template);

        String day = getIntent().getStringExtra("TITLE");
        isEditable = getIntent().getBooleanExtra("IS_EDITABLE", true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<ScheduleItem> data = generateData();

        adapter = new ScheduleAdapter(data, day, isEditable);
        recyclerView.setAdapter(adapter);

        GET_schedule(day);

        FloatingActionButton btnSaveUpdate = findViewById(R.id.btnSaveUpdate);

        if(!isEditable){

            btnSaveUpdate.setVisibility(View.INVISIBLE);

        }

        else {
            btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    List<ScheduleItem> scheduleItems = adapter.getScheduleData();

                    if (isFirstClick) {

                        POST_schedule(day, scheduleItems);
                        Toast.makeText(ScheduleTemplate.this, "Data saved!", Toast.LENGTH_SHORT).show();
                    } else {

                        PUT_schedule(day, scheduleItems);
                        Toast.makeText(ScheduleTemplate.this, "Data updated!", Toast.LENGTH_SHORT).show();
                    }

                    isFirstClick = false;
                }
            });

        }

    }

    //POST the schedule datagit add .
    
    private void POST_schedule(String day, List<ScheduleItem> scheduleData){

        String day_url = day.replace(" ", "");
        // String url = "http://coms-309-035.class.las.iastate.edu:8080/Schedule/" + username + tripCode + day_url;

        String url = "https://7557e865-ef05-4e77-beaf-a69fca370355.mock.pstmn.io/Schedule/Post/" + day_url;
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

    //UPDATE the schedule data
    private void PUT_schedule(String day, List<ScheduleItem> scheduleData){

        String day_url = day.replace(" ", "");

        // String url = "http://coms-309-035.class.las.iastate.edu:8080/Schedule/" + username + tripCode + day_url;
        String url = "https://7557e865-ef05-4e77-beaf-a69fca370355.mock.pstmn.io/Schedule/Update/" + day_url;
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

    //GET the schedule data from the json file
    private void GET_schedule(String day){

        String day_url = day.replace(" ", "");

        //String url = "http://coms-309-035.class.las.iastate.edu:8080/Schedule/" + username + tripCode + day_url;
        String url = "https://7557e865-ef05-4e77-beaf-a69fca370355.mock.pstmn.io/Schedule/Get/" + day_url;

        RequestQueue queue = Volley.newRequestQueue(this);

        //JsonObjectRequest for the GET request
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

    private List<ScheduleItem> generateData(){

        List<ScheduleItem> data = new ArrayList<>();

        for(int i = 0; i <= 50; i++){

            ScheduleItem item = new ScheduleItem();
            data.add(item);
        }

        return data;
    }
}


