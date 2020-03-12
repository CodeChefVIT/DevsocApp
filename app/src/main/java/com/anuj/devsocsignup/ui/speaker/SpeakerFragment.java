package com.anuj.devsocsignup.ui.speaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anuj.devsocsignup.Adapters.FAQ;
import com.anuj.devsocsignup.Adapters.FaqAdapter;
import com.anuj.devsocsignup.Adapters.MyRecyclerViewAdapter;
import com.anuj.devsocsignup.Classes.ObjectSerializer;
import com.anuj.devsocsignup.R;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpeakerFragment extends Fragment {

    private SharedPreferences preferences;
    private List<FAQ> currentFaqs = new ArrayList<>();
    private ArrayList<FAQ> Test = new ArrayList<>();
    RecyclerView faq_view;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_speaker, container, false);
        preferences = getActivity().getSharedPreferences("dynamic", Context.MODE_PRIVATE);
        faq_view = root.findViewById(R.id.faq_view);
        Test.add(new FAQ(getString(R.string.ques1),getString(R.string.ans1)));
        Test.add(new FAQ(getString(R.string.ques2),getString(R.string.ans2)));
        Test.add(new FAQ(getString(R.string.ques3),getString(R.string.ans3)));
        Test.add(new FAQ(getString(R.string.ques4),getString(R.string.ans4)));

        final ImageView qrcode = root.findViewById(R.id.speakers);

        Glide.with(Objects.requireNonNull(getContext())).load(R.drawable.start).into(qrcode);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        faq_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            currentFaqs = (List<FAQ>) ObjectSerializer.deserialize(preferences.getString("faq", ObjectSerializer.serialize(Test)));
            Log.d("ques",currentFaqs.get(0).getQ());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        FaqAdapter adapter = new FaqAdapter(currentFaqs);
        faq_view.setAdapter(adapter);

    }


}

