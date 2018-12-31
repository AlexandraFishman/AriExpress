package com.example.emotz.ariexpress.modules;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emotz.ariexpress.ListViewRegistered;
import com.example.emotz.ariexpress.LogInActivity;
import com.example.emotz.ariexpress.R;
import com.example.emotz.ariexpress.UserCart;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static java.lang.Math.max;

public class MyCustomAdapterCart extends BaseAdapter implements ListAdapter  {

    private ArrayList<ProductWithID> list = new ArrayList<ProductWithID>();
    private Context context;
    private DatabaseReference productsDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    public double price;
    public double currentPrice;
    public UserCart myCart;
    public Button BuyBtn;


    public MyCustomAdapterCart(ArrayList<ProductWithID> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {

        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    public ArrayList<ProductWithID> getList() {
        return this.list;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final String ID=list.get(position).ID;
        View view = convertView;
        notifyDataSetChanged();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cortume_layout_cart_list, null);
        }

        //Handle TextView and display string from your list
        //here we take myCart single item and print it to the list, I took only name
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).name);
      price=list.get(position).price;
        //Handle buttons and add onClickListeners
        Button dltBtn = (Button)view.findViewById(R.id.cart_btn);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID=user.getUid();
        myCart = (UserCart) context;


       dltBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                    Log.d("productID", ID);
                removeItem(ID,position);
                    //parent.getChildAt(0).getId();
                    //Log.d("out: ", );
                notifyDataSetChanged();
            }
        });

       list.get(position).amountEditTxt =(EditText)view.findViewById(R.id.emountEditText);




        list.get(position).amountEditTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                list.get(position).amount =Integer.parseInt(list.get(position).amountEditTxt.getText().toString());
                double totPrice = 0;
                for (int i=0 ;i<list.size();i++) {
                    totPrice+=(list.get(i).price*list.get(i).amount);
                    Log.d("tempPrice123",list.get(i).name+" "+list.get(i).price+" "+list.get(i).amount);
                    Log.d("tempPrice1234",totPrice+"");
                    myCart.tPrice.setText(totPrice+"");
                }
                return false;
            }
        });

Log.d("f123454321", myCart.tPrice.getText().toString());

        BuyBtn=myCart.BuyBtn;

        BuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ProductWithID pwi: list    ) {
                    updateQuan(pwi);
                    removeItemFromDatabase(pwi.ID);
                    context.startActivity(new Intent(context.getApplicationContext(),
                            ListViewRegistered.class));
                }
            }
        });

        notifyDataSetChanged();
        return view;
    }

    private void updateQuan(ProductWithID pwi) {

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Products").child(pwi.ID).child("quantity");
        int newQuan=max(0,pwi.quantity-pwi.amount);
        databaseReference.setValue(newQuan);

        Toast.makeText(context, "Your Order is in process\nThank you for choosing AriExpress!",
                Toast.LENGTH_SHORT).show();
    }


//    public void addNewItem(String productName, String productID){
//
//        productsDatabase = FirebaseDatabase.getInstance().getReference().child("Cart").child(userID);
//        productsDatabase.child(productID).setValue(productID)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
////                        Toast.makeText(MyCustomAdapter.this,"Item was added successfully!!",
////                                Toast.LENGTH_LONG).show();
//                    }
//                })
//
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
////                        Toast.makeText(MyCustomAdapter.this,"Failed adding item",
////                                Toast.LENGTH_LONG).show();
//                    }
//                });
//    }

    public void removeItem(String productID, final int position){
        productsDatabase = FirebaseDatabase.getInstance().getReference().child("Cart").child(userID);
        productsDatabase.child(productID).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(AdminActivity.this,"Item removed!!",
//                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
//                        Toast.makeText(AdminActivity.this,"Failed removing item",
//                                Toast.LENGTH_LONG).show();
                    }

                });
        list.remove(position);
   }
    public void removeItemFromDatabase(String productID){
        productsDatabase = FirebaseDatabase.getInstance().getReference().child("Cart").child(userID);
        productsDatabase.child(productID).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(AdminActivity.this,"Item removed!!",
//                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
//                        Toast.makeText(AdminActivity.this,"Failed removing item",
//                                Toast.LENGTH_LONG).show();
                    }

                });
    }
}