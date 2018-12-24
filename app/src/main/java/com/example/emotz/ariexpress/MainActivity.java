package com.example.emotz.ariexpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button_register;
    private Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_register = (Button)findViewById(R.id.moveRegButt);
        button_login = (Button)findViewById(R.id.moveLogButt);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button_register){
                    startActivity(new Intent(getApplicationContext(),
                            RegisterActivity.class));
                }
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button_login){
                    startActivity(new Intent(getApplicationContext(),
                            LogInActivity.class));
                }
            }
        });
    }
}
