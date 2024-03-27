package com.example.itinerarybuddy.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.itinerarybuddy.data.Post_Itinerary;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<List<Post_Itinerary>> postsLiveData;
    private final MutableLiveData<List<String>> destinationsLiveData;


    public DashboardViewModel() {
        postsLiveData = new MutableLiveData<List<Post_Itinerary>>();
        destinationsLiveData = new MutableLiveData<>();
        // Load initial data when ViewModel is created
        loadPosts();
        loadDestinations();
    }

    public LiveData<List<Post_Itinerary>> getPosts() {
        return postsLiveData;
    }

    public LiveData<List<String>> getDestinations() {
        return destinationsLiveData;
    }

    private void loadPosts() {
        // Call repository method to fetch posts asynchronously
      /*  postRepository.fetchPosts(new PostRepository.PostsCallback() {
            @Override
            public void onSuccess(List<Post_Itinerary> posts) {
                // Update LiveData with fetched posts
                postsLiveData.setValue(posts);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error if fetching posts fails
            }
        });*/
    }

    private void loadDestinations() {
        // Call repository method to fetch destinations asynchronously
       /* destinationRepository.fetchDestinations(new DestinationRepository.DestinationsCallback() {
            @Override
            public void onSuccess(List<String> destinations) {
                // Update LiveData with fetched destinations
                destinationsLiveData.setValue(destinations);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error if fetching destinations fails
            }
        });
    }*/
    }
}
