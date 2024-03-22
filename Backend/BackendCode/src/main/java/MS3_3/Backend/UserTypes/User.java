package MS3_3.Backend.UserTypes;

import MS3_3.Backend.AdminDashboard.Admin;
import MS3_3.Backend.AdminDashboard.AdminRepository;
import MS3_3.Backend.Ambassador.Ambassador;
import MS3_3.Backend.Ambassador.AmbassadorRepository;
import MS3_3.Backend.Groups.TravelGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;

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

    private int numPosts;

    private int numLikes;

    private boolean canPost;

  @ManyToMany
      @JoinTable(name = "group_members", joinColumns = @JoinColumn(name = "user_name"), inverseJoinColumns = @JoinColumn(name = "id"))
  @JsonIgnore
  private List<TravelGroup> groups;



    public User(String email, String userName,String password,String state,String city,
                String userType){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.state = state;
        this.city = city;
        this.userType = userType;
        this.numPosts = 0;
        this.numLikes = 0;
        this.canPost = true;
        this.groups = new ArrayList<>();
    }
    public User() {
        this.groups = new ArrayList<>();
    }

    public List<TravelGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(List<TravelGroup> groupNames) {
        this.groups = groupNames;
    }

    public void addGroup(TravelGroup groupName){
        this.groups.add(groupName);
    }

    public void removeGroup(TravelGroup groupName){
        this.groups.remove(groupName);
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

    public int getAccountLikes(){
        return this.numLikes;
    }

    public void addAccountLikes(){
        this.numLikes += 1;
    }
    public void addUserPosts(){
        this.numPosts += 1;
    }

    public int getNumPosts() {
        return this.numPosts;
    }

    public void blockPosts(){
        this.canPost = false;
    }

    public void EnablePosting(){
        this.canPost = true;
    }

    public boolean CanPost() {
        return this.canPost;
    }

    public void upgradeUserToAmbassador(){
        if(getNumPosts() > 10 && getAccountLikes() > 200) {
            this.userType = "Ambassador";
        }

    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}

