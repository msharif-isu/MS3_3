package com.example.itinerarybuddy.data;

public class Post_Itinerary {

    private String username;
    private String timePosted;
    private String postFile;
    private String caption;

    public Post_Itinerary(String username, String timePosted, String postFile, String caption){

        this.username = username;
        this.timePosted = timePosted;
        this.postFile = postFile;
        this.caption = caption;
    }

    public String getUsername(){
        return username;
    }

    public String getTimePosted(){
        return timePosted;
    }

    public String getPostFile(){
        return postFile;
    }

    public String getCaption(){
        return caption;
    }


}
