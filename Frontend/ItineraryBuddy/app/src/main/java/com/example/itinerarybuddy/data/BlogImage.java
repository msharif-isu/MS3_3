package com.example.itinerarybuddy.data;

import android.graphics.Bitmap;

public class BlogImage {

    private final Bitmap image;

    private final int id;

    public BlogImage(Bitmap image, int id){
        this.image = image;
        this.id = id;
    }

    public Bitmap getBitmap(){
        return image;
    }

    public int getId(){
        return id;
    }

}
