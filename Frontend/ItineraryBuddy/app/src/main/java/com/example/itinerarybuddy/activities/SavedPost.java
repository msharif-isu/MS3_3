package com.example.itinerarybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Post_Itinerary;

import java.util.List;

public class SavedPost extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView initialView;
    private List<Post_Itinerary> savedPosts;
    private SavedPostsAdapter savedPostAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_post);
        
        recyclerView = findViewById(R.id.recyclerSavedPost);
        initialView = findViewById(R.id.initial_savedPost);
        
        savedPostAdapter = new SavedPostsAdapter(savedPosts, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(savedPostAdapter);
        
        fetchSavedPostsData();

    }

    private void fetchSavedPostsData() {

        if(savedPosts != null && !savedPosts.isEmpty()){

            recyclerView.setVisibility(View.VISIBLE);
            initialView.setVisibility(View.GONE);

            savedPostAdapter.setSavedPosts(savedPosts);
            savedPostAdapter.notifyDataSetChanged();
        }

        else{

            recyclerView.setVisibility(View.GONE);
            initialView.setVisibility(View.VISIBLE);
        }
    }
}