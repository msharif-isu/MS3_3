package com.example.itinerarybuddy.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;

import org.json.JSONObject;

import java.util.ArrayList;

public class TestGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.fragment_group_list);

        ListView list = findViewById(R.id.group_list);

        UserData.queue = Volley.newRequestQueue(getApplicationContext());
        ArrayList<Group> groups = UserData.getGroups();
        ArrayList<String> names = new ArrayList<String>();
        //for(int i = 0; i < UserData.groups.size(); i++){
            //names.add(UserData.groups.get(i).getTravelGroupName());
        //}
        TextView b = findViewById(R.id.groups_list_title);
        //b.setText(groups.size());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, names);
        list.setAdapter(adapter);

    }
}
