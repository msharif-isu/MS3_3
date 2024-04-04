package com.example.itinerarybuddy.activities;

import static android.content.Intent.*;

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

/**
 * The DayCardAdapter class is a RecyclerView adapter responsible for binding day titles and contents
 * to the corresponding views in the day card items.
 * <p>
 * It also handles item click events to open a schedule template activity for the selected day.
 */
public class DayCardAdapter extends RecyclerView.Adapter<DayCardAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> dayTitles;
    private ArrayList<String> dayContents;


    /**
     * Constructs a new DayCardAdapter.
     *
     * @param context The context in which the adapter will be used.
     * @param dayTitles The list of day titles.
     * @param dayContents The list of day contents.
     */

    private boolean isEditable;
    private String source;
    public DayCardAdapter(Context context, ArrayList<String> dayTitles, ArrayList<String> dayContents, boolean isEditable, String source) {

        this.context = context;
        this.dayTitles = dayTitles;
        this.dayContents = dayContents;
        this.isEditable = isEditable;
        this.source = source;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_card, parent, false);
        return new ViewHolder(view);
    }


    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTextView.setText(dayTitles.get(position));
        holder.contentTextView.setText(dayContents.get(position));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the data set.
     */
    @Override
    public int getItemCount() {
        return dayTitles.size();
    }


    /**
     * The ViewHolder class represents each item view in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        TextView contentTextView;

        /**
         * Constructs a new ViewHolder.
         *
         * @param itemView The view for each item in the RecyclerView.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewDayTitle);
            contentTextView = itemView.findViewById(R.id.textViewDayContent);

            // Set click listener on itemView
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                String title = dayTitles.get(position);
                Intent intent = new Intent(context, ScheduleTemplate.class);
                intent.putExtra("TITLE", title);

                //Retrieve extras from the intent that started DayCard
                intent.putExtra("IS_EDITABLE", isEditable);
                intent.putExtra("SOURCE", source);

                context.startActivity(intent);
            }
        }
    }
}

