package com.example.roosevelt.networkinginandroidlab;

/**
 * Created by roosevelt on 8/4/16.
 */
public class GroceryItem {

    private String name, price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public GroceryItem(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
