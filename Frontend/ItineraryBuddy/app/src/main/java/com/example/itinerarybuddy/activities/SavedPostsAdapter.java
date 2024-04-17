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
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONObject;

import java.util.List;

/**
 * Adapter class for displaying saved posts in a RecyclerView.
 */
class SavedPostsAdapter extends RecyclerView.Adapter<SavedPostsAdapter.SavedPostViewHolder> {

    private List<Post_Itinerary> savedPosts;
    private Context context;

    private RequestQueue requestQueue;

    /**
     * Constructor to initialize the adapter with a list of saved posts and context.
     *
     * @param savedPosts The list of saved posts to be displayed.
     * @param context The context of the application.
     */
    public SavedPostsAdapter(List<Post_Itinerary> savedPosts, Context context) {
        this.savedPosts = savedPosts;
        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * ViewHolder class to hold the views for each saved post item.
     */
    public static class SavedPostViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;
        public TextView postFileTextView;
        public TextView captionTextView;

        public ImageView saveIcon;

        /**
         * Constructor for ViewHolder.
         *
         * @param itemView The view containing the UI elements for the saved post item.
         */
        public SavedPostViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.text_username);
            postFileTextView = itemView.findViewById(R.id.text_post_file);
            captionTextView = itemView.findViewById(R.id.text_caption);

            saveIcon = itemView.findViewById(R.id.icon_savePost);
        }
    }


    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public SavedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_post, parent, false);
        return new SavedPostViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
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

    /**
     * Sends a DELETE request to remove a saved post.
     *
     * @param post The Post_Itinerary object representing the post to be removed.
     */
    public void DELETE_savedPost(Post_Itinerary post){

      //  String url = "http://coms-309-035.class.las.iastate.edu:8080/bookmarks/" + UserData.getUsername() +"/"+ post.getPostID();

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

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return savedPosts.size();
    }

    /**
     * Method to update the list of saved posts.
     *
     * @param savedPosts The new list of saved posts.
     */
    public void setSavedPosts(List<Post_Itinerary> savedPosts) {
        this.savedPosts = savedPosts;
    }
}