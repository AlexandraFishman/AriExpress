package com.example.emotz.ariexpress;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        logout = (Button)findViewById(R.id.logoutButt);
        logout.setOnClickListener(new View.OnClickListener() {//go to register page
            @Override
            public void onClick(View v) {
                if (v == logout){
                    startActivity(new Intent(getApplicationContext(),
                            LogInActivity.class));
                }
            }
        });
    }
}
