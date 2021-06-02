package com.example.foodapp;
import android.graphics.Bitmap;

public class Model {
    String imagetitle;
    String imagedes;
    String date;
    String location;
    String quantity;
    Bitmap image;


    public Model(String imagetitle, String imagedes, Bitmap image, String date, String location, String quantity) {
        this.imagetitle = imagetitle;
        this.imagedes = imagedes;
        this.image = image;
        this.date = date;
        this.quantity = quantity;
        this.location = location;
    }

    public String getImagetitle() {
        return imagetitle;
    }


    public String getImagedes() {
        return imagedes;
    }


    public String getQuantity() {
        return quantity;
    }


    public Bitmap getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }



    public String getDate() {
        return date;
    }

}
