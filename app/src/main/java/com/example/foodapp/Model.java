package com.example.foodapp;
import android.graphics.Bitmap;

public class Model {
    String imagetitle;
    String imagedes;
    String date;
    String location;
    Bitmap image;


    public Model(String imagetitle, String imagedes, Bitmap image,String date,String location) {
        this.imagetitle = imagetitle;
        this.imagedes = imagedes;
        this.image = image;
        this.date = date;
        this.location = location;
    }

    public String getImagetitle() {
        return imagetitle;
    }

    public void setImagetitle(String imagetitle) {
        this.imagetitle = imagetitle;
    }

    public String getImagedes() {
        return imagedes;
    }

    public void setImagedes(String imagedes) {
        this.imagedes = imagedes;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
