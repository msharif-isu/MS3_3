package MS3_3.Backend.FileUpload;

import MS3_3.Backend.TravelGroups.TravelGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "ImageData")
@Data
@AllArgsConstructor
@Builder
public class Image {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnore
    private TravelGroup travelGroup;


    private String name;
    private String type;

    //ALTER TABLE image_data MODIFY COLUMN imagedata LONGBLOB;
    @Lob
    @Column(name = "imagedata", columnDefinition="LONGBLOB")
    private byte[] imageData;

    public Image(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }

    public Image() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


}


