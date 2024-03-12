package com.example.itinerarybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.ui.dashboard.DashboardFragment;
import com.example.itinerarybuddy.ui.dashboard.TestGroup;

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
                startActivity(new Intent(getApplicationContext(), personalPage1.class));
            }
        });

        groupOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(getApplicationContext(), v);
                MenuInflater inflater = menu.getMenuInflater();

                inflater.inflate(R.menu.group_settings_menu, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.action_view_members){
                            //TODO: group member
                            return true;
                        }

                        else if(item.getItemId() == R.id.action_leave_group){
                            //TODO: leave group
                            return true;
                        }

                        else{
                            return false;
                        }
                    }
                });

                menu.show();

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
