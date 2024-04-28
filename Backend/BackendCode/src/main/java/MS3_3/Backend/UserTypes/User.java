package MS3_3.Backend.UserTypes;

import MS3_3.Backend.PersonalItinerary.Events.PersonalItineraryEvent;
import MS3_3.Backend.PersonalItinerary.PersonalItinerary;
import MS3_3.Backend.TravelGroups.TravelGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    private String email;

    @Id
    private String userName;

    private String password;

    private String state;

    private String city;

    private String userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonalItinerary> personalItineraries;

    private int numPosts;

    private int numLikes;

    private boolean canPost;


    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    @JsonIgnore
    private List<TravelGroup> groupCodes;

    @ElementCollection
    private List<Integer> MemberOf;

    public User(String email, String userName, String password, String state, String city,
                String userType) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.state = state;
        this.city = city;
        this.userType = userType;
        this.numPosts = 0;
        this.numLikes = 0;
        this.canPost = true;
        this.MemberOf = new ArrayList<>();
        this.groupCodes = new ArrayList<>();
        this.personalItineraries = new ArrayList<>();
    }

    public User() {
        this.MemberOf = new ArrayList<>();
        this.groupCodes = new ArrayList<>();
        this.personalItineraries = new ArrayList<>();
    }

    public List<PersonalItinerary> getPersonalItineraries() {
        return personalItineraries;
    }

    public void setPersonalItineraries(List<PersonalItinerary> personalItinerary) {
        this.personalItineraries = personalItinerary;
    }

    public List<Integer> getUserCodes() {
        return MemberOf;
    }

    public void setUserCodes(List<Integer> groupCodes) {
        this.MemberOf = groupCodes;
    }

    public void addUserCodes(Integer groupCode) {
        this.MemberOf.add(groupCode);
    }

    public void removeUserCodes(Integer groupCode) {
        this.MemberOf.remove(groupCode);
    }


    public List<TravelGroup> getGroupCodes() {
        return groupCodes;
    }

    public void setGroupCodes(List<TravelGroup> groupCodes) {
        this.groupCodes = groupCodes;
    }

    public void addGroupCodes(TravelGroup groupName) {
        this.groupCodes.add(groupName);
    }

    public void removeGroupCodes(TravelGroup groupName) {
        this.groupCodes.remove(groupName);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getUserType() {
        return userType;
    }

    public int getAccountLikes() {
        return this.numLikes;
    }

    public void addAccountLikes() {
        this.numLikes += 1;
    }

    public void addUserPosts() {
        this.numPosts += 1;
    }

    public int getNumPosts() {
        return this.numPosts;
    }

    public void blockPosts() {
        this.canPost = false;
    }

    public void EnablePosting() {
        this.canPost = true;
    }

    public boolean CanPost() {
        return this.canPost;
    }

    public void upgradeUserToAmbassador() {
        if (getNumPosts() > 10 && getAccountLikes() > 200) {
            this.userType = "Ambassador";
        }

    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}

