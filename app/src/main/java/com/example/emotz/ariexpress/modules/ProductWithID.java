package com.example.emotz.ariexpress.modules;

import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class ProductWithID {

    public String name;
    public double price;
    public int quantity;
    public String ID;
    public int amount;
    public EditText amountEditTxt;

    public ProductWithID(){

    }

    public ProductWithID(Product prod){
            this.name = prod.name;
            this.price = prod.price;
            this.quantity = prod.quantity;
            this.amount=1;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", name);
        result.put("Price", price);
        result.put("Quantity", quantity);


        return result;
    }



    @Override
    public String toString() {
        super.toString();
        return ("Name: "+this.name+"\n"+ "ID: "+this.ID +"\n"+"Price: "+this.price+"\n"+"Quantity: "+this.quantity);
    }
}
