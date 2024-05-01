package MS3_3.Backend.AmbassadorBlogPost.Images;

import MS3_3.Backend.TravelGroups.TravelGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogPostImageController {

    @Autowired
    private BlogPostStorageService service;

    @Autowired
    private TravelGroupRepository travelGroupRepository;

    @Autowired
    private BlogPostImageRepository imageRepository;

//    @PostMapping("/Blog/Image")
//    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
//        String uploadImage = service.uploadImage(file);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(uploadImage);
//    }
//
//    @GetMapping("/Image/{Id}")
//    public ResponseEntity<?> downloadImageByName(@PathVariable int Id){
//        byte[] imageData=service.downloadImageByImageId(Id);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageData);
//    }
}

