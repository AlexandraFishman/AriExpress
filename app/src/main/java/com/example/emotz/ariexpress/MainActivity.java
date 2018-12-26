package com.example.emotz.ariexpress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.emotz.ariexpress.modules.MyCustomAdapter;
import com.example.emotz.ariexpress.modules.Product;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Firebase productsDatabase;


    private ArrayList<String> productsList =  new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        productsDatabase = new Firebase("https://ariexpress-3bb59.firebaseio.com/Products");
//        productsDatabase = FirebaseDatabase.getInstance().getReference();


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, productsList);


      //  productsList.add("a");

        productsDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /////
                Log.d("datasnapshot key = ", dataSnapshot.getKey());
                Product prod = dataSnapshot.getValue(Product.class);
                Log.d("Value = ", prod.toString());
                productsList.add(prod.toString());
               arrayAdapter.notifyDataSetChanged();
                /////
                ///////
//                for (DataSnapshot products : dataSnapshot.getChildren()) {
//                    String value = products.getValue(String.class); //child("Prod1")
//                    productsList.add(value);
//                    arrayAdapter.notifyDataSetChanged();
//                }
                ///////

//                String value = dataSnapshot.getValue(String.class);
//                productsList.add(value);
//                arrayAdapter.notifyDataSetChanged();
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


      ArrayList<String> list = new ArrayList<String>();
      arrayAdapter.notifyDataSetChanged();
      list.addAll(productsList);


        MyCustomAdapter adapter = new MyCustomAdapter(productsList, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.listView);
        lView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
