package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable
    private Button bBlue; //Button to swap colors!
    private Button bOrange; //Button to swap colors!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);             // link to Main activity XML

        /* initialize UI elements */
        messageText = findViewById(R.id.main_msg_txt);      // link to message textview in the Main activity XML
        messageText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        messageText.setText("Hello World! Justin was here!");

        //Instantiate buttons and change their colors
        bBlue = findViewById(R.id.bBlue);
        bBlue.setBackgroundColor(getColor(R.color.blue));
        bOrange = findViewById(R.id.bOrange);
        bOrange.setBackgroundColor(getColor(R.color.orange));

        //Set action listeners so that when either button is clicked, the text changes to their color
        bBlue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                messageText.setTextColor(getColor(R.color.blue));
            }
        });

        bOrange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                messageText.setTextColor(getColor(R.color.orange));
            }
        });
    }
}