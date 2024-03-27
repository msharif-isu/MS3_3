package com.example.itinerarybuddy.ui.dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Post_Itinerary;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post_Itinerary> posts;
    private Context context;

    // Constructor to initialize the adapter with a list of posts
    public PostAdapter(List<Post_Itinerary> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    // ViewHolder class to hold the views for each post item
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;
        public TextView timePostedTextView;
        public TextView postFileTextView;
        public TextView captionTextView;


        public TextView commentsTextView;
        public ImageView commentIcon;

        public TextView likeCountView;
        public ImageView likeImageView;

        public TextView saveCountView;
        public ImageView saveImageView;





        public PostViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.text_username);
            timePostedTextView = itemView.findViewById(R.id.text_time_posted);
            postFileTextView = itemView.findViewById(R.id.text_post_file);
            captionTextView = itemView.findViewById(R.id.text_caption);

            commentIcon = itemView.findViewById(R.id.icon_comment);
            commentsTextView = itemView.findViewById(R.id.text_comments);

            likeImageView = itemView.findViewById(R.id.icon_like);
            likeCountView = itemView.findViewById(R.id.like_count);

            saveImageView = itemView.findViewById(R.id.icon_save);
            saveCountView = itemView.findViewById(R.id.save_count);
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        // Get the data model based on position
        Post_Itinerary post = posts.get(position);

        // Set the data for the views
        holder.usernameTextView.setText(post.getUsername());
        holder.timePostedTextView.setText(post.getTimePosted());
        holder.postFileTextView.setText(post.getPostFile());
        holder.captionTextView.setText(post.getCaption());
        holder.commentsTextView.setText(post.getComments().toString());

        holder.likeCountView.setText(String.valueOf(post.getLikeCount()));
        if (post.isLiked()) {
            holder.likeImageView.setImageResource(R.drawable.ic_like_after);
        } else {
            holder.likeImageView.setImageResource(R.drawable.ic_like_before);
        }

        holder.likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(post.isLiked()){

                    post.setLiked(false);
                    post.decreaseLikeCount();
                    holder.likeCountView.setText("Liked: "+String.valueOf(post.getLikeCount()));
                    holder.likeImageView.setImageResource(R.drawable.ic_like_before);
                }

                else{

                    post.setLiked(true);
                    post.increaseLikeCount();
                    holder.likeCountView.setText("Liked: " + String.valueOf(post.getLikeCount()));
                    holder.likeImageView.setImageResource(R.drawable.ic_like_after);

                }
            }
        });

        holder.commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog(holder, post);
            }
        });


        holder.saveCountView.setText(String.valueOf(post.getSavedCount()));
        if (post.isSaved()) {
            holder.saveImageView.setImageResource(R.drawable.ic_bookmark_aftr);
        } else {
            holder.saveImageView.setImageResource(R.drawable.ic_bookmark_bfr);
        }

        holder.saveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(post.isSaved()){

                    post.setSaved(false);
                    post.decreaseSaveCount();
                    holder.saveCountView.setText("Saved: " + String.valueOf(post.getSavedCount()));
                    holder.saveImageView.setImageResource(R.drawable.ic_bookmark_bfr);
                }

                else{

                    post.setSaved(true);
                    post.increaseSaveCount();
                    holder.saveCountView.setText("Saved: " + String.valueOf(post.getSavedCount()));
                    holder.saveImageView.setImageResource(R.drawable.ic_bookmark_aftr);

                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return posts.size();
    }

    //Method to show comment dialog
    private void showCommentDialog(PostViewHolder holder, Post_Itinerary post){

        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Add Comment");

        //Set the input
        final EditText input = new EditText(context);
        input.setHint("Enter your comment");
        builder.setView(input);


        // Set up the buttons
        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String comment = input.getText().toString().trim();
                if (!comment.isEmpty()) {
                    post.addComment(comment);
                    holder.commentsTextView.setText(post.getComments().toString());
                }
                dialog.dismiss();
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
}

