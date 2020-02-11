package com.anuj.devsocsignup;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends BaseActivity{

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;

    MutedVideoView vv;
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
        setContentView(R.layout.activity_log_in);

        mEmailField = findViewById(R.id.log_in_text);
        mPasswordField = findViewById(R.id.password_text);
        Button sign_up = findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();

        sign_up.setOnClickListener(v -> {
            if (validateForm()) {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        TextView link = findViewById(R.id.signup_link);
        link.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(i);
        });
        SpannableString content = new SpannableString(getString(R.string.sign_up_link));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        link.setText(content);

        vv = findViewById(R.id.videoView4);
        vv.setOnPreparedListener(mp -> mp.setLooping(true));
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.back_vid);

        vv.setDrawingCacheEnabled(true);
        vv.setVideoURI(uri);
        vv.requestFocus();



    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        showProgressDialog();
        View view=this.getCurrentFocus();
        hideKeyboard(view);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        if(user.isEmailVerified()) {
                            Intent intent = new Intent(LoginActivity.this, BottomNavActivity.class);
                            startActivity(intent);
                        }
                        else {
                            startActivity(new Intent(LoginActivity.this,CheckVerifiedActivity.class));
                            Toast.makeText(getApplicationContext(),"Verify Email and Sign In again",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        mPasswordField.setText(null);
                    }
                    hideProgressDialog();
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        vv.start();
        super.onResume();
    }
}
