package com.example.itinerarybuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/** Control panel where an admin will be able to moderate activity. */
public class AdminDashboardActivity extends AppCompatActivity {

    private EditText input1;

    private EditText input2;

    private EditText input3;

    private EditText input4;

    private Button button1;

    private Button button2;

    private Button button3;

    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_activity);

        input1 = findViewById(R.id.id_input1);
        input2 = findViewById(R.id.id_input2);
        input3 = findViewById(R.id.id_input3);
        input4 = findViewById(R.id.id_input4);
        button1 = findViewById(R.id.confirm1);
        button2 = findViewById(R.id.confirm2);
        button3 = findViewById(R.id.confirm3);
        button4 = findViewById(R.id.confirm4);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = input1.getText().toString();
                updateData(id, "\"usertype\": \"user\"");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /** Update the users data given the admin dashboard commands. */
    private void updateData(String id, String change) {
        String url = "";
        String jsonString = "{\"userID\": " + id + "," + change + "}";
        JSONObject json;
        try {
            //Make the json object to PUT
            json = new JSONObject(jsonString);

            //Request to PUT the json object to the url
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            User.requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
