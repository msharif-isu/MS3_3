package com.example.itinerarybuddy.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.DialogCreateGroupBinding;
import com.example.itinerarybuddy.databinding.DialogGroupDetailsBinding;
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                            showGroupDetails();
                            return true;
                        }
                        else if(item.getItemId() == R.id.action_leave_group){
                            leaveGroupDialog();
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

    private void showGroupDetails(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.layout.dialog_group_details);
        builder.setTitle("Travel Group Details");

        View v = DialogGroupDetailsBinding.inflate(getLayoutInflater()).getRoot();
        builder.setView(v);

        ListView members = v.findViewById(R.id.group_members_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, group.getMembers());
        members.setAdapter(adapter);

        TextView code = v.findViewById(R.id.group_code_label);
        String codeText = "Group Code: " + group.getTravelGroupCode() + "\n(Share this code with others to join this group)";
        code.setText(codeText);

        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void editGroup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.layout.dialog_create_group);
        builder.setTitle("Edit Travel Group");

        View v = DialogCreateGroupBinding.inflate(getLayoutInflater()).getRoot();
        builder.setView(v);

        EditText nameInput = v.findViewById(R.id.group_name_input);
        nameInput.setText(group.getTravelGroupName());
        EditText destinationInput = v.findViewById(R.id.group_destination_input);
        destinationInput.setText(group.getTravelGroupDestination());
        EditText descriptionInput = v.findViewById(R.id.group_description_input);
        descriptionInput.setText(group.getTravelGroupDescription());

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameInput.getText().toString();
                String destination = destinationInput.getText().toString();
                String description = descriptionInput.getText().toString();
                updateGroup(name, destination, description);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updateGroup(String name, String destination, String description){
        // Create JSON for group, along with array for group members
        JSONObject groupData = new JSONObject();
        JSONArray memberData = new JSONArray();
        ArrayList<String> members = group.getMembers();
        for(int i = 0; i < members.size(); i++){
            JSONObject json = new JSONObject();
            try {
                json.put("userName", members.get(i));
                memberData.put(i, json);
            }catch(JSONException e){
                Log.e("JSON Error: ", e.toString());
            }
        }
        try {
            groupData.put("travelGroupName", name);
            groupData.put("travelGroupCode", group.getTravelGroupCode());
            groupData.put("travelGroupDestination", destination);
            groupData.put("travelGroupCreator", group.getTravelGroupCreator());
            groupData.put("travelGroupDescription", description);
            groupData.put("travelGroupMembers", memberData);
        }catch(JSONException e){
            Log.e("JSON Error: ", e.toString());
        }

        // Make the post request given the url and group json
        final String url = "http://coms-309-035.class.las.iastate.edu:8080/Group/Update";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, groupData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());
                Toast.makeText(getApplicationContext(), "Group Created!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error: ", error.toString());
                Toast.makeText(getApplicationContext(), "Error Updating Group!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                HashMap<String, String> g = new HashMap<>();
                g.put("travelGroupName", name);
                g.put("travelGroupCode", group.getTravelGroupCode());
                g.put("travelGroupDestination", destination);
                g.put("travelGroupCreator", group.getTravelGroupCreator());
                g.put("travelGroupDescription", description);
                g.put("travelGroupMembers", "\"travelGroupMembers\": []");
                return g;
            }

            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");
                return map;
            }
        };
        Singleton.getInstance(getApplicationContext()).addRequest(req);

        recreate();
    }

    private void leaveGroupDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to leave this travel group?");

        builder.setPositiveButton("Leave Group", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                leaveGroup();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void leaveGroup(){

    }
}
