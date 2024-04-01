package com.example.itinerarybuddy.ui.dashboard;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.DayCard;
import com.example.itinerarybuddy.data.Itinerary;
import com.example.itinerarybuddy.data.Post_Itinerary;
import com.example.itinerarybuddy.data.Spinner_ItineraryInfo;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.ui.home.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private RequestQueue requestQueue;
    private List<Post_Itinerary> posts;
    private Context context;
    private DashboardFragment fragment;

    // Constructor to initialize the adapter with a list of posts
    public PostAdapter(List<Post_Itinerary> posts, Context context, DashboardFragment fragment) {
        this.posts = posts;
        this.context = context;
        this.fragment = fragment;

        requestQueue = Volley.newRequestQueue(context);
    }

    // ViewHolder class to hold the views for each post item
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;
        public TextView postFileTextView;
        public TextView captionTextView;

        public TextView commentsTextView;
        public ImageView commentIcon;
        public TextView moreComments;

        public TextView likeCountView;
        public ImageView likeImageView;

        public TextView saveCountView;
        public ImageView saveImageView;

        public ImageView moreView;


        public PostViewHolder(View itemView) {
            super(itemView);

            usernameTextView = itemView.findViewById(R.id.text_username);
            postFileTextView = itemView.findViewById(R.id.text_post_file);
            captionTextView = itemView.findViewById(R.id.text_caption);

            commentIcon = itemView.findViewById(R.id.icon_comment);
            commentsTextView = itemView.findViewById(R.id.text_comments);
            moreComments = itemView.findViewById(R.id.more_comments);

            likeImageView = itemView.findViewById(R.id.icon_like);
            likeCountView = itemView.findViewById(R.id.like_count);

            saveImageView = itemView.findViewById(R.id.icon_save);
            saveCountView = itemView.findViewById(R.id.save_count);

            moreView = itemView.findViewById(R.id.icon_more);

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
    public void onBindViewHolder(@NonNull PostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Get the data model based on position
        Post_Itinerary post = posts.get(position);

        // Set the data for the views
        holder.usernameTextView.setText(post.getUsername());
        holder.postFileTextView.setText(post.getPostFile());
        holder.captionTextView.setText(post.getCaption());
        holder.commentsTextView.setText(post.getComments().toString());


        // Set OnClickListener for postFileTextView to start DayCard activity
        holder.postFileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtain the context from the adapter
                Context context = holder.itemView.getContext();

                // Use the context to start the activity
                Intent intent = new Intent(context, DayCard.class);

                // Pass the number of days to the intent
                intent.putExtra("NUM_OF_DAYS", post.getDays());
                intent.putExtra("IS_EDITABLE", false);
                intent.putExtra("SOURCE", "Community");
                context.startActivity(intent);
            }
        });


    // Show only first 2 comments initially
        StringBuilder seeComment = new StringBuilder();
        List<Post_Itinerary.Comment> showComment = post.getComments();
        int numCommentsToShow = Math.min(2, showComment.size());
        for (int i = 0; i < numCommentsToShow; i++) {
            Post_Itinerary.Comment comment = showComment.get(i);
            seeComment.append("<b>")
                    .append(comment.getUsername())
                    .append("</b>  ")
                    .append(comment.getCommentText())
                    .append("<br>");
        }
        holder.commentsTextView.setText(android.text.Html.fromHtml(seeComment.toString()));

        // If there are more than 2 comments, show "View more comments" text
        if (showComment.size() > 2) {
            holder.moreComments.setVisibility(View.VISIBLE);
            holder.moreComments.setText("View all comments");
        } else {
            holder.moreComments.setVisibility(View.GONE);
        }

        // Set click listener for "View More Comments" text
        holder.moreComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show all comments
                StringBuilder allCommentsText = new StringBuilder();
                for (Post_Itinerary.Comment comment : showComment) {
                    allCommentsText.append("<b>")
                            .append(comment.getUsername())
                            .append("</b>  ")
                            .append(comment.getCommentText())
                            .append("<br>");
                }
                holder.commentsTextView.setText(android.text.Html.fromHtml(allCommentsText.toString()));
                holder.moreComments.setVisibility(View.GONE); // Hide "View more comments" text after showing all comments
            }
        });

        holder.likeCountView.setText(String.valueOf(post.getLikeCount()));

        if (post.isLiked()) {
            holder.likeImageView.setImageResource(R.drawable.ic_like_after);
        } else {
            holder.likeImageView.setImageResource(R.drawable.ic_like_before);
        }

        holder.likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (post.isLiked()) {

                    post.setLiked(false);
                    post.decreaseLikeCount();
                    holder.likeCountView.setText("Liked: " + String.valueOf(post.getLikeCount()));
                    holder.likeImageView.setImageResource(R.drawable.ic_like_before);
                } else {

                    post.setLiked(true);
                    post.increaseLikeCount();
                    holder.likeCountView.setText("Liked: " + String.valueOf(post.getLikeCount()));
                    holder.likeImageView.setImageResource(R.drawable.ic_like_after);

                }

                PUT_updateLikeCount(post);
            }
        });

        holder.commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog(holder, post);
            }
        });

        holder.saveCountView.setText(String.valueOf(post.getSaveCount()));
        if (post.isSaved()) {
            holder.saveImageView.setImageResource(R.drawable.ic_bookmark_aftr);
        } else {
            holder.saveImageView.setImageResource(R.drawable.ic_bookmark_bfr);
        }

        holder.saveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (post.isSaved()) {

                    post.setSaved(false);
                    post.decreaseSaveCount();
                    holder.saveCountView.setText("Saved: " + String.valueOf(post.getSaveCount()));
                    holder.saveImageView.setImageResource(R.drawable.ic_bookmark_bfr);

                    POST_updateSavedPost(post);

                } else {

                    post.setSaved(true);
                    post.increaseSaveCount();
                    holder.saveCountView.setText("Saved: " + String.valueOf(post.getSaveCount()));
                    holder.saveImageView.setImageResource(R.drawable.ic_bookmark_aftr);

                    POST_updateSavedPost(post);
                }

                PUT_updateSaveCount(post);
            }

        });

        holder.moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, position);
            }
        });

    }

    private void POST_updateSavedPost(Post_Itinerary post) {

        //String url = "http://coms-309-035.class.las.iastate.edu:8080/Itinerary/Share/" + username + post.getPostID();
        String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share/SavedPost";

        JSONObject postData = new JSONObject();

        try {
            // Add the data of the post
            postData.put("username", post.getUsername());
            postData.put("postFile", post.getPostFile());
            postData.put("postID", post.getPostID());
            postData.put("tripCode", post.getTripCode());
            postData.put("number of days", post.getDays());
            postData.put("caption", post.getCaption());
            postData.put("likeCount", post.getLikeCount());
            postData.put("saveCount", post.getSaveCount());


            // Convert comments list to JSON array
            JSONArray commentsArray = new JSONArray();
            for (Post_Itinerary.Comment comment : post.getComments()) {
                JSONObject commentObject = new JSONObject();
                commentObject.put("username", comment.getUsername());
                commentObject.put("commentText", comment.getCommentText());
                commentsArray.put(commentObject);
            }
            postData.put("comments", commentsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int method;

        if(post.isSaved()){

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Volley Response Saved: ", response.toString());

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Error Post Saved: ", error.toString());
                        }
                    });

            requestQueue.add(jsonObjectRequest);

        }

        else{

           // url += post.getPostID();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, postData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Volley Response Delete Saved:", response.toString());
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Error Delete Saved Post: ", error.toString());
                        }
                    });

            requestQueue.add(jsonObjectRequest);
        }



    }

    private void PUT_updateSaveCount(Post_Itinerary post) {

       // String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share/SaveCount" + post.getPostID();

        String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share/SaveCount";

        JSONObject requestData = new JSONObject();

        try{
            requestData.put("saveCount", post.getSaveCount());

        } catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Update Save Count", "Success");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Update Save Count Error", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void PUT_updateLikeCount(Post_Itinerary post) {


        //String url = "" + post.getPostID();
        String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share/LikeCount";

        JSONObject requestData = new JSONObject();

        try{
            requestData.put("likeCount", post.getSaveCount());

        } catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Update Like Count", "Success");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Update Like Count Error", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    // Return the size of your dataset (invoked by the layout manager)
    private void showCommentDialog(PostViewHolder holder, Post_Itinerary post) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Comment");

        // Set the input field
        final EditText input = new EditText(context);
        input.setHint("Enter your comment");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String commentText = input.getText().toString().trim();
                if (!commentText.isEmpty()) {

                    //String username = UserData.getUsername();
                    String username = "Aina";
                    post.addComment(username, commentText);
                    notifyDataSetChanged();

                    POST_addComment(post, username, commentText);
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

    private void POST_addComment(Post_Itinerary post, String username, String commentText) {

        String url = "https://1064bd8c-7f0f-4802-94f1-71b8b5568975.mock.pstmn.io/Itinerary/Share/Comment";

        JSONObject commentData = new JSONObject();

        try{
            commentData.put("username", username);
            commentData.put("commentText", commentText);
        }

        catch(JSONException e){

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, commentData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Add Comment Response", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Add comment Error", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

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


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public interface OnItemClickListener {
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }

}



