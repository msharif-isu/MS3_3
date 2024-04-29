package com.example.itinerarybuddy.data;

public class BlogItem {

    private String title;
    private String username;
    private String postDate;
    private String imageUrl;

    private String blogID;

    public BlogItem(String title, String username, String postDate, String blogID){

        this.title = title;
        this.username = username;
        this.postDate = postDate;
        this.blogID = blogID;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String changedTitle){

        title = changedTitle;
    }

    public String getBlogID() {
        return blogID;
    }

    public String getUsername(){

        return username;

    }
    public String getPostDate(){
        return postDate;
    }

    public void setImageUrl(String imageUrl){

        this.imageUrl = imageUrl;
    }
    public String getImageUrl(){

        return imageUrl;
    }
}
