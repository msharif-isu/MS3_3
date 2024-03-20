package com.example.itinerarybuddy.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());

        postAdapter = new PostAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(postAdapter);

        ImageView postButton = root.findViewById(R.id.postContent);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPostDialog();
            }
        });

        /*final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        loadItineraries();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showPostDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Post Itinerary");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_post_itinerary, null);
        builder.setView(dialogView);

        EditText captionEditText = dialogView.findViewById(R.id.captionEditText);
        Spinner itinerarySpinner = dialogView.findViewById(R.id.itinerarySpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getUserItineraries());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itinerarySpinner.setAdapter(adapter);

        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedItinerary = itinerarySpinner.getSelectedItem().toString();
                String caption = captionEditText.getText().toString();

                if(caption.split("\\s+").length > 50){
                    Toast.makeText(requireContext(), "Caption should be less than 50 words", Toast.LENGTH_SHORT).show();
                    return;
                }

                createPost(selectedItinerary, caption);
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

    private List<String> getUserItineraries(){

        return new ArrayList<>();
    }

    private void loadItineraries(){}
}