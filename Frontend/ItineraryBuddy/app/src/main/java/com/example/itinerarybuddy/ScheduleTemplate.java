package com.example.itinerarybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.itinerarybuddy.data.ScheduleItem;

import java.util.ArrayList;
import java.util.List;

public class ScheduleTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_template);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<ScheduleItem> data = generateData();
        ScheduleAdapter adapter = new ScheduleAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    private List<ScheduleItem> generateData(){

        List<ScheduleItem> data = new ArrayList<>();

        for(int i = 0; i <= 250; i++){

            ScheduleItem item = new ScheduleItem();
            data.add(item);
        }

        return data;
    }
}


