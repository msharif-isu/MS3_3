package com.example.itinerarybuddy.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.DatePicker;
import android.widget.TextView;

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
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.xml.transform.ErrorListener;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayAdapter<String> itineraryAdapter;
    private EditText startDateEditText;
    private EditText endDateEditText;

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

    private void showAddItineraryDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
       builder.setTitle("Add Itinerary");


       View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_itinerary,null);
       builder.setView(dialogView);

       final EditText destinationEditText = dialogView.findViewById(R.id.destinationEditText);
       startDateEditText = dialogView.findViewById(R.id.startDateEditText);
       endDateEditText = dialogView.findViewById(R.id.endDateEditText);

       startDateEditText.setOnClickListener(new View.OnClickListener(){

           public void onClick(View v){
               showDatePickerDialog(startDateEditText);
           }
       });

       endDateEditText.setOnClickListener(new View.OnClickListener(){

           public void onClick(View v){
               showDatePickerDialog(endDateEditText);
           }
       });

       /* final EditText input = new EditText(requireContext());
        builder.setView(input);*/

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String destination = destinationEditText.getText().toString();
                String tripCode = generateUniqueTripCode();
                String startDate = startDateEditText.getText().toString();
                String endDate = endDateEditText.getText().toString();

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
                        startDateEditText.setText(dateFormat.format(calendar.getTime()));

                        showEndDatePickerDialog(calendar, endDateEditText);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showEndDatePickerDialog(final Calendar startDateCalendar, final EditText endDate){

        int year = startDateCalendar.get(Calendar.YEAR);
        int month = startDateCalendar.get(Calendar.MONTH);
        int day = startDateCalendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog endDatePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar endDateCalendar = Calendar.getInstance();
                        endDateCalendar.set(year, monthOfYear, dayOfMonth);


                        if(endDateCalendar.before(startDateCalendar)){

                           Toast.makeText(requireContext(), "End date cannot be before start date", Toast.LENGTH_SHORT).show();
                        }

                        else{

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            endDate.setText(dateFormat.format(endDateCalendar.getTime()));
                        }
                    }
                }, year, month, day);

        endDatePickerDialog.show();
    }

/*    void showErrorEndDate(String errorMessage){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Error");
        builder.setMessage(errorMessage);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        TextView messageText = new TextView(requireContext());

        messageText.setText(errorMessage);
        messageText.setTextColor(Color.RED);
        messageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        builder.setView(messageText);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Vibrator vibrator = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);

                if(vibrator != null){
                    vibrator.vibrate(200);
                }

                dialog.dismiss();
            }
        });
    }*/
    private void createNewFrame(String destination, String tripCode, String startDate, String endDate) {

        String itineraryInfo =
                "Destination: " + destination
                + "\nTrip Code: " + tripCode
                + "\nStart Date: " + startDate
                + "\nEnd Date: " + endDate;

        //Save the info in itineraryAdapter
        itineraryAdapter.insert(itineraryInfo, 0);

        itineraryAdapter.notifyDataSetChanged();
    }
}