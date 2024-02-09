package com.example.itinerarybuddy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        test = findViewById(R.id.test_view);
        test.setText("Test- this is the login page");
    }
}
