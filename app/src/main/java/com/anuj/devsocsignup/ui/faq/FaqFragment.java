package com.anuj.devsocsignup.ui.faq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anuj.devsocsignup.Adapters.MyRecyclerViewAdapter;
import com.anuj.devsocsignup.Adapters.TeamMember;
import com.anuj.devsocsignup.R;

import java.util.ArrayList;
import java.util.List;

public class FaqFragment extends Fragment {

    private List<TeamMember> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_faq, container, false);
        RecyclerView our_team = root.findViewById(R.id.our_team);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        our_team.setLayoutManager(layoutManager);
        AddItems();
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(list);
        LinearLayoutManager horizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        our_team.setLayoutManager(horizontal);
        our_team.setAdapter(adapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void AddItems(){
        list = new ArrayList<>();
        TeamMember a = new TeamMember("Position","Name",R.drawable.blue_color);
        list.add(new TeamMember("Chair","Aryan Bhatnagar",R.drawable.aryan_bhatnagar2));
        list.add(new TeamMember("Vice Chair(Management)","Amulya Boyapati",R.drawable.blue_color));
        list.add(new TeamMember("Vice Chair(Technical)","Angad Sharma",R.drawable.blue_color));
        list.add(new TeamMember("Secretary Internal Affairs","Animesh Ashish",R.drawable.blue_color));
        list.add(new TeamMember("Operations Director","Pritika Roy",R.drawable.blue_color));
        list.add(new TeamMember("Events Director","Aman Raghav",R.drawable.blue_color));
        list.add(new TeamMember("Secretary External Affairs","Akshat Pethiya",R.drawable.blue_color));
        list.add(new TeamMember("HR Director", "Tanya Sharma",R.drawable.blue_color));
        list.add(new TeamMember("Finance Director","Debalok Banerjee",R.drawable.blue_color));
        list.add(new TeamMember("Projects and Research Director","Shaswat Srivastava",R.drawable.blue_color));
        list.add(new TeamMember("Design and Media Director","Rachit Agarwal",R.drawable.blue_color));
        list.add(new TeamMember("Publicity and Marketing Director", "Raghav Jhawar", R.drawable.blue_color));
        list.add(new TeamMember("Technical Adviser","Yash Mehrotra",R.drawable.blue_color));
        list.add(new TeamMember("Logistics Director","Kunal Kumar",R.drawable.blue_color));
    }
}
