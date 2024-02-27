package com.example.itinerarybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Control panel where an admin will be able to moderate activity.
 */
public class AdminDashboardActivity extends AppCompatActivity {

    private EditText input1;

    private EditText input2;

    private EditText input3;

    private EditText input4;

    private Button button1;

    private Button button2;

    private Button button3;

    private Button button4;

    private Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        buttonReturn = findViewById(R.id.return_home);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this, MainActivity.class));
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = input1.getText().toString();
                updateData(username, 1);
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

    /**
     * Update the users data given the admin dashboard commands.
     */
    private void updateData(String username, int change) {
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Users/" + username;

        JSONObject user = getUser(username);
        String email;
        String userName;
        String password;
        String state;
        String city;

        try {
            email = user.getString("email");
            userName = user.getString("userName");
            password = user.getString("password");
            state = user.getString("state");
            city = user.getString("city");

            String jsonString = null;
            if (change == 1) {
                jsonString = "{\n" +
                        "\"email\": \"" + email + "\",\n" +
                        "\"userName\": \"" + userName + "\",\n" +
                        "\"password\": \"" + password + "\",\n" +
                        "\"state\": \"" + state + "\",\n" +
                        "\"city\": \"" + city + "\",\n" +
                        "\"userType\": \"" + "User" + "\"\n" +
                        "}";
            }
            else if(change == 2){
                jsonString = "{\n" +
                        "\"email\": \"" + email + "\",\n" +
                        "\"userName\": \"" + userName + "\",\n" +
                        "\"password\": \"" + password + "\",\n" +
                        "\"state\": \"" + state + "\",\n" +
                        "\"city\": \"" + city + "\",\n" +
                        "\"userType\": \"" + "Ambassador" + "\"\n" +
                        "}";
            }

            try {
                //Make the json object to PUT
                JSONObject json = new JSONObject(jsonString);

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
                Singleton.getInstance(getApplicationContext()).addRequest(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    /** Retrieve the data for enetered user to be modified and PUT to update. */
    private JSONObject getUser(String username){
        final JSONObject[] json = {null};
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Users/" + username;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());
                json[0] = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log and display error toast
                Log.e("Volley Error: ", error.toString());
                Toast.makeText(getApplicationContext(), "Failed to find user.", Toast.LENGTH_LONG).show();
            }
        });
        // Add request to Singleton request queue.
        Singleton.getInstance(getApplicationContext()).addRequest(req);
        return json[0];
    }
}
