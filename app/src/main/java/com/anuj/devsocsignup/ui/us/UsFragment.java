package com.anuj.devsocsignup.ui.us;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class UsFragment extends Fragment {

    private List<TeamMember> list = new ArrayList<>();
    private View ChildView;
    private int RecyclerViewItemPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_us, container, false);
        RecyclerView our_team = root.findViewById(R.id.our_team);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        our_team.setLayoutManager(layoutManager);
        AddItems();
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(list);
        LinearLayoutManager horizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        our_team.setLayoutManager(horizontal);
        our_team.setAdapter(adapter);

        ImageView sponsors = root.findViewById(R.id.sponsors);
        sponsors.setClipToOutline(true);

        our_team.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent e) {

                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    return false;
                }
            });
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                ChildView = rv.findChildViewUnder(e.getX(),e.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(e)) {

                    //Getting clicked value.
                    RecyclerViewItemPosition = rv.getChildAdapterPosition(ChildView);

                    // Showing clicked item value on screen using toast message.
                    String url = list.get(RecyclerViewItemPosition).getUrl();
                    if(url!=null) {
                        Uri uriUrl = Uri.parse(url);
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);
                    }
                    //Toast.makeText(getContext(), list.get(RecyclerViewItemPosition).getUrl(), Toast.LENGTH_LONG).show();

                }


                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void AddItems(){
        list.add(new TeamMember("Chair","Aryan Bhatnagar",R.drawable.aryan_bhatnagar,"https://www.instagram.com/aryan_bhatnagarr/"));
        list.add(new TeamMember("Vice Chair Management","Amulya Boyapati",R.drawable.amulya,"https://www.linkedin.com/in/amulya-boyapati-546b4a15a/"));
        list.add(new TeamMember("Vice Chair Technical","Angad Sharma",R.drawable.angad_sharma,"https://www.instagram.com/l04db4l4nc3r/"));
        list.add(new TeamMember("Secretary Internal Affairs","Animesh Ashish",R.drawable.animesh_ashish,"https://www.linkedin.com/in/animesh-ashish/"));
        list.add(new TeamMember("Operations Director","Pritika Roy",R.drawable.pritika_roy2,"https://www.linkedin.com/in/pritika-roy-chowdhury-3956ba160/"));
        list.add(new TeamMember("Events Director","Aman Raghav",R.drawable.aman_raghav));
        list.add(new TeamMember("Secretary External Affairs","Akshat Pethiya",R.drawable.akshat_pethiya ,"https://www.linkedin.com/in/akshat-pethiya-04295a177"));
        list.add(new TeamMember("HR Director", "Tanya Sharma",R.drawable.tanya_sharma));
        list.add(new TeamMember("Finance Director","Debalok Banerjee",R.drawable.debalok,"https://www.instagram.com/justdeba/"));
        list.add(new TeamMember("Projects Director","Shaswat Srivastava",R.drawable.shaswat_srivastava,"https://www.linkedin.com/in/shaswat-srivastava/"));
        list.add(new TeamMember("Design and Media Director","Rachit Agarwal",R.drawable.rachit_agarwal2,"https://www.instagram.com/rachit_agarwal1512/"));
        list.add(new TeamMember("Marketing Director", "Raghav Jhawar", R.drawable.raghav_jhavar,"https://www.instagram.com/raghavjhawar02/"));
        list.add(new TeamMember("Technical Adviser","Yash Mehrotra",R.drawable.yash_meherotra2,"https://www.linkedin.com/in/yashmehrotra9/"));
        list.add(new TeamMember("Logistics Director","Kunal Kumar",R.drawable.kunal_kumar,"https://www.instagram.com/kunal.kumar19/"));
    }
}
