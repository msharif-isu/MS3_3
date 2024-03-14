package com.example.itinerarybuddy.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.itinerarybuddy.R;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    private List<String> itineraries;
    private Context context;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    private static final int MENU_EDIT = 1;
    private static final int MENU_DELETE = 2;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener) {
        super(context, resource, objects);
        this.context = context;
        this.itineraries = objects;
        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewItinerary = convertView.findViewById(R.id.textViewItinerary);
            viewHolder.imageViewIcon = convertView.findViewById(R.id.iconPopUp);

            viewHolder.imageViewIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v, position); // Call showPopupMenu when the image is clicked
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String itineraryInfo = getItem(position);
        if (itineraryInfo != null) {
            String[] itineraryData = itineraryInfo.split("\n");

            // Set itinerary information
            StringBuilder fullItinerary = new StringBuilder();
            for (String data : itineraryData) {
                fullItinerary.append(data).append("\n");
            }
            viewHolder.textViewItinerary.setText(fullItinerary.toString().trim());
        }


        return convertView;
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.itinerary_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case MENU_EDIT:
                        editClickListener.onEditClicked(position);
                        return true;
                    case MENU_DELETE:
                        deleteClickListener.onDeleteClicked(position);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public interface OnDeleteClickListener {
        void onDeleteClicked(int position);
    }

    public interface OnEditClickListener {
        void onEditClicked(int position);
    }

    private static class ViewHolder {
        TextView textViewItinerary;
        ImageView imageViewIcon;
    }
}

