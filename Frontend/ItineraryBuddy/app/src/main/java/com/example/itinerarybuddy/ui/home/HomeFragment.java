package com.example.itinerarybuddy.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Time;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayAdapter<String> itineraryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       /* final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        itineraryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        ListView list = root.findViewById(R.id.listViewItineraries);
        list.setAdapter(itineraryAdapter);

        FloatingActionButton fab = root.findViewById(R.id.addItinerary);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItineraryDialog();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showAddItineraryDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Itinerary");

        final EditText input = new EditText(requireContext());
        builder.setView(input);

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String destination = input.getText().toString();
                String tripCode = generateUniqueTripCode();
                createNewFrame(destination, tripCode);
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

    private String generateUniqueTripCode(){

        Random rand = new Random(System.currentTimeMillis());

        Set<Integer> generatedNumbers = new HashSet<>();

        while(true){
            int randomNum = rand.nextInt(10000000);

            if(!generatedNumbers.contains(randomNum)){
                generatedNumbers.add(randomNum);
                return String.format("%08d", randomNum);
            }
        }

    }
    private void createNewFrame(String destination, String tripCode) {

        String itineraryInfo = "Destination: " + destination + "\nTrip Code: " + tripCode;

        itineraryAdapter.insert(itineraryInfo, 0);

        itineraryAdapter.notifyDataSetChanged();
    }
}