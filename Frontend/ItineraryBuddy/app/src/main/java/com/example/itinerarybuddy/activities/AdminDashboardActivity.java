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

/** Control panel where an admin will be able to moderate activity. */
public class AdminDashboardActivity extends AppCompatActivity {

    private EditText input1;

    private EditText input2;

    private EditText input3;

    private EditText input4;

    private EditText input5;

    private EditText input6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_activity);

        Button button1;
        Button button2;
        Button button3;
        Button button4;
        Button button5;
        Button button6;
        Button buttonReturn;

        input1 = findViewById(R.id.id_input1);
        input2 = findViewById(R.id.id_input2);
        input3 = findViewById(R.id.id_input3);
        input4 = findViewById(R.id.id_input4);
        input5 = findViewById(R.id.id_input5);
        input6 = findViewById(R.id.id_input6);
        button1 = findViewById(R.id.confirm1);
        button2 = findViewById(R.id.confirm2);
        button3 = findViewById(R.id.confirm3);
        button4 = findViewById(R.id.confirm4);
        button5 = findViewById(R.id.confirm5);
        button6 = findViewById(R.id.confirm6);
        buttonReturn = findViewById(R.id.return_home);

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this, MainActivity.class));
            }
        });

        // Revoke ambassador status
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String admin = UserData.getUsername();
                String username = input1.getText().toString();
                updateData("http://coms-309-035.class.las.iastate.edu:8080/Ambassador/Revoke/" + admin + "/" + username, "is no longer an Ambassador.");
                input1.setText("");
            }
        });

        // Grant ambassador status
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String admin = UserData.getUsername();
                String username = input2.getText().toString();
                updateData("http://coms-309-035.class.las.iastate.edu:8080/Ambassador/Grant/" + admin + "/" + username, "is now an Ambassador.");
                input2.setText("");
            }
        });

        // Revoke Admin status
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = input3.getText().toString();
                String admin = UserData.getUsername();
                updateData("http://coms-309-035.class.las.iastate.edu:8080/Admin/Revoke/" + admin + "/" + username, "is no longer an Admin.");
                input3.setText("");
            }
        });

        // Grant admin status
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String admin = UserData.getUsername();
                String username = input4.getText().toString();
                updateData("http://coms-309-035.class.las.iastate.edu:8080/Admin/Grant/" + admin + "/" + username, "is now an Admin.");
                input4.setText("");
            }
        });

        // Disable user posting
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = input5.getText().toString();
                String admin = UserData.getUsername();
                updateData("http://coms-309-035.class.las.iastate.edu:8080/Admin/DisablePosting/"+ admin + "/" + username, "can no longer post.");
                input5.setText("");
            }
        });

        // Enable user posting
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = input6.getText().toString();
                String admin = UserData.getUsername();
                updateData("http://coms-309-035.class.las.iastate.edu:8080/Admin/EnablePosting/"+ admin + "/" + username, "can now post.");
                input6.setText("");
            }
        });
    }

    /** Update the users data given the admin dashboard commands. */
    private void updateData(String url, String change) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley: ", response.toString());
                try {
                    String user = response.getString("userName");
                    Toast.makeText(getApplicationContext(), user + " " + change, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley: ", error.toString());
                Toast.makeText(getApplicationContext(), "Failed to update user!", Toast.LENGTH_LONG).show();
            }
        });
        Singleton.getInstance(getApplicationContext()).addRequest(req);
    }
}
