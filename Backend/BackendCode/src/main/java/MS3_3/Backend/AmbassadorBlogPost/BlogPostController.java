package MS3_3.Backend.AmbassadorBlogPost;

import MS3_3.Backend.Ambassador.AmbassadorRepository;
import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostImage;
import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostImageRepository;
import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostImageUtils;
import MS3_3.Backend.AmbassadorBlogPost.Images.BlogPostStorageService;
import MS3_3.Backend.FileUpload.Image;
import MS3_3.Backend.TravelGroups.TravelGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class BlogPostController {

    @Autowired
    private BlogPostStorageService service;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private BlogPostImageRepository imageRepository;

    @Autowired
    private AmbassadorRepository ambassadorRepository;

    @GetMapping("/BlogPost/Username/{userName}")
    public List<BlogPost> getBlogByUserName(@PathVariable String userName){
        return ambassadorRepository.findByUserName(userName).getBlogPosts();
    }

    @GetMapping("BlogPost/Image/{Id}")
    public ResponseEntity<?> downloadBlogImagesByName(@PathVariable int Id){
        byte[] imageData=service.downloadImageByImageId(Id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @GetMapping("BlogPost/CoverImage/{blogId}")
    public ResponseEntity<?> downloadCoverImageById(@PathVariable int blogId){
        if(blogPostRepository.findByBlogPostId(blogId).getBlogImageList().size() != 0) {
            Random rand = new Random();
            int size = blogPostRepository.findByBlogPostId(blogId).getBlogImageList().size() - 1;
            int tempId = rand.nextInt(size + 1);
            int CoverId = blogPostRepository.findByBlogPostId(blogId).getBlogImageList().get(tempId).getId();
            byte[] imageData = service.downloadImageByImageId(CoverId);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageData);
        }
        else{
            byte[] imageData = service.downloadImageByImageId(1);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageData);
        }
    }

    @PostMapping("/BlogPost/{userName}/{blogName}/{postDate}")
    public BlogPost createBlogPost(@PathVariable String userName,@PathVariable String blogName,@PathVariable String postDate) {
        BlogPost blogPost = new BlogPost(blogName,postDate, ambassadorRepository.findByUserName(userName).getUserName());
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

    @DeleteMapping("/BlogPost/Blank/{blogId}")
    public BlogPost resetBlogPost(@PathVariable int blogId) {
        BlogPost temp = blogPostRepository.findByBlogPostId(blogId);
        temp.setBlogPostTitle("");
        temp.getBlogImageList().clear();
        BlogPost output = blogPostRepository.save(temp);
        return output;
    }

    @DeleteMapping("/BlogPost/{blogId}")
    public String deleteBlogPost(@PathVariable int blogId) {
        blogPostRepository.deleteByBlogPostId(blogId);
        return "Blog Deleted";
    }

    @DeleteMapping("/BlogPost/Image/{blogImageId}")
    public String deleteBlogPostImage(@PathVariable int blogImageId) {
        imageRepository.deleteById(blogImageId);
        return "Image " + blogImageId + " deleted";
    }

    @PostMapping("/Blog/Image")
    public ResponseEntity<?> uploadNewBlogImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("Blog/Image/{Id}")
    public ResponseEntity<?> downloadNewBlogImageByName(@PathVariable int Id){
        byte[] imageData=service.downloadImageByImageId(Id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

}
