package com.anuj.devsocsignup.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.anuj.devsocsignup.Adapters.FAQ;
import com.anuj.devsocsignup.BottomNavActivity;
import com.anuj.devsocsignup.CheckVerifiedActivity;
import com.anuj.devsocsignup.Classes.ObjectSerializer;
import com.anuj.devsocsignup.LoginActivity;
import com.anuj.devsocsignup.MutedVideoView;
import com.anuj.devsocsignup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    private FirebaseUser user;
    MutedVideoView vv;
    SharedPreferences preferences;


    private ArrayList<FAQ> Faqs,currentFaqs;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = getWindow();
        Drawable bg = getDrawable(R.drawable.main_bg);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
        window.setBackgroundDrawable(bg);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();

        vv = findViewById(R.id.videoView101);
        vv.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0,0);
        });
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash);

        vv.setDrawingCacheEnabled(true);
        vv.setVideoURI(uri);
        vv.requestFocus();
        preferences = getSharedPreferences("dynamic", Context.MODE_PRIVATE);
        Faqs = new ArrayList<>();


    }

    @Override
    protected void onResume() {
        vv.start();
        try {
            currentFaqs = (ArrayList<FAQ>) ObjectSerializer.deserialize(preferences.getString("faq", ObjectSerializer.serialize(new ArrayList<FAQ>())));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        DatabaseReference faqs= FirebaseDatabase.getInstance().getReference("faq");
        faqs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp:dataSnapshot.getChildren()){
                    String ques = dsp.child("ques").getValue(String.class);
//                        Log.d("ques",ques);
                    String ans = dsp.child("ans").getValue(String.class);
                    Faqs.add(new FAQ(ques,ans));
                }
//                    for(int i = 0; i<Faqs.size();i++){
//                        Log.d("Firebase",Faqs.get(i).getQ());
//                        Log.d("Pref",currentFaqs.get(i).getQ());
//                    }
                if(!Faqs.equals(currentFaqs)){
                    SharedPreferences.Editor editor = preferences.edit();
                    try {
                        editor.putString("faq", ObjectSerializer.serialize(Faqs));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    editor.commit();
                    Log.d("Commit","Done");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                if (user != null && user.isEmailVerified()) {
                    Intent myIntent = new Intent(SplashScreen.this, BottomNavActivity.class);
                    SplashScreen.this.startActivity(myIntent);
                } else if (user == null) {
                    Intent myIntent = new Intent(SplashScreen.this, LoginActivity.class);
                    SplashScreen.this.startActivity(myIntent);
                } else {
                    startActivity(new Intent(SplashScreen.this, CheckVerifiedActivity.class));
                }
            }
        }.start();
        super.onResume();
    }



}
