package com.example.itinerarybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.*;
import android.os.Bundle;

import com.example.itinerarybuddy.data.User;

/**
 * This is the home page.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(com.example.itinerarybuddy.R.layout.main_activity);

        TextView name = findViewById(com.example.itinerarybuddy.R.id.username);
        TextView email = findViewById(com.example.itinerarybuddy.R.id.email);

        name.setText(User.getUsername());
        email.setText(User.getEmail());
    }
}
