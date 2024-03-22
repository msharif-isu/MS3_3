package MS3_3.Backend.Ambassador;

import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.UserTypes.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Ambassador{
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

    @OneToMany
    private List<TravelGroup> groups_created;


    public Ambassador(User user){
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.state = user.getState();
        this.city = user.getCity();
        this.userType = "Ambassador";
        this.numPosts = user.getNumPosts();
        this.numLikes = user.getAccountLikes();
        this.canPost = true;
        this.groups_created = new ArrayList<>();
    }

    public Ambassador() {
        this.groups_created = new ArrayList<>();
    }

    public List<TravelGroup> getGroups_created() {
        return this.groups_created;
    }

    public void addGroup(TravelGroup groupName){
        this.groups_created.add(groupName);
    }

    public void removeGroup(TravelGroup groupName){
        this.groups_created.remove(groupName);
    }

    public void setGroups_created(List<TravelGroup> groups_created) {
        this.groups_created = groups_created;
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

    public boolean CanPost() {
        return this.canPost;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
