package MS3_3.Backend.UserTypes;

import MS3_3.Backend.AdminDashboard.Admin;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.LogManager;

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

    public User(String email, String userName,String password,String state,String city,
                String userType){
        this.email = email;
        this.state = state;
        this.city = city;
        this.userType = userType;
        this.numPosts = 0;
        this.numLikes = 0;
        this.canPost = true;
        if(this.userType.equals("Admin")){

        }
    }
    public User() {
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
