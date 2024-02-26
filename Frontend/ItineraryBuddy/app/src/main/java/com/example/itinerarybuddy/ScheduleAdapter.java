package com.example.itinerarybuddy;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itinerarybuddy.data.ScheduleItem;

import java.util.List;

//To adapt a list of schedule data to a RecyclerView
class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_DATA = 1;

    private List<ScheduleItem> scheduleData;

    public ScheduleAdapter(List<ScheduleItem> scheduleData) {
        this.scheduleData = scheduleData;
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

        if (getItemViewType(position) == VIEW_TYPE_DATA) {
            ScheduleItem item = scheduleData.get(position - 1);
            holder.bindData(item);
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

        private TextView headerTextViewDay;
        private TextView headerTextViewDate;
        private TextView headerTextViewTime;
        private TextView headerTextViewPlaces;
        private TextView headerTextViewNote;


        private EditText dataEditTextDay;
        private EditText dataEditTextDate;
        private EditText dataEditTextTime;
        private EditText dataEditTextPlaces;
        private EditText dataEditTextNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            if (itemView.findViewById(R.id.headerTextViewDay) != null) {
                // This is a header view
                headerTextViewDay = itemView.findViewById(R.id.headerTextViewDay);
                headerTextViewDate = itemView.findViewById(R.id.headerTextViewDate);
                headerTextViewTime = itemView.findViewById(R.id.headerTextViewTime);
                headerTextViewPlaces = itemView.findViewById(R.id.headerTextViewPlaces);
                headerTextViewNote = itemView.findViewById(R.id.headerTextViewNote);
            } else {
                // This is a data view
                dataEditTextDay = itemView.findViewById(R.id.dataEditTextDay);
                dataEditTextDate = itemView.findViewById(R.id.dataEditTextDate);
                dataEditTextTime = itemView.findViewById(R.id.dataEditTextTime);
                dataEditTextPlaces = itemView.findViewById(R.id.dataEditTextPlaces);
                dataEditTextNote = itemView.findViewById(R.id.dataEditTextNote);

                dataEditTextDay.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if(getAdapterPosition() > 0 && getAdapterPosition() <= scheduleData.size()){

                            ScheduleItem item = schedule.get(getAdapterPosition() - 1);
                            item.setDay(Integer.parseInt(charSequence.toString()));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                public void bindData(ScheduleItem item){

                    dataEditTextDay.setText(String.valueOf(item.getDay()));
                    dataEditTextDate.setText(item.getDate());
                    dataEditTextTime.setText(item.getTime());
                    dataEditTextPlaces.setText(item.getPlaces());
                    dataEditTextNote.setText(item.getNote());
                }

            }

        }


    }
}


