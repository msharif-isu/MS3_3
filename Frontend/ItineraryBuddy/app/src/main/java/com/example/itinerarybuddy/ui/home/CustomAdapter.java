package com.example.itinerarybuddy.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Itinerary;

import java.util.List;

/**
 * CustomAdapter is an ArrayAdapter implementation for displaying a list of itineraries
 * in a ListView with customized layout.
 */
public class CustomAdapter extends ArrayAdapter<Itinerary> {

    private final List<Itinerary> itineraries; //List of itineraries to display
    private final Context context;          //Context of the application
    private final OnEditClickListener editClickListener; //Listener for edit button click events
    private final OnDeleteClickListener deleteClickListener; //Listener for delete button click events

    /**
     * Constructor for CustomAdapter.
     *
     * @param context            The context of the calling activity or fragment.
     * @param resource           The resource ID for a layout file containing a TextView to use when
     *                           instantiating views.
     * @param objects            The objects to represent in the ListView.
     * @param editClickListener Listener for edit button click events.
     * @param deleteClickListener Listener for delete button click events.
     */
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Itinerary> objects, OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener) {
        super(context, resource);
        this.context = context;
        this.itineraries = objects;
        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.).
     *
     * @param position    The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
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

        Itinerary itinerary = getItem(position);
        if (itinerary != null) {
            String itineraryInfo = "Destination: " + itinerary.getDestination() +
                    "\nStart Date: " + itinerary.getStartDate() +
                    "\nEnd Date: " + itinerary.getEndDate() +
                    "\nNumber of Days: " + itinerary.getNumDays();

            viewHolder.textViewItinerary.setText(itineraryInfo);
        }

        return convertView;
    }

    /**
     * Displays a popup menu when the icon is clicked.
     *
     * @param view     The anchor view for the popup menu.
     * @param position The position of the item clicked.
     */
    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.itinerary_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.action_edit){
                    editClickListener.onEditClicked(position);
                    return true;
                }

                else if(item.getItemId() == R.id.action_delete){
                    deleteClickListener.onDeleteClicked(position);
                    return true;
                }
                else{
                    return false;
                }
            }
        });
        popupMenu.show();
    }

    /**
     * Interface definition for a callback to be invoked when a delete button is clicked.
     */
    public interface OnDeleteClickListener {

        void onDeleteClicked(int position);
    }

    /**
     * Interface definition for a callback to be invoked when an edit button is clicked.
     */
    public interface OnEditClickListener {
        void onEditClicked(int position);
    }


    /**
     * ViewHolder class to hold views to avoid unnecessary calls to findViewById().
     */
    private static class ViewHolder {
        TextView textViewItinerary; // TextView to display itinerary information
        ImageView imageViewIcon;  // ImageView for popup menu
    }
}

