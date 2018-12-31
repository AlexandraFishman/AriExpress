package com.example.emotz.ariexpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView Email;
    private TextView Uid;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Email = (TextView)findViewById(R.id.profileEmail);
        Uid = (TextView)findViewById(R.id.profileUid);
        mAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(R.id.button_logout);
        user = mAuth.getCurrentUser();//use this to get user info, will be through the user object


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==logout){
                    if (user != null) {
                        mAuth.signOut();
                        startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                    }
                }
            }
        });

        if (user != null){//HOW TO GET USE INFO - EXAMPLE
            String email = user.getEmail();
            String uid = user.getUid();
            Email.setText(email);
            Uid.setText(uid);
        }
    }
    }

