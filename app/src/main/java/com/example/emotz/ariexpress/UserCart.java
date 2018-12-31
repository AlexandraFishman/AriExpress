package com.example.emotz.ariexpress;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.emotz.ariexpress.modules.MyCustomAdapterCart;
import com.example.emotz.ariexpress.modules.ProductWithID;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserCart extends AppCompatActivity {

    private DatabaseReference productDatabase;
    private DatabaseReference cartDatabase;
    private ArrayList<ProductWithID> productsList =  new ArrayList<ProductWithID>();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private MyCustomAdapterCart adapter = new MyCustomAdapterCart(productsList, this);
    private  String itemId;
    private String userID;
    private ArrayList<String> arrCart=new ArrayList<String>();
    public TextView tPrice;
    public Button BuyBtn;
    private ArrayList<ProductWithID> prodList;
    private double totPrice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_user_cart);
        tPrice=(TextView)findViewById(R.id.totalPriceEditText) ;
        tPrice.setText("5");
        BuyBtn=(Button) findViewById(R.id.completePurcuseBtn) ;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID=user.getUid();
        productDatabase = FirebaseDatabase.getInstance().getReference().child("Products");
        cartDatabase = FirebaseDatabase.getInstance().getReference().child("Cart").child(userID);
        final ListView lView = (ListView)findViewById(R.id.listView);
        lView.setAdapter(adapter);

       // products = new Firebase("https://ariexpress-3bb59.firebaseio.com/Products");


        cartDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                arrCart.add(dataSnapshot.getKey());
                Log.d("check:::",arrCart.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                arrCart.remove(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        productDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {
                String item=dataSnapshot.getKey();
                Log.d("datasnapshot key = ", item+" "+arrCart.toString());
                if(arrCart.contains(item)){
                    Log.d("datasnapshot key = ", dataSnapshot.getKey());
                    ProductWithID prod = dataSnapshot.getValue(ProductWithID.class);
                    prod.ID=(dataSnapshot.getKey());
                    Log.d("Value = ", prod.toString());
                    productsList.add(prod);
                    adapter.notifyDataSetChanged();
                    for (int i=0 ;i<productsList.size();i++) {
                        totPrice+=(productsList.get(i).price*productsList.get(i).amount);
                        Log.d("tempPrice",totPrice+"");
                    }
                }
                else{
                    Log.d("QWER","something wrong");
                }

            }

            @Override
            public void onChildChanged(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {
                for (int i=0 ;i<productsList.size();i++) {
                    totPrice+=(productsList.get(i).price*productsList.get(i).amount);
                    Log.d("tempPrice",totPrice+"");
                }
            }

            @Override
            public void onChildRemoved(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (int i=0 ;i<productsList.size();i++) {
                    totPrice+=(productsList.get(i).price*productsList.get(i).amount);
                    Log.d("tempPrice",totPrice+"");
                }
            }

            @Override
            public void onChildMoved(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        adapter.notifyDataSetChanged();
//        prodList=adapter.getList();
//        Log.d("canitbe",adapter.getCount()+"");
//        for (int i=0 ;i<prodList.size();i++) {
//            totPrice+=(prodList.get(i).price*prodList.get(i).amount);
//            Log.d("tempPrice",totPrice+"");
//        }
//        Log.d("TOTALppp",totPrice+"");
//        tPrice.setText(totPrice+"");
//
//
//        tPrice.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                totPrice=0;
//                for (ProductWithID pwi: prodList) {
//                    totPrice+=(pwi.price*pwi.amount);
//                }
//                tPrice.setText(totPrice+"");
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

       MyCustomAdapterCart adapt=( MyCustomAdapterCart )lView.getAdapter();

       ArrayList<ProductWithID> products=adapt.getList();
    }
}
