package com.anuj.devsocsignup.ui.timeline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anuj.devsocsignup.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class TimelineFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timeline, container, false);
        ImageView timeline = root.findViewById(R.id.timeline_view);
        timeline.setClipToOutline(true);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(timeline);
        photoViewAttacher.update();

               return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}