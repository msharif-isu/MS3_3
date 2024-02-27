package com.example.itinerarybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itinerarybuddy.data.ScheduleItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
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

    private boolean isFirstClick = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_template);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<ScheduleItem> data = generateData();
        ScheduleAdapter adapter = new ScheduleAdapter(data);
        recyclerView.setAdapter(adapter);


        FloatingActionButton btnSaveUpdate = findViewById(R.id.btnSaveUpdate);

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<ScheduleItem> scheduleItems = adapter.getScheduleData();

                if(isFirstClick){

                    POST_schedule(scheduleItems);
                    Toast.makeText(ScheduleTemplate.this, "Data saved!", Toast.LENGTH_SHORT).show();
                }

                else{

                    UPDATE_schedule(scheduleItems);
                    Toast.makeText(ScheduleTemplate.this, "Data updated!", Toast.LENGTH_SHORT).show();
                }

                isFirstClick = false;
            }
        });
    }

    private void POST_schedule(List<ScheduleItem> scheduleData){

        String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Schedule/Post";
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

    private void UPDATE_schedule(List<ScheduleItem> scheduleData){

        String url = "https://c1ae4a21-9cb6-4e77-a7a2-a5d5066df0d7.mock.pstmn.io/Schedule/Update";
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

    private List<ScheduleItem> generateData(){

        List<ScheduleItem> data = new ArrayList<>();

        for(int i = 0; i <= 250; i++){

            ScheduleItem item = new ScheduleItem();
            data.add(item);
        }

        return data;
    }
}


