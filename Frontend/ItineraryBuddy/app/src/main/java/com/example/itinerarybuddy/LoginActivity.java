package com.example.itinerarybuddy;

import androidx.annotation.Nullable;
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
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/** This is the login page. */
public class LoginActivity extends AppCompatActivity {

    /** Input field for username. */
    private EditText usernameInput;

    /** Input field for password. */
    private EditText passwordInput;

    /** Button for login. */
    private Button loginButton;

    /** Button for sign up page. */
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Instantiate needed fields by id
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.create_account_button);
        User.requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Set listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        // Set listener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to the registration page
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    /** Creates the url given the entered username and password and creates a GET request for the corresponding JSON object. */
    public void login() {
        //Get the inputted username and password and create a url to get a user
        String enteredUsername = usernameInput.getText().toString();
        String enteredPassword = passwordInput.getText().toString();
        String url = "/Users/login/" + enteredUsername + "/" + enteredPassword;

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());

                //Get the username and password from the returned JSON
                String username;
                String password;
                try {
                    username = response.getString("username");
                    password = response.getString("password");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                //Make sure returned username and password match the input ones
                if(username.equals(enteredUsername) && password.equals(enteredPassword)){
                    //Set static user variable to the returned JSON object
                    User.userInfo = response;

                    //Start main page activity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else{
                    //Incorrect login message
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log and display error toast
                Log.e("Volley Error: ", error.toString());
                Toast.makeText(getApplicationContext(), "There was an error.", Toast.LENGTH_LONG).show();
            }
        });
        User.requestQueue.add(json);
    }
}
