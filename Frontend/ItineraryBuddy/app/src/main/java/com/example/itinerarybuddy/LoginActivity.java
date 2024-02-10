package com.example.itinerarybuddy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

/**
 * This is the login page.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Input field for username.
     */
    private EditText usernameInput;

    /**
     * Input field for password.
     */
    private EditText passwordInput;

    /**
     * Button for login.
     */
    private Button loginButton;

    /**
     * Button for sign up page.
     */
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.create_account_button);

    }
}
