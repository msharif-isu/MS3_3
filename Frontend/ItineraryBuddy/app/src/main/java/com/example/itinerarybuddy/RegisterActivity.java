package com.example.itinerarybuddy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * This is where the users will register.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput;

    private EditText emailInput;

    private EditText passwordInput;

    private EditText confirmPasswordInput;

    private Button registerButton;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        //Instantiate needed fields by id
        usernameInput = findViewById(R.id.username_register_input);
        emailInput = findViewById(R.id.email_register_input);
        passwordInput = findViewById(R.id.password_register_input);
        confirmPasswordInput = findViewById(R.id.password_confirm_input);
        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.sign_in_here_button);

        //Set listener for this button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to the login page
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        //Set listener for this button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Register the user
                register();
            }
        });
    }

    /**
     * Extracts data from text inputs, forms it into a string, and is made into a JSON object and posted.
     */
    private void register(){
        String url = "";

        //Extract strings from the text fields
        String username = usernameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = usernameInput.getText().toString();

        // Only execute if passwords match
        if(password.equals(confirmPassword)){
            //Form string for JSON object
            String userString = "{\n" +
                    "  \"username\": " + username + ",\n" +
                    "  \"email\": " + email + ",\n" +
                    "  \"password\": " + password + "\n" +
                    "}";

            JSONObject newUser;
            try{
                //Create the JSON object
                newUser = new JSONObject(userString);

                //Post the JSON object
                JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST, url, newUser , new Response.Listener<JSONObject>() {
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
                });
                User.requestQueue.add(json);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
