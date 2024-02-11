package com.example.itinerarybuddy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

/**
 * This is where the users will register.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput;

    private EditText emailInput;

    private EditText passwordInput;

    private EditText confirmPasswordInput;

    private Button registerButton;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        //Instantiate needed fields by id
        usernameInput = findViewById(R.id.username_register_input);
        emailInput = findViewById(R.id.email_register_input);
        passwordInput = findViewById(R.id.password_register_input);
        confirmPasswordInput = findViewById(R.id.password_confirm_input);
        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.sign_in_here_button);

        //Set listener for this button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to the login page
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        //Set listener for this button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    /**
     * Extracts data from text inputs, forms it into a string, and is made into a JSON object and posted.
     */
    private void register(){

    }
}
