//package MS3_3.Backend;
//
//import MS3_3.Backend.CommunityPost.CommunityPost;
//import io.restassured.RestAssured;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class NoahSystemTest {
//    @LocalServerPort
//    static
//    int port;
//    @BeforeAll
//    public static void setUp() {
//        RestAssured.baseURI = "http://coms-309-035.class.las.iastate.edu";
//        RestAssured.port = 8080;
//    }
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @Test
//    public void testCreateCommunityPost() {
//        // Arrange
//        String userName = "testUser";
//        CommunityPost communityPost = new CommunityPost();
//        // Set community post attributes as needed for the test
//
//        // Act
//        ResponseEntity<CommunityPost> response = restTemplate.postForEntity("/CommunityPost/{userName}", communityPost, CommunityPost.class, userName);
//
//        // Assert
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//        // Add more assertions based on the expected behavior
//    }
//
//    @Test
//    public void testGetCommunityPostById() {
//        // Arrange
//        int communityPostId = 1; // Assuming this community post ID exists in the database
//
//        // Act
//        ResponseEntity<CommunityPost> response = restTemplate.getForEntity("/CommunityPost/{communityPostId}", CommunityPost.class, communityPostId);
//
//        // Assert
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//        // Add more assertions based on the expected behavior
//    }
//
//    @Test
//    public void testUpdateCommunityPost() {
//        // Arrange
//        int communityPostId = 1; // Assuming this community post ID exists in the database
//        CommunityPost updatedCommunityPost = new CommunityPost();
//        // Set updated attributes for the community post
//
//        // Act
//        restTemplate.put("/CommunityPost/{communityPostId}", updatedCommunityPost, communityPostId);
//        ResponseEntity<CommunityPost> response = restTemplate.getForEntity("/CommunityPost/{communityPostId}", CommunityPost.class, communityPostId);
//
//        // Assert
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//        // Add more assertions based on the expected behavior after update
//    }
//
//    @Test
//    public void testDeleteCommunityPost() {
//        // Arrange
//        int communityPostId = 1; // Assuming this community post ID exists in the database
//
//        // Act
//        restTemplate.delete("/CommunityPost/{communityPostId}", communityPostId);
//        ResponseEntity<CommunityPost> response = restTemplate.getForEntity("/CommunityPost/{communityPostId}", CommunityPost.class, communityPostId);
//
//        // Assert
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNull(); // Community post should not exist after deletion
//    }
//
//    @Test
//    public void testGetAllCommunityPosts() {
//        // Act
//        ResponseEntity<CommunityPost[]> response = restTemplate.getForEntity("/CommunityPost", CommunityPost[].class);
//
//        // Assert
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//        // Add more assertions based on the expected behavior of getting all community posts
//    }
//}
