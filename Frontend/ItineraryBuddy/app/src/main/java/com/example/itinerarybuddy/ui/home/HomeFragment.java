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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.DayCard;
import com.example.itinerarybuddy.activities.LoginActivity;
import com.example.itinerarybuddy.data.Itinerary;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.FragmentHomeBinding;
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

    public static JSONObject personalItinerary;
    List<Itinerary> itineraries = new ArrayList<>();

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

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ListView list = root.findViewById(R.id.listViewItineraries);
        itineraryAdapter = new CustomAdapter(requireContext(), R.layout. list_item_layout, itineraries, this, this);
        list.setAdapter(itineraryAdapter);

        GET_itinerary();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected itinerary
                Itinerary selectedItinerary = itineraryAdapter.getItem(position);

                int days = selectedItinerary.getNumDays();
                int itineraryID = selectedItinerary.getItineraryID();

                Intent intent = new Intent(requireContext(), DayCard.class);

                Bundle bundle = new Bundle();
                bundle.putParcelable("SELECTED_ITINERARY", selectedItinerary);
                bundle.putInt("NUM_OF_DAYS", days);
                bundle.putBoolean("IS_EDITABLE", true);
                bundle.putString("SOURCE", "PERSONAL");
                bundle.putInt("ITINERARY_ID", itineraryID);

                GET_anItinerary(selectedItinerary);

                intent.putExtras(bundle);
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
                String startDate = startDateInput.getText().toString();
                String endDate = endDateInput.getText().toString();

                createNewFrame(destination, startDate, endDate);
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
     *
     * @param startDate The start date of the itinerary in "yyyy-MM-dd" format.
     * @param endDate The end date of the itinerary in "yyyy-MM-dd" format.
     */
    private void createNewFrame(String destination, String startDate, String endDate) {

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
                "Destination: " + destination
                        + "\nStart Date: " + startDate
                        + "\nEnd Date: " + endDate
                        + "\nNumber of Days: " + numOfDays;


        Itinerary newItinerary = new Itinerary();

        newItinerary.setDestination(destination);
        newItinerary.setStartDate(startDate);
        newItinerary.setEndDate(endDate);
        newItinerary.setNumOfDays(numOfDays);

        // Insert the itinerary information into the itinerary adapter
        itineraryAdapter.insert(newItinerary, 0);
        // Notify the adapter of the data set change
        itineraryAdapter.notifyDataSetChanged();

        //itineraries.add(0,newItinerary);
        // Initiate a POST request to add the itinerary to the server
        try {
            POST_itinerary(newItinerary);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void POST_itinerary(Itinerary newItinerary) throws InterruptedException {

        //Make a network request using Volley
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Personal/Itinerary/" + UserData.getUsername();

        //Convert itinerary information to JSON format
        String tripData = "{\n" +
                "\"itineraryName\": \"" + newItinerary.getDestination() + "\",\n" +
                "\"startDate\": \"" + newItinerary.getStartDate() + "\",\n" +
                "\"endDate\": \"" + newItinerary.getEndDate() + "\",\n" +
                "\"numDays\": \"" + newItinerary.getNumDays() + "\"\n" +
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

                    Log.d("Volley Response POST: ", response.toString());
                    Toast.makeText(requireContext(), "Itinerary created successfully", Toast.LENGTH_LONG).show();

                    GET_itinerary();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Volley Error POST: ", error.toString());
                    Toast.makeText(requireContext(), "Error creating itinerary", Toast.LENGTH_SHORT).show();
                }
            })

            {
                @Override
                public Map<String, String> getHeaders(){
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Content-Type", "application/json");
                    return map;
                }

            };

            //Add the JsonObjectRequest to the request queue
            Singleton.getInstance(requireContext()).addRequest(jsonObject);
            Thread.sleep(500);


        } catch (JSONException e) {
            e.printStackTrace();
        }

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
    private void editItinerary(int position){

        Itinerary editItinerary = itineraries.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Itinerary");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_itinerary, null);
        builder.setView(dialogView);

        final EditText destinationEdit = dialogView.findViewById(R.id.destinationEditText);
        startDateInput = dialogView.findViewById(R.id.startDateEditText);
        endDateInput = dialogView.findViewById(R.id.endDateEditText);

        // Populate EditText fields with existing itinerary information
        if (editItinerary != null) {
            destinationEdit.setText(editItinerary.getDestination());
            startDateInput.setText(editItinerary.getStartDate());
            endDateInput.setText(editItinerary.getEndDate());
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
                "\nStart Date: " + startDate +
                "\nEnd Date: " + endDate +
                "\nNumber of Days: " + numOfDays;

        Itinerary updatedItinerary = itineraries.get(position);

        updatedItinerary.setDestination(destination);
        updatedItinerary.setStartDate(startDate);
        updatedItinerary.setEndDate(endDate);
        updatedItinerary.setNumOfDays(numOfDays);

        // Remove the old itinerary item and insert the updated one at the beginning of the list
        itineraryAdapter.remove(itineraryAdapter.getItem(position));
        itineraryAdapter.insert(updatedItinerary,0);

        itineraries.set(position, updatedItinerary);

        // Notify the adapter that the data set has changed
        itineraryAdapter.notifyDataSetChanged();

        PUT_itinerary(updatedItinerary);
    }

    private void PUT_itinerary(Itinerary editItinerary){

        // Retrieve the trip code for the specified itinerary position
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Personal/Itinerary/" + editItinerary.getItineraryID();

        //Convert itinerary information to JSON format
        String editedData = "{\n" +
                "\"itineraryName\": \"" + editItinerary.getDestination() + "\",\n" +
                "\"startDate\": \"" + editItinerary.getStartDate() + "\",\n" +
                "\"endDate\": \"" + editItinerary.getEndDate() + "\",\n" +
                "\"numDays\": \"" + editItinerary.getNumDays() + "\"\n" +
                "}";

        //Declare JSONObject to hold the itinerary data
        JSONObject itineraryData;

        try {

            //Create a JSONObject from the tripData
            itineraryData = new JSONObject(editedData);

            //JsonObjectRequest for the PUT request
            JsonObjectRequest updateRequest = new JsonObjectRequest(Request.Method.PUT, url, itineraryData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    personalItinerary = response;
                    Log.d("Volley Response PUT: ", response.toString());
                    Toast.makeText(requireContext(), "Itinerary updated successfully", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Volley Error PUT: ", error.toString());
                    Toast.makeText(requireContext(), "Error updating itinerary", Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            // Add the PUT request to the request queue
            Singleton.getInstance(requireContext()).addRequest(updateRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    //DELETE (Delete itinerary function related)
    /**
     * Sends a DELETE request to delete the itinerary associated with the specified trip code.
     *
     * @param itineraryID The trip code of the itinerary to be deleted.
     */
    private void DELETE_itinerary(int itineraryID) {

        String url = "http://coms-309-035.class.las.iastate.edu:8080/Personal/Itinerary/" + itineraryID;
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
        Singleton.getInstance(requireContext()).addRequest(deleteRequest);
    }


    /**
     * Prompts the user to confirm the deletion of an itinerary at the specified position in the adapter.
     * Deletes the itinerary if the user confirms.
     *
     * @param position The position of the itinerary item in the adapter to be deleted.
     */
    private void deleteItinerary(int position){

        AlertDialog.Builder confirmDeleteBuilder = new AlertDialog.Builder(requireContext());
        confirmDeleteBuilder.setTitle("Confirm Delete");
        confirmDeleteBuilder.setMessage("Are you sure you want to delete this itinerary?");

        confirmDeleteBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Remove the data from the adapter
                itineraryAdapter.remove(itineraryAdapter.getItem(position));
                itineraryAdapter.notifyDataSetChanged();

                Itinerary deleteItinerary = itineraries.get(position);

                // Send a DELETE request to delete the itinerary
                DELETE_itinerary(deleteItinerary.getItineraryID());

                itineraries.remove(position);
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
                        List<Itinerary> fetchedItineraries = updateItineraryAdapter(response);
                        itineraries.addAll(fetchedItineraries);

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
        Singleton.getInstance(requireContext()).addRequest(jsonArrayRequest);
    }

    private void GET_anItinerary(Itinerary choosen_Itinerary) {
        final String url = "http://coms-309-035.class.las.iastate.edu:8080/Personal/Itinerary/" + choosen_Itinerary.getItineraryID();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                    personalItinerary = jsonObject;
                    String destination = "Destination: " + choosen_Itinerary.getDestination();
                    String start = "Start Date: " + choosen_Itinerary.getStartDate();
                    String end = "End Date: " + choosen_Itinerary.getEndDate();
                    String length = "Number of Days: " + choosen_Itinerary.getNumDays();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Volley Error: ", volleyError.toString());
            }
        });
        Singleton.getInstance(requireContext() ).addRequest(request);
    }
    /**
     * Updates the adapter with itinerary data obtained from the JSON array.
     *
     * @param itineraryArray The JSON array containing itinerary information.
     * @return
     */
    private List<Itinerary> updateItineraryAdapter(JSONArray itineraryArray) {

        // Clear the existing data in the adapter
        itineraryAdapter.clear();
        List<Itinerary> itineraries = new ArrayList<>();

        // Iterate over the JSON array to extract itinerary information
        for (int i = 0; i < itineraryArray.length(); i++) {
            try {
                JSONObject itineraryObject = itineraryArray.getJSONObject(i);
                String destination = itineraryObject.getString("itineraryName");
                String startDate = itineraryObject.getString("startDate");
                String endDate = itineraryObject.getString("endDate");
                int numOfDays = itineraryObject.getInt("numDays");

                int id = itineraryObject.getInt("id");

                String itineraryInfo = "Destination: " + destination +
                        "\nStart Date: " + startDate +
                        "\nEnd Date: " + endDate +
                        "\nNumber of Days: " + numOfDays;

                Itinerary itinerary = new Itinerary();

                itinerary.setItineraryID(id);
                itinerary.setDestination(destination);
                itinerary.setStartDate(startDate);
                itinerary.setEndDate(endDate);
                itinerary.setNumOfDays(numOfDays);

                itineraryAdapter.insert(itinerary, 0);
                itineraries.add(0, itinerary);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        itineraryAdapter.notifyDataSetChanged();
        return itineraries;
    }

}