package com.example.itinerarybuddy.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.BlogItem;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BlogCardAdapter extends RecyclerView.Adapter<BlogCardAdapter.ViewHolder> {

    private List<BlogItem> blogItems;
    private Context context;

    private ImageView selectedImage;
    private Uri uploadImageUri;

    public BlogCardAdapter(List<BlogItem> blogItems, Context context) {
        this.blogItems = blogItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_blog_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BlogItem cardItem = blogItems.get(position);
        holder.blogTitle.setText(cardItem.getTitle());
        holder.username.setText(cardItem.getUsername());
        holder.blogPostDate.setText(cardItem.getPostDate().toString());

        // Set span size based on position
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        layoutParams.width = layoutParams.width * (position % 2 == 0 ? 2 : 1);
        holder.itemView.setLayoutParams(layoutParams);

        // Set OnClickListener for blogMainImage ImageView
        holder.blogMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
    }



    private void openImagePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Main Image for Blog");
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_blog_image, null);
        builder.setView(dialogView);

        selectedImage = dialogView.findViewById(R.id.selected_blog_image);
        Button selectImageButton = dialogView.findViewById(R.id.select_blog_image_button);
        Button deleteImageButton = dialogView.findViewById(R.id.delete_blog_image_button);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to select image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ((Activity) context).startActivityForResult(intent, 1);
            }
        });

        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage.setImageResource(android.R.color.transparent); // Reset the image
                uploadImageUri = null; // Reset the upload image URI
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (uploadImageUri != null) {
                    byte[] imageData = uriToImage(uploadImageUri);
                    imageMethod(imageData, Request.Method.PUT);
                } else {
                    Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private byte[] uriToImage(Uri imageUri) {
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

    private void imageMethod(byte[] data, int method) {
        // Implement your image upload logic here
        // For example, you can make a network request to upload the image
        // Replace the following placeholder code with your actual implementation

        // String url = "YOUR_IMAGE_UPLOAD_ENDPOINT";
        // CustomImageRequest request = new CustomImageRequest(url, data,
        //     new Response.Listener<NetworkResponse>() {
        //         @Override
        //         public void onResponse(NetworkResponse networkResponse) {
        //             // Handle successful image upload
        //         }
        //     },
        //     new Response.ErrorListener() {
        //         @Override
        //         public void onErrorResponse(VolleyError volleyError) {
        //             // Handle error
        //         }
        //     });
        // Singleton.getInstance(context.getApplicationContext()).addToRequestQueue(request);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            blogTitle = itemView.findViewById(R.id.blogTitle);
            username = itemView.findViewById(R.id.blogUsername);
            blogPostDate = itemView.findViewById(R.id.blogPostDate);
            blogMainImage = itemView.findViewById(R.id.blogImage);
        }
    }
}
