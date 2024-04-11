package com.example.itinerarybuddy.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.ScheduleAdapter;
import com.example.itinerarybuddy.data.ScheduleItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * The ScheduleTemplate class represents the activity for managing and displaying the schedule template.
 * It allows users to input and save schedule data for a specific day.
 */
public class GroupSchedule extends AppCompatActivity {

    private boolean isEditable;
    private String tripCode;
    private boolean isFirstClick = true;
    private ScheduleAdapter adapter;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_template);

        // Retrieve the day title from the intent
        String day = getIntent().getStringExtra("TITLE");
        isEditable = getIntent().getBooleanExtra("IS_EDITABLE", true);
        tripCode = getIntent().getStringExtra("TRIPCODE");


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Generate sample schedule data
        //List<ScheduleItem> data = generateData();

        //adapter = new ScheduleAdapter(data, day, isEditable);
        recyclerView.setAdapter(adapter);


        // Set click listener for the save/update button
        FloatingActionButton btnSaveUpdate = findViewById(R.id.btnSaveUpdate);


        if (!isEditable) {

            btnSaveUpdate.setVisibility(View.INVISIBLE);
        }
        else {
            btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }

    }

    private void postSchedule(String day, List<ScheduleItem> scheduleData) {

    }

    private void putSchedule(String day, List<ScheduleItem> scheduleData) {


    }

    private void getSchedule(String day) {

    }
}


