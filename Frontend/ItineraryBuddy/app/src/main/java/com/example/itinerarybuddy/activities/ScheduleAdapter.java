package com.example.itinerarybuddy.activities;

import android.app.TimePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.ScheduleItem;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;


/**
 * The ScheduleAdapter class is a RecyclerView adapter responsible for managing the schedule data
 * and binding it to the corresponding views in the RecyclerView.
 */

class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_DATA = 1;

    private String dayTitle;
    private static List<ScheduleItem> scheduleData;

    private boolean isEditable;


    /**
     * Constructs a new ScheduleAdapter.
     *
     * @param scheduleData The list of schedule items.
     * @param dayTitle The title of the day.
     * @param isEditable the ability to Edit schedule
     */


    public ScheduleAdapter(List<ScheduleItem> scheduleData, String dayTitle, boolean isEditable) {

        this.scheduleData = scheduleData;
        this.dayTitle = dayTitle;
        this.isEditable = isEditable;
    }

    /**
     * Returns the list of schedule data.
     *
     * @return The list of schedule data.
     */
    public List<ScheduleItem> getScheduleData(){
        return scheduleData;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_header, parent, false);

            TextView dayTitleTV = view.findViewById(R.id.DayTitle);

            if(dayTitleTV != null){
                dayTitleTV.setText(dayTitle);
            }


        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_data, parent, false);
        }
        return new ViewHolder(view, dayTitle);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_HEADER) {
            holder.setDayTitle(dayTitle);
        } else if (viewType == VIEW_TYPE_DATA) {
            if (position > 0 && position <= scheduleData.size()) {
                ScheduleItem item = scheduleData.get(position - 1);
                holder.bindData(item);

                holder.setDataFieldsEditable(isEditable);
            }
        }
    }



    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the data set.
     */
    @Override
    public int getItemCount() {
        return scheduleData.size() + 1;
    }

    /**
     * Returns the view type of the item at the specified position.
     *
     * @param position The position of the item in the data set.
     * @return An integer representing the view type of the item at the specified position.
     */
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_DATA;
    }

    /**
     * Adds new data to the beginning of the schedule.
     *
     * @param newData The list of new schedule items to prepend.
     */
    public void prependData(List<ScheduleItem> newData){

        scheduleData.addAll(0, newData);
        notifyItemRangeInserted(0, newData.size());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView headerTextViewTime;
        private TextView headerTextViewPlaces;
        private TextView headerTextViewNote;

        private TextView dayTitleTextView;
        private EditText dataEditTextTime;
        private EditText dataEditTextPlaces;
        private EditText dataEditTextNote;


        public void setDataFieldsEditable(boolean isEditable){

            if(dataEditTextTime != null){
                dataEditTextTime.setEnabled(isEditable);
            }

            if(dataEditTextPlaces != null){
                dataEditTextPlaces.setEnabled(isEditable);
            }

            if(dataEditTextNote != null){
                dataEditTextNote.setEnabled(isEditable);
            }

        }


        public ViewHolder(@NonNull View itemView, String dayTitle) {
            super(itemView);

            dayTitleTextView = itemView.findViewById(R.id.DayTitle);


            if (itemView.findViewById(R.id.headerTextViewTime) != null) {
                // This is a header view
                headerTextViewTime = itemView.findViewById(R.id.headerTextViewTime);
                headerTextViewPlaces = itemView.findViewById(R.id.headerTextViewPlaces);
                headerTextViewNote = itemView.findViewById(R.id.headerTextViewNote);
            } else {
                // This is a data view
                dataEditTextTime = itemView.findViewById(R.id.dataEditTextTime);
                dataEditTextPlaces = itemView.findViewById(R.id.dataEditTextPlaces);
                dataEditTextNote = itemView.findViewById(R.id.dataEditTextNote);

                // Set click listener for time EditText to show time picker dialog
                dataEditTextTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTimePickerDialog();
                    }
                });

                // Add text change listeners for places and notes EditTexts to update schedule item
                dataEditTextPlaces.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (getAdapterPosition() > 0 && getAdapterPosition() <= scheduleData.size()) {
                            ScheduleItem item = scheduleData.get(getAdapterPosition() - 1);
                            item.setPlaces(s.toString());

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                dataEditTextNote.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (getAdapterPosition() > 0 && getAdapterPosition() <= scheduleData.size()) {
                            ScheduleItem item = scheduleData.get(getAdapterPosition() - 1);
                            item.setNotes(s.toString());

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });


            }

        }

        private void showTimePickerDialog() {
            TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    // Create a Time object with the selected time
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    Time selectedTime = new Time(calendar.getTimeInMillis());

                    // Update the ScheduleItem in the list with the selected time
                    if (getAdapterPosition() > 0 && getAdapterPosition() <= scheduleData.size()) {
                        ScheduleItem item = scheduleData.get(getAdapterPosition() - 1);
                        item.setTime(selectedTime);

                        // Update the EditText field to display the selected time
                        dataEditTextTime.setText(selectedTime.toString());
                    }
                }
            };

            // Show the TimePickerDialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(itemView.getContext(), timeSetListener, 0, 0, true);
            timePickerDialog.show();
        }

        /**
         * Displays the day title.
         *
         * @param title The title of the day.
         */
        public void setDayTitle(String title) {
            if (dayTitleTextView != null) {
                dayTitleTextView.setText(title);
            }
        }

        /**
         * Binds data to the views in the ViewHolder.
         *
         * @param item The schedule item to bind.
         */
        public void bindData(ScheduleItem item) {
            if (item != null) {
                if (headerTextViewTime != null) {
                    // This is a header view
                    headerTextViewTime.setText("Time");
                    headerTextViewPlaces.setText("Places");
                    headerTextViewNote.setText("Note");

                } else {
                    // This is a data view
                    if (dataEditTextTime != null) {
                        dataEditTextTime.setText(item.getTime() != null ? item.getTime().toString() : "");
                    }
                    if (dataEditTextPlaces != null) {
                        dataEditTextPlaces.setText(item.getPlaces());
                    }
                    if (dataEditTextNote != null) {
                        dataEditTextNote.setText(item.getNotes());
                    }
                }
            }
        }

    }
}


