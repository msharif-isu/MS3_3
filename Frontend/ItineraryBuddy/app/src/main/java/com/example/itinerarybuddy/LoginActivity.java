package com.example.itinerarybuddy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the login page.
 */
public class LoginActivity extends AppCompatActivity {

    /** JSON Object for user data to be passed into each activity. */
    public JSONObject user;

    /** Input field for username. */
    private EditText usernameInput;

    /** Input field for password. */
    private EditText passwordInput;

    /** Button for login. */
    private Button loginButton;

    /** Button for sign up page. */
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
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

    /**
     * Gets the JSON from the URL and assigns the username and password variables accordingly.
     */
    public void login(){
        //Get the inputted username and password and create a url to get a user
        String enteredUsername = usernameInput.getText().toString();
        String enteredPassword = passwordInput.getText().toString();
        String url = "/Users/login/" + enteredUsername + "/" + enteredPassword;

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());
                try{
                    //Extract correct data from JSON
                    String username = response.getString("name");
                    String password = response.getString("password");
                    if(enteredUsername.equals(username) && enteredPassword.equals(password)){
                        //Set static user variable to the returned JSON object
                        User.userInfo = response;

                        //If username and password are a match, proceed to main page.
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else{
                        //Otherwise, show a login error
                        Toast.makeText(getApplicationContext(), "Incorrect credentials!", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error: ", error.toString());
            }
        });
        User.requestQueue.add(jsonObj);
    }
}
