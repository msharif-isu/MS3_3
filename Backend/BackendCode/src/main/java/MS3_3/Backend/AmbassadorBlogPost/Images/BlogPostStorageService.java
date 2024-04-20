package MS3_3.Backend.AmbassadorBlogPost.Images;

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
/**
    public String uploadGroupImage(MultipartFile file, int groupId) throws IOException {

        BlogPostImage imageData = imageRepository.save(BlogPostImage.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(BlogPostImageUtils.compressImage(file.getBytes())).build()
        );
        long oldId = travelGroupRepository.findById(groupId).getGroupImage().getId();
        //travelGroupRepository.findById(groupId).setGroupImage(imageData);
        if (imageData != null) {
            return "file uploaded successfully to Group " + travelGroupRepository.findById(groupId).getTravelGroupName() + " : " + file.getOriginalFilename();
        }
        if(oldId != 38) {
            repository.deleteById(oldId);
        }
        return null;
    }

    public String changeGroupImage(MultipartFile file, int groupId) throws IOException {

        BlogPostImage imageData = repository.save(BlogPostImage.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(BlogPostImageUtils.compressImage(file.getBytes())).build()
        );
        TravelGroup travelGroup =  travelGroupRepository.findById(groupId);
        long oldId = travelGroup.getGroupImage().getId();
        //travelGroup.setGroupImage(imageData);
        travelGroupRepository.save(travelGroup);
        if (imageData != null) {
            return "file uploaded successfully to Group " + travelGroupRepository.findById(groupId).getTravelGroupName() + " : " + file.getOriginalFilename();
        }
        if(travelGroup.getGroupImage().getId() != 38) {
            repository.deleteById(oldId);
        }
        return null;
    }

    public byte[] downloadImageByFileName(String fileName){
        BlogPostImage dbImageData = repository.findByName(fileName);
        byte[] images= BlogPostImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }

    public byte[] downloadImageByGroupId(int groupId){
        BlogPostImage dbImageData = travelGroupRepository.findById(groupId).getGroupImage();
        byte[] images= BlogPostImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }

    public byte[] downloadImageByImageId(int imageId){
        BlogPostImage dbImageData = repository.findById(imageId);
        byte[] images= BlogPostImageUtils.decompressImage(dbImageData.getImageData());
        return images;
    }

    public String changeImageByGroupId(int groupId, BlogPostImage newImage){
        TravelGroup travelGroup =  travelGroupRepository.findById(groupId);
        long oldId = travelGroup.getGroupImage().getId();
        //travelGroup.setGroupImage(newImage);
        travelGroupRepository.save(travelGroup);
        if(travelGroup.getGroupImage().getId() != 38) {
            repository.deleteById(oldId);
        }
        return "file successfully deleted from Group: " + travelGroupRepository.findById(groupId).getTravelGroupName();
    }
*/
}
