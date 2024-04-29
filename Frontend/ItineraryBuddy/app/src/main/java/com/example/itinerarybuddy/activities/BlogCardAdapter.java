package com.example.itinerarybuddy.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.BlogItem;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.ui.notifications.NotificationsFragment;

import java.util.List;


public class BlogCardAdapter extends RecyclerView.Adapter<BlogCardAdapter.ViewHolder> {

    private List<BlogItem> blogItems;
    private Context context;

    private ImageView selectedImage;
    private Uri uploadImageUri;

    NotificationsFragment fragment;

    private OnItemClickListener mListener;

    public BlogCardAdapter(List<BlogItem> blogItems, Context context, NotificationsFragment fragment) {
        this.blogItems = blogItems;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_blog_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BlogItem cardItem = blogItems.get(position);
        holder.blogTitle.setText(cardItem.getTitle());
        holder.username.setText(cardItem.getUsername());
        holder.blogPostDate.setText(cardItem.getPostDate().toString());

        // Set span size based on position
       GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        layoutParams.width = layoutParams.width * (position % 2 == 0 ? 2 : 1);
        holder.itemView.setLayoutParams(layoutParams);


        downloadCoverImageById(cardItem.getBlogID(), holder.blogMainImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });


        if(UserData.getUsername().equals(cardItem.getUsername())) {
            holder.iconMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v, position);
                }

            });
        }

    }

    /**
     * Displays a popup menu for the options related to a post.
     *
     * @param view The anchor view for the popup menu.
     * @param position The position of the post in the RecyclerView.
     */
    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.itinerary_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_edit) {
                    fragment.EditClicked(position);
                    return true;
                } else if (item.getItemId() == R.id.action_delete) {
                    fragment.DeleteClicked(position);
                    return true;
                } else {
                    return false;
                }
            }
        });
        popupMenu.show();
    }

    private void downloadCoverImageById(int blogId, final ImageView imageView) {
        // Replace "YOUR_API_ENDPOINT" with your actual API endpoint for downloading cover images
        String url = "http://coms-309-035.class.las.iastate.edu:8080/BlogPost/CoverImage/" + blogId;

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        // Set the downloaded image to the ImageView
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        Log.e("Volley Error", "Error downloading cover image: " + error.toString());
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(context).add(imageRequest);
    }



    // Method to set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    // Interface for item click events
    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    /**
     * Interface definition for a callback to be invoked when a delete button is clicked.
     */
    public interface OnDeleteClickListener {

        void onDeleteClickedBlog(int position);
    }

    /**
     * Interface definition for a callback to be invoked when an edit button is clicked.
     */
    public interface OnEditClickListener {
        void onEditClickedBlog(int position);
    }

    public void setSelectedImage(Uri imageUri) {
        uploadImageUri = imageUri;
        // Update the UI to display the selected image if needed
        notifyDataSetChanged(); // This will trigger onBindViewHolder() to update the view
    }


    @Override
    public int getItemCount() {
        return blogItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView blogTitle;
        TextView username;
        TextView blogPostDate;
        ImageView blogMainImage;

        ImageView iconMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            blogTitle = itemView.findViewById(R.id.blogTitle);
            username = itemView.findViewById(R.id.blogUsername);
            blogPostDate = itemView.findViewById(R.id.blogPostDate);
            blogMainImage = itemView.findViewById(R.id.blogImage);
            iconMore = itemView.findViewById(R.id.iconBlogMore);
        }
    }
}
