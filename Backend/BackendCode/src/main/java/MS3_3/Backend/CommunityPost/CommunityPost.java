package MS3_3.Backend.CommunityPost;

import MS3_3.Backend.CommunityPost.Events.CommunityPostEvent;
import MS3_3.Backend.UserTypes.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int communityPostId;

    @ManyToOne
    @JsonIgnore
    private User creator;

    private String title;

    private int numLikes;

    private ArrayList<String> comments;

    private String postedDate;

    @OneToMany(mappedBy = "communityPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityPostEvent> communityPostEventsList;

    public CommunityPost() {
        this.communityPostEventsList = new ArrayList<>();
    }

    public CommunityPost(User creator, String title, int numLikes, ArrayList<String> comments, String postedDate) {
        this.creator = creator;
        this.title = title;
        this.numLikes = numLikes;
        this.comments = comments;
        this.postedDate = postedDate;
        this.communityPostEventsList = new ArrayList<>();
    }

    public int getCommunityPostID() {
        return communityPostId;
    }

    public void setCommunityPostID(int communityPostID) {
        this.communityPostId = communityPostID;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public List<CommunityPostEvent> getCommunityPostEventsList() {
        return communityPostEventsList;
    }

    public void setCommunityPostEventsList(List<CommunityPostEvent> communityPostEventsList) {
        this.communityPostEventsList = communityPostEventsList;
    }
}
