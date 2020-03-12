package com.anuj.devsocsignup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.anuj.devsocsignup.Adapters.FAQ;
import com.anuj.devsocsignup.Classes.ObjectSerializer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.System.exit;

public class BottomNavActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    SharedPreferences preferences;

    private ArrayList<FAQ> Faqs,currentFaqs;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = getWindow();
        Drawable bg = getDrawable(R.drawable.main_bg);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
        window.setBackgroundDrawable(bg);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_bottom_nav);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_timeline, R.id.navigation_us, R.id.navigation_speak, R.id.navigation_user)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        Faqs = new ArrayList<>();
        preferences = getSharedPreferences("dynamic", Context.MODE_PRIVATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (user != null) {
//            try {
//                currentFaqs = (ArrayList<FAQ>) ObjectSerializer.deserialize(preferences.getString("faq", ObjectSerializer.serialize(new ArrayList<FAQ>())));
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            DatabaseReference faqs= FirebaseDatabase.getInstance().getReference("faq");
//            faqs.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for(DataSnapshot dsp:dataSnapshot.getChildren()){
//                        String ques = dsp.child("ques").getValue(String.class);
////                        Log.d("ques",ques);
//                        String ans = dsp.child("ans").getValue(String.class);
//                        Faqs.add(new FAQ(ques,ans));
//                    }
////                    for(int i = 0; i<Faqs.size();i++){
////                        Log.d("Firebase",Faqs.get(i).getQ());
////                        Log.d("Pref",currentFaqs.get(i).getQ());
////                    }
//                    if(!Faqs.equals(currentFaqs)){
//                        SharedPreferences.Editor editor = preferences.edit();
//                        try {
//                            editor.putString("faq", ObjectSerializer.serialize(Faqs));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        editor.commit();
//                        Log.d("Commit","Done");
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
