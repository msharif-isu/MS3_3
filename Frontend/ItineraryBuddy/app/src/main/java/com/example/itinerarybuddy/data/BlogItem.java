package com.example.itinerarybuddy.data;

public class BlogItem {

    private String title;
    private String username;
    private String postDate;
    private String imageUrl;

    private int blogID;

    public BlogItem(String title, String username, String postDate){

        this.title = title;
        this.username = username;
        this.postDate = postDate;

    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String changedTitle){

        title = changedTitle;
    }

    public void setBlogID(int blogID){

        this.blogID = blogID;


    }

    public int getBlogID() {
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
