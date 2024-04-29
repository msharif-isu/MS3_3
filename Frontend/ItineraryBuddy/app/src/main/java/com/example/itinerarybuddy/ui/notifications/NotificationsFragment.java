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
import com.android.volley.toolbox.StringRequest;
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

public class NotificationsFragment extends Fragment implements BlogCardAdapter.OnEditClickListener,BlogCardAdapter.OnDeleteClickListener {

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

        postBlogAdapter = new BlogCardAdapter(cardItems, requireContext(), this);
        recyclerView.setAdapter(postBlogAdapter);

        ImageView postButton = root.findViewById(R.id.postBlog);

            postButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v){
                        if(UserData.getUsertype().equals("Ambassador")){
                    showPostBlogDialog();
                }
                        else{
                             Toast.makeText(requireContext(), "Only Travel Ambassadors can post picture blog.", Toast.LENGTH_LONG).show();
                        }
                }
            });


        postBlogAdapter.setOnItemClickListener(new BlogCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Get the clicked BlogItem
                BlogItem clickedBlogItem = cardItems.get(position);

                // Open the BlogPhotoAlbum activity and pass necessary data
                Intent intent = new Intent(requireContext(), BlogPhotoAlbum.class);
                intent.putExtra("ID", clickedBlogItem.getBlogID());
                intent.putExtra("USER", clickedBlogItem.getUsername());
                startActivity(intent);
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
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()); // Use Locale.getDefault() for consistent formatting across devices
                String formattedDate = dateFormat.format(currentDate);

                String BlogTitle = titleEditText.getText().toString();


                BlogItem newBlogItem = new BlogItem(BlogTitle, UserData.getUsername(), formattedDate);
                cardItems.add(0, newBlogItem);

                try {
                    POST_BlogItem(newBlogItem);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

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


    private void POST_BlogItem(BlogItem blogItem) throws InterruptedException {

        //String url = "https://ff1e6a32-8cf4-4764-9239-e2a66d09085e.mock.pstmn.io/Blog";

        String url = "http://coms-309-035.class.las.iastate.edu:8080/BlogPost/"+ blogItem.getUsername() + "/" + blogItem.getTitle() + "/" + blogItem.getPostDate();

        // Create a JSON object to hold the blog item data
       /* JSONObject blogData = new JSONObject();

        try {
            blogData.put("title", blogItem.getTitle());
            blogData.put("username", blogItem.getUsername());
            blogData.put("postDate", blogItem.getPostDate());

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
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
        Thread.sleep(500);
        GET_previousBlogPosts();
    }


    private void loadPosts() {

        GET_previousBlogPosts();
        // Notify adapter of data change
        postBlogAdapter.notifyDataSetChanged();
    }

    private void GET_previousBlogPosts() {
        // URL for fetching previous blog posts
        String url = "http://coms-309-035.class.las.iastate.edu:8080/BlogPost";
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
        String title = jsonObject.getString("blogPostTitle");
        String username = jsonObject.getString("userName");
        String postDate = jsonObject.getString("postDate");
        int blogID = jsonObject.getInt("id");
        // Parse other properties if necessary

        BlogItem newCard = new BlogItem(title, username, postDate);
        newCard.setBlogID(blogID);
        return newCard;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Handles the click event for editing a post's caption.
     *
     * @param position The position of the post in the list.
     */
    @Override
    public void onEditClickedBlog(int position) {
        // Call EditClicked method in DashboardFragment
        EditClicked(position);
    }

    /**
     * Shows a dialog for editing the caption of a post.
     *
     * @param position The position of the post in the list.
     */
    public void EditClicked(int position) {
        // Get the post at the specified position
        BlogItem blog = cardItems.get(position);

        // Show a dialog for editing the caption
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Title");

        EditText editText = new EditText(requireContext());
        editText.setText(blog.getTitle());

        builder.setView(editText);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the caption of the post
                String newTitle = editText.getText().toString();
                blog.setTitle(newTitle);

                PUT_editTitle(blog, newTitle);

                // Update the RecyclerView
                postBlogAdapter.notifyItemChanged(position);


                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void PUT_editTitle(BlogItem blog, String newTitle){

       // String url = "https://ff1e6a32-8cf4-4764-9239-e2a66d09085e.mock.pstmn.io/Blog";
         String url = "http://coms-309-035.class.las.iastate.edu:8080/BlogPost/" + blog.getBlogID() + "/" + newTitle + "/" + blog.getPostDate();

        JSONObject captionData = new JSONObject();

        try{
            captionData.put("blogPostTitle", newTitle);

        }catch (JSONException e){
            e.printStackTrace();
            // Toast.makeText(requireContext(), "Error updating JSON file", Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, captionData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Handle successful response
                        Log.d("Volley Response", "Caption updated successfully");
                        Toast.makeText(requireContext(), "Caption updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors in the response
                        Log.e("Volley Error", "Error updating caption: " + error.toString());
                        Toast.makeText(requireContext(), "Error updating caption", Toast.LENGTH_SHORT).show();
                    }
                });

        Singleton.getInstance(requireContext()).addRequest(jsonObjectRequest);
    }


    /**
     * Handles the click event for deleting a post.
     *
     * @param position The position of the post in the list.
     */
    @Override
    public void onDeleteClickedBlog(int position) {
        // Call DeleteClicked method in DashboardFragment
        DeleteClicked(position);
    }


    /**
     * Deletes a post from the server and the local list.
     *
     * @param position The position of the post to be deleted.
     */
    public void DeleteClicked(int position) {
        // Remove the post from the list

        BlogItem deleting_post = cardItems.get(position);
        int blogId = deleting_post.getBlogID();

        cardItems.remove(position);

        // Update the RecyclerView
        postBlogAdapter.notifyItemRemoved(position);

        DELETE_post(blogId);

    }

    /**
     * Sends a DELETE request to delete a post from the server.
     *
     * @param blogID The ID of the post to be deleted.
     */
    private void DELETE_post(int blogID){

        String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/BlogPost/"  + blogID;
       // String url = "https://ff1e6a32-8cf4-4764-9239-e2a66d09085e.mock.pstmn.io/Blog";

        //  String url = "http://coms-309-035.class.las.iastate.edu:8080/Blog/" + blogID;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Volley Response", "Post deleted successfully");
                        Toast.makeText(requireContext(), "Post deleted successfully!", Toast.LENGTH_SHORT).show();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Error deleting post: " + error.toString());
                        Toast.makeText(requireContext(), "Error deleting post", Toast.LENGTH_SHORT).show();
                    }
                });
        Singleton.getInstance(requireContext()).addRequest(stringRequest);
    }
}