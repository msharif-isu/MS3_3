package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.w3c.dom.Text;
import java.util.*;

public class RandomActivity extends AppCompatActivity {

    // Declare text fields and buttons
    private TextView text;

    private Button btnRandom;

    private Button btnBack;

    // Declare needed variables for generating a random number
    private Random rand;

    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        // Initialize views based on their XML ids
        text = findViewById(R.id.random_num);
        btnRandom = findViewById(R.id.random_btn);
        btnBack = findViewById(R.id.random_back_btn);

        // Create new Random object to generate numbers
        rand = new Random();

        // Set click listener for back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Transfer back to the main activity
                Intent intent = new Intent(RandomActivity.this, MainActivity.class);
                intent.putExtra("NUM", String.valueOf(num));
                startActivity(intent);
            }
        });

        // Set click listener for back button
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate a random int from 0-100000 and change text to it
                num = rand.nextInt(100000);
                text.setText(String.format(Integer.toString(num)));
            }
        });

    }
}
