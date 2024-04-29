package com.example.itinerarybuddy.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.DayCard;
import com.example.itinerarybuddy.activities.LoginActivity;
import com.example.itinerarybuddy.activities.ScheduleTemplate;
import com.example.itinerarybuddy.data.Itinerary;
import com.example.itinerarybuddy.data.UserData;


import com.example.itinerarybuddy.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * This fragment displays home content including a list of itineraries.
 * It also allows adding, editing, and deleting itineraries.
 */
public class HomeFragment extends Fragment implements CustomAdapter.OnEditClickListener, CustomAdapter.OnDeleteClickListener {

    private FragmentHomeBinding binding;
    private CustomAdapter itineraryAdapter;
    private EditText startDateInput;
    private EditText endDateInput;

    public int numOfDays;

    /**
     * Initializes the fragment's user interface and binds the data.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        Itinerary.requestQueue = Volley.newRequestQueue(requireContext());
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ListView list = root.findViewById(R.id.listViewItineraries);
        itineraryAdapter = new CustomAdapter(requireContext(), R.layout. list_item_layout, homeViewModel.getItineraries(), this, this);
        list.setAdapter(itineraryAdapter);

        GET_itinerary();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Get the selected itinerary
                String selectedItinerary = itineraryAdapter.getItem(position);

                int days = extractNumOfDays(selectedItinerary);
                String tripCode = extractTripCode(selectedItinerary);

                Intent intent = new Intent(requireContext(), DayCard.class);
                Intent intent2 = new Intent(requireContext(), ScheduleTemplate.class);

                intent.putExtra("NUM_OF_DAYS", days);
                intent.putExtra("IS_EDITABLE", true);
                intent.putExtra("SOURCE", "Personal");
                intent2.putExtra("TRIPCODE", tripCode);

                startActivity(intent);
            }
        });

        ImageButton fab = root.findViewById(R.id.addItinerary);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItineraryDialog();
            }
        });

        ImageButton toGroups = root.findViewById(R.id.group_button);
        toGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), ListGroups.class));
            }
        });

        ImageButton settings = root.findViewById(R.id.mainpage_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a menu with three options given XML resource
                PopupMenu menu = new PopupMenu(requireContext(), v);
                MenuInflater inflater = menu.getMenuInflater();

                inflater.inflate(R.menu.mainpage_menu, menu.getMenu());

                // Set click listener for each option
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.action_logout){
                            UserData.userInfo = null;
                            startActivity(new Intent(requireContext(), LoginActivity.class));
                            return true;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });

        return root;
    }


    private String extractTripCode(String itinerary) {
        String label = "Trip Code: ";
        int labelIndex = itinerary.indexOf(label);

        if (labelIndex != -1) {
            return itinerary.substring(labelIndex + label.length()).trim();
        }
        return null;
    }

    /**
     * Called when the fragment is no longer in use. This is called after onStop() and before onDetach().
     * It is used to clean up any resources used by the fragment.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    /**
     * Displays a dialog for adding a new itinerary.
     * The dialog prompts the user to enter destination, start date, and end date for the new itinerary.
     * It includes input fields for each of these details and handles user interaction for adding the itinerary.
     */
    private void showAddItineraryDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Itinerary");

        //Inflate the dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_itinerary, null);
        builder.setView(dialogView);

        //Get references to input fields in the dialog layout
        final EditText destinationEditText = dialogView.findViewById(R.id.destinationEditText);
        startDateInput = dialogView.findViewById(R.id.startDateEditText);
        endDateInput = dialogView.findViewById(R.id.endDateEditText);

        // Set click listeners for date input fields to show date picker dialogs
        startDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePickerDialog(startDateInput);
            }
        });

        endDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog(endDateInput);
            }
        });


        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Retrieve values entered by the user
                String destination = destinationEditText.getText().toString();
                String tripCode = generateUniqueTripCode();
                String startDate = startDateInput.getText().toString();
                String endDate = endDateInput.getText().toString();

                // Create a new itinerary with the provided details
                createNewFrame(destination, tripCode, startDate, endDate);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Display the dialog
        builder.show();
    }


    /**
     * Generates a unique trip code for a new itinerary.
     * The trip code is an 8-digit alphanumeric code that is randomly generated.
     * It ensures uniqueness by checking if the generated code is not already present in a set of generated codes.
     *
     * @return A unique trip code as a string.
     */
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


    private void showStartDatePickerDialog(final EditText startDateInput) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog startDatePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        startDateInput.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);

        startDatePickerDialog.show();
    }

    private void showEndDatePickerDialog(final EditText endDateInput) {
        // Get the text from the startDateInput EditText
        String startDateText = startDateInput.getText().toString();

        // Check if the start date is empty
        if (startDateText.isEmpty()) {
            // If start date is empty, show a message and return
            Toast.makeText(requireContext(), "Please select a start date first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse the start date to a Calendar object
        Calendar startDateCalendar = parseDate(startDateText);

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
                            endDateInput.setText(dateFormat.format(endDateCalendar.getTime()));
                        }
                    }
                }, year, month, day);

        endDatePickerDialog.show();
    }

    private Calendar parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a new itinerary frame with the provided destination, trip code, start date, and end date.
     * Calculates the number of days between the start and end dates.
     * Constructs the itinerary information string and inserts it into the itinerary adapter.
     * Notifies the adapter of the data set change and initiates a POST request to add the itinerary to the server.
     *
     * @param destination The destination of the itinerary.
     * @param tripCode The unique trip code associated with the itinerary.
     * @param startDate The start date of the itinerary in "yyyy-MM-dd" format.
     * @param endDate The end date of the itinerary in "yyyy-MM-dd" format.
     */
    private void createNewFrame(String destination, String tripCode, String startDate, String endDate) {

        // Initialize a SimpleDateFormat for parsing dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        numOfDays = 0;

        try{

            Date startDateObj = dateFormat.parse(startDate);
            Date endDateObj = dateFormat.parse(endDate);

            // Calculate the difference in milliseconds between end and start dates
            long differenceInMilliseconds = endDateObj.getTime() - startDateObj.getTime();

            // Convert milliseconds to days
            numOfDays = (int) TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);

        }catch (ParseException e){
            e.printStackTrace();
        }

        String itineraryInfo =
                "itineraryName: " + destination
                        + "\nstartDate: " + startDate
                        + "\nendDate: " + endDate
                        + "\nnumDays: " + numOfDays;

        // Insert the itinerary information into the itinerary adapter
        itineraryAdapter.insert(itineraryInfo, 0);

        // Notify the adapter of the data set change
        itineraryAdapter.notifyDataSetChanged();

        // Initiate a POST request to add the itinerary to the server
        POST_itinerary(destination, tripCode, startDate, endDate, numOfDays);

    }

    /**
     * Sends a POST request to create a new itinerary on the server.
     *
     * @param destination The destination of the itinerary.
     * @param tripCode The unique trip code associated with the itinerary.
     * @param startDate The start date of the itinerary in "yyyy-MM-dd" format.
     * @param endDate The end date of the itinerary in "yyyy-MM-dd" format.
     * @param numOfDays The number of days for the itinerary.
     */
    private void POST_itinerary(String destination, String tripCode, String startDate, String endDate, int numOfDays){

        //Make a network request using Volley

        //String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/Create";
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Personal/Itinerary/" + UserData.getUsername();

        // Create a new request queue using Volley
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        //Convert itinerary information to JSON format
        String tripData = "{\n" +
                "\"destination\": \"" + destination + "\",\n" +
                "\"tripCode\": \"" + tripCode + "\",\n" +
                "\"start date\": \"" + startDate + "\",\n" +
                "\"end date\": \"" + endDate + "\"\n" +
                "\"number of days\": \"" + numOfDays + "\"\n" +
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

                // Override getParams() to provide parameters for the request body (not used for JSON request)
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("destination", destination);
                map.put("tripCode", tripCode);
                map.put("start date", startDate);
                map.put("end date", endDate);
                map.put("number of days", String.valueOf(numOfDays));

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

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Extracts the number of days from the given itinerary string.
     *
     * @param itinerary The itinerary string containing information about the itinerary.
     * @return The number of days extracted from the itinerary string. Returns 0 if the number of days cannot be parsed.
     */
    private int extractNumOfDays(String itinerary){

        String label = "Number of Days: ";

        int labelIndex = itinerary.indexOf(label);

        if(labelIndex != -1){

            String numOfDaysString = itinerary.substring(labelIndex+label.length());

            try{

                return Integer.parseInt(numOfDaysString.trim());

            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        return 0;
    }


    /**
     * Initializes the contents of the options menu.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater The MenuInflater object that can be used to inflate the menu layout.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.itinerary_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * Handles the action when an item is clicked for editing.
     *
     * @param position The position of the item in the adapter to be edited.
     */
    public void onEditClicked(int position){

        editItinerary(position);
    }

    /**
     * Handles the action when an item is clicked for deletion.
     *
     * @param position The position of the item in the adapter to be deleted.
     */
    public void onDeleteClicked(int position){

        deleteItinerary(position);
    }


    /**
     * Displays a dialog for editing an itinerary item.
     *
     * @param position The position of the itinerary item to be edited in the adapter.
     */
    private void editItinerary(final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Itinerary");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_itinerary, null);
        builder.setView(dialogView);

        final EditText destinationEdit = dialogView.findViewById(R.id.destinationEditText);
        startDateInput = dialogView.findViewById(R.id.startDateEditText);
        endDateInput = dialogView.findViewById(R.id.endDateEditText);

        // Retrieve itinerary information for the selected item
        String itineraryInfo = itineraryAdapter.getItem(position);

        // Populate EditText fields with existing itinerary information
        if(itineraryInfo != null){
            String[] itineraryData = itineraryInfo.split("\n");
            destinationEdit.setText(itineraryData[0].substring("Destination: ".length()));
            startDateInput.setText(itineraryData[2].substring("Start Date: ".length()));
            endDateInput.setText(itineraryData[3].substring("End date: ".length()));

        }

        startDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePickerDialog(startDateInput);
            }
        });

        endDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog(endDateInput);
            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String destination = destinationEdit.getText().toString();
                String startDate = startDateInput.getText().toString();
                String endDate = endDateInput.getText().toString();

                // Update the itinerary item with new information
                updateItinerary(position, destination, startDate, endDate);

                // Send a PUT request to update the itinerary on the server
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

    /**
     * Updates the itinerary item at the specified position with new information and refreshes the adapter.
     *
     * @param position   The position of the itinerary item to be updated in the adapter.
     * @param destination   The updated destination for the itinerary.
     * @param startDate     The updated start date for the itinerary.
     * @param endDate       The updated end date for the itinerary.
     */
    private void updateItinerary(int position, String destination, String startDate, String endDate){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try{

            Date startDateObj = dateFormat.parse(startDate);
            Date endDateObj = dateFormat.parse(endDate);

            long differenceInMilliseconds = endDateObj.getTime() - startDateObj.getTime();
            numOfDays = (int) TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);

        }catch (ParseException e){
            e.printStackTrace();
        }

        String updatedItineraryInfo = "Destination: " + destination +
                "\nTrip Code: " + getTripCodeFromAdapterPosition(position) +
                "\nStart Date: " + startDate +
                "\nEnd Date: " + endDate +
                "\nNumber of Days: " + numOfDays;

        // Remove the old itinerary item and insert the updated one at the beginning of the list
        itineraryAdapter.remove(itineraryAdapter.getItem(position));
        itineraryAdapter.insert(updatedItineraryInfo,0);

        // Notify the adapter that the data set has changed
        itineraryAdapter.notifyDataSetChanged();

    }

    /**
     * Retrieves the trip code from the itinerary item at the specified position in the adapter.
     *
     * @param position The position of the itinerary item in the adapter.
     * @return The trip code extracted from the itinerary item.
     */
    private String getTripCodeFromAdapterPosition(int position){
        String itineraryInfo = itineraryAdapter.getItem(position);

        if(itineraryInfo != null){

            String[] itineraryData = itineraryInfo.split("\n");
            String tripCode = itineraryData[1].substring("Trip Code:".length());
            return tripCode;
        }

        return null;
    }

    /**
     * Sends a PUT request to update the itinerary information for the specified trip identified by its position.
     *
     * @param destination The updated destination for the itinerary.
     * @param position    The position of the itinerary item in the adapter, used to identify the trip.
     * @param startDate   The updated start date for the itinerary.
     * @param endDate     The updated end date for the itinerary.
     */
    private void PUT_itinerary(final String destination, final int position, final String startDate, final String endDate){

        // Retrieve the trip code for the specified itinerary position
        String tripCode = getTripCodeFromAdapterPosition(position);
        String url = "http://coms-309-035.class.las.iastate.edu:8080//Personal/Itinerary/" + tripCode;
       // String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/Update/" + tripCode;

        //String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/" + tripCode;

        // Create a StringRequest for the PUT request
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

                // Construct parameters for the PUT request
                Map<String, String> params = new HashMap<>();
                params.put("destination", destination);
                params.put("start date", startDate);
                params.put("end date", endDate);
                params.put("number of days", String.valueOf(numOfDays));
                return params;
            }

            @Override
            public Map<String, String> getHeaders(){

                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        // Add the PUT request to the request queue
        Itinerary.requestQueue.add(updateRequest);
    }


    //DELETE (Delete itinerary function related)
    /**
     * Sends a DELETE request to delete the itinerary associated with the specified trip code.
     *
     * @param tripCode The trip code of the itinerary to be deleted.
     */
    private void DELETE_itinerary(final String tripCode) {

        String url = "http://coms-309-035.class.las.iastate.edu:8080/Personal/Itinerary/" + tripCode;
       // String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/Delete/" + tripCode;

        //String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/" + tripCode;

        // Create a StringRequest for the DELETE request
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

        // Add the DELETE request to the request queue
        Itinerary.requestQueue.add(deleteRequest);
    }


    /**
     * Prompts the user to confirm the deletion of an itinerary at the specified position in the adapter.
     * Deletes the itinerary if the user confirms.
     *
     * @param position The position of the itinerary item in the adapter to be deleted.
     */
    private void deleteItinerary(final int position){

        AlertDialog.Builder confirmDeleteBuilder = new AlertDialog.Builder(requireContext());
        confirmDeleteBuilder.setTitle("Confirm Delete");
        confirmDeleteBuilder.setMessage("Are you sure you want to delete this itinerary?");

        confirmDeleteBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Retrieve the trip code for the specified itinerary position
                String tripCode = getTripCodeFromAdapterPosition(position);

                //Remove the data from the adapter
                itineraryAdapter.remove(itineraryAdapter.getItem(position));
                itineraryAdapter.notifyDataSetChanged();

                // Send a DELETE request to delete the itinerary
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
    /**
     * Sends a GET request to retrieve itinerary information from the server.
     * Updates the adapter with the fetched itinerary data upon successful response.
     */
    public void GET_itinerary(){

        String url = "http://coms-309-035.class.las.iastate.edu:8080/Personal/Itineraries/" + UserData.getUsername();
       // String url = "https://5569939f-7918-4af9-937a-86edcfe9bc7f.mock.pstmn.io/Itinerary/GetInfo";


        // Create a JsonArrayRequest for the GET request
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

        // Add the JsonArrayRequest to the request queue
        Itinerary.requestQueue.add(jsonArrayRequest);
    }

    /**
     * Updates the adapter with itinerary data obtained from the JSON array.
     *
     * @param itineraryArray The JSON array containing itinerary information.
     */
    private void updateItineraryAdapter(JSONArray itineraryArray) {

        // Clear the existing data in the adapter
        itineraryAdapter.clear();

        // Iterate over the JSON array to extract itinerary information
        for (int i = 0; i < itineraryArray.length(); i++) {
            try {
                JSONObject itineraryObject = itineraryArray.getJSONObject(i);
                String destination = itineraryObject.getString("destination");
                String tripCode = itineraryObject.getString("tripCode");
                String startDate = itineraryObject.getString("start date");
                String endDate = itineraryObject.getString("end date");
                String numOfDays = itineraryObject.getString("number of days");

                int numDays = Integer.parseInt(numOfDays);

                String itineraryInfo = "Destination: " + destination +
                        "\nTrip Code: " + tripCode +
                        "\nStart Date: " + startDate +
                        "\nEnd Date: " + endDate +
                        "\nNumber of Days: " + numDays;

                // Add the itinerary information to the adapter
                itineraryAdapter.add(itineraryInfo);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        itineraryAdapter.notifyDataSetChanged();
    }

}