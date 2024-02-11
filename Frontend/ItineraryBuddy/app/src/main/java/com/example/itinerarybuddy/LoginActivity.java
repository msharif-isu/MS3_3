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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the login page.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Input field for username.
     */
    private EditText usernameInput;

    /**
     * Input field for password.
     */
    private EditText passwordInput;

    /**
     * Button for login.
     */
    private Button loginButton;

    /**
     * Button for sign up page.
     */
    private Button registerButton;

    private String username;

    private String password;

    private RequestQueue q;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.create_account_button);

        q = Volley.newRequestQueue(getApplicationContext());
        String url = "https://jsonplaceholder.typicode.co/1m/users";
        getJsonData("https://443da8f0-75e2-4be2-8e84-834c5d63eda6.mock.pstmn.io/user");

        // Set listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //postInputs("https://10af03af-423b-4e27-bc38-f8965a59688c.mock.pstmn.io/post");
                //getJsonData("");
                if(usernameInput.getText().toString().equals(username) && passwordInput.getText().toString().equals(password)){
                    //If username and password are a match, proceed to main page.
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else{
                    //Otherwise, show a login error
                    Toast.makeText(getApplicationContext(), "Incorrect credentials!", Toast.LENGTH_SHORT).show();
                }
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
     * @param url
     */
    private void getJsonData(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());
                try{
                    username = response.getString("id");
                    password = response.getString("email");
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error: ", error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                //headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("param1", "value1");
                //params.put("param2", "value2");
                return params;
            }
        };
        q.add(jsonObj);
    }

    /**
     * Posts a JSON object with just the username and password so backend can find a user match.
     * @param url
     */
    public void postInputs(String url){
        JSONObject data = null;
        String info = "{\n\t\"username\": " + "\"" + usernameInput + "\",\n\"" +
                "\"password\": " + "\"password\"\n}";
        try{
            data = new JSONObject(info);
        }catch(Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObj = new JsonObjectRequest(url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error: ", error.toString());
            }
        });
        q.add(jsonObj);
    }
}
