package MS3_3.Backend;

import MS3_3.Backend.AdminDashboard.Admin;
import MS3_3.Backend.Ambassador.Ambassador;
import MS3_3.Backend.Ambassador.AmbassadorController;
import MS3_3.Backend.Ambassador.AmbassadorRepository;
import MS3_3.Backend.FileUpload.Image;
import MS3_3.Backend.FileUpload.ImageRepository;
import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.Test;
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
public class JacksonSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AmbassadorRepository ambassadorRepository;

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
    public void testUserPost() {
        String userName = "UserName";
        User user = new User("email",  userName,  "password",  "state",  "city",
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
    public void testGetUserByUserName() {
        // Arrange
        String userName = "UserName";

        // Act
        ResponseEntity<User> response = restTemplate.getForEntity("/Users/{userName}", User.class, userName);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserName()).isEqualTo(userName);
    }

    @Test
    public void testGroupPostNonAmbassador() {
        String travelDestination = "Europe";
        User user = new User("email",  "NotAmbassador",  "password",  "state",  "city",
                "User");
        TravelGroup group = new TravelGroup("Traveling to EU", "EU", "NotAmbassador", "Europe", "To Europe");
        imageRepository.save(new Image(null,null,null));
        try {
            ResponseEntity<TravelGroup> response = restTemplate.postForEntity("/Group", group, TravelGroup.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGroupPostIsAmbassador() {
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
/**
    @Test
    public void testGrantAmbassador() {
        // Arrange
        String adminUserName = "admin";
        User user = new User("email",  "userName",  "password",  "state",  "city",
                "User");
        Admin admin = new Admin(user);

        ResponseEntity<Ambassador> response = restTemplate.put("/Ambassador/Grant/{adminUserName}/{userName}", user, User.class, admin, Admin.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserType()).isEqualTo("Ambassador");

    }

@Test
public void testGrantAmbassador() {
    // Arrange
    String UserName = "userName";
    String Password = "123";
    User user = new User("email", UserName, Password, "state", "city",
            "User");


    ResponseEntity<User> response = restTemplate.get("/Users/login/{userName}/{password}", UserName, Password, User.class);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getUserType()).isEqualTo("Ambassador");
    }
*/
}


