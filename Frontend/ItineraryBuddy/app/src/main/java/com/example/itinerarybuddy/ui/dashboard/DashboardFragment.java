package com.example.itinerarybuddy.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.toolbox.Volley;
import com.example.itinerarybuddy.R;
import com.example.itinerarybuddy.activities.LoadGroup;
import com.example.itinerarybuddy.data.Group;
import com.example.itinerarybuddy.data.UserData;
import com.example.itinerarybuddy.databinding.FragmentDashboardBinding;
import com.example.itinerarybuddy.databinding.FragmentGroupListBinding;
import com.example.itinerarybuddy.databinding.FragmentHomeBinding;
import com.example.itinerarybuddy.ui.home.HomeViewModel;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel = new DashboardViewModel();

    private FragmentDashboardBinding dashboardBinding;

    private FragmentGroupListBinding groupListBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();



        dashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false);
        groupListBinding = FragmentGroupListBinding.inflate(inflater, container, false);
        View root = groupListBinding.getRoot();

        ListView groupList = root.findViewById(R.id.group_list);
        ArrayAdapter<Group> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        UserData.queue = Volley.newRequestQueue(requireContext());
        UserData.initializeGroups(adapter);

        groupList.setAdapter(adapter);
        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(requireContext(), LoadGroup.class);
                Bundle bundle = new Bundle();
                bundle.putString("POSITION", Integer.valueOf(position).toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void showGroups(@NonNull LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState){

        dashboardBinding = null;
        groupListBinding = FragmentGroupListBinding.inflate(inflater, container, false);
        View root = groupListBinding.getRoot();
    }
}