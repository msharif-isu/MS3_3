package com.example.itinerarybuddy.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.BlogItem;

import java.util.List;

public class BlogCardAdapter extends RecyclerView.Adapter<BlogCardAdapter.ViewHolder> {

    private List<BlogItem> blogItems;

    public BlogCardAdapter(List<BlogItem> blogItems){

        this.blogItems = blogItems;
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
    }

    @Override
    public int getItemCount() {
        return blogItems.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView blogTitle;
        TextView username;
        TextView blogPostDate;
        ImageView imageUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            blogTitle = itemView.findViewById(R.id.blogTitle);
            username = itemView.findViewById(R.id.blogUsername);
            blogPostDate = itemView.findViewById(R.id.blogPostDate);
            imageUrl = itemView.findViewById(R.id.blogImage);

        }
    }
}
