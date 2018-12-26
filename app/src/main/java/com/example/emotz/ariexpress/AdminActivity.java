package com.example.emotz.ariexpress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import com.example.emotz.ariexpress.MainActivity;
import com.example.emotz.ariexpress.modules.Product;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AdminActivity extends AppCompatActivity {

    private Button addProductButton;
    private Button removeProductButton;

    private EditText productName;
    private EditText productPrice;
    private EditText productQuantity;

    private DatabaseReference productsDatabase;

    public boolean addNewItem(EditText productName, EditText productPrice, EditText productQuantity){
        try {
            Product newProduct = new Product(productName.getText().toString(), Double.parseDouble(productPrice.getText().toString()), Integer.parseInt(productQuantity.getText().toString()));
            productsDatabase = FirebaseDatabase.getInstance().getReference().child("Products").push();
            productsDatabase.setValue(newProduct);
            Toast.makeText(AdminActivity.this, newProduct.toString(),
                    Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e){
            Toast.makeText(AdminActivity.this, e.toString(),
                    Toast.LENGTH_LONG).show();
            return false;
        }
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

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == addProductButton){
                    boolean success = addNewItem(productName,productPrice,productQuantity);
                    String msg = productName.getText().toString()+ " "+ productPrice.getText().toString() + " " + productQuantity.getText().toString();
//                    Toast.makeText(AdminActivity.this, msg,
//                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
