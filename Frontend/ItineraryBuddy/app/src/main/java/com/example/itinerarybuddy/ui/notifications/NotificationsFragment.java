package com.example.itinerarybuddy.ui.notifications;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.BlogCardAdapter;
import com.example.itinerarybuddy.data.BlogItem;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.FragmentNotificationsBinding;
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private RecyclerView recyclerView;

    private BlogCardAdapter postBlogAdapter;
    List<BlogItem> cardItems = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewBlogPost);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // Use requireContext() instead of 'this'

        postBlogAdapter = new BlogCardAdapter(cardItems, requireContext());
        recyclerView.setAdapter(postBlogAdapter);

        ImageView postButton = root.findViewById(R.id.postBlog);

           /* postButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v){
                        if(UserData.getUsertype().equals("Ambassador")){
                    showPostBlogDialog();
                }
                        else{
                             Toast.makeText(requireContext(), "Only Travel Ambassadors can post picture blog.", Toast.LENGTH_LONG).show();
                        }
                }
            });*/

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){

                showPostBlogDialog();

            }
        });

        loadPosts();
        return root;
    }


    private void showPostBlogDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Post Picture Blog");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_post_blog, null);
        builder.setView(dialogView);

        EditText titleEditText = dialogView.findViewById(R.id.blogPostTitle);

        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()); // Use Locale.getDefault() for consistent formatting across devices
                String formattedDate = dateFormat.format(currentDate);

                String BlogTitle = titleEditText.getText().toString();

                BlogItem newBlogItem = new BlogItem(BlogTitle, UserData.getUsername(), formattedDate);
                cardItems.add(0, newBlogItem);

                POST_BlogItem(newBlogItem);

                postBlogAdapter.notifyItemInserted(0);
                postBlogAdapter.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            postBlogAdapter.setSelectedImage(selectedImageUri);
        }
    }


    private void POST_BlogItem(BlogItem blogItem) {

        String url = "https://ff1e6a32-8cf4-4764-9239-e2a66d09085e.mock.pstmn.io";

        // Create a JSON object to hold the blog item data
        JSONObject blogData = new JSONObject();

        try {
            blogData.put("title", blogItem.getTitle());
            blogData.put("username", blogItem.getUsername());
            blogData.put("postDate", blogItem.getPostDate());
            // Add more properties if necessary
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, blogData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response from the server
                        // You can update UI or take other actions based on the response
                        Log.d("POST Response", response.toString());
                        Toast.makeText(requireContext(), "Posted!", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                Log.e("POST Error", error.toString());
                Toast.makeText(requireContext(), "Error posting blog", Toast.LENGTH_SHORT).show();
            }
        });

        Singleton.getInstance(requireContext()).addRequest(jsonObjectRequest);
    }


    private void loadPosts() {

        GET_previousBlogPosts();
        // Notify adapter of data change
        postBlogAdapter.notifyDataSetChanged();
    }

    private void GET_previousBlogPosts() {
        // URL for fetching previous blog posts
        String url = "https://ff1e6a32-8cf4-4764-9239-e2a66d09085e.mock.pstmn.io";

        // Create a GET request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle successful response
                        try {
                            // Clear the existing cardItems list
                            cardItems.clear();

                            // Iterate through the JSON array of blog posts
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject postObject = response.getJSONObject(i);
                                // Parse each blog post from the JSON object
                                BlogItem blogItem = parseBlogItemFromJson(postObject);
                                // Add the parsed blog post to the list of cardItems
                                cardItems.add(blogItem);
                            }
                            // Notify the adapter of data change
                            postBlogAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors in the response
                Log.e("Volley Error GET", error.toString());
                Toast.makeText(requireContext(), "Error fetching previous blog posts", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue
        Singleton.getInstance(requireContext()).addRequest(jsonArrayRequest);
    }

    private BlogItem parseBlogItemFromJson(JSONObject jsonObject) throws JSONException {
        String title = jsonObject.getString("title");
        String username = jsonObject.getString("username");
        String postDate = jsonObject.getString("postDate");
        // Parse other properties if necessary
        return new BlogItem(title, username, postDate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}