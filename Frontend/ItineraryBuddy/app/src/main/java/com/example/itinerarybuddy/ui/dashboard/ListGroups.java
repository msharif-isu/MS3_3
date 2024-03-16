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
import com.example.itinerarybuddy.util.Singleton;

import org.json.JSONObject;

public class ListGroups extends AppCompatActivity {

    protected static ArrayAdapter<Group> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.fragment_group_list);

        ListView list = findViewById(R.id.group_list);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        UserData.queue = Volley.newRequestQueue(getApplicationContext());
        UserData.initializeGroups(adapter);

        /*
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        */
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), LoadGroup.class);
                Bundle bundle = new Bundle();
                bundle.putString("POSITION", Integer.valueOf(position).toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        ImageButton addGroup = findViewById(R.id.add_group_button);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroupDialog();
            }
        });

        ImageButton backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), personalPage1.class));
            }
        });

    }

    private void addGroupDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.layout.dialog_add_group);
        builder.setTitle("Join a Group");

        View v = DialogAddGroupBinding.inflate(getLayoutInflater()).getRoot();
        builder.setView(v);

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText codeInput = v.findViewById(R.id.group_code_input);
                String code = codeInput.getText().toString();
                joinGroup(code);
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
        /*
        String user = UserData.getUsername();
        String url = "http://coms-309-035.class.las.iastate.edu:8080/Group/AddUser/" + code + "/" + user;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: fix data on update
                Log.d("Volley Response: ", response.toString());
                UserData.appendAdapter(response);
                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "Group Joined!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error: ", error.toString());
                Toast.makeText(getApplicationContext(), "Error Joining Group.", Toast.LENGTH_LONG).show();
            }
        });
        Singleton.getInstance(getApplicationContext()).addRequest(req);
        */

    }
}
