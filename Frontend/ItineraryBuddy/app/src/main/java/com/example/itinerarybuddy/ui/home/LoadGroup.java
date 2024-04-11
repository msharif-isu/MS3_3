package com.example.itinerarybuddy.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.DayCard;
import com.example.itinerarybuddy.activities.ScheduleTemplate;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.DialogCreateGroupBinding;
import com.example.itinerarybuddy.databinding.DialogGroupDetailsBinding;
import com.example.itinerarybuddy.databinding.DialogSelectImageBinding;
import com.example.itinerarybuddy.util.CustomImageRequest;
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This is where a specific group can be loaded. Will contain group details, settings, chat, etc.
 */
public class LoadGroup extends AppCompatActivity {

    /**
     * This is the travel group object that this page represents. Passed in from the ListGroups list adapter and displayed here.
     */
    protected static Group group;

    protected static int index;

    /**
     * Prompt for selecting image from library.
     */
    private ActivityResultLauncher<String> getImage;

    /**
     * Cover image within the main group page.
     */
    private ImageView groupImage;

    /**
     * Image view for the image selection page in settings.
     */
    private ImageView selectImage;

    /**
     * URI associated with selected image to upload.
     */
    private Uri uploadImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.fragment_group_page);

        // Instantiate buttons
        ImageButton back = findViewById(R.id.back_button);
        ImageButton groupOptions = findViewById(R.id.options_button);
        ImageButton chat = findViewById(R.id.chat_button);
        groupImage = findViewById(R.id.group_image);
        LinearLayout itinerary = findViewById(R.id.itinerary_details);

        // Extract the group from the previous activity using bundle
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            index = Integer.parseInt(Objects.requireNonNull(bundle.getString("POSITION")));
            group = ListGroups.adapter.getItem(index);

            // Instantiate text views
            TextView name = findViewById(R.id.group_title);
            TextView description = findViewById(R.id.group_description);
            TextView destination = findViewById(R.id.group_destination);

            // Set data to the various views
            assert group != null;
            name.setText(group.getTravelGroupName());
            description.setText(group.getTravelGroupDescription());
            String destinationText = "Traveling to: " + group.getTravelGroupDestination();
            destination.setText(destinationText);
            getImage(groupImage);
        }

        // Set click listener for back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListGroups.class));
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GroupChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("POSITION", Integer.valueOf(index).toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        // Set click listener for options button
        groupOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a menu with three options given XML resource
                PopupMenu menu = new PopupMenu(getApplicationContext(), v);
                MenuInflater inflater = menu.getMenuInflater();

                inflater.inflate(R.menu.group_settings_menu, menu.getMenu());

                // Set click listener for each option
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

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GroupChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("POSITION", Integer.valueOf(index).toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        // Image selection process
        getImage = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if(uri != null){
                uploadImageUri = uri;
                selectImage.setImageURI(uri);
                Toast.makeText(getApplicationContext(), "Image Selected!", Toast.LENGTH_LONG).show();
            }
        });

        groupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Allow user to change group image if they are an ambassador.
                if(UserData.getUsertype().equals("Ambassador")){
                    selectGroupImage();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Only Travel Ambassadors can edit the group.", Toast.LENGTH_LONG).show();
                }
            }
        });

        itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int days = 5;
                String tripCode = "1234";

                Intent intent = new Intent(getApplicationContext(), DayCard.class);
                intent.putExtra("NUM_OF_DAYS", days);
                intent.putExtra("SOURCE", "GROUP");
                intent.putExtra("IS_EDITABLE", UserData.getUsertype().equals("User"));

                startActivity(intent);
            }
        });
    }

    /**
     * Initiates a dialog popup that displays the details of the group, such as members and the group code.
     */
    private void showGroupDetails(){
        // Create new alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.layout.dialog_group_details);
        builder.setTitle("Travel Group Details");

        View v = DialogGroupDetailsBinding.inflate(getLayoutInflater()).getRoot();
        builder.setView(v);

        // Set text elements
        ListView members = v.findViewById(R.id.group_members_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, group.getMembers());
        members.setAdapter(adapter);

        TextView code = v.findViewById(R.id.group_code_label);
        String codeText = "Group Code: " + group.getTravelGroupID() + "\n(Share this code with others to join this group)";
        code.setText(codeText);

        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * Initiates a dialog popup where the user may enter edits to the group information, such as name etc.
     */
    private void editGroup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.layout.dialog_create_group);
        builder.setTitle("Edit Travel Group");
        View editView = DialogCreateGroupBinding.inflate(getLayoutInflater()).getRoot();
        builder.setView(editView);

        EditText nameInput = editView.findViewById(R.id.group_name_input);
        nameInput.setText(group.getTravelGroupName());
        EditText destinationInput = editView.findViewById(R.id.group_destination_input);
        destinationInput.setText(group.getTravelGroupDestination());
        EditText descriptionInput = editView.findViewById(R.id.group_description_input);
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

    /**
     * Helper method for editGroup() that makes a PUT request for the given modifications to the group.
     * @param name of the group.
     * @param destination of the group.
     * @param description of the group.
     */
    private void updateGroup(String name, String destination, String description){
        // Create JSON for group, along with array for group members
        JSONObject groupData = new JSONObject();
        JSONArray memberData = new JSONArray();

        try {
            groupData.put("travelGroupName", name);
            groupData.put("travelGroupCode", "null");
            groupData.put("travelGroupDestination", destination);
            groupData.put("travelGroupAmbassador", UserData.getUsername());
            groupData.put("travelGroupDescription", description);
            groupData.put("members", memberData);
        }catch(JSONException e){
            Log.e("JSON Error: ", e.toString());
        }

        // Make the post request given the url and group json
        final String url = "http://coms-309-035.class.las.iastate.edu:8080/Group/" + group.getTravelGroupID();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, groupData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());
                UserData.updateUserData();
                Toast.makeText(getApplicationContext(), "Group Successfully Modified!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), ListGroups.class));
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
                g.put("travelGroupCode", group.getTravelGroupID());
                g.put("travelGroupDestination", destination);
                g.put("travelGroupCreator", group.getTravelGroupAmbassador());
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
    }

    /**
     * Initiates a dialog popup to prompt the user if they want to leave the travel group.
     */
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

    /**
     * Helper method for leaveGroupDialog() to make a PUT request to remove the user from the group.
     */
    private void leaveGroup(){
        String user = UserData.getUsername();
        String groupId = group.getTravelGroupID();
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Group/RemoveUser/" + groupId + "/" + user;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Volley Response: ", response.toString());
                UserData.updateUserData();
                Toast.makeText(getApplicationContext(), "You have left the group!", Toast.LENGTH_LONG).show();
                deleteGroup();
                startActivity(new Intent(getApplicationContext(), ListGroups.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error: ", error.toString());
                Toast.makeText(getApplicationContext(), "Error Leaving Group!", Toast.LENGTH_LONG).show();
            }
        });
        Singleton.getInstance(getApplicationContext()).addRequest(req);
    }

    /**
     * Helper method for leaveGroup() to remove the travel group locally from the group list.
     */
    private void deleteGroup(){
        ListGroups.adapter.remove(group);
        ListGroups.adapter.notifyDataSetChanged();
    }
    

    // THE FOLLOWING FUNCTIONS ARE RELATED TO FEATURE 2: IMAGE UPLOAD!

    /**
     * Initiates the dialog to allow ambassador to modify the group image.
     */
    private void selectGroupImage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.layout.dialog_select_image);
        builder.setTitle("Edit Travel Group");
        View view = DialogSelectImageBinding.inflate(getLayoutInflater()).getRoot();
        builder.setView(view);

        Button select = view.findViewById(R.id.select_image_button);
        Button delete = view.findViewById(R.id.delete_image);
        selectImage = view.findViewById(R.id.selected_image);
        getImage(selectImage);

        int[] method = {-1};
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage.launch("image/*");
                method[0] = 0;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage.setImageResource(R.drawable.add_a_photo);
                method[0] = 1;
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(method[0] == 0){
                    imageMethod(uriToImage(uploadImageUri), Request.Method.PUT);
                }
                else if(method[0] == 1){
                    imageMethod(null, Request.Method.DELETE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "please Make a Selection", Toast.LENGTH_LONG).show();
                }

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
     * Helper method to convert the image data format to be uploaded.
     * @param image uri.
     * @return uri as bytes.
     */
    private byte[] uriToImage(Uri image){
        byte[] bytes = new byte[4*1024];
        try{
            @SuppressLint("Recycle") InputStream input = getContentResolver().openInputStream(image);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            while (true) {
                assert input != null;
                if (input.read(bytes) == -1){
                    break;
                }
                else{
                    buffer.write(bytes, 0, bytes.length);
                }
            }

            bytes = buffer.toByteArray();
        }catch(IOException e){
            Log.e("Error: ", e.toString());
        }

        return bytes;
    }

    /**
     * Network requests will be made here based on the passed in method type. Image will be uploaded or deleted.
     * @param data of the image in bytes.
     * @param method for the request.
     */
    private void imageMethod(byte[] data, int method){
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Group/Image/" + group.getTravelGroupID();

        if(method == Request.Method.PUT){
            CustomImageRequest request = new CustomImageRequest(url, data, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse networkResponse) {
                    Log.d("Upload", "Response: " + networkResponse.toString());
                    getImage(groupImage);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("Upload", "Error: " + volleyError.getMessage());
                }
            });
            Singleton.getInstance(getApplicationContext()).addRequest(request);
        }
        else if(method == Request.Method.DELETE){
            CustomImageRequest request = new CustomImageRequest(url, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse networkResponse) {
                    Log.d("Image deleted:", networkResponse.toString());
                    getImage(groupImage);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("Delete", "Error: " + volleyError.getMessage());
                }
            });
            Singleton.getInstance(getApplicationContext()).addRequest(request);
        }
    }

    /**
     * Makes an image request to get the group image.
     * @param image desired ImageView to set.
     */
    private void getImage(ImageView image){
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Group/Image/" + group.getTravelGroupID();
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                Log.d("Volley Image: ", bitmap.toString());
                image.setImageBitmap(bitmap);
            }
        }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Volley Error: ", volleyError.toString());
            }
        });
        Singleton.getInstance(getApplicationContext()).addRequest(request);
    }
}
