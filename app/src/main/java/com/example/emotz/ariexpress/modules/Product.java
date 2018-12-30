package com.example.emotz.ariexpress.modules;

import java.util.HashMap;
import java.util.Map;

public class Product {

    public String name;
    public double price;
    public int quantity;


    public Product(){

    }

    public Product(String name, double price, int quantity){
            this.name = name;
            this.price = price;
            this.quantity = quantity;
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
        return ("Name: "+this.name+"\n"+"Price: "+this.price+"\n"+"Quantity: "+this.quantity);
    }
}
