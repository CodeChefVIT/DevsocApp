package com.anuj.devsocsignup.ui.qr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anuj.devsocsignup.R;
import com.bumptech.glide.Glide;

import java.util.Objects;

public class SpeakerFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_speaker, container, false);
        final ImageView qrcode = root.findViewById(R.id.speakers);

        Glide.with(Objects.requireNonNull(getContext())).load(R.drawable.start).into(qrcode);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}

