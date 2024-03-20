package com.example.itinerarybuddy.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Itinerary;
import com.example.itinerarybuddy.data.Post_Itinerary;
import com.example.itinerarybuddy.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    private List<String> destinations = new ArrayList<>();
    private List<Post_Itinerary> posts = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewPost);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        postAdapter = new PostAdapter(posts, requireContext());
        recyclerView.setAdapter(postAdapter);

        ImageView postButton = root.findViewById(R.id.postContent);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPostDialog();
            }
        });

        fetchDestinations();
        loadPosts();

        return root;
    }

    // Function to show the dialog for posting
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
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, destinations);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            itinerarySpinner.setAdapter(adapter);
        } else {
            Log.e("DashboardFragment", "itinerarySpinner is null");
        }

        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (itinerarySpinner != null) {
                    String selectedItinerary = itinerarySpinner.getSelectedItem().toString();
                    String caption = captionEditText.getText().toString();

                    if (caption.split("\\s+").length > 50) {
                        Toast.makeText(requireContext(), "Caption should be less than 50 words", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    createPost(selectedItinerary, caption);
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




    private void createPost(String selectedItinerary, String caption){}


    private void fetchDestinations(){

        String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/GetInfo";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {

                            try {

                                JSONObject itineraryObj = response.getJSONObject(i);
                                String destination = itineraryObj.getString("destination");
                                destinations.add(destination);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

    private void loadPosts() {
        // Fetch posts from backend or database
        // For demo, let's add some sample posts
        posts.add(new Post_Itinerary("user1", "2 hours ago", "post1.jpg", "This is the first post."));
        posts.add(new Post_Itinerary("user2", "1 hour ago", "post2.jpg", "This is the second post."));
        posts.add(new Post_Itinerary("user3", "30 minutes ago", "post3.jpg", "This is the third post."));

        // Notify adapter of data change
        postAdapter.notifyDataSetChanged();
    }
}