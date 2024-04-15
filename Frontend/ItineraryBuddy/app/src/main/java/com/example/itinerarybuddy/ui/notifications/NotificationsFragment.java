package com.example.itinerarybuddy.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.BlogCardAdapter;
import com.example.itinerarybuddy.data.BlogItem;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.FragmentNotificationsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private RecyclerView recyclerView;

    private BlogCardAdapter postBlogAdapter;
    List<BlogItem> cardItems = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewBlogPost);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // Use requireContext() instead of 'this'


        postBlogAdapter = new BlogCardAdapter(cardItems);
        recyclerView.setAdapter(postBlogAdapter);

        ImageView postButton = root.findViewById(R.id.postBlog);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPostBlogDialog();
            }
        });




        return root;
    }

    private void showPostBlogDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Post Picture Blog");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_post_blog, null);
        builder.setView(dialogView);

        EditText titleEditText = dialogView.findViewById(R.id.blogPostTitle);

        builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()); // Use Locale.getDefault() for consistent formatting across devices
                String formattedDate = dateFormat.format(currentDate);

                String BlogTitle = titleEditText.getText().toString();

                cardItems.add(new BlogItem(BlogTitle, "Aina", formattedDate));
                postBlogAdapter.notifyItemInserted(0);
                postBlogAdapter.notifyDataSetChanged();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}