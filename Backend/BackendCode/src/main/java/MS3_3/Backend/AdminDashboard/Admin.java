package MS3_3.Backend.AdminDashboard;

import MS3_3.Backend.UserTypes.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Admin extends User{
    private String email;

    private String firstName;

    private String lastName;

    @Id
    private String userName;

    private String password;

    private String state;

    private String city;

    private String userType;

    private int numPosts;

    private int numLikes;

    private boolean canPost;
    public Admin(User user){
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.state = user.getState();
        this.city = user.getCity();
        this.userType = "Admin";
        this.numPosts = user.getNumPosts();
        this.numLikes = user.getAccountLikes();
        this.canPost = true;
    }

}
