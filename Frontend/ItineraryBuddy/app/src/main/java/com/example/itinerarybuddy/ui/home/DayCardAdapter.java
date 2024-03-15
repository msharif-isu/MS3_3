package com.example.itinerarybuddy.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itinerarybuddy.R;

import java.util.ArrayList;

public class DayCardAdapter extends RecyclerView.Adapter<DayCardAdapter.ViewHolder> {

    private ArrayList<String> dayTitles;
    private ArrayList<String> dayContents;
    private Context context;

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
        holder.bind(dayTitles.get(position), dayContents.get(position));
    }

    @Override
    public int getItemCount() {
        return dayTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDayTitle;
        private TextView textViewDayContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDayTitle = itemView.findViewById(R.id.textViewDayTitle);
            textViewDayContent = itemView.findViewById(R.id.textViewDayContent);
        }

        public void bind(String dayTitle, String dayContent) {
            textViewDayTitle.setText(dayTitle);
            textViewDayContent.setText(dayContent);
        }
    }
}
