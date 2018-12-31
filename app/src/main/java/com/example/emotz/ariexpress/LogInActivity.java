package com.example.emotz.ariexpress;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private Button button_register;
    private Button button_login;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        button_register = (Button)findViewById(R.id.regButt);
        button_login = (Button)findViewById(R.id.logInButt);
        email = (EditText)findViewById(R.id.emailInput);
        password = (EditText)findViewById(R.id.pwdInput);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        button_register.setOnClickListener(new View.OnClickListener() {//go to register page
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
                    LoginUser();
                }
            }
        });
    }
    public void LoginUser(){

        final String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && (isAdmin(Email)==true)){//if login succesful and user is admin
                            currentUser = mAuth.getCurrentUser();
                            finish();
                            startActivity(new Intent(getApplicationContext(),
                                    AdminActivity.class));
                        }
                        else if(task.isSuccessful() && (isAdmin(Email)==false)){//if login succesful and user is not admin
                            currentUser = mAuth.getCurrentUser();
                            finish();
                            startActivity(new Intent(getApplicationContext(),
                                    ListViewRegistered.class));
                        }
                        else {
                            Toast.makeText(LogInActivity.this, "couldn't login",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean isAdmin(String Email){
        boolean isadmin=false;
        String admin1="emotzny@gmail.com";
        String admin2="alexyafish@gmail.com";
        String admin3="arbel.nathan0@gmail.com";
        if(Email.equals(admin1) || Email.equals(admin2) || Email.equals(admin3)){
            isadmin=true;
        }
        return isadmin;
    }
}
