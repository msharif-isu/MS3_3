package MS3_3.Backend.Place;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    @Transactional
    void deleteByUniqueCode(int uniqueCode);

    Place findByUniqueCode(int uniqueCode);
}
