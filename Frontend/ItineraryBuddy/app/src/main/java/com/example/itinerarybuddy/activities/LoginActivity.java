package com.example.itinerarybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.util.Singleton;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/** This is the login page. */
public class LoginActivity extends AppCompatActivity {

    /** Input field for username. */
    private EditText usernameInput;

    /** Input field for password. */
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.login_activity);

        // Declare buttons
        Button loginButton;
        Button registerButton;

        //Instantiate needed fields by id
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.create_account_button);

        // Set listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Attempt login if the inputs are non empty
                if(!usernameInput.getText().toString().equals("") && !passwordInput.getText().toString().equals("")) {
                    login();
                }
            }
        });

        // Set listener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to the registration page
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    /** Creates the url given the entered username and password and creates a GET request for the corresponding JSON object. */
    private void login() {
        // Get the inputted username and password and create a url to get a user
        String enteredUsername = usernameInput.getText().toString();
        String enteredPassword = passwordInput.getText().toString();
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Users/login/" + enteredUsername + "/" + enteredPassword;

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());

                // Save user JSON and proceed to homepage
                UserData.userInfo = response;
                startActivity(new Intent(getApplicationContext(), personalPage1.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log and display error toast
                Log.e("Volley Error: ", error.toString());
                Toast.makeText(getApplicationContext(), "There was an error.", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                HashMap<String, String> map = new HashMap<>();
                map.put("userName", enteredUsername);
                map.put("password", enteredPassword);
                return map;
            }

            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");
                return map;
            }
        };

        // Add request to Singleton request queue.
        Singleton.getInstance(getApplicationContext()).addRequest(json);
    }
}
