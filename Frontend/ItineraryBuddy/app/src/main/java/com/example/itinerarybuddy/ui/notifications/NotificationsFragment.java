package com.example.itinerarybuddy.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewBlogPost);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // Use requireContext() instead of 'this'

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()); // Use Locale.getDefault() for consistent formatting across devices
        String formattedDate = dateFormat.format(currentDate);

        List<BlogItem> cardItems = new ArrayList<>();
     //   cardItems.add(new BlogItem("Title 1", UserData.getUsername(), formattedDate));


        cardItems.add(new BlogItem("Title 1", "Aina", formattedDate));

        cardItems.add(new BlogItem("Title 2", "Aina", formattedDate));

        cardItems.add(new BlogItem("Title 3", "Aina", formattedDate));

       // cardItems.add(new BlogItem("Title 4", "Aina", formattedDate));


        BlogCardAdapter adapter = new BlogCardAdapter(cardItems);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}