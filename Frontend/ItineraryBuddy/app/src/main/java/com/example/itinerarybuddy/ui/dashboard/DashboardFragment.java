package com.example.itinerarybuddy.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.SavedPost;
import com.example.itinerarybuddy.activities.WebSocketListener;
import com.example.itinerarybuddy.activities.WebSocketManager;
import com.example.itinerarybuddy.data.Itinerary;
import com.example.itinerarybuddy.data.Post_Itinerary;
import com.example.itinerarybuddy.data.Spinner_ItineraryInfo;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.FragmentDashboardBinding;
import com.example.itinerarybuddy.util.Singleton;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DashboardFragment extends Fragment implements WebSocketListener, OnItemClickListener {

    private WebSocketManager webSocketManager;
    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    private final List<Spinner_ItineraryInfo> itineraryInfos = new ArrayList<>();
    private final List<Post_Itinerary> posts = new ArrayList<>();
    private final String BASE_URL = "ws://localhost:8080/post/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewPost);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        postAdapter = new PostAdapter(posts, requireContext(), this);
        recyclerView.setAdapter(postAdapter);

        connectWebSocket();

        WebSocketManager.getInstance().setWebSocketListener(this);

        ImageView postButton = root.findViewById(R.id.postContent);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPostDialog();
            }
        });

        ImageView savedContent = root.findViewById(R.id.savedContent);

        savedContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(requireContext(), SavedPost.class);
                startActivity(intent);
            }
        });

       // GET_previousPosts();
        GET_fetch_Destinations_TripCode();
        loadPosts();

        return root;
    }

    /**
     * Generates a unique post ID consisting of 3 random letters followed by 4 random numbers.
     *
     * @return A string representing the generated post ID.
     */
    public static String generatePostID(){

        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";

        Random random = new Random();

        StringBuilder postIDBuilder = new StringBuilder();

        //Generate 3 random letters
        for(int i = 0; i < 3; i++){
            postIDBuilder.append(letters.charAt(random.nextInt(letters.length())));
        }

        for(int i = 0; i < 4; i++){
            postIDBuilder.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return postIDBuilder.toString();
    }

    /**
     * Connects to the WebSocket server using the BASE_URL and a specific username.
     * Adjust the server URL based on your requirements.
     */
    private void connectWebSocket() {

       // String serverUrl = BASE_URL + UserData.getUsername();
        String serverUrl = BASE_URL + "Aina"; // Adjust this based on your requirements
        WebSocketManager.getInstance().connectWebSocket(serverUrl);
    }


    /**
     * Parses a Post_Itinerary object from a WebSocket message.
     *
     * @param message The WebSocket message to parse.
     * @return A Post_Itinerary object parsed from the message, or null if parsing fails.
     */
    private Post_Itinerary parsePostFromMessage(String message) {
        // Parse the message and construct a Post_Itinerary object
        // This will depend on the format of the message sent by your server
        // Replace this with actual parsing logic based on your server's message format
        try {
            JSONObject jsonObject = new JSONObject(message);
            String username = jsonObject.getString("username");
            String postFile = jsonObject.getString("postFile");
            String caption = jsonObject.getString("caption");
            int likeCount = jsonObject.getInt("likeCount");
            //boolean likeValue = jsonObject.getBoolean("likeValue");
            int saveCount = jsonObject.getInt("saveCount");
            //boolean saveValue = jsonObject.getBoolean("saveValue");
            String tripCode = jsonObject.getString("tripCode");
            int numDays = jsonObject.getInt("numDays");
            String postID = jsonObject.getString("postID");

            // Parse comments array
            JSONArray commentsArray = jsonObject.getJSONArray("comments");
            ArrayList<Post_Itinerary.Comment> comments = new ArrayList<>();
            for (int i = 0; i < commentsArray.length(); i++) {
                JSONObject commentObject = commentsArray.getJSONObject(i);
                String commenterUsername = commentObject.getString("username");
                String commentText = commentObject.getString("commentText");
                comments.add(new Post_Itinerary.Comment(commenterUsername, commentText));
            }

            // Create and return a new Post_Itinerary object
            Post_Itinerary returning_post =  new Post_Itinerary(username, postFile, tripCode, numDays, caption, postID);

            returning_post.setLikeCount(likeCount);
            returning_post.setSaveCount(saveCount);

            return returning_post;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Shows a dialog for posting an itinerary.
     */
    public void showPostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Post Itinerary");

        // Inflate the dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_post_itinerary, null);
        builder.setView(dialogView);

        EditText captionEditText = dialogView.findViewById(R.id.captionEditText);

        // Find the itinerarySpinner in the dialog view
        Spinner itinerarySpinner = dialogView.findViewById(R.id.itinerarySpinner);

        if (itinerarySpinner != null) {

            if (itineraryInfos.size() < 1) {
                // If no itineraries, set a hint message
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"No Personal Itinerary to be Posted"});
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                itinerarySpinner.setAdapter(adapter);
                itinerarySpinner.setEnabled(false); // Disable the spinner
            } else {

                // If there are itineraries, populate the spinner with destinations
                ItineraryInfoAdapter adapter = new ItineraryInfoAdapter(requireContext(), itineraryInfos);
                itinerarySpinner.setAdapter(adapter);
                itinerarySpinner.setEnabled(true); // Enable the spinner
            }
        } else {
            Log.e("DashboardFragment", "itinerarySpinner is null");
        }

        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (itinerarySpinner != null) {
                    String selectedItinerary = itinerarySpinner.getSelectedItem().toString();

                    // Split selected itinerary to extract destination and trip code
                    String[] itineraryParts = selectedItinerary.split(",");
                    if (itineraryParts.length != 2) {
                        Log.e("DashboardFragment", "Invalid selected itinerary format: " + selectedItinerary);
                        return;
                    }
                    String destination = itineraryParts[0].trim();
                    String tripCode = itineraryParts[1].trim();

                    String caption = captionEditText.getText().toString();

                    // Retrieve the trip code and number of days corresponding to the selected itinerary
                    String choosen_tripCode = "";
                    int choosen_numDays = 0;

                    for (Spinner_ItineraryInfo itineraryInfo : itineraryInfos) {
                        if (itineraryInfo.getDestination().equals(destination) && itineraryInfo.getTripCode().equals(tripCode)) {
                            choosen_tripCode = itineraryInfo.getTripCode();
                            choosen_numDays = itineraryInfo.getNumDays();
                            break;
                        }
                    }

                    if (caption.split("\\s+").length > 50) {
                        Toast.makeText(requireContext(), "Caption should be less than 50 words", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String postID = generatePostID();

                    //Post_Itinerary newPost = new Post_Itinerary(UserData.getUsername(),"Just Now", selectedItinerary,caption );
                    Post_Itinerary newPost = new Post_Itinerary(UserData.getUsername(), destination, choosen_tripCode, choosen_numDays, caption, postID);

                    POST_newPost(newPost);
                    WebSocketManager.getInstance().sendPost(newPost);

                    posts.add(0, newPost);
                    postAdapter.notifyItemInserted(0);

                } else {
                    Log.e("DashboardFragment", "itinerarySpinner is null");
                }
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

    /**
     * Posts a new itinerary to the server.
     *
     * @param post The Post_Itinerary object representing the itinerary to be posted.
     */
    private void POST_newPost(Post_Itinerary post){

        //String url = "http://coms-309-035.class.las.iastate.edu:8080/PostItinerary";
        String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share";


        // Create a new JSONObject to hold the post data
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", post.getUsername());
            postData.put("postFile", post.getPostFile());
            postData.put("postID", post.getPostID());
            postData.put("tripCode", post.getTripCode());
            postData.put("number of days", post.getDays());
            postData.put("caption", post.getCaption());
            postData.put("likeCount", post.getLikeCount());
            postData.put("saveCount", post.getSaveCount());

            // Convert comments list to JSON array
            JSONArray commentsArray = new JSONArray();
            for (Post_Itinerary.Comment comment : post.getComments()) {
                JSONObject commentObject = new JSONObject();
                commentObject.put("username", comment.getUsername());
                commentObject.put("commentText", comment.getCommentText());
                commentsArray.put(commentObject);
            }
            postData.put("comments", commentsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley Response: ", response.toString());
                        Toast.makeText(requireContext(), "Posted!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error POST: ", error.toString());
                        Toast.makeText(requireContext(), "Error posting itinerary", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        Singleton.getInstance(requireContext()).addRequest(jsonObjectRequest);
    }

    /**
     * Fetches previous posts from the server.
     */
    private void GET_previousPosts() {
        // URL for fetching previous posts

        //String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/Share" + username;
        String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share";

     //   String url = "http://coms-309-035.class.las.iastate.edu:8080/PostedItinerary/List";

        // RequestQueue for handling Volley requests
        RequestQueue queue = Volley.newRequestQueue(requireContext());

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
                                posts.add(post);
                            }
                            // Notify the adapter of data change
                            postAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors in the response
                Log.e("Volley Error GET: ", error.toString());
                Toast.makeText(requireContext(), "Error fetching previous posts", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }

    /**
     * Parses a Post_Itinerary object from a JSON object.
     *
     * @param jsonObject The JSON object representing the post.
     * @return A Post_Itinerary object parsed from the JSON object, or null if parsing fails.
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
     * Handles the click event for editing a post's caption.
     *
     * @param position The position of the post in the list.
     */
        @Override
    public void onEditClicked(int position) {
        // Call EditClicked method in DashboardFragment
        EditClicked(position);
    }

    /**
     * Handles the click event for deleting a post.
     *
     * @param position The position of the post in the list.
     */
    @Override
    public void onDeleteClicked(int position) {
        // Call DeleteClicked method in DashboardFragment
        DeleteClicked(position);
    }

    /**
     * Shows a dialog for editing the caption of a post.
     *
     * @param position The position of the post in the list.
     */
    public void EditClicked(int position) {
        // Get the post at the specified position
        Post_Itinerary post = posts.get(position);

        // Show a dialog for editing the caption
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Caption");

        EditText editText = new EditText(requireContext());
        editText.setText(post.getCaption());
        
        builder.setView(editText);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the caption of the post
                String newCaption = editText.getText().toString();
                post.setCaption(newCaption);

                PUT_editCaption(post, newCaption);

                // Update the RecyclerView
                postAdapter.notifyItemChanged(position);


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

    /**
     * Updates the caption of a post on the server.
     *
     * @param post       The Post_Itinerary object representing the post to be updated.
     * @param newCaption The new caption to be set for the post.
     */
    private void PUT_editCaption(Post_Itinerary post, String newCaption){

        //String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/Share/" + username + post.getPostID();
        String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share";

       // String url = "http://coms-309-035.class.las.iastate.edu:8080/PostedItinerary/" + post.getPostID();

        JSONObject captionData = new JSONObject();

        try{
            captionData.put("caption", newCaption);

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
     * Deletes a post from the server and the local list.
     *
     * @param position The position of the post to be deleted.
     */
    public void DeleteClicked(int position) {
        // Remove the post from the list

        Post_Itinerary deleting_post = posts.get(position);
        String postId = deleting_post.getPostID();

        posts.remove(position);

        // Update the RecyclerView
        postAdapter.notifyItemRemoved(position);

        DELETE_post(postId);

    }

    /**
     * Sends a DELETE request to delete a post from the server.
     *
     * @param postID The ID of the post to be deleted.
     */
    private void DELETE_post(String postID){

        //String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/Share/" + username + postID;
        String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share" + postID;

      //  String url = "http://coms-309-035.class.las.iastate.edu:8080/PostedItinerary/" + postID;

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

    /**
     * Fetches destinations and trip codes from the server.
     */
    private void GET_fetch_Destinations_TripCode(){

        String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/GetInfo";

//        String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/List/" + UserData.getUsername();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Check if the fragment is attached to a context
                        if (getContext() != null) {
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject itineraryObj = response.getJSONObject(i);
                                    String destination = itineraryObj.getString("destination");
                                    String tripCode = itineraryObj.getString("tripCode");
                                    int numDays = itineraryObj.getInt("number of days");

                                    itineraryInfos.add(new Spinner_ItineraryInfo(destination, tripCode, numDays));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.e("DashboardFragment", "Fragment is not attached to a context");
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error GET: ", error.toString());
                if (error.networkResponse != null) {
                    Log.e("Volley Error", "Status Code: " + error.networkResponse.statusCode);
                    Log.e("Volley Error: ", "Error Response: " + new String(error.networkResponse.data));
                }

                Toast.makeText(requireContext(), "Error fetching itinerary info", Toast.LENGTH_SHORT).show();
            }
        });

        Itinerary.requestQueue.add(jsonArrayRequest);

    }

    /**
     * Loads posts from the server or database.
     */
    private void loadPosts() {
        // Fetch posts from backend or database
        // For demo, let's add some sample posts
        posts.add(new Post_Itinerary("user1", "post1.jpg", "12345678", 2, "This is the first post.", "ABC1234"));
        //posts.add(new Post_Itinerary("user2", "1 hour ago", "post2.jpg", "This is the second post."));
        //posts.add(new Post_Itinerary("user3", "30 minutes ago", "post3.jpg", "This is the third post."));

        GET_previousPosts();

        // Notify adapter of data change
        postAdapter.notifyDataSetChanged();
    }


    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

        Toast.makeText(requireContext(), "WebSocket connected successfully", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onWebSocketMessage(String message) {
        handleWebSocketMessage(message);
    }


    /**
     * Handles the message received from the WebSocket server.
     *
     * @param message The message received from the server.
     */
    private void handleWebSocketMessage(String message) {

        // Parse the message received from the WebSocket server
        Post_Itinerary newPost = parsePostFromMessage(message);

        if (newPost != null) {
            // Check if the post already exists in the list
            boolean postExist = false;

            for(int i = 0; i < posts.size(); i++){

                Post_Itinerary postItinerary = posts.get(i);

                if(postItinerary.getUsername().equals(newPost.getUsername()) && postItinerary.getPostID().equals(newPost.getPostID())){

                    //Update the existing post with the new data
                    posts.set(i, newPost);
                    postExist = true;
                    break;
                }
            }

            if(!postExist){
                //If the post does not exist in the list, add it
                posts.add(0, newPost);
            }

            //Notify the adapter of data change
            postAdapter.notifyItemInserted(0);
        }
    }


    @Override
    public void onWebSocketClose(int code, String reason, boolean remote){

    }

    @Override
    public void onWebSocketError(Exception ex){}


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //Remove the WebSocketListener when the fragment is destroyed
        if(webSocketManager != null){
            webSocketManager.removeWebSocketListener();
        }
    }
}