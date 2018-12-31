package com.example.emotz.ariexpress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.emotz.ariexpress.modules.MyCustomAdapter;
import com.example.emotz.ariexpress.modules.Product;
import com.example.emotz.ariexpress.modules.ProductWithID;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ListViewRegistered  extends AppCompatActivity {

    private Firebase productsDatabase;
    private ArrayList<ProductWithID> productsList =  new ArrayList<ProductWithID>();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private MyCustomAdapter adapter = new MyCustomAdapter(productsList, this);
    private Button toCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_registered_main);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        productsDatabase = new Firebase("https://ariexpress-3bb59.firebaseio.com/Products");
        final ListView lView = (ListView)findViewById(R.id.listView);
        lView.setAdapter(adapter);

        toCart = (Button)findViewById(R.id.toCart);//move to log in/register
        toCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == toCart){
                    startActivity(new Intent(getApplicationContext(),
                            UserCart.class));
                }
            }
        });

        productsDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /////
                Log.d("datasnapshot key = ", dataSnapshot.getKey());
                Product product = dataSnapshot.getValue(Product.class);
                ProductWithID prod = new  ProductWithID(product);
                prod.ID=(dataSnapshot.getKey());
                Log.d("Value = ", prod.toString());
                productsList.add(prod);
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