package com.example.itinerarybuddy.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.UserData;

public class TestGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);

        //TextView name = findViewById(com.example.itinerarybuddy.R.id.username);
        //TextView email = findViewById(com.example.itinerarybuddy.R.id.email);

        //name.setText(UserData.getUsername());
        //email.setText(UserData.getEmail());
    }
}
