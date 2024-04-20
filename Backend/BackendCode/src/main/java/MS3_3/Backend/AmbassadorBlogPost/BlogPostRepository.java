package MS3_3.Backend.AmbassadorBlogPost;

import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostImage;
import MS3_3.Backend.FileUpload.Image;
import MS3_3.Backend.Groups.TravelGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    BlogPost findByBlogPostId(int blogPostId);

    @Transactional
    void deleteByBlogPostId(int blogPostId);
}
