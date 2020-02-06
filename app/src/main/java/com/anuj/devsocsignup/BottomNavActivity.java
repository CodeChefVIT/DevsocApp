package com.anuj.devsocsignup;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class BottomNavActivity extends AppCompatActivity {

//    AppBarConfiguration mAppBarConfig;
//    ActionBarDrawerToggle toggle;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        Window window = getWindow();
        Drawable bg = getDrawable(R.drawable.main_bg);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(android.R.color.transparent));
        window.setBackgroundDrawable(bg);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_bottom_nav);
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_timeline, R.id.navigation_map, R.id.navigation_faq, R.id.navigation_qr, R.id.navigation_sponsors, R.id.navigation_speakers, R.id.navigation_us)
                .build();
//        mAppBarConfig = new AppBarConfiguration.Builder(R.id.navigation_timeline,R.id.navigation_qr,R.id.navigation_speakers,R.id.navigation_sponsors,R.id.navigation_us,R.id.navigation_faq)
//                .setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupActionBarWithNavController(this,navController,mAppBarConfig);
        NavigationUI.setupWithNavController(navView, navController);
//        NavigationUI.setupWithNavController(navView,navController);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfig)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
