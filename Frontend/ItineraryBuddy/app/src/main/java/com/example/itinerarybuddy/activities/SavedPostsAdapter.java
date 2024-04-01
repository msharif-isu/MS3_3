package com.example.itinerarybuddy.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Post_Itinerary;

import java.util.List;

class SavedPostsAdapter extends RecyclerView.Adapter<SavedPostsAdapter.SavedPostViewHolder> {

    private List<Post_Itinerary> savedPosts;
    private Context context;

    // Constructor to initialize the adapter with a list of saved posts
    public SavedPostsAdapter(List<Post_Itinerary> savedPosts, Context context) {
        this.savedPosts = savedPosts;
        this.context = context;
    }

    // ViewHolder class to hold the views for each saved post item
    public static class SavedPostViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;

        public TextView postFileTextView;
        public TextView captionTextView;

        public SavedPostViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.text_username);
            postFileTextView = itemView.findViewById(R.id.text_post_file);
            captionTextView = itemView.findViewById(R.id.text_caption);
        }
    }

    @NonNull
    @Override
    public SavedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_saved_post, parent, false);
        return new SavedPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedPostViewHolder holder, int position) {
        // Get the data model based on position
        Post_Itinerary savedPost = savedPosts.get(position);

        // Set the data for the views
        holder.usernameTextView.setText(savedPost.getUsername());
        holder.postFileTextView.setText(savedPost.getPostFile());
        holder.captionTextView.setText(savedPost.getCaption());
    }

    @Override
    public int getItemCount() {
        return savedPosts.size();
    }

    // Method to update the list of saved posts
    public void setSavedPosts(List<Post_Itinerary> savedPosts) {
        this.savedPosts = savedPosts;
    }
}