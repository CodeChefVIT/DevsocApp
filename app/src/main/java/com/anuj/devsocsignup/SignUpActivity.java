package com.anuj.devsocsignup;

import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SignUpActivity extends BaseActivity{

    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ArrayList<Object> UserList;

    private EditText mEmailField;
    private EditText mPasswordField;

    TextView sign_in_link;

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
        setContentView(R.layout.activity_sign_up);

        mEmailField = findViewById(R.id.sign_up_text);
        mPasswordField = findViewById(R.id.password_signup_text);
        Button sign_up = findViewById(R.id.signup_button);

        sign_in_link = findViewById(R.id.signin_page_link);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref1 = mDatabase.child("users");

        showProgressDialog();
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserList = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    UserList.add(String.valueOf(dsp.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        hideProgressDialog();

        sign_up.setOnClickListener(v -> {
            if (validateForm()) {
                if(UserList.contains(mEmailField.getText().toString().replace(".","_")))
                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("Incorrect Email ID");
                    builder.setMessage("Please use the email you used to register for the Hackathon.");
                    builder.setCancelable(true);

                    builder.setPositiveButton("Ok", (dialog, which) -> {
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
//                    Toast.makeText(getApplicationContext(), "Please use the email you used to register for the Hackathon", Toast.LENGTH_LONG).show();
                }
            }
        });
        vv = findViewById(R.id.videoView3);
        vv.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0,0);
        });
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sign_up);

        vv.setDrawingCacheEnabled(true);
        vv.setVideoURI(uri);
        vv.requestFocus();

        sign_in_link.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this,LoginActivity.class)));
        SpannableString content = new SpannableString(getString(R.string.sign_in_link));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        sign_in_link.setText(content);


    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        View view=this.getCurrentFocus();
        hideKeyboard(view);

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        sendEmailVerification();
                        mDatabase.child("food").child(email.replace(".","_")).setValue(0);
                        Intent intent = new Intent(SignUpActivity.this, CheckVerifiedActivity.class);
                        startActivity(intent);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
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

    private void sendEmailVerification() {

        final FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this,
                                "Verification email sent to " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.getException());
                        Toast.makeText(SignUpActivity.this,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        vv.start();
        super.onResume();
    }
}
