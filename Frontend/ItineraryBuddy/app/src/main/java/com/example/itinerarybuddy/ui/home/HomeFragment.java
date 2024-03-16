package com.example.itinerarybuddy.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.DatePicker;
import android.widget.PopupMenu;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.itinerarybuddy.data.Itinerary;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

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
import com.example.itinerarybuddy.util.Singleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayAdapter<String> itineraryAdapter;
    private EditText startDateInput;
    private EditText endDateInput;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        Itinerary.requestQueue = Volley.newRequestQueue(requireContext());
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       /* final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        itineraryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, homeViewModel.getItineraries());
        ListView list = root.findViewById(R.id.listViewItineraries);
        list.setAdapter(itineraryAdapter);

        GET_itinerary();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                showPopupMenu(view, position);
            }
        });

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


    //POST (Add itinerary function related)
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

        POST_itinerary(destination, tripCode, startDate, endDate);

        // saveItineraryToShared(destination, tripCode, startDate, endDate);

    }

    private void POST_itinerary(String destination, String tripCode, String startDate, String endDate){

        //Make a network request using Volley
        // String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/Create";
        String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/Create";

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        //Convert itinerary information to JSON format
        String tripData = "{\n" +
                "\"destination\": \"" + destination + "\",\n" +
                "\"tripCode\": \"" + tripCode + "\",\n" +
                "\"start date\": \"" + startDate + "\",\n" +
                "\"end date\": \"" + endDate + "\"\n" +
                "}";

        Log.d("Volley Request Data: ", tripData);

        //Declare JSONObject to hold the itinerary data
        JSONObject itineraryData;

        try {

            //Create a JSONObject from the tripData
            itineraryData = new JSONObject(tripData);

            //JsonObjectRequest for the POST request
            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, itineraryData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Volley Response: ", response.toString());
                    Toast.makeText(requireContext(), "Itinerary created successfully", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Volley Error POST: ", error.toString());
                    Toast.makeText(requireContext(), "Error creating itinerary", Toast.LENGTH_SHORT).show();
                }
            })
            {@Override
            protected Map<String, String> getParams(){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("destination", destination);
                map.put("tripCode", tripCode);
                map.put("start date", startDate);
                map.put("end date", endDate);

                return map;
            }
                @Override
                public Map<String, String> getHeaders(){
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Content-Type", "application/json");
                    return map;
                }


            };

            //Add the JsonObjectRequest to the request queue
            Itinerary.requestQueue.add(jsonObject);
           // Singleton.getInstance(requireContext()).addRequest(jsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    //PUT (Update and Edit itinerary function related)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.itinerary_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.action_edit){
            return true;
        }

        else if(item.getItemId() == R.id.action_delete){
            return true;
        }

        else{
            return false;
        }
    }

    private void showPopupMenu(View view, int position){

        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();

        inflater.inflate(R.menu.itinerary_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_edit){
                    editItinerary(position);
                    return true;
                }

                else if(item.getItemId() == R.id.action_delete){
                    deleteItinerary(position);
                    return true;
                }

                else{
                    return false;
                }            }
        });

        popupMenu.show();
    }

    private void editItinerary(final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Itinerary");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_itinerary, null);
        builder.setView(dialogView);

        final EditText destinationEdit = dialogView.findViewById(R.id.destinationEditText);
        startDateInput = dialogView.findViewById(R.id.startDateEditText);
        endDateInput = dialogView.findViewById(R.id.endDateEditText);

        String itineraryInfo = itineraryAdapter.getItem(position);

        if(itineraryInfo != null){

            String[] itineraryData = itineraryInfo.split("\n");
            destinationEdit.setText(itineraryData[0].substring("Destination: ".length()));
            startDateInput.setText(itineraryData[2].substring("Start Date: ".length()));
            endDateInput.setText(itineraryData[3].substring("End date: ".length()));

        }

        startDateInput.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showDatePickerDialog(startDateInput);
            }
        });

        endDateInput.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                showEndDatePickerDialog(Calendar.getInstance(), endDateInput);
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String destination = destinationEdit.getText().toString();
                String startDate = startDateInput.getText().toString();
                String endDate = endDateInput.getText().toString();

                updateItinerary(position, destination, startDate, endDate);

                PUT_itinerary(destination, position, startDate, endDate);

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

    private void updateItinerary(int position, String destination, String startDate, String endDate){

        String updatedItineraryInfo = "Destination: " + destination +
                "\nTrip Code: " + getTripCodeFromAdapterPosition(position) +
                "\nStart Date: " + startDate +
                "\nEnd Date: " + endDate;

        itineraryAdapter.remove(itineraryAdapter.getItem(position));
        itineraryAdapter.insert(updatedItineraryInfo,0);
        itineraryAdapter.notifyDataSetChanged();
    }

    private String getTripCodeFromAdapterPosition(int position){
        String itineraryInfo = itineraryAdapter.getItem(position);

        if(itineraryInfo != null){

            String[] itineraryData = itineraryInfo.split("\n");
            String tripCode = itineraryData[1].substring("Trip Code:".length());
            return tripCode;
        }

        return null;
    }

    private void PUT_itinerary(final String destination, final int position, final String startDate, final String endDate){

        String tripCode = getTripCodeFromAdapterPosition(position);
        //String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/Update" + tripCode;
        String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/Update/" + tripCode;

        StringRequest updateRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Update Response: ", response);
                        Toast.makeText(requireContext(), "Itinerary updated successfully", Toast.LENGTH_SHORT).show();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error PUT: ", error.toString());

                        Toast.makeText(requireContext(), "Error updating itinerary", Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams(){

                Map<String, String> params = new HashMap<>();
                params.put("destination", destination);
                params.put("start date", startDate);
                params.put("end date", endDate);
                return params;
            }

            @Override
            public Map<String, String> getHeaders(){

                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        Itinerary.requestQueue.add(updateRequest);
    }


    //DELETE (Delete itinerary function related)
    private void DELETE_itinerary(final String tripCode) {

        //String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/Delete" + tripCode;
        String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/Delete/" + tripCode;

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Delete Response: ", response);
                        Toast.makeText(requireContext(), "Itinerary deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error Delete:", error.toString());

                        Toast.makeText(requireContext(), "Error deleting itinerary", Toast.LENGTH_SHORT).show();
                    }
                }) {

            public Map<String, String> getHeaders() {
                // Add headers if needed, e.g., authorization token
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        Itinerary.requestQueue.add(deleteRequest);
    }


    private void deleteItinerary(final int position){

        AlertDialog.Builder confirmDeleteBuilder = new AlertDialog.Builder(requireContext());
        confirmDeleteBuilder.setTitle("Confirm Delete");
        confirmDeleteBuilder.setMessage("Are you sure you want to delete this itinerary?");

        confirmDeleteBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               String tripCode = getTripCodeFromAdapterPosition(position);

               //Remove the data from the adapter
                itineraryAdapter.remove(itineraryAdapter.getItem(position));
                itineraryAdapter.notifyDataSetChanged();

                DELETE_itinerary(tripCode);
            }
        });

        confirmDeleteBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        confirmDeleteBuilder.show();
    }

    //GET (fetch itinerary information function related)

    public void GET_itinerary(){


        //String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/GetInfo";
        String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/GetInfo";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //Parse the JSON array and update the adapter with itinerary information
                        updateItineraryAdapter(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Volley Error GET: ", error.toString());

                        if(error.networkResponse != null){
                            Log.e("Volley Error", "Status Code: " + error.networkResponse.statusCode);
                            Log.e("Volley Error: ", "Error Response: " + new String(error.networkResponse.data));
                        }
                        Toast.makeText(requireContext(), "Error fetching itinerary info", Toast.LENGTH_SHORT).show();
                    }
                });

        Itinerary.requestQueue.add(jsonArrayRequest);
    }

    private void updateItineraryAdapter(JSONArray itineraryArray) {

        //itineraryAdapter.clear();

        for (int i = 0; i < itineraryArray.length(); i++) {
            try {
                JSONObject itineraryObject = itineraryArray.getJSONObject(i);
                String destination = itineraryObject.getString("destination");
                String tripCode = itineraryObject.getString("tripCode");
                String startDate = itineraryObject.getString("start date");
                String endDate = itineraryObject.getString("end date");

                String itineraryInfo = "Destination: " + destination +
                        "\nTrip Code: " + tripCode +
                        "\nStart Date: " + startDate +
                        "\nEnd Date: " + endDate;

                itineraryAdapter.add(itineraryInfo);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        itineraryAdapter.notifyDataSetChanged();
    }

}