package com.example.itinerarybuddy.ui.notifications;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.BlogImage;

import java.util.ArrayList;

/**
 * Custom adapter made for the chat list for improved layout and design.
 */
public class ImageAdapter extends ArrayAdapter<BlogImage> {

    /**
     * Creates a new adapter. By default utilizes the image_card resource for the card.
     * @param context application context.
     */
    public ImageAdapter(@NonNull Context context) {
        super(context, R.layout.image_card);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        BlogImage blog = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.image_card, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.card_photo);

        if(blog != null){
            imageView.setImageBitmap(blog.getBitmap());
        }

        return view;
    }
}
