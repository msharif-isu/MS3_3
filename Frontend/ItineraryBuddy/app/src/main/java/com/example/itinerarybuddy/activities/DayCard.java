package com.example.itinerarybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.itinerarybuddy.R;

import java.util.ArrayList;

public class DayCard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DayCardAdapter dayCardAdapter;
    private ArrayList<String> dayTitles;
    private ArrayList<String> dayContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_card);

        int numOfDays = getIntent().getIntExtra("NUM_OF_DAYS", 0);

        recyclerView = findViewById(R.id.recyclerViewDayCard);

        dayTitles = new ArrayList<>();
        dayContents = new ArrayList<>();

        for(int i = 1; i <= numOfDays; i++){
            dayTitles.add("Day " + i);
            dayContents.add("Schedule Content for Day " + i);
        }

        dayCardAdapter = new DayCardAdapter(this, dayTitles, dayContents);
        recyclerView.setAdapter(dayCardAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}