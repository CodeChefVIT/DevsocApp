package com.anuj.devsocsignup;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "EmailPassword";

    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase,ref1;
    private ArrayList UserList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);
        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        findViewById(R.id.emailCreateAccountButton).setOnClickListener(this);
        findViewById(R.id.signOutButton).setOnClickListener(this);
        findViewById(R.id.emailSignInButton).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ref1=mDatabase.child("users");
        showProgressDialog();
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserList = new ArrayList<String>();
                for(DataSnapshot dsp:dataSnapshot.getChildren()){
                    UserList.add(String.valueOf(dsp.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        hideProgressDialog();

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            Intent intent = new Intent(MainActivity.this, BottomNavActivity.class);
            startActivity(intent);
        }
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "cru3eeateAccount:" + email);
        View view=this.getCurrentFocus();
        hideKeyboard(view);

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            mPasswordField.setText(null);
                        }

                        hideProgressDialog();
                    }
                });
        mDatabase.child("food").child(email.replace(".","_")).setValue(0);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        showProgressDialog();
        View view=this.getCurrentFocus();
        hideKeyboard(view);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            mPasswordField.setText(null);
                        }

                        if (!task.isSuccessful()) {
                            mStatusTextView.setText("Authentication Failed");
                        }
                        hideProgressDialog();
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
        mEmailField.setText(null);
        mPasswordField.setText(null);
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

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {


            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);

        } else {
            mStatusTextView.setText("Signed Out");
            mDetailTextView.setText(null);

            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        FirebaseUser user;
        if (i == R.id.emailCreateAccountButton && validateForm()) {
            if(UserList.contains(mEmailField.getText().toString().replace(".","_"))) {
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                Intent intent = new Intent(MainActivity.this, BottomNavActivity.class);
                startActivity(intent);
            }
            else
                Toast.makeText(getApplicationContext(),"Please use the email you used to register for the Hackathon",Toast.LENGTH_LONG).show();

        }
         else if (i == R.id.signOutButton) {
            signOut();
        }
         else if (i == R.id.emailSignInButton && validateForm()) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            user = mAuth.getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(MainActivity.this, BottomNavActivity.class);
                startActivity(intent);

            }
        }
    }
}
