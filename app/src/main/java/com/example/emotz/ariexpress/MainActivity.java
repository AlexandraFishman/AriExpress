package com.example.emotz.ariexpress;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.example.emotz.ariexpress.modules.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Firebase productsDatabase;
    private Button button_register;
    private Button button_login;

    private ArrayList<String> productsList =  new ArrayList<>();

    private ListView productsListView;

    private Button logRegButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        logRegButt = (Button)findViewById(R.id.logRegButt);//move to log in/register
        logRegButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == logRegButt){
                    startActivity(new Intent(getApplicationContext(),
                            LogInActivity.class));
                }
            }
        });


        productsDatabase = new Firebase("https://ariexpress-3bb59.firebaseio.com/Products");
//        productsDatabase = FirebaseDatabase.getInstance().getReference();
        productsListView = (ListView) findViewById(R.id.listView);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, productsList);

        productsListView.setAdapter(arrayAdapter);

        productsDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("datasnapshot key = ", dataSnapshot.getKey());
                Product prod = dataSnapshot.getValue(Product.class);
                Log.d("Value = ", prod.toString());
                productsList.add(prod.toString());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}
