package MS3_3.Backend.AmbassadorBlogPost;

import MS3_3.Backend.Ambassador.Ambassador;
import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostImage;
import MS3_3.Backend.FileUpload.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BlogPost {

    @ManyToOne
    @JsonIgnore
    private Ambassador ambassador;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blogPostId;

    private String userName;

    private String blogPostTitle;

    private String postDate;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogPostImage> blogImageList;


    public BlogPost(String blogPostTitle, String postDate, String userName) {
        this.userName = userName;
        this.postDate = postDate;
        this.blogPostTitle = blogPostTitle;
        this.blogImageList = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return blogPostId;
    }

    public String getBlogPostTitle() {
        return blogPostTitle;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public BlogPost() {
        this.blogImageList = new ArrayList<>();
    }

    public List<BlogPostImage> getBlogImageList() {
        return blogImageList;
    }

    public void blogImageList(List<BlogPostImage> blogImageList) {
        this.blogImageList = blogImageList;
    }

    public void setBlogPostTitle(String blogPostTitle) {
        this.blogPostTitle = blogPostTitle;
    }

    public Ambassador getAmbassador() {
        return ambassador;
    }

    public void setAmbassador(Ambassador ambassador) {
        this.ambassador = ambassador;
    }
}
