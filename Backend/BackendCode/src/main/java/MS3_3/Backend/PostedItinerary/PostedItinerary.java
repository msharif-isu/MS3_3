package MS3_3.Backend.PostedItinerary;

import MS3_3.Backend.Day.Day;
import MS3_3.Backend.UserTypes.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;

@Entity
public class PostedItinerary {

    public User creator;
    public String timePosted;
    //Destination
    private String postFile;
    private String caption;
    private int likeCount;
    private boolean likeValue;
    private int saveCount;
    private boolean saveValue;
    private String tripCode;
    private int numDays;
    @Id
    private String postID;
    private ArrayList<Comment> comments;
    private Day[] days;

    public PostedItinerary(){}

    public PostedItinerary(User creator, String timePosted, String postFile, String caption, int likeCount, boolean likeValue, int saveCount, boolean saveValue, String tripCode, int numDays, String postID, ArrayList<Comment> comments, Day[] days) {
        this.creator = creator;
        this.timePosted = timePosted;
        this.postFile = postFile;
        this.caption = caption;
        this.likeCount = likeCount;
        this.likeValue = likeValue;
        this.saveCount = saveCount;
        this.saveValue = saveValue;
        this.tripCode = tripCode;
        this.numDays = numDays;
        this.postID = postID;
        this.comments = comments;
        this.days = days;
    }

    public User getCreator() {
        return creator;
    }

    public String getTimePosted() {
        return timePosted;
    }

    public String getPostFile() {
        return postFile;
    }

    public String getCaption() {
        return caption;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public boolean getLikeValue() {
        return likeValue;
    }

    public int getSaveCount() {
        return saveCount;
    }

    public boolean getSaveValue() {
        return saveValue;
    }

    public String getTripCode() {
        return tripCode;
    }

    public int getNumDays() {
        return numDays;
    }

    public String getPostID() {
        return postID;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public Day[] getDays() {
        return days;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setTimePosted(String timePosted) {
        this.timePosted = timePosted;
    }

    public void setPostFile(String postFile) {
        this.postFile = postFile;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setLikeValue(boolean likeValue) {
        this.likeValue = likeValue;
    }

    public void setSaveCount(int saveCount) {
        this.saveCount = saveCount;
    }

    public void setSaveValue(boolean saveValue) {
        this.saveValue = saveValue;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setDays(Day[] days) {
        this.days = days;
    }

    public static class Comment{
        private User creator;
        private String commentText;

        public Comment(User creator, String commentText) {
            this.creator = creator;
            this.commentText = commentText;
        }

        public User getCreator(){
            return creator;
        }

        public String getUsername() {
            return creator.getUserName();
        }

        public String getCommentText() {
            return commentText;
        }

        public void setCreator(User creator) {
            this.creator = creator;
        }

        public void setCommentText(String commentText) {
            this.commentText = commentText;
        }
    }
}
