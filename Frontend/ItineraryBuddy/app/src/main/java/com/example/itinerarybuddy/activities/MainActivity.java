package com.example.itinerarybuddy.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itinerarybuddy.data.UserData;

/**
 * This is a placeholder to test the login. Will be replaced with correct homepage.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(com.example.itinerarybuddy.R.layout.main_activity);

        TextView name = findViewById(com.example.itinerarybuddy.R.id.username);
        TextView email = findViewById(com.example.itinerarybuddy.R.id.email);

        name.setText(UserData.getUsername());
        email.setText(UserData.getEmail());
    }
}
