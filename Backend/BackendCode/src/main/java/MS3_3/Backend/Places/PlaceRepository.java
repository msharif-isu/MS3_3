package MS3_3.Backend.Places;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place,String> {
    @Transactional
    void deleteByPlaceName(String placeName);

    Place findByPlaceName(String placeName);
}
