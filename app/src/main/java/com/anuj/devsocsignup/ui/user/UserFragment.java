package com.anuj.devsocsignup.ui.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anuj.devsocsignup.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Objects;

import static android.graphics.Color.WHITE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserFragment extends Fragment {

    private final static String pref = "User Details";
    private final static int QRcodeWidth = 800 ;
    private final static int QRcodeHeight = 800 ;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ImageView qrcode;
    private TextView user_name;
    private String username;
    private Button logout;
    private TextView user_pos;
    private MaterialLetterIcon icon;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(pref, Context.MODE_PRIVATE);
        user_pos = root.findViewById(R.id.textView6);
        qrcode = root.findViewById(R.id.qrcode);
        ImageView map = root.findViewById(R.id.imageView2);
        logout = root.findViewById(R.id.log_out);
        user_name = root.findViewById(R.id.user_name);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDB = FirebaseDatabase.getInstance().getReference("users");
        user = mAuth.getCurrentUser();
        assert user != null;
        String email_key = Objects.requireNonNull(user.getEmail()).replace(".", "_");
        username = preferences.getString("USERNAME",null);
        Log.d(TAG,"*****************Preferences read"+username);
        if(username == null) {
            mDB.child(email_key).child("Name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    username = dataSnapshot.getValue(String.class);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("USERNAME",username);
                    editor.apply();
                    Log.d(TAG,"*****************Preferences Created added"+username);
                    user_name.setText(username);
                    user_pos.setText("Participant");
                    icon = root.findViewById(R.id.imageView3);
                    icon.setLetter(username);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            Log.d(TAG,"*****************Preferences Exist");
            user_name.setText(username);
            user_pos.setText("Participant");
            icon = root.findViewById(R.id.imageView3);
            icon.setLetter(username);
        }
        map.setOnClickListener(v -> mapURL());


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent i = Objects.requireNonNull(getActivity()).getPackageManager().
                    getLaunchIntentForPackage(getActivity().getPackageName());
            assert i != null;
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SharedPreferences preferences = this.getActivity().getSharedPreferences(pref,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("USERNAME",null);
            editor.apply();
            Log.d(TAG,"********Cleared Preferences");
            startActivity(i);
        });

        try {
            if (user != null){
                Bitmap bitmap = TextToImageEncode(user.getEmail());
                qrcode.setImageBitmap(bitmap);
                qrcode.setClipToOutline(true);
            }

        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.QR_CODE,
                    QRcodeWidth, QRcodeHeight, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ? WHITE : 0xff072031;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, QRcodeWidth, 0, 0, bitMatrixWidth, bitMatrixHeight);
        Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.qrlogo2);
        bitmap = mergeBitmaps(overlay,bitmap);
        return bitmap;
    }

    private Bitmap mergeBitmaps(Bitmap overlay, Bitmap bitmap) {

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        Bitmap combined = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bitmap, new Matrix(), null);

        int centreX = (canvasWidth  - overlay.getWidth()) /2;
        int centreY = (canvasHeight - overlay.getHeight()) /2 ;
        canvas.drawBitmap(overlay, centreX, centreY, null);


        return combined;
    }

    private void mapURL(){
        String url = "https://www.google.com/maps/dir//Anna+Auditorium,+VIT+University,+Vellore,+Tamil+Nadu+632014/@12.9700056,79.1533752,17z/data=!4m8!4m7!1m0!1m5!1m1!1s0x3bad47a01ae8e111:0x9575db6fe3bc68e2!2m2!1d79.1555639!2d12.9700056";
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}