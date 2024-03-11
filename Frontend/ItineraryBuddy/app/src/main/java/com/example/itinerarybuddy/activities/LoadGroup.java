package com.example.itinerarybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;

public class LoadGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.fragment_group_page);

        ImageButton back = findViewById(R.id.back_button);
        ImageButton groupOptions = findViewById(R.id.options_button);
        ImageButton groupChat = findViewById(R.id.chat_button);

        Bundle bundle = getIntent().getExtras();
        int position;
        if(bundle != null) {
            position = Integer.parseInt(bundle.getString("POSITION"));
            Group group = UserData.adapter.getItem(position);

            TextView name = findViewById(R.id.group_title);
            TextView description = findViewById(R.id.group_description);
            TextView destination = findViewById(R.id.group_destination);

            assert group != null;
            name.setText(group.getTravelGroupName());
            description.setText(group.getTravelGroupDescription());
            String destinationText = "Traveling to: " + group.getTravelGroupDestination();
            destination.setText(destinationText);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TestGroup.class));
            }
        });

        groupOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: group options listener
            }
        });

        groupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: group chat listener
            }
        });

    }
}
