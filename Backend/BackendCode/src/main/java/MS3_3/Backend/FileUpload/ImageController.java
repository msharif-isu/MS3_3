package MS3_3.Backend.FileUpload;

import MS3_3.Backend.Groups.TravelGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class ImageController {

    @Autowired
    private StorageService service;

    @Autowired
    private TravelGroupRepository travelGroupRepository;

    @Autowired
    private ImageRepository imageRepository;

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
        Image copy = new Image();
        copy.setImageData(imageRepository.findById(38).getImageData());
        copy.setType(imageRepository.findById(38).getType());
        copy.setName(imageRepository.findById(38).getName());
        imageRepository.save(copy);
        String uploadImage = service.changeImageByGroupId(groupId,copy);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
}
