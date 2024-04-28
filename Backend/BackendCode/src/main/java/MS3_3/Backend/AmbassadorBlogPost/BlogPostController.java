package MS3_3.Backend.AmbassadorBlogPost;

import MS3_3.Backend.Ambassador.AmbassadorRepository;
import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostImage;
import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostImageRepository;
import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostImageUtils;
import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostStorageService;
import MS3_3.Backend.FileUpload.Image;
import MS3_3.Backend.Groups.TravelGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BlogPostController {

    @Autowired
    private BlogPostStorageService service;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private BlogPostImageRepository imageRepository;

    @Autowired
    AmbassadorRepository ambassadorRepository;

    @GetMapping("BlogPost/Image/{Id}")
    public ResponseEntity<?> downloadImageByName(@PathVariable int Id){
        byte[] imageData=service.downloadImageByImageId(Id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/BlogPost/{userName}/{blogName}/{postDate}")
    public BlogPost createBlogPost(@PathVariable String userName,@PathVariable String blogName,@PathVariable String postDate) {
        BlogPost blogPost = new BlogPost(blogName,postDate);
        blogPost.setAmbassador(ambassadorRepository.findByUserName(userName));
        blogPostRepository.save(blogPost);
        return blogPostRepository.findByBlogPostId(blogPost.getId());
    }

    @GetMapping("/BlogPost/{blogId}")
    public BlogPost downloadBlogImage(@PathVariable int blogId){
        return blogPostRepository.findByBlogPostId(blogId);
    }

    @GetMapping("/BlogPost/Images/{blogId}")
    public List<Integer> downloadImagesByBlogId(@PathVariable int blogId) {
        List<Integer> responses = new ArrayList<>();
        int i =0;
        while(i < blogPostRepository.findByBlogPostId(blogId).getBlogImageList().size()){
            int j = blogPostRepository.findByBlogPostId(blogId).getBlogImageList().get(i).getId();
            responses.add(j);
            i++;
        }
        return responses;
    }

    @PutMapping("/BlogPost/Image/{blogId}")
    public ResponseEntity<?> addBlogImage(@PathVariable int blogId, @RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = service.addBlogImage(file, blogId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @PutMapping("/BlogPost/{blogId}/{blogName}/{postDate}")
    public BlogPost updateBlogPost(@PathVariable int blogId, @PathVariable String blogName,@PathVariable String postDate) {
        BlogPost blogPost = blogPostRepository.findByBlogPostId(blogId);
        blogPost.setBlogPostTitle(blogName);
        blogPost.setPostDate(postDate);
        BlogPost output = blogPostRepository.save(blogPost);
        return output;
    }

    @DeleteMapping("/BlogPost/{blogId}")
    public BlogPost deleteBlogPost(@PathVariable int blogId) {
        BlogPost temp = blogPostRepository.findByBlogPostId(blogId);
        temp.setBlogPostTitle("");
        temp.getBlogImageList().clear();
        BlogPost output = blogPostRepository.save(temp);
        return output;
    }

    @DeleteMapping("/BlogPost/Image/{blogImageId}")
    public String deleteBlogPostImage(@PathVariable int blogImageId) {
        imageRepository.deleteById(blogImageId);
        return "Image " + blogImageId + " deleted";
    }

}
