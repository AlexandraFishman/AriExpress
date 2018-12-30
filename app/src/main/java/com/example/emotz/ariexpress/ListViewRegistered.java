package com.example.emotz.ariexpress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.emotz.ariexpress.modules.MyCustomAdapter;
import com.example.emotz.ariexpress.modules.ProductWithID;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class ListViewRegistered  extends AppCompatActivity {

    private Firebase productsDatabase;
    private ArrayList<String> productsList =  new ArrayList<String>();
    //private ListView productsListView;
    private MyCustomAdapter adapter = new MyCustomAdapter(productsList, this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_registered_main);


        productsDatabase = new Firebase("https://ariexpress-3bb59.firebaseio.com/Products");
        final ListView lView = (ListView)findViewById(R.id.listView);
        lView.setAdapter(adapter);



        productsDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /////
                Log.d("datasnapshot key = ", dataSnapshot.getKey());
                ProductWithID prod = dataSnapshot.getValue(ProductWithID.class);
                prod.setID(dataSnapshot.getKey());
                Log.d("Value = ", prod.toString());
                productsList.add(prod.toString());
                adapter.notifyDataSetChanged();

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