package com.example.itinerarybuddy.data;

import java.util.ArrayList;

public class Post_Itinerary {

    public String username;
    public String timePosted;
    public String postFile;
    public String caption;
    public int likeCount;
    public boolean likeValue;
    public int saveCount;
    public boolean saveValue;

    private ArrayList<String> comments;
    public Post_Itinerary(String username, String timePosted, String postFile, String caption){

        this.username = username;
        this.timePosted = timePosted;
        this.postFile = postFile;
        this.caption = caption;

        this.likeCount = 0;
        this.likeValue = false;

        this.saveCount = 0;
        this.saveValue = false;

        this.comments = new ArrayList<>();
    }

    public String getUsername(){
        return username;
    }

    public String getTimePosted(){
        return timePosted;
    }

    public void setTimePosted(String time){

        timePosted = time;
    }

    public String getPostFile(){
        return postFile;
    }

    public String getCaption(){
        return caption;
    }


    public boolean isLiked() {

        return likeValue;
    }

    public int getLikeCount() {

        return likeCount;
    }

    public void setLiked(boolean b) {

        likeValue = b;
    }

    public void decreaseLikeCount() {

        likeCount--;

    }

    public void increaseLikeCount() {

        likeCount++;
    }

    public void setSaved(boolean b) {

        saveValue = b;
    }

    public void increaseSaveCount() {

        saveCount++;
    }

    public void decreaseSaveCount() {

        saveCount--;
    }

    public boolean isSaved() {

        return saveValue;
    }

    public int getSavedCount() {

        return saveCount;
    }

    public ArrayList<String> getComments() {

        return comments;

    }

    public void addComment(String comment) {
        comments.add(comment);
    }
}
