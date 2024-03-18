package com.example.itinerarybuddy.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itinerarybuddy.R;

import java.util.ArrayList;

public class DayCardAdapter extends RecyclerView.Adapter<DayCardAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> dayTitles;
    private ArrayList<String> dayContents;

    public DayCardAdapter(Context context, ArrayList<String> dayTitles, ArrayList<String> dayContents) {
        this.context = context;
        this.dayTitles = dayTitles;
        this.dayContents = dayContents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTextView.setText(dayTitles.get(position));
        holder.contentTextView.setText(dayContents.get(position));
    }

    @Override
    public int getItemCount() {
        return dayTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        TextView contentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewDayTitle);
            contentTextView = itemView.findViewById(R.id.textViewDayContent);

            // Set click listener on itemView
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                String title = dayTitles.get(position);
                Intent intent = new Intent(context, ScheduleTemplate.class);
                intent.putExtra("TITLE", title);
                context.startActivity(intent);
            }
        }
    }
}

