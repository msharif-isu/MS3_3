package com.example.itinerarybuddy.activities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Post_Itinerary;
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONObject;

import java.util.List;

class SavedPostsAdapter extends RecyclerView.Adapter<SavedPostsAdapter.SavedPostViewHolder> {

    private List<Post_Itinerary> savedPosts;
    private Context context;

    private RequestQueue requestQueue;

    // Constructor to initialize the adapter with a list of saved posts
    public SavedPostsAdapter(List<Post_Itinerary> savedPosts, Context context) {
        this.savedPosts = savedPosts;
        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
    }

    // ViewHolder class to hold the views for each saved post item
    public static class SavedPostViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;
        public TextView postFileTextView;
        public TextView captionTextView;

        public ImageView saveIcon;
        public SavedPostViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.text_username);
            postFileTextView = itemView.findViewById(R.id.text_post_file);
            captionTextView = itemView.findViewById(R.id.text_caption);

            saveIcon = itemView.findViewById(R.id.icon_savePost);
        }
    }

    @NonNull
    @Override
    public SavedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_post, parent, false);
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

        holder.saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(savedPost.isSaved()){

                    holder.saveIcon.setImageResource(R.drawable.ic_bookmark_bfr);
                    savedPost.setSaved(false);

                    DELETE_savedPost(savedPost);

                }

                else{
                    holder.saveIcon.setImageResource(R.drawable.ic_bookmark_aftr);
                    savedPost.setSaved(true);
                }
            }
        });
        holder.postFileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtain the context from the adapter
                Context context = holder.itemView.getContext();

                // Use the context to start the activity
                Intent intent = new Intent(context, DayCard.class);

                // Pass the number of days to the intent
                intent.putExtra("NUM_OF_DAYS", savedPost.getDays());
                intent.putExtra("IS_EDITABLE", false);
                intent.putExtra("SOURCE", "Community");
                context.startActivity(intent);
            }
        });
    }

    public void DELETE_savedPost(Post_Itinerary post){

        String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share/SavedPost" + post.getPostID();

        //String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share/SavedPost";

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Volley Response", "Post removed successfully");
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error removing post: " + error.toString());

                    }
                });

        requestQueue.add(stringRequest);
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