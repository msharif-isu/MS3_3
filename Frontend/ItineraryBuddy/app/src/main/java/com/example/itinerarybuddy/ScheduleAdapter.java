package com.example.itinerarybuddy;

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

import com.example.itinerarybuddy.data.ScheduleItem;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

//To adapt a list of schedule data to a RecyclerView
class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_DATA = 1;

    private static List<ScheduleItem> scheduleData;

    public ScheduleAdapter(List<ScheduleItem> scheduleData) {
        this.scheduleData = scheduleData;
    }

    public List<ScheduleItem> getScheduleData(){
        return scheduleData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_header, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_data, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_DATA) {
            if (position > 0 && position <= scheduleData.size()) {
                ScheduleItem item = scheduleData.get(position - 1);
                holder.bindData(item);
            }
        }
    }


    @Override
    public int getItemCount() {
        return scheduleData.size() + 1;
    }

    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_DATA;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView headerTextViewTime;
        private TextView headerTextViewPlaces;
        private TextView headerTextViewNote;

        private EditText dataEditTextTime;
        private EditText dataEditTextPlaces;
        private EditText dataEditTextNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


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

               /* dataEditTextTime.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (getAdapterPosition() > 0 && getAdapterPosition() <= scheduleData.size()) {
                            ScheduleItem item = scheduleData.get(getAdapterPosition() - 1);
                            item.setTime(s.toString());



                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });*/

                dataEditTextTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTimePickerDialog();
                    }
                });

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


