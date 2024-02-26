package com.example.itinerarybuddy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

/**
 * This is the home page.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        TextView name = findViewById(R.id.username);
        TextView email = findViewById(R.id.email);

        name.setText(User.getUsername());
        email.setText(User.getEmail());
    }
}
