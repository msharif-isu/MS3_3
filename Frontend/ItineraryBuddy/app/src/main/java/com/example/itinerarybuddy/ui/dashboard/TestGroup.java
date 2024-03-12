package com.example.itinerarybuddy.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.LoadGroup;
import com.example.itinerarybuddy.activities.personalPage1;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.fragment_group_list);

        ListView list = findViewById(R.id.group_list);

        ArrayAdapter<Group> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
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
                Intent i = new Intent(getApplicationContext(), personalPage1.class);
                Bundle bundle = new Bundle();
                bundle.putString("POSITION", Integer.valueOf(position).toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}
