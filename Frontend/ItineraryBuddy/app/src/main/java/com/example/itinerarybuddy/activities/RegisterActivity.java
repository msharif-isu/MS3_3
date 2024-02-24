package com.example.itinerarybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.os.Bundle;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.User;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/** This is where the users will register. */
public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput;

    private EditText emailInput;

    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Declare other fields
        EditText confirmPasswordInput;
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
                if(passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString()))
                    register();
            }
        });
    }

    /** Extracts data from text inputs, forms it into a string, and is made into a JSON object and posted. */
    private void register(){
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Users/Create";

        // Extract strings from the text fields
        String username = usernameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = usernameInput.getText().toString();

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
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, newUser , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Volley Response: ", response.toString());
                    Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error: ", error.toString());
                    Toast.makeText(getApplicationContext(), "There was an error.", Toast.LENGTH_LONG).show();
                }
            })
            {

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
                    return map;
                }
            };
            User.requestQueue.add(jsonObj);
        }catch(JSONException e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }
}
