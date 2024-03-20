package MS3_3.Backend.Place;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PlaceRepository extends JpaRepository<Place, String> {
    @Transactional
    void deleteByUniqueCode(String uniqueCode);

    Place findByUniqueCode(String uniqueCode);
}
