package com.example.itinerarybuddy.ui.dashboard;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    private View view;

    private boolean initialized;

    public DashboardViewModel() {
        initialized = false;
    }

    public View getView(){
        return view;
    }

    public void setView(View v){
        view = v;
    }

    public boolean isInitialized(){
        return initialized;
    }
}