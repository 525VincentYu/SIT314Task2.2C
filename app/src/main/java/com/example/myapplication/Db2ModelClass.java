package com.example.myapplication;

import android.graphics.Bitmap;

import java.io.Serializable;
//  database order class type
public class Db2ModelClass implements Serializable {
    Bitmap image;
    String name;
    String discription;

    public Db2ModelClass(Bitmap image, String name, String discription) {
        this.image = image;
        this.name = name;
        this.discription = discription;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
