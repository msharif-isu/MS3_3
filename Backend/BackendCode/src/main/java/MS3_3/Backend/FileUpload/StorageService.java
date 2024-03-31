package MS3_3.Backend.FileUpload;

import MS3_3.Backend.Groups.TravelGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private ImageRepository repository;

    @Autowired
    private TravelGroupRepository travelGroupRepository;

    public String uploadImage(MultipartFile file) throws IOException {

        Image imageData = repository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build()
        );
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public String uploadGroupImage(MultipartFile file, int groupId) throws IOException {

        Image imageData = repository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build()
        );
       // travelGroupRepository.findById(groupId).setGroupImage(imageData);
        if (imageData != null) {
            return "file uploaded successfully to Group " + travelGroupRepository.findById(groupId).getTravelGroupName() + " : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImageByFileName(String fileName){
        Image dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }

    public byte[] downloadImageByGroupId(int groupId){
        Image dbImageData = travelGroupRepository.findById(groupId).getGroupImage();
        byte[] images=ImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }

}
