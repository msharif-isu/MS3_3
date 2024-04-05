package MS3_3.Backend.PostedItinerary;

import MS3_3.Backend.TravelGroupChat.ChatSocket;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;

@Controller
@ServerEndpoint(value = "/PostedItineraries/ws/{username}")
public class PostedItineraryChatSocket {
    private static PostedItineraryRepository postRepo;
    private static CommentRepository comRepo;

    private static UserRepository userRepo;

    @Autowired
    public void setPostedItineraryRepository(PostedItineraryRepository repo) {
        postRepo = repo;
    }

    @Autowired
    public void setCommentRepository(CommentRepository repo) {
        comRepo = repo;
    }

    @Autowired
    public void setUserRepository(UserRepository repo) {
        userRepo = repo;
    }

    private static final Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static final Map<String, Session> usernameSessionMap = new Hashtable<>();
    private static final Map<String, Set<String>> postLikesMap = new HashMap<>();
    private static final Map<String, List<PostedItinerary.Comment>> postCommentsMap = new HashMap<>();
    public static final Map<String, Set<String>> userBookmarksMap = new HashMap<>();


    private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        logger.info("Entered Webpage");
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        for (PostedItinerary post : postRepo.findAll()) {
            Set<String> likes = new HashSet<>();
            postLikesMap.put(post.getPostID(), likes);
        }
        for (PostedItinerary post : postRepo.findAll()) {
            List<PostedItinerary.Comment> comments = new ArrayList<>();
            postCommentsMap.put(post.getPostID(), comments);
        }
        Set<String> bookmarkedPosts = userBookmarksMap.getOrDefault(username, new HashSet<>());
        for (PostedItinerary post : postRepo.findAll()) {
            if (bookmarkedPosts.contains(post.getPostID())) {
                bookmarkedPosts.add(post.getPostID());
            }
        }

        List<PostedItinerary> allPostedItineraries = postRepo.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();

        root.put("postedItineraries", objectMapper.valueToTree(allPostedItineraries));
        root.put("postLikesMap", objectMapper.valueToTree(postLikesMap));
        root.put("postCommentsMap", objectMapper.valueToTree(postCommentsMap));
        root.put("userBookmarksMap", objectMapper.valueToTree(userBookmarksMap));

        String jsonData = objectMapper.writeValueAsString(root);
        session.getBasicRemote().sendText(jsonData);

    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("username") String username) throws IOException {
        logger.info("Entered into Message: Got Message:" + message);

        //message format is "action:postId", for example: "like:postId"
        String[] parts = message.split(":");
        if (parts.length == 2) {
            String action = parts[0];
            String postID = parts[1];
            switch (action) {
                case "like":
                    addLikeToPost(postID, username);
                    break;
                case "unlike":
                    removeLikeFromPost(postID, username);
                    break;
                case "bookmark":
                    addBookmarkForUser(username, postID);
                    break;
                case "unbookmark":
                    removeBookmarkForUser(username, postID);
                    break;
                case "comment":
                    // Assuming the comment is in the format "commentText:postId"
                    String[] commentParts = postID.split(":");
                    if (commentParts.length == 2) {
                        String commentText = commentParts[0];
                        String postIdForComment = commentParts[1];
                        User creator = userRepo.findByUserName(username); // Fetch the User object based on the username
                        if (creator != null) {
                            PostedItinerary postedItinerary = postRepo.findByPostID(postIdForComment);
                            if (postedItinerary != null) {
                                PostedItinerary.Comment comment = new PostedItinerary.Comment(creator, postedItinerary, commentText, UUID.randomUUID().toString());
                                addCommentToPostedItinerary(postIdForComment, comment);
                            } else {
                                logger.error("PostedItinerary with postID " + postIdForComment + " not found.");
                            }
                        } else {
                            logger.error("User with username " + username + " not found.");
                        }
                    } else {
                        logger.error("Invalid comment format: " + message);
                    }
                    break;
                case "uncomment":
                    String[] uncommentParts = postID.split(":");
                    if (uncommentParts.length == 2) {
                        String commentID = uncommentParts[0];
                        String postIDForComment = uncommentParts[1];

                        // Fetch the comment by its ID
                        PostedItinerary.Comment commentToRemove = comRepo.findByCommentID(commentID);

                        if (commentToRemove != null) {
                            // Remove the comment from the posted itinerary
                            removeCommentFromPostedItinerary(postIDForComment, commentToRemove);
                        } else {
                            logger.error("Comment with ID " + commentID + " not found for postID " + postIDForComment);
                        }
                    } else {
                        logger.error("Invalid uncomment format: " + message);
                    }
                    break;
                default:
                    logger.error("Unknown action: " + action);
                    break;
            }
        } else {
            logger.error("Invalid message format: " + message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        String message = username + " disconnected";
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }

    public static void addLikeToPost(String postID, String username) {
        Set<String> likes = postLikesMap.getOrDefault(postID, new HashSet<>());
        likes.add(username);
        postLikesMap.put(postID, likes);
    }

    public static void removeLikeFromPost(String postID, String username) {
        Set<String> likes = postLikesMap.getOrDefault(postID, new HashSet<>());
        likes.remove(username);
        postLikesMap.put(postID, likes);
    }

    public static void addBookmarkForUser(String username, String postID) {
        Set<String> bookmarks = userBookmarksMap.getOrDefault(username, new HashSet<>());
        bookmarks.add(postID);
        userBookmarksMap.put(username, bookmarks);
    }

    public static void removeBookmarkForUser(String username, String postID) {
        Set<String> bookmarks = userBookmarksMap.getOrDefault(username, new HashSet<>());
        bookmarks.remove(postID);
        userBookmarksMap.put(username, bookmarks);
    }

    public static void addCommentToPostedItinerary(String postID, PostedItinerary.Comment comment) {
        PostedItinerary postedItinerary = postRepo.findByPostID(postID);
        if (postedItinerary != null) {
            postedItinerary.addComment(comment);
            List<PostedItinerary.Comment> comments = postCommentsMap.getOrDefault(postID, new ArrayList<>());
            comments.add(comment);
            postCommentsMap.put(postID, comments);
            postRepo.save(postedItinerary);
        } else {
            System.out.println("PostedItinerary with postID " + postID + " not found.");
        }
    }

    public static void removeCommentFromPostedItinerary(String postID, PostedItinerary.Comment comment) {
        PostedItinerary postedItinerary = postRepo.findByPostID(postID);
        if (postedItinerary != null) {
            postedItinerary.removeComment(comment);
            List<PostedItinerary.Comment> comments = postCommentsMap.getOrDefault(postID, new ArrayList<>());
            comments.remove(comment);
            postCommentsMap.put(postID, comments);
            postRepo.save(postedItinerary);
        } else {
            System.out.println("PostedItinerary with postID " + postID + " not found.");
        }
    }


}
