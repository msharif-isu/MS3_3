package com.example.itinerarybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.BlogItem;
import com.example.itinerarybuddy.data.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Blogpage extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogpage);

        recyclerView = findViewById(R.id.recyclerViewBlogPost);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = dateFormat.format(currentDate);

        List<BlogItem> cardItems = new ArrayList<>();

        cardItems.add(new BlogItem("Title 1", UserData.getUsername(), formattedDate, ""));


        BlogCardAdapter adapter = new BlogCardAdapter(cardItems);
        recyclerView.setAdapter(adapter);
    }
}