package com.example.itinerarybuddy.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itinerarybuddy.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<String> itineraries;

    public PostAdapter(Context context, List<String> itineraries) {
        this.context = context;
        this.itineraries = itineraries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_itinerary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String itinerary = itineraries.get(position);
        holder.bind(itinerary);
    }

    @Override
    public int getItemCount() {
        return itineraries.size();
    }

    public void updateData(List<String> newItineraries) {
        itineraries.clear();
        itineraries.addAll(newItineraries);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itineraryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itineraryTextView = itemView.findViewById(R.id.itineraryTextView);
        }

        public void bind(String itinerary) {
            itineraryTextView.setText(itinerary);
        }
    }
}
