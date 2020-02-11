package com.anuj.devsocsignup.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.anuj.devsocsignup.BottomNavActivity;
import com.anuj.devsocsignup.CheckVerifiedActivity;
import com.anuj.devsocsignup.LoginActivity;
import com.anuj.devsocsignup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    private FirebaseUser user;
    VideoView vv;
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


    }

    @Override
    protected void onResume() {
        vv.start();
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
