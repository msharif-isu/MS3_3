package MS3_3.Backend;

import MS3_3.Backend.AdminDashboard.Admin;
import MS3_3.Backend.AdminDashboard.AdminRepository;
import MS3_3.Backend.Ambassador.Ambassador;
import MS3_3.Backend.Ambassador.AmbassadorController;
import MS3_3.Backend.Ambassador.AmbassadorRepository;
import MS3_3.Backend.AmbassadorBlogPost.BlogPost;
import MS3_3.Backend.FileUpload.Image;
import MS3_3.Backend.FileUpload.ImageRepository;
import MS3_3.Backend.TravelGroups.TravelGroup;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class JacksonSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AmbassadorRepository ambassadorRepository;

    @Autowired
    private ImageRepository imageRepository;

    @LocalServerPort
    int port;


    @Before
    public void setup() {
        RestAssured.baseURI = "http://coms-309-035.class.las.iastate.edu";
        RestAssured.port = port;
    }

    @Test
    public void testAUserPost() {
        String userName = "Jackson";
        User user = new User("email",  "Jackson",  "123",  "state",  "city",
                "User");
        try {
            ResponseEntity<User> response = restTemplate.postForEntity("/Users/Create", user, User.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().getUserName()).isEqualTo(userName);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBGetUserByUserName() {
        // Arrange
        String userName = "Jackson";

        // Act
        ResponseEntity<User> response = restTemplate.getForEntity("/Users/{userName}", User.class, userName);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserType()).isEqualTo("User");
    }

    @Test
    public void testCTryLogin() {
        // Arrange
        String userName = "Jackson";
        String password = "123";

        // Act
        ResponseEntity<User> response = restTemplate.getForEntity("/Users/login/{userName}/{password}", User.class, userName, password);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserName()).isEqualTo(userName);
        assertThat(response.getBody().getPassword()).isEqualTo(password);
    }

    @Test
    public void testDGroupPostNonAmbassador() {
        String travelDestination = "Europe";
        User user = new User("email",  "NotAmbassador",  "password",  "state",  "city",
                "User");
        TravelGroup group = new TravelGroup("Traveling to EU", "EU", "NotAmbassador", "Europe", "To Europe");
        imageRepository.save(new Image("Image",null,null));
        try {
            ResponseEntity<TravelGroup> response = restTemplate.postForEntity("/Group", group, TravelGroup.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEGroupPostIsAmbassador() {
        String travelDestination = "Europe";
        User user = new User("email",  "userName",  "password",  "state",  "city",
                "User");
        Ambassador ambassador = new Ambassador(user);
        ambassadorRepository.save(ambassador);
        try {
            ResponseEntity<TravelGroup> response = restTemplate.postForEntity("/Group", new TravelGroup("Traveling to EU", "EU", "userName", "Europe", "To Europe"), TravelGroup.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().getTravelGroupDestination()).isEqualTo("Europe");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFGrantAdmin() {
        // Arrange
        String adminUserName = "admin";
        User user = new User("email",  "userName",  "password",  "state",  "city",
                "User");
        User user2 = new User("email",  "userName2",  "password2",  "state",  "city",
                "User");
        user.setUserType("Admin");
        Admin admin = new Admin(user);
        adminRepository.save(admin);
        userRepository.save(user);
        userRepository.save(user2);
        restTemplate.put("/Admin/Grant/{adminUserName}/{userName}", Admin.class, admin.getUserName(), user2.getUserName());
        ResponseEntity<User> response = restTemplate.getForEntity("/Users/{userName}", User.class, user2.getUserName());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserType()).isEqualTo("Admin");

    }

    @Test
    public void testGRevokeAmbassador() {
        User user = new User("email",  "userName",  "password",  "state",  "city",
                "User");
        User user2 = new User("email",  "userName2",  "password2",  "state",  "city",
                "User");
        user.setUserType("Admin");
        Admin admin = new Admin(user);
        Ambassador ambassador = new Ambassador(user2);
        user2.setUserType("Ambassador");
        ambassadorRepository.save(ambassador);
        adminRepository.save(admin);
        userRepository.save(user);
        userRepository.save(user2);
        restTemplate.put("/Ambassador/Revoke/{adminUserName}/{userName}", Admin.class, admin.getUserName(), user2.getUserName());
        ResponseEntity<User> response = restTemplate.getForEntity("/Users/{userName}", User.class, user2.getUserName());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserType()).isEqualTo("User");
    }

    @Test
    public void testHImageUpload() {
        try {
            ResponseEntity<Image> response = restTemplate.getForEntity("/Group/Image/{groupId}", Image.class, 1);
            assertThat(response.getBody().getImageData()).isEqualTo(null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIBlogPostCreation() {
        // Arrange
        String userName = "userName";
        String blogName = "Test Blog";
        String postDate = "Today";

        BlogPost BlogPost;
        ResponseEntity<BlogPost> response = restTemplate.postForEntity("/BlogPost/{userName}/{blogName}/{postDate}",
                BlogPost.class, BlogPost.class, userName, blogName, postDate);

        assertThat(response.getBody().getBlogPostTitle()).isEqualTo("Test Blog");
        assertThat(response.getBody().getPostDate()).isEqualTo("Today");
    }
}
