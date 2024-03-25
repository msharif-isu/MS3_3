package com.example.itinerarybuddy.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.personalPage1;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.DialogAddGroupBinding;
import com.example.itinerarybuddy.databinding.DialogCreateGroupBinding;
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class ListGroups extends AppCompatActivity {

    /**
     * Adapter for groups list.
     */
    protected static ArrayAdapter<Group> adapter;

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.fragment_group_list);

        list = findViewById(R.id.group_list);

        // Initialize adapter by configuring it with group data from UserData class
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        UserData.queue = Volley.newRequestQueue(getApplicationContext());
        initializeGroups();

        /*
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        */
        list.setAdapter(adapter);

        // Click on any group listed to load its unique page
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Pass in the index of the group selected from the adapter to the next page so displayed group is correct
                Intent i = new Intent(getApplicationContext(), LoadGroup.class);
                Bundle bundle = new Bundle();
                bundle.putString("POSITION", Integer.valueOf(position).toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        // Show dialog to add a group via code
        ImageButton addGroup = findViewById(R.id.add_group_button);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroupDialog();
            }
        });

        // Show dialog to create a group
        ImageButton createGroup = findViewById(R.id.create_group_button);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserData.getUsertype().equals("User")){
                    // Only travel ambassadors may create a group
                    Toast.makeText(getApplicationContext(), "Only Travel Ambassadors can create a group.", Toast.LENGTH_LONG).show();
                }
                else{
                    createGroupDialog();
                }
            }
        });

        // Back to the main page
        ImageButton backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), personalPage1.class));
            }
        });

    }

    private void initializeGroups(){
        ArrayList<String> ids = UserData.getGroupIds();
        String url;
        String id;
        for(int i = 0; i < ids.size(); i++){
            id = ids.get(i);
            url = "http://coms-309-035.class.las.iastate.edu:8080/Group/" + id;
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Volley Response: ", response.toString());
                    appendAdapter(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley Error: ", error.toString());
                }
            });
            Singleton.getInstance(getApplicationContext()).addRequest(req);
        }
    }

    protected static void appendAdapter(JSONObject response){
        String groupName = UserData.getGroupName(response);
        String groupCode = UserData.getGroupCode(response);
        String groupDestination = UserData.getGroupDestination(response);
        String groupDescription = UserData.getGroupDescription(response);
        ArrayList<String> members = UserData.getGroupMembers(response);
        Group g = new Group(groupName, groupCode, groupDestination, groupDescription, members);
        adapter.add(g);
    }

    /**
     * Creates popup dialog where the user can submit a group code to join.
     */
    private void addGroupDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Join a Travel Group");

        View v = DialogAddGroupBinding.inflate(getLayoutInflater()).getRoot();
        builder.setView(v);

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText codeInput = v.findViewById(R.id.group_code_input);
                String id = codeInput.getText().toString();
                joinGroup(id);
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

    /**
     * Creates popup dialog where the Ambassador can create a group.
     */
    private void createGroupDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create a Travel Group");

        View v = DialogCreateGroupBinding.inflate(getLayoutInflater()).getRoot();
        builder.setView(v);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText nameInput = v.findViewById(R.id.group_name_input);
                String name = nameInput.getText().toString();
                EditText destinationInput = v.findViewById(R.id.group_destination_input);
                String destination = destinationInput.getText().toString();
                EditText descriptionInput = v.findViewById(R.id.group_description_input);
                String description = descriptionInput.getText().toString();

                createGroup(name, destination, description);
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

    private void joinGroup(String code){
        String user = UserData.getUsername();
        final String url = "http://coms-309-035.class.las.iastate.edu:8080/Group/AddUser/" + code + "/" + user;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());
                UserData.updateUserData();
                Toast.makeText(getApplicationContext(), "Group Joined!", Toast.LENGTH_LONG).show();
                updateList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error: ", error.toString());
                Toast.makeText(getApplicationContext(), "Error Joining Group!", Toast.LENGTH_LONG).show();
            }
        });
        Singleton.getInstance(getApplicationContext()).addRequest(req);
    }

    /**
     * Makes a post request to store the new group on the backend.
     * @param name name of travel group.
     * @param destination destination of travel travel group.
     * @param description description of travel travel group.
     */
    private void createGroup(String name, String destination, String description){
        // Create JSON for group, along with array for group members
        JSONObject group = new JSONObject();
        JSONArray members = new JSONArray();
        try {
            group.put("travelGroupName", name);
            group.put("travelGroupCode", "null");
            group.put("travelGroupDestination", destination);
            group.put("travelGroupAmbassador", UserData.getUsername());
            group.put("travelGroupDescription", description);
            group.put("members", members);
        }catch(JSONException e){
            Log.e("JSON Error: ", e.toString());
        }

        // Make the post request given the url and group json
        final String url = "http://coms-309-035.class.las.iastate.edu:8080/Group";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, group, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());
                UserData.updateUserData();
                Toast.makeText(getApplicationContext(), "Group Created!", Toast.LENGTH_LONG).show();
                updateList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error: ", error.toString());
                Toast.makeText(getApplicationContext(), "Error Creating Group!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                HashMap<String, String> group = new HashMap<>();
                group.put("travelGroupName", name);
                group.put("travelGroupCode", "null");
                group.put("travelGroupDestination", destination);
                group.put("travelGroupCreator", UserData.getUsername());
                group.put("travelGroupDescription", description);
                group.put("travelGroupMembers", "\"travelGroupMembers\": []");
                return group;
            }

            @Override
            public Map<String, String> getHeaders(){
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");
                return map;
            }
        };
        Singleton.getInstance(getApplicationContext()).addRequest(req);
    }

    protected void updateList(JSONObject response){
        appendAdapter(response);
        adapter.notifyDataSetChanged();
        adapter.getItem(adapter.getCount()-1);
        list.performItemClick(list, adapter.getCount()-1, (adapter.getItemId(adapter.getCount()-1)));
    }
}
