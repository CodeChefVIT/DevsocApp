package com.anuj.devsocsignup.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.anuj.devsocsignup.BottomNavActivity;
import com.anuj.devsocsignup.MainActivity;
import com.anuj.devsocsignup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getWindow().setStatusBarColor(Color.parseColor("#0497E7"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        CountDownTimer countDownTimer=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long l)
            {
            }

            @Override
            public void onFinish()
            {
                if(user!=null) {
                    Intent myIntent = new Intent(SplashScreen.this, BottomNavActivity.class);
                    SplashScreen.this.startActivity(myIntent);
                }
                else{
                    Intent myIntent = new Intent(SplashScreen.this, MainActivity.class);
                    SplashScreen.this.startActivity(myIntent);
                }
            }
        }.start();
    }
}
