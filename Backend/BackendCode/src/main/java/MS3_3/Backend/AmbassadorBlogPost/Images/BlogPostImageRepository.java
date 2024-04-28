package MS3_3.Backend.AmbassadorBlogPost.Images;

import MS3_3.Backend.FileUpload.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostImageRepository extends JpaRepository<BlogPostImage, Long> {
    BlogPostImage findByName(String fileName);
    BlogPostImage findById(int id);

    @Transactional
    void deleteById(int id);
}

