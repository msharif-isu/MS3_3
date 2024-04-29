package com.example.itinerarybuddy.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
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

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.BlogItem;
import com.example.itinerarybuddy.ui.notifications.NotificationsFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BlogCardAdapter extends RecyclerView.Adapter<BlogCardAdapter.ViewHolder> {

    private List<BlogItem> blogItems;
    private Context context;

    private ImageView selectedImage;
    private Uri uploadImageUri;

    NotificationsFragment fragment;

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
        

       /* if (uploadImageUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uploadImageUri);
                holder.blogMainImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            // Set a default image or clear the ImageView if no image is selected
            holder.blogMainImage.setImageResource(R.drawable.add_a_photo); // Change this to your default image resource
        }


        // Set OnClickListener for blogMainImage ImageView
        holder.blogMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        holder.iconMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, position);
            }

        });*/
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


    public byte[] uriToImage(Uri imageUri) {
        byte[] bytes = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            bytes = byteArrayOutputStream.toByteArray();
            inputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
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
