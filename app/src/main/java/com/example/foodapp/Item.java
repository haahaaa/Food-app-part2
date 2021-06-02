package com.example.foodapp;

import android.graphics.Bitmap;

public class Item {
    private final String title;
    private final String quantity;
    private final Bitmap image;

    public String getTitle() {
        return title;
    }

    public String getQuantity() {
        return quantity;
    }

    public Bitmap getImage() {
        return image;
    }

    public Item(String title, String quantity, Bitmap image) {

        this.title = title;
        this.quantity = quantity;
        this.image = image;
    }
}
