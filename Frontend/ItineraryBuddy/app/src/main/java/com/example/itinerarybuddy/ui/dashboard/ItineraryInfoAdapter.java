package com.example.itinerarybuddy.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.itinerarybuddy.data.Itinerary;

import java.util.List;

public class ItineraryInfoAdapter extends ArrayAdapter<Itinerary> {
    private final LayoutInflater inflater;

    public ItineraryInfoAdapter(Context context, List<Itinerary> itineraryInfos) {
        super(context, 0, itineraryInfos);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        // Customize how each item in the spinner is displayed
        TextView textView = convertView.findViewById(android.R.id.text1);
        Itinerary itineraryInfo = getItem(position);
        if (itineraryInfo != null) {
            textView.setText(itineraryInfo.getDestination());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}

