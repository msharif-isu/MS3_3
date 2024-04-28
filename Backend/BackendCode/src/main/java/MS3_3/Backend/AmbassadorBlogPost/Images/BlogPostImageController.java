package MS3_3.Backend.AmbassadorBlogPost.Images;

import MS3_3.Backend.TravelGroups.TravelGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class BlogPostImageController {

    @Autowired
    private BlogPostStorageService service;

    @Autowired
    private TravelGroupRepository travelGroupRepository;

    @Autowired
    private BlogPostImageRepository imageRepository;
/**
    @PostMapping("/Image")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/Image/{Id}")
    public ResponseEntity<?> downloadImageByName(@PathVariable int Id){
        byte[] imageData=service.downloadImageByImageId(Id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("/Group/Image/{groupId}")
    public ResponseEntity<?> uploadGroupImage(@PathVariable int groupId, @RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = service.uploadGroupImage(file, groupId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/Group/Image/{groupId}")
    public ResponseEntity<?> downloadGroupImage(@PathVariable int groupId){
        byte[] imageData=service.downloadImageByGroupId(groupId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PutMapping("/Group/Image/{groupId}")
    public ResponseEntity<?> changeGroupImage(@PathVariable int groupId, @RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = service.changeGroupImage(file, groupId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @DeleteMapping("/Group/Image/{groupId}")
    public ResponseEntity<?> deleteGroupImage(@PathVariable int groupId) {
        BlogPostImage copy = new BlogPostImage();
        copy.setImageData(imageRepository.findById(38).getImageData());
        copy.setType(imageRepository.findById(38).getType());
        copy.setName(imageRepository.findById(38).getName());
        imageRepository.save(copy);
        String uploadImage = service.changeImageByGroupId(groupId,copy);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
    */
}

