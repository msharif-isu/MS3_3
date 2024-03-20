package com.example.itinerarybuddy.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.DialogGroupDetailsBinding;

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
                        if(item.getItemId() == R.id.action_group_members){
                            showGroupMembers();
                            return true;
                        }
                        else if(item.getItemId() == R.id.action_edit_group){
                            //TODO: leave group
                            return true;
                        }
                        else if(item.getItemId() == R.id.action_edit_group){
                            if(UserData.getUsertype().equals("User")){
                                Toast.makeText(getApplicationContext(), "Only Travel Ambassadors can edit the group.", Toast.LENGTH_LONG).show();
                            }
                            else{
                                editGroup();
                            }
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

    private void showGroupMembers(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.layout.dialog_group_details);
        builder.setTitle("Travel Group Members");

        View v = DialogGroupDetailsBinding.inflate(getLayoutInflater()).getRoot();
        builder.setView(v);

        ListView members = v.findViewById(R.id.group_members_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, group.getTravelGroupMembers());
        members.setAdapter(adapter);
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void editGroup(){

    }
}
