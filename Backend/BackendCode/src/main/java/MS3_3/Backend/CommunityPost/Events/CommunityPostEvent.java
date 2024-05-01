package MS3_3.Backend.CommunityPost.Events;

import MS3_3.Backend.CommunityPost.CommunityPost;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class CommunityPostEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int communityPostEventId;

    @ManyToOne
    @JoinColumn(name = "communityPostId")
    @JsonIgnore
    private CommunityPost communityPost;

    private int dayNum;
    private String time;
    private String place;
    private String notes;

    public CommunityPostEvent() {}

    public CommunityPostEvent(int dayNum, String time, String place, String notes) {
        this.dayNum = dayNum;
        this.time = time;
        this.place = place;
        this.notes = notes;
    }

    public CommunityPostEvent(CommunityPost communityPost, int dayNum, String time, String place, String notes) {
        this.communityPost = communityPost;
        this.dayNum = dayNum;
        this.time = time;
        this.place = place;
        this.notes = notes;
    }

    public CommunityPost getCommunityPost() {
        return communityPost;
    }

    public void setCommunityPost(CommunityPost communityPost) {
        this.communityPost = communityPost;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public int getId() {
        return communityPostEventId;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getNotes() {
        return notes;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setEvent(CommunityPostEvent event) {
        this.dayNum = event.getDayNum();
        this.time = event.getTime();
        this.notes = event.getNotes();
        this.place = event.getPlace();
    }
}
