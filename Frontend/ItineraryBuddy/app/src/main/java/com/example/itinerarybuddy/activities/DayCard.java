package com.example.itinerarybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.itinerarybuddy.R;

import java.util.ArrayList;

public class DayCard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DayCardAdapter dayCardAdapter;
    private ArrayList<String> dayTitles;
    private ArrayList<String> dayContents;
    private String source;
    private boolean isEditable;

    /**
     * Called when the activity is starting.
     * <p>
     * Initializes the activity layout, retrieves the number of days from the intent extra,
     * generates day titles and contents, and sets up the RecyclerView with the adapter.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_card);

        // Retrieve the number of days from the intent extra
        int numOfDays = getIntent().getIntExtra("NUM_OF_DAYS", 0);

        source = getIntent().getStringExtra("SOURCE");
        isEditable = getIntent().getBooleanExtra("IS_EDITABLE", true);


        recyclerView = findViewById(R.id.recyclerViewDayCard);

        dayTitles = new ArrayList<>();
        dayContents = new ArrayList<>();

        // Generate day titles and contents
        for(int i = 1; i <= numOfDays; i++){
            dayTitles.add("Day " + i);
            dayContents.add("Schedule Content for Day " + i);
        }


        // Initialize and set up the RecyclerView adapter
        dayCardAdapter = new DayCardAdapter(this, dayTitles, dayContents, isEditable, source);

        recyclerView.setAdapter(dayCardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}