package com.example.itinerarybuddy.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.DatePicker;
import android.widget.TextView;


import com.example.itinerarybuddy.data.Itinerary;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.ErrorListener;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayAdapter<String> itineraryAdapter;
    private EditText startDateInput;
    private EditText endDateInput;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       /* final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        itineraryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, homeViewModel.getItineraries());
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

    private void showAddItineraryDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Itinerary");


        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_itinerary, null);
        builder.setView(dialogView);

        final EditText destinationEditText = dialogView.findViewById(R.id.destinationEditText);
        startDateInput = dialogView.findViewById(R.id.startDateEditText);
        endDateInput = dialogView.findViewById(R.id.endDateEditText);

        startDateInput.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                showDatePickerDialog(startDateInput);
            }
        });

        endDateInput.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                showDatePickerDialog(endDateInput);
            }
        });


        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String destination = destinationEditText.getText().toString();
                String tripCode = generateUniqueTripCode();
                String startDate = startDateInput.getText().toString();
                String endDate = endDateInput.getText().toString();

                createNewFrame(destination, tripCode, startDate, endDate);
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

    private String generateUniqueTripCode() {

        Random rand = new Random(System.currentTimeMillis());

        Set<Integer> generatedNumbers = new HashSet<>();

        while (true) {
            int randomNum = rand.nextInt(10000000);

            if (!generatedNumbers.contains(randomNum)) {
                generatedNumbers.add(randomNum);
                return String.format("%08d", randomNum);
            }
        }

    }


    private void showDatePickerDialog(final EditText date) {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        startDateInput.setText(dateFormat.format(calendar.getTime()));

                        showEndDatePickerDialog(calendar, endDateInput);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showEndDatePickerDialog(final Calendar startDateCalendar, final EditText endDate) {

        int year = startDateCalendar.get(Calendar.YEAR);
        int month = startDateCalendar.get(Calendar.MONTH);
        int day = startDateCalendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog endDatePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar endDateCalendar = Calendar.getInstance();
                        endDateCalendar.set(year, monthOfYear, dayOfMonth);


                        if (endDateCalendar.before(startDateCalendar)) {

                            Toast.makeText(requireContext(), "End date cannot be before start date", Toast.LENGTH_SHORT).show();
                        } else {

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            endDate.setText(dateFormat.format(endDateCalendar.getTime()));
                        }
                    }
                }, year, month, day);

        endDatePickerDialog.show();
    }

    private void createNewFrame(String destination, String tripCode, String startDate, String endDate) {

        String itineraryInfo =
                "Destination: " + destination
                        + "\nTrip Code: " + tripCode
                        + "\nStart Date: " + startDate
                        + "\nEnd Date: " + endDate;

        //Save the info in itineraryAdapter
        itineraryAdapter.insert(itineraryInfo, 0);

        itineraryAdapter.notifyDataSetChanged();

        //Make a network request using Volley
       // String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/Create";
        String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/Create";


        RequestQueue queue = Volley.newRequestQueue(requireContext());

        String tripData = "{\n" +
                "\"destination\": \"" + destination + "\",\n" +
                "\"tripCode\": \"" + tripCode + "\",\n" +
                "\"start date\": \"" + startDate + "\",\n" +
                "\"end date\": \"" + endDate + "\",\n"  +
                "}";


        JSONObject itineraryData;

        try {

            itineraryData = new JSONObject(tripData);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, itineraryData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Volley Response: ", response.toString());
                    Toast.makeText(requireContext(), "Itinerary created successfully", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error: ", error.toString());
                    Toast.makeText(requireContext(), "Error creating itinerary", Toast.LENGTH_SHORT).show();
                }

        })

            {

               /* protected Map<String, String> getParams(){

                HashMap<String, String> map = new HashMap<String, String>();

                map.put("destination", destination);
                map.put("tripCode", tripCode);
                map.put("start date", startDate);
                map.put("end date", endDate);

                return map;
            }*/

            public Map<String, String> getHeaders(){
                    HashMap<String, String> map = new HashMap<String, String>();
                    return map;
            }
            };

            Itinerary.requestQueue.add(jsonObject);


    } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}