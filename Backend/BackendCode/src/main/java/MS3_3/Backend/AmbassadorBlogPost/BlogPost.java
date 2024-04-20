package MS3_3.Backend.AmbassadorBlogPost;

import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostImage;
import MS3_3.Backend.FileUpload.Image;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BlogPost {

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogPostImage> groupImageList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blogPostId;

    private String blogPostTitle;

    public BlogPost(String blogPostTitle) {
        this.blogPostTitle = blogPostTitle;
        this.groupImageList = new ArrayList<>();
    }

    public BlogPost() {
        this.groupImageList = new ArrayList<>();
    }

    public List<BlogPostImage> getGroupImageList() {
        return groupImageList;
    }

    public void setGroupImageList(List<BlogPostImage> groupImageList) {
        this.groupImageList = groupImageList;
    }

    public String getBlogPostTitle() {
        return blogPostTitle;
    }

    public void setBlogPostTitle(String blogPostTitle) {
        this.blogPostTitle = blogPostTitle;
    }

    public int getId() {
        return blogPostId;
    }
}
