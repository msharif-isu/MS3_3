package com.example.itinerarybuddy.data;

import java.util.Date;

public class BlogItem {

    private String title;
    private String username;
    private String postDate;
    private String imageUrl;

    public BlogItem(String title, String username, String postDate, String imageUrl){

        this.title = title;
        this.username = username;
        this.postDate = postDate;
        this.imageUrl = imageUrl;
    }

    public String getTitle(){
        return title;
    }

    public String getUsername(){

        return username;

    }
    public String getPostDate(){
        return postDate;
    }

    public String getImageUrl(){

        return imageUrl;
    }
}
