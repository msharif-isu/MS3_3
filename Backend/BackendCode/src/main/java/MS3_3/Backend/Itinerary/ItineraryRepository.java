package MS3_3.Backend.Itinerary;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<Itinerary,String> {
    @Transactional
    void deleteByShareCode(String shareCode);

    Itinerary findByShareCode(String shareCode);
}
