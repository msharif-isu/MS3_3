package com.example.itinerarybuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_DATA = 1;
    public List<String> data;

    public ScheduleAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if(viewType == VIEW_TYPE_HEADER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_header, parent, false);
        }

        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_data, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*if(getItemViewType(position) == VIEW_TYPE_DATA){
            String item = data.get(position-1);
            holder.bindData(item);
        }*/
    }


    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER: VIEW_TYPE_DATA;
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
            }

           /* int initialWidth = itemView.getResources().getDisplayMetrics().widthPixels/5;

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    initialWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            headerTextViewDay.setLayoutParams(params);
            headerTextViewDate.setLayoutParams(params);
            headerTextViewTime.setLayoutParams(params);
            headerTextViewPlaces.setLayoutParams(params);
            headerTextViewNote.setLayoutParams(params);

            dataEditTextDay.setLayoutParams(params);
            dataEditTextDate.setLayoutParams(params);
            dataEditTextTime.setLayoutParams(params);
            dataEditTextPlaces.setLayoutParams(params);
            dataEditTextNote.setLayoutParams(params);*/
        }

        public void bindData(String item) {
            if (dataEditTextDay != null) {
                // Customize data binding logic based on your data model
                // For simplicity, setting the same value to all columns
                dataEditTextDay.setText(item);
                dataEditTextDate.setText(item);
                dataEditTextTime.setText(item);
                dataEditTextPlaces.setText(item);
                dataEditTextNote.setText(item);
            }
        }


    }
}
