package com.example.itinerarybuddy.ui.dashboard;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.personalPage1;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.ActivityPersonalPage1Binding;
import com.example.itinerarybuddy.databinding.FragmentGroupPageBinding;
import com.example.itinerarybuddy.ui.dashboard.DashboardFragment;
import com.example.itinerarybuddy.ui.dashboard.ListGroups;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoadGroup extends AppCompatActivity {

    private Group group;


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
            group = ListGroups.adapter.getItem(position);

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
                startActivity(new Intent(getApplicationContext(), ListGroups.class));
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

                        if(item.getItemId() == R.id.action_group_details){
                            showGroupDetails();

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

    private void showGroupDetails(){

    }
}
