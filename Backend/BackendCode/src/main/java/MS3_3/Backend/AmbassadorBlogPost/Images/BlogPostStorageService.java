package MS3_3.Backend.AmbassadorBlogPost.Images;

import MS3_3.Backend.AmbassadorBlogPost.BlogPost;
import MS3_3.Backend.AmbassadorBlogPost.BlogPostRepository;
import MS3_3.Backend.Groups.TravelGroup;
import MS3_3.Backend.Groups.TravelGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BlogPostStorageService {
    @Autowired
    private BlogPostImageRepository imageRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    public String uploadImage(MultipartFile file) throws IOException {

        BlogPostImage imageData = imageRepository.save(BlogPostImage.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(BlogPostImageUtils.compressImage(file.getBytes())).build()
        );
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public String addBlogImage(MultipartFile file, int blogId) throws IOException {

        BlogPostImage imageData = imageRepository.save(BlogPostImage.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(BlogPostImageUtils.compressImage(file.getBytes())).build()
        );
        BlogPost tempBlogPost = blogPostRepository.findByBlogPostId(blogId);
        tempBlogPost.getGroupImageList().add(imageData);
        blogPostRepository.save(tempBlogPost);
        if (imageData != null) {
            return "file uploaded successfully to Blog " + blogPostRepository.findByBlogPostId(blogId).getId() + " : " + file.getOriginalFilename();
        }
        return null;
    }

    public String deleteBlogImage(int imageId, int blogId) throws IOException {

        BlogPostImage imageData = imageRepository.findById(imageId);
        BlogPost tempBlogPost = blogPostRepository.findByBlogPostId(blogId);
        tempBlogPost.getGroupImageList().remove(imageData);
        blogPostRepository.save(tempBlogPost);
        if (imageData != null) {
            return "file deleted successfully from Blog " + blogPostRepository.findByBlogPostId(blogId).getId();
        }
        return null;
    }

    public byte[] downloadImageByFileName(String fileName){
        BlogPostImage dbImageData = imageRepository.findByName(fileName);
        byte[] images= BlogPostImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }

    public byte[] downloadImageByImageId(int imageId){
        BlogPostImage dbImageData = imageRepository.findById(imageId);
        byte[] images= BlogPostImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }
}
