package com.example.emotz.ariexpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.emotz.ariexpress.modules.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class AdminActivity extends AppCompatActivity {

    private Button addProductButton;
    private Button removeProductButton;

    private EditText productName;
    private EditText productPrice;
    private EditText productQuantity;
    private EditText productId;

    private DatabaseReference productsDatabase;

    public void addNewItem(EditText productName, EditText productPrice, EditText productQuantity){
            Product newProduct = new Product(productName.getText().toString(), Double.parseDouble(productPrice.getText().toString()), Integer.parseInt(productQuantity.getText().toString()));
            productsDatabase = FirebaseDatabase.getInstance().getReference().child("Products").push();
            productsDatabase.setValue(newProduct)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminActivity.this,"Item was added successfully!!",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AdminActivity.this,"Failed adding item",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void removeItem(EditText productId){
        productsDatabase = FirebaseDatabase.getInstance().getReference().child("Products").child(productId.getText().toString());
        productsDatabase.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminActivity.this,"Item removed!!",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AdminActivity.this,"Failed removing item",
                                Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addProductButton = (Button)findViewById(R.id.AddButton);
        removeProductButton = (Button)findViewById(R.id.DeleteButton);
        productName = (EditText)findViewById(R.id.ProductNameTextBox);
        productPrice = (EditText)findViewById(R.id.productPriceTextBox);
        productQuantity = (EditText)findViewById(R.id.productQuantityTextBox);
        productId = (EditText)findViewById(R.id.productIdTextBox2);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == addProductButton){
                    addNewItem(productName,productPrice,productQuantity);
                }
            }
        });

        removeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == removeProductButton){
                    removeItem(productId);
                }
            }
        });
    }
}
