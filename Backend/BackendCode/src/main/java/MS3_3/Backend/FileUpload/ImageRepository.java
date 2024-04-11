package MS3_3.Backend.FileUpload;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByName(String fileName);
    Image findById(int id);

    @Transactional
    void deleteById(int id);

}

