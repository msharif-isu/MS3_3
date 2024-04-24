package MS3_3.Backend.FileUpload;

import MS3_3.Backend.TravelGroups.TravelGroup;
import MS3_3.Backend.TravelGroups.TravelGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
<<<<<<< HEAD
        travelGroupRepository.findByTravelGroupId(groupId).setGroupImage(imageData);
=======
        long oldId = travelGroupRepository.findById(groupId).getGroupImage().getId();
        travelGroupRepository.findById(groupId).setGroupImage(imageData);
>>>>>>> origin/main
        if (imageData != null) {
            return "file uploaded successfully to Group " + travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupName() + " : " + file.getOriginalFilename();
        }
        if(oldId != 38) {
            repository.deleteById(oldId);
        }
        return null;
    }

    public String changeGroupImage(MultipartFile file, int groupId) throws IOException {

        Image imageData = repository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build()
        );
<<<<<<< HEAD
        TravelGroup travelGroup =  travelGroupRepository.findByTravelGroupId(groupId);
=======
        TravelGroup travelGroup =  travelGroupRepository.findById(groupId);
        long oldId = travelGroup.getGroupImage().getId();
>>>>>>> origin/main
        travelGroup.setGroupImage(imageData);
        travelGroupRepository.save(travelGroup);
        if (imageData != null) {
            return "file uploaded successfully to Group " + travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupName() + " : " + file.getOriginalFilename();
        }
        if(travelGroup.getGroupImage().getId() != 38) {
            repository.deleteById(oldId);
        }
        return null;
    }

    public byte[] downloadImageByFileName(String fileName){
        Image dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }

    public byte[] downloadImageByGroupId(int groupId){
        Image dbImageData = travelGroupRepository.findByTravelGroupId(groupId).getGroupImage();
        byte[] images=ImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }

    public byte[] downloadImageByImageId(int imageId){
        Image dbImageData = repository.findById(imageId);
        byte[] images=ImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }

    public String changeImageByGroupId(int groupId, Image newImage){
<<<<<<< HEAD
        TravelGroup travelGroup =  travelGroupRepository.findByTravelGroupId(groupId);
        travelGroup.setGroupImage(newImage);
        travelGroupRepository.save(travelGroup);
        return "file successfully deleted from Group: " + travelGroupRepository.findByTravelGroupId(groupId).getTravelGroupName();
=======
        TravelGroup travelGroup =  travelGroupRepository.findById(groupId);
        long oldId = travelGroup.getGroupImage().getId();
        travelGroup.setGroupImage(newImage);
        travelGroupRepository.save(travelGroup);
        if(travelGroup.getGroupImage().getId() != 38) {
            repository.deleteById(oldId);
        }
        return "file successfully deleted from Group: " + travelGroupRepository.findById(groupId).getTravelGroupName();
>>>>>>> origin/main
    }

}
