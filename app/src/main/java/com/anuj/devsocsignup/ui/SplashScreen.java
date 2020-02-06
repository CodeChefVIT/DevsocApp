package com.anuj.devsocsignup.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.anuj.devsocsignup.BottomNavActivity;
import com.anuj.devsocsignup.MainActivity;
import com.anuj.devsocsignup.R;
import com.anuj.devsocsignup.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#0497E7"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
    }

    @Override
    protected void onResume() {
        CountDownTimer countDownTimer=new CountDownTimer(1000,1000) {
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
                    Intent myIntent = new Intent(SplashScreen.this, SignUpActivity.class);
                    SplashScreen.this.startActivity(myIntent);
                }
            }
        }.start();
        super.onResume();
    }


}
