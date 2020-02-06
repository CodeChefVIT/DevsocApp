package com.anuj.devsocsignup.ui.qr;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anuj.devsocsignup.Adapters.FAQ;
import com.anuj.devsocsignup.Adapters.FaqAdapter;
import com.anuj.devsocsignup.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class QRFragment extends Fragment {

    private FirebaseAuth mAuth;
    private RecyclerView faq_view;
    private List<FAQ> Questions;
    private FaqAdapter adapter;
    FaqAdapter.ViewHolder holder;
    View ChildView;
    TextView textView;
    int RecyclerViewItemPosition;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_qr, container, false);
        final ImageView qrcode = root.findViewById(R.id.speakers);
        textView = root.findViewById(R.id.ans);
//        faq_view = root.findViewById(R.id.faq_view);
//        faq_view.setLayoutManager(new LinearLayoutManager(getActivity()));
//        AddElements();
//        adapter = new FaqAdapter(Questions);
//        faq_view.setAdapter(adapter);
//        faq_view.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.OnGestureListener() {
//                @Override
//                public boolean onDown(MotionEvent e) {
//                    return false;
//                }
//
//                @Override
//                public void onShowPress(MotionEvent e) {
//
//                }
//
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//
//                @Override
//                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                    return false;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//
//                }
//
//                @Override
//                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                    return false;
//                }
//            });
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                ChildView = rv.findChildViewUnder(e.getX(),e.getY());
//
//                if(ChildView != null && gestureDetector.onTouchEvent(e)) {
//
//                    //Getting clicked value.
//                    RecyclerViewItemPosition = rv.getChildAdapterPosition(ChildView);
//
//                    // Showing clicked item value on screen using toast message.
//                    holder.showAns();
//
//                    Toast.makeText(getContext(), Questions.get(RecyclerViewItemPosition).getA(), Toast.LENGTH_LONG).show();
//
//                }
//
//
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Glide.with(getContext()).load(R.drawable.giphy).into(qrcode);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void AddElements(){
        Questions = new ArrayList<>();
        Questions.add(new FAQ(getString(R.string.ques1),getString(R.string.ans1)));
    }


}

