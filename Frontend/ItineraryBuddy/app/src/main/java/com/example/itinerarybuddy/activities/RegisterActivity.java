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
import com.example.itinerarybuddy.util.Singleton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/** This is where the users will register. */
public class RegisterActivity extends AppCompatActivity {

    /** Text input for username. */
    private EditText usernameInput;

    /** Text input for email. */
    private EditText emailInput;

    /** Text input for password. */
    private EditText passwordInput;

    /** Text input for password confirmation. */
    private EditText confirmPasswordInput;

    /** URL for POST request. */
    private final String URL = "http://coms-309-035.class.las.iastate.edu:8080/Users/Create";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Declare other fields;
        Button registerButton;
        Button loginButton;

        // Instantiate needed fields by id
        usernameInput = findViewById(R.id.username_register_input);
        emailInput = findViewById(R.id.email_register_input);
        passwordInput = findViewById(R.id.password_register_input);
        confirmPasswordInput = findViewById(R.id.password_confirm_input);
        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.sign_in_here_button);

        // Set listener for this button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to the login page
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        // Set listener for this button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Register the user if the passwords match
                if(passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
                    // Input fields must not be empty.
                    if(!usernameInput.getText().toString().equals("") && !emailInput.getText().toString().equals("") && !passwordInput.getText().toString().equals("")) {
                        register();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Invalid inputs", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /** Extracts data from text inputs, forms it into a string, and is made into a JSON object and posted. */
    private void register(){
        // Extract strings from the text fields
        String username = usernameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        //Form string for JSON object
        /*
        {
            "email": "input",
            "userName": "input",
            "password": "input",
            "state": "null",
            "city":"null",
            "userType": "User"
        }
        */
        String userString = "{\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"userName\": \"" + username + "\",\n" +
                "\"password\": \"" + password + "\",\n" +
                "\"state\": \"" + "null" + "\",\n" +
                "\"city\": \"" + "null" + "\",\n" +
                "\"userType\": \"" + "User" + "\"\n" +
                "}";

        JSONObject newUser;
        try{
            // Create the JSON object
            newUser = new JSONObject(userString);

            // Post the JSON object
            JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST, URL, newUser , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Volley Response: ", response.toString());
                    Toast.makeText(getApplicationContext(), "Account Created!", Toast.LENGTH_LONG).show();
                }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error: ", error.toString());
                    Toast.makeText(getApplicationContext(), "There was an error.", Toast.LENGTH_LONG).show();
                }
            })
            {
                // Set the params and headers.
                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("email", email);
                    map.put("username", username);
                    map.put("password", password);
                    map.put("state", "null");
                    map.put("city", "null");
                    map.put("userType", "User");
                    return map;
                }

                @Override
                public Map<String, String> getHeaders(){
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Content-Type", "application/json");
                    return map;
                }
            };

            // Add the request to our Singleton request queue.
            Singleton.getInstance(getApplicationContext()).addRequest(json);
        }catch(JSONException e){
            Toast.makeText(getApplicationContext(), "There was an error.", Toast.LENGTH_LONG).show();
        }
    }
}
