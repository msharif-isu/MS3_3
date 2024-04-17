package com.example.itinerarybuddy.activities;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Post_Itinerary;
import com.example.itinerarybuddy.data.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity class for displaying saved posts.
 */
public class SavedPost extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView initialView;
    private List<Post_Itinerary> savedPostsLists;
    private SavedPostsAdapter savedPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_post);

        recyclerView = findViewById(R.id.recyclerSavedPost);
        initialView = findViewById(R.id.initial_savedPost);

        savedPostsLists = new ArrayList<>(); // Initialize the list

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        savedPostAdapter = new SavedPostsAdapter(savedPostsLists, this);
        recyclerView.setAdapter(savedPostAdapter);


        GET_SavedPostsData();



    }


    /**
     * Fetches saved posts data from the server.
     */
    private void GET_SavedPostsData() {
        // URL for fetching previous posts

        //String url = "http://coms-309-035.class.las.iastate.edu:8080/bookmarks/" + UserData.getUsername();
        String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share";

        // RequestQueue for handling Volley requests
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a GET request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response
                        try {
                            JSONArray postsArray = response.getJSONArray("posts");
                            // Iterate through the array of posts
                            for (int i = 0; i < postsArray.length(); i++) {
                                JSONObject postObject = postsArray.getJSONObject(i);
                                // Parse each post from the JSON object
                                Post_Itinerary post = parsePostFromJson(postObject);
                                // Add the parsed post to your list of posts
                                savedPostsLists.add(post);

                            }
                            // Notify the adapter of data change
                            savedPostAdapter.notifyDataSetChanged();
                            updateRecyclerView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SavedPost.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors in the response
                Log.e("Volley Error GET: ", error.toString());
                Toast.makeText(SavedPost.this, "Error fetching previous posts", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }

    /**
     * Parses a JSON object to extract post data.
     *
     * @param jsonObject The JSON object representing a post.
     * @return The parsed Post_Itinerary object.
     */
    private Post_Itinerary parsePostFromJson(JSONObject jsonObject) {
        try {
            String username = jsonObject.getString("username");
            String postFile = jsonObject.getString("postFile");
            String postID = jsonObject.getString("postID");
            String tripCode = jsonObject.getString("tripCode");
            int numOfDays = jsonObject.getInt("number of days");
            String caption = jsonObject.getString("caption");
            int likeCount = jsonObject.getInt("likeCount");
            int saveCount = jsonObject.getInt("saveCount");


            JSONArray commentsArray = jsonObject.getJSONArray("comments");
            List<Post_Itinerary.Comment> comments = new ArrayList<>();

            // Iterate through the comments array
            for (int j = 0; j < commentsArray.length(); j++) {
                JSONObject commentObject = commentsArray.getJSONObject(j);
                String commenterUsername = commentObject.getString("username");
                String commentText = commentObject.getString("commentText");
                // Create a Comment object and add it to the comments list
                comments.add(new Post_Itinerary.Comment(commenterUsername, commentText));
            }

            Post_Itinerary postItinerary = new Post_Itinerary(username, postFile, tripCode, numOfDays, caption, postID);

            postItinerary.setLikeCount(likeCount);
            postItinerary.setSaveCount(saveCount);

            // Create and return a new Post_Itinerary object using the constructor
            return postItinerary;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Updates the RecyclerView based on the availability of saved posts.
     */
    private void updateRecyclerView() {

        if(savedPostsLists != null && !savedPostsLists.isEmpty()){

            recyclerView.setVisibility(View.VISIBLE);
            initialView.setVisibility(View.GONE);

            savedPostAdapter.setSavedPosts(savedPostsLists);
            savedPostAdapter.notifyDataSetChanged();
        }

        else{
            recyclerView.setVisibility(View.GONE);
            initialView.setVisibility(View.VISIBLE);
        }
    }
}